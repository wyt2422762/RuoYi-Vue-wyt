package com.ruoyi.bus.mapper;

import com.ruoyi.bus.domain.Consumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户 数据层
 *
 * @author ruoyi
 */
public interface ConsumerMapper {

    /**
     * 根据条件分页查询客户列表
     *
     * @param consumer 客户信息
     * @return 客户信息集合
     */
    List<Consumer> selectConsumerList(Consumer consumer);

    /**
     * 通过客户ID查询客户
     *
     * @param consumerId 客户ID
     * @return 客户对象信息
     */
    Consumer selectConsumerById(Long consumerId);

    /**
     * 新增客户信息
     *
     * @param consumer 客户信息
     * @return 结果
     */
    int insertConsumer(Consumer consumer);

    /**
     * 修改客户信息
     *
     * @param consumer 客户信息
     * @return 结果
     */
    int updateConsumer(Consumer consumer);

    /**
     * 通过客户ID删除客户
     *
     * @param consumerId 客户ID
     * @return 结果
     */
    int deleteConsumerById(Long consumerId);

    /**
     * 批量删除客户信息
     *
     * @param consumerIds 需要删除的客户ID
     * @return 结果
     */
    int deleteConsumerByIds(Long[] consumerIds);

    /**
     * 校验手机号码是否唯一
     *
     * @param phoneNumber 手机号码
     * @return 结果
     */
    Consumer checkPhoneUnique(String phoneNumber);

    /**
     * 校验email是否唯一
     *
     * @param email 客户邮箱
     * @return 结果
     */
    Consumer checkEmailUnique(String email);

    /**
     * 修改客户头像
     *
     * @param consumerId 客户id
     * @param avatar     头像地址
     * @return 结果
     */
    int updateConsumerAvatar(@Param("consumerId") Long consumerId, @Param("avatar") String avatar);

    /**
     * 修改客户状态
     * @param consumer 客户信息
     * @return 结果
     */
    int updateConsumerStatus(Consumer consumer);

}
