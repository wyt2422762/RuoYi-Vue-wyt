package com.ruoyi.bus.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 客户表(老人)
 *
 * @author wyt
 */
@Data
public class Consumer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    //@Excel(name = "客户编号", cellType = Excel.ColumnType.NUMERIC)
    @NotNull(message = "客户编号不能为空", groups = {EditGroup.class})
    private Long consumerId;

    /**
     * 客户姓名
     */
    @Excel(name = "姓名")
    @NotBlank(message = "姓名不能为空")
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
    @Excel(name = "性别", dictType = "sys_user_sex")
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
    //@NotBlank(message = "紧急联系人姓名不能为空")
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    @Excel(name = "紧急联系人电话")
    //@NotBlank(message = "紧急联系人电话不能为空")
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
     * 老人分类
     */
    @Excel(name = "老人分类", dictType = "bus_laoren_fenlei")
    private String type;

    /**
     * 老人服务类型
     */
    @Excel(name = "老人服务类型", dictType = "bus_laoren_type")
    private String serviceType;

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
}
