package com.ruoyi.wx.model;

import lombok.Data;

/**
 * 微信用户类
 * @author wyt
 */
@Data
public class WxUserInfo {
    private String nickName;
    private String avatarUrl;
    private String country;
    private String province;
    private String city;
    private String language;
    private Byte gender;
}
