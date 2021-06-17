package com.ruoyi.web.controller.bus.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.JzWs;
import com.ruoyi.bus.service.standard.IJzWsService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 家政-卫生清洁-收费标准Controller
 * 
 * @author wyt
 * @date 2021-06-16
 */
@RestController
@RequestMapping("bus/standard/housekeeping/ws")
public class JzWsController extends BaseController
{
    @Autowired
    private IJzWsService jzWsService;

    /**
     * 查询家政-卫生清洁-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:ws:list')")
    @GetMapping("/list")
    public TableDataInfo list(JzWs jzWs)
    {
        startPage();
        List<JzWs> list = jzWsService.selectJzWsList(jzWs);
        return getDataTable(list);
    }

    /**
     * 导出家政-卫生清洁-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:ws:export')")
    @Log(title = "家政-卫生清洁-收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(JzWs jzWs)
    {
        List<JzWs> list = jzWsService.selectJzWsList(jzWs);
        ExcelUtil<JzWs> util = new ExcelUtil<>(JzWs.class);
        return util.exportExcel(list, "家政-卫生清洁-收费标准数据");
    }

    /**
     * 获取家政-卫生清洁-收费标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:ws:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(jzWsService.selectJzWsById(id));
    }

    /**
     * 新增家政-卫生清洁-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:ws:add')")
    @Log(title = "家政-卫生清洁-收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody JzWs jzWs)
    {
        jzWs.buildNo();
        if (StringUtils.isNotEmpty(jzWs.getNo()) && UserConstants.NOT_UNIQUE.equals(jzWsService.checkNoUnique(jzWs))){
            return AjaxResult.error("新增卫生清洁收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzWs.setCreateBy(SecurityUtils.getUsername());
        return toAjax(jzWsService.insertJzWs(jzWs));
    }

    /**
     * 修改家政-卫生清洁-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:ws:edit')")
    @Log(title = "家政-卫生清洁-收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody JzWs jzWs)
    {
        jzWs.buildNo();
        if (StringUtils.isNotEmpty(jzWs.getNo()) && UserConstants.NOT_UNIQUE.equals(jzWsService.checkNoUnique(jzWs))){
            return AjaxResult.error("修改卫生清洁收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzWs.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(jzWsService.updateJzWs(jzWs));
    }

    /**
     * 删除家政-卫生清洁-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:ws:remove')")
    @Log(title = "家政-卫生清洁-收费标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jzWsService.deleteJzWsByIds(ids));
    }
}
