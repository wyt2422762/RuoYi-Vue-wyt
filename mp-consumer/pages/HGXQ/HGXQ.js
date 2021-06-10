//护工详情

const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')

import {
  service
} from '../../utils/request.js'

//获取应用实例
const app = getApp()

Page({
  data: {
    //loading
    hiddenLoading: true,
    //护工详情
    nurse: {
      nurseId: null,
    },
    //头像
    avatarBaseUrl: config.backBaseUrl,
    //评价列表
    evaluations: []
  },
  onLoad: function (e) {
    let that = this
    that.data.nurse.nurseId = e.nurseId
    //查询详情
    that.getDetail()
  },
  //查询护工详情
  getDetail() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    //请求数据
    service.get('/nurse/' + that.data.nurse.nurseId, {
      data: that.data.queryParams
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      that.setData({
        nurse: res.data.nurse,
        evaluations: res.data.evaluations,
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  //选择护工
  selectNurse() {
    let that = this
    let pages = getCurrentPages()
    let prev2Page = pages[pages.length - 3] //上两层页面
    //设置订单页面的数据
    prev2Page.setData({
      'order.nurseId': that.data.nurse.nurseId,
      'nurse.nurseId': that.data.nurse.nurseId,
      'nurse.name': that.data.nurse.name,
    })
    //返回上2页
    wx.navigateBack({
      delta: 2,
    })
  }
})