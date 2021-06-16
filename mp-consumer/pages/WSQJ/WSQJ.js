// 家政 - 卫生清洁

const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')
let iView = require('../../utils/iViewUtil.js')

import {
  service
} from '../../utils/request.js'

Page({
  data: {
    type: {
      id: null,
      name: null
    },
    date: "",
    countries: ["请选择", "1", "2", "3"],
    home: ["请选择", "8", "2", "3"],
    Sanatorium: ["请选择居室面积", "4", "5", "3"],
    btn_disabled: true,

    //订单
    order: {
      consumerId: null,
      type: config.orderType['家政服务'],
      workTime: util.formatDate(new Date()) + ' - ' + util.formatDateAfterMonth(util.formatDate(new Date()), 1),
      orderType: '0',
    },
  },
  bindAgreeChange: function (e) {
    this.setData({
      isAgree: e.detail.value.length,
    })
    if (e.detail.value.length == 1) {
      this.setData({
        btn_disabled: false,
      })
    } else {
      //onsole.log(e.detail.value.length)
      this.setData({
        btn_disabled: true
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    that.data.order.consumerId = wx.getStorageSync('user').consumerId
    that.data.type.id = e.typeId
    that.data.type.name = e.typeName
  },
})