package com.ruoyi.bus.domain;

import com.ruoyi.common.validation.group.EditGroup;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 投诉对象 complaint
 *
 * @author wyt
 * @date 2021-06-01
 */
public class Complaint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "投诉id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 订单编号
     */
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
     * 手机号
     */
    private String phonenumber;

    /**
     * 投诉内容
     */
    private String content;


    private List<ComplaintAttach> attach;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public List<ComplaintAttach> getAttach() {
        return attach;
    }

    public void setAttach(List<ComplaintAttach> attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderNo", getOrderNo())
                .append("consumerId", getConsumerId())
                .append("consumerName", getConsumerName())
                .append("phonenumber", getPhonenumber())
                .append("content", getContent())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("remark", getRemark())
                .toString();
    }
}
