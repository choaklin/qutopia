<template>
    <div>
        <el-form :inline="true" :model="formInline" size="medium" style="background-color: #f2f2f2; padding-top: 25px">
            <el-form-item label="文章标题">
                <el-input v-model="formInline.user" placeholder="文章标题"></el-input>
            </el-form-item>
            <el-form-item label="标签">
                <el-select v-model="formInline.region" placeholder="标签">
                    <el-option label="区域一" value="shanghai"></el-option>
                    <el-option label="区域二" value="beijing"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>

        <div style="margin-top: 30px">
            <el-table :data="pagination.content" border style="width: 100%" :height="487">
                <el-table-column prop="title" label="标题" width="350" showOverflowTooltip></el-table-column>
                <el-table-column prop="province" label="分类" width="140" align="center"></el-table-column>
                <el-table-column prop="tags" label="标签">
                    <template slot-scope="scope">
                        <el-tag v-for="tag in scope.row.tags" :key="tag">{{tag}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="viewCount" label="浏览数" width="70" align="center"></el-table-column>
                <el-table-column prop="commentCount" label="评论数" width="70" align="center"></el-table-column>
                <el-table-column prop="createTime" label="发表时间" width="160" align="center"></el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="handleClick(scope.row)">预览</el-button>
                        <el-button type="text" size="small">编辑</el-button>
                        <el-button type="text" size="small">删除</el-button>
                        <el-button type="text" size="small">评论管理</el-button>
                    </template>
                </el-table-column>
            </el-table>

        </div>
        <div style="float: right; margin-top: 15px;">
            <el-pagination background
                           :current-page.sync="pagination.pageNumber"
                           :page-size="pagination.pageSize"
                           layout="total, sizes, prev, pager, next,jumper"
                           :total="pagination.totalElements">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import vm from 'vue';

    export default {
        name: "post-index",

        mounted: function() {
            console.log('>> execute mounted callback function, eg load data');
            this.loadTableData();
        },

        data: function () {
            return {
                formInline: {
                    user: "",
                    region: ""
                },

                pagination: {
                    pageNumber: 0,
                    pageSize: 10,
                    content: [],
                    totalElements: 0,
                }
            }
        },

        methods: {
            onSubmit: function () {
                this.loadTableData();
            },

            loadTableData: function () {
                vm.axios.get('/web/article').then((response) => {

                    if (response.status === 200) {
                        let responseData = response.data;

                        this.pagination.pageNumber = responseData.number;
                        this.pagination.pageSize = responseData.size;
                        this.pagination.content = responseData.content;
                        this.pagination.totalElements = responseData.totalElements;
                    }
                });
            }
        }
    }
</script>

<style lang="less">
    .el-form--inline {
        .el-form-item {
            padding-left: 20px;
        }
    }

    .el-table td, .el-table th {
        padding: 6px 0;
    }

    .el-tag + .el-tag {
        margin-left: 5px;
    }
    .el-button+.el-button {
        margin-left: 5px;
    }
</style>
