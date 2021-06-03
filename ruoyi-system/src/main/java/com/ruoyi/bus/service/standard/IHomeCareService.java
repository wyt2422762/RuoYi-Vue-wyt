package com.ruoyi.bus.service.standard;

import java.util.List;
import com.ruoyi.bus.domain.standard.HomeCare;

/**
 * 收费标准-居家陪护Service接口
 * 
 * @author wyt
 * @date 2021-06-02
 */
public interface IHomeCareService
{
    /**
     * 查询收费标准-居家陪护
     *
     * @param id 收费标准-居家陪护ID
     * @return 收费标准-居家陪护
     */
    HomeCare selectHomeCareById(Long id);


    /**
     * 查询收费标准-居家陪护
     * 
     * @param no 收费标准-居家陪护ID
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
     * 批量删除收费标准-居家陪护
     * 
     * @param ids 需要删除的收费标准-居家陪护ID
     * @return 结果
     */
    int deleteHomeCareByIds(Long[] ids);

    /**
     * 删除收费标准-居家陪护信息
     * 
     * @param id 收费标准-居家陪护ID
     * @return 结果
     */
    int deleteHomeCareById(Long id);

    /**
     * 校验编码是否唯一
     *
     * @param homeCare 居家陪护信息
     * @return 结果
     */
    String checkNoUnique(HomeCare homeCare);
}
