// pages/ZLLR/ZLLR.js

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
    personNum: null,
    personNumIndex: 0,
    pNum: null,
    dayTime: null,
    dayTimeIndex: 0,
    dat: null,
    //押金
    deposit: null,
    btn_disabled: true,
    //居家陪护收费标准
    homeCareStandardMap: {},
    nowDate: util.formatDate(new Date()),
    startDate: util.formatDate(new Date()),
    endDate: util.formatDateAfterMonth(util.formatDate(new Date()), 1),
    //订单
    order: {
      consumerId: null,
      type: config.orderType['居家陪护'],
      workTime: util.formatDate(new Date()) + ' - ' + util.formatDateAfterMonth(util.formatDate(new Date()), 1),
      orderType: '0',
    },
    //护工信息
    nurse: {},
    //额外服务
    extraList: [],
    extraMap: {},
    extras: [],
    extraMoney: 0
  },
  //额外服务变化
  extraChange(e) {
    let that = this
    let index = that.data.extras.indexOf(e.detail.value)
    index === -1 ? that.data.extras.push(e.detail.value) : that.data.extras.splice(index, 1)
    that.setData({
      extras: that.data.extras
    })
    that.calcMoney()
  },
  //日期变化
  bindDateChange: function (e) {
    let that = this
    let ed = util.formatDateAfterMonth(e.detail.value, 1)
    that.setData({
      startDate: e.detail.value,
      endDate: ed,
      'order.workTime': e.detail.value + ' - ' + ed
    })
  },
  //人数变化
  bindPersonNumChange: function (e) {
    let that = this
    that.setData({
      personNumIndex: e.detail.value,
      pNum: that.data.personNum[e.detail.value].dictValue - 0
    })
    that.calcMoney()
  },
  //每日陪护时间变化
  dayTimeChange: function (e) {
    let that = this
    this.setData({
      dayTimeIndex: e.detail.value,
      dat: that.data.dayTime[e.detail.value].dictValue - 0
    })
    that.calcMoney()
  },
  //地址变化
  addrChange(e) {
    this.setData({
      'order.addr': e.detail.value
    })
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
  onLoad: function (e) {
    let that = this
    that.setData({
      'order.consumerId': wx.getStorageSync('user').consumerId,
      'type.id': e.typeId,
      'type.name': e.typeName
    })
    //1. 修改标题
    wx.setNavigationBarTitle({
      title: e.typeName
    })
    //2. 获取各种人数字典、每日小时数自带字典，收费标准，额外服务
    allReq([getDicts("bus_person_num"), getDicts("bus_hourPerDay"), service.get('/standard/homeCare', {}), service.get('/standard/extras', {})]).then(res => {
      that.setData({
        personNum: res[0].data,
        pNum: res[0].data[0].dictValue - 0,

        dayTime: res[1].data,
        dat: res[1].data[0].dictValue - 0,

        homeCareStandardMap: res[2].data,

        extraList: res[3].data.list,
        extraMap: res[3].data.map
      }, function () {
        //计算钱
        debugger
        that.calcMoney()
      })
    })
  },
  //计算金额
  calcMoney() {
    let that = this
    //首先获取对应的收费标准
    let key = that.data.type.id + '-' + that.data.pNum + '-' + that.data.dat

    let stantard = that.data.homeCareStandardMap[key]
    if (stantard) {
      //押金
      let deposit = stantard.deposit
      //金额
      let money = that.calc(stantard, 1)
      that.setData({
        'order.money': money,
        deposit: deposit
      })
    } else {
      that.setData({
        'order.money': null,
        deposit: null
      })
    }
  },
  //计算钱
  calc(standard, month) {
    let that = this
    //额外服务
    let extraMoney = 0
    if(that.data.extras && that.data.extras.length > 0){
      for(let i = 0; i < that.data.extras.length; i++){
        extraMoney += (that.data.extraMap[that.data.extras[i]].money - 0)
      }
    }
    return (standard.money == null ? 0 : standard.money) * month + (standard.deposit == null ? 0 : standard.deposit) + extraMoney
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  },
  //提交订单
  submitOrder() {
    let that = this
    //处理人数
    if (that.data.personNum[that.data.personNumIndex]) {
      that.data.order.personNum = that.data.personNum[that.data.personNumIndex]
    }
    //处理额外服务
    if (that.data.extras && that.data.extras.length > 0) {
      that.data.order.extra = that.data.extras
    }
    //参数检查
    if (!that.checkOrderParam()) {
      return false
    }
    //参数检查通过，提交订单

    //这里处理meta内容
    let meta = []
    //服务类型
    if(that.data.type.name){
      let mta = {
        label: '服务类型',
        data: that.data.type.name
      }
      meta.push(mta)
    }
    //人数
    if (that.data.personNum[that.data.personNumIndex]) {
      let personNum = that.data.personNum[that.data.personNumIndex].dictValue
      let mta = {
        label: '人数',
        data: personNum
      }
      meta.push(mta)
    }
    //处理额外服务
    if (that.data.extras && that.data.extras.length > 0) {
      let extra = that.data.extras
      let mta = {
        label: '额外服务',
        data: extra.join(';')
      }
      meta.push(mta)
    }
    //添加额外服务信息
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
  }
})