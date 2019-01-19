import cookie from 'js-cookie'
import {loginByUsername, logout, getUserInfo} from '../../api/login'


let key_token = 'token';

const _methods = {

    getToken: function () {
        return cookie.get(key_token);
    },

    setToken: function (token) {
        cookie.set(key_token, token)
    },

    removeToken: function () {
        cookie.remove(key_token)
    }
};


const user = {
    state: {
        user: '',
        name: '',
        avatar: '',
        status: '',
        roles: [],
        token: _methods.getToken(),
        setting: {
            articlePlatform: []
        }
    },

    mutations: {
        SET_CODE: (state, code) => {
            state.code = code
        },
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_INTRODUCTION: (state, introduction) => {
            state.introduction = introduction
        },
        SET_SETTING: (state, setting) => {
            state.setting = setting
        },
        SET_STATUS: (state, status) => {
            state.status = status
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        }
    },

    actions: {
        // 用户名登录
        doLogin({commit}, userInfo) {
            const loginInput = userInfo.loginInput.trim()
            return new Promise((resolve, reject) => {
                loginByUsername(loginInput, userInfo.password).then(token => {
                    commit('SET_TOKEN', token)
                    _methods.setToken(token);

                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        /**
         * 获取用户信息
         *
         * @param commit context.commit
         * @param state context.state
         * @returns {Promise<any>}
         */
        getUserInfo({commit, state}) {
            return new Promise((resolve, reject) => {
                getUserInfo(state.token).then(response => {
                    // 由于mockjs 不支持自定义状态码只能这样hack
                    if (!response.data) {
                        reject('error')
                    }
                    const data = response.data

                    // 验证返回的roles是否是一个非空数组
                    if (data.roles && data.roles.length > 0) {
                        commit('SET_ROLES', data.roles)
                    } else {
                        reject('getInfo: roles must be a non-null array !')
                    }

                    commit('SET_NAME', data.name)
                    commit('SET_AVATAR', data.avatar)
                    commit('SET_INTRODUCTION', data.introduction)
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 第三方验证登录
        // LoginByThirdparty({ commit, state }, code) {
        //   return new Promise((resolve, reject) => {
        //     commit('SET_CODE', code)
        //     loginByThirdparty(state.status, state.email, state.code).then(response => {
        //       commit('SET_TOKEN', response.data.token)
        //       setToken(response.data.token)
        //       resolve()
        //     }).catch(error => {
        //       reject(error)
        //     })
        //   })
        // },

        /**
         *  登出
         *  @param commit
         *  @param state
         */
        doLogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    _methods.removeToken();

                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                _methods.removeToken();

                resolve()
            })
        },

        // 动态修改权限
        ChangeRoles({commit, dispatch}, role) {
            return new Promise(resolve => {
                commit('SET_TOKEN', role)
                _methods.setToken(role)

                getUserInfo(role).then(response => {
                    const data = response.data
                    commit('SET_ROLES', data.roles)
                    commit('SET_NAME', data.name)
                    commit('SET_AVATAR', data.avatar)
                    commit('SET_INTRODUCTION', data.introduction)
                    dispatch('GenerateRoutes', data) // 动态修改权限后 重绘侧边菜单

                    resolve()
                })
            })
        }
    }
}

export default user
