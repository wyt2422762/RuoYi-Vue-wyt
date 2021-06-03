package com.ruoyi.wechat.mp.service;

import com.ruoyi.wechat.mp.domain.LunBoTu;

import java.util.List;

/**
 * 微信小程序service
 * @author wyt
 */
public interface IMpService {

    /**
     * 获取轮播图
     *
     * @return 结果
     */
    List<LunBoTu> getLunBoTu();

    /**
     * 删除轮播图
     *
     * @param lunBoTu 轮播图
     * @return 结果
     */
    int insertLunBoTu(LunBoTu lunBoTu);

    /**
     * 删除轮播图
     *
     * @param id 轮播图id
     * @return 结果
     */
    int deleteLunBoTu(Long id);

    /**
     * 批量删除轮播图
     *
     * @param ids 轮播图ids
     * @return 结果
     */
    public int deleteLunBoTus(Long[] ids);

    /**
     * 显示、不显示轮播图
     *
     * @param id     轮播图id
     * @param status 是否显示
     * @return 结果
     */
    int updateLunBoTuStatus(LunBoTu lunBoTu);
}
