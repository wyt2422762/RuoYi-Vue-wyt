package com.ruoyi.bus.domain;


import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 投诉附件
 * @author Administrator
 */
@Data
public class ComplaintAttach extends BaseEntity {

    private Long complaintId;

    private String url;
}
