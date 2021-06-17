package com.ruoyi.web.controller.bus.standard;

import com.ruoyi.bus.domain.standard.JzCl;
import com.ruoyi.bus.service.standard.IJzClService;
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
 * 家政-窗帘清洗-收费标准Controller
 * 
 * @author wyt
 * @date 2021-06-16
 */
@RestController
@RequestMapping("bus/standard/housekeeping/cl")
public class JzClController extends BaseController
{
    @Autowired
    private IJzClService jzClService;

    /**
     * 查询家政-窗帘清洗-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cl:list')")
    @GetMapping("/list")
    public TableDataInfo list(JzCl jzCl)
    {
        startPage();
        List<JzCl> list = jzClService.selectJzClList(jzCl);
        return getDataTable(list);
    }

    /**
     * 导出家政-窗帘清洗-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cl:export')")
    @Log(title = "家政-窗帘清洗-收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(JzCl jzCl)
    {
        List<JzCl> list = jzClService.selectJzClList(jzCl);
        ExcelUtil<JzCl> util = new ExcelUtil<>(JzCl.class);
        return util.exportExcel(list, "家政-窗帘清洗-收费标准数据");
    }

    /**
     * 获取家政-窗帘清洗-收费标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(jzClService.selectJzClById(id));
    }

    /**
     * 新增家政-窗帘清洗-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cl:add')")
    @Log(title = "家政-窗帘清洗-收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody JzCl jzCl)
    {
        jzCl.buildNo();
        if (StringUtils.isNotEmpty(jzCl.getNo()) && UserConstants.NOT_UNIQUE.equals(jzClService.checkNoUnique(jzCl))){
            return AjaxResult.error("新增窗帘清洗收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzCl.setCreateBy(SecurityUtils.getUsername());
        return toAjax(jzClService.insertJzCl(jzCl));
    }

    /**
     * 修改家政-窗帘清洗-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cl:edit')")
    @Log(title = "家政-窗帘清洗-收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody JzCl jzCl)
    {
        jzCl.buildNo();
        if (StringUtils.isNotEmpty(jzCl.getNo()) && UserConstants.NOT_UNIQUE.equals(jzClService.checkNoUnique(jzCl))){
            return AjaxResult.error("修改窗帘清洗收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzCl.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(jzClService.updateJzCl(jzCl));
    }

    /**
     * 删除家政-窗帘清洗-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cl:remove')")
    @Log(title = "家政-窗帘清洗-收费标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jzClService.deleteJzClByIds(ids));
    }
}
