const { $Toast } = require('../component/iView/base/index');

const toast = {
  text(msg) {
    $Toast({
      content: msg
    });
  },
  success(msg) {
    $Toast({
      content: msg,
      type: 'success'
    });
  },
  warning(msg) {
    $Toast({
      content: msg,
      type: 'warning'
    });
  },
  error(msg) {
    $Toast({
      content: msg,
      type: 'error'
    });
  },
  loading(msg) {
    $Toast({
      content: msg,
      type: 'loading'
    });
  }
}

module.exports = {
  toast: toast,
}