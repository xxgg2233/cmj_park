import request from '@/utils/request'

var urlStr = "/park/admin/userMsg"

// 分页查询用户信息
export function listUser(adminQuery) {
    return request({
        url: urlStr + '/listUser',
        method: 'get',
        params: adminQuery
    })
}

// 添加用户信息
export function addUser(userMsg) {
    return request({
        url: urlStr + '/addUser',
        method: 'post',
        data: userMsg   // json格式提交
    })
}

// 添加缴费卡
export function addCard(userMsg) {
    return request({
        url: urlStr + '/addCard',
        method: 'post',
        data: userMsg   // json格式提交
    })
}

// 充值
export function updateCard(userMsg) {
    return request({
        url: urlStr + '/updateCard',
        method: 'put',
        data: userMsg,      // json格式提交
    })
}

// 修改用户
export function updateUser(userMsg) {
    return request({
        url: urlStr + '/update',
        method: 'put',
        data: userMsg,      // json格式提交
    })
}

// 删除用户
export function deleteUser(userIds) {
    return request({
        url: urlStr + '/delete/' + userIds,
        method: 'delete',
    })
}