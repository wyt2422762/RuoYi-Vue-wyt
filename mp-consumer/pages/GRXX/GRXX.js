// 个人信息
import {
  service
} from '../../utils/request.js'

let iView = require('../../utils/iViewUtil.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    //loading
    hiddenLoading: true,
    //客户信息
    consumer: null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //获取个人信息
    this.getUserInfo()
  },
  //获取个人信息方法
  getUserInfo() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/userInfo/getConsumer', {
      data: {
        phoneNumber: wx.getStorageSync('phoneNumber')
      }
    }).then(res => {
      that.setData({
        consumer: res.data,
        hiddenLoading: !that.data.hiddenLoading
      })
    }).catch(error => {
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      iView.toast.error('读取个人信息失败')
    })
  },
  //修改个人信息方法
  update(e) {
    let that = this
    let consumer = that.data.consumer
    //参数检查
    if (!consumer.name) {
      iView.toast.warning('姓名不能为空')
      return false
    } else if (!consumer.emergencyContactName) {
      iView.toast.warning('紧急联系人姓名不能为空')
      return false
    } else if (!consumer.emergencyContactPhone) {
      iView.toast.warning('紧急联系人电话不能为空')
      return false
    } else if (!consumer.addr) {
      iView.toast.warning('家庭地址不能为空')
      return false
    }
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    //请求数据
    service.put('/userInfo/updateConsumer', {
      data: consumer
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      //更新缓存的用户信息
      wx.setStorageSync('user', consumer)
      iView.toast.success('保存成功')
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      iView.toast.error('保存失败')
    })
  },
  //姓名变化
  nameChange(e) {
    let that = this
    that.data.consumer.name = e.detail.detail.value
  },
  //年龄变化
  ageChange(e) {
    let that = this
    that.data.consumer.age = e.detail.detail.value
  },
  //身份证号变化
  idNoChange(e) {
    let that = this
    that.data.consumer.idNo = e.detail.detail.value
  },
  //紧急联系人姓名变化
  emergencyContactNameChange(e) {
    let that = this
    that.data.consumer.emergencyContactName = e.detail.detail.value
  },
  //紧急联系人电话变化
  emergencyContactPhoneChange(e) {
    let that = this
    that.data.consumer.emergencyContactPhone = e.detail.detail.value
  },
  //家庭地址变化
  addrChange(e) {
    let that = this
    that.data.consumer.addr = e.detail.detail.value
  },
})