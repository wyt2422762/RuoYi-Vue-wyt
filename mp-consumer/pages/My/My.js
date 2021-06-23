//my.js
import {
  service
} from '../../utils/request.js'

let gto = require('../../utils/goto.js')
const config = require("../../utils/config.js")
let iView = require('../../utils/iViewUtil.js')

const app = getApp()

Page({
  /**
   * 页面的初始数据
   */
  data: {
    isLogin: app.globalData.isLogin,
    //客服电话
    phone_cs: config.phone_cs
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow(e) {
    this.setData({
      isLogin: app.globalData.isLogin,
    })
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
        appId: config.appid,
        sessionKey: wx.getStorageSync('sessionKey'),
        encryptedData: e.detail.encryptedData,
        iv: e.detail.iv
      }
    }).then(res => {
      //存电话号码
      wx.setStorageSync('phoneNumber', res.data['purePhoneNumber'])
      //登录
      service.get('/base/login', {
        data: {
          openId: wx.getStorageSync('openid'),
          phoneNumber:  wx.getStorageSync('phoneNumber'),
          type: '0'
        }
      }).then(res => {
        //存token
        wx.setStorageSync('token', res.data.token)
        //存用户信息
        wx.setStorageSync('user', res.data.user)
        app.isUserComplete(res.data.user)
        app.globalData.isLogin = true
        that.setData({
          isLogin: true
        })
      }).catch(err => {
        iView.toast.error('登录失败')
      })
    })
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  },
  //联系客服
  callService(e) {
    wx.makePhoneCall({
      phoneNumber: this.data.phone_cs
    })
  }
})