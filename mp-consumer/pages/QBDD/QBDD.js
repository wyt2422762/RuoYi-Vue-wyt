// 订单列表页

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
    //订单类型字典
    orderTypeOptions: [],
    //订单状态字典
    orderStatusOptions: [],
    //加载更多
    more: {
      loadText: '加载更多',
      loading: false
    },
    // 查询参数
    queryParams: {
      pageNum: 1,
      pageSize: 5,
      orderByColumn: 'create_time',
      isAsc: 'desc',
      consumerId: null,
    },
    // 订单表格数据
    orderList: [],
    //分页参数
    page: {
      //总条数
      total: null,
      //总页数
      pages: null
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    that.data.queryParams.consumerId = wx.getStorageSync('user').consumerId
    allReq([getDicts("bus_order_type"), getDicts("bus_order_status")]).then(res => {
      that.data.orderTypeOptions = res[0].data
      that.data.orderStatusOptions = res[1].data
    })
  },
  onShow() {
    let that = this
    that.data.queryParams.pageNum = 1
    //查询订单列表
    that.getList()
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    let that = this
    that.more()
  },
  //查询订单列表
  getList() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/order/list', {
      data: that.data.queryParams
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      that.setData({
        orderList: res.rows,
        'page.total': res.total
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  //加载更多
  more() {
    let that = this
    that.data.queryParams.pageNum = that.data.queryParams.pageNum + 1
    // 暂存数据
    let listBefore = that.data.orderList;
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    //请求数据
    service.get('/order/list', {
      data: that.data.queryParams
    }).then(res => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      //判断有无数据
      if (res.rows.length <= 0) {
        that.data.queryParams.pageNum = that.data.queryParams.pageNum - 1
        iView.toast.warning('没有更多数据了!~')
      } else {
        that.setData({
          orderList: listBefore.concat(res.rows),
          'page.total': res.total,
        })
      }
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  // 订单类型处理
  parseOrderType(value) {
    let that = this
    for (let wlo in that.orderTypeOptions) {
      if (that.orderTypeOptions[wlo].dictValue === value) {
        return that.orderTypeOptions[wlo].dictLabel;
      }
    }
  },
  // 订单状态处理
  parseOrderStatus(value) {
    let that = this
    for (let wlo in that.orderStatusOptions) {
      if (that.orderStatusOptions[wlo].dictValue === value) {
        return that.orderStatusOptions[wlo].dictLabel;
      }
    }
  },
})