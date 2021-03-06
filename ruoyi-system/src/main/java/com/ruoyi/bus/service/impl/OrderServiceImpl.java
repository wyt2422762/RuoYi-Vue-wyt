package com.ruoyi.bus.service.impl;

import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.domain.OrderMeta;
import com.ruoyi.bus.mapper.OrderMapper;
import com.ruoyi.bus.service.IOrderService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public int insertOrder(Order order) {
        order.setCreateTime(DateUtils.getNowDate());
        //内容详情
        List<OrderMeta> orderMetas = order.getMeta();
        if(orderMetas != null && !orderMetas.isEmpty()){
            for (OrderMeta orderMeta : orderMetas) {
                orderMeta.setOrderNo(order.getOrderNo());
            }
            orderMapper.insertOrderMeta(orderMetas);
        }
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrderById(Long orderno) {
        return orderMapper.deleteOrderById(orderno);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int paySuccess(String orderNo, String statusOld, String statusNew) {
        int res = orderMapper.updateOrderStatus(orderNo, statusOld, statusNew);
        if(res > 0){
            orderMapper.updateOrderPayTime(Long.getLong(orderNo));
        }
        return res;
    }

    /**
     * 更新订单支付状态
     * @param orderNo 订单号
     * @param statusOld 旧状态
     * @param statusNew 新状态
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrderStatus(String orderNo, String statusOld, String statusNew) {
        return orderMapper.updateOrderStatus(orderNo, statusOld, statusNew);
    }

    /**
     * 批量删除订单
     *
     * @param ordernos 需要删除的订单ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrderByIds(Long[] ordernos) {
        return orderMapper.deleteOrderByIds(ordernos);
    }

    /**
     * 查询订单列表(小程序使用)
     *
     * @param order 订单
     * @return 订单集合
     */
    @Override
    public List<Order> selectOrderList_mp(Order order){
        return orderMapper.selectOrderList_mp(order);
    }

}
