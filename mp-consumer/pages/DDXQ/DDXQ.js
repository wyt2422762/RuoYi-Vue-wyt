//订单详情页
import {
  getDicts
} from '../../utils/dict.js'
import {
  service
} from '../../utils/request.js'
import {
  pay
} from '../../utils/pay.js'

let iView = require('../../utils/iViewUtil.js')

Page({
  data: {
    //loading
    hiddenLoading: true,
    //订单类型字典
    orderTypeOptions: [],
    //订单状态字典
    orderStatusOptions: [],
    order: {}
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onLoad: function (options) {
    let that = this
    that.data.order.orderNo = options.orderNo
    //获取订单类型字典
    getDicts("bus_order_type").then(res => {
      that.orderTypeOptions = res.data;
    })
    //获取订单状态字典
    getDicts("bus_order_status").then(res => {
      that.orderStatusOptions = res.data;
    })
    //获取数据
    that.getDetail()
  },
  //获取详情
  getDetail() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    //查询数据
    service.get('/order/' + that.data.order.orderNo, {}).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      that.setData({
        order: res.data
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  //订单取消
  cancelOrder(e) {
    let that = this
    let orderNo = that.data.order.orderNo
    service.put("/order/cancel/" + orderNo, {
    }).then(res => {
      iView.toast.success('取消成功')
    }).catch(err => {
      iView.toast.error('取消失败')
    })
  },
  //支付方法
  payOrder(e) {
    debugger
    let that = this
    //订单编号
    let orderNo = that.data.order.orderNo
    //金额(元)
    let money = that.data.order.money
    //支付
    pay(orderNo, money, that.paySuccess, that.payFail)
  },
  //支付成功回调方法
  paySuccess(res) {
    let that = this
    iView.toast.success('支付成功')
    //刷新当前页面
    that.onLoad()
  },
  //支付失败回调方法
  payFail(err) {
    let that = this
    iView.toast.error('支付失败')
    return false
  }
})