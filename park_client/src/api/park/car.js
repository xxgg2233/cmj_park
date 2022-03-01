import request from '@/utils/request'
var urlStr = "/park/member/car"

// 按照车主查询
export function listCar(carQuery) {
    return request({
        url: urlStr + '/listMemberPage',
        method: 'get',
        params: carQuery
    })
}

// 获取车辆信息编辑
export function getCarDetail(carId) {
    return request({
        url: urlStr + '/' + carId,
        method: 'get',
    })
}

// 添加车辆信息
export function addCar(file) {
    return request({
        url: urlStr + '/add',
        method: 'post',
        data: file   // json格式提交
    })
}

// 修改车辆信息
export function updateCar(file) {
    return request({
        url: urlStr + '/update',
        method: 'put',
        data: file,      // json格式提交
    })
}

// 删除车辆信息
export function deleteCar(carIds) {
    return request({
        url: urlStr + '/' + carIds,
        method: 'delete',
    })
}