import request from '@/utils/request'

// 查询评价列表
export function listEvaluation(query) {
  return request({
    url: '/bus/evaluation/list',
    method: 'get',
    params: query
  })
}

// 查询评价详细
export function getEvaluation(evaluationid) {
  return request({
    url: '/bus/evaluation/' + evaluationid,
    method: 'get'
  })
}

// 新增评价
export function addEvaluation(data) {
  return request({
    url: '/bus/evaluation',
    method: 'post',
    data: data
  })
}

// 修改评价
export function updateEvaluation(data) {
  return request({
    url: '/bus/evaluation',
    method: 'put',
    data: data
  })
}

// 删除评价
export function delEvaluation(evaluationid) {
  return request({
    url: '/bus/evaluation/' + evaluationid,
    method: 'delete'
  })
}

// 导出评价
export function exportEvaluation(query) {
  return request({
    url: '/bus/evaluation/export',
    method: 'get',
    params: query
  })
}
