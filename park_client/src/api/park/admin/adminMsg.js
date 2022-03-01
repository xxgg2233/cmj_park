import request from '@/utils/request'

var urlStr = "/park/admin/adminMsg"

// 分页查询管理员信息
export function listAdmin(adminQuery) {
    return request({
        url: urlStr + '/listAdmin',
        method: 'get',
        params: adminQuery
    })
}

// 添加管理员信息
export function addAdmin(adminMsg) {
    return request({
        url: urlStr + '/add',
        method: 'post',
        data: adminMsg   // json格式提交
    })
}

// 修改管理员
export function updateAdmin(adminMsg) {
    return request({
        url: urlStr + '/update',
        method: 'put',
        data: adminMsg,      // json格式提交
    })
}

// 删除管理员
export function deleteAdmin(adminIds) {
    return request({
        url: urlStr + '/delete/' + adminIds,
        method: 'delete',
    })
}