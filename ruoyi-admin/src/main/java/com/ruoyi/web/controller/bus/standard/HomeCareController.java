package com.ruoyi.web.controller.bus.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.HomeCare;
import com.ruoyi.bus.service.standard.IHomeCareService;
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
 * 收费标准-居家陪护Controller
 * 
 * @author wyt
 * @date 2021-06-02
 */
@RestController
@RequestMapping("/bus/standard/homecare")
public class HomeCareController extends BaseController
{
    @Autowired
    private IHomeCareService homeCareService;

    /**
     * 查询收费标准-居家陪护列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:homecare:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomeCare homeCare)
    {
        startPage();
        List<HomeCare> list = homeCareService.selectHomeCareList(homeCare);
        return getDataTable(list);
    }

    /**
     * 导出收费标准-居家陪护列表
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:homecare:export')")
    @Log(title = "收费标准-居家陪护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HomeCare homeCare)
    {
        List<HomeCare> list = homeCareService.selectHomeCareList(homeCare);
        ExcelUtil<HomeCare> util = new ExcelUtil<>(HomeCare.class);
        return util.exportExcel(list, "收费标准-居家陪护数据");
    }

    /**
     * 获取收费标准-居家陪护详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:homecare:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(homeCareService.selectHomeCareById(id));
    }

    /**
     * 新增收费标准-居家陪护
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:homecare:add')")
    @Log(title = "收费标准-居家陪护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody HomeCare homeCare)
    {
        homeCare.buildNo();
        if (StringUtils.isNotEmpty(homeCare.getNo())
                && UserConstants.NOT_UNIQUE.equals(homeCareService.checkNoUnique(homeCare))
        ){
            return AjaxResult.error("新增居家陪护收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        homeCare.setCreateBy(SecurityUtils.getUsername());
        return toAjax(homeCareService.insertHomeCare(homeCare));
    }

    /**
     * 修改收费标准-居家陪护
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:homecare:edit')")
    @Log(title = "收费标准-居家陪护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody HomeCare homeCare)
    {
        homeCare.buildNo();
        if (StringUtils.isNotEmpty(homeCare.getNo()) && UserConstants.NOT_UNIQUE.equals(homeCareService.checkNoUnique(homeCare))){
            return AjaxResult.error("编辑居家陪护收费标准失败，该类型的收费标准已存在，同类型的收费标准只能存在一个");
        }
        homeCare.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(homeCareService.updateHomeCare(homeCare));
    }

    /**
     * 删除收费标准-居家陪护
     */
    @PreAuthorize("@ss.hasPermi('bus:standard:homecare:remove')")
    @Log(title = "收费标准-居家陪护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(homeCareService.deleteHomeCareByIds(ids));
    }
}
