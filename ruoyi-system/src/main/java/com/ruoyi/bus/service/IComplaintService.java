package com.ruoyi.bus.service;

import java.util.List;
import com.ruoyi.bus.domain.Complaint;

/**
 * 投诉Service接口
 * 
 * @author wyt
 * @date 2021-06-01
 */
public interface IComplaintService 
{
    /**
     * 查询投诉
     * 
     * @param id 投诉ID
     * @return 投诉
     */
    public Complaint selectComplaintById(Long id);

    /**
     * 查询投诉列表
     * 
     * @param complaint 投诉
     * @return 投诉集合
     */
    public List<Complaint> selectComplaintList(Complaint complaint);
}
