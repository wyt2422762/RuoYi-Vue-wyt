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
    that.quertDetail()
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
  },
})