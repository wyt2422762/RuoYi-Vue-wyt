import request from '@/utils/request'

// 查询家政-窗帘清洗-收费标准列表
export function listCl(query) {
  return request({
    url: '/bus/standard/housekeeping/cl/list',
    method: 'get',
    params: query
  })
}

// 查询家政-窗帘清洗-收费标准详细
export function getCl(id) {
  return request({
    url: '/bus/standard/housekeeping/cl/' + id,
    method: 'get'
  })
}

// 新增家政-窗帘清洗-收费标准
export function addCl(data) {
  return request({
    url: '/bus/standard/housekeeping/cl',
    method: 'post',
    data: data
  })
}

// 修改家政-窗帘清洗-收费标准
export function updateCl(data) {
  return request({
    url: '/bus/standard/housekeeping/cl',
    method: 'put',
    data: data
  })
}

// 删除家政-窗帘清洗-收费标准
export function delCl(id) {
  return request({
    url: '/bus/standard/housekeeping/cl/' + id,
    method: 'delete'
  })
}

// 导出家政-窗帘清洗-收费标准
export function exportCl(query) {
  return request({
    url: '/bus/standard/housekeeping/cl/export',
    method: 'get',
    params: query
  })
}
