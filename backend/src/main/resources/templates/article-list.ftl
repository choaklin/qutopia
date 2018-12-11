<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>文章列表 - 浮闲阁</title>
        <link href="../public/style/article-list.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="main">
                <div class="articles left">
                    <article class="card" style="transition: opacity 0.3s ease-out 0s, transform 0.3s ease-out 0s; opacity: 1; transform: scale(1); transform-origin: center top 0px;">
                        <div class="thumbnail">
                            <img class="thumbnail" src="../public/images/article/desert.jpg" alt="JavaScript的执行环境和作用域">
                        </div>
                        <header class="title">
                            <h2><a rel="bookmark" href="#">JavaScript的执行环境和作用域</a></h2>
                        </header>
                        <section class="overview">
                            <span>执行环境（execution context）是JavaScript中最为重要的一个概念。</span>
                        </section>
                        <footer class="tags">
                            <a class="tag" href="#">JavaScript的作用域</a>
                            <a class="tag" href="#">Docker</a>
                            <a class="tag" href="#">Mac OS X</a>
                        </footer>
                    </article>
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
                                <li class="root-parent">
                                    <a href="#" class="item">
                                        <span class="title">JavaSE</span>
                                        <span class="quantity">16</span>
                                    </a>
                                    <ul class="sub">
                                        <li>
                                            <a href="#" class="item">
                                                <span class="title">泛型</span>
                                                <span class="quantity">7</span>
                                            </a>
                                        </li>
                                        <li class="sub-parent">
                                            <a href="#" class="item">
                                                <span class="title">IO</span>
                                                <span class="quantity">9</span>
                                            </a>
                                            <ul class="sub">
                                                <li>
                                                    <a href="#" class="item">
                                                        <span class="title">输入</span>
                                                        <span class="quantity">6</span>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" class="item">
                                                        <span class="title">输出</span>
                                                        <span class="quantity">3</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#" class="item">
                                        <span class="title">JavaEE</span>
                                        <span class="quantity">16</span>
                                    </a>
                                    <ul class="sub">
                                        <li>
                                            <a href="#" class="item">
                                                <span class="title">Servlet</span>
                                                <span class="quantity">6</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#" class="item">
                                                <span class="title">JSP</span>
                                                <span class="quantity">3</span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                                <li></li>
                            </ul>
                        </div>
                    </div>

                    <div class="card sidebar-item archive">
                        <div class="sidebar-header">
                            <span class="sidebar-header-title">归档</span>
                        </div>
                        <div class="sidebar-content"></div>
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
            $(document).ready(function () {
                // $(".card").addClass("boom-animation");
            })
        </script>
    </body>
</html>