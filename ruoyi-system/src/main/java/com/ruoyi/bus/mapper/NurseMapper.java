package com.ruoyi.bus.mapper;

import java.util.List;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import org.apache.ibatis.annotations.Param;

/**
 * 护工Mapper接口
 * 
 * @author ruoyi
 * @date 2021-05-29
 */
public interface NurseMapper
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
     * @param phoneNumber 手机号码
     * @return 结果
     */
    Consumer checkPhoneUnique(String phoneNumber);
    
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
     * 删除护工
     * 
     * @param nurseid 护工ID
     * @return 结果
     */
    int deleteNurseById(Long nurseid);

    /**
     * 批量删除护工
     * 
     * @param nurseids 需要删除的数据ID
     * @return 结果
     */
    int deleteNurseByIds(Long[] nurseids);

    /**
     * 修改护工头像
     *
     * @param nurseId 护工id
     * @param avatar     头像地址
     * @return 结果
     */
    int updateNurseAvatar(@Param("nurseId") Long nurseId, @Param("avatar") String avatar);

    /**
     * 修改护工状态
     * @param nurse 护工信息
     * @return 结果
     */
    int updateNurseStatus(Nurse nurse);

}
