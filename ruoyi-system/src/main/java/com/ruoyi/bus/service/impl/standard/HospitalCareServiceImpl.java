package com.ruoyi.bus.service.impl.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.HospitalCare;
import com.ruoyi.bus.mapper.standard.HospitalCareMapper;
import com.ruoyi.bus.service.standard.IHospitalCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收费标准-医院陪护Service业务层处理
 *
 * @author wyt
 * @date 2021-06-02
 */
@Service
public class HospitalCareServiceImpl implements IHospitalCareService {
    @Autowired
    private HospitalCareMapper hospitalCareMapper;

    /**
     * 查询收费标准-医院陪护
     *
     * @param id 收费标准-医院陪护ID
     * @return 收费标准-医院陪护
     */
    @Override
    public HospitalCare selectHospitalCareById(Long id) {
        return hospitalCareMapper.selectHospitalCareById(id);
    }

    /**
     * 查询收费标准-医院陪护
     *
     * @param no 收费标准-医院陪护NO
     * @return 收费标准-医院陪护
     */
    @Override
    public HospitalCare selectHospitalCareByNo(String no) {
        return hospitalCareMapper.selectHospitalCareByNo(no);
    }

    /**
     * 查询收费标准-医院陪护列表
     *
     * @param hospitalCare 收费标准-医院陪护
     * @return 收费标准-医院陪护
     */
    @Override
    public List<HospitalCare> selectHospitalCareList(HospitalCare hospitalCare) {
        return hospitalCareMapper.selectHospitalCareList(hospitalCare);
    }

    /**
     * 新增收费标准-医院陪护
     *
     * @param hospitalCare 收费标准-医院陪护
     * @return 结果
     */
    @Override
    @Transactional
    public int insertHospitalCare(HospitalCare hospitalCare) {
        return hospitalCareMapper.insertHospitalCare(hospitalCare);
    }

    /**
     * 修改收费标准-医院陪护
     *
     * @param hospitalCare 收费标准-医院陪护
     * @return 结果
     */
    @Override
    @Transactional
    public int updateHospitalCare(HospitalCare hospitalCare) {
        return hospitalCareMapper.updateHospitalCare(hospitalCare);
    }

    /**
     * 批量删除收费标准-医院陪护
     *
     * @param ids 需要删除的收费标准-医院陪护ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteHospitalCareByIds(Long[] ids) {
        return hospitalCareMapper.deleteHospitalCareByIds(ids);
    }

    /**
     * 删除收费标准-医院陪护信息
     *
     * @param id 收费标准-医院陪护ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteHospitalCareById(Long id) {
        return hospitalCareMapper.deleteHospitalCareById(id);
    }

    /**
     * 校验编码是否唯一
     *
     * @param hospitalCare 医院陪护信息
     * @return 结果
     */
    @Override
    public String checkNoUnique(HospitalCare hospitalCare) {
        HospitalCare h = hospitalCareMapper.selectHospitalCareByNo(hospitalCare.getNo());
        if (h != null) {
            //判断是否是自己
            if (h.getId().equals(hospitalCare.getId())) {
                return "0";
            }
            return "1";
        }
        return "0";
    }
}
