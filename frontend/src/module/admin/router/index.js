import Vue from 'vue'
import VueRouter from 'vue-router'
import adminHome from '../components/home'

import addPost from '../components/post/add'
import postIndex from '../components/post/index'
import draftPost from '../components/post/draft'

import comment from '../components/comment'
import about from  '../components/about'

//== 使用模块化机制编程，导入Vue和VueRouter，要调用 Vue.use(VueRouter)
Vue.use(VueRouter)
export default new VueRouter({
    routes: [
        {
            path: '/home',
            component: adminHome
        },
        
        //== article center
        {
            path: '/article/add',
            component: addPost
        },
        {
            path: '/article/index',
            component: postIndex
        },
        {
            path: '/article/draft',
            component: draftPost
        },
    
        
        {
            path: '/comment',
            component: comment
        },
        {
            path: '/about',
            component: about
        },
    ]
})
