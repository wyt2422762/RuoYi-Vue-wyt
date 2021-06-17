import request from '@/utils/request'

// 查询家政-卫生清洁-收费标准列表
export function listCd(query) {
  return request({
    url: '/bus/standard/housekeeping/cd/list',
    method: 'get',
    params: query
  })
}

// 查询家政-卫生清洁-收费标准详细
export function getCd(id) {
  return request({
    url: '/bus/standard/housekeeping/cd/' + id,
    method: 'get'
  })
}

// 新增家政-卫生清洁-收费标准
export function addCd(data) {
  return request({
    url: '/bus/standard/housekeeping/cd',
    method: 'post',
    data: data
  })
}

// 修改家政-卫生清洁-收费标准
export function updateCd(data) {
  return request({
    url: '/bus/standard/housekeeping/cd',
    method: 'put',
    data: data
  })
}

// 删除家政-卫生清洁-收费标准
export function delCd(id) {
  return request({
    url: '/bus/standard/housekeeping/cd/' + id,
    method: 'delete'
  })
}

// 导出家政-卫生清洁-收费标准
export function exportCd(query) {
  return request({
    url: '/bus/standard/housekeeping/cd/export',
    method: 'get',
    params: query
  })
}
