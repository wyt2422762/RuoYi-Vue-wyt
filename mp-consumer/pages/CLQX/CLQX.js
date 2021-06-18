// 家政 - 窗帘清洗

const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')
let iView = require('../../utils/iViewUtil.js')

import {
  service,
  allReq
} from '../../utils/request.js'
import {
  getDicts,
  getDictValueByLabel
} from '../../utils/dict.js'

Page({
  data: {
    //loading
    hiddenLoading: true,
    //项目选择是否显示
    showWorks: false,
    type: {
      id: null,
      name: null
    },
    btn_disabled: true,
    //居室面积字典
    sizeOptions: [],
    sizeStr: "",
    sizeValue: null,
    sizeIndex: null,
    //窗帘厚度字典
    thicknessOptions: [],
    thicknessIndex: null,
    //选择的厚度(文字)
    thicknessStr: '',
    //选择的厚度(值)
    thickness_value: null,
    //收费标准
    standardMap: {},
    //服务时间
    time: util.formatDate(new Date()),
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
    //获取卫生清洁项目字典，居室面积字典，收费标准
    allReq([getDicts('bus_housekeeping_thickness'), getDicts('bus_living_size'), service.get('/standard/housekeeping/cl', {})]).then(res => {
      that.setData({
        thicknessOptions: res[0].data,
        sizeOptions: res[1].data,
        standardMap: res[2].data.map
      })
    })
  },
  //厚度变化
  thicknessChange(e){
    let that = this
    that.setData({
      thicknessIndex: e.detail.value,
      thickness_value: that.data.thicknessOptions[e.detail.value].dictValue,
      thicknessStr: that.data.thicknessOptions[e.detail.value].dictLabel
    }, function(){
      that.calacMoney()
    })
  },
  //居室面积变化
  sizeChange(e) {
    let that = this
    that.setData({
      sizeIndex: e.detail.value,
      sizeValue: that.data.sizeOptions[e.detail.value].dictValue,
      sizeStr: that.data.sizeOptions[e.detail.value].dictLabel
    }, function(){
      that.calacMoney()
    })
  },
  //服务时间变化
  timeChange(e) {
    let that = this
    that.setData({
      time: e.detail.value,
      'order.workTime': e.detail.value
    })
  },
  //地址变化
  addrChange(e) {
    this.setData({
      'order.addr': e.detail.value
    })
  },
  //计算钱
  calacMoney() {
    let that = this
    let size = that.data.sizeValue
    let thickness = that.data.thickness_value

    let money = null
    if(size && thickness){
      money = 0
      let key = size + '-' + thickness
      let std = that.data.standardMap[key]
      if(std){
        money += (that.data.standardMap[key].money - 0)
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
    //项目
    if(that.data.thicknessStr && that.data.thicknessStr.length > 0){
      let mta = {
        label: '窗帘厚度',
        data: that.data.thicknessStr
      }
      meta.push(mta)
    }
    //居室面积
    if(that.data.sizeStr && that.data.sizeStr.length > 0){
      let mta = {
        label: '居室面积',
        data: that.data.sizeStr
      }
      meta.push(mta)
    }
    //添加meta信息
    if(meta && meta.length > 0){
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