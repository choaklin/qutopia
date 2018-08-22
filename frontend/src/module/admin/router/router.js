import Vue from 'vue'
import VueRouter from 'vue-router'
// 默认加载index.vue
import desktop from '../components/desktop'

//== 使用模块化机制编程，导入Vue和VueRouter，要调用 Vue.use(VueRouter)
Vue.use(VueRouter)
export default new VueRouter({
    routes: [
        {
            path: '/',
            component: desktop
        }
    ]
})
