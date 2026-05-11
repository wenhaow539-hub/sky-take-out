package com.sky.controller.user;

import com.sky.annotation.Anonymous;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = " 用户端订单相关接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;





    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<String> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return  Result.success();
    }

//    @PutMapping("/payment")
//    @ApiOperation("订单支付")
//    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
//        log.info("订单支付：{}", ordersPaymentDTO);
//        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
//        log.info("生成预支付交易单：{}", orderPaymentVO);
//        return Result.success(orderPaymentVO);
//    }

    /**
     * 查看历史订单
     * @param page
     * @param pageSize
     * @param status
     * @return
     */

    @GetMapping("/historyOrders")
    @ApiOperation("查看历史订单")
    @Anonymous
    public Result<PageResult> historyOrders(int page, int pageSize, Integer status) {
        PageResult pageResult = orderService.historyOrders(page,pageSize,status);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @Anonymous
    @ApiOperation("查看订单详情")
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> getDetailById(@PathVariable("id") Long id){
        return Result.success(orderService.getDetailById(id));
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @Anonymous
    @ApiOperation("取消订单")
    @PutMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable("id") Long id){
        orderService.cancelById(id);
        return Result.success();
    }


    /**
     * 再来一单
     * @param id
     * @return
     */
    @Anonymous
    @ApiOperation("再来一单")
    @PostMapping("/repetition/{id}")
    public Result repetition(@PathVariable("id") Long id){
        orderService.repetition(id);
        return Result.success();
    }


}
