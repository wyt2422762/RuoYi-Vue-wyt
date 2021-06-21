import {
  getDicts
} from '../../utils/dict.js'
import {
  service,
  allReq
} from '../../utils/request.js'

let iView = require('../../utils/iViewUtil.js')

Page({
  data: {
    //loading
    hiddenLoading: true,
    //选项卡index
    currentTab: '0',
    //订单类型字典
    orderTypeOptions: [],
    //订单状态字典
    orderStatusOptions: [],
    //进行中list
    orderList_jxz: [],
    // 查询参数(进行中)
    queryParams_jxz: {
      pageNum: 1,
      pageSize: 5,
      orderByColumn: 'create_time',
      isAsc: 'desc',
      nurseId: null,
      status: 0
    },
    //分页参数(进行中)
    page_jxz: {
      //总条数
      total: null,
      //总页数
      pages: null
    },

  },
  onLoad: function (e) {
    let that = this
    allReq([getDicts("bus_order_type"), getDicts("bus_order_status")]).then(res => {
      that.data.orderTypeOptions = res[0].data
      that.data.orderStatusOptions = res[1].data
    })
  },
  onShow() {
    let that = this
    that.data.queryParams_jxz.nurseId = wx.getStorageSync('user').nurseId
    that.data.queryParams_jxz.pageNum = 1
    //查询订单列表
    that.getOrderList_jxz()
  },
  //获取进行中订单
  getOrderList_jxz() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/order/list', {
      data: that.data.queryParams_jxz
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      that.setData({
        orderList_jxz: res.rows,
        'page_jxz.total': res.total
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  //选项卡切换
  tabChange(e) {
    this.setData({
      currentTab: e.detail.key
    })
  },
  swiperChange(e) {
    let that = this
    that.setData({
      currentTab: e.detail.current
    })
  },
})