package com.ruoyi.bus.service.impl.standard;

import com.ruoyi.bus.domain.standard.JzWs;
import com.ruoyi.bus.mapper.standard.JzWsMapper;
import com.ruoyi.bus.service.standard.IJzWsService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 家政-卫生清洁-收费标准Service业务层处理
 * 
 * @author wyt
 * @date 2021-06-16
 */
@Service
public class JzWsServiceImpl implements IJzWsService 
{
    @Autowired
    private JzWsMapper jzWsMapper;

    /**
     * 查询家政-卫生清洁-收费标准
     * 
     * @param id 家政-卫生清洁-收费标准ID
     * @return 家政-卫生清洁-收费标准
     */
    @Override
    public JzWs selectJzWsById(Long id)
    {
        return jzWsMapper.selectJzWsById(id);
    }

    /**
     * 查询家政-卫生清洁-收费标准列表
     * 
     * @param jzWs 家政-卫生清洁-收费标准
     * @return 家政-卫生清洁-收费标准
     */
    @Override
    public List<JzWs> selectJzWsList(JzWs jzWs)
    {
        return jzWsMapper.selectJzWsList(jzWs);
    }

    /**
     * 新增家政-卫生清洁-收费标准
     * 
     * @param jzWs 家政-卫生清洁-收费标准
     * @return 结果
     */
    @Override
    public int insertJzWs(JzWs jzWs)
    {
        jzWs.setCreateTime(DateUtils.getNowDate());
        return jzWsMapper.insertJzWs(jzWs);
    }

    /**
     * 修改家政-卫生清洁-收费标准
     * 
     * @param jzWs 家政-卫生清洁-收费标准
     * @return 结果
     */
    @Override
    public int updateJzWs(JzWs jzWs)
    {
        jzWs.setUpdateTime(DateUtils.getNowDate());
        return jzWsMapper.updateJzWs(jzWs);
    }

    /**
     * 批量删除家政-卫生清洁-收费标准
     * 
     * @param ids 需要删除的家政-卫生清洁-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzWsByIds(Long[] ids)
    {
        return jzWsMapper.deleteJzWsByIds(ids);
    }

    /**
     * 删除家政-卫生清洁-收费标准信息
     * 
     * @param id 家政-卫生清洁-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzWsById(Long id)
    {
        return jzWsMapper.deleteJzWsById(id);
    }
}
