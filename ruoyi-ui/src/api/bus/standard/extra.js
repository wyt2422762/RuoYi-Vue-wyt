import request from '@/utils/request'

// 查询额外服务列表
export function listExtra(query) {
  return request({
    url: '/bus/standard/extra/list',
    method: 'get',
    params: query
  })
}

// 查询额外服务详细
export function getExtra(id) {
  return request({
    url: '/bus/standard/extra/' + id,
    method: 'get'
  })
}

// 新增额外服务
export function addExtra(data) {
  return request({
    url: '/bus/standard/extra',
    method: 'post',
    data: data
  })
}

// 修改额外服务
export function updateExtra(data) {
  return request({
    url: '/bus/standard/extra',
    method: 'put',
    data: data
  })
}

// 删除额外服务
export function delExtra(id) {
  return request({
    url: '/bus/standard/extra/' + id,
    method: 'delete'
  })
}

// 导出额外服务
export function exportExtra(query) {
  return request({
    url: '/bus/standard/extra/export',
    method: 'get',
    params: query
  })
}
