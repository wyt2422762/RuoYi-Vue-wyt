// pages/ZLLR/ZLLR.js

const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')
let iView = require('../../utils/iViewUtil.js')

import {
  service
} from '../../utils/request.js'

Page({
  data: {
    type:{
      id: null,
      name: null
    },
    personNum: ["1", "2"],
    personNumIndex: 0,
    pNum: 1,
    dayTime: ["12", "24"],
    dayTimeIndex: 0,
    dat: 12,
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
    nurse: {}
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
      pNum: that.data.personNum[e.detail.value]
    })
    that.calcMoney()
  },
  //每日陪护时间变化
  dayTimeChange: function (e) {
    let that = this
    this.setData({
      dayTimeIndex: e.detail.value,
      dat: that.data.dayTime[e.detail.value]
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
    that.data.order.consumerId = wx.getStorageSync('user').consumerId
    that.data.type.id = e.typeId
    that.data.type.name = e.typeName
    //1. 获取收费标准
    that.getHomeCareStandard()
    //2. 修改标题
    wx.setNavigationBarTitle({
      title: e.typeName
    })
  },
  //获取居家陪护收费标准
  getHomeCareStandard() {
    let that = this
    service.get('/standard/homeCare', {}).then(res => {
      that.data.homeCareStandardMap = res.data
      that.calcMoney()
    })
  },
  //计算金额
  calcMoney() {
    let that = this
    //首先获取对应的收费标准
    let key = that.data.type.id + '-' + that.data.pNum + '-' + that.data.dat
    let stantard = that.data.homeCareStandardMap[key]
    //押金
    let deposit = stantard.deposit
    //金额
    let money = that.calc(stantard, 1)
    that.setData({
      'order.money': money,
      deposit: deposit
    })
  },
  //计算钱
  calc(standard, month) {
    return (standard.money == null ? 0 : standard.money) * month + (standard.deposit == null ? 0 : standard.deposit);
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  },
  //提交订单
  submitOrder() {
    let that = this
    if(!that.checkOrderParam()){
      return false
    }
    //参数检查通过，提交订单
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
      wx.navigateTo({
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
    if(!order['consumerId']){
      iView.toast.warning('客户id不能为空')
      return false
    } else if(!order['nurseId']){
      iView.toast.warning('请选择护工')
      return false
    } else if(!order['addr']){
      iView.toast.warning('请选择地址')
      return false
    } else if(!order['money']){
      iView.toast.warning('请选择服务项目')
      return false
    }
    return true
  }
})