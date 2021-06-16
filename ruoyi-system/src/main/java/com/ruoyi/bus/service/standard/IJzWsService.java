package com.ruoyi.bus.service.standard;

import com.ruoyi.bus.domain.standard.JzWs;

import java.util.List;

/**
 * 家政-卫生清洁-收费标准Service接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface IJzWsService {
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
     * 批量删除家政-卫生清洁-收费标准
     *
     * @param ids 需要删除的家政-卫生清洁-收费标准ID
     * @return 结果
     */
    int deleteJzWsByIds(Long[] ids);

    /**
     * 删除家政-卫生清洁-收费标准信息
     *
     * @param id 家政-卫生清洁-收费标准ID
     * @return 结果
     */
    int deleteJzWsById(Long id);
}
