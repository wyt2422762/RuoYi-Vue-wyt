package com.ruoyi.wx.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 微信登录信息类
 *
 * @author wyt
 */
public class WxLoginInfo {
    @NotBlank(message = "code不能为空")
    private String code;
    @NotNull(message = "wxUserInfo不能为空")
    private WxUserInfo wxUserInfo;
    /**
     * 用来判断是护工还是客户（0 客户  1 护工）
     */
    @NotNull(message = "type不能为空")
    private Integer type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public WxUserInfo getUserInfo() {
        return wxUserInfo;
    }

    public void setUserInfo(WxUserInfo wxUserInfo) {
        this.wxUserInfo = wxUserInfo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
