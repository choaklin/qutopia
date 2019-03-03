/**
 *  http请求的全局配置
 */
import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '../store'

// create an axios instance
const httpClient = axios.create({
    baseURL: process.env.ADMIN_REQUEST_BASE_URL,    // api 的 base_url
    timeout: 5000 // request timeout
})

// request interceptor
httpClient.interceptors.request.use(
    config => {
        // Do something before request is sent
        let token = store.state.authentication.token;
        if (token) {
            // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
            config.headers['token'] = token;
        }
        return config
    },
    error => {
        // Do something with request error
        console.log(error) // for debug
        Promise.reject(error)
    }
)

// response interceptor
httpClient.interceptors.response.use(
    // response => response,

    /**
     * http response的结构:
     * {
     *     // 标准的http协议层
     *     status: 200,
     *     statusText: 'ok',
     *
     *     // 业务自定义层
     *     data: {
     *         code: 200,
     *         desc: '',
     *         data: []
     *     }
     * }
     *
     * 请求的统一拦截器
     * 1. 解析HTTP的状态码
     *    2XX 成功
     *    - 200: OK，表示从客户端发来的请求在服务器端被正确处理
     *    - 204: No content，表示请求成功，但响应报文不含实体的主体部分
     *    - 206: Partial Content，进行范围请求
     *
     *    3XX 重定向
     *    - 301：moved permanently，永久性重定向，表示资源已被分配了新的 URL
     *    - 302：found，临时性重定向，表示资源临时被分配了新的 URL
     *    - 303：see other，表示资源存在着另一个 URL，应使用 GET 方法丁香获取资源
     *    - 304：not modified，表示服务器允许访问资源，但因发生请求未满足条件的情况
     *    - 307：temporary redirect，临时重定向，和302含义相同
     *
     *    4XX 客户端错误
     *    - 400: bad request，请求报文存在语法错误
     *    - 401: unauthorized，表示发送的请求需要有通过 HTTP 认证的认证信息
     *    - 403: forbidden，表示对请求资源的访问被服务器拒绝, 无权限
     *    - 404: not found，表示在服务器上没有找到请求的资源
     *
     *    5XX 服务端错误
     *    - 500: internal sever error，表示服务器端在执行请求时发生了错误
     *    - 503: service unavailable，表明服务器暂时处于超负载或正在停机维护，无法处理请求
     *
     * 2. 处理业务自定义
     *
     * 下面的注释为通过在 response 里，自定义code来标示请求状态
     * 当code返回如下情况则说明权限有问题，登出并返回到登录页
     * 如想通过 XmlHttpRequest 来状态码标识 逻辑可写在下面error中
     * 以下代码均为样例，请结合自生需求加以修改，若不需要，则可删除
     */
    response => {
        if (response.status !== 200) {
            debugger
            const res = response.data
            Message({
                message: res.message,
                type: 'error',
                duration: 5 * 1000
            })
            // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
            if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
                // 请自行在引入 MessageBox
                // import { Message, MessageBox } from 'element-ui'
                MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    store.dispatch('FedLogOut').then(() => {
                        location.reload() // 为了重新实例化vue-router对象 避免bug
                    })
                })
            }
            return Promise.reject('error')
        } else {
            return response.data
        }
    },

    error => {
        // debugger
        let response = error.response;
        let errorMsg = '[' + response.status + '] ';

        switch (response.status) {
            case 500:
                errorMsg += response.data.message;
                break;
            case 504:
                errorMsg += '客户端请求处理超时，请检查服务端是否正常运行!';
                break
            default: errorMsg = error.message;
        }
        Message({
            message: errorMsg,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default httpClient
