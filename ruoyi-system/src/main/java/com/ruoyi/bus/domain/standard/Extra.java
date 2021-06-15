package com.ruoyi.bus.domain.standard;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 额外服务对象
 *
 * @author wyt
 * @date 2021-06-15
 */
@Data
public class Extra extends BaseEntity {

    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 额外服务名称
     */
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 额外服务费用
     */
    @NotNull(message = "费用不能为空")
    private Long money;
}
