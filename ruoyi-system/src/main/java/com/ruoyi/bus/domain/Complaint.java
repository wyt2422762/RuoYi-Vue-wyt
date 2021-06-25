package com.ruoyi.bus.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 投诉对象 complaint
 *
 * @author wyt
 * @date 2021-06-01
 */
@Data
public class Complaint extends BaseEntity {
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
    @NotBlank(message = "投诉内容不能为空")
    private String content;

    /**
     * 附件
     */
    private List<ComplaintAttach> attach;

    /**
     * 客户信息
     */
    private Consumer consumer;
}
