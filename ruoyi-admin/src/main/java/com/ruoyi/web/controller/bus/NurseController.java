package com.ruoyi.web.controller.bus;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.domain.NursePosition;
import com.ruoyi.bus.service.INursePositionService;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 护工Controller
 * 
 * @author ruoyi
 * @date 2021-05-29
 */
@RestController
@RequestMapping("/bus/nurse")
public class NurseController extends BaseController
{
    @Autowired
    private INurseService nurseService;
    @Autowired
    private INursePositionService nursePostionService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询护工列表
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:list')")
    @GetMapping("/list")
    public TableDataInfo list(Nurse nurse)
    {
        startPage();
        List<Nurse> list = nurseService.selectNurseList(nurse);
        return getDataTable(list);
    }

    /**
     * 查询护工列表(全部)
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(Nurse nurse)
    {
        List<Nurse> list = nurseService.selectNurseList(nurse);
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 导出护工列表
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:export')")
    @Log(title = "护工", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Nurse nurse)
    {
        List<Nurse> list = nurseService.selectNurseList(nurse);
        ExcelUtil<Nurse> util = new ExcelUtil<>(Nurse.class);
        return util.exportExcel(list, "护工数据");
    }

    @Log(title = "护工管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('bus:nurse:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Nurse> util = new ExcelUtil<>(Nurse.class);
        List<Nurse> nurseList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = nurseService.importNurse(nurseList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Nurse> util = new ExcelUtil<>(Nurse.class);
        return util.importTemplateExcel("护工数据");
    }

    /**
     * 获取护工详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:query')")
    @GetMapping(value = "/{nurseId}")
    public AjaxResult getInfo(@PathVariable("nurseId") Long nurseId)
    {
        return AjaxResult.success(nurseService.selectNurseById(nurseId));
    }

    /**
     * 新增护工
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:add')")
    @Log(title = "护工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Nurse nurse)
    {
        if (StringUtils.isNotEmpty(nurse.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(nurseService.checkPhoneUnique(nurse)))
        {
            return AjaxResult.error("新增护工'" + nurse.getPhonenumber() + "'失败，手机号码已存在");
        }
        nurse.setCreateBy(SecurityUtils.getUsername());
        return toAjax(nurseService.insertNurse(nurse));
    }

    /**
     * 修改护工
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:edit')")
    @Log(title = "护工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody Nurse nurse)
    {
        nurseService.checkNurseAllowed(nurse);
        if (StringUtils.isNotEmpty(nurse.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(nurseService.checkPhoneUnique(nurse)))
        {
            return AjaxResult.error("修改护工'" + nurse.getPhonenumber() + "'失败，手机号码已存在");
        }
        nurse.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(nurseService.updateNurse(nurse));
    }

    /**
     * 删除护工
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:remove')")
    @Log(title = "护工", businessType = BusinessType.DELETE)
	@DeleteMapping("/{nurseids}")
    public AjaxResult remove(@PathVariable Long[] nurseids)
    {
        return toAjax(nurseService.deleteNurseByIds(nurseids));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated(EditGroup.class) @RequestBody Nurse nurse)
    {
        nurseService.checkNurseAllowed(nurse);
        nurse.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(nurseService.updateNurseStatus(nurse));
    }

    /**
     * 上传头像
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:add') or @ss.hasPermi('bus:nurse:edit')")
    @PostMapping("/uploadAvatar")
    public AjaxResult add(@RequestParam(value="file", required = false) MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            String path = FileUploadUtils.upload(RuoYiConfig.getNurseAvatarPath(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", path);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 查询护工位置
     * @param nursePosition 护工位置
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:query')")
    @GetMapping("/listPosition")
    public AjaxResult listPostion(NursePosition nursePosition){
        List<NursePosition> nursePositions = nursePostionService.selectNursePositionList(nursePosition);
        return AjaxResult.success("查询成功", nursePositions);
    }

    /**
     * 清空护工位置
     * @param nurseId 护工id
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('bus:nurse:remove')")
    @DeleteMapping("/clearPosition/{nurseId}")
    public AjaxResult clearPosition(@PathVariable Long nurseId){
        nursePostionService.clearNursePosition(nurseId);
        return AjaxResult.success("清空成功");
    }

}
