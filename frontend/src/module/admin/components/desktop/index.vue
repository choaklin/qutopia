<template>
	<el-container>
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

<style scoped>
    .el-container{
        height: 100%;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        line-height: 200px;
        height: 100%;
    }

    .el-header{
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 68px;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }
</style>
