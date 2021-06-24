package com.ruoyi.bus.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 护工对象
 *
 * @author ruoyi
 * @date 2021-05-29
 */
@Data
public class Nurse extends BaseEntity {
    /**
     * id
     */
    @NotNull(message = "护工id不能为空", groups = {EditGroup.class})
    private Long nurseId;

    /**
     * 部门id
     */
    @Excel(name = "部门id")
    private Long deptId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @NotBlank(message = "护工姓名不能为空")
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Long age;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别", dictType = "bus_workStatus")
    private String sex;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phonenumber;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idNo;

    /**
     * 服务星级
     */
    @Excel(name = "服务星级", dictType = "bus_workLevel")
    private String workLevel;

    /**
     * 服务状态
     */
    @Excel(name = "服务状态", dictType = "bus_workStatus")
    private String workStatus;

    /**
     * 标签
     */
    @Excel(name = "标签")
    private List<String> labels;

    /**
     * 能力
     */
    @Excel(name = "能力")
    private List<String> abilities;

    /**
     * 证书
     */
    @Excel(name = "证书")
    private List<String> certificates;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String avatar;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除
     */
    //@Excel(name = "删除标志", readConverterExp = "删除标志（0代表存在 2代表删除)")
    private String delFlag;

    private SysDept dept;

    /**
     * openId
     */
    private String openId;
}
