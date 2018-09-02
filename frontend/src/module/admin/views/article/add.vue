<template>
    <div>
        <div class="half-range">
            <el-form ref="form" :model="article" label-width="60px">
                <el-form-item required label="标题">
                    <el-input v-model="article.title" placeholder="文章标题，字数控制在 50 个字以内"></el-input>
                </el-form-item>
                <el-form-item required label="摘要">
                    <el-input type="textarea" v-model="article.overview" placeholder="无摘要不精彩" :autosize="{minRows:3, maxRows:4}"></el-input>
                </el-form-item>
                <el-form-item required label="内容">
                    <mavon-editor v-model="article.content" codeStyle="railscasts"/>
                </el-form-item>

                <el-form-item required label="类型">
                    <div style="float: left;">
                        <el-radio-group v-model="article.type">
                            <el-radio :label="1">原创</el-radio>
                            <el-radio :label="2">转载</el-radio>
                        </el-radio-group>
                    </div>
                    <div style="float: left; margin-left: 15px; width: 88%">
                        <el-input :disabled="article.type==1" placeholder="输入转载的原文链接"></el-input>
                    </div>
                </el-form-item>

                <el-form-item label="标签">
                    <el-tag closable :disable-transitions="false" v-for="tag in article.tags" :key="tag" @close="handleClose(tag)">
                        {{tag}}
                    </el-tag>
                    <el-input v-if="inputVisible" ref="saveTagInput" class="input-new-tag" v-model="inputValue" size="small" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm"></el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
                </el-form-item>

                <el-form-item label="选项">
                    <el-checkbox-group v-model="article.options">
                        <el-checkbox label="置顶" name="putPop"></el-checkbox>
                        <el-checkbox label="禁止评论" name="commentable"></el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

                <el-form-item style="text-align: center">
                    <el-button type="primary" @click="increase(true)">发表文章</el-button>
                    <el-button @click="increase(false)">保存草稿</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>

    import Vue from 'vue';

    export default {
        name: "article-increase",
        data: function () {
            return {
                inputVisible: false,
                inputValue: '',

                type: 1,

                article: {
                    title: '',
                    overview: '',
                    content: '',
                    type: 1,
                    tags: [],
                    options: [],
                    published: false
                }
            }
        },

        methods: {
            handleClose(tag) {
                this.article.tags.splice(this.article.tags.indexOf(tag), 1);
            },

            showInput() {
                this.inputVisible = true;
                this.$nextTick(() => {
                    this.$refs.saveTagInput.focus();
                });
            },

            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    this.article.tags.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },

            increase: function (published) {

                let article = this.article;
                article.published = published;

                Vue.axios.get("https://github.com/b3log/solo/blob/b0e1c451d1f1afb2d3444ce4f33b8d4c35fd1f3e/src/main/resources/repository.json")
                    .then((response) => {
                        console.log(response)
                    });

                Vue.axios.delete
            }
        }
    }
</script>

<style lang="less">
    .el-tag + .el-tag {
        margin-left: 10px;
    }

    .input-new-tag {
        width: 120px;
        margin-left: 10px;
        height: 32px;
        line-height: 32px;
        vertical-align: center;
        &:first-child {
            margin-left: 0;
        }
    }

    .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 32px;
        padding-top: 0;
        padding-bottom: 0;
        &:first-child {
            margin-left: 0;
        }
    }
</style>
