//投诉

import {
  service
} from '../../utils/request.js'

let iView = require('../../utils/iViewUtil.js')
let config = require('../../utils/config.js')

Page({
  data: {
    imgs: [],
    //loading
    hiddenLoading: true,
    // 订单表格数据
    orderList: [],
    // 默认选中
    defaultIndex: 0,
    //投诉内容
    complaint: {
      consumerId: null,
      orderNo: null,
      content: ''
    },
    baseUrl: config.backBaseUrl
  },
  // 上传图片
  chooseImg: function (e) {
    let that = this
    let imgs = this.data.imgs
    let complaint = that.data.complaint
    let orderNo = complaint.orderNo
    let url = config.apiBaseUrl + "/file/upload"
    if (imgs.length >= 3) {
      //最多传3张图片
      return false
    }
    wx.chooseImage({
      // count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        let imgs = that.data.imgs;
        if (res.tempFilePaths && res.tempFilePaths.length > 0) {
          for (let i = 0; i < res.tempFilePaths.length; i++) {
            wx.uploadFile({
              url: url,
              filePath: res.tempFilePaths[i],
              name:"file",
              header: {
                "Content-Type": "multipart/form-data",
                'Authorization': 'Bearer ' + wx.getStorageSync('token')
              },
              formData: {
                uploadDir: '/touSu/' + orderNo
              },
              success(res1) {
                let datas = JSON.parse(res1.data)
                imgs.push(datas.path)
                that.setData({
                  imgs: imgs
                })
              },
              fail(err1) {
                console.log(err1)
              }
            })
          }
        }
      }
    });
  },
  // 删除图片
  deleteImg(e) {
    let imgs = this.data.imgs;
    let index = e.currentTarget.dataset.index;
    imgs.splice(index, 1);
    this.setData({
      imgs: imgs
    });
  },
  // 预览图片
  previewImg: function (e) {
    let that = this
    //获取当前图片的下标
    let index = e.currentTarget.dataset.index;
    //所有图片
    let imgs = this.data.imgs;
    let imgs2 = [];
    for(let i = 0; i<imgs.length; i++){
      imgs2.push(that.data.baseUrl + imgs[i])
    }
    wx.previewImage({
      //当前显示图片
      current: imgs2[index],
      //所有图片
      urls: imgs2
    })
  },
  onLoad: function (options) {
    let that = this
    that.data.complaint.consumerId = wx.getStorageSync('user').consumerId
    //获取订单列表
    that.getAll()
  },
  // 订单编号变化
  orderNoChange(e){
    let that = this
    that.setData({
      'complaint.orderNo': that.data.orderList[e.detail.value].orderNo,
      defaultIndex: e.detail.value
    })
  },
  //内容变化
  contentChange(e) {
    let that = this
    that.data.complaint.content = e.detail.detail.value
  },
  //查询订单列表
  getAll() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/order/all', {
      data: that.data.queryParams
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      if(res.rows && res.rows.length > 0){
        that.setData({
          orderList: res.rows,
          'complaint.orderNo': res.rows[0].orderNo
        })
      }
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  //添加投诉
  addComplaint() {
    let that = this
    let complaint = that.data.complaint
    let imgs = that.data.imgs
    //参数检查
    if(!complaint.consumerId){
      iView.toast.warning('客户id不能为空')
      return false
    } else if(!complaint.content){
      iView.toast.warning('投诉内容不能为空')
      return false
    } else if(!complaint.orderNo){
      iView.toast.warning('订单编号不能为空')
      return false
    }
    //附件处理
    if(!complaint.attach){
      complaint.attach = []
    }
    for(let i = 0; i<imgs.length; i++){
      complaint.attach.push({'url': imgs[i]})
    }
    //提交数据
    service.post('/complaint', {
      data: complaint
    }).then(res => {
      iView.toast.success('投诉提交成功')
      wx.navigateBack({
        delta: 1,
      })
    }).catch(err => {
      iView.toast.error('投诉提交失败')
    })
  }
})