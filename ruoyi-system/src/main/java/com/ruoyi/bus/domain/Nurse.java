package com.ruoyi.bus.domain;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 护工对象 sys_nurse
 *
 * @author ruoyi
 * @date 2021-05-29
 */
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
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
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
    @Excel(name = "服务星级")
    private String workLevel;

    /**
     * 服务状态
     */
    @Excel(name = "服务状态")
    private String workStatus;

    //标签
    private List<String> labels;
    //能力
    private List<String> abilities;

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
    @Excel(name = "删除标志", readConverterExp = "删除标志（0代表存在 2代表删除)")
    private String delFlag;

    private SysDept dept;

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getAge() {
        return age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setWorkLevel(String workLevel) {
        this.workLevel = workLevel;
    }

    public String getWorkLevel() {
        return workLevel;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public List<String> getLabels() {
        return this.labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getAbilities() {
        return this.abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("nurseid", getNurseId())
                .append("deptid", getDeptId())
                .append("name", getName())
                .append("age", getAge())
                .append("sex", getSex())
                .append("phonenumber", getPhonenumber())
                .append("worklevel", getWorkLevel())
                .append("workstatus", getWorkStatus())
                .append("avatar", getAvatar())
                .append("status", getStatus())
                .append("delflag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
