package com.ruoyi.bus.mapper.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.JzWs;

/**
 * 家政-卫生清洁-收费标准Mapper接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface JzWsMapper {
    /**
     * 查询家政-卫生清洁-收费标准
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 家政-卫生清洁-收费标准
     */
    JzWs selectJzWsById(Long id);

    /**
     * 查询家政-卫生清洁-收费标准列表
     *
     * @param jzWs 家政-卫生清洁-收费标准
     * @return 家政-卫生清洁-收费标准集合
     */
    List<JzWs> selectJzWsList(JzWs jzWs);

    /**
     * 新增家政-卫生清洁-收费标准
     *
     * @param jzWs 家政-卫生清洁-收费标准
     * @return 结果
     */
    int insertJzWs(JzWs jzWs);

    /**
     * 修改家政-卫生清洁-收费标准
     *
     * @param jzWs 家政-卫生清洁-收费标准
     * @return 结果
     */
    int updateJzWs(JzWs jzWs);

    /**
     * 删除家政-卫生清洁-收费标准
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 结果
     */
    int deleteJzWsById(Long id);

    /**
     * 批量删除家政-卫生清洁-收费标准
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJzWsByIds(Long[] ids);

    /**
     * 查询收费标准
     *
     * @param no 收费标准-no
     * @return 结果
     */
    JzWs selectByNo(String no);
}
