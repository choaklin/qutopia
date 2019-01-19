import httpClient from '../util/http-client'

export function loginByUsername(loginInput, password) {
    const data = {
        loginInput,
        password
    };
    return httpClient({
        baseURL: process.env.COMMON_REQUEST_BASE_URL,
        url: '/login',
        method: 'post',
        data
    })
}

export function logout() {
    return httpClient({
        baseURL: process.env.COMMON_REQUEST_BASE_URL,
        url: '/login/logout',
        method: 'get'
    })
}

export function getUserInfo(token) {
    return httpClient({
        url: '/user/info',
        method: 'get',
        params: {token}
    })
}

