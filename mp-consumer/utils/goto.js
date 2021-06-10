//跳转(必须登录)
let gotoIfLogin = function(url) {
  let that = this
  //判断是否登录
  let token = wx.getStorageSync('token')
  if(token){
    wx.navigateTo({ url: url })
  } else {
    wx.showToast({
      title: '请先登录',
      icon: 'none'
    })
  }
}
module.exports = {
  gotoIfLogin
}

