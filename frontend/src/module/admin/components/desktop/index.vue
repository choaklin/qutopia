<template>
	<el-container class="qutopia-container">
        <the-sidebar />

        <el-container class="is-vertical">
            <the-header></the-header>
            <el-main>
                <span>Main</span>
                <span>此处要开发出嵌套的路由视图</span>
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
