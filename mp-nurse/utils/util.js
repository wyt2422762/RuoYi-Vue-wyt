const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('-')
}

const formatDateAfterMonth = (dateStr, n) => {
  let s = dateStr.split("-")
  let yy = parseInt(s[0])
  let mm = parseInt(s[1])
  let dd = parseInt(s[2])

  let dt = new Date(yy, mm, dd)
  dt.setMonth((dt.getMonth()-1) + n)

  let num = mm + parseInt(n);
  if (num / 12 > 1) {
    yy += Math.floor(num / 12);
    mm = num % 12;
  } else {
    mm += parseInt(n);
  }
  
  let dt2 = new Date(dt.getFullYear(), dt.getMonth(), 0)
  let dd1 = dd > dt2.getDate() ? dt2.getDate() : dd

  return [yy, mm, dd1].map(formatNumber).join('-')
}

const getDaysBetween = (dateStr1, dateStr2) => {
  let startDate = Date.parse(dateStr1);
  let endDate = Date.parse(dateStr2);
  if (startDate > endDate) {
    return 0;
  }
  if (startDate == endDate) {
    return 1;
  }
  var days = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000) + 1;
  return days;
}


const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatTime: formatTime,
  formatDate: formatDate,
  formatDateAfterMonth: formatDateAfterMonth,
  getDaysBetween: getDaysBetween
}