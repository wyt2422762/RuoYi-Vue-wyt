import request from '@/utils/request'

// 查询投诉列表
export function listComplaint(query) {
  return request({
    url: '/bus/complaint/list',
    method: 'get',
    params: query
  })
}

// 查询投诉详细
export function getComplaint(id) {
  return request({
    url: '/bus/complaint/' + id,
    method: 'get'
  })
}


