package com.ruoyi.bus.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 护工位置实体类
 *
 * @author wyt
 */
@Data
public class NursePosition extends BaseEntity {
    /**
     * 护工id
     */
    @NotNull(message = "护工id不能为空")
    private Long nurseId;

    /**
     * 经度
     */
    @NotBlank(message = "经度不能为空")
    private String lng;

    /**
     * 维度
     */
    @NotBlank(message = "纬度不能为空")
    private String lat;

    /**
     * 上报时间
     */
    private Date time;

    private Nurse nurse;
}
