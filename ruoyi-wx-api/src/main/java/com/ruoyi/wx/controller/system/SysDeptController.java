package com.ruoyi.wx.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/wx/{appId}/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;
}
