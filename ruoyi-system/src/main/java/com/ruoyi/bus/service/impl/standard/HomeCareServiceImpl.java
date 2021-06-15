package com.ruoyi.bus.service.impl.standard;

import com.ruoyi.bus.domain.standard.HomeCare;
import com.ruoyi.bus.mapper.standard.HomeCareMapper;
import com.ruoyi.bus.service.standard.IHomeCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收费标准-居家陪护Service业务层处理
 *
 * @author wyt
 * @date 2021-06-02
 */
@Service
public class HomeCareServiceImpl implements IHomeCareService {
    @Autowired
    private HomeCareMapper homeCareMapper;

    /**
     * 查询收费标准-居家陪护
     *
     * @param id 收费标准-居家陪护ID
     * @return 收费标准-居家陪护
     */
    @Override
    public HomeCare selectHomeCareById(Long id) {
        return homeCareMapper.selectHomeCareById(id);
    }

    /**
     * 查询收费标准-居家陪护
     *
     * @param no 收费标准-居家陪护NO
     * @return 收费标准-居家陪护
     */
    @Override
    public HomeCare selectHomeCareByNo(String no) {
        return homeCareMapper.selectHomeCareByNo(no);
    }

    /**
     * 查询收费标准-居家陪护列表
     *
     * @param homeCare 收费标准-居家陪护
     * @return 收费标准-居家陪护
     */
    @Override
    public List<HomeCare> selectHomeCareList(HomeCare homeCare) {
        return homeCareMapper.selectHomeCareList(homeCare);
    }

    /**
     * 新增收费标准-居家陪护
     *
     * @param homeCare 收费标准-居家陪护
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHomeCare(HomeCare homeCare) {
        return homeCareMapper.insertHomeCare(homeCare);
    }

    /**
     * 修改收费标准-居家陪护
     *
     * @param homeCare 收费标准-居家陪护
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateHomeCare(HomeCare homeCare) {
        return homeCareMapper.updateHomeCare(homeCare);
    }

    /**
     * 批量删除收费标准-居家陪护
     *
     * @param ids 需要删除的收费标准-居家陪护ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteHomeCareByIds(Long[] ids) {
        return homeCareMapper.deleteHomeCareByIds(ids);
    }

    /**
     * 删除收费标准-居家陪护信息
     *
     * @param id 收费标准-居家陪护ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteHomeCareById(Long id) {
        return homeCareMapper.deleteHomeCareById(id);
    }

    /**
     * 校验编码是否唯一
     *
     * @param homeCare 居家陪护信息
     * @return 结果
     */
    @Override
    public String checkNoUnique(HomeCare homeCare) {
        HomeCare h = homeCareMapper.selectHomeCareByNo(homeCare.getNo());
        if (h != null) {
            //判断是否是自己
            if (h.getId().equals(homeCare.getId())) {
                return "0";
            }
            return "1";
        }
        return "0";
    }

}
