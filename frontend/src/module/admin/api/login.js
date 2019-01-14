import httpClient from '../util/http-client'

export function loginByUsername(username, password) {
    const data = {
        username,
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
        url: '/front/logout',
        method: 'post'
    })
}

export function getUserInfo(token) {
    return httpClient({
        url: '/user/info',
        method: 'get',
        params: {token}
    })
}

