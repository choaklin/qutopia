<template>
    <div id="main-content">
        <div class="background"></div>
        <div id="main-box"></div>

        <div class="login-body  animated fadeInLeft">
            <div class="login-main pr">
                <form action="javascript:;" method="post" class="login-form">
                    <h3> 管理中心 </h3>
                    <h5 style="padding-bottom: 10px"> System Management Center </h5>

                    <!-- 账号登陆 -->
                    <div id="MobileBox" class="item-box">
                        <div class="input-group user-name"> <span class="input-group-addon"><i class="icon-user"></i></span>
                            <input v-model="loginForm.username" type="text" name="username" class="form-control" placeholder="用户名/手机号">
                        </div>
                        <div class="input-group password"> <span class="input-group-addon"><i class="icon-lock"></i></span>
                            <input v-model="loginForm.password" type="password" name="password" class="form-control" placeholder="密码">
                        </div>

                        <div class="login_btn_panel">
                            <button class="btn btn-primary btn-block btn-lg" data-ajax="post" type="submit" @click="handleLogin" data-callback="success">登录</button>
                            <div class="check-tips"></div>
                        </div>
                    </div></form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "login",

        data() {
            return {
                redirectUrl: undefined,
                loginForm: {
                    username: null,
                    password: null
                }
            }
        },

        watch:  {
            $route: {
                handler: function (route) {
                    this.redirectUrl = route.query && route.query.redirectUrl;
                },
                immediate: true
            }
        },

        methods: {
            handleLogin: function () {
                this.$store.dispatch('doLogin', this.loginForm).then(() => {
                    this.$router.push({ path: this.redirect || '/' })
                }).catch(() => {
                    alert('用户登陆失败')
                });
            }
        }
    }
</script>

<style scoped="less">
    @import "../../assets/style/zui.css";
    @import "../../assets/style/login.css";

    .background {
        width: 100%;
        height: 100%;
        position: fixed;
        left: 0;
        top: 0;
        z-index: 1;
        background-image: url("../../assets/image/star-bg.png") !important;;
    }

    #main-box {
        background-image: url("../../assets/image/dot.png");
        background-repeat: repeat;
        opacity: .7;
    }
</style>
