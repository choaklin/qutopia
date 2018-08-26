<template>
	<el-container class="qutopia-container">
        <!-- 侧边 -->
        <the-sidebar />

        <el-container class="is-vertical">
            <!-- 头部 -->
            <the-header/>

            <!-- 主内容区域 -->
            <el-main>
                <transition name="fade-transverse">
                    <router-view class="router-view"/>
                </transition>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
	import TheSidebar from "./the-sidebar";
    import TheHeader from "./the-header";
    export default {
		name: "index",
        components: {TheHeader, TheSidebar}
    }
</script>

<style lang="less" scoped>

</style>
