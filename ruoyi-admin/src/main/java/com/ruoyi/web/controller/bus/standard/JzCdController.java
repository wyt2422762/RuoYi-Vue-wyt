package com.ruoyi.web.controller.bus.standard;

import com.ruoyi.bus.domain.standard.JzCd;
import com.ruoyi.bus.service.standard.IJzCdService;
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
 * 家政-床单被罩清洗-收费标准Controller
 * 
 * @author wyt
 * @date 2021-06-16
 */
@RestController
@RequestMapping("bus/standard/housekeeping/cd")
public class JzCdController extends BaseController
{
    @Autowired
    private IJzCdService jzCdService;

    /**
     * 查询家政-床单被罩清洗-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cd:list')")
    @GetMapping("/list")
    public TableDataInfo list(JzCd jzCd)
    {
        startPage();
        List<JzCd> list = jzCdService.selectJzCdList(jzCd);
        return getDataTable(list);
    }

    /**
     * 导出家政-床单被罩清洗-收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cd:export')")
    @Log(title = "家政-床单被罩清洗-收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(JzCd jzCd)
    {
        List<JzCd> list = jzCdService.selectJzCdList(jzCd);
        ExcelUtil<JzCd> util = new ExcelUtil<>(JzCd.class);
        return util.exportExcel(list, "家政-床单被罩清洗-收费标准数据");
    }

    /**
     * 获取家政-床单被罩清洗-收费标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(jzCdService.selectJzCdById(id));
    }

    /**
     * 新增家政-床单被罩清洗-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cd:add')")
    @Log(title = "家政-床单被罩清洗-收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody JzCd jzCd)
    {
        jzCd.buildNo();
        if (StringUtils.isNotEmpty(jzCd.getNo()) && UserConstants.NOT_UNIQUE.equals(jzCdService.checkNoUnique(jzCd))){
            return AjaxResult.error("新增床单被罩清洗收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzCd.setCreateBy(SecurityUtils.getUsername());
        return toAjax(jzCdService.insertJzCd(jzCd));
    }

    /**
     * 修改家政-床单被罩清洗-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cd:edit')")
    @Log(title = "家政-床单被罩清洗-收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody JzCd jzCd)
    {
        jzCd.buildNo();
        if (StringUtils.isNotEmpty(jzCd.getNo()) && UserConstants.NOT_UNIQUE.equals(jzCdService.checkNoUnique(jzCd))){
            return AjaxResult.error("修改床单被罩清洗收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        jzCd.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(jzCdService.updateJzCd(jzCd));
    }

    /**
     * 删除家政-床单被罩清洗-收费标准
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:housekeeping:cd:remove')")
    @Log(title = "家政-床单被罩清洗-收费标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jzCdService.deleteJzCdByIds(ids));
    }
}
