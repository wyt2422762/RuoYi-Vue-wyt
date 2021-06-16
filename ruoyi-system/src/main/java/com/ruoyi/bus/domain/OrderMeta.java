package com.ruoyi.bus.domain;

import lombok.Data;

/**
 * 订单详情
 * @author wyt
 */
@Data
public class OrderMeta {

    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 数据项名称
     */
    private String label;

    /**
     * 数据想内容
     */
    private String data;

}
