package com.ruoyi.bus.service.impl;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.mapper.NurseMapper;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 护工Service业务层处理
 *
 * @author ruoyi
 * @date 2021-05-29
 */
@Service
public class NurseServiceImpl implements INurseService {
    @Autowired
    private NurseMapper nurseMapper;

    /**
     * 查询护工
     *
     * @param nurseid 护工ID
     * @return 护工
     */
    @Override
    public Nurse selectNurseById(Long nurseid) {
        return nurseMapper.selectNurseById(nurseid);
    }

    @Override
    public String checkPhoneUnique(Nurse nurse) {
        Long nurseId = StringUtils.isNull(nurse.getNurseId()) ? -1L : nurse.getNurseId();
        Nurse info = nurseMapper.checkPhoneUnique(nurse.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getNurseId().longValue() != nurseId.longValue()) {
            return "1";
        }
        return "0";
    }

    /**
     * 查询护工列表
     *
     * @param nurse 护工
     * @return 护工
     */
    @Override
    public List<Nurse> selectNurseList(Nurse nurse) {
        return nurseMapper.selectNurseList(nurse);
    }

    /**
     * 新增护工
     *
     * @param nurse 护工
     * @return 结果
     */
    @Override
    @Transactional
    public int insertNurse(Nurse nurse) {
        nurse.setCreateTime(DateUtils.getNowDate());
        return nurseMapper.insertNurse(nurse);
    }

    /**
     * 修改护工
     *
     * @param nurse 护工
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNurse(Nurse nurse) {
        nurse.setUpdateTime(DateUtils.getNowDate());
        return nurseMapper.updateNurse(nurse);
    }

    /**
     * 批量删除护工
     *
     * @param nurseids 需要删除的护工ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteNurseByIds(Long[] nurseids) {
        return nurseMapper.deleteNurseByIds(nurseids);
    }

    /**
     * 删除护工信息
     *
     * @param nurseid 护工ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteNurseById(Long nurseid) {
        return nurseMapper.deleteNurseById(nurseid);
    }

    /**
     * 修改护工头像
     *
     * @param nurseId 护工id
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    @Transactional
    public boolean updateNurseAvatar(Long nurseId, String avatar) {
        return nurseMapper.updateNurseAvatar(nurseId, avatar) > 0;
    }

    /**
     * 校验护工是否允许操作
     *
     * @param nurse 护工信息
     */
    @Override
    public void checkNurseAllowed(Nurse nurse) {
    }

    /**
     * 修改护工状态
     *
     * @param nurse 护工信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNurseStatus(Nurse nurse) {
        return nurseMapper.updateNurseStatus(nurse);
    }

    /**
     * 根据手机号查询护工(小程序使用)
     * @param phoneNumber 手机号
     * @return 结果
     */
    @Override
    public Nurse selectNurseByPhoneNumber_mp(String phoneNumber) {
        return nurseMapper.selectNurseByPhoneNumber_mp(phoneNumber);
    }

    /**
     * 查询护工列表(小程序使用)
     *
     * @param nurse 护工
     * @return 护工
     */
    @Override
    public List<Nurse> selectNurseList_mp(Nurse nurse) {
        return nurseMapper.selectNurseList_mp(nurse);
    }

}
