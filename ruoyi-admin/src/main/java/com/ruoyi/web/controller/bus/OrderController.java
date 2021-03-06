package com.ruoyi.web.controller.bus;

import com.ruoyi.bus.domain.Order;
import com.ruoyi.bus.service.IOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.validation.group.CreateGroup;
import com.ruoyi.common.validation.group.EditGroup;
import com.ruoyi.enm.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单Controller
 *
 * @author wyt
 * @date 2021-05-31
 */
@RestController
@RequestMapping("/bus/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('bus:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('bus:order:query')")
    @GetMapping(value = "/{orderNo}")
    public AjaxResult getInfo(@PathVariable("orderNo") Long orderNo)
    {
        return AjaxResult.success(orderService.selectOrderById(orderNo));
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('bus:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Order order)
    {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<>(Order.class);
        return util.exportExcel(list, "订单数据");
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('bus:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Order order)
    {
        order.setCreateBy(SecurityUtils.getUsername());
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('bus:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody Order order)
    {
        order.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('bus:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderNos}")
    public AjaxResult remove(@PathVariable Long[] orderNos)
    {
        return toAjax(orderService.deleteOrderByIds(orderNos));
    }

    /**
     * 派遣订单
     */
    @PreAuthorize("@ss.hasPermi('bus:order:edit')")
    @Log(title = "派遣订单", businessType = BusinessType.DELETE)
    @PutMapping("/{orderNo}/dispatch")
    public AjaxResult dispatch(@PathVariable Long orderNo)
    {
        int num = orderService.updateOrderStatus(orderNo + "", OrderStatusEnum.WPQ, OrderStatusEnum.WZF);
        //返回数据
        return AjaxResult.success(num > 0 ? "派遣成功": "订单状态改变");
    }

    /**
     * 订单服务中
     */
    @PreAuthorize("@ss.hasPermi('bus:order:edit')")
    @Log(title = "订单服务中", businessType = BusinessType.DELETE)
    @PutMapping("/{orderNo}/working")
    public AjaxResult working(@PathVariable Long orderNo)
    {
        int num = orderService.updateOrderStatus(orderNo + "", OrderStatusEnum.YZF, OrderStatusEnum.FWZ);
        //返回数据
        return AjaxResult.success(num > 0 ? "启动成功": "订单状态改变");
    }

    /**
     * 订单完成
     */
    @PreAuthorize("@ss.hasPermi('bus:order:edit')")
    @Log(title = "订单完成", businessType = BusinessType.DELETE)
    @PutMapping("/{orderNo}/done")
    public AjaxResult done(@PathVariable Long orderNo)
    {
        int num = orderService.updateOrderStatus(orderNo + "", OrderStatusEnum.FWZ, OrderStatusEnum.YWC);
        //返回数据
        return AjaxResult.success(num > 0 ? "完成成功": "订单状态改变");
    }
}
