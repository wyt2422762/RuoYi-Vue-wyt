import request from '@/utils/request'

// 查询收费标准-医院陪护列表
export function listHospitalcare(query) {
  return request({
    url: '/bus/standard/hospitalcare/list',
    method: 'get',
    params: query
  })
}

// 查询收费标准-医院陪护详细
export function getHospitalcare(id) {
  return request({
    url: '/bus/standard/hospitalcare/' + id,
    method: 'get'
  })
}

// 新增收费标准-医院陪护
export function addHospitalcare(data) {
  return request({
    url: '/bus/standard/hospitalcare',
    method: 'post',
    data: data
  })
}

// 修改收费标准-医院陪护
export function updateHospitalcare(data) {
  return request({
    url: '/bus/standard/hospitalcare',
    method: 'put',
    data: data
  })
}

// 删除收费标准-医院陪护
export function delHospitalcare(id) {
  return request({
    url: '/bus/standard/hospitalcare/' + id,
    method: 'delete'
  })
}

// 导出收费标准-医院陪护
export function exportHospitalcare(query) {
  return request({
    url: '/bus/standard/hospitalcare/export',
    method: 'get',
    params: query
  })
}
