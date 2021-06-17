// 家政 - 卫生清洁

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
    //项目字典
    workOptions: [],
    //选择的服务项(文字)
    works_label: [],
    works_labelStr: '',
    //选择的服务项(值)
    works_value: [],
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
      //console.log(e.detail.value.length)
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
    allReq([getDicts('bus_housekeeping_ws'), getDicts('bus_living_size'), service.get('/standard/housekeeping/ws', {})]).then(res => {
      that.setData({
        workOptions: res[0].data,
        sizeOptions: res[1].data,
        standardMap: res[2].data.map
      })
    })
  },
  //打开服务项目选择
  worksOpen() {
    let that = this
    that.setData({
      showWorks: true
    })
  },
  //关闭服务项目选择
  worksClose() {
    let that = this
    that.setData({
      showWorks: false
    })
  },
  //服务项目变化
  worksChange(e) {
    let that = this
    let index = that.data.works_label.indexOf(e.detail.value)
    index === -1 ? that.data.works_label.push(e.detail.value) : that.data.works_label.splice(index, 1)
    index === -1 ? that.data.works_value.push(getDictValueByLabel(that.data.workOptions, e.detail.value)) : that.data.works_value.splice(index, 1)
    that.setData({
      works_value: that.data.works_value,
      works_label: that.data.works_label,
      works_labelStr: that.data.works_label.join(';')
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
    let works = that.data.works_value
    let size = that.data.sizeValue

    let money = null
    if(works && works.length > 0 && size){
      money = 0
      for(let i = 0;i < works.length; i++){
        let key = size + '-' + works[i]
        let std = that.data.standardMap[key]
        if(std){
          money += (that.data.standardMap[key].money - 0)
        } else {
          money = null
          break
        }
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
    if(that.data.works_labelStr && that.data.works_labelStr.length > 0){
      let mta = {
        label: '项目',
        data: that.data.works_labelStr
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