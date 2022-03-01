import request from '@/utils/request'


// 分页查询停车记录
export function listRecord(recordMsg) {
    return request({
        url: '/park/member/record/listMemberPage',
        method: 'get',
        params: recordMsg
    })
}

// 查询所有停车记录
export function listAllRec(recordMsg) {
    return request({
        url: '/park/admin/AllRec',
        method: 'get',
        params: recordMsg
    })
}