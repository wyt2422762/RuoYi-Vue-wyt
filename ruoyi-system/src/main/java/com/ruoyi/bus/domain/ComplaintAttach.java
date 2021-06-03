package com.ruoyi.bus.domain;


import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投诉附件
 * @author Administrator
 */
public class ComplaintAttach extends BaseEntity {

    private Long complaintId;

    private String url;

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
