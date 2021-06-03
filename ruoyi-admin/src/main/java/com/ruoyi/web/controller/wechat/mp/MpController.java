package com.ruoyi.web.controller.wechat.mp;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.wechat.mp.domain.LunBoTu;
import com.ruoyi.wechat.mp.service.IMpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 微信小程序管理
 *
 * @author wyt
 */
@RestController
@RequestMapping("/wechat/mp")
public class MpController extends BaseController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private IMpService mpService;

    /**
     * 轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('wechat:mp:lunbotu:list')")
    @GetMapping("/lunbotu/list")
    public TableDataInfo lunBoTu() {
        startPage();
        List<LunBoTu> lunBoTus = mpService.getLunBoTu();
        return getDataTable(lunBoTus);
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('wechat:mp:lunbotu:add')")
    @Log(title = "微信小程序轮播图管理", businessType = BusinessType.INSERT)
    @PostMapping("/lunbotu")
    public AjaxResult add(@RequestParam(value="file", required = false) MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String path = FileUploadUtils.upload(RuoYiConfig.getWechatMpLunBoTuPath(), file);
            LunBoTu lunBoTu = new LunBoTu();
            lunBoTu.setCreateBy(loginUser.getUsername());
            lunBoTu.setUrl(path);
            mpService.insertLunBoTu(lunBoTu);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", path);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 修改轮播图是否显示
     */
    @PreAuthorize("@ss.hasPermi('wechat:mp:lunbotu:edit')")
    @Log(title = "微信小程序轮播图管理", businessType = BusinessType.UPDATE)
    @PutMapping("/lunbotu")
    public AjaxResult edit(@RequestBody LunBoTu lunBoTu)
    {
        lunBoTu.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(mpService.updateLunBoTuStatus(lunBoTu));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('wechat:mp:lunbotu:remove')")
    @Log(title = "微信小程序轮播图管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/lunbotu/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mpService.deleteLunBoTus(ids));
    }

}
