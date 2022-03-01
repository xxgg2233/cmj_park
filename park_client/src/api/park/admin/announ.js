import request from '@/utils/request'

// 查询公告
export function getAnnoun() {
    return request({
        url: '/park/member/getAnnoun',
        method: 'get',
    })
}

// 查询公告
export function getAnnounAdmin() {
    return request({
        url: '/park/admin/getAnnoun',
        method: 'get',
    })
}

// 修改公告
export function updateAnnoun(announMsg) {
    return request({
        url: '/park/admin/setAnnoun',
        method: 'put',
        data: announMsg,      // json格式提交
    })
}
