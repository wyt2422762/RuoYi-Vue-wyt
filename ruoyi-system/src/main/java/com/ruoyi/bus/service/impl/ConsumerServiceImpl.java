package com.ruoyi.bus.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.mapper.ConsumerMapper;
import com.ruoyi.bus.service.IConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户业务处理层
 * @author wyt
 */
@Service
public class ConsumerServiceImpl implements IConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 根据条件分页查询客户列表
     *
     * @param consumer 客户信息
     * @return 客户信息集合
     */
    @Override
    public List<Consumer> selectConsumerList(Consumer consumer) {
        return consumerMapper.selectConsumerList(consumer);
    }

    /**
     * 通过客户ID查询客户
     *
     * @param consumerId 客户ID
     * @return 客户对象信息
     */
    @Override
    public Consumer selectConsumerById(Long consumerId) {
        return consumerMapper.selectConsumerById(consumerId);
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param consumer 客户信息
     * @return 结果
     */
    @Override
    public String checkPhoneUnique(Consumer consumer) {
        Long consumerId = StringUtils.isNull(consumer.getConsumerId()) ? -1L : consumer.getConsumerId();
        Consumer info = consumerMapper.checkPhoneUnique(consumer.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getConsumerId().longValue() != consumerId.longValue())
        {
            return "1";
        }
        return "0";
    }

    /**
     * 校验email是否唯一
     *
     * @param consumer 客户信息
     * @return 结果
     */
    @Override
    public String checkEmailUnique(Consumer consumer) {
        Long userId = StringUtils.isNull(consumer.getConsumerId()) ? -1L : consumer.getConsumerId();
        Consumer info = consumerMapper.checkEmailUnique(consumer.getEmail());
        if (StringUtils.isNotNull(info) && info.getConsumerId().longValue() != userId.longValue())
        {
            return "1";
        }
        return "0";
    }

    /**
     * 校验客户是否允许操作
     *
     * @param consumer 客户信息
     */
    @Override
    public void checkConsumerAllowed(Consumer consumer) {
    }

    /**
     * 新增客户信息
     *
     * @param consumer 客户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertConsumer(Consumer consumer) {
        return consumerMapper.insertConsumer(consumer);
    }

    /**
     * 修改客户信息
     *
     * @param consumer 客户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateConsumer(Consumer consumer) {
        return consumerMapper.updateConsumer(consumer);
    }

    /**
     * 修改客户状态
     *
     * @param consumer 客户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateConsumerStatus(Consumer consumer) {
        return consumerMapper.updateConsumerStatus(consumer);
    }

    /**
     * 修改客户头像
     *
     * @param consumerId 客户id
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateConsumerAvatar(Long consumerId, String avatar) {
        return consumerMapper.updateConsumerAvatar(consumerId, avatar) > 0;
    }

    /**
     * 通过客户ID删除客户
     *
     * @param consumerId 客户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteConsumerById(Long consumerId) {
        return consumerMapper.deleteConsumerById(consumerId);
    }

    /**
     * 批量删除客户信息
     *
     * @param consumerIds 需要删除的客户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteConsumerByIds(Long[] consumerIds) {
        return consumerMapper.deleteConsumerByIds(consumerIds);
    }

    /**
     * 导入客户数据
     *
     * @param consumerList 客户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作客户
     * @return 结果
     */
    @Override
    public String importConsumer(List<Consumer> consumerList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(consumerList) || consumerList.isEmpty())
        {
            throw new CustomException("导入客户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Consumer consumer1 : consumerList)
        {
            try
            {
                // 验证手机号是否唯一
                Consumer consumer = consumerMapper.checkPhoneUnique(consumer1.getPhonenumber());
                if (StringUtils.isNull(consumer))
                {
                    consumer1.setCreateBy(operName);
                    this.insertConsumer(consumer1);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、客户(").append(consumer1.getName()).append("|").append(consumer1.getPhonenumber()).append(")导入成功");
                }
                else if (isUpdateSupport)
                {
                    consumer1.setUpdateBy(operName);
                    this.updateConsumer(consumer1);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、客户(").append(consumer1.getName()).append("|").append(consumer1.getPhonenumber()).append(")更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、客户(").append(consumer1.getName()).append("|").append(consumer1.getPhonenumber()).append(")已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、客户(" + consumer1.getName() + "|" + consumer1.getPhonenumber() + ")导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 根据手机号查询客户(小程序使用)
     * @param phoneNumber 手机号
     * @return 结果
     */
    @Override
    public Consumer selectConsumerByPhoneNumber_mp(String phoneNumber) {
        return consumerMapper.selectConsumerByPhoneNumber_mp(phoneNumber);
    }
}
