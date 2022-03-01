import request from '@/utils/request'

// 图片上传
export function fileUpload(file) {
    return request({
        url: '/park/member/car/upload',
        method: 'post',
        data: file,
    })
}
