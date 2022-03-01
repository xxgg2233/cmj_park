import request from '@/utils/request'

// 登录
export function login(loginData) {
    return request({
        url: '/login',
        method: 'post',
        data: loginData
    })
}

// 登录管理员
export function loginadmin(loginData) {
    return request({
        url: '/login/admins',
        method: 'post',
        data: loginData
    })
}

// 获取登录用户信息
export function getLoggedInUserInfo() {
    return request({
        url: '/getLoggedInUserInfo',
        method: 'get',
    })
}

// 登出
export function logout() {
    return request({
        url: '/logout',
        method: 'get',
    })
}