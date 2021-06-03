package com.ruoyi.bus.service;

import com.ruoyi.bus.domain.Order;

import java.util.List;

/**
 * 订单Service接口
 * @author wyt
 */
public interface IOrderService {

    /**
     * 查询订单
     *
     * @param orderno 订单ID
     * @return 订单
     */
    public Order selectOrderById(Long orderno);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单
     *
     * @param ordernos 需要删除的订单ID
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ordernos);

    /**
     * 删除订单信息
     *
     * @param orderno 订单ID
     * @return 结果
     */
    public int deleteOrderById(Long orderno);

}
