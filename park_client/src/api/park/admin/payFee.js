import request from '@/utils/request'

var urlStr = "/park/admin/payFee"

//提交缴费信息
export function payFee(payMsg) {
    return request({
        url: urlStr + '/pay',
        method: 'put',
        data: payMsg,      // json格式提交
    })
}

//获取缴费卡信息
export function getMoney(payMsg) {
    return request({
        url: urlStr + '/getMoney/' + payMsg,
        method: 'get',
    })
}