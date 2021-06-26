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
    //订单列表(进行中)
    orderList_jxz: [],
    // 查询参数(进行中)
    queryParams_jxz: {
      pageNum: 1,
      pageSize: 5,
      orderByColumn: 'create_time',
      isAsc: 'desc',
      nurseId: null,
      status: 3
    },
    //分页参数(进行中)
    page_jxz: {
      //总条数
      total: null,
      //总页数
      pages: null
    },

    //订单列表(已完成)
    orderList_ywc: [],
    // 查询参数(已完成)
    queryParams_ywc: {
      pageNum: 1,
      pageSize: 5,
      orderByColumn: 'create_time',
      isAsc: 'desc',
      nurseId: null,
      status: 4
    },
    //分页参数(已完成)
    page_ywc: {
      //总条数
      total: null,
      //总页数
      pages: null
    },
  },
  onLoad(e) {
    let that = this
    allReq([getDicts("bus_order_type"), getDicts("bus_order_status")]).then(res => {
      that.data.orderTypeOptions = res[0].data
      that.data.orderStatusOptions = res[1].data
    })
  },
  onShow() {
    let that = this
    let nurseId = wx.getStorageSync('user').nurseId
    that.data.queryParams_jxz.nurseId = nurseId
    that.data.queryParams_jxz.pageNum = 1
    that.data.queryParams_ywc.nurseId = nurseId
    that.data.queryParams_ywc.pageNum = 1
    //查询订单列表
    that.getOrderList()
  },
  //获取订单列表(进行中已完成)
  getOrderList() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    allReq([service.get('/order/list', {
      data: that.data.queryParams_jxz
    }), service.get('/order/list', {
      data: that.data.queryParams_ywc
    })]).then(res => {
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        orderList_jxz: res[0].rows,
        'page_jxz.total': res[0].total,
        orderList_ywc: res[1].rows,
        'page_ywc.total': res[1].total,
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
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
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
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
  //获取已完成订单
  getOrderList_ywc() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    service.get('/order/list', {
      data: that.data.queryParams_ywc
    }).then(res => {
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        orderList_ywc: res.rows,
        'page_ywc.total': res.total
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
    let currentTab = that.data.currentTab
    let queryParams = null
    let orderList = null
    if (currentTab == '0') {
      //进行中
      queryParams = that.data.queryParams_jxz
      orderList = that.data.orderList_jxz
    } else if (currentTab == '1') {
      //已完成
      queryParams = that.data.queryParams_ywc
      orderList = that.data.orderList_ywc
    }
    if (queryParams) {
      queryParams.pageNum = queryParams.pageNum + 1
      // 暂存数据
      let listBefore = orderList
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
      //请求数据
      service.get('/order/list', {
        data: queryParams
      }).then(res => {
        //判断有无数据
        if (res.rows.length <= 0) {
          that.setData({
            hiddenLoading: !that.data.hiddenLoading
          })
          queryParams.pageNum = queryParams.pageNum - 1
          iView.toast.warning('没有更多数据了!~')
        } else {
          if (currentTab == '0') {
            that.setData({
              hiddenLoading: !that.data.hiddenLoading,
              orderList_jxz: listBefore.concat(res.rows),
              'page.total': res.total,
            })
          } else if (currentTab == '1') {
            that.setData({
              hiddenLoading: !that.data.hiddenLoading,
              orderList_jxz: listBefore.concat(res.rows),
              'page.total': res.total,
            })
          }
        }
      }).catch(err => {
        //loading
        that.setData({
          hiddenLoading: !that.data.hiddenLoading
        })
      })
    }
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
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    this.more()
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