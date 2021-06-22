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
    dat: 12,
    //押金
    deposit: null,
    //餐费(总)
    mealSum: null,
    btn_disabled: true,
    //居家陪护收费标准
    hospitalCareStandardMap: {},
    nowDate: util.formatDate(new Date()),
    startDate: util.formatDate(new Date()),
    endDate: util.formatDate(new Date()),
    //订单
    order: {
      consumerId: null,
      type: config.orderType['医院陪护'],
      workTime: util.formatDate(new Date()) + ' - ' + util.formatDate(new Date()),
      orderType: '1',
    },
    //护工信息
    nurse: {}
  },
  //开始日期变化
  bindStartDateChange: function (e) {
    let that = this
    let ed = that.data.endDate < e.detail.value ? that.data.endDate : e.detail.value
    that.setData({
      startDate: e.detail.value,
      endDate: ed,
      'order.workTime': e.detail.value + ' - ' + ed
    })
    that.calcMoney()
  },
  //结束日期变化
  bindEndDateChange: function (e) {
    let that = this
    if (e.detail.value < that.data.startDate) {
      e.detail.value = that.data.startDate
    }
    that.setData({
      endDate: e.detail.value,
      'order.workTime': that.data.startDate + ' - ' + e.detail.value
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
    //2. 获取各种人数字典、每日小时数自带字典，收费标准
    allReq([getDicts("bus_person_num"), getDicts("bus_hourPerDay"), service.get('/standard/hospitalCare', {})]).then(res => {
      that.setData({
        personNum: res[0].data,
        pNum: res[0].data[0].dictValue - 0,

        dayTime: res[1].data,
        dat: res[1].data[0].dictValue - 0,

        hospitalCareStandardMap: res[2].data,
      }, function () {
        //计算钱
        that.calcMoney()
      })
    })
  },
  //计算金额
  calcMoney() {
    let that = this
    //首先获取对应的收费标准
    let key = that.data.type.id + '-' + that.data.dat
    let stantard = that.data.hospitalCareStandardMap[key]
    if (stantard) {
      //押金
      let deposit = stantard.deposit
      //餐费(每天)
      let meal = stantard.meal
      //天数
      let days = util.getDaysBetween(that.data.startDate, that.data.endDate)
      //金额
      let money = that.calc(stantard, days)
      that.setData({
        'order.money': money,
        deposit: deposit,
        mealSum: meal * days
      })
    } else {
      that.setData({
        'order.money': null,
        deposit: null,
        mealSum: null
      })
    }
  },
  //计算钱
  calc(standard, days) {
    return (standard.money == null ? 0 : standard.money) * days +
      (standard.meal == null ? 0 : standard.meal) * days +
      (standard.deposit == null ? 0 : standard.deposit);
  },
  //跳转
  goto(e) {
    let url = e.currentTarget.dataset.url
    gto.gotoIfLogin(url)
  },
  //提交订单
  submitOrder() {
    let that = this
    if (!that.checkOrderParam()) {
      return false
    }
    //参数检查通过，提交订单

    //处理meta
    let meta = []
    //服务类型
    if(that.data.type.name){
      let mta = {
        label: '服务类型',
        data: that.data.type.name
      }
      meta.push(mta)
    }
    //每日小时数
    if (that.data.dat) {
      let mta = {
        label: '每日小时数',
        data: that.data.dat
      }
      meta.push(mta)
    }
    //餐费
    if (that.data.mealSum) {
      let mta = {
        label: '餐费',
        data: that.data.mealSum
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