package com.ninep.user.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.user.admin.response.OrderInfoPageResp;
import com.ninep.user.admin.DTO.OrderInfoEditDTO;
import com.ninep.user.admin.DTO.OrderInfoPageDTO;
import com.ninep.user.service.IOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-订单信息表")
@RestController
@RequestMapping("/user/admin/order/info")
public class OrderInfoController {

    @Resource
    private IOrderInfoService orderInfoService;

    @ApiOperation(value = "订单信息表分页", notes = "订单信息表分页")
    @PostMapping(value = "/page")
    public Result<Page<OrderInfoPageResp>> page(@RequestBody OrderInfoPageDTO orderInfoPageDTO) {
        Page<OrderInfoPageResp> page=orderInfoService.pageForOrder(orderInfoPageDTO);
        return Result.success(page);
    }

    @ApiOperation(value = "订单信息表修改", notes = "订单信息表修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody OrderInfoEditDTO editVo) {
        String result=orderInfoService.edit(editVo);
        return Result.success(result);
    }
}
