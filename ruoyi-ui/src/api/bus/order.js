import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/bus/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderNo) {
  return request({
    url: '/bus/order/' + orderNo,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/bus/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/bus/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orderNo) {
  return request({
    url: '/bus/order/' + orderNo,
    method: 'delete'
  })
}

// 导出订单
export function exportOrder(query) {
  return request({
    url: '/bus/order/export',
    method: 'get',
    params: query
  })
}

//查询订单评价
export function getEvaluateData(orderNo){
  return request({
    url: '/bus/evaluation/getByOrderNo/' + orderNo,
    method: 'get'
  })
}
