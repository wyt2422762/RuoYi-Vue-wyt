package com.ruoyi.web.controller.bus.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.Extra;
import com.ruoyi.bus.service.standard.IExtraService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 额外服务Controller
 *
 * @author wyt
 * @date 2021-06-15
 */
@RestController
@RequestMapping("/bus/standard/extra")
public class ExtraController extends BaseController {
    @Autowired
    private IExtraService extraService;

    /**
     * 查询额外服务列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:extra:list')")
    @GetMapping("/list")
    public TableDataInfo list(Extra extra) {
        startPage();
        List<Extra> list = extraService.selectExtraList(extra);
        return getDataTable(list);
    }

    /**
     * 导出额外服务列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:extra:export')")
    @Log(title = "额外服务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Extra extra) {
        List<Extra> list = extraService.selectExtraList(extra);
        ExcelUtil<Extra> util = new ExcelUtil<>(Extra.class);
        return util.exportExcel(list, "额外服务数据");
    }

    /**
     * 获取额外服务详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:extra:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(extraService.selectExtraById(id));
    }

    /**
     * 新增额外服务
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:extra:add')")
    @Log(title = "额外服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Extra extra) {
        return toAjax(extraService.insertExtra(extra));
    }

    /**
     * 修改额外服务
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:extra:edit')")
    @Log(title = "额外服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Extra extra) {
        return toAjax(extraService.updateExtra(extra));
    }

    /**
     * 删除额外服务
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:extra:remove')")
    @Log(title = "额外服务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(extraService.deleteExtraByIds(ids));
    }
}
