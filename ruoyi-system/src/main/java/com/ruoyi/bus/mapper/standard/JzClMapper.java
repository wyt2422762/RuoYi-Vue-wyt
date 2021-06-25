package com.ruoyi.bus.mapper.standard;

import com.ruoyi.bus.domain.standard.JzCl;

import java.util.List;

/**
 * 家政-窗帘清洗-收费标准Mapper接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface JzClMapper {
    /**
     * 查询家政-窗帘清洗-收费标准
     *
     * @param id 家政-窗帘清洗-收费标准ID
     * @return 家政-窗帘清洗-收费标准
     */
    JzCl selectJzClById(Long id);

    /**
     * 查询家政-窗帘清洗-收费标准列表
     *
     * @param jzCl 家政-窗帘清洗-收费标准
     * @return 家政-窗帘清洗-收费标准集合
     */
    List<JzCl> selectJzClList(JzCl jzCl);

    /**
     * 新增家政-窗帘清洗-收费标准
     *
     * @param jzCl 家政-窗帘清洗-收费标准
     * @return 结果
     */
    int insertJzCl(JzCl jzCl);

    /**
     * 修改家政-窗帘清洗-收费标准
     *
     * @param jzCl 家政-窗帘清洗-收费标准
     * @return 结果
     */
    int updateJzCl(JzCl jzCl);

    /**
     * 删除家政-窗帘清洗-收费标准
     *
     * @param id 家政-窗帘清洗-收费标准ID
     * @return 结果
     */
    int deleteJzClById(Long id);

    /**
     * 批量删除家政-窗帘清洗-收费标准
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJzClByIds(Long[] ids);

    /**
     * 查询收费标准
     *
     * @param no 收费标准-no
     * @return 结果
     */
    JzCl selectByNo(String no);
}
