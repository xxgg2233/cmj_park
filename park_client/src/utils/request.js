import axios from 'axios'
import { getToken, getTokenAdmin, removeToken, removeUserInfo } from "./auth";
import { showSuccessMsg, showErrorMsg } from "./msg";
import Vue from "vue";
import router from "@/router";

const TOKEN_PREFIX = 'Bearer '
// create an axios instance
const service = axios.create({
    baseURL: process.env.VUE_APP_SERVER_URL, // url = base url + request url
    timeout: 5000, // request timeout
})

// 添加请求拦截器，若token存在则在请求头中加token，不存在也继续请求
service.interceptors.request.use(
    config => {
        // 每次发送请求之前检测都vuex存有token,那么都要放在请求头发送给服务器,没有则不带token
        // Authorization是必须的
        if (getToken()) {
            config.headers.Authorization = TOKEN_PREFIX + getToken()
        } else if (getTokenAdmin()) {
            config.headers.Authorization = TOKEN_PREFIX + getTokenAdmin()
        }
        return config;
    },
    error => {
        console.log("在request拦截器显示错误：", error.response)
        return Promise.reject(error);
    }
);

// respone拦截器
service.interceptors.response.use(
    resp => {
        // 未设置状态码则默认成功状态
        const code = resp.data.code || 200;
        // 获取响应信息
        const msg = resp.data.msg
        if (code === 200) {
            return resp.data
        } else if (code === 401) {
            removeToken()
            removeUserInfo()
            // 以路径后面挂参数的方式传参，刷新时不会丢失参数
            router.push({ path: '/login', query: { fromPath: router.currentRoute.path } })
        } else if (code === 403) {
            router.go(-1)
            Vue.toasted.show(msg, { position: 'top-center', duration: 3000, theme: 'outline', type: 'error' })
        } else {
            Vue.toasted.show(msg, { position: 'top-center', duration: 3000, theme: 'outline', type: 'error' })
        }
        return Promise.reject(new Error(msg))
    },
    error => {
        console.log('err: ' + error)
        let { message } = error;
        if (message == "Network Error") {
            message = "后端接口连接异常";
        } else if (message.includes("timeout")) {
            message = "后端接口请求超时";
        } else if (message.includes("Request failed with status code")) {
            message = "后端接口" + message.substr(message.length - 3) + "异常";
        }
        showErrorMsg(message)
        return Promise.reject(error)
    }
);


export default service