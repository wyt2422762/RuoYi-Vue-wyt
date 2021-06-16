package com.ruoyi.bus.domain.standard;

import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 家政-卫生清洁-收费标准对象
 *
 * @author wyt
 * @date 2021-06-16
 */
@Data
public class JzWs extends BaseEntity {

    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 编号
     */
    @NotBlank(message = "编号不能为空")
    private String no;

    /**
     * 居室面积(字典数据)
     */
    @NotBlank(message = "居室面积不能为空")
    private String size;

    /**
     * 项目(字典数据)
     */
    @NotBlank(message = "项目不能为空")
    private String work;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Long money;


}
