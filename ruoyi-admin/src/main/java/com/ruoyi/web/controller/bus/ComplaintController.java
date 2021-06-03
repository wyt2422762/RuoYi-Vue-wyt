package com.ruoyi.web.controller.bus;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bus.domain.Complaint;
import com.ruoyi.bus.service.IComplaintService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投诉Controller
 *
 * @author wyt
 * @date 2021-06-01
 */
@RestController
@RequestMapping("/bus/complaint")
public class ComplaintController extends BaseController {
    @Autowired
    private IComplaintService complaintService;

    /**
     * 查询投诉列表
     */
    @PreAuthorize("@ss.hasPermi('bus:complaint:list')")
    @GetMapping("/list")
    public TableDataInfo list(Complaint complaint) {
        startPage();
        List<Complaint> list = complaintService.selectComplaintList(complaint);
        return getDataTable(list);
    }

    /**
     * 导出投诉列表
     */
    @PreAuthorize("@ss.hasPermi('bus:complaint:export')")
    @Log(title = "投诉", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Complaint complaint) {
        List<Complaint> list = complaintService.selectComplaintList(complaint);
        ExcelUtil<Complaint> util = new ExcelUtil<>(Complaint.class);
        return util.exportExcel(list, "投诉数据");
    }

    /**
     * 获取投诉详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:complaint:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(complaintService.selectComplaintById(id));
    }
}
