import { getLocalData, removeLocalData, setLocalData, getSessionData, setSessionData, removeSessionData } from "./cache";

const TOKEN_KEY = 'jwt-token'
const USER_INFO_KEY = 'user-info'

export function getToken() {
    return getLocalData(TOKEN_KEY)
}

export function getTokenAdmin() {
    return getSessionData(TOKEN_KEY)
}

export function setToken(token) {
    setLocalData(TOKEN_KEY, token)
}

export function setTokenAdmin(token) {
    setSessionData(TOKEN_KEY, token)
}

export function removeToken() {
    removeLocalData(TOKEN_KEY)
}

export function removeTokenAdmin() {
    removeSessionData(TOKEN_KEY)
}

export function getUserInfo() {
    let userInfoStr = getLocalData(USER_INFO_KEY)
    if (userInfoStr) {
        return JSON.parse(userInfoStr)
    }
    return null
}

export function setUserInfo(userInfo) {
    setLocalData(USER_INFO_KEY, JSON.stringify(userInfo))
}

export function removeUserInfo() {
    removeLocalData(USER_INFO_KEY)
}

export function getAdminInfo() {
    let userInfoStr = getSessionData(USER_INFO_KEY)
    if (userInfoStr) {
        return JSON.parse(userInfoStr)
    }
    return null
}

export function setAdminInfo(userInfo) {
    setSessionData(USER_INFO_KEY, JSON.stringify(userInfo))
}

export function removeAdminInfo() {
    removeSessionData(USER_INFO_KEY)
}
