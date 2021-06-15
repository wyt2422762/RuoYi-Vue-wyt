// request请求封装
import dov from './dov.min.js'
import errorCode from './errCode.js'

const config = require("./config")

// const app = getApp()

// 创建实例
const service = dov.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: config.apiBaseUrl,
})

// request拦截器
service.interceptors.request.use(config => {
  //这里加token
  if(wx.getStorageSync('token') && !config.notAddToken){
    if(!config.data){
      config.data = {}
    }
    if(!config.header){
      config.header = {}
    }
    config.header['Authorization'] = 'Bearer ' + wx.getStorageSync('token')
    console.log('添加token')
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// response拦截器
service.interceptors.response.use(res => {
  // 未设置状态码则默认成功状态
  const code = res.data.code || res.data.status || 200;
  // 获取错误信息
  const msg = errorCode[code] || res.data.msg || errorCode['default']
  if (code === 401) {
    console.log('登录状态已过期，重新请求token')
    return Promise.reject(new Error(msg))
  } else if (code === 500) {
    console.log('系统内部错误')
    return Promise.reject(new Error(msg))
  } else if(code === 200){
    return res.data
  } else {
    console.log('错误')
    return Promise.reject('error')
  }
}, error => {
  console.log('err' + error)
  return Promise.reject(error)
})

export {
  service
}