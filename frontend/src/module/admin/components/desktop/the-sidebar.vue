<template>
    <el-aside width="230px">
        <div class="sidebar-container" :class="{'is-active':isCollapse}">

            <!-- logo -->
            <div class="logo">
                <transition name="fade">
                    <span v-if="isCollapse" class="logo-title is-bold " key="0" :class="{'is-text':!type,'is-img':type}">
                        <template v-if="type">
                            <img :src="website.logo" width="40" height="40" />
                        </template>
                        <template v-else>
                            {{website.logo}}
                        </template>
                    </span>
                </transition>

                <transition-group name="fade">
                    <template v-if="!isCollapse">
                        <span class="logo-title is-bold" key="1">{{website.title}} </span>
                    </template>
                </transition-group>
            </div>

            <!-- 菜单 -->
            <el-menu background-color="#00142a" text-color="hsla(0,0%,100%,.65)" active-text-color="#409eff">
                <el-menu-item index="1" @click="open()">
                    <i class="el-icon-location"></i>
                    <span slot="title">首页</span>
                </el-menu-item>

                <el-submenu index="2">
                    <template slot="title">
                        <i class="el-icon-edit-outline"></i>
                        <span>文章</span>
                    </template>
                    <el-menu-item index="2-1">
                        <i class="el-icon-edit"></i>
                        <span slot="title">发布文章</span>
                    </el-menu-item>
                    <el-menu-item index="2-2">
                        <i class="el-icon-menu"></i>
                        <span slot="title">文章管理</span>
                    </el-menu-item>
                    <el-menu-item index="2-3">草稿箱</el-menu-item>
                </el-submenu>

                <el-menu-item index="3" @click="open()">
                    <i class="fa fa-comments"></i>
                    <span slot="title">评论管理</span>
                </el-menu-item>

                <el-menu-item index="4" @click="open()">
                    <i class="fa fa-link"></i>
                    <span slot="title">友链管理</span>
                </el-menu-item>

                <el-menu-item index="5" @click="open()">
                    <i class="el-icon-info"></i>
                    <span slot="title">关于</span>
                </el-menu-item>
            </el-menu>
        </div>
    </el-aside>
</template>

<script>
	export default {
		name: "the-sidebar",
        data() {
		    return {
                isCollapse: false,
                website: {
                    logo: "闲",
                    title: "浮闲阁",
                    author: "BY smallwei",
                }
            }
        },

        methods: {
		    open: function () {

            }
        }
	}
</script>

<style scoped>
    .fade-leave-active {
        transition: opacity 0.2s;
    }
    .fade-enter-active {
        transition: opacity 2.5s;
    }
    .fade-enter, .fade-leave-to {
        opacity: 0;
    }

    .sidebar-container {
        height: 100%;
        background: #00142a;
    }
    .el-menu {
        border-right:none!important
    }
    .logo {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 230px;
        height: 68px;
        line-height: 64px;
        background: #002140;
        color: #fdfdfd;
        text-align: center;
        font-size: 20px;
        font-weight: 600;
        overflow: hidden;
        box-sizing: border-box;
    }
    .logo-title {
        color: #409eff;
        font-size: 28px;
        letter-spacing: 15px;
    }
    .logo-title.is-bold {
         font-weight: 700;
    }
    .is-text {
        position: absolute;
        top: 0;
        left: 20px;
    }
    .is-img {
        position: absolute;
        top: 10px;
        left: 10px;
    }
</style>
