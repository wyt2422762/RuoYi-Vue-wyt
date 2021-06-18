package com.ruoyi.task;

import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.service.IOrderService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单状态task
 *
 * @author wyt
 */
@Component("orderStatusTask")
public class OrderStatusTask {

    @Autowired
    private IOrderService orderService;

    public void exec() {
        System.out.println("执行定时任务");
        /*List<Order> orders = orderService.selectOrderList(null);
        //获取当前时间
        String date = DateUtils.getDate();

        for (Order order : orders) {
            //1. 判断订单状态
            String status = order.getStatus();
            //2. 获取服务时间
            String workTime = order.getWorkTime();
            if ("2".equals(status)) {
                //已支付改成服务中，已完成
                if (workTime.contains(" - ")) {
                    String[] times = workTime.split(" - ");
                    if (date.compareTo(times[1]) > 0) {
                        //改成已完成
                        order.setStatus("4");
                    } else if (date.compareTo(times[0]) >= 0) {
                        //改成服务中
                        order.setStatus("3");
                    }
                } else {
                    if (date.compareTo(workTime) > 0) {
                        //改成已完成
                        order.setStatus("4");
                    } else if (date.compareTo(workTime) == 0) {
                        //改成进行中
                        order.setStatus("3");
                    }
                }
            } else if ("3".equals(status)) {
                //服务中改成已完成
                if (workTime.contains(" - ")) {
                    String[] times = workTime.split(" - ");
                    if (date.compareTo(times[1]) >= 0) {
                        //改成已完成
                        order.setStatus("4");
                    }
                } else {
                    if (date.compareTo(workTime) > 0) {
                        //改成已完成
                        order.setStatus("4");
                    }
                }
            }
        }*/
    }

}