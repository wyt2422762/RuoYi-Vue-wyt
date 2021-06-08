package com.ruoyi.bus.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 客户表(老人)
 *
 * @author wyt
 */
public class Consumer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    @Excel(name = "客户编号", cellType = Excel.ColumnType.NUMERIC)
    @NotNull(message = "客户编号不能为空", groups = {EditGroup.class})
    private Long consumerId;

    /**
     * 客户姓名
     */
    @Excel(name = "姓名")
    @NotBlank(message = "客户编号不能为空")
    private String name;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phonenumber;

    /**
     * 性别
     */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 年龄
     */
    @Excel(name = "年龄", cellType = Excel.ColumnType.NUMERIC)
    private Integer age;

    /**
     * 出生年月
     */
    @Excel(name = "出生年月")
    private String birth;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String addr;

    /**
     * 紧急联系人姓名
     */
    @Excel(name = "紧急联系人姓名")
    @NotBlank(message = "紧急联系人姓名不能为空")
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    @Excel(name = "紧急联系人电话")
    @NotBlank(message = "紧急联系人电话不能为空")
    private String emergencyContactPhone;

    /**
     * 现病史
     */
    @Excel(name = "现病史")
    private String medicalHistoryNow;

    /**
     * 历史病史
     */
    @Excel(name = "历史病史")
    private String medicalHistoryHis;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idNo;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /* 头像 */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * openId
     */
    private String openId;

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getMedicalHistoryNow() {
        return medicalHistoryNow;
    }

    public void setMedicalHistoryNow(String medicalHistoryNow) {
        this.medicalHistoryNow = medicalHistoryNow;
    }

    public String getMedicalHistoryHis() {
        return medicalHistoryHis;
    }

    public void setMedicalHistoryHis(String medicalHistoryHis) {
        this.medicalHistoryHis = medicalHistoryHis;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
