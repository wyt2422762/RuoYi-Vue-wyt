
Page({
 
  /**
   * 页面的初始数据
   */
  data: {
   
 
    showTopTips: false,
    date: "选择日期",
    date2: "选择日期",
    date3: "选择日期",
    date4: "选择日期",
    countries: ["请选择任务类型","电脑", "软件"],
 
  },
  bindDateChange: function (e) {
    this.setData({
      date: e.detail.value
    })
  },
  bindDateChange2: function (e) {
    this.setData({
      date2: e.detail.value
    })
  },
  bindDateChange3: function (e) {
    this.setData({
      date3: e.detail.value
    })
  },
  bindDateChange4: function (e) {
    this.setData({
      date4: e.detail.value
    })
  },
  bindCountryChange: function (e) {this.setData({
      countryIndex: e.detail.value
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
 
  },
 
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
 
  },
 
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
 
  },
 
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
 
  }
})