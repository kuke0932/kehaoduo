import request from '@/utils/request'
import qs from 'qs'
export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/system/admin/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function logout() {
  return request({
    url: '/system/admin/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/system/admin/getUserInfo',
    method: 'get',
    params: { token }
  })
}

