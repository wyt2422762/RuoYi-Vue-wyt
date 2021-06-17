package com.ruoyi.bus.domain.standard;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 家政-窗帘清洗-收费标准对象
 *
 * @author wyt
 * @date 2021-06-16
 */
@Data
public class JzCl extends BaseEntity {

    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 编号
     */
    //@NotBlank(message = "编号不能为空")
    private String no;

    /**
     * 居室面积(字典数据)
     */
    @NotBlank(message = "居室面积不能为空")
    private String size;

    /**
     * 厚度(字典数据)
     */
    @NotBlank(message = "厚度不能为空")
    private String thickness;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Long money;

    /**
     * 生成编号
     */
    public void buildNo() {
        this.no = this.size + "-" + this.thickness;
    }
}
