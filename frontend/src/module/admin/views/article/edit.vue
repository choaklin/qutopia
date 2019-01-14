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
                    <el-button type="primary" @click="submit(true)">发表文章</el-button>
                    <el-button @click="submit(false)">保存草稿</el-button>
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

                article: {
                    type: 1,    // 技术类
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
                }
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
                                // 扩展value属性， el-autocomplete规范
                                p.value = p.name;
                            });
                            callback(data);
                        }
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
                this.tagName = '';
                console.log(this.article.tagMap);
            },

            removeTag(tagName) {
                if (tagName) {
                    Vue.delete(this.article.tagMap, tagName);
                }
            },

            submit: function (published) {
                let article = this.article;
                article.published = published;
                Vue.axios.post(this.resource._article_manage, article)
                    .then((response) => {
                        if (response.status === 200) {
                            alert("添加文章成功")
                        }
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