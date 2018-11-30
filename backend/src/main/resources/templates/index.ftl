<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>qutopia - choaklin</title>
    <link href="/public/style/index.css" rel="stylesheet"/>
    <link href="/public/lib/fullpage/jquery.fullpage.min.css" rel="stylesheet"/>
</head>

<body>
<canvas id="welcome"></canvas>
<div id="home" style="display: block;">
    <div class="person half-range">
        <div class="figure"><img src="/public/images/figure.png"></div>
        <div class="sign">致虚极 . 守静笃</div>

        <div class="nav">
            <div class="menu"><a href="/archive">博客</a></div>
            <div class="menu"><a href="/archive/index">关于</a></div>
        </div>

        <div class="link">
            <div class="item"><a href="http://weibo.com/crazysos" target="_blank"><img src="/public/images/weibo.png" class="weibo" title="微博" alt="微博"/></a></div>
            <div class="item"><a href="https://github.com/choaklin" target="_blank"><img src="/public/images/github.png" class="github" title="Github" alt="github"/></a></div>
        </div>
    </div>


    <div id="fullpage" class="tourist half-range">
        <div class="section" style="background: url(/public/images/tourist/hangzhou.jpg); background-size: cover;"></div>
        <div class="section" style="background: url(/public/images/tourist/huangshan.jpg); background-size: cover;"></div>
        <div class="section" style="background: url(/public/images/tourist/yunding.jpg); background-size: cover;"></div>
        <div class="section" style="background: url(/public/images/tourist/pingtan.jpg); background-size: cover;"></div>
        <div class="section" style="background: url(/public/images/tourist/chaka.jpg); background-size: cover;"></div>
    </div>
</div>
</body>

<script type="text/javascript" src="/public/lib/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/public/lib/jquery/jquery.easing.1.3.min.js"></script>
<script type="text/javascript" src="/public/lib/fullpage/jquery.fullpage.min.js"></script>

<script type="text/javascript" src="/public/js/index.js"></script>
<script>
        $(document).ready(function() {
            init.waite();
            init.hideWelcome();
            init.showMain();
            $('#fullpage').fullpage({
                loopBottom: true,
                loopTop: true,
                css3: false,
                easing: 'easeOutCubic'
            });
        });
    </script>
</html>