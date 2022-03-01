import request from '@/utils/request'
var urlStr = "/park/member/user"

// 获取个人信息
export function getUser(username) {
    return request({
        url: urlStr + '/' + username,
        method: 'get',
    })
}

//获取用户卡余额
export function getCardMsg(username) {
    return request({
        url: urlStr + '/card/' + username,
        method: 'get',
    })
}

// 修改用户信息
export function updateUser(user) {
    return request({
        url: urlStr,
        method: 'put',
        data: user   // json格式提交
    })
}

// 修改密码
export function changePwd(info) {
    return request({
        url: urlStr,
        method: 'post',
        data: info   // json格式提交
    })
}