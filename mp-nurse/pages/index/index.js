let iView = require('../../utils/iViewUtil.js')

import {
    service
} from '../../utils/request.js'

Page({
    /**
     * 页面的初始数据
     */
    data: {
        addmissage: '选的位置',
        // markers	 Array	标记点
        stitle: '',
        latitude: "",
        longitude: "",
        scale: 14,
        markers: [{
            iconPath: '../../img/1.3.png',
            id: 0,
            latitude: 37.72459174262153,
            longitude: 112.72847547743055,
            width: 50,
            height: 50
        }],
        polyline: [{
            points: [],
            color: "#FF0000DD",
            width: 2,
            dottedLine: true,
            arrowLine: true
        }],
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
     * 生命周期函数--监听页面显示
     */
    onLoad(e) {
        let that = this
        //开启后台定位
        wx.startLocationUpdateBackground({
            success(res) {
                console.log('开启后台定位成功', res)
            },
            fail(err) {
                console.log('开启后台定位失败', err)
                iView.toast.warning('请转到设置位置信息[使用小程序期间和离开小程序后], 并点击重新进入小程序')
            }
        })
        //获取当前的地理位置、速度
        wx.getLocation({
            type: 'gcj02', // 默认为 wgs84 返回 gps 坐标，gcj02 返回可用于 wx.openLocation 的坐标
            success: function (res) {
                //赋值经纬度
                that.setData({
                    latitude: res.latitude,
                    longitude: res.longitude,
                    markers: [{
                        id: 1,
                        iconPath: '../../img/1.3.png',
                        longitude: res.longitude, // 经度
                        latitude: res.latitude, //纬度
                        width: 32,
                        height: 32
                    }]
                })
            }
        })
        //位置变化
        wx.onLocationChange(res => {
            if (that.data.longitude !== res.longitude || that.data.latitude !== res.latitude) {
                //上报位置信息
                that.uploadLocation(res.longitude, res.latitude)
                that.setData({
                    longitude: res.longitude, // 经度
                    latitude: res.latitude, //纬度
                    markers: [{
                        id: 1,
                        iconPath: '../../img/1.3.png',
                        longitude: res.longitude, // 经度
                        latitude: res.latitude, //纬度
                        width: 32,
                        height: 32
                    }]
                })
            }
        })
    },
    //上报位置
    uploadLocation(longitude, latitude) {
        let that = this
        let token = wx.getStorageSync('token')
        //登录后上报
        if (!token) {
            return false
        }
        let user = wx.getStorageSync('user')
        service.post('/nurse/position', {
            data: {
                lng: longitude,
                lat: latitude,
                nurseId: user.nurseId
            }
        }).catch(err => {
            console.log('上报位置失败', err)
        })
    }
})