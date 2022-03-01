import request from '@/utils/request'
var urlStr = "/park/member/park"

// 按关键字查询车位信息
export function listPark(parkMsg) {
    return request({
        url: urlStr + '/listPage',
        method: 'get',
        params: parkMsg
    })
}

// 修改预约位
export function updateCarN(parkMsg) {
    return request({
        url: urlStr,
        method: 'put',
        data: parkMsg   // json格式提交
    })
}