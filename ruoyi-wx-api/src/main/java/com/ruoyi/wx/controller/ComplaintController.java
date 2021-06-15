package com.ruoyi.wx.controller;


import com.ruoyi.bus.domain.Complaint;
import com.ruoyi.bus.service.IComplaintService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.validation.group.CreateGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 投诉
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/complaint")
public class ComplaintController extends BaseController {

    @Autowired
    private IComplaintService complaintService;

    /**
     * 查询投诉列表
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @GetMapping("/list")
    public TableDataInfo list(Complaint complaint)
    {
        startPage();
        List<Complaint> complaintList = complaintService.selectComplaintList_mp(complaint);
        return getDataTable(complaintList);
    }

    /**
     * 添加投诉
     */
    @PreAuthorize("hasAuthority('consumer')")
    @PostMapping("")
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Complaint complaint)
    {
        complaintService.insertConsumer(complaint);
        return AjaxResult.success("订单添加成功", complaint);
    }

}
