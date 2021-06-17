package com.ruoyi.bus.service.standard;

import com.ruoyi.bus.domain.standard.JzClean;

import java.util.List;

/**
 * 家政-长期保洁-收费标准Service接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface IJzCleanService {
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
     * 批量删除家政-长期保洁-收费标准
     *
     * @param ids 需要删除的家政-长期保洁-收费标准ID
     * @return 结果
     */
    int deleteJzCleanByIds(Long[] ids);

    /**
     * 删除家政-长期保洁-收费标准信息
     *
     * @param id 家政-长期保洁-收费标准ID
     * @return 结果
     */
    int deleteJzCleanById(Long id);

    /**
     * 校验编码是否唯一
     *
     * @param jzClean 长期保洁信息
     * @return 结果
     */
    String checkNoUnique(JzClean jzClean);
}
