const appId = 'wx46ec2c2610591c41'

const config = {
  //appid
  appid: appId,
  //后台基础路径
  //backBaseUrl: 'http://127.0.0.1:8080',
  backBaseUrl: 'https://fdxmgl.fd.com.cn:8443/web',

  //后台基础路径接口
  apiBaseUrl: 'https://fdxmgl.fd.com.cn:8443/api/wx/' + appId,
  //apiBaseUrl: 'http://127.0.0.1:8082/wx/' + appId,
  //订单类型
  orderType: {
    '居家陪护': '0',
    '医院陪护': '1',
    '家政服务': '2'
  },
  //订单状态
  orderStatus: {
    '未派遣': '0',
    '未支付': '1',
    '已支付': '2',
    '服务中': '3',
    '已完成': '4',
    '已取消': '5',
    '已退款': '6'
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
    notifyUrl: 'https://fdxmgl.fd.com.cn:8443/api/wx/' + appId + '/pay/notify/order'
    //notifyUrl: 'http://127.0.0.1:8082/api/wx/' + appId + '/pay/notify/order'
  }
}

module.exports = config

