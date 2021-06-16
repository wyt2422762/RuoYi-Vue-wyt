package com.ruoyi.bus.mapper;

import java.util.List;

import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.domain.OrderMeta;

/**
 * 订单Mapper接口
 *
 * @author wyt
 * @date 2021-05-31
 */
public interface OrderMapper {
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
     * 删除订单
     *
     * @param orderno 订单ID
     * @return 结果
     */
    int deleteOrderById(Long orderno);

    /**
     * 批量删除订单
     *
     * @param ordernos 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderByIds(Long[] ordernos);

    /**
     * 查询订单列表(小程序使用)
     *
     * @param order 订单
     * @return 订单集合
     */
    List<Order> selectOrderList_mp(Order order);

    /**
     * 添加订单内容详情
     * @param orderMetas
     * @return 结果
     */
    int insertOrderMeta(List<OrderMeta> orderMetas);
}
