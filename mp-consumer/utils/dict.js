//查询字典工具类

import { service } from "./request"

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return service.get('/dict/data/type/' + dictType, {})
}