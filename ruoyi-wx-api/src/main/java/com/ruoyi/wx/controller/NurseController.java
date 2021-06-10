package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.Evaluation;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.service.IEvaluationService;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 护工
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/nurse")
public class NurseController extends BaseController {

    @Autowired
    private INurseService nurseService;
    @Autowired
    private IEvaluationService evaluationService;

    /**
     * 查询护工列表
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @GetMapping("/list")
    public TableDataInfo list(Nurse nurse)
    {
        startPage();
        List<Nurse> orderList = nurseService.selectNurseList_mp(nurse);
        return getDataTable(orderList);
    }

    /**
     * 获取护工详细信息
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @GetMapping(value = "/{nurseId}")
    public AjaxResult getInfo(@PathVariable("nurseId") Long nurseId)
    {

        Nurse nurse = nurseService.selectNurseById(nurseId);

        startPage(1, 5, "create_time desc");

        Evaluation evaluation = new Evaluation();
        evaluation.setNurseId(nurse.getNurseId());
        List<Evaluation> evaluations = evaluationService.selectEvaluationList(evaluation);

        Map<String, Object> res = new HashMap<>(2);
        res.put("nurse", nurse);
        res.put("evaluations", evaluations);

        return AjaxResult.success("查询成功", res);
    }


}
