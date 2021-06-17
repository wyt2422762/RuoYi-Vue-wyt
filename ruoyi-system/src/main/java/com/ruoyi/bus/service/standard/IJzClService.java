package com.ruoyi.bus.service.standard;

import com.ruoyi.bus.domain.standard.JzCl;

import java.util.List;

/**
 * 家政-卫生清洁-收费标准Service接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface IJzClService {
    /**
     * 查询家政-卫生清洁-收费标准
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 家政-卫生清洁-收费标准
     */
    JzCl selectJzClById(Long id);

    /**
     * 查询家政-卫生清洁-收费标准列表
     *
     * @param jzCl 家政-卫生清洁-收费标准
     * @return 家政-卫生清洁-收费标准集合
     */
    List<JzCl> selectJzClList(JzCl jzCl);

    /**
     * 新增家政-卫生清洁-收费标准
     *
     * @param jzCl 家政-卫生清洁-收费标准
     * @return 结果
     */
    int insertJzCl(JzCl jzCl);

    /**
     * 修改家政-卫生清洁-收费标准
     *
     * @param jzCl 家政-卫生清洁-收费标准
     * @return 结果
     */
    int updateJzCl(JzCl jzCl);

    /**
     * 批量删除家政-卫生清洁-收费标准
     *
     * @param ids 需要删除的家政-卫生清洁-收费标准ID
     * @return 结果
     */
    int deleteJzClByIds(Long[] ids);

    /**
     * 删除家政-卫生清洁-收费标准信息
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 结果
     */
    int deleteJzClById(Long id);

    /**
     * 校验编码是否唯一
     *
     * @param jzCl 卫生清洁信息
     * @return 结果
     */
    String checkNoUnique(JzCl jzCl);
}
