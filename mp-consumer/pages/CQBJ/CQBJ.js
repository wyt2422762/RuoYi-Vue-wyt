// 长期保洁
const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')
let iView = require('../../utils/iViewUtil.js')

import {
  service,
  allReq
} from '../../utils/request.js'
import {
  getDicts
} from '../../utils/dict.js'

Page({
  data: {
    //loading
    hiddenLoading: true,
    type: {
      id: null,
      name: null
    },
    btn_disabled: true,
    //服务时长字典
    durationOptions: [],
    durationStr: "",
    durationValue: null,
    durationIndex: null,
    //服务频率字典
    frequencyOptions: [],
    frequencyStr: "",
    frequencyValue: null,
    frequencyIndex: null,

    //收费标准
    standardMap: {},
    nowTime: util.formatDate(new Date()),
    //服务时间
    startTime: util.formatDate(new Date()),
    endTime: util.formatDateAfterMonth(util.formatDate(new Date()), 1),
    //订单
    order: {
      consumerId: null,
      type: config.orderType['家政服务'],
      workTime: util.formatDate(new Date()),
      orderType: '2',
      money: null
    },
    //护工信息
    nurse: {}
  },
  //同意协议变化
  bindAgreeChange: function (e) {
    this.setData({
      isAgree: e.detail.value.length,
    })
    if (e.detail.value.length == 1) {
      this.setData({
        btn_disabled: false,
      })
    } else {
      this.setData({
        btn_disabled: true
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(e) {
    let that = this
    that.data.order.consumerId = wx.getStorageSync('user').consumerId
    that.data.type.id = e.typeId
    that.data.type.name = e.typeName
    //获取保洁时长，保洁频率字典，收费标准
    allReq([getDicts('bus_clean_time'), getDicts('bus_clean_frequency'), service.get('/standard/housekeeping/clean', {})]).then(res => {
      that.setData({
        durationOptions: res[0].data,
        frequencyOptions: res[1].data,
        standardMap: res[2].data.map
      })
    })
  },
  //服务时间变化
  timeChange(e) {
    let that = this
    let ed = util.formatDateAfterMonth(e.detail.value, 1)
    that.setData({
      startTime: e.detail.value,
      endTime: ed,
      'order.workTime': e.detail.value + ' - ' + ed
    })
  },
  //地址变化
  addrChange(e) {
    this.setData({
      'order.addr': e.detail.value
    })
  },
  //服务时长变化
  durationChange(e) {
    let that = this
    that.setData({
      durationIndex: e.detail.value,
      durationValue: that.data.durationOptions[e.detail.value].dictValue,
      durationStr: that.data.durationOptions[e.detail.value].dictLabel
    }, function () {
      that.calacMoney()
    })
  },
  //服务频率变化
  frequencyChange(e) {
    let that = this
    that.setData({
      frequencyIndex: e.detail.value,
      frequencyValue: that.data.frequencyOptions[e.detail.value].dictValue,
      frequencyStr: that.data.frequencyOptions[e.detail.value].dictLabel
    }, function () {
      that.calacMoney()
    })
  },
  //计算钱
  calacMoney() {
    let that = this
    //时长
    let duration = that.data.durationValue
    //频率
    let frequency = that.data.frequencyValue
    //几个月
    let month = 1

    let money = null
    if (duration && frequency) {
      money = 0
      let key = frequency + '-' + duration
      let std = that.data.standardMap[key]
      if (std) {
        money += (that.data.standardMap[key].money - 0) * month
      } else {
        money = null
      }
    }
    that.setData({
      'order.money': money
    })
  },
  //提交订单
  submitOrder(e) {
    let that = this
    //参数检查
    if (!that.checkOrderParam()) {
      return false
    }
    //参数检查通过，提交订单

    //这里处理meta内容
    let meta = []
    //保洁时长
    if (that.data.durationStr && that.data.durationStr.length > 0) {
      let mta = {
        label: '保洁时长',
        data: that.data.durationStr
      }
      meta.push(mta)
    }
    //保洁频率
    if (that.data.frequencyStr && that.data.frequencyStr.length > 0) {
      let mta = {
        label: '保洁频率',
        data: that.data.frequencyStr
      }
      meta.push(mta)
    }
    //添加meta信息
    if (meta && meta.length > 0) {
      that.data.order.meta = meta
    }

    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.post('/order', {
      data: that.data.order
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      wx.redirectTo({
        url: '../QBDD/QBDD',
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      iView.toast.error('提交失败')
    })
  },
  //参数检查
  checkOrderParam() {
    let that = this
    let order = that.data.order
    if (!order['consumerId']) {
      iView.toast.warning('客户id不能为空')
      return false
    } else if (!order['nurseId']) {
      iView.toast.warning('请选择护工')
      return false
    } else if (!order['addr']) {
      iView.toast.warning('请选择地址')
      return false
    } else if (!order['money']) {
      iView.toast.warning('请选择服务项目')
      return false
    }
    return true
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  },
})