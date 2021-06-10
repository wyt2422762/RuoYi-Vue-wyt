//订单详情页

import {
  getDicts
} from '../../utils/dict.js'
import {
  service
} from '../../utils/request.js'

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
  }
})