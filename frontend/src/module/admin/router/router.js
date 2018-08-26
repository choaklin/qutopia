import Vue from 'vue';
import VueRouter from 'vue-router';
// 默认加载index.vue
import desktop from '../components/desktop';

//== 使用模块化机制编程，导入Vue和VueRouter，要调用 Vue.use(VueRouter)
Vue.use(VueRouter)
export default new VueRouter({
    routes: [
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
                    path: 'post/add',
                    name: "添加文章",
                    component: () => import ('../views/post/add')
                },
                {
                    path: 'post/index',
                    name: "文章管理",
                    component: () => import ('../views/post/index')
                },
                {
                    path: 'post/draft',
                    name: "草稿箱",
                    component: () => import ('../views/post/draft')
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
        }
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
