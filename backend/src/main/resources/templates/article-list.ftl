<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>文章列表 - 浮闲阁</title>
        <link href="../public/style/article-list.css" rel="stylesheet" type="text/css"/>
        <link href="../public/font/iconfont.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="main">
                <div class="articles left">
                    <#if articles??>
                        <#list articles as article>
                            <article class="card" style="transition: opacity 0.3s ease-out 0s, transform 0.3s ease-out 0s; opacity: 1; transform: scale(1); transform-origin: center top 0px;">
                                <div class="thumbnail">
                                    <img class="thumbnail" src="../public/images/article/desert.jpg" alt="${article.title}">
                                </div>
                                <header class="title">
                                    <h2><a rel="bookmark" href="article/${article.id}">${article.title}</a></h2>
                                </header>
                                <section class="overview">
                                    <span>${article.overview!""}</span>
                                </section>
                                <footer class="tags">
                                    <#if article.articleTags??>
                                        <#list article.articleTags as tag>
                                            <a class="tag" href="#">${tag.name}</a>
                                        </#list>
                                    </#if>
                                </footer>
                            </article>
                        </#list>
                    </#if>
                </div>

                <div class="sidebar right">
                    <div class="card sidebar-item search">
                        <div class="sidebar-header">
                            <span class="sidebar-header-title">搜索</span>
                        </div>
                        <div class="sidebar-content">
                            <div class="search-input">
                                <input type="text" name="keyword" placeholder="请输入关键字搜索">
                                <button type="submit" class="search-submit"></button>
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
                                            <a href="#" class="item">
                                                <span class="title">
                                                    <i class="iconfont icon-${category.icon!''}"></i>
                                                    ${category.name}
                                                </span>
                                                <span class="quantity">${category.articleCount}</span>
                                            </a>
                                            <#if category.children??>
                                                <ul class="sub" style="display: ${(category_index==0)?string('block', 'none')}">
                                                <#list category.children as children>
                                                    <li>
                                                        <a href="#" class="item">
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
                        <div class="sidebar-content"></div>
                    </div>
                </div>
            </div>

        </div>

        <script type="text/javascript" src="../public/lib/jquery/jquery-1.10.2.min.js"></script>
        <script>
            $('a.item').on('click', function (e) {
                e.preventDefault();
                $(e.delegateTarget).siblings('ul').slideToggle();
            })
        </script>
    </body>
</html>
