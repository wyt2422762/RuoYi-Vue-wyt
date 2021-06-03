package com.ruoyi.bus.domain.standard;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 居家陪护收费标准
 * 居家陪护按月计费
 *
 * @author wyt
 */
public class HomeCare extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 标准编号
     */
    @NotBlank(message = "标准编号不能为空")
    private String no;

    /**
     * 服务类型
     */
    @NotBlank(message = "服务类型不能为空")
    private String type;

    /**
     * 人数
     */
    @NotNull(message = "人数不能为空")
    private Integer num;

    /**
     * 每日陪护小时数
     */
    @NotNull(message = "每日陪护小时数不能为空")
    private Integer hourPerDay;

    /**
     * 押金
     */
    private Integer deposit;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Integer money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getHourPerDay() {
        return hourPerDay;
    }

    public void setHourPerDay(Integer hourPerDay) {
        this.hourPerDay = hourPerDay;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void buildNo() {
        this.no = this.type + "-" + this.num + "-" + this.hourPerDay;
    }

    /**
     * 计算订单金额(收费标准金额 * 月数 + 押金)
     *
     * @param months 月数
     * @return 总金额
     */
    public long calc(int months) {
        return (money == null ? 0 : money) * months + (deposit == null ? 0 : deposit);
    }
}
