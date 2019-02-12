
<#macro header>
    <!-- 页面头部 -->
    <header class="header">
        <button id="open-button" class="menu-button">Open Menu</button>
    </header>

    <!-- 功能菜单导航 -->
    <nav class="off-canvas">
        <nav class="menu">
            <menu class="icon-list">
                <a href="#"><i class="fa fa-fw fa-star-o"></i><span>Favorites</span></a>
                <a href="#"><i class="fa fa-fw fa-bell-o"></i><span>Alerts</span></a>
                <a href="#"><i class="fa fa-fw fa-envelope-o"></i><span>Messages</span></a>
                <a href="#"><i class="fa fa-fw fa-comment-o"></i><span>Comments</span></a>
                <a href="#"><i class="fa fa-fw fa-bar-chart-o"></i><span>Analytics</span></a>
                <a href="#"><i class="fa fa-fw fa-newspaper-o"></i><span>Reading List</span></a>
            </menu>
        </nav>
        <button class="close-button" id="close-button">Close Menu</button>

        <!-- 动画路径 -->
        <div id="morph-shape" class="morph-shape" data-morph-open="M-7.312,0H15c0,0,66,113.339,66,399.5C81,664.006,15,800,15,800H-7.312V0z;M-7.312,0H100c0,0,0,113.839,0,400c0,264.506,0,400,0,400H-7.312V0z">
            <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 100 800" preserveAspectRatio="none">
                <path d="M-7.312,0H0c0,0,0,113.839,0,400c0,264.506,0,400,0,400h-7.312V0z"/>
            </svg>
        </div>
    </nav>
</#macro>