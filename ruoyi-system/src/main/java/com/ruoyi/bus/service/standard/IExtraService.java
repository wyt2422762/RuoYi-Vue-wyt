package com.ruoyi.bus.service.standard;


import com.ruoyi.bus.domain.standard.Extra;

import java.util.List;

/**
 * 额外服务Service接口
 *
 * @author wyt
 * @date 2021-06-15
 */
public interface IExtraService {
    /**
     * 查询额外服务
     *
     * @param id 额外服务ID
     * @return 额外服务
     */
    Extra selectExtraById(Long id);

    /**
     * 查询额外服务列表
     *
     * @param extra 额外服务
     * @return 额外服务集合
     */
    List<Extra> selectExtraList(Extra extra);

    /**
     * 新增额外服务
     *
     * @param extra 额外服务
     * @return 结果
     */
    int insertExtra(Extra extra);

    /**
     * 修改额外服务
     *
     * @param extra 额外服务
     * @return 结果
     */
    int updateExtra(Extra extra);

    /**
     * 批量删除额外服务
     *
     * @param ids 需要删除的额外服务ID
     * @return 结果
     */
    int deleteExtraByIds(Long[] ids);

    /**
     * 删除额外服务信息
     *
     * @param id 额外服务ID
     * @return 结果
     */
    int deleteExtraById(Long id);
}
