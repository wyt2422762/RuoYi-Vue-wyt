package com.ruoyi.bus.service;

import com.ruoyi.bus.domain.Order;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author wyt
 */
public interface IOrderService {

    /**
     * 查询订单
     *
     * @param orderno 订单ID
     * @return 订单
     */
    Order selectOrderById(Long orderno);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    int updateOrder(Order order);

    /**
     * 批量删除订单
     *
     * @param ordernos 需要删除的订单ID
     * @return 结果
     */
    int deleteOrderByIds(Long[] ordernos);

    /**
     * 删除订单信息
     *
     * @param orderno 订单ID
     * @return 结果
     */
    int deleteOrderById(Long orderno);

    /**
     * 支付成功
     * @param orderno
     * @param statusOld
     * @param statusNew
     * @return
     */
    int paySuccess(String orderno, String statusOld, String statusNew);

    /**
     * 更新订单状态
     * @param orderNo 订单号
     * @param statusOld 旧状态
     * @param statusNew 新状态
     * @return 结果
     */
    int updateOrderStatus(String orderNo, String statusOld, String statusNew);

    /**
     * 查询订单列表(小程序使用)
     *
     * @param order 订单
     * @return 订单集合
     */
    List<Order> selectOrderList_mp(Order order);
}
