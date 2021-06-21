Page({

    /**
     * 页面的初始数据
     */
    data: {
        addmissage: '选的位置',
        // markers	 Array	标记点
        stitle: '故宫',
        latitude: "",
        longitude: "",
        scale: 14,
        markers: [],
        //controls控件 是左下角圆圈小图标,用户无论放大多少,点这里可以立刻回到当前定位(控件（更新一下,即将废弃，建议使用 cover-view 代替）)
        controls: [{
            id: 1,
            iconPath: '../../images/img/controls.png',
            position: {
                left: 15,
                top: 260 - 50,
                width: 40,
                height: 40
            },
            clickable: true
        }],
        distanceArr: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onShow: function (options) {
        let that = this
        //获取当前的地理位置、速度
        wx.getLocation({
            type: 'wgs84', // 默认为 wgs84 返回 gps 坐标，gcj02 返回可用于 wx.openLocation 的坐标
            success: function (res) {
                //赋值经纬度
                that.setData({
                    latitude: res.latitude,
                    longitude: res.longitude,

                })
            }
        })
    },
})