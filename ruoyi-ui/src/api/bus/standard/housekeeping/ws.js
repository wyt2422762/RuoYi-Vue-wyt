import request from '@/utils/request'

// 查询家政-卫生清洁-收费标准列表
export function listWs(query) {
  return request({
    url: '/bus/standard/housekeeping/ws/list',
    method: 'get',
    params: query
  })
}

// 查询家政-卫生清洁-收费标准详细
export function getWs(id) {
  return request({
    url: '/bus/standard/housekeeping/ws/' + id,
    method: 'get'
  })
}

// 新增家政-卫生清洁-收费标准
export function addWs(data) {
  return request({
    url: '/bus/standard/housekeeping/ws',
    method: 'post',
    data: data
  })
}

// 修改家政-卫生清洁-收费标准
export function updateWs(data) {
  return request({
    url: '/bus/standard/housekeeping/ws',
    method: 'put',
    data: data
  })
}

// 删除家政-卫生清洁-收费标准
export function delWs(id) {
  return request({
    url: '/bus/standard/housekeeping/ws/' + id,
    method: 'delete'
  })
}

// 导出家政-卫生清洁-收费标准
export function exportWs(query) {
  return request({
    url: '/bus/standard/housekeeping/ws/export',
    method: 'get',
    params: query
  })
}
