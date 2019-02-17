<#include "micro/header.ftl">
<#include "micro/footer.ftl">

<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<title>文章详情</title>
		<link href="../../public/font/iconfont.css" rel="stylesheet" type="text/css"/>
        <link href="../public/style/article-detail.css" rel="stylesheet"/>
		<#--<link href="../public/lib/tocbot/tocbot.css" rel="stylesheet">-->
        <link href="../public/lib/prism/prism-oakidia.css" rel="stylesheet">
		<link href="../public/style/theme.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<!-- 页面头部及抽屉菜单 -->
		<@header/>

		<main class="container">
			<div class="container-wrap">
				<#--<nav class="toc js-toc"></nav>-->
				<article class="post" style="display: block;">
					<!-- thumbnail & title -->
					<div class="thumbnail" style="background-image: url(../public/images/tourist/chaka.JPG)">
						<h1 class="title">${article.title}</h1>
					</div>

					<!-- post meta info -->
					<div class="meta"></div>

					<!-- post content-->
					<div class="content">
						${article.content}
					</div>

					<footer class="license">
						<div class="license-wrap">
							<p><strong>本文使用 <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh">CC BY-NC-SA 4.0</a> 协议许可</strong></p>
							<p><strong>本文标题：</strong>${article.title}</p>
							<p><strong>本文链接：</strong><a href="http:www.baidu.com">http:www.baidu.com</a></p>
						</div>
					</footer>
				</article>
			</div>
		</main>

		<!-- 底部菜单 -->
		<@footer/>
	</body>

	<script src="../public/lib/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="../public/lib/prism/clipboard.min.js" type="text/javascript"></script>
	<script src="../public/lib/prism/prism.js" type="text/javascript"></script>
	<#--<script src="../public/lib/tocbot/tocbot.min.js" type="text/javascript"></script>-->
	<script type="text/javascript" src="../public/lib/nicescroll/jquery.nicescroll.min.js"></script>
	<script type="text/javascript" src="../public/lib/svg/snap.svg-min.js"></script>
	<script type="text/javascript" src="../public/lib/classie.js"></script>
	<script type="text/javascript" src="../public/js/off-canvas.min.js"></script>

	<script>
        // initial post table of content
		$(document).ready(function () {
            // tocbot.init({
            //     tocSelector: '.js-toc',
            //     contentSelector: '.post',
            //     headingSelector: 'h1,h2,h3',
            //     positionFixedSelector: '.js-toc',
            //     headingsOffset: 10,
            //     collapseDepth: 3
            // });
            $('body').niceScroll();
        });
	</script>
</html>