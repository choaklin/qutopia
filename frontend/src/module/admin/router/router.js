import 'nprogress/nprogress.css'// progress bar style

import Vue from 'vue';
import store from '../store'
import VueRouter from 'vue-router';
import nprogress from 'nprogress'
import desktop from '../components/desktop';

//== 使用模块化机制编程，导入Vue和VueRouter，要调用 Vue.use(VueRouter)
Vue.use(VueRouter);

/**
 * nprogress的配置
 *
 * @see http://ricostacruz.com/nprogress/
 */
nprogress.configure({
    showSpinner: false
});

const router = new VueRouter({
    routes: [
        {
            path: '/login',
            name: '登陆',
            component: () => import ('../views/login')
        },
        {
            path: '/',
            name: "后台管理主页",
            component: desktop,
            // 默认转发到[主页]菜单
            redirect: '/home',
            children: [
                // 各个菜单
                {
                    path: 'home',
                    name: "主页",
                    component: () => import ('../views/home')
                },
                {
                    path: 'article/add',
                    name: "添加文章",
                    component: () => import ('../views/article/add')
                },
                {
                    path: 'article/index',
                    name: "文章管理",
                    component: () => import ('../views/article/index')
                },
                {
                    path: 'article/edit/:id',
                    name: "文章编辑",
                    component: () => import ('../views/article/edit')
                },
                {
                    path: 'article/draft',
                    name: "草稿箱",
                    component: () => import ('../views/article/draft')
                },
                {
                    path: 'comment',
                    name: "评论管理",
                    component: () => import ('../views/comment')
                },
                {
                    path: 'link',
                    name: '友链管理',
                    component: () => import ('../views/link')
                },
                {
                    path: 'about',
                    name: "关于",
                    component: () => import ('../views/about')
                }
            ],
        },
    ],

    scrollBehavior: function (to, from, savedPosition) {
        if (savedPosition) {
            console.log(savedPosition);
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    }
})

// 定义白名单: 未登陆状态下允许访问页面的
const whiteList = ['/login'];
router.beforeEach((to, from, next) => {
    // start progress bar
    nprogress.start()
    let target = to.path;
    let token = store.state.authentication.token;
    // determine if there has token
    if (token) {
        console.info('has token')
        // 存在token且不是前往登录页，则放行；否则重定向至 / 路径
        if (target && target.indexOf('login') === -1) {
            next();
        } else {
            next({path: '/'});
        }
        nprogress.done();
    } else {
        // 否则全部重定向到登录页
        console.warn('no token')
        if (whiteList.indexOf(target) !== -1) {
            console.info('>> goto while list page', target);
            next();
        } else {
            console.info('>> forward login page')
            next('/login?redirect=' + target)
            nprogress.done();
        }
    }
})

router.afterEach(() => {
    // finish progress bar
    nprogress.done();
})

export default router
