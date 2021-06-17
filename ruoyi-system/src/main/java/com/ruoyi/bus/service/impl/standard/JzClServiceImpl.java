package com.ruoyi.bus.service.impl.standard;

import com.ruoyi.bus.domain.standard.JzCl;
import com.ruoyi.bus.mapper.standard.JzClMapper;
import com.ruoyi.bus.service.standard.IJzClService;
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
public class JzClServiceImpl implements IJzClService 
{
    @Autowired
    private JzClMapper jzClMapper;

    /**
     * 查询家政-卫生清洁-收费标准
     * 
     * @param id 家政-卫生清洁-收费标准ID
     * @return 家政-卫生清洁-收费标准
     */
    @Override
    public JzCl selectJzClById(Long id)
    {
        return jzClMapper.selectJzClById(id);
    }

    /**
     * 查询家政-卫生清洁-收费标准列表
     * 
     * @param jzCl 家政-卫生清洁-收费标准
     * @return 家政-卫生清洁-收费标准
     */
    @Override
    public List<JzCl> selectJzClList(JzCl jzCl)
    {
        return jzClMapper.selectJzClList(jzCl);
    }

    /**
     * 新增家政-卫生清洁-收费标准
     * 
     * @param jzCl 家政-卫生清洁-收费标准
     * @return 结果
     */
    @Override
    public int insertJzCl(JzCl jzCl)
    {
        jzCl.setCreateTime(DateUtils.getNowDate());
        return jzClMapper.insertJzCl(jzCl);
    }

    /**
     * 修改家政-卫生清洁-收费标准
     * 
     * @param jzCl 家政-卫生清洁-收费标准
     * @return 结果
     */
    @Override
    public int updateJzCl(JzCl jzCl)
    {
        jzCl.setUpdateTime(DateUtils.getNowDate());
        return jzClMapper.updateJzCl(jzCl);
    }

    /**
     * 批量删除家政-卫生清洁-收费标准
     * 
     * @param ids 需要删除的家政-卫生清洁-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzClByIds(Long[] ids)
    {
        return jzClMapper.deleteJzClByIds(ids);
    }

    /**
     * 删除家政-卫生清洁-收费标准信息
     * 
     * @param id 家政-卫生清洁-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzClById(Long id)
    {
        return jzClMapper.deleteJzClById(id);
    }

    /**
     * 校验编码是否唯一
     *
     * @param jzCl 卫生清洁信息
     * @return 结果
     */
    @Override
    public String checkNoUnique(JzCl jzCl) {
        JzCl h = jzClMapper.selectByNo(jzCl.getNo());
        if (h != null) {
            //判断是否是自己
            if (h.getId().equals(jzCl.getId())) {
                return "0";
            }
            return "1";
        }
        return "0";
    }
}
