package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.service.IConsumerService;
import com.ruoyi.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人信息接口
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/userInfo")
public class UserInfoController {

    @Autowired
    private IConsumerService consumerService;

    /**
     * 获取客户个人信息
     * @param phoneNumber 手机号
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @GetMapping("getConsumer")
    public AjaxResult getConsumer(String phoneNumber){
        Consumer consumer = consumerService.selectConsumerByPhoneNumber_mp(phoneNumber);
        return AjaxResult.success("查询成功", consumer);
    }

}
