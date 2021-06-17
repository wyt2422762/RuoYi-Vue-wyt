package com.ruoyi.bus.mapper.standard;

import com.ruoyi.bus.domain.standard.JzCd;

import java.util.List;

/**
 * 家政-卫生清洁-收费标准Mapper接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface JzCdMapper {
    /**
     * 查询家政-卫生清洁-收费标准
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 家政-卫生清洁-收费标准
     */
    JzCd selectJzCdById(Long id);

    /**
     * 查询家政-卫生清洁-收费标准列表
     *
     * @param jzCd 家政-卫生清洁-收费标准
     * @return 家政-卫生清洁-收费标准集合
     */
    List<JzCd> selectJzCdList(JzCd jzCd);

    /**
     * 新增家政-卫生清洁-收费标准
     *
     * @param jzCd 家政-卫生清洁-收费标准
     * @return 结果
     */
    int insertJzCd(JzCd jzCd);

    /**
     * 修改家政-卫生清洁-收费标准
     *
     * @param jzCd 家政-卫生清洁-收费标准
     * @return 结果
     */
    int updateJzCd(JzCd jzCd);

    /**
     * 删除家政-卫生清洁-收费标准
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 结果
     */
    int deleteJzCdById(Long id);

    /**
     * 批量删除家政-卫生清洁-收费标准
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJzCdByIds(Long[] ids);

    /**
     * 查询收费标准
     *
     * @param no 收费标准-no
     * @return
     */
    JzCd selectByNo(String no);
}
