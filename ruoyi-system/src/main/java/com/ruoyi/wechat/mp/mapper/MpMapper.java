package com.ruoyi.wechat.mp.mapper;

import com.ruoyi.wechat.mp.domain.LunBoTu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信小程序轮播图 数据层
 *
 * @author ruoyi
 */
public interface MpMapper {

    /**
     * 获取轮播图
     *
     * @return 结果
     */
    List<LunBoTu> getLunBoTu();

    /**
     * 添加轮播图
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
    public int deleteLunBoTu(Long id);

    /**
     * 批量删除轮播图
     *
     * @param ids 轮播图ids
     * @return 结果
     */
    public int deleteLunBoTus(Long[]ids);

    /**
     * 显示、不显示轮播图
     *
     * @param lunBoTu     轮播图
     * @return 结果
     */
    public int updateLunBoTuStatus(LunBoTu lunBoTu);
}
