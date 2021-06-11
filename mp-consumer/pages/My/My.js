//my.js
import {
  service
} from '../../utils/request.js'

let gto = require('../../utils/goto.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: getApp().globalData.userInfo,
    isLogin: getApp().globalData.isLogin
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },
  //登录(获取用户信息，token)
  login(e) {
    this.getPhoneNumber(e)
  },
  //获取用户手机号
  getPhoneNumber(e) {
    let that = this
    service.get('/base/phone', {
      data: {
        appId: getApp().globalData.appid,
        sessionKey: getApp().globalData.sessionKey,
        encryptedData: e.detail.encryptedData,
        iv: e.detail.iv
      }
    }).then(res => {
      //存电话号码
      wx.setStorageSync('phoneNumber', res.data['purePhoneNumber'])
      //登录
      service.get('/base/login', {
        data: {
          openId: getApp().globalData.openId,
          phoneNumber:  wx.getStorageSync('phoneNumber'),
          type: '0'
        }
      }).then(res => {
        //存token
        wx.setStorageSync('token', res.data.token)
        //存用户信息
        wx.setStorageSync('user', res.data.user)
        //app.globalData.user = res.data.user
        getApp().isUserComplete(res.data.user)
        getApp().globalData.isLogin = true
        that.setData({
          isLogin: true
        })
      })
    })
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  }
})