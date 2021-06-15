package com.ruoyi.bus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 评价表
 *
 * @author wyt
 */
@Data
public class Evaluation extends BaseEntity {

    @NotNull(message = "评价编号不能为空", groups = {EditGroup.class})
    private Long evaluationId;
    @NotNull(message = "订单编号不能为空")
    private Long orderNo;

    /**
     * 客户id
     */
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
    @NotBlank(message = "评分不能为空")
    private String text;

    /**
     * 评价日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 删除标志（0代表存在 2代表删除)
     */

    private String delFlag;

    /**
     * 客户
     */
    private Consumer consumer;

    /**
     * 护工
     */
    private Nurse nurse;
}
