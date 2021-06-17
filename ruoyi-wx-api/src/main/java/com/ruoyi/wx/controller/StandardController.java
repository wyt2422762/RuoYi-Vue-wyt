package com.ruoyi.wx.controller;

import com.ruoyi.bus.domain.standard.*;
import com.ruoyi.bus.service.standard.*;
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
    @Autowired
    private IJzWsService jzWsService;
    @Autowired
    private IJzClService jzClService;
    @Autowired
    private IJzCdService jzCdService;

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
     * 获取额外项目收费标准
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

    /**
     * 获取卫生清洁收费标准
     *
     * @return 结果
     */
    @GetMapping("housekeeping/ws")
    public AjaxResult getHws() {
        List<JzWs> jzWs = jzWsService.selectJzWsList(null);
        Map<String, JzWs> map = new HashMap<>();
        for (JzWs jzW : jzWs) {
            map.put(jzW.getNo(), jzW);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("list", jzWs);
        res.put("map", map);
        return AjaxResult.success("查询成功", res);
    }

    /**
     * 获取窗帘清洗收费标准
     *
     * @return 结果
     */
    @GetMapping("housekeeping/cl")
    public AjaxResult getHcl() {
        List<JzCl> jzCls = jzClService.selectJzClList(null);
        Map<String, JzCl> map = new HashMap<>();
        for (JzCl jzCl : jzCls) {
            map.put(jzCl.getNo(), jzCl);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("list", jzCls);
        res.put("map", map);
        return AjaxResult.success("查询成功", res);
    }

    /**
     * 获取床单被罩清洗收费标准
     *
     * @return 结果
     */
    @GetMapping("housekeeping/cd")
    public AjaxResult getHcd() {
        List<JzCd> jzCds = jzCdService.selectJzCdList(null);
        Map<String, JzCd> map = new HashMap<>();
        for (JzCd jzCd : jzCds) {
            map.put(jzCd.getNo(), jzCd);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("list", jzCds);
        res.put("map", map);
        return AjaxResult.success("查询成功", res);
    }
}
