package com.ruoyi.framework.config;

import com.ruoyi.common.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订单编号生成配置
 * @author wyt
 */
@Configuration
public class OrderNoConfig {

    /**
     * 机房id
     */
    @Value("${orderNo.datacenterId}")
    private long datacenterId;

    /**
     * 机器id
     */
    @Value("${orderNo.machineId}")
    private long machineId;

    @Bean
    public OrderNoUtil orderNoUtil(){
        return new OrderNoUtil(datacenterId, machineId);
    }

}
