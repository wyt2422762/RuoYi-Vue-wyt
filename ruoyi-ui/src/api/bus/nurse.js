import request from '@/utils/request'

// 查询护工列表
export function listNurse(query) {
  return request({
    url: '/bus/nurse/list',
    method: 'get',
    params: query
  })
}

// 查询护工详细
export function getNurse(nurseid) {
  return request({
    url: '/bus/nurse/' + nurseid,
    method: 'get'
  })
}

// 新增护工
export function addNurse(data) {
  return request({
    url: '/bus/nurse',
    method: 'post',
    data: data
  })
}

// 修改护工
export function updateNurse(data) {
  return request({
    url: '/bus/nurse',
    method: 'put',
    data: data
  })
}

// 删除护工
export function delNurse(nurseid) {
  return request({
    url: '/bus/nurse/' + nurseid,
    method: 'delete'
  })
}

// 导出护工
export function exportNurse(query) {
  return request({
    url: '/bus/nurse/export',
    method: 'get',
    params: query
  })
}

// 护工状态修改
export function changeNurseStatus(nurseId, status) {
  const data = {
    nurseId,
    status
  }
  return request({
    url: '/bus/nurse/changeStatus',
    method: 'put',
    data: data
  })
}