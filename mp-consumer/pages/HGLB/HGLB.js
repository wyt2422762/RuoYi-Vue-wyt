//index.js

const config = require("../../utils/config.js")
let util = require("../../utils/util.js")
let gto = require('../../utils/goto.js')
let iView = require('../../utils/iViewUtil.js')

import {
  service
} from '../../utils/request.js'

//获取应用实例
const app = getApp()

Page({
  data: {
    //loading
    hiddenLoading: true,
    //加载更多
    more: {
      loadText: '加载更多',
      loading: false
    },
    // 查询参数
    queryParams: {
      pageNum: 1,
      pageSize: 5,
      status: '0',
      // orderByColumn: 'create_time',
      // isAsc: 'desc',
    },
    //分页参数
    page: {
      //总条数
      total: null,
      //总页数
      pages: null
    },
    //护工列表
    nurseList: [],
    //头像
    avatarBaseUrl: config.backBaseUrl
  },
  onLoad() {
    let that = this
    that.getList()
  },
  //获取护工列表
  getList() {
    let that = this
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    //请求数据
    service.get('/nurse/list', {
      data: that.data.queryParams
    }).then(res => {
      that.setData({
        hiddenLoading: !that.data.hiddenLoading,
        nurseList: res.rows,
        'page.total': res.total
      })
    }).catch(err => {
      //loading
      that.setData({
        hiddenLoading: !that.data.hiddenLoading
      })
    })
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    let that = this
    that.more()
  },
  //加载更多
  more() {
    let that = this
    that.data.queryParams.pageNum = that.data.queryParams.pageNum + 1
    // 暂存数据
    let listBefore = that.data.nurseList;
    //loading
    that.setData({
      hiddenLoading: !that.data.hiddenLoading
    })
    //请求数据
    service.get('/nurse/list', {
      data: that.data.queryParams
    }).then(res => {
      //判断有无数据
      if (res.rows.length <= 0) {
        that.setData({
          hiddenLoading: !that.data.hiddenLoading
        })
        that.data.queryParams.pageNum = that.data.queryParams.pageNum - 1
        iView.toast.warning('没有更多数据了')
      } else {
        that.setData({
          hiddenLoading: !that.data.hiddenLoading,
          nurseList: listBefore.concat(res.rows),
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
})