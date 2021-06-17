package com.ruoyi.bus.service.standard;

import com.ruoyi.bus.domain.standard.JzCd;

import java.util.List;

/**
 * 家政-床单被罩清洗-收费标准Service接口
 *
 * @author wyt
 * @date 2021-06-16
 */
public interface IJzCdService {
    /**
     * 查询家政-床单被罩清洗-收费标准
     *
     * @param id 家政-床单被罩清洗-收费标准ID
     * @return 家政-床单被罩清洗-收费标准
     */
    JzCd selectJzCdById(Long id);

    /**
     * 查询家政-床单被罩清洗-收费标准列表
     *
     * @param jzCd 家政-床单被罩清洗-收费标准
     * @return 家政-床单被罩清洗-收费标准集合
     */
    List<JzCd> selectJzCdList(JzCd jzCd);

    /**
     * 新增家政-床单被罩清洗-收费标准
     *
     * @param jzCd 家政-床单被罩清洗-收费标准
     * @return 结果
     */
    int insertJzCd(JzCd jzCd);

    /**
     * 修改家政-床单被罩清洗-收费标准
     *
     * @param jzCd 家政-床单被罩清洗-收费标准
     * @return 结果
     */
    int updateJzCd(JzCd jzCd);

    /**
     * 批量删除家政-床单被罩清洗-收费标准
     *
     * @param ids 需要删除的家政-床单被罩清洗-收费标准ID
     * @return 结果
     */
    int deleteJzCdByIds(Long[] ids);

    /**
     * 删除家政-床单被罩清洗-收费标准信息
     *
     * @param id 家政-床单被罩清洗-收费标准ID
     * @return 结果
     */
    int deleteJzCdById(Long id);

    /**
     * 校验编码是否唯一
     *
     * @param jzCd 床单被罩清洗信息
     * @return 结果
     */
    String checkNoUnique(JzCd jzCd);
}
