package com.ruoyi.bus.mapper.standard;

import java.util.List;

import com.ruoyi.bus.domain.standard.HospitalCare;

/**
 * 收费标准-医院陪护Mapper接口
 *
 * @author wyt
 * @date 2021-06-02
 */
public interface HospitalCareMapper {
    /**
     * 查询收费标准-医院陪护
     *
     * @param id 收费标准-医院陪护ID
     * @return 收费标准-医院陪护
     */
    HospitalCare selectHospitalCareById(Long id);

    /**
     * 查询收费标准-医院陪护
     *
     * @param no 收费标准-医院陪护no
     * @return 收费标准-医院陪护
     */
    HospitalCare selectHospitalCareByNo(String no);

    /**
     * 查询收费标准-医院陪护列表
     *
     * @param hospitalCare 收费标准-医院陪护
     * @return 收费标准-医院陪护集合
     */
    List<HospitalCare> selectHospitalCareList(HospitalCare hospitalCare);

    /**
     * 新增收费标准-医院陪护
     *
     * @param hospitalCare 收费标准-医院陪护
     * @return 结果
     */
    int insertHospitalCare(HospitalCare hospitalCare);

    /**
     * 修改收费标准-医院陪护
     *
     * @param hospitalCare 收费标准-医院陪护
     * @return 结果
     */
    int updateHospitalCare(HospitalCare hospitalCare);

    /**
     * 删除收费标准-医院陪护
     *
     * @param id 收费标准-医院陪护ID
     * @return 结果
     */
    int deleteHospitalCareById(Long id);

    /**
     * 批量删除收费标准-医院陪护
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteHospitalCareByIds(Long[] ids);
}
