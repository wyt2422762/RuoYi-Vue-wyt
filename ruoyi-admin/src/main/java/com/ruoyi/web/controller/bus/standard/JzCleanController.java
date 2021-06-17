package com.ruoyi.web.controller.bus.standard;

import com.ruoyi.bus.domain.standard.JzClean;
import com.ruoyi.bus.service.standard.IJzCleanService;
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
 * 家政-长期保洁-收费标准Controller
 * 
 * @author wyt
 * @date 2021-06-16
 */
@RestController
@RequestMapping("bus/standard/housekeeping/clean")
public class JzCleanController extends BaseController
{
    @Autowired
    private IJzCleanService jzCleanService;

    /**
     * 查询家政-长期保洁-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:clean:list')")
    @GetMapping("/list")
    public TableDataInfo list(JzClean jzClean)
    {
        startPage();
        List<JzClean> list = jzCleanService.selectJzCleanList(jzClean);
        return getDataTable(list);
    }

    /**
     * 导出家政-长期保洁-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:clean:export')")
    @Log(title = "家政-长期保洁-收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(JzClean jzClean)
    {
        List<JzClean> list = jzCleanService.selectJzCleanList(jzClean);
        ExcelUtil<JzClean> util = new ExcelUtil<>(JzClean.class);
        return util.exportExcel(list, "家政-长期保洁-收费标准数据");
    }

    /**
     * 获取家政-长期保洁-收费标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:clean:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(jzCleanService.selectJzCleanById(id));
    }

    /**
     * 新增家政-长期保洁-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:clean:add')")
    @Log(title = "家政-长期保洁-收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody JzClean jzClean)
    {
        jzClean.buildNo();
        if (StringUtils.isNotEmpty(jzClean.getNo()) && UserConstants.NOT_UNIQUE.equals(jzCleanService.checkNoUnique(jzClean))){
            return AjaxResult.error("新增长期保洁收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzClean.setCreateBy(SecurityUtils.getUsername());
        return toAjax(jzCleanService.insertJzClean(jzClean));
    }

    /**
     * 修改家政-长期保洁-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:clean:edit')")
    @Log(title = "家政-长期保洁-收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody JzClean jzClean)
    {
        jzClean.buildNo();
        if (StringUtils.isNotEmpty(jzClean.getNo()) && UserConstants.NOT_UNIQUE.equals(jzCleanService.checkNoUnique(jzClean))){
            return AjaxResult.error("修改长期保洁收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzClean.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(jzCleanService.updateJzClean(jzClean));
    }

    /**
     * 删除家政-长期保洁-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:clean:remove')")
    @Log(title = "家政-长期保洁-收费标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jzCleanService.deleteJzCleanByIds(ids));
    }
}
