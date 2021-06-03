package com.ruoyi.web.controller.bus.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.HospitalCare;
import com.ruoyi.bus.service.standard.IHospitalCareService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收费标准-医院陪护Controller
 * 
 * @author wyt
 * @date 2021-06-02
 */
@RestController
@RequestMapping("/bus/standard/hospitalcare")
public class HospitalCareController extends BaseController
{
    @Autowired
    private IHospitalCareService hospitalCareService;

    /**
     * 查询收费标准-医院陪护列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:hospitalcare:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalCare hospitalCare)
    {
        startPage();
        List<HospitalCare> list = hospitalCareService.selectHospitalCareList(hospitalCare);
        return getDataTable(list);
    }

    /**
     * 导出收费标准-医院陪护列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:hospitalcare:export')")
    @Log(title = "收费标准-医院陪护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HospitalCare hospitalCare)
    {
        List<HospitalCare> list = hospitalCareService.selectHospitalCareList(hospitalCare);
        ExcelUtil<HospitalCare> util = new ExcelUtil<>(HospitalCare.class);
        return util.exportExcel(list, "收费标准-医院陪护数据");
    }

    /**
     * 获取收费标准-医院陪护详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:hospitalcare:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalCareService.selectHospitalCareById(id));
    }

    /**
     * 新增收费标准-医院陪护
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:hospitalcare:add')")
    @Log(title = "收费标准-医院陪护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody HospitalCare hospitalCare)
    {
        hospitalCare.buildNo();
        if (StringUtils.isNotEmpty(hospitalCare.getNo()) && UserConstants.NOT_UNIQUE.equals(hospitalCareService.checkNoUnique(hospitalCare))){
            return AjaxResult.error("新增医院陪护收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        hospitalCare.setCreateBy(SecurityUtils.getUsername());
        return toAjax(hospitalCareService.insertHospitalCare(hospitalCare));
    }

    /**
     * 修改收费标准-医院陪护
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:hospitalcare:edit')")
    @Log(title = "收费标准-医院陪护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody HospitalCare hospitalCare)
    {
        hospitalCare.buildNo();
        if (StringUtils.isNotEmpty(hospitalCare.getNo()) && UserConstants.NOT_UNIQUE.equals(hospitalCareService.checkNoUnique(hospitalCare))){
            return AjaxResult.error("修改医院陪护收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        hospitalCare.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(hospitalCareService.updateHospitalCare(hospitalCare));
    }

    /**
     * 删除收费标准-医院陪护
     */
    @PreAuthorize("@ss.hasPermi('bus:care:remove')")
    @Log(title = "收费标准-医院陪护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospitalCareService.deleteHospitalCareByIds(ids));
    }
}
