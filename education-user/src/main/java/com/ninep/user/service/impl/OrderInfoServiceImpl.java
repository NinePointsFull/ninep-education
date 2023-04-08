package com.ninep.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.api.course.FeignCourseService;
import com.ninep.common.DTO.CourseViewDTO;
import com.ninep.common.enums.OrderStatusEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.ThreadContextUtil;
import com.ninep.user.admin.response.OrderInfoPageResp;
import com.ninep.user.admin.DTO.OrderInfoEditDTO;
import com.ninep.user.admin.DTO.OrderInfoPageDTO;
import com.ninep.user.auth.response.AuthOrderInfoResp;
import com.ninep.user.auth.vo.AuthOrderInfoVo;
import com.ninep.user.entity.OrderInfo;
import com.ninep.user.mapper.OrderInfoMapper;
import com.ninep.user.service.IOrderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Resource
    private FeignCourseService feignCourseService;

    @Override
    public Page<AuthOrderInfoResp> listForPage(AuthOrderInfoVo infoVo) {
        IPage<OrderInfo> orderInfoIPage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(infoVo.getPageCurrent(), infoVo.getPageSize());
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, ThreadContextUtil.userId());
        wrapper.orderByDesc(OrderInfo::getId);
        if (infoVo.getOrderStatus()!=null){
            wrapper.eq(OrderInfo::getOrderStatus,infoVo.getOrderStatus());
        } else if (infoVo.getOrderStatus()==null) {
            // 默认关闭状态下的订单不显示
            wrapper.notIn(OrderInfo::getOrderStatus,OrderStatusEnum.CLOSE.getCode());
        }
        this.page(orderInfoIPage,wrapper);
        Page<OrderInfo> parse = PageUtil.parse(orderInfoIPage);
        Page<AuthOrderInfoResp> transform = PageUtil.transform(parse, AuthOrderInfoResp.class);
        if (!CollUtil.isEmpty(transform.getList())){
            List<Long> courseIds = transform.getList().stream().map(AuthOrderInfoResp::getCourseId).collect(Collectors.toList());
            Map<Long, CourseViewDTO> courseViewDTOMap = feignCourseService.view(courseIds).stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
            for (AuthOrderInfoResp authOrderInfoResp:transform.getList()){
                authOrderInfoResp.setCourseName(courseViewDTOMap.get(authOrderInfoResp.getCourseId()).getCourseName());
                authOrderInfoResp.setCourseLogo(courseViewDTOMap.get(authOrderInfoResp.getCourseId()).getCourseLogo());
            }
        }
        return transform;
    }

    @Override
    public Page<OrderInfoPageResp> pageForOrder(OrderInfoPageDTO orderInfoPageDTO) {
        IPage<OrderInfo> orderInfoPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(orderInfoPageDTO.getPageCurrent(), orderInfoPageDTO.getPageSize());
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        if (orderInfoPageDTO.getOrderNo()!=null && orderInfoPageDTO.getOrderNo()>0){
            wrapper.like(OrderInfo::getOrderNo, orderInfoPageDTO.getOrderNo());
        }
        if (StringUtils.hasText(orderInfoPageDTO.getMobile())){
            wrapper.like(OrderInfo::getMobile, orderInfoPageDTO.getMobile());
        }
        wrapper.orderByDesc(OrderInfo::getId);
        this.page(orderInfoPage,wrapper);
        Page<OrderInfo> parse = PageUtil.parse(orderInfoPage);
        Page<OrderInfoPageResp> transform = PageUtil.transform(parse, OrderInfoPageResp.class);
        if (CollUtil.isNotEmpty(transform.getList())){
            //查出所有课程
            List<Long> courseIds = transform.getList().stream().map(OrderInfoPageResp::getCourseId).collect(Collectors.toList());
            List<CourseViewDTO> view = feignCourseService.view(courseIds);
            Map<Long, CourseViewDTO> courseViewMap = view.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
            for (OrderInfoPageResp resp:transform.getList()){
                resp.setCourseViewDTO(courseViewMap.get(resp.getCourseId()));
                //手机号脱敏
                resp.setMobile(DesensitizedUtil.mobilePhone(resp.getMobile()));
            }
        }


        return transform;
    }

    @Override
    public String edit(OrderInfoEditDTO editVo) {
        boolean b = this.updateById(BeanUtil.copyProperties(editVo, OrderInfo.class));
        if (b==true){
            return "操作成功";
        }
        throw new BaseException("操作失败");
    }
}
