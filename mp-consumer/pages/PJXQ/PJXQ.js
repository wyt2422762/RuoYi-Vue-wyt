// pages/PJXQ/PJXQ.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    flag:[0, 0, 0],
    startext: ['', '', ''],
    stardata: [1, 2, 3, 4, 5],
  },
// 五星评价事件
changeColor: function (e) {
  var index = e.currentTarget.dataset.index;
  var num = e.currentTarget.dataset.no;
  var a = 'flag[' + index + ']';
  var b = 'startext[' + index + ']';
  var that = this;
  if(num == 1) {
    that.setData({
      [a]: 1,
      [b]: '非常不满意'
    });
  } else if (num == 2){
    that.setData({
      [a]: 2,
      [b]: '不满意'
    });
  } else if (num == 3) {
    that.setData({
      [a]: 3,
      [b]: '一般'
    });
  } else if (num == 4) {
    that.setData({
      [a]: 4,
      [b]: '满意'
    });
  } else if (num == 5) {
    that.setData({
      [a]: 5,
      [b]: '非常满意'
    });
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