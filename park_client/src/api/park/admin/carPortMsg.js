import request from '@/utils/request'

var urlStr = "/park/admin/carPortMsg"

// 获取车位
export function listPark(parkQuery) {
    return request({
        url: urlStr + '/listPark',
        method: 'get',
        params: parkQuery
    })
}

// 添加车位
export function addPark(parkMsg) {
    return request({
        url: urlStr + '/add',
        method: 'post',
        data: parkMsg   // json格式提交
    })
}

// 更新车位
export function updatePark(parkMsg) {
    return request({
        url: urlStr + '/update',
        method: 'put',
        data: parkMsg,      // json格式提交
    })
}

// 删除车位
export function deletePark(parkIds) {
    return request({
        url: urlStr + '/delete/' + parkIds,
        method: 'delete',
    })
}