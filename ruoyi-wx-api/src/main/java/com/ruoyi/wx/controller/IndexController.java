package com.ruoyi.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.wechat.mp.domain.LunBoTu;
import com.ruoyi.wechat.mp.service.IMpService;
import com.ruoyi.wx.core.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
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
