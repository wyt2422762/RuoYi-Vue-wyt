package com.ruoyi.bus.service.impl;

import com.ruoyi.bus.domain.NursePosition;
import com.ruoyi.bus.mapper.NursePositionMapper;
import com.ruoyi.bus.service.INursePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 护工位置Service业务层处理
 *
 * @author wyt
 * @date 2021-05-31
 */
@Service
public class NursePositionServiceImpl implements INursePositionService {

    @Autowired
    private NursePositionMapper nursePositionMapper;

    @Override
    public List<NursePosition> selectNursePositionList(NursePosition nursePosition) {
        return nursePositionMapper.selectNursePositionList(nursePosition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addNursePosition(NursePosition nursePosition) {
        return nursePositionMapper.addNursePosition(nursePosition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int clearNursePosition(Long nurseId) {
        return nursePositionMapper.clearNursePosition(nurseId);
    }


}
