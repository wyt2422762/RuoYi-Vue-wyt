package com.ruoyi.wx.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.wechat.mp.domain.LunBoTu;
import com.ruoyi.wechat.mp.service.IMpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页接口
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/index")
public class IndexController {

    @Autowired
    private IMpService mpService;

    /**
     * 获取首页轮播图
     *
     * @return 结果
     */
    @GetMapping("getLunBoTu")
    public AjaxResult getLunBoTu() {
        List<LunBoTu> lunBoTu = mpService.getLunBoTu_mp();
        return AjaxResult.success("查询成功", lunBoTu);
    }

}
