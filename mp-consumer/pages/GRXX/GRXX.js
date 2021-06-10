// 个人信息

import {
  service
} from '../../utils/request.js'

Page({
  /**
   * 页面的初始数据
   */
  data: {
    consumer: null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //获取个人信息
    this.getUserInfo()
  },
  //获取个人信息方法
  getUserInfo() {
    let that = this
    console.log('电话号码 = ' + wx.getStorageSync('phoneNumber'))
    service.get('/userInfo/getConsumer', {
      data: {
        phoneNumber: wx.getStorageSync('phoneNumber')
      }
    }).then(res => {
      that.setData({
        consumer: res.data
      })
    })
  }
})