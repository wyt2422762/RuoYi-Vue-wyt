package com.ruoyi.bus.service;

import com.ruoyi.bus.domain.Consumer;

import java.util.List;

/**
 * @author wyt
 */
public interface IConsumerService {
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
     * 校验手机号码是否唯一
     *
     * @param consumer 客户信息
     * @return 结果
     */
    String checkPhoneUnique(Consumer consumer);

    /**
     * 校验email是否唯一
     *
     * @param consumer 客户信息
     * @return 结果
     */
    String checkEmailUnique(Consumer consumer);

    /**
     * 校验客户是否允许操作
     *
     * @param consumer 客户信息
     */
    void checkConsumerAllowed(Consumer consumer);

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
     * 修改客户状态
     *
     * @param consumer 客户信息
     * @return 结果
     */
    int updateConsumerStatus(Consumer consumer);

    /**
     * 修改客户头像
     *
     * @param consumerId 客户id
     * @param avatar 头像地址
     * @return 结果
     */
    boolean updateConsumerAvatar(Long consumerId, String avatar);
    
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
     * 导入客户数据
     *
     * @param consumerList 客户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作客户
     * @return 结果
     */
    String importConsumer(List<Consumer> consumerList, Boolean isUpdateSupport, String operName);
}
