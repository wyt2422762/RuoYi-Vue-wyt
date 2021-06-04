package com.ruoyi.web.controller.bus;

import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
