package com.ruoyi.bus.mapper.standard;

import com.ruoyi.bus.domain.standard.JzClean;

import java.util.List;

/**
 * 家政-长期保洁-收费标准Mapper接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface JzCleanMapper {
    /**
     * 查询家政-长期保洁-收费标准
     *
     * @param id 家政-长期保洁-收费标准ID
     * @return 家政-长期保洁-收费标准
     */
    JzClean selectJzCleanById(Long id);

    /**
     * 查询家政-长期保洁-收费标准列表
     *
     * @param jzClean 家政-长期保洁-收费标准
     * @return 家政-长期保洁-收费标准集合
     */
    List<JzClean> selectJzCleanList(JzClean jzClean);

    /**
     * 新增家政-长期保洁-收费标准
     *
     * @param jzClean 家政-长期保洁-收费标准
     * @return 结果
     */
    int insertJzClean(JzClean jzClean);

    /**
     * 修改家政-长期保洁-收费标准
     *
     * @param jzClean 家政-长期保洁-收费标准
     * @return 结果
     */
    int updateJzClean(JzClean jzClean);

    /**
     * 删除家政-长期保洁-收费标准
     *
     * @param id 家政-长期保洁-收费标准ID
     * @return 结果
     */
    int deleteJzCleanById(Long id);

    /**
     * 批量删除家政-长期保洁-收费标准
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJzCleanByIds(Long[] ids);

    /**
     * 查询收费标准
     *
     * @param no 收费标准-no
     * @return 结果
     */
    JzClean selectByNo(String no);
}
