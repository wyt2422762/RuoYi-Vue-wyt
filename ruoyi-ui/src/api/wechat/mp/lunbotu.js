import request from '@/utils/request'

// 查询微信小程序轮播图列表
export function listLunbotu(query) {
  return request({
    url: '/wechat/mp/lunbotu/list',
    method: 'get',
    params: query
  })
}

// 查询微信小程序轮播图详细
export function getLunbotu(id) {
  return request({
    url: '/wechat/mp/lunbotu/' + id,
    method: 'get'
  })
}

// 新增微信小程序轮播图
export function addLunbotu(data) {
  return request({
    url: '/wechat/mp/lunbotu',
    method: 'post',
    headers: {"Content-Type": "multipart/form-data"},
    data: data
  })
}

// 修改微信小程序轮播图
export function updateLunbotu(data) {
  return request({
    url: '/wechat/mp/lunbotu',
    method: 'put',
    data: data
  })
}

// 删除微信小程序轮播图
export function delLunbotu(id) {
  return request({
    url: '/wechat/mp/lunbotu/' + id,
    method: 'delete'
  })
}

//

