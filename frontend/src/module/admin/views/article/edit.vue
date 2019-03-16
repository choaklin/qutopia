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
                    <mavon-editor v-model="article.content" :toolbars="editorToolbar" codeStyle="dracula"/>
                </el-form-item>

                <el-form-item required label="类型">
                    <div style="float: left;">
                        <el-radio-group v-model="article.createType" @change="handleCreateTypeChange">
                            <el-radio :label="'original'">原创</el-radio>
                            <el-radio :label="'reproduce'">转载</el-radio>
                        </el-radio-group>
                    </div>
                    <div style="float: left; margin-left: 15px; width: 85%">
                        <el-input size="small" v-model="article.reproduceUrl" :disabled="article.createType==='original'" placeholder="输入转载的原文链接"></el-input>
                    </div>
                </el-form-item>

                <el-form-item required label="分类">
                    <el-cascader v-model="selectedCategory" :options="categories" filterable change-on-select></el-cascader>

                    <!--<el-select size="middle" v-model="article.categoryId">-->
                        <!--<el-option v-for="item in categories"-->
                                   <!--:key="item.id"-->
                                   <!--:label="item.name"-->
                                   <!--:value="item.id">-->
                        <!--</el-option>-->
                    <!--</el-select>-->
                </el-form-item>

                <el-form-item label="标签">
                    <el-tag closable :disable-transitions="false"
                            v-for="(value, key) in article.tagMap" :key="key" @close="removeTag(key)">
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

                <!--<el-form-item label="选项">-->
                    <!--<el-checkbox-group v-model="article.options">-->
                        <!--<el-checkbox label="置顶" name="putPop">置顶</el-checkbox>-->
                        <!--<el-checkbox v-model="article.commentable" label="true" name="commentable">禁止评论</el-checkbox>-->
                    <!--</el-checkbox-group>-->
                <!--</el-form-item>-->

                <el-form-item style="text-align: center">
                    <el-button type="primary" @click="submit(true)">保存更改</el-button>
                    <el-button @click="cancel">取消编辑</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>

    import Vue from 'vue';
    import httpClient from '../../util/http-client';

    export default {
        name: "article-edit",
        data: function () {
            return {
                resource: {
                    _category_manage: 'categoryManage/',
                    _tag_manage: 'tagManage/',
                    _article_manage: 'articleManage/'
                },

                editorToolbar: {
                    bold: true, // 粗体
                    italic: true, // 斜体
                    header: true, // 标题
                    underline: true, // 下划线
                    strikethrough: true, // 中划线
                    mark: true, // 标记
                    quote: true, // 引用
                    ol: true, // 有序列表
                    ul: true, // 无序列表
                    link: true, // 链接
                    imagelink: true, // 图片链接
                    code: true, // code
                    table: true, // 表格
                    fullscreen: true, // 全屏编辑
                    readmodel: true, // 沉浸式阅读
                    help: true, // 帮助
                    /* 1.3.5 */
                    undo: true, // 上一步
                    redo: true, // 下一步
                    trash: true, // 清空
                    save: true, // 保存（触发events中的save事件）
                    /* 1.4.2 */
                    navigation: true, // 导航目录
                    /* 2.2.1 */
                    subfield: true, // 单双栏模式
                },

                // 分类, 默认是空数组
                categories: [],

                // 标签
                tagInputVisible: false,
                tagName: '',

                articleId: null,
                article: {
                    title: '',
                    overview: '',
                    content: '',
                    createType: 'original',
                    reproduceUrl: '',
                    categoryId: '',
                    // key是标签名称， value是标签ID
                    // 非系统存在的标签value是空字符串
                    tagMap: {},
                    // options: [],
                    commentable: true,
                    published: false
                },
                selectedCategory: []
            }
        },

        methods: {
            htmlCode: function(status, value) {
                console.log(status);
                console.log(value);
            },

            handleCreateTypeChange: function(createType) {
                if (createType) {
                    // 原创，则转载地址置空
                    if (createType === 'original') {
                        this.article.reproduceUrl = '';
                    }
                }
            },

            fetchArticle: function(id) {
                httpClient({
                    url: this.resource._article_manage + id,
                    method: 'GET'
                }).then((response) => {
                    this.article.title = response.title;
                    this.article.overview = response.overview;
                    this.article.content = response.content;
                    this.article.createType = response.createType;
                    this.article.reproduceUrl = response.reproduceUrl;
                    let categoryPath = response.categoryPath;
                    if (categoryPath) {
                        this.selectedCategory = categoryPath.split('/');
                    }
                    this.article.categoryId = this.selectedCategory.length == 2 ? this.selectedCategory[1] : null;

                    // key是标签名称， value是标签ID
                    // 非系统存在的标签value是空字符串
                    let articleTags = response.articleTags;
                    if (articleTags) {
                        articleTags.forEach(item => {
                            this.article.tagMap[item.name] = item.tagId;
                        });
                    }
                    this.article.commentable = response.commentable;
                    this.article.published = response.published;
                });
            },

            /**
             * 加载分类的数据
             */
            loadCategories: function() {
                httpClient.get(this.resource._category_manage + 'list?parentId=0').then((response) => {
                    this.categories = response;
                    this.categories.forEach(item => {
                        item.value = item.id;
                        item.label = item.name;

                        if (item.children) {
                            item.children.forEach(child => {
                                child.value = child.id;
                                child.label = child.name;
                            })
                        }
                    });
                })
            },

            showTagQueryInput: function() {
                this.tagInputVisible = true;
                this.$nextTick(() => {
                    this.$refs.tagInput.focus();
                });
            },

            fetchTags: function(name, callback) {
                httpClient.get(this.resource._tag_manage + 'list?dimension=TECHNIQUE&name=' + name).then((response) => {
                    if (response.length > 0) {
                        response.forEach(function (p) {
                            // 扩展value属性， el-autocomplete规范
                            p.value = p.name;
                        });
                        callback(response);
                    }
                })
            },

            confirmTagInput: function (tag) {
                if (tag.id) {
                    this.article.tagMap[tag.name] = tag.id;
                } else {
                    this.article.tagMap[this.tagName] = '';
                }
                this.tagInputVisible = false;
                this.tagName = null;
                console.log(this.article.tagMap);
            },

            removeTag: function(tagName) {
                // debugger
                if (tagName) {
                    Vue.delete(this.article.tagMap, tagName);
                }
            },

            submit: function (published) {
                let article = this.article;
                article.published = published;

                let hierarchyCount = this.selectedCategory.length;
                if ( hierarchyCount < 2 ) {
                    this.$message({
                        message: '请选择一个二级文章分类',
                        type: 'warning'
                    });
                    return;
                } else {
                    // 使用第二级ID
                    article.categoryId = this.selectedCategory[1];
                }

                let  tagCount = 0;
                let tags = article.tagMap;
                for (let tag in tags) {
                    if (tags.hasOwnProperty(tag)) {
                        tagCount += 1;
                    }
                }
                if (tagCount < 1) {
                    this.$message({
                        message: '脑子呢，想不出一个标签吗？',
                        type: 'warning'
                    });
                    return;
                }

                // Vue.axios.put(this.resource._article_manage + this.articleId, article)
                httpClient({
                    url: this.resource._article_manage + this.articleId,
                    method: 'PUT',
                    data: article
                }).then((response) => {
                    this.$message({
                        message: '文章更新成功',
                        type: 'success'
                    });

                    this.$router.push({path: "/article/index"});
                });
            },

            cancel: function () {
                this.$router.push({path: "/article/index"});
            }
        },

        created: function () {
            this.articleId = this.$route.params.id;

            this.loadCategories();
            this.fetchArticle(this.$route.params.id)
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
