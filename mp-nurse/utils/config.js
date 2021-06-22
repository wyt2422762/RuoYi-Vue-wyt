const appId = 'wxe8fb45f5ee29f354'

const config = {
  //appid
  appid: appId,
  //后台基础路径
  //backBaseUrl: 'http://localhost:8080',
  backBaseUrl: 'https://fdxmgl.fd.com.cn:8443/web',
  //后台基础路径接口
  //apiBaseUrl: 'http://localhost:8082/wx/' + appId,
  apiBaseUrl: 'https://fdxmgl.fd.com.cn:8443/api/wx/' + appId,
  //订单类型
  orderType: {
    '居家陪护': '0',
    '医院陪护': '1',
    '家政服务': '2'
  },
  //客服电话
  phone_cs: '12344456993',
  //支付相关
  wxPay: {
    body: '夕阳红',
    //spbillCreateIp
    spbillCreateIp: '127.0.0.1',
    //tradeType
    tradeType: 'JSAPI',
    //notifyUrl
    //notifyUrl: 'http://localhost:8082/wx/' + appId + '/pay/notify/order'
    notifyUrl: 'https://fdxmgl.fd.com.cn:8443/api/wx/' + appId + '/pay/notify/order'
  }
}

module.exports = config

