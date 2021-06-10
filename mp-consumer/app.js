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
    let userProfile = wx.getStorageSync('userProfile')
    if (userProfile) {
      that.globalData.hasUserProfile = true
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
    });

    // // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo
    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     }
    //   }
    // })
    //this.getUserProfile()
  },
  // 获取用户信息
  getUserProfile() {
    // if (!this.hasUserProfile) {
    //   let that = this
    //   wx.showModal({
    //     title: '温馨提示',
    //     content: '正在请求您的个人信息',
    //     success(res) {
    //       if (res.confirm) {
    //         // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    //         wx.getUserProfile({
    //           desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
    //           success: (res) => {
    //             wx.setStorageSync('userProfile', res)
    //             that.globalData.hasUserProfile = true
    //           }
    //         })
    //       }
    //     }
    //   })
    // }
  },
  //判断用户信息是否完善
  isUserComplete() {},
  globalData: {
    userInfo: null,
    //appid
    appid: config.appid,
    //后台基础路径
    backBaseUrl: config.backBaseUrl,
    //接口基础路径接口
    apiBaseUrl: config.apiBaseUrl,
    //是否登录
    isLogin: false,
    //是否有个微信人信息
    hasUserProfile: false,
    //openId
    openId: null,
    //sessionKey
    sessionKey: null,
  }
})