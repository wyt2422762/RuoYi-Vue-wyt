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
    //头像
    avatarBaseUrl: config.backBaseUrl,
    userInfo: wx.getStorageSync('user'),
    isLogin: app.globalData.isLogin,
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
        appId: config.appid,
        sessionKey: config.sessionKey,
        encryptedData: e.detail.encryptedData,
        iv: e.detail.iv
      }
    }).then(res => {
      //存电话号码
      wx.setStorageSync('phoneNumber', res.data['purePhoneNumber'])
      //登录
      service.get('/base/login', {
        data: {
          openId: config.appid,
          phoneNumber:  wx.getStorageSync('phoneNumber'),
          type: '1'
        }
      }).then(res => {
        //存token
        wx.setStorageSync('token', res.data.token)
        //存用户信息
        wx.setStorageSync('user', res.data.user)
        app.globalData.isLogin = true
        that.setData({
          isLogin: true,
          userInfo: wx.getStorageSync('user')
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
  }
})