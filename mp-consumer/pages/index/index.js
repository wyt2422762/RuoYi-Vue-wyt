//index.js

import {
  service
} from '../../utils/request.js'

//获取应用实例
const app = getApp()
Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    currentSelectTripType: 'pinche',
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    //轮播图
    bannerBaseUrl: app.globalData.backBaseUrl,
    bannerUrl: [],
  },
  // 更新data 切换选中状态
  selectedPinche: function (e) {
    this.setData({
      currentSelectTripType: e.currentTarget.dataset.id
    })
  },
  selectedBaoche: function (e) {
    this.setData({
      currentSelectTripType: e.currentTarget.dataset.id
    })
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    let that = this
    that.getBannerUrl()
  },
  //获取轮播图连接
  getBannerUrl() {
    let that = this
    service.get('/index/getLunBoTu', {
      notAddToken: true
    }).then(res => {
      that.setData({
        bannerUrl: res.data
      })
    })
  }

})