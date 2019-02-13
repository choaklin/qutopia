<#include "micro/header.ftl">
<#include "micro/footer.ftl">

<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>文章列表 - 浮闲阁</title>
        <link href="../public/font/iconfont.css" rel="stylesheet" type="text/css"/>
        <link href="../public/lib/nprogress/nprogress.css" rel="stylesheet" type="text/css"/>
        <link href="../public/style/article-list.css" rel="stylesheet" type="text/css"/>
        <link href="../public/style/theme.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <!-- 页面头部及抽屉菜单 -->
        <@header/>

        <!-- 内容主题 -->
        <main class="container">
            <div class="container-wrap">
                <div class="column col-640 main-content">
                    <div id="search_bar" class="card search-bar" style="display: none;">
                        <div class="card-content">
                            <nav id="search_bar_category" class="breadcrumb" style="display: none;">
                                <ul id="category_search_content"></ul>
                            </nav>

                            <nav id="search_bar_tag" class="menu" style="display: none">
                                <p class="menu-label">标签</p>
                                <div id="tag_search_content" class="field is-grouped is-grouped-multiline">
                                    <div class="control">
                                        <a class="tags has-addons" href="/hexo-theme-icarus/tags/Advanced-Topics/">
                                            <span class="tag">Advanced Topics</span>
                                            <span class="tag is-grey">1</span>
                                        </a>
                                    </div>
                                </div>
                            </nav>
                        </div>
                    </div>

                    <div id="article_container" class="article">
                        <#if articles??>
                            <#list articles as article>
                                <article class="card" style="transition: opacity 0.3s ease-out 0s, transform 0.3s ease-out 0s; opacity: 1; transform: scale(1); transform-origin: center top 0px;">
                                    <div class="thumbnail">
                                        <img class="thumbnail" src="../public/images/article/desert.jpg" alt="${article.title}">
                                        <header class="title">
                                            <h2><a rel="bookmark" href="article/${article.id}">${article.title}</a></h2>
                                        </header>
                                    </div>
                                    <section class="overview">
                                        <span>${article.overview!""}</span>
                                    </section>
                                    <footer class="tags">
                                        <div class="tags">
                                            <#if article.articleTags??>
                                                <#list article.articleTags as tag>
                                                    <a class="tag" <#if tag.desc??>data-tippy="${tag.desc}"</#if> href="#">${tag.name}</a>
                                                </#list>
                                            </#if>
                                        </div>
                                        <div class="publish-time">
                                            <span><i class="iconfont date"></i>${article.createTime?string('yyyy-MM-dd')}</span>
                                        </div>
                                    </footer>
                                </article>
                            </#list>
                        </#if>
                    </div>
                </div>

                <div class="column col-290">
                    <div class="card sidebar-item search">
                        <div class="sidebar-header">
                            <span class="sidebar-header-title">搜索</span>
                        </div>
                        <div class="sidebar-content">
                            <div class="search-input">
                                <input id="search_keyword" type="text" name="keyword" placeholder="请输入关键字搜索">
                                <button id="search_submit_btn" type="submit" class="search-submit"></button>
                            </div>
                        </div>
                    </div>
                    <div class="card sidebar-item catalog">
                        <div class="sidebar-header">
                            <span class="sidebar-header-title">分类</span>
                        </div>
                        <div class="sidebar-content">
                            <ul class="root">
                                <#if categories??>
                                    <#list categories as category>
                                        <li class="root-parent">
                                            <a href="#" class="item" data-id="${category.id}">
                                                <span class="title">
                                                    <i class="iconfont icon-${category.icon!''}"></i>
                                                    ${category.name}
                                                </span>
                                                <span class="quantity">${category.articleCount}</span>
                                            </a>
                                            <#if category.children??>
                                                <ul class="sub" style="display:none">
                                                <#list category.children as children>
                                                    <li>
                                                        <a href="#" class="item" data-id="${children.id}">
                                                            <span class="title">${children.name}</span>
                                                            <span class="quantity">${children.articleCount}</span>
                                                        </a>
                                                    </li>
                                                </#list>
                                                </ul>
                                            </#if>
                                        </li>
                                    </#list>
                                </#if>
                            </ul>
                        </div>
                    </div>

                    <div class="card sidebar-item tags">
                        <div class="sidebar-header">
                            <span class="sidebar-header-title">标签</span>
                        </div>
                        <div class="sidebar-content">
                            <#if tags??>
                                <div id="tag_search_content" class="field is-grouped is-grouped-multiline">
                                    <#list tags as tag>
                                        <div class="control">
                                            <a class="tags has-addons" <#if tag.desc??>data-tippy="${tag.desc}"</#if> href="/hexo-theme-icarus/tags/Advanced-Topics/">
                                                <span class="tag"><i class="iconfont icon-qiye"></i>${tag.name}</span>
                                                <span class="tag is-grey">${tag.publishedCount}</span>
                                            </a>
                                        </div>
                                    </#list>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </main>


        <!-- 底部菜单 -->
        <@footer/>

        <script type="text/javascript" src="../public/lib/jquery/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="../public/lib/nprogress/nprogress.js"></script>
        <script type="text/javascript" src="../public/lib/nicescroll/jquery.nicescroll.min.js"></script>
        <script type="text/javascript" src="../public/lib/tippy/tippy.all.min.js"></script>
        <script type="text/javascript" src="../public/lib/svg/snap.svg-min.js"></script>
        <script type="text/javascript" src="../public/lib/classie.js"></script>
        <script type="text/javascript" src="../public/js/off-canvas.min.js"></script>

        <script>
            var categories = {};
            <#if categories??>
                <#list categories as parent>
                    categories['${parent.id}'] = {
                        id: '${parent.id}',
                        name: '${parent.name}',
                        parentId: '${parent.parentId}'
                    };
                    <#if parent.children??>
                        <#list parent.children as children>
                            categories['${children.id}'] = {
                                id: '${children.id}',
                                name: '${children.name}',
                                parentId: '${children.parentId}'
                            };
                        </#list>
                    </#if>
                </#list>
            </#if>
            ;
            // 页面所有元素加载完隐藏进度条
            window.onload = function () {
                NProgress.done();
            }
            // 页面刷新执行进度条
            window.onbeforeunload = function () {
                NProgress.start();
            }
            $(document).ready(function () {
                NProgress.start();
                tippy.setDefaults({
                    position: 'top',
                    arrow: true,
                    delay: 200,
                    animation: 'perspective',
                    interactive: true
                });

                var data = {
                    isSearchBarDisplay: false,
                    /**
                     * 空字符，代表全部文章
                     * @see template#buildCategorySearchContent
                     */
                    currentCategoryId: '',
                    keyword: '',

                    // 从0开始
                    pageNo: 0,
                    pageSize: 5
                };

                var node = {
                    nice: null,
                    searchBar: $('#search_bar'),
                    searchBarCategory: $('#search_bar_category'),
                    categorySearchContent: $('#category_search_content'),
                    searchBarTag: $('#search_bar_tag'),
                    tagSearchContent: $('#tag_search_content'),

                    articleContainer: $('#article_container'),

                    searchKeyword: $('#search_keyword'),
                    searchSubmitBtn: $('#search_submit_btn'),
                };

                var template = {
                    buildArticleCard: function(articles) {
                        var temp = [];
                        if (!articles) {
                            return '';
                        }
                        for (var a = 0; a < articles.length; a++) {
                            var article = articles[a];

                            temp.push('<article class="card" style="transition: opacity 0.3s ease-out 0s, transform 0.3s ease-out 0s; opacity: 1; transform: scale(1); transform-origin: center top 0px;">');
                            temp.push('<div class="thumbnail"><img class="thumbnail" src="../public/images/article/desert.jpg" alt="' + article.title + '"></div>');
                            temp.push('<header class="title"><h2><a rel="bookmark" href="article/' + article.id + '">' + article.title + '</a></h2></header>');
                            temp.push('<section class="overview"><span>' + article.overview + '</span></section>');
                            temp.push('<footer><div class="tags">');
                            var tags = articles.articleTags;
                            if (tags) {
                                var tagCount = tags.length;
                                for (var i = 0; i < tagCount; i++) {
                                    var tag = tags[i];
                                    if (tag.desc) {
                                        temp.push('<a class="tag" href="#" data-tippy="' + tag.desc+ '">' + tag.name + '</a>');
                                    } else {
                                        temp.push('<a class="tag" href="#">' + tag.name + '</a>');
                                    }

                                }
                            }
                            temp.push('</div>')
                            temp.push('<div><span><i class="iconfont date"></i>' + article.createTime + '</span></div>')
                            temp.push('</footer>');
                            temp.push('</article>');
                        }
                        return temp.join('');
                    },

                    buildCategorySearchContent: function (parent, child) {
                        var temp = [];
                        temp.push('<li data-id="0"><a href="#" data-id="">全部</a></li>');
                        if (parent) {
                            temp.push('<li data-id="' + parent.id + '" ><a href="#" data-id="' + parent.id+ '">' + parent.name + '</a></li>');
                        }
                        temp.push('<li data-id="' + child.id + '" class="is-active"><a href="#" data-id="' + child.id+ '">' + child.name + '</a></li>');
                        return temp.join('');
                    }
                };

                var services = {

                    /**
                     * 变更文章分类
                     *
                     * @param categoryId 分类ID
                     */
                    changeCategory: function(categoryId) {

                        var category = categories[categoryId];
                        if (!categoryId) {
                            console.error("未匹配到文章分类");
                            return;
                        }

                        if (data.currentCategoryId === categoryId) {
                            console.warn("当前选中的分类已是: " + category.name);
                            return;
                        } else {
                            data.currentCategoryId = categoryId;
                        }

                        console.debug(category);
                        node.categorySearchContent.html(template.buildCategorySearchContent(
                            categories[category.parentId], category
                        ));
                        node.searchBarCategory.show();

                        utils.refreshSearchBar();
                        this.getArticles(true);
                    },

                    /**
                     * 获取文章分页
                     *
                     * @param restart 页码是否重新开始
                     */
                    getArticles: function (restart) {

                        data.pageNo = restart ? 0 : data.pageNo;

                        $.ajax({
                            type: 'GET',
                            url: '/article/page',
                            data: {
                                page: data.pageNo,
                                size: data.pageSize,

                                categoryId: data.currentCategoryId,
                                title: data.keyword
                            },
                            beforeSend: function (xhr) {
                                NProgress.start();
                            },
                            success: function (result, status, xhr) {
                                // node.articleContainer.html(
                                //     template.buildArticleCard(result.content)
                                // );
                            },
                            complete: function (xhr, status) {
                                node.nice.resize();
                                NProgress.done();
                            }
                        });
                    }
                };

                var utils = {
                    refreshSearchBar: function () {
                        if (data.currentCategoryId) {
                            if (!data.isSearchBarDisplay) {
                                node.searchBar.slideDown();
                                data.isSearchBarDisplay = true;
                            }
                        } else {
                            node.searchBar.slideUp();
                            data.isSearchBarDisplay = false;
                            data.pageNo = 0;
                        }
                    }
                };

                var event = {
                    publish: function () {
                        this.categoryChangeHandle();
                        this.categoryChangeFromSearchBarHandle();
                        this.searchInputKeyPressHandle();
                        this.searchBtnClickHandle();

                        node.nice = $("body").niceScroll();
                    },

                    /**
                     * 文章分类的点击事件拦截处理器
                     */
                    categoryChangeHandle: function () {
                        $('a.item').on('click', function (e) {
                            e.preventDefault();
                            var delegate = $(e.delegateTarget);

                            var categoryId = delegate.attr('data-id');
                            var category = categories[categoryId];
                            if (category.parentId !== '0') {
                                services.changeCategory(categoryId);
                            }
                            delegate.siblings('ul').slideToggle(function () {
                                node.nice.resize();
                            });
                        })
                    },

                    /**
                     * 来自面包屑导航的文章分类的点击事件拦截处理器
                     */
                    categoryChangeFromSearchBarHandle: function () {
                        $('ul#category_search_content').on('click', function (e) {
                            e.preventDefault();
                            var target = $(e.target);

                            var categoryId = target.attr('data-id');
                            // 查询全部文章
                            if (categoryId === '') {
                                data.currentCategoryId = '';
                                utils.refreshSearchBar();
                                services.getArticles();
                            } else {
                                services.changeCategory(categoryId);
                            }
                        });
                    },

                    /**
                     * 查询输入文本框的键盘输入处理器，主要监听enter
                     */
                    searchInputKeyPressHandle: function () {
                        node.searchKeyword.on('keypress', function (e) {

                            if (e.keyCode === 13) {
                                console.log('输入了enter');

                                var keyword = node.searchKeyword.val();
                                if ($.trim(keyword)) {
                                    data.keyword = keyword;
                                    services.getArticles(true)
                                }
                            }
                        });
                    },

                    /**
                     * 查询按钮的点击处理器
                     */
                    searchBtnClickHandle: function () {
                        node.searchSubmitBtn.on('click', function (e) {
                            e.preventDefault();

                            var keyword = node.searchKeyword.val();
                            if ($.trim(keyword)) {
                                data.keyword = keyword;
                                services.getArticles(true)
                            }
                        });
                    }
                };

                event.publish();
            });
        </script>
    </body>
</html>
