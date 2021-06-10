// pages/TSXQ/TSXQ.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgs: [] ,
  },
 // 上传图片
 chooseImg: function (e) {
  var that = this;
  var _this = this;
  var imgs = this.data.imgs;
  if (imgs.length >= 9) {
    this.setData({
      lenMore: 1
    });
    setTimeout(function () {
      that.setData({
        lenMore: 0
      });
    }, 2500);
    return false;
  }
  wx.chooseImage({
    // count: 1, // 默认9
    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
    success: function (res) {
      var imgs = that.data.imgs;
      if(res.tempFilePaths&&res.tempFilePaths.length>0){
        for(let i=0;i<res.tempFilePaths.length;i++){
          imgs.push(res.tempFilePaths[i]);
          // wx.uploadFile({
          //   url: app.globalData.server+"/GYFW/Phone/UpLoad",
          //   filePath: res.tempFilePaths[i],
          //   name:"tp",
          //   header: {  
          //     "Content-Type": "multipart/form-data"  
          //   },  
          //   success:function(up_res){
          //     var data = JSON.parse(up_res.data); 
          //     _this.data.tpPath.push(data[0])
          //     _this.setData({
          //       tpPath: _this.data.tpPath
          //     })
          //     console.log(_this.data.tpPath)
          //   }
          // })
        }
        _this.setData({
          imgs: imgs
        });
      }
    }
  });
},
// 删除图片
deleteImg: function (e) {
  var imgs = this.data.imgs;
  var index = e.currentTarget.dataset.index;
  imgs.splice(index, 1);
  this.setData({
    imgs: imgs
  });
},
// 预览图片
previewImg: function (e) {
  //获取当前图片的下标
  var index = e.currentTarget.dataset.index;
  //所有图片
  var imgs = this.data.imgs;
  wx.previewImage({
    //当前显示图片
    current: imgs[index],
    //所有图片
    urls: imgs
  })
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