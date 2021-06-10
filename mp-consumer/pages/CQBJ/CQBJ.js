// pages/ZLLR/ZLLR.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: "",
    countries: ["请选择","1", "2", "3"],
    home: ["请选择","8", "2", "3"],
    Sanatorium: ["请选择居室面积","4", "5", "3"],
  
    btn_disabled:true,
  },
  bindDateChange: function (e) {
    this.setData({
      date: e.detail.value
    })
  },
  bindhomeChange: function (e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);
    this.setData({
      homeIndex: e.detail.value
    })
  },

    bindCountryChange: function (e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);
    this.setData({
      countryIndex: e.detail.value
    })
  },
  bindCountry2Change: function (e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);
    this.setData({
      SanatoriumIndex: e.detail.value
    })
  },
  bindAgreeChange:function(e) {
    //  console.log(e.detail.value)
      this.setData({
        isAgree:e.detail.value.length,
      })
      if (e.detail.value.length==1){
       this.setData({
         btn_disabled:false,
       })
     }else{
        //onsole.log(e.detail.value.length)
       this.setData({
         btn_disabled:true
       })
     }
    },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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