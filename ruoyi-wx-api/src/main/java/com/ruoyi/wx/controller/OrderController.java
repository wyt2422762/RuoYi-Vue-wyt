package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.service.IOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.OrderNoUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
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


    //订单取消


    //订单xxx

}
