<template>
    <div>
        <div class="half-range">
            <el-form ref="form" :model="article" label-width="60px">
                <el-form-item required label="标题">
                    <el-input v-model="article.title" placeholder="文章标题，字数控制在 50 个字以内"></el-input>
                </el-form-item>
                <el-form-item required label="图片">
                    <el-input v-model="article.thumbnail" placeholder="文章的背景图地址"></el-input>
                </el-form-item>
                <el-form-item required label="摘要">
                    <el-input type="textarea" v-model="article.overview" placeholder="无摘要不精彩" :autosize="{minRows:3, maxRows:4}"></el-input>
                </el-form-item>
                <el-form-item required label="内容">
                    <mavon-editor ref="md" v-model="article.content" :toolbars="editorToolbar" codeStyle="dracula" @imgAdd="uploadImage"/>
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
                </el-form-item>

                <el-form-item required label="标签">
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

                <el-form-item :class="scrollDown?'fix-operation-zoom':''" style="text-align: center">
                    <el-button type="primary" @click="submit(true)">发表文章</el-button>
                    <el-button @click="submit(false)">保存草稿</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>

    import Vue from 'vue'
    import httpClient from '../../util/http-client'

    export default {
        name: "article-increase",
        data: function () {
            return {
                scrollTop: 0,
                scrollDown: false,

                resource: {
                    _category_manage: 'categoryManage/',
                    _tag_manage: 'tagManage/',
                    _article_manage: 'articleManage/',
                    _file_manage: 'resource/',
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
                    title: '',
                    thumbnail: '',
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
            // 绑定@imgAdd event
            uploadImage: function(position, $file) {
                // 第一步.将图片上传到服务器.
                let formdata = new FormData();
                formdata.append('file', $file);
                httpClient({
                    url: this.resource._file_manage + 'upload',
                    headers: {'Content-Type': 'multipart/form-data' },
                    method: 'post',
                    data: formdata
                }).then((response) => {
                    // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                    // $vm.$img2Url 详情见本页末尾
                    this.$refs.md.$img2Url(position, response);
                })
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
                    })
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

                httpClient.post(this.resource._article_manage, article)
                    .then((response) => {
                        this.$message({
                            message: '文章添加成功',
                            type: 'success'
                        });
                        this.$router.push({path: "/article/index"});
                    });
            },

            handleScroll: function () {
                if (this) {
                    let scrollTop = this.$parent.$el.scrollTop;
                    // 页面滚动距顶部距离
                    if (scrollTop - this.scrollTop > 0) {
                        this.scrollDown = true;
                    } else {
                        this.scrollDown = false;
                    }
                    this.scrollTop = scrollTop;
                }
            }
        },

        created: function () {
            window.addEventListener('scroll', this.handleScroll, true)

            this.loadCategories();
        }
    }
</script>

<style lang="less">
    .el-tag + .el-tag {
        margin-left: 10px !important;
    }

    .input-new-tag {
        width: 120px !important;
        margin-left: 10px !important;
        height: 32px !important;
        line-height: 32px !important;
        vertical-align: center !important;
        &:first-child {
            margin-left: 0 !important;
        }
    }

    .button-new-tag {
        margin-left: 10px !important;
        height: 32px !important;
        line-height: 32px !important;
        padding-top: 0 !important;
        padding-bottom: 0 !important;
        &:first-child {
            margin-left: 0 !important;
        }
    }

    .fix-operation-zoom {
        position: fixed;
        padding: 15px 0;
        bottom: 0;
        left: 0;
        right: 0;
        width: 100%;
        z-index: 1510;
    }
</style>
