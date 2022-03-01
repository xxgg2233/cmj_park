import request from '@/utils/request'

// 注册用户
export function addUser(user) {
    return request({
        url: '/park/register/insert',
        method: 'get',
        params: user   // json格式提交
    })
}