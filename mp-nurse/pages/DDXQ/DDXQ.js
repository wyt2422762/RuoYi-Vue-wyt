//订单详情页
import {
  getDicts
} from '../../utils/dict.js'
import {
  service,
  allReq
} from '../../utils/request.js'

let iView = require('../../utils/iViewUtil.js')

Page({
  data: {
    //loading
    hiddenLoading: true,
    //是否显示完成确认
    showDoneOrderConfirm: false,
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
  //显示完成确认
  showDoneOrder() {
    this.setData({
      showDoneOrderConfirm: true
    })
  },
  //隐藏完成确认
  hideDoneOrder() {
    this.setData({
      showDoneOrderConfirm: false
    })
  },
  //订单完成
  doneOrder() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    let orderNo = that.data.order.orderNo
    service.put("/order/done/" + orderNo, {}).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        showDoneOrderConfirm: false
      })
      iView.toast.success('操作成功')
      wx.navigateBack({
        delta: 1,
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        showDoneOrderConfirm: false
      })
      iView.toast.error('操作失败')
    })
  },
})