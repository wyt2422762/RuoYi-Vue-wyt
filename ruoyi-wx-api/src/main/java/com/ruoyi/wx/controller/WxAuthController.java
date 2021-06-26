package com.ruoyi.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.service.IConsumerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.wx.core.config.WxConfig;
import com.ruoyi.wx.model.WxReqCommonParam;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 微信认证类
 *
 * @author wyt
 */
@RestController
@RequestMapping("/wx/{appId}/auth")
public class WxAuthController extends BaseController {

    @Autowired
    private IConsumerService consumerService;

    @Autowired
    private TokenService tokenService;

    /**
     * 微信获取openId
     *
     * @param appId appid
     * @param code  code
     * @return 结果
     */
    @GetMapping("getOpenId")
    public AjaxResult getOpenId(@PathVariable String appId, @Validated @NotBlank String code) {
        final WxMaService wxService = WxConfig.getMaService(appId);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            // 可以增加自己的逻辑，关联业务相关数据
            return AjaxResult.success(session);
        } catch (WxErrorException e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("获取openId失败");
        }
    }

    /**
     * 获取用户绑定手机号信息
     *
     * @param appId appId
     * @param wxRcp 微信基础参数
     * @param encryptedData encryptedData
     * @param iv iv
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @GetMapping("/phone")
    public AjaxResult phone(@PathVariable String appId, @Validated WxReqCommonParam wxRcp, @Validated @NotBlank String encryptedData, @Validated @NotBlank String iv) {
        final WxMaService wxService = WxConfig.getMaService(appId);
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(wxRcp.getSessionKey(), wxRcp.getRawData(), wxRcp.getSignature())) {
           throw new CustomException("user check failed");
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(wxRcp.getSessionKey(), encryptedData, iv);
        return AjaxResult.success(phoneNoInfo);
    }

    /**
     * 获取token
     * @param appId appId
     * @param openId openId
     * @param phoneNumber 手机号
     * @param type 0 客户 1 护工
     * @return token
     */
    @GetMapping("/getToken")
    public AjaxResult getToken(@PathVariable String appId, @Validated @NotBlank String openId, @Validated @NotBlank String phoneNumber, @Validated @NotBlank String type){
        //判断是客户还是护工
        switch (type){
            //客户
            case "0":
                Consumer consumer = consumerService.selectConsumerByPhoneNumber_mp(phoneNumber);
                if(consumer == null){
                    //这里需不需要注册新客户？
                } else {
                    if(!openId.equals(consumer.getOpenId())){
                        consumer.setOpenId(openId);
                        consumerService.updateConsumer(consumer);
                    }
                    //登陆
                    String token = tokenService.createToken(consumer);
                    return AjaxResult.success("成功", token);
                }
                break;
            //护工
            case "1":
                break;
            default:
        }

        return null;
    }

}
