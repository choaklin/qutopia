<template>
    <el-header :height="'68'">
        <div class="right-panel">
            <!-- 选择主题 -->
            <el-tooltip class="item" effect="dark" content="特色主题" placement="bottom">
                <span>
                    <i class="el-icon-qutopia-color" @click="open"></i>
                    <div>
                        <el-dialog title="选择" :visible.sync="box" width="30%">
                            <el-radio-group v-model="selectedTheme">
                                <el-radio v-for="(theme, index) in themes" :key="index" :label="theme.value">
                                    {{theme.name}}
                                </el-radio>
                            </el-radio-group>

                            <el-color-picker v-model="color"></el-color-picker>
                        </el-dialog>
                    </div>
                </span>
            </el-tooltip>

            <!-- 用户头像 -->
            <el-tooltip class="item" effect="dark" content="用户头像" placement="bottom">
                <img class="user-image" src="../../assets/logo_25.png">
            </el-tooltip>

            <!-- 设置项下拉菜单 -->
            <el-dropdown>
                <span class="el-dropdown-link">
                    <span class="username">管理员</span>
                    <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                        <i class="el-icon-qutopia-info-circle"></i>
                        <router-link to="/info/index">个人信息</router-link>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <i class="el-icon-qutopia-logout"></i>
                        <span>退出系统</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </el-header>
</template>

<script>
	export default {
		name: "the-header",

        data: function() {
		    return {
                color: "#409eff",
                box: false,
                selectedTheme: 'theme-star',
                themes: [
                    {
                        name: "默认",
                        value: ""
                    },
                    {
                        name: '星空',
                        value: 'theme-star'
                    }
                ]
            };
        },
        methods: {
            open: function () {
                this.box = true;
            },
        },

        watch: {
            selectedTheme: function (theme) {
                // this.$store.commit('SET_THEME_NAME', val);
                console.log(theme);
                document.body.className = theme;
            }
        }
	}
</script>

<style lang="less" scoped>

    .el-header {
        .right-panel {
            display: flex;
            align-items: center;
            justify-content: right;

            .item {
                font-size: 24px;
                margin-right: 12px;
                i {
                    font-size: 24px;
                }
            }

            .user-image {
                border-radius: 12px;
            }

            .username {
                font-size: 14px;
            }
        }
    }

</style>
