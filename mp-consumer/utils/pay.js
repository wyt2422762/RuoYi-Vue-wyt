//微信支付
import { service } from "./request"
const config = require("./config.js")

/**
 * 支付方法
 * 步骤：
 * 1. 调用统一下单接口，并组装生成支付所需参数对象.
 * 2. 利用第1步返回的数据调用微信的支付接口
 * 
 * @param {订单号} orderNo
 * @param {金额(元)} money
 * @param {支付成功回调} successhandler
 * @param {支付失败回调} failHandler
 */
export function pay(orderNo, money, successhandler, failHandler) {
  let app = getApp()
  service.post('/pay/createOrder', {
    data: {
      //openid
      openid: wx.getStorageSync('openid'),
      //金额
      totalFee: money * 100, //微信支付的单位是分
      //totalFee: 1, //微信支付的单位是分
      //订单号
      outTradeNo: orderNo,
      //body
      body: config.wxPay.body,
      //spbillCreateIp
      spbillCreateIp: config.wxPay.spbillCreateIp,
      //tradeType
      tradeType: config.wxPay.tradeType,
      //notifyUrl
      notifyUrl: config.wxPay.notifyUrl
    }
  }).then(res => {
    console.log('统一支付返回成功，接下来调用支付接口')
    if (res) {
      //唤起微信支付
      wx.requestPayment({
        timeStamp: res.timeStamp,
        nonceStr: res.nonceStr,
        package: res.packageValue,
        paySign: res.paySign,
        signType: res.signType,
        appId: res.appId,
        //支付成功回调
        success(payRes) {
          console.log('支付成功', payRes)
          if(successhandler && typeof successhandler == 'function'){
            successhandler(payRes)
          }
        },
        //支付失败回调
        fail(payFail) {
          console.log('支付失败', payFail)
          //调用关闭订单接口
          //closeOrder(orderNo)
          if(failHandler && typeof failHandler == 'function'){
            failHandler(payFail)
          }
        }
      })
    }
  }).catch(err => {
    if(failHandler && typeof failHandler == 'function'){
      failHandler(err)
    }
    return false
  })
}

//关闭订单方法
function closeOrder(orderNo) {
  service.get('/pay/closeOrder/' + orderNo, { 
  }).then(res => {
    console.log('关闭订单成功')
  }).catch(err => {
    console.log('关闭订单失败')
  })
}