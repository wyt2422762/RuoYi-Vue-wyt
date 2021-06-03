package com.ruoyi.web.controller.bus;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.service.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 客户管理
 * @author wyt
 */
@RestController
@RequestMapping("/bus/consumer")
public class ConsumerController extends BaseController {
    @Autowired
    private IConsumerService consumerService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取客户列表
     */
    @PreAuthorize("@ss.hasPermi('bus:consumer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Consumer consumer)
    {
        startPage();
        List<Consumer> list = consumerService.selectConsumerList(consumer);
        return getDataTable(list);
    }

    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('bus:consumer:export')")
    @GetMapping("/export")
    public AjaxResult export(Consumer consumer)
    {
        List<Consumer> list = consumerService.selectConsumerList(consumer);
        ExcelUtil<Consumer> util = new ExcelUtil<>(Consumer.class);
        return util.exportExcel(list, "客户数据");
    }

    @Log(title = "客户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('bus:consumer:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Consumer> util = new ExcelUtil<>(Consumer.class);
        List<Consumer> consumerList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = consumerService.importConsumer(consumerList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Consumer> util = new ExcelUtil<>(Consumer.class);
        return util.importTemplateExcel("客户数据");
    }

    /**
     * 根据客户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:consumer:query')")
    @GetMapping(value = { "/", "/{consumerId}" })
    public AjaxResult getInfo(@PathVariable(value = "consumerId", required = false) Long consumerId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(consumerId))
        {
            ajax.put(AjaxResult.DATA_TAG, consumerService.selectConsumerById(consumerId));
        }
        return ajax;
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('bus:consumer:add')")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Consumer consumer)
    {
        if (StringUtils.isNotEmpty(consumer.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(consumerService.checkPhoneUnique(consumer)))
        {
            return AjaxResult.error("新增客户'" + consumer.getPhonenumber() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(consumer.getEmail())
                && UserConstants.NOT_UNIQUE.equals(consumerService.checkEmailUnique(consumer)))
        {
            return AjaxResult.error("新增客户'" + consumer.getEmail() + "'失败，邮箱账号已存在");
        }
        consumer.setCreateBy(SecurityUtils.getUsername());
        return toAjax(consumerService.insertConsumer(consumer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('bus:consumer:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody Consumer consumer)
    {
        consumerService.checkConsumerAllowed(consumer);
        if (StringUtils.isNotEmpty(consumer.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(consumerService.checkPhoneUnique(consumer)))
        {
            return AjaxResult.error("修改客户'" + consumer.getPhonenumber() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(consumer.getEmail())
                && UserConstants.NOT_UNIQUE.equals(consumerService.checkEmailUnique(consumer)))
        {
            return AjaxResult.error("修改客户'" + consumer.getEmail() + "'失败，邮箱账号已存在");
        }
        consumer.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(consumerService.updateConsumer(consumer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('bus:consumer:remove')")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(consumerService.deleteConsumerByIds(userIds));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('bus:consumer:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated(EditGroup.class) @RequestBody Consumer consumer)
    {
        consumerService.checkConsumerAllowed(consumer);
        consumer.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(consumerService.updateConsumerStatus(consumer));
    }

}
