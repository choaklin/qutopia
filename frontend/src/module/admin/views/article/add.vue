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
                        <el-input size="small" :disabled="article.type==1" placeholder="输入转载的原文链接"></el-input>
                    </div>
                </el-form-item>

                <el-form-item required label="分类">
                    <el-select size="middle" v-model="article.categoryId">
                        <el-option v-for="item in categories"
                                   :key="item.id"
                                   :label="item.name"
                                   :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="标签">
                    <el-tag closable :disable-transitions="false"
                            v-for="(value, key) in article.tags" :key="key" @close="removeTag(key)">
                        {{key}}
                    </el-tag>
                    <el-autocomplete
                        v-if="tagInputVisible" ref="tagInput" class="input-new-tag" size="small"
                        v-model="tagName"
                        :trigger-on-focus="false"
                        :fetch-suggestions="fetchTags"
                        @select="confirmTagInput"
                        @keyup.enter.native="confirmTagInput">
                    </el-autocomplete>

                    <el-button v-else class="button-new-tag" @click="showTagQueryInput">+ 新标签</el-button>
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
                resource: {
                    _category_manage: '/admin/categoryManage/',
                    _tag_manage: '/admin/tagManage/',
                    _article_manage: '/admin/articleManage/'
                },

                // 分类, 默认是空数组
                categories: [],

                // 标签
                tagInputVisible: false,
                tagName: '',

                article: {
                    type: 1,    // 技术类
                    title: '',
                    overview: '',
                    content: '',
                    tags: {},
                    options: [],
                    published: false
                }
            }
        },

        methods: {
            /**
             * 加载分类的数据
             */
            loadCategories: function() {
                Vue.axios.get(this.resource._category_manage + 'list?parentId=0').then((response) => {
                    if (response.status === 200) {
                        this.categories = response.data;
                    }
                })
            },

            showTagQueryInput: function() {
                this.tagInputVisible = true;
                this.$nextTick(() => {
                    this.$refs.tagInput.focus();
                });
            },

            fetchTags: function(name, callback) {
                Vue.axios.get(this.resource._tag_manage + 'list?name=' + name).then((response) => {
                    if (response.status === 200) {
                        let data = response.data;
                        if (data.length > 0) {
                            data.forEach(function (p) {
                                p.value = p.name;
                            });
                            callback(data);
                        }
                    }
                })
            },

            confirmTagInput: function (tag) {
                if (tag.id) {
                    this.article.tags[tag.name] = tag.id;
                } else {
                    this.article.tags[this.tagName] = '';
                }
                this.tagInputVisible = false;
                this.tagName = '';
                console.log(this.article.tags);
            },

            removeTag(tagName) {
                if (tagName) {
                    Vue.delete(this.article.tags, tagName);
                }
            },

            increase: function (published) {

                let article = this.article;
                article.published = published;
                article.categoryId = '402881ee677f023501677f0235c60000';
                article.tags = {
                    'JavaScript回调': '12344',
                    'Github': ''
                };

                Vue.axios.post(this.resource._article_manage, article)
                    .then((response) => {
                        console.log(response)
                    });
            }
        },

        created: function () {
            console.log(this);

            this.loadCategories();
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
