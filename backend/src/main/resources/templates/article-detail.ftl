<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>文章详情</title>
        <link href="../public/style/article.css" rel="stylesheet"/>

		<link href="../public/lib/tocbot/tocbot.css" rel="stylesheet">
        <link href="../public/lib/prism/prism-oakidia.css" rel="stylesheet">
	</head>
	<body>

		<div class="container">
            <nav class="toc js-toc"></nav>
			<article class="post" style="display: block;">
				<!-- thumbnail & title -->
				<div class="thumbnail" style="background-image: url(../public/images/tourist/chaka.JPG)">
					<h1 class="title">${article.title}</h1>
				</div>

				<!-- post meta info -->
				<div class="meta">
				</div>

				<!-- post content-->
				<div class="content">
					${article.content}
				</div>
			</article>


			<nav class="material-nav mdl-color-text--grey-50 mdl-cell mdl-cell--12-col">
				<a href="/2017/04/04/Story-of-Journey/" id="post_nav-newer" class="prev-content">
					<button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon mdl-color--white mdl-color-text--grey-900" role="presentation" data-upgraded=",MaterialButton,MaterialRipple"><i class="material-icons">arrow_back</i><span class="mdl-button__ripple-container"><span class="mdl-ripple is-animating" style="width: 92.5097px; height: 92.5097px; transform: translate(-50%, -50%) translate(15px, 16px);"></span></span></button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 新篇
				</a>
				<div class="section-spacer"></div>
				<a href="/2017/01/20/Raspi-source-wifi-static-ip/" id="post_nav-older" class="next-content">旧篇 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon mdl-color--white mdl-color-text--grey-900" role="presentation" data-upgraded=",MaterialButton,MaterialRipple">
					<i class="material-icons">arrow_forward</i>
						<span class="mdl-button__ripple-container"><span class="mdl-ripple"></span></span>
					</button>
				</a>
			</nav>
		</div>
	</body>

	<script src="../public/lib/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="../public/lib/prism/clipboard.min.js" type="text/javascript"></script>
	<script src="../public/lib/prism/prism.js" type="text/javascript"></script>
	<script src="../public/lib/tocbot/tocbot.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="../public/lib/nicescroll/jquery.nicescroll.min.js"></script>

	<script>
        // initial post table of content
		$(document).ready(function () {
            tocbot.init({
                tocSelector: '.js-toc',
                contentSelector: '.post',
                headingSelector: 'h1,h2,h3',
                positionFixedSelector: '.js-toc',
                headingsOffset: 10,
                collapseDepth: 3
            });

            $('body').niceScroll();
        });
	</script>
</html>