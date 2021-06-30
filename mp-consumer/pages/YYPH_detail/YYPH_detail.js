Page({
  data: {
    type: {
      id: null,
      name: null
    },
  },
  onLoad(e) {
    let that = this
    that.setData({
      'type.id': e.typeId,
      'type.name': e.typeName
    })
    //修改标题
    wx.setNavigationBarTitle({
      title: e.typeName
    })
  }
})