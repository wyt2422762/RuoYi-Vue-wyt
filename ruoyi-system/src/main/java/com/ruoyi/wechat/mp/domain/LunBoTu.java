package com.ruoyi.wechat.mp.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 微信小程序首页轮播图domain
 * @author Administrator
 */
@Data
public class LunBoTu extends BaseEntity {

    private Long id;
    /**
     * 链接
     */
    private String url;
    /**
     * 是否显示 0=显示,1=不显示
     */
    private String status;
}
