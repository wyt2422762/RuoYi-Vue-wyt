package com.ruoyi.bus.domain.standard;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 医院陪护收费标准
 * 医院陪护按天计费
 *
 * @author wyt
 */
@Data
public class HospitalCare extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 标准编号
     */
    @NotBlank(message = "标准编号不能为空", groups = {EditGroup.class})
    private String no;

    /**
     * 服务类型
     */
    @NotBlank(message = "服务类型不能为空")
    private String type;

    /**
     * 每日陪护小时数
     */
    @NotNull(message = "每日陪护小时数不能为空")
    private Integer hourPerDay;

    /**
     * 每日餐费
     */
    @NotNull(message = "每日餐费不能为空")
    private Integer meal;

    /**
     * 押金
     */
    private Integer deposit;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Integer money;

    /**
     * 生成编号
     */
    public void buildNo() {
        this.no = this.type + "-" + this.hourPerDay;
    }

    /**
     * 计算订单金额((收费标准金额 + 餐费) * 天数 + 押金)
     *
     * @param days 天数
     * @return 总金额
     */
    public long calc(int days) {
        return (long) ((money == null ? 0 : money) + (meal == null ? 0 : meal)) * days + (deposit == null ? 0 : deposit);
    }
}
