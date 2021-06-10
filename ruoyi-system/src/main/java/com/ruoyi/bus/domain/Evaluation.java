package com.ruoyi.bus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 评价表
 *
 * @author wyt
 */
public class Evaluation extends BaseEntity {

    @Excel(name = "评价编号", cellType = Excel.ColumnType.NUMERIC)
    @NotNull(message = "评价编号不能为空", groups = {EditGroup.class})
    private Long evaluationId;
    @Excel(name = "订单编号", cellType = Excel.ColumnType.NUMERIC)
    @NotNull(message = "订单编号不能为空")
    private Long orderNo;

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
     * 客户电话
     */
    private String consumerPhone;

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
     * 评分
     */
    @NotNull(message = "评分不能为空")
    private Long score;

    /**
     * 评价内容
     */
    private String text;

    /**
     * 评价日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 删除标志（0代表存在 2代表删除)
     */
    @Excel(name = "删除标志", readConverterExp = "删除标志（0代表存在 2代表删除)")
    private String delFlag;

    /**
     * 客户
     */
    private Consumer consumer;

    /**
     * 护工
     */
    private Nurse nurse;

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    @NotBlank(message = "订单编号不能为空")
    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    @NotBlank(message = "客户编号不能为空")
    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    @NotBlank(message = "护工编号不能为空")
    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    @NotBlank(message = "评分不能为空")
    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
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
}
