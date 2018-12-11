import request from '@/utils/request'
import qs from 'qs'

/**
 * 修改密码
 *
 * @param oldPwd
 * @param newPwd
 */
export function changePassword(oldPwd, newPwd) {
  const data = {
    oldPwd,
    newPwd
  }
  return request({
    url: '/bs/admin/changePassword',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 添加代理商
 * @param loginName
 * @param loginPwd
 * @param mobile
 * @param telephone
 * @param area
 * @param appId
 * @param appSecret
 * @param token
 * @param aesKey
 * @param areaCode
 */
export function addAgent(loginName, loginPwd, mobile, telephone, area, areaCode, appId, appSecret, token, aesKey) {
  const data = {
    loginName,
    loginPwd,
    mobile,
    telephone,
    area,
    areaCode,
    appId,
    appSecret,
    token,
    aesKey
  }
  return request({
    url: '/bs/admin/addAgent',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 代理商信息列表
 *
 * @param nameOrTel
 * @param beginTime
 * @param endTime
 * @param pageNum
 * @param pageSize
 */
export function listAgent(nameOrTel, beginTime, endTime, pageNum = 1, pageSize = 10) {
  const data = {
    nameOrTel,
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/listAgent',
    method: 'get',
    params: data
  })
}

/**
 * 根据userId获取代理商信息
 *
 * @param id
 */
export function fetchDataById(id) {
  const data = {
    id
  }
  return request({
    url: '/bs/admin/queryAgentByUserId',
    method: 'get',
    params: data
  })
}

/**
 * 修改代理商信息
 * @param sysUserId
 * @param mobile
 * @param telephone
 * @param area
 * @param areaCode
 * @param appId
 * @param appSecret
 * @param token
 * @param aesKey
 */
export function updateAgent(sysUserId, mobile, telephone, area, areaCode, appId, appSecret, token, aesKey) {
  const data = {
    sysUserId,
    mobile,
    telephone,
    area,
    areaCode,
    appId,
    appSecret,
    token,
    aesKey
  }
  return request({
    url: '/bs/admin/updateAgent',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 删除代理商信息
 * @param sysUserId
 */
export function removeAgent(sysUserId) {
  const data = {
    sysUserId
  }
  return request({
    url: '/bs/admin/logicalDelAgent',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 批量删除代理商信息
 *
 * @param ids
 */
export function batchRemoveAgent(ids) {
  const data = {
    ids
  }
  return request({
    url: '/bs/admin/batchRemoveAgent',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 更新密码
 * @param username
 * @param oldPwd
 * @param newPwd
 */
export function updatePassword(username, oldPwd, newPwd) {
  const data = {
    username,
    oldPwd,
    newPwd
  }
  return request({
    url: '/bs/admin/updatePassword',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 查看用户量 （普通用户和广告主）TODO（暂时没写）
 * @param userType
 * @param beginTime
 * @param endTime
 * @return
 */
export function countUser(userType, beginTime, endTime) {
  const data = {
    userType,
    beginTime,
    endTime
  }
  return request({
    url: '/bs/admin/countUser',
    method: 'get',
    params: data
  })
}

/**
 * 查看广告主的资料
 * @param shopNameOrTel
 * @param beginTime
 * @param endTime
 * @param pageNum
 * @param pageSize
 */
export function listAdvertiser(shopNameOrTel, beginTime, endTime, pageNum = 1, pageSize = 10) {
  const data = {
    shopNameOrTel,
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/listAdvertiser',
    method: 'get',
    params: data
  })
}

/**
 * 获取广告主消费总额
 * @return
 */
export function getAllConsumed() {
  return request({
    url: '/bs/admin/getAllConsumed',
    method: 'get'
  })
}

/**
 * 获取时间段消费明细
 * @param publicityType
 * @param contentType
 * @param userName 用户名
 * @param beginTime
 * @param endTime
 * @param pageNum
 * @param pageSize
 * @return List
 */
export function listConsumedDetail(publicityType, contentType, userName, beginTime, endTime, pageNum = 1, pageSize = 31) {
  const data = {
    publicityType,
    contentType,
    userName,
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/listConsumedDetail',
    method: 'get',
    params: data
  })
}

/**
 * 分成
 * @return BigDecimal
 */
export function computeEarning() {
  return request({
    url: '/bs/admin/computeEarning',
    method: 'get'
  })
}

/**
 * 分成明细
 * @param beginTime
 * @param endTime
 * @param pageNum
 * @param pageSize
 * @return BigDecimal
 */
export function computeEarningDetail(beginTime, endTime, pageNum = 1, pageSize = 31) {
  const data = {
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/computeEarningDetail',
    method: 'get',
    params: data
  })
}

/**
 * 提现明细
 * @param beginTime
 * @param endTime
 * @param pageNum
 * @param pageSize
 * @return BigDecimal
 */
export function withDrawDetail(beginTime, endTime, pageNum = 1, pageSize = 31) {
  const data = {
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/withDrawDetail',
    method: 'get',
    params: data
  })
}

/**
 * 查看广告主的资料 审核
 * @param advertiserName
 * @param businessLine
 * @param pageNum
 * @param pageSize
 * @return page{
    id
    shopName
    mobile
    businessLineCodes
    createTime
    addressCodes
    cartFront
    cartBack
    businessLicence
    checkStatus
    }
 */
export function listAdvertiser4Check(advertiserName, checkStatus, beginTime, endTime, pageNum = 1, pageSize = 10) {
  const data = {
    advertiserName,
    checkStatus,
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/listAdvertiser4Check',
    method: 'get',
    params: data
  })
}

/**
 * 审核广告主
 * @param advertiserName
 * @param businessLine
 * @param pageNum
 * @param pageSize
 * @return {status: 200, message: ''}
 */
export function checkAdvertiser(webUserId, fromStatus, toStatus, checkResult) {
  const data = {
    webUserId,
    fromStatus,
    toStatus,
    checkResult
  }
  return request({
    url: '/bs/admin/checkAdvertiser',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 * 查看广告的资料 审核
 * @param advertiserName
 * @param businessLine
 * @param pageNum
 * @param pageSize
 * @return page{
    id
    webUserId
    loginName
    mobile
    shopName
    businessLicence
    publicityTitle
    publicityIntro
    content
    publicityType
    contentType
    checkStatus
    }
 */
export function listPublicity4Check(publicityTitle, publicityType, contentType, checkStatus, beginTime, endTime, pageNum = 1, pageSize = 10) {
  const data = {
    publicityTitle,
    publicityType,
    contentType,
    checkStatus,
    beginTime,
    endTime,
    pageNum,
    pageSize
  }
  return request({
    url: '/bs/admin/listPublicity4Check',
    method: 'get',
    params: data
  })
}

/**
 * 审核广告
 * @param advertiserName
 * @param businessLine
 * @param pageNum
 * @param pageSize
 * @return {status: 200, message: ''}
 */
export function checkPublicity(publicityId, fromStatus, toStatus, checkResult) {
  const data = {
    publicityId,
    fromStatus,
    toStatus,
    checkResult
  }
  return request({
    url: '/bs/admin/checkPublicity',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 *
 * @param publicityId
 * @param fromStatus
 * @param toStatus
 * @param checkResult
 */
export function withdraw(money, password) {
  const data = {
    money,
    password
  }
  return request({
    url: '/bs/admin/withdraw',
    method: 'post',
    data: qs.stringify(data)
  })
}

/**
 *
 * @param publicityId
 * @param fromStatus
 * @param toStatus
 * @param checkResult
 */
export function queryMySite(money, password) {
  return request({
    url: '/bs/admin/queryAgentWeChatMenuClickUrl',
    method: 'get'
  })
}
