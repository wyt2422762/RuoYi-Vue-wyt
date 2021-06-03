import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 查询客户列表
export function listConsumer(query) {
  return request({
    url: '/bus/consumer/list',
    method: 'get',
    params: query
  })
}

// 查询客户详细
export function getConsumer(consumerId) {
  return request({
    url: '/bus/consumer/' + praseStrEmpty(consumerId),
    method: 'get'
  })
}

// 新增客户
export function addConsumer(data) {
  return request({
    url: '/bus/consumer',
    method: 'post',
    data: data
  })
}

// 修改客户
export function updateConsumer(data) {
  return request({
    url: '/bus/consumer',
    method: 'put',
    data: data
  })
}

// 删除客户
export function delConsumer(consumerId) {
  return request({
    url: '/bus/consumer/' + consumerId,
    method: 'delete'
  })
}

// 导出用户
export function exportConsumer(query) {
  return request({
    url: '/bus/consumer/export',
    method: 'get',
    params: query
  })
}

// 客户状态修改
export function changeConsumerStatus(consumerId, status) {
  const data = {
    consumerId,
    status
  }
  return request({
    url: '/bus/consumer/changeStatus',
    method: 'put',
    data: data
  })
}



// 客户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/bus/consumer/profile/avatar',
    method: 'post',
    data: data
  })
}

// 下载客户导入模板
export function importTemplate() {
  return request({
    url: '/bus/consumer/importTemplate',
    method: 'get'
  })
}
