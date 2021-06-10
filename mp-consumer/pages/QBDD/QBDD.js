// 订单列表页

import {
  getDicts
} from '../../utils/dict.js'
import {
  service
} from '../../utils/request.js'

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
    //获取订单类型字典
    getDicts("bus_order_type").then(res => {
      that.orderTypeOptions = res.data;
    })
    //获取订单状态字典
    getDicts("bus_order_status").then(res => {
      that.orderStatusOptions = res.data;
    })
    //查询订单列表
    that.getList()
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    //模拟加载
    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    let that = this
    that.more()
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
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
        wx.showToast({
          title: '没有更多数据了!~',
          icon: 'none'
        })
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