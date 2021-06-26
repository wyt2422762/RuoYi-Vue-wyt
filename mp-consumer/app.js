const config = require("./utils/config")
import {
  service
} from './utils/request.js'

//app.js
App({
  onShow: function () {
    let that = this

    //验证token是否有效，无效的话重新登录后台
    let token = wx.getStorageSync('token')
    if (token) {
      that.globalData.isLogin = true
      that.validateToken(token)
    } else {
      //token失效，需要重新登陆
      wx.clearStorageSync('token')
      wx.clearStorageSync('user')
      wx.clearStorageSync('phoneNumber')
      wx.clearStorageSync('isLogin')
      that.globalData.isLogin = false
    }

    // 登录
    // wx.checkSession({
    //   success: res => {
    //     console.log('登录状态正常')
    //   },
    //   fail: res => {
    //     console.log('登录状态不正常')
    //     // 登录
    //     wx.login({
    //       success: res => {
    //         // 发送 res.code 到后台换取 openId, sessionKey, unionId
    //         service.get('/base/getOpenId', {
    //           notAddToken: true,
    //           data: {
    //             code: res.code
    //           }
    //         }).then(res => {
    //           wx.setStorageSync('openid', res.data['openid'])
    //           wx.setStorageSync('sessionKey', res.data['sessionKey'])
    //         })
    //       }
    //     })
    //   }
    // })

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

  },
  //判断用户信息是否完善
  isUserComplete(user) {
    let that = this
    if (!user.name || !user.addr) {
      wx.setStorageSync('userInfoComplete', false)
    } else {
      wx.setStorageSync('userInfoComplete', true)
    }
  },
  //获取个人信息方法(这里用来革新缓存的用户信息)
  getUserInfo() {
    let that = this
    service.get('/userInfo/getConsumer', {
      data: {
        phoneNumber: wx.getStorageSync('phoneNumber')
      }
    }).then(res => {
      //更新缓存的个人信息
      wx.setStorageSync('user', res.data)
      that.isUserComplete(res.data)
    }).catch(err => {
      console.log('读取个人信息失败', err)
    })
  },
  //验证token是否有效
  validateToken(token) {
    let that = this
    service.get('/base/validateToken/' + token, {
      notAddToken: true,
    }).then(res => {
      let validate = res.data
      if (!validate) {
        //token失效，需要重新登陆
        wx.clearStorageSync('token')
        wx.clearStorageSync('user')
        wx.clearStorageSync('phoneNumber')
        wx.clearStorageSync('isLogin')
        that.globalData.isLogin = false

        wx.redirectTo({
          url: './pages/My/My',
        })

      } else {
        //token有效, 更新用户信息
        that.getUserInfo()
      }
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
  }
})