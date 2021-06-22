//订单详情页
import {
  getDicts
} from '../../utils/dict.js'
import {
  service,
  allReq
} from '../../utils/request.js'
import {
  pay
} from '../../utils/pay.js'

let iView = require('../../utils/iViewUtil.js')
let gto = require('../../utils/goto.js')

Page({
  data: {
    //loading
    hiddenLoading: true,
    //是否显示取消确认
    showCancelOrderConfirm: false,
    //支付按钮是否点击过
    payButtonClicked: false,
    //订单类型字典
    orderTypeOptions: [],
    //订单状态字典
    orderStatusOptions: [],
    order: {}
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onLoad(options) {
    let that = this
    that.data.order.orderNo = options.orderNo
    //获取字典，数据
    allReq([getDicts("bus_order_type"), getDicts("bus_order_status")]).then(res => {
      that.data.orderTypeOptions = res[0].data
      that.data.orderStatusOptions = res[1].data
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
  //显示取消确认
  showCancelOrder() {
    this.setData({
      showCancelOrderConfirm: true
    })
  },
  //隐藏取消确认
  hideCancelOrder() {
    this.setData({
      showCancelOrderConfirm: false
    })
  },
  //订单取消
  cancelOrder(e) {
    let that = this
    let orderNo = that.data.order.orderNo
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.put("/order/cancel/" + orderNo, {}).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        showCancelOrderConfirm: false
      })
      iView.toast.success('取消成功')
      wx.navigateBack({
        delta: 1,
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        showCancelOrderConfirm: false
      })
      iView.toast.error('取消失败')
    })
  },
  //支付方法
  payOrder(e) {
    let that = this
    that.setData({
      payButtonClicked: false
    })
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
    that.setData({
      payButtonClicked: false
    })
    wx.navigateBack({
      delta: 1,
    })
    //更新订单状态
    // let orderNo = that.data.order.orderNo
    // service.put("/order/paySuccess/" + orderNo, {}).then(res => {
    // }).catch(err => {
    //   console.log("更改订单状态失败")
    // }).finally(f => {
    //   wx.navigateBack({
    //     delta: 1,
    //   })
    // })
  },
  //支付失败回调方法
  payFail(err) {
    let that = this
    iView.toast.error('支付失败')
    console.error("支付失败", err)
    that.setData({
      payButtonClicked: false
    })
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    let orderNo = this.data.order.orderNo
    gto.gotoIfLogin(url + "?orderNo=" + orderNo)
  },
})