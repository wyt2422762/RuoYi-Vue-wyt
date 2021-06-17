import request from '@/utils/request'

// 查询家政-长期保洁-收费标准列表
export function listClean(query) {
  return request({
    url: '/bus/standard/housekeeping/clean/list',
    method: 'get',
    params: query
  })
}

// 查询家政-长期保洁-收费标准详细
export function getClean(id) {
  return request({
    url: '/bus/standard/housekeeping/clean/' + id,
    method: 'get'
  })
}

// 新增家政-长期保洁-收费标准
export function addClean(data) {
  return request({
    url: '/bus/standard/housekeeping/clean',
    method: 'post',
    data: data
  })
}

// 修改家政-长期保洁-收费标准
export function updateClean(data) {
  return request({
    url: '/bus/standard/housekeeping/clean',
    method: 'put',
    data: data
  })
}

// 删除家政-长期保洁-收费标准
export function delClean(id) {
  return request({
    url: '/bus/standard/housekeeping/clean/' + id,
    method: 'delete'
  })
}

// 导出家政-长期保洁-收费标准
export function exportClean(query) {
  return request({
    url: '/bus/standard/housekeeping/clean/export',
    method: 'get',
    params: query
  })
}
