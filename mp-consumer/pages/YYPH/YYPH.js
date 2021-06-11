// 居家陪护
let gto = require('../../utils/goto.js')

import {
  getDicts
} from '../../utils/dict.js'

//获取应用实例
const app = getApp()

Page({
  data: {
    typeList: []
  },
  onLoad() {
    let that = this
    //获取医院陪护类型
    //获取订单类型字典
    getDicts("bus_type_hospitalcare").then(res => {
      that.setData({
        typeList: res.data
      })
    })
  },
  goto(e) {
    let url = e.currentTarget.dataset.url
    let typeid = e.currentTarget.dataset.typeid
    let typename = e.currentTarget.dataset.typename
    url = url + "?typeId=" + typeid + "&typeName=" + typename
    gto.gotoIfLogin(url)
  },
})