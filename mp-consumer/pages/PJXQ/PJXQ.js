// pages/PJXQ/PJXQ.js

const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')
let iView = require('../../utils/iViewUtil.js')

import {
  service
} from '../../utils/request.js'

Page({
  data: {
    //loading
    hiddenLoading: true,
    //评价
    evaluation: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this
    that.data.evaluation.orderNo = options.orderNo
  },
  //分数变化
  scoreChange(e) {
    let that = this
    that.setData({
      'evaluation.score': e.detail.index
    })
  },
  //内容变化
  textChange(e) {
    let that = this
    that.setData({
      'evaluation.text': e.detail.detail.value
    })
  },
  //提交评价
  submit(e) {
    let that = this
    debugger
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.post('/order/evaluation/' + that.data.evaluation.orderNo, {
      data: that.data.evaluation
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      iView.toast.success('评价成功')
      wx.redirectTo({
        url: '../QBDD/QBDD',
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      iView.toast.error('评价失败')
    })
  }
})