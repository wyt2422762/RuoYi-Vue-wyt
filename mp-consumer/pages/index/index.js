//index.js

import {
  service
} from '../../utils/request.js'

let gto = require('../../utils/goto.js')
let config = require('../../utils/config.js')

//获取应用实例
const app = getApp()
Page({
  data: {
    //轮播图
    bannerBaseUrl: config.backBaseUrl,
    bannerUrl: [],
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
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  },
  //跳转
  goto1(e) {
    let url = e.currentTarget.dataset.url
    let typeid = e.currentTarget.dataset.typeid
    let typename = e.currentTarget.dataset.typename
    url = url + "?typeId=" + typeid + "&typeName=" + typename
    gto.gotoIfLogin(url)
  }
})