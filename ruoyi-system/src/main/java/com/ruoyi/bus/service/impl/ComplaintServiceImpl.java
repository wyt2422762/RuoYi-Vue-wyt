package com.ruoyi.bus.service.impl;

import java.util.List;

import com.ruoyi.bus.domain.ComplaintAttach;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bus.mapper.ComplaintMapper;
import com.ruoyi.bus.domain.Complaint;
import com.ruoyi.bus.service.IComplaintService;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 添加投诉
     * @param complaint 投诉内容
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertConsumer(Complaint complaint){
        //添加投诉信息
        int res = complaintMapper.insertComplaint(complaint);
        //添加投诉附件
        List<ComplaintAttach> attach = complaint.getAttach();
        if(attach != null && !attach.isEmpty()){
            for (ComplaintAttach complaintAttach : attach) {
                complaintAttach.setComplaintId(complaint.getId());
            }
            complaintMapper.insertComplaintAttach(attach);
        }
        return res;
    }

    /**
     * 查询投诉列表(小程序使用)
     *
     * @param complaint 投诉
     * @return 投诉
     */
    @Override
    public List<Complaint> selectComplaintList_mp(Complaint complaint)
    {
        return complaintMapper.selectComplaintList_mp(complaint);
    }
}
