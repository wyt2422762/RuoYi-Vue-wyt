package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.standard.Extra;
import com.ruoyi.bus.domain.standard.HomeCare;
import com.ruoyi.bus.domain.standard.HospitalCare;
import com.ruoyi.bus.service.standard.IExtraService;
import com.ruoyi.bus.service.standard.IHomeCareService;
import com.ruoyi.bus.service.standard.IHospitalCareService;
import com.ruoyi.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收费标准
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/standard")
public class StandardController {

    @Autowired
    private IHomeCareService homeCareService;
    @Autowired
    private IHospitalCareService hospitalCareService;
    @Autowired
    private IExtraService extraService;

    /**
     * 获取居家陪护收费标准
     *
     * @return 结果
     */
    @GetMapping("homeCare")
    public AjaxResult getHomeCare() {
        List<HomeCare> homeCares = homeCareService.selectHomeCareList(null);
        Map<String, HomeCare> res = new HashMap<>();
        for (HomeCare homeCare : homeCares) {
            res.put(homeCare.getNo(), homeCare);
        }
        return AjaxResult.success("查询成功", res);
    }

    /**
     * 获取医院陪护收费标准
     *
     * @return 结果
     */
    @GetMapping("hospitalCare")
    public AjaxResult getHospitalCare() {
        List<HospitalCare> hospitalCares = hospitalCareService.selectHospitalCareList(null);
        Map<String, HospitalCare> res = new HashMap<>();
        for (HospitalCare hospitalCare : hospitalCares) {
            res.put(hospitalCare.getNo(), hospitalCare);
        }
        return AjaxResult.success("查询成功", res);
    }


    /**
     * 获取医院陪护收费标准
     *
     * @return 结果
     */
    @GetMapping("extras")
    public AjaxResult getExtra() {
        List<Extra> extras = extraService.selectExtraList(null);
        Map<String, Extra> map = new HashMap<>();
        for (Extra extra : extras) {
            map.put(extra.getName(), extra);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("list", extras);
        res.put("map", map);

        return AjaxResult.success("查询成功", res);
    }
}
