package com.ruoyi.bus.mapper.standard;

import com.ruoyi.bus.domain.standard.HomeCare;

import java.util.List;

/**
 * 收费标准-居家陪护Mapper接口
 * 
 * @author wyt
 * @date 2021-06-02
 */
public interface HomeCareMapper
{

    /**
     * 查询收费标准-居家陪护
     *
     * @param id 收费标准-居家陪护id
     * @return 收费标准-居家陪护
     */
    HomeCare selectHomeCareById(Long id);

    /**
     * 查询收费标准-居家陪护
     * 
     * @param no 收费标准-居家陪护编号
     * @return 收费标准-居家陪护
     */
    HomeCare selectHomeCareByNo(String no);

    /**
     * 查询收费标准-居家陪护列表
     * 
     * @param homeCare 收费标准-居家陪护
     * @return 收费标准-居家陪护集合
     */
    List<HomeCare> selectHomeCareList(HomeCare homeCare);

    /**
     * 新增收费标准-居家陪护
     * 
     * @param homeCare 收费标准-居家陪护
     * @return 结果
     */
    int insertHomeCare(HomeCare homeCare);

    /**
     * 修改收费标准-居家陪护
     * 
     * @param homeCare 收费标准-居家陪护
     * @return 结果
     */
    int updateHomeCare(HomeCare homeCare);

    /**
     * 删除收费标准-居家陪护
     * 
     * @param id 收费标准-居家陪护ID
     * @return 结果
     */
    int deleteHomeCareById(Long id);

    /**
     * 批量删除收费标准-居家陪护
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteHomeCareByIds(Long[] ids);
}
