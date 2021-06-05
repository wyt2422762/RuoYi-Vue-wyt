package com.ruoyi.wx.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信请求基础类
 *
 * @author wyt
 */
@Data
public class WxReqCommonParam {

    @NotBlank(message = "appId不能为空")
    private String appId;
    @NotBlank(message = "sessionKey不能为空")
    private String sessionKey;
    @NotBlank(message = "signature不能为空")
    private String signature;
    @NotBlank(message = "rawData不能为空")
    private String rawData;

}
