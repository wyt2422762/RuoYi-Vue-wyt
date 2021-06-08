package com.ruoyi.bus.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单表
 *
 * @author Administrator
 */
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
    @Excel(name = "删除标志", readConverterExp = "删除标志（0代表存在 2代表删除)")
    private String delFlag;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkLevel() {
        return workLevel;
    }

    public void setWorkLevel(String workLevel) {
        this.workLevel = workLevel;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
