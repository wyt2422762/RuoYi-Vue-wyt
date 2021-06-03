import request from '@/utils/request'

// 查询收费标准-居家陪护列表
export function listHomecare(query) {
  return request({
    url: '/bus/standard/homecare/list',
    method: 'get',
    params: query
  })
}

// 查询收费标准-居家陪护详细
export function getHomecare(id) {
  return request({
    url: '/bus/standard/homecare/' + id,
    method: 'get'
  })
}

// 新增收费标准-居家陪护
export function addHomecare(data) {
  return request({
    url: '/bus/standard/homecare',
    method: 'post',
    data: data
  })
}

// 修改收费标准-居家陪护
export function updateHomecare(data) {
  return request({
    url: '/bus/standard/homecare',
    method: 'put',
    data: data
  })
}

// 删除收费标准-居家陪护
export function delHomecare(id) {
  return request({
    url: '/bus/standard/homecare/' + id,
    method: 'delete'
  })
}

// 导出收费标准-居家陪护
export function exportHomecare(query) {
  return request({
    url: '/bus/standard/homecare/export',
    method: 'get',
    params: query
  })
}
