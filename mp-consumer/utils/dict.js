//查询字典工具类

import {
  service
} from "./request"

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return service.get('/dict/data/type/' + dictType, {})
}

//根据字典标签获取数据字典的值
export function getDictValueByLabel(dicts, value) {
  let actions = [];
  Object.keys(dicts).some((key) => {
    if (dicts[key].dictLabel == ('' + value)) {
      actions.push(dicts[key].dictValue);
      return true;
    }
  })
  return actions.join('');
}