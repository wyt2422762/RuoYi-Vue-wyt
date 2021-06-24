package com.ruoyi.bus.service;

import com.ruoyi.bus.domain.NursePosition;

import java.util.List;

/**
 * 护工位置接口
 *
 * @author ruoyi
 * @date 2021-05-29
 */
public interface INursePositionService {

    /**
     * 查询护工位置列表
     *
     * @param nursePosition 护工位置
     * @return 结果
     */
    List<NursePosition> selectNursePositionList(NursePosition nursePosition);

    /**
     * 添加护工位置
     *
     * @param nursePosition 护工位置
     * @return 结果
     */
    int addNursePosition(NursePosition nursePosition);

    /**
     * 清空护工位置
     * @param nurseId 护工id
     * @return 结果
     */
    int clearNursePosition(Long nurseId);
}
