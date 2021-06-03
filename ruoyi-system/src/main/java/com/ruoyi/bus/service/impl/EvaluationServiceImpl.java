package com.ruoyi.bus.service.impl;

import java.util.List;

import com.ruoyi.bus.domain.Evaluation;
import com.ruoyi.bus.mapper.EvaluationMapper;
import com.ruoyi.bus.service.IEvaluationService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评价Service业务层处理
 * 
 * @author wyt
 * @date 2021-05-31
 */
@Service
public class EvaluationServiceImpl implements IEvaluationService
{
    @Autowired
    private EvaluationMapper evaluationMapper;

    /**
     * 查询评价
     * 
     * @param evaluationId 评价ID
     * @return 评价
     */
    @Override
    public Evaluation selectEvaluationById(Long evaluationId)
    {
        return evaluationMapper.selectEvaluationById(evaluationId);
    }

    /**
     * 查询订单评价
     * @param orderNo 订单编号
     * @return 结果
     */
    @Override
    public Evaluation selectEvaluationByOrderNo(Long orderNo) {
        return evaluationMapper.selectEvaluationByOrderNo(orderNo);
    }

    /**
     * 查询评价列表
     * 
     * @param evaluation 评价
     * @return 评价
     */
    @Override
    public List<Evaluation> selectEvaluationList(Evaluation evaluation)
    {
        return evaluationMapper.selectEvaluationList(evaluation);
    }

    /**
     * 新增评价
     * 
     * @param evaluation 评价
     * @return 结果
     */
    @Override
    @Transactional
    public int insertEvaluation(Evaluation evaluation)
    {
        evaluation.setCreateTime(DateUtils.getNowDate());
        return evaluationMapper.insertEvaluation(evaluation);
    }

    /**
     * 修改评价
     * 
     * @param evaluation 评价
     * @return 结果
     */
    @Override
    @Transactional
    public int updateEvaluation(Evaluation evaluation)
    {
        evaluation.setUpdateTime(DateUtils.getNowDate());
        return evaluationMapper.updateEvaluation(evaluation);
    }

    /**
     * 批量删除评价
     * 
     * @param evaluationIds 需要删除的评价ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteEvaluationByIds(Long[] evaluationIds)
    {
        return evaluationMapper.deleteEvaluationByIds(evaluationIds);
    }

    /**
     * 删除评价信息
     * 
     * @param evaluationId 评价ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteEvaluationById(Long evaluationId)
    {
        return evaluationMapper.deleteEvaluationById(evaluationId);
    }
}
