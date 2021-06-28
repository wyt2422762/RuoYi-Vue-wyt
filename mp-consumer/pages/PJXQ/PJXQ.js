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
    //detail
    detail: null,
    //评价
    evaluation: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this
    that.data.evaluation.orderNo = options.orderNo
    that.setData({
      detail: options.detail
    })
    if(that.data.detail){
      that.quertDetail()
    }
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
      wx.navigateBack({
        delta: 2,
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      iView.toast.error('评价失败')
    })
  },
  //查看评价详情
  quertDetail(){
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/order/getOrderEvaluation/' + that.data.evaluation.orderNo, {
    }).then(res => {
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        evaluation: res.data
      })
    }).catch(err => {
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  }
})