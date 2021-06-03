package com.ruoyi.bus.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bus.mapper.ComplaintMapper;
import com.ruoyi.bus.domain.Complaint;
import com.ruoyi.bus.service.IComplaintService;

/**
 * 投诉Service业务层处理
 * 
 * @author wyt
 * @date 2021-06-01
 */
@Service
public class ComplaintServiceImpl implements IComplaintService 
{
    @Autowired
    private ComplaintMapper complaintMapper;

    /**
     * 查询投诉
     * 
     * @param id 投诉ID
     * @return 投诉
     */
    @Override
    public Complaint selectComplaintById(Long id)
    {
        return complaintMapper.selectComplaintById(id);
    }

    /**
     * 查询投诉列表
     * 
     * @param complaint 投诉
     * @return 投诉
     */
    @Override
    public List<Complaint> selectComplaintList(Complaint complaint)
    {
        return complaintMapper.selectComplaintList(complaint);
    }
}
