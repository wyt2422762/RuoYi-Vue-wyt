package com.ruoyi.bus.service;

import java.util.List;
import com.ruoyi.bus.domain.Nurse;

/**
 * 护工Service接口
 * 
 * @author ruoyi
 * @date 2021-05-29
 */
public interface INurseService
{
    /**
     * 查询护工
     * 
     * @param nurseId 护工ID
     * @return 护工
     */
    Nurse selectNurseById(Long nurseId);

    /**
     * 校验手机号码是否唯一
     *
     * @param nurse 客户信息
     * @return 结果
     */
    String checkPhoneUnique(Nurse nurse);
    
    /**
     * 查询护工列表
     * 
     * @param nurse 护工
     * @return 护工集合
     */
    List<Nurse> selectNurseList(Nurse nurse);

    /**
     * 新增护工
     * 
     * @param nurse 护工
     * @return 结果
     */
    int insertNurse(Nurse nurse);

    /**
     * 修改护工
     * 
     * @param nurse 护工
     * @return 结果
     */
    int updateNurse(Nurse nurse);

    /**
     * 批量删除护工
     * 
     * @param nurseids 需要删除的护工ID
     * @return 结果
     */
    int deleteNurseByIds(Long[] nurseids);

    /**
     * 删除护工信息
     * 
     * @param nurseid 护工ID
     * @return 结果
     */
    int deleteNurseById(Long nurseid);

    /**
     * 修改护工头像
     *
     * @param nurseId 护工id
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateNurseAvatar(Long nurseId, String avatar);

    /**
     * 校验护工是否允许操作
     *
     * @param nurse 护工信息
     */
    void checkNurseAllowed(Nurse nurse);

    /**
     * 修改护工状态
     *
     * @param nurse 护工信息
     * @return 结果
     */
    int updateNurseStatus(Nurse nurse);

    /**
     * 根据手机号查询护工(小程序使用)
     * @param phoneNumber 手机号
     * @return 结果
     */
    Nurse selectNurseByPhoneNumber_mp(String phoneNumber);

    /**
     * 导入护工数据
     *
     * @param nurseList 护工数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作人
     * @return 结果
     */
    String importNurse(List<Nurse> nurseList, Boolean isUpdateSupport, String operName);

    /**
     * 查询护工列表(小程序使用)
     * @param nurse
     * @return
     */
    List<Nurse> selectNurseList_mp(Nurse nurse);
}
