//跳转(必须登录)

let iView = require('./iViewUtil.js')

let gotoIfLogin = function (url) {
  let that = this
  //判断是否登录
  let token = wx.getStorageSync('token')
  if (token) {
    //判断用户信息是否完善
    if (getApp().globalData.userInfoComplete) {
      wx.navigateTo({
        url: url
      })
    } else {
      iView.toast.warning('用户信息未完善，请先完善用户信息')
      return false
    }
  } else {
    iView.toast.warning('请先登录')
  }
}
module.exports = {
  gotoIfLogin
}