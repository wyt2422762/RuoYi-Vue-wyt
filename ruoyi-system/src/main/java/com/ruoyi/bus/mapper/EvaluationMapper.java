package com.ruoyi.bus.mapper;

import com.ruoyi.bus.domain.Evaluation;

import java.util.List;

/**
 * 评价Mapper接口
 *
 * @author wyt
 * @date 2021-05-31
 */
public interface EvaluationMapper {
    /**
     * 查询评价
     *
     * @param evaluationId 评价ID
     * @return 评价
     */
    Evaluation selectEvaluationById(Long evaluationId);

    /**
     * 查询订单评价
     * @param orderNo 订单编号
     * @return 结果
     */
    Evaluation selectEvaluationByOrderNo(Long orderNo);

    /**
     * 查询评价列表
     *
     * @param evaluation 评价
     * @return 评价集合
     */
    List<Evaluation> selectEvaluationList(Evaluation evaluation);

    /**
     * 新增评价
     *
     * @param evaluation 评价
     * @return 结果
     */
    int insertEvaluation(Evaluation evaluation);

    /**
     * 修改评价
     *
     * @param evaluation 评价
     * @return 结果
     */
    int updateEvaluation(Evaluation evaluation);

    /**
     * 删除评价
     *
     * @param evaluationId 评价ID
     * @return 结果
     */
    int deleteEvaluationById(Long evaluationId);

    /**
     * 批量删除评价
     *
     * @param evaluationIds 需要删除的数据ID
     * @return 结果
     */
    int deleteEvaluationByIds(Long[] evaluationIds);
}
