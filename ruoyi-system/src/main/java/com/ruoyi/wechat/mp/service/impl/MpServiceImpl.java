package com.ruoyi.wechat.mp.service.impl;

import com.ruoyi.wechat.mp.domain.LunBoTu;
import com.ruoyi.wechat.mp.mapper.MpMapper;
import com.ruoyi.wechat.mp.service.IMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信小程序service
 *
 * @author wyt
 */
@Service
public class MpServiceImpl implements IMpService {

    @Autowired
    private MpMapper mpMapper;

    /**
     * 获取轮播图
     *
     * @return 结果
     */
    @Override
    public List<LunBoTu> getLunBoTu() {
        return mpMapper.getLunBoTu();
    }

    @Override
    public int insertLunBoTu(LunBoTu lunBoTu) {
        return mpMapper.insertLunBoTu(lunBoTu);
    }

    /**
     * 删除轮播图
     *
     * @param id 轮播图id
     * @return 结果
     */
    @Override
    public int deleteLunBoTu(Long id) {
        return mpMapper.deleteLunBoTu(id);
    }

    /**
     * 批量删除轮播图
     *
     * @param ids 轮播图ids
     * @return 结果
     */
    @Override
    public int deleteLunBoTus(Long[] ids) {
        return mpMapper.deleteLunBoTus(ids);
    }

    /**
     * 显示、不显示轮播图
     *
     * @param lunBoTu 轮播图
     * @return 结果
     */
    @Override
    public int updateLunBoTuStatus(LunBoTu lunBoTu) {
        return mpMapper.updateLunBoTuStatus(lunBoTu);
    }
}
