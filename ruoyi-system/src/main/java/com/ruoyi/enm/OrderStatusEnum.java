package com.ruoyi.enm;

/**
 * 订单状态枚举
 *
 * @author wyt
 */
public enum OrderStatusEnum {

    /**
     * 未派遣
     */
    WPQ("未派遣", "0"),
    /**
     * 未支付
     */
    WZF("未支付", "1"),
    /**
     * 已支付
     */
    YZF("已支付", "2"),
    /**
     * 服务中
     */
    FWZ("服务中", "3"),
    /**
     * 已完成
     */
    YWC("已完成", "4"),
    /**
     * 已取消
     */
    YQX("已取消", "5"),
    /**
     * 已退款
     */
    YTK("已退款", "6");

    private String name;
    private String value;

    OrderStatusEnum(String name, String value) {
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
