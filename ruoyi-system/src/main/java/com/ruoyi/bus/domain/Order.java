package com.ruoyi.bus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 订单表
 *
 * @author Administrator
 */
@Data
public class Order extends BaseEntity {
    /**
     * 订单编号
     */
    @NotNull(message = "订单编号不能为空", groups = {EditGroup.class})
    private Long orderNo;

    /**
     * 订单类型
     */
    @NotBlank(message = "订单类型不能为空")
    private String orderType;

    /**
     * 服务时间
     */
    @NotBlank(message = "服务时间不能为空")
    private String workTime;

    /**
     * 服务级别
     */
    private String workLevel;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Long money;

    /**
     * 押金
     */
    private Long deposit;

    /**
     * 客户id
     */
    @NotNull(message = "客户id不能为空")
    private Long consumerId;

    /**
     * 客户姓名
     */
    private String consumerName;

    /**
     * 护工id
     */
    @NotNull(message = "护工id不能为空")
    private Long nurseId;

    /**
     * 护工姓名
     */
    private String nurseName;

    /**
     * 服务地址
     */
    @NotNull(message = "地址不能为空")
    private String addr;

    /**
     * 评价id
     */
    private Long evaluationId;

    /**
     * 客户
     */
    private Consumer consumer;

    /**
     * 护工
     */
    private Nurse nurse;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 评价
     */
    private Evaluation evaluation;

    /**
     * 删除标志（0代表存在 2代表删除)
     */
    private String delFlag;

    /**
     * 付款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 人数
     */
    private Long personNum;

    /**
     * 额外服务
     */
    private List<String> extra;
}
