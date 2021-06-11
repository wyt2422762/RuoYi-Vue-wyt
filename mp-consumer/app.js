const config = require("./utils/config")
import {
  service
} from './utils/request.js'

//app.js
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
              that.globalData.openId = res.data['openid']
              that.globalData.sessionKey = res.data['sessionKey']
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
  //判断用户信息是否完善
  isUserComplete(user) {
    let that = this
    if(!user.name || !user.addr || !user.emergencyContactPhone || !user.emergencyContactName){
      that.globalData.userInfoComplete = false
    } else {
      that.globalData.userInfoComplete = true
    }
  },
  //获取个人信息方法(这里用来革新缓存的用户信息)
  getUserInfo() {
    let that = this
    console.log('电话号码 = ' + wx.getStorageSync('phoneNumber'))
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/userInfo/getConsumer', {
      data: {
        phoneNumber: wx.getStorageSync('phoneNumber')
      }
    }).then(res => {
      console.log('读取个人信息成功')
      //更新缓存的个人信息
      wx.setStorageSync('user', res.data)
      isUserComplete(res.data)
    }).catch(error => {
      console.log('读取个人信息失败')
    })
  },
  globalData: {
    //appid
    appid: config.appid,
    //后台基础路径
    backBaseUrl: config.backBaseUrl,
    //接口基础路径接口
    apiBaseUrl: config.apiBaseUrl,
    //是否登录
    isLogin: false,
    //openId
    openId: null,
    //sessionKey
    sessionKey: null,
    //用户信息是否完善标识
    userInfoComplete: false
  }
})