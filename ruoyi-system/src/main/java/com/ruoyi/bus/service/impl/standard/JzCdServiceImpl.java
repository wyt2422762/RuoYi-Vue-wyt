package com.ruoyi.bus.service.impl.standard;

import com.ruoyi.bus.domain.standard.JzCd;
import com.ruoyi.bus.mapper.standard.JzCdMapper;
import com.ruoyi.bus.service.standard.IJzCdService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 家政-床单被罩清洗-收费标准Service业务层处理
 * 
 * @author wyt
 * @date 2021-06-16
 */
@Service
public class JzCdServiceImpl implements IJzCdService 
{
    @Autowired
    private JzCdMapper jzCdMapper;

    /**
     * 查询家政-床单被罩清洗-收费标准
     * 
     * @param id 家政-床单被罩清洗-收费标准ID
     * @return 家政-床单被罩清洗-收费标准
     */
    @Override
    public JzCd selectJzCdById(Long id)
    {
        return jzCdMapper.selectJzCdById(id);
    }

    /**
     * 查询家政-床单被罩清洗-收费标准列表
     * 
     * @param jzCd 家政-床单被罩清洗-收费标准
     * @return 家政-床单被罩清洗-收费标准
     */
    @Override
    public List<JzCd> selectJzCdList(JzCd jzCd)
    {
        return jzCdMapper.selectJzCdList(jzCd);
    }

    /**
     * 新增家政-床单被罩清洗-收费标准
     * 
     * @param jzCd 家政-床单被罩清洗-收费标准
     * @return 结果
     */
    @Override
    public int insertJzCd(JzCd jzCd)
    {
        jzCd.setCreateTime(DateUtils.getNowDate());
        return jzCdMapper.insertJzCd(jzCd);
    }

    /**
     * 修改家政-床单被罩清洗-收费标准
     * 
     * @param jzCd 家政-床单被罩清洗-收费标准
     * @return 结果
     */
    @Override
    public int updateJzCd(JzCd jzCd)
    {
        jzCd.setUpdateTime(DateUtils.getNowDate());
        return jzCdMapper.updateJzCd(jzCd);
    }

    /**
     * 批量删除家政-床单被罩清洗-收费标准
     * 
     * @param ids 需要删除的家政-床单被罩清洗-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzCdByIds(Long[] ids)
    {
        return jzCdMapper.deleteJzCdByIds(ids);
    }

    /**
     * 删除家政-床单被罩清洗-收费标准信息
     * 
     * @param id 家政-床单被罩清洗-收费标准ID
     * @return 结果
     */
    @Override
    public int deleteJzCdById(Long id)
    {
        return jzCdMapper.deleteJzCdById(id);
    }

    /**
     * 校验编码是否唯一
     *
     * @param jzCd 床单被罩清洗信息
     * @return 结果
     */
    @Override
    public String checkNoUnique(JzCd jzCd) {
        JzCd h = jzCdMapper.selectByNo(jzCd.getNo());
        if (h != null) {
            //判断是否是自己
            if (h.getId().equals(jzCd.getId())) {
                return "0";
            }
            return "1";
        }
        return "0";
    }
}
