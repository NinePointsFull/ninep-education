package com.ninep.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.api.course.FeignCourseService;
import com.ninep.api.system.FeignSystemService;
import com.ninep.common.cache.CacheRedis;
import com.ninep.common.DTO.CourseViewDTO;
import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.common.enums.*;
import com.ninep.common.exception.BaseException;
import com.ninep.common.pay.PayFace;
import com.ninep.common.pay.req.TradeNotifyReq;
import com.ninep.common.pay.req.TradeOrderReq;
import com.ninep.common.pay.resp.TradeNotifyResp;
import com.ninep.common.pay.resp.TradeOrderResp;
import com.ninep.common.pay.util.AliPayConfig;
import com.ninep.common.pay.util.PayModelEnum;
import com.ninep.common.pay.util.TradeStatusEnum;
import com.ninep.common.pay.util.WxPayConfig;
import com.ninep.common.utils.*;
import com.ninep.user.auth.response.AuthOrderPayResp;
import com.ninep.user.auth.vo.AuthOrderCancelVo;
import com.ninep.user.auth.vo.AuthOrderPayVo;
import com.ninep.user.entity.OrderInfo;
import com.ninep.user.entity.OrderPay;
import com.ninep.user.entity.Users;
import com.ninep.user.mapper.OrderInfoMapper;
import com.ninep.user.mapper.OrderPayMapper;
import com.ninep.user.mapper.UsersMapper;
import com.ninep.user.service.IOrderPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单支付信息表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
@Slf4j
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {


    private static final String NOTIFYURL = "{domain}/user/api/order/pay/notify/{payModel}/{payImpl}";

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private FeignCourseService feignCourseService;
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private  Map<String, PayFace> payFaceMap;
    @Resource
    private CacheRedis cacheRedis;
    @Resource
    private FeignSystemService feignSystemService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthOrderPayResp pay(AuthOrderPayVo payVo) {
        AssertUtil.notNull(payVo.getCourseId(),"请选择需要购买的课程");
        //课程是否已经购买
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getCourseId, payVo.getCourseId()).eq(OrderInfo::getUserId, ThreadContextUtil.userId()).eq(OrderInfo::getOrderStatus,OrderStatusEnum.SUCCESS));
        if (orderInfo!=null && OrderStatusEnum.SUCCESS.getCode().equals(orderInfo.getOrderStatus())){
            throw new BaseException("该课程已经购买");
        }

        //校验课程信息
        CourseViewDTO courseViewDTO = feignCourseService.view(Arrays.asList(payVo.getCourseId())).get(0);
        if (courseViewDTO!=null){
            if (!StatusIdEnum.YES.getCode().equals(courseViewDTO.getStatusId()) || !PutawayEnum.UP.getCode().equals(courseViewDTO.getIsPutaway())) {
                throw new BaseException("该课程不允许购买");
            }
        }else if (courseViewDTO==null){
            throw new BaseException("系统异常");
        }

        //校验用户信息
        Users users = usersMapper.selectById(ThreadContextUtil.userId());
        if (users!=null){
            if (!StatusIdEnum.YES.getCode().equals(users.getStatusId())){
                throw new BaseException("该用户已被禁用");
            }
        }else if (users==null){
            throw new BaseException("系统异常");
        }

        //创建支付订单
        OrderPay orderPay=createOrderPay(payVo,courseViewDTO);
        //创建订单
        orderInfo=createOrderInfo(orderPay,courseViewDTO,users);

        //发起支付
        TradeOrderResp tradeOrderDTO=createTradeOrder(payVo,courseViewDTO,orderPay);
        if (tradeOrderDTO.isSuccess()) {
            //返回支付信息
            AuthOrderPayResp resp = BeanUtil.copyProperties(orderInfo, AuthOrderPayResp.class);
            resp.setPayMessage(tradeOrderDTO.getPayMessage());
            resp.setSerialNumber(orderPay.getSerialNumber());
            return resp;
        }
       throw new BaseException("下单失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String notify(HttpServletRequest request, Integer payModel, String payImpl) {
        // step1：获取支付通知参数
        TradeNotifyReq notifyParam = getTradeNotifyParam(request);
        // 支付模式
        notifyParam.setPayModel(payModel);
        // 获取支付配置
        getPayConfig(notifyParam);
        // 获取支付通道
        PayFace payFace = payFaceMap.get(payImpl);
        TradeNotifyResp resp = payFace.tradeNotify(notifyParam);
        log.info("回调通知处理={}", JSUtil.toJsonString(resp));

        if (resp.isSuccess() && resp.getTradeStatus().equals(TradeStatusEnum.SUCCESS.getCode())) {
            // 处理交易成功订单
            OrderPay orderPay = baseMapper.selectOne(new LambdaQueryWrapper<OrderPay>().eq(OrderPay::getSerialNumber, resp.getTradeOrderNo()));
            if (ObjectUtil.isNotEmpty(orderPay) && !orderPay.getOrderStatus().equals(OrderStatusEnum.SUCCESS.getCode())) {
                OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo,orderPay.getOrderNo()));
                if (ObjectUtil.isNotEmpty(orderInfo) && !orderInfo.getOrderStatus().equals(OrderStatusEnum.SUCCESS.getCode())) {
                    // 更新支付订单
                    updateOrderPay(orderPay);
                    // 更新订单
                    updateOrderInfo(orderInfo);
                    // 课程绑定用户
                    feignCourseService.binding(new UserCourseBindingDTO().setCourseId(orderInfo.getCourseId()).setUserId(orderInfo.getUserId()).setBuyType(BuyTypeEnum.BUY.getCode()));
                }
            }
        }
        return resp.getReturnMsg();
    }

    @Override
    public String cancel(AuthOrderCancelVo authOrderCancelVo) {
        //根据订单号获取订单
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, authOrderCancelVo.getOrderNo()));
        if (ObjectUtil.isEmpty(orderInfo) || orderInfo.getOrderStatus().equals(OrderStatusEnum.SUCCESS.getCode())) {
            // 成功的订单，不能操作
            throw new BaseException("该订单不存在或以交易成功，请查看订单号是否正确！");
        }
        orderInfo.setOrderStatus(OrderStatusEnum.CLOSE.getCode());
        orderInfoMapper.updateById(orderInfo);
        return "操作成功";
    }

    @Override
    public void updateOrderStatusByOrderNo(Long orderNo, Integer orderStatus) {
        LambdaQueryWrapper<OrderPay> orderPayLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderPayLambdaQueryWrapper.eq(OrderPay::getOrderNo,orderNo);
        OrderPay one = this.getOne(orderPayLambdaQueryWrapper);
        if (one==null){
            throw new BaseException("找不到该订单");
        }
        one.setOrderStatus(orderStatus);
        this.updateById(one);
    }

    private void getPayConfig(TradeNotifyReq req) {
        Map<String, String> payConfig = cacheRedis.getByJson(ConstantsUtil.RedisPre.PAY + ConfigTypeEnum.PAY.getCode(), HashMap.class);
        if (ObjectUtil.isEmpty(payConfig)) {
            payConfig = feignSystemService.getByConfigType(ConfigTypeEnum.PAY.getCode());
            cacheRedis.set(ConstantsUtil.RedisPre.PAY + ConfigTypeEnum.PAY.getCode(), payConfig);
        }
        req.setAliPayConfig(BeanUtil.mapToBean(payConfig, AliPayConfig.class,true,CopyOptions.create()));
        req.setWxPayConfig(BeanUtil.mapToBean(payConfig, WxPayConfig.class,true,CopyOptions.create()));
    }

    private void updateOrderInfo(OrderInfo orderInfo) {
        OrderInfo info = new OrderInfo();
        info.setId(orderInfo.getId());
        info.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
        info.setPayTime(LocalDateTime.now());
        orderInfoMapper.updateById(info);
    }

    private void updateOrderPay(OrderPay orderPay) {
        OrderPay pay = new OrderPay();
        pay.setId(orderPay.getId());
        pay.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
        pay.setPayTime(LocalDateTime.now());
        baseMapper.updateById(pay);
    }



    /**
     * 获取交易通知参数
     *
     * @param request 通知请求
     * @return 交易通知参数
     */
    private TradeNotifyReq getTradeNotifyParam(HttpServletRequest request) {
        Map<String, String> headerMap = getHeaderParam(request);
        log.info("交易回调通知--header参数：{}", headerMap);

        // 获取Query参数
        Map<String, String> queryParamMap = getQueryParam(request);
        log.info("交易回调通知--query参数：{}", queryParamMap);

        // 获取body参数
        String bodyParam = getBodyParam(request);
        log.info("交易回调通知--body参数：{}", bodyParam);
        return new TradeNotifyReq().setHeaderMap(headerMap).setQueryParamMap(queryParamMap).setBodyParam(bodyParam);
    }

    /**
     * 获取HttpServletRequest请求body参数
     *
     * @param request 回调请求
     * @return body参数
     */
    protected String getBodyParam(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder sb = new StringBuilder();
            br = request.getReader();
            if (br != null) {
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("HttpServletRequest-获取body参数失败", e);
            return null;
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("HttpServletRequest--获取body参数关闭流失败", e);
                }
            }
        }
    }

    protected Map<String, String> getQueryParam(HttpServletRequest request) {
        Map<String, String> queryParamMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String val = request.getParameter(name);
            queryParamMap.put(name, val);
        }
        return queryParamMap;
    }


    protected Map<String, String> getHeaderParam(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        // 获取请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String val = request.getHeader(name);
            headerMap.put(name, val);
        }
        return headerMap;
    }

    /**
     * 进行支付
     * @param payVo
     * @param courseViewDTO
     * @param orderPay
     * @return
     */
    private TradeOrderResp createTradeOrder(AuthOrderPayVo payVo, CourseViewDTO courseViewDTO, OrderPay orderPay) {
        //TODO 支付
        String impl = PayTypeEnum.byCode(payVo.getPayType()).getImpl();
        PayFace payFace = payFaceMap.get(impl);
        if (ObjectUtil.isNull(payFace)) {
            log.error("该接口没实现，payType={}",payVo.getPayType());
            throw new BaseException("获取失败");

        }

        TradeOrderReq orderReq = new TradeOrderReq();
        // 获取支付配置
        getPayConfig(orderReq);

        // 直连模式
        orderReq.setPayModel(PayModelEnum.DIRECT_SALES.getCode());
        orderReq.setTradeSerialNo(orderPay.getSerialNumber().toString());
        orderReq.setAmount(orderPay.getCoursePrice());
        orderReq.setGoodsName(courseViewDTO.getCourseName());
        orderReq.setUserClientIp(payVo.getUserClientIp());
        orderReq.setNotifyUrl(getNotifyUrl(orderReq.getPayModel(), impl));
        if (StringUtils.hasText(payVo.getQuitUrl())) {
            orderReq.setQuitUrl(payVo.getQuitUrl() + "?tradeSerialNo=" + orderReq.getTradeSerialNo());
        }
        return payFace.tradeOrder(orderReq);
    }

    private String getNotifyUrl(Integer payModel, String impl) {
        String websiteDomain = cacheRedis.get(ConstantsUtil.RedisPre.DOMAIN);
        if (!StringUtils.hasText(websiteDomain)) {
            websiteDomain = feignSystemService.getwWbsiteDomainByKey("websiteDomain");
            cacheRedis.set(ConstantsUtil.RedisPre.DOMAIN, websiteDomain, 1, TimeUnit.HOURS);
        }
        return NOTIFYURL.replace("{domain}", websiteDomain).replace("{payModel}", payModel.toString()).replace("{payImpl}", impl);
    }

    /**
     *
     * @param orderReq
     */
    private void getPayConfig(TradeOrderReq orderReq) {
        Map<String, String> payConfig = cacheRedis.getByJson(ConstantsUtil.RedisPre.PAY + ConfigTypeEnum.PAY.getCode(), HashMap.class);
        if (ObjectUtil.isEmpty(payConfig)) {
            payConfig = feignSystemService.getByConfigType(ConfigTypeEnum.PAY.getCode());
            cacheRedis.set(ConstantsUtil.RedisPre.PAY + ConfigTypeEnum.PAY.getCode(), payConfig);
        }
        orderReq.setAliPayConfig(BeanUtil.mapToBean(payConfig, AliPayConfig.class,true, CopyOptions.create()));
        orderReq.setWxPayConfig(BeanUtil.mapToBean(payConfig, WxPayConfig.class,true,CopyOptions.create()));
    }

    /**
     * 创建订单
     * @param courseViewDTO
     * @param users
     * @return
     */
    private OrderInfo createOrderInfo(OrderPay orderPay, CourseViewDTO courseViewDTO, Users users) {
        OrderInfo orderInfo = BeanUtil.copyProperties(orderPay,OrderInfo.class);
        orderInfo.setOrderNo(orderPay.getOrderNo());
        orderInfo.setUserId(users.getId());
        orderInfo.setMobile(users.getMobile());
        orderInfo.setRegisterTime(LocalDateTime.now());
        orderInfo.setCourseId(courseViewDTO.getId());
        orderInfoMapper.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 创建支付订单
     * @param payVo
     * @param courseViewDTO
     * @return
     */
    private OrderPay createOrderPay(AuthOrderPayVo payVo, CourseViewDTO courseViewDTO) {
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(NOUtil.getOrderNo());
        orderPay.setSerialNumber(NOUtil.getSerialNumber());
        orderPay.setRulingPrice(courseViewDTO.getRulingPrice());
        orderPay.setCoursePrice(courseViewDTO.getCoursePrice());
        orderPay.setPayType(payVo.getPayType());
        orderPay.setOrderStatus(OrderStatusEnum.WAIT.getCode());
        orderPay.setRemarkCus(payVo.getRemarkCus());
        orderPay.setPayTime(LocalDateTime.now());
        this.save(orderPay);
        return orderPay;

    }
}
