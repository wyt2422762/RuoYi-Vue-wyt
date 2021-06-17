package com.ruoyi.bus.service.impl.standard;

import com.ruoyi.bus.domain.standard.JzClean;
import com.ruoyi.bus.mapper.standard.JzCleanMapper;
import com.ruoyi.bus.service.standard.IJzCleanService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 家政-长期保洁-收费标准Service业务层处理
 * 
 * @author wyt
 * @date 2021-06-16
 */
@Service
public class JzCleanServiceImpl implements IJzCleanService 
{
    @Autowired
    private JzCleanMapper jzCleanMapper;

    /**
     * 查询家政-长期保洁-收费标准
     * 
     * @param id 家政-长期保洁-收费标准ID
     * @return 家政-长期保洁-收费标准
     */
    @Override
    public JzClean selectJzCleanById(Long id)
    {
        return jzCleanMapper.selectJzCleanById(id);
    }

    /**
     * 查询家政-长期保洁-收费标准列表
     * 
     * @param jzClean 家政-长期保洁-收费标准
     * @return 家政-长期保洁-收费标准
     */
    @Override
    public List<JzClean> selectJzCleanList(JzClean jzClean)
    {
        return jzCleanMapper.selectJzCleanList(jzClean);
    }

    /**
     * 新增家政-长期保洁-收费标准
     * 
     * @param jzClean 家政-长期保洁-收费标准
     * @return 结果
     */
    @Override
    public int insertJzClean(JzClean jzClean)
    {
        jzClean.setCreateTime(DateUtils.getNowDate());
        return jzCleanMapper.insertJzClean(jzClean);
    }

    /**
     * 修改家政-长期保洁-收费标准
     * 
     * @param jzClean 家政-长期保洁-收费标准
     * @return 结果
     */
    @Override
    public int updateJzClean(JzClean jzClean)
    {
        jzClean.setUpdateTime(DateUtils.getNowDate());
        return jzCleanMapper.updateJzClean(jzClean);
    }

    /**
     * 批量删除家政-长期保洁-收费标准
     * 
     * @param ids 需要删除的家政-长期保洁-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzCleanByIds(Long[] ids)
    {
        return jzCleanMapper.deleteJzCleanByIds(ids);
    }

    /**
     * 删除家政-长期保洁-收费标准信息
     * 
     * @param id 家政-长期保洁-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzCleanById(Long id)
    {
        return jzCleanMapper.deleteJzCleanById(id);
    }

    /**
     * 校验编码是否唯一
     *
     * @param jzClean 长期保洁信息
     * @return 结果
     */
    @Override
    public String checkNoUnique(JzClean jzClean) {
        JzClean h = jzCleanMapper.selectByNo(jzClean.getNo());
        if (h != null) {
            //判断是否是自己
            if (h.getId().equals(jzClean.getId())) {
                return "0";
            }
            return "1";
        }
        return "0";
    }
}
