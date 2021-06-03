package com.ruoyi.bus.service.impl;

import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.mapper.OrderMapper;
import com.ruoyi.bus.service.IOrderService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author wyt
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询订单
     *
     * @param orderno 订单ID
     * @return 订单
     */
    @Override
    public Order selectOrderById(Long orderno) {
        return orderMapper.selectOrderById(orderno);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order) {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        order.setCreateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(Order order) {
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    /**
     * 删除订单信息
     *
     * @param orderno 订单ID
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long orderno) {
        return orderMapper.deleteOrderById(orderno);
    }

    /**
     * 批量删除订单
     *
     * @param ordernos 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] ordernos) {
        return orderMapper.deleteOrderByIds(ordernos);
    }
}
