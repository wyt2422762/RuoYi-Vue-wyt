//app.js
const config = require("./utils/config")
import {
  service
} from './utils/request.js'

App({
  onLaunch: function () {
    console.log('app onLanuch')
    let that = this
    // 展示本地存储能力
    let user = wx.getStorageSync('user')
    if (user) {
      that.globalData.isLogin = true
    }
    // 登录
    wx.checkSession({
      success: res => {
        console.log('登录状态正常')
      },
      fail: res => {
        console.log('登录状态不正常')
        // 登录
        wx.login({
          success: res => {
            // 发送 res.code 到后台换取 openId, sessionKey, unionId
            service.get('/base/getOpenId', {
              notAddToken: true,
              data: {
                code: res.code
              }
            }).then(res => {
              wx.setStorageSync('openid', res.data['openid'])
              wx.setStorageSync('sessionKey', res.data['sessionKey'])
            })
          }
        })
      }
    })
    //获取用户信息
    if (user) {
      that.getUserInfo()
    }
  },
  //获取个人信息方法(这里用来革新缓存的用户信息)
  getUserInfo() {
    let that = this
    console.log('电话号码 = ' + wx.getStorageSync('phoneNumber'))
    service.get('/userInfo/getNurse', {
      data: {
        phoneNumber: wx.getStorageSync('phoneNumber')
      }
    }).then(res => {
      console.log('读取个人信息成功')
      //更新缓存的个人信息
      wx.setStorageSync('user', res.data)
      that.isUserComplete(res.data)
    }).catch(error => {
      console.log('读取个人信息失败')
    })
  },
  globalData: {
    //是否登录
    isLogin: false,
  }
})