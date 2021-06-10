package com.ruoyi.web.controller.bus;

import java.util.List;

import com.ruoyi.bus.domain.Evaluation;
import com.ruoyi.bus.service.IEvaluationService;
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
 * 评价Controller
 * 
 * @author wyt
 * @date 2021-05-31
 */
@RestController
@RequestMapping("/bus/evaluation")
public class EvaluationController extends BaseController
{
    @Autowired
    private IEvaluationService evaluationService;

    /**
     * 查询评价列表
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(Evaluation evaluation)
    {
        startPage();
        List<Evaluation> list = evaluationService.selectEvaluationList(evaluation);
        return getDataTable(list);
    }

    /**
     * 导出评价列表
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:export')")
    @Log(title = "评价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Evaluation evaluation)
    {
        List<Evaluation> list = evaluationService.selectEvaluationList(evaluation);
        ExcelUtil<Evaluation> util = new ExcelUtil<>(Evaluation.class);
        return util.exportExcel(list, "评价数据");
    }

    /**
     * 获取评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:query')")
    @GetMapping(value = "/{evaluationid}")
    public AjaxResult getInfo(@PathVariable("evaluationid") Long evaluationid)
    {
        return AjaxResult.success(evaluationService.selectEvaluationById(evaluationid));
    }

    /**
     * 获取某个订单的评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:query')")
    @GetMapping(value = "getByOrderNo/{orderNo}")
    public AjaxResult getByOrderNo(@PathVariable("orderNo") Long orderNo)
    {
        return AjaxResult.success(evaluationService.selectEvaluationByOrderNo(orderNo));
    }

    /**
     * 新增评价
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:add')")
    @Log(title = "评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Evaluation evaluation)
    {
        return toAjax(evaluationService.insertEvaluation(evaluation));
    }

    /**
     * 修改评价
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:edit')")
    @Log(title = "评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody Evaluation evaluation)
    {
        return toAjax(evaluationService.updateEvaluation(evaluation));
    }

    /**
     * 删除评价
     */
    @PreAuthorize("@ss.hasPermi('bus:evaluation:remove')")
    @Log(title = "评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{evaluationids}")
    public AjaxResult remove(@PathVariable Long[] evaluationids)
    {
        return toAjax(evaluationService.deleteEvaluationByIds(evaluationids));
    }
}
