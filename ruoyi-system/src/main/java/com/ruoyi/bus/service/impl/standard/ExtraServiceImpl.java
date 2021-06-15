package com.ruoyi.bus.service.impl.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.Extra;
import com.ruoyi.bus.mapper.standard.ExtraMapper;
import com.ruoyi.bus.service.standard.IExtraService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 额外服务Service业务层处理
 *
 * @author wyt
 * @date 2021-06-15
 */
@Service
public class ExtraServiceImpl implements IExtraService {
    @Autowired
    private ExtraMapper extraMapper;

    /**
     * 查询额外服务
     *
     * @param id 额外服务ID
     * @return 额外服务
     */
    @Override
    public Extra selectExtraById(Long id) {
        return extraMapper.selectExtraById(id);
    }

    /**
     * 查询额外服务列表
     *
     * @param extra 额外服务
     * @return 额外服务
     */
    @Override
    public List<Extra> selectExtraList(Extra extra) {
        return extraMapper.selectExtraList(extra);
    }

    /**
     * 新增额外服务
     *
     * @param extra 额外服务
     * @return 结果
     */
    @Override
    public int insertExtra(Extra extra) {
        extra.setCreateTime(DateUtils.getNowDate());
        return extraMapper.insertExtra(extra);
    }

    /**
     * 修改额外服务
     *
     * @param extra 额外服务
     * @return 结果
     */
    @Override
    public int updateExtra(Extra extra) {
        extra.setUpdateTime(DateUtils.getNowDate());
        return extraMapper.updateExtra(extra);
    }

    /**
     * 批量删除额外服务
     *
     * @param ids 需要删除的额外服务ID
     * @return 结果
     */
    @Override
    public int deleteExtraByIds(Long[] ids) {
        return extraMapper.deleteExtraByIds(ids);
    }

    /**
     * 删除额外服务信息
     *
     * @param id 额外服务ID
     * @return 结果
     */
    @Override
    public int deleteExtraById(Long id) {
        return extraMapper.deleteExtraById(id);
    }
}
