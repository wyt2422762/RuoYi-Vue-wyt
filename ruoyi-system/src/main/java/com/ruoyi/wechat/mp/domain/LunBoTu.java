package com.ruoyi.wechat.mp.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信小程序首页轮播图domain
 * @author Administrator
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
