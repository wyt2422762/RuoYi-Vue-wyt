package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.service.IConsumerService;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.validation.group.EditGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private INurseService nurseService;

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

    /**
     * 更新个人信息
     * @param consumer 客户信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('consumer')")
    @PutMapping("updateConsumer")
    public AjaxResult updateConsumer(@Validated(EditGroup.class) @RequestBody Consumer consumer){
        consumerService.updateConsumer(consumer);
        return AjaxResult.success("修改");
    }

    /**
     * 获取护工个人信息
     * @param phoneNumber 手机号
     * @return 结果
     */
    @PreAuthorize("hasAuthority('nurse')")
    @GetMapping("getNurse")
    public AjaxResult getNurse(String phoneNumber){
        Nurse nurse = nurseService.selectNurseByPhoneNumber_mp(phoneNumber);
        return AjaxResult.success("查询成功", nurse);
    }

}
