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
				<div class="content">${article.content}</div>
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


			<div class="post-comment">

				<div class="comment-wrap">
					<header>
						<span class="comment-count">14 条评论</span>
					</header>
					<section>
						<div class="comment-list">
							<ul>
								<li>
									<div class="comment-item">
										<div class="single-reply">
											<div class="avatar">
												<a href="#"><img width="40" height="40" src="../public/images/comment/50.png"></a>
											</div>

											<div class="comment-content">
												<header class="content-header">
												<span class="byline">
													<span class="commenter publisher">日光微澜*ق</span>
												</span>
													<span class="meta">
													<span class="bullet">•</span>
													<span class="device">Chrome</span>
												</span>
												</header>
												<div class="content-body">
													哪里啊使用 npm run install 安装依赖，然后run build 可以生成编译文件使用 npm run install 安装依赖，然后run build 可以生成编译文件使用 npm run install 安装依赖，然后run build 可以生成编译文件
												</div>
												<footer class="comment-footer">
													<span class="time">2016年6月23日</span>
													<a href="javascript:void(0)"><i></i>(976)</a>
													<span class="sep">|</span>
													<a href="javascript:void(0)" class="s-fc3" data-id="53421839" data-type="reply">回复</a>
												</footer>
											</div>
										</div>

										<div class="comment-list comment-sub">
											<ul>
												<li>
													<div class="single-reply">
														<div class="avatar">
															<a href="#"><img width="40" height="40" src="../public/images/comment/choaklin.png"></a>
														</div>

														<div class="comment-content">
															<header class="content-header">
															<span class="byline">
																<span class="commenter publisher">茶马古道</span>回复<span class="commenter respondent">日光微澜*ق</span>
															</span>
																<span class="meta">
																<span class="bullet">•</span>
																<span class="device">a mouth ago</span>
															</span>
															</header>
															<div class="content-body">你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊你猜啊</div>
															<footer class="comment-footer">
															</footer>
														</div>
													</div>
												</li>

												<li>
													<div class="single-reply">
														<div class="avatar">
															<a href="#"><img width="40" height="40" src="../public/images/comment/50.png"></a>
														</div>
														<div class="comment-content">
															<header class="content-header">
															<span class="byline">
																<span class="commenter publisher">日光微澜*ق</span>回复<span class="commenter respondent">茶马古道</span>
															</span>
																<span class="meta">
																<span class="bullet">•</span>
																<span class="device">a mouth ago</span>
															</span>
															</header>
															<div class="content-body">不知道</div>
															<footer class="comment-footer">
															</footer>
														</div>
													</div>
												</li>
											</ul>
										</div>
									</div>

									<div>
										<div>
											<textarea style="width: 100%; resize: none; height: 60px; line-height: 20px; padding: 3px 8px 5px;border-radius: 2px; border-color: #e7e7e7;"></textarea>
										</div>
										<div class="btns f-cb f-pr auto-1497273941609-parent">
											<i class="icn u-icn u-icn-36 j-flag"></i>
											<i class="icn u-icn u-icn-41 j-flag"></i>
											<a href="javascript:void(0)" class="btn u-btn u-btn-1 j-flag" id="auto-id-d2rJ6wvXh6R5DAvW">回复</a>
											<span class="zs s-fc4 j-flag">134</span>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</section>
				</div>
			</div>
		</div>
	</body>

	<script src="../public/lib/prism/clipboard.min.js" type="text/javascript"></script>
	<script src="../public/lib/prism/prism.js" type="text/javascript"></script>
	<script src="../public/lib/tocbot/tocbot.min.js" type="text/javascript"></script>
	<script src="../public/lib/smooth-scroll/smooth-scroll.js" type="text/javascript"></script>
	<script>
        // initial post table of content
		tocbot.init({
			tocSelector: '.js-toc',
			contentSelector: '.post',
			headingSelector: 'h1,h2,h3',
			positionFixedSelector: '.js-toc',
			headingsOffset: 10,
			collapseDepth: 3
		});
	</script>
</html>