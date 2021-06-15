package com.ruoyi.bus.mapper;

import java.util.List;

import com.ruoyi.bus.domain.Complaint;
import com.ruoyi.bus.domain.ComplaintAttach;

/**
 * 投诉Mapper接口
 *
 * @author wyt
 * @date 2021-06-01
 */
public interface ComplaintMapper {
    /**
     * 查询投诉
     *
     * @param id 投诉ID
     * @return 投诉
     */
    Complaint selectComplaintById(Long id);

    /**
     * 查询投诉列表
     *
     * @param complaint 投诉
     * @return 投诉集合
     */
    List<Complaint> selectComplaintList(Complaint complaint);

    /**
     * 添加投诉
     * @param complaint 投诉内容
     * @return 结果
     */
    int insertComplaint(Complaint complaint);

    /**
     * 添加投诉
     * @param complaintAttachs 投诉内容附件
     * @return 结果
     */
    int insertComplaintAttach(List<ComplaintAttach> complaintAttachs);

    /**
     * 查询投诉列表(小程序使用)
     *
     * @param complaint 投诉
     * @return 投诉集合
     */
    List<Complaint> selectComplaintList_mp(Complaint complaint);
}
