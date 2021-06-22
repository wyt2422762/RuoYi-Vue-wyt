package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.Evaluation;
import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.service.IEvaluationService;
import com.ruoyi.bus.service.IOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.OperatorType;
import com.ruoyi.common.utils.OrderNoUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.enm.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人信息接口
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private OrderNoUtil orderNoUtil;
    @Autowired
    private IEvaluationService evaluationService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<Order> orderList = orderService.selectOrderList_mp(order);
        return getDataTable(orderList);
    }

    /**
     * 查询订单列表
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @GetMapping("/all")
    public TableDataInfo all(Order order)
    {
        List<Order> orderList = orderService.selectOrderList_mp(order);
        return getDataTable(orderList);
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @GetMapping(value = "/{orderNo}")
    public AjaxResult getInfo(@PathVariable("orderNo") Long orderNo)
    {
        return AjaxResult.success(orderService.selectOrderById(orderNo));
    }

    /**
     * 创建订单
     * @param order 订单信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @PostMapping(value = "")
    @Log(title="创建订单", operatorType = OperatorType.CONSUMER, businessType = BusinessType.INSERT)
    public AjaxResult createOrder(@Validated(CreateGroup.class) @RequestBody Order order){
        //1. 生成订单编号
        long orderNo = orderNoUtil.getNextId();
        order.setOrderNo(orderNo);
        //2. 甚至createBy(订单的创建者即为客户)
        order.setCreateBy(order.getConsumerId() + "");
        //3. 保存数据库
        orderService.insertOrder(order);
        //4. 返回结果
        return AjaxResult.success("订单添加成功", order);
    }

    //订单支付
    /**
     * 创建订单
     * @param orderNo 订单信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @PostMapping(value = "/paySuccess/{orderNo}")
    @Log(title="订单支付成功更改订单状态", operatorType = OperatorType.CONSUMER, businessType = BusinessType.UPDATE)
    public AjaxResult paySuccessOrder(@PathVariable Long orderNo){
        //1. 查询订单看状态是否==0
        Order order = orderService.selectOrderById(orderNo);
        //2. 修改订单状态为已支付
        order.setStatus("3");
        orderService.updateOrder(order);
        return AjaxResult.success("订单支付成功");
    }

    /**
     * 取消订单
     * @param orderNo 订单编号
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @PutMapping(value = "/cancel/{orderNo}")
    @Log(title="取消订单", operatorType = OperatorType.CONSUMER, businessType = BusinessType.UPDATE)
    public AjaxResult cancelOrder(@PathVariable Long orderNo){
        //1. 查询订单看状态是否==0
        Order order = orderService.selectOrderById(orderNo);
        if("0".equals(order.getStatus())){
            //修改订单状态为5 已取消
            order.setStatus("5");
            orderService.updateOrder(order);
            return AjaxResult.success("订单取消成功");
        } else {
            //订单状态不为0，不能取消
            return AjaxResult.error(5001001, "订单状态已改变，无法取消");
        }
    }

    /**
     * 订单评价
     * @param orderNo 订单编号
     * @param evaluation 评价
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @PostMapping(value = "/evaluation/{orderNo}")
    @Log(title="评价订单", operatorType = OperatorType.CONSUMER, businessType = BusinessType.UPDATE)
    public AjaxResult evaluationOrder(@PathVariable Long orderNo, @Validated(CreateGroup.class) @RequestBody Evaluation evaluation){
        //查询订单
        Order order = orderService.selectOrderById(orderNo);
        evaluation.setNurseId(order.getNurseId());
        evaluation.setConsumerId(order.getConsumerId());
        evaluationService.insertEvaluation(evaluation);
        return AjaxResult.success("订单评价成功");
    }


    /**
     * 完成订单
     * @param orderNo 订单编号
     * @return 结果
     */
    @PreAuthorize("hasAuthority('nurse')")
    @PutMapping(value = "/done/{orderNo}")
    @Log(title="取消订单", operatorType = OperatorType.NURSE, businessType = BusinessType.UPDATE)
    public AjaxResult doneOrder(@PathVariable Long orderNo){
        int res = orderService.updateOrderStatus(orderNo + "", OrderStatusEnum.FWZ, OrderStatusEnum.YWC);
        return AjaxResult.success(res);
    }
}
