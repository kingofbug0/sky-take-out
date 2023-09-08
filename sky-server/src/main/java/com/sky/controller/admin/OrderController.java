package com.sky.controller.admin;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/admin/order")
@Api(tags = "查询订单相关接口")
@Slf4j
public class OrderController
{
    @Autowired
    private OrderService orderService;
    @GetMapping("/conditionSearch")
    @ApiOperation("查看所有订单状态")
   public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO)
   {
       PageResult pageResult=orderService.conditionSearch(ordersPageQueryDTO);
       return Result.success(pageResult);
   }
   @GetMapping("/statistics")
   @ApiOperation("查询订单数量统计")
   public Result<OrderStatisticsVO> statistics()
   {
       OrderStatisticsVO orderStatisticsVO=orderService.statistics();
       return Result.success(orderStatisticsVO);
   }

    /**
     * 接受订单
     * @param ordersConfirmDTO
     * @return
     */
   @PutMapping("/confirm")
   @ApiOperation("接受订单")
   public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO)
   {
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
   }
   @PutMapping("/rejection")
   @ApiOperation("取消订单")
   public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO)
   {
       orderService.rejection(ordersRejectionDTO);
       return Result.success();
   }
    @PutMapping("/delivery/{id}")
    @ApiOperation("取消订单")
    public Result delivery(@PathVariable Long id)
    {
        orderService.delivery(id);
        return Result.success();
    }
}
