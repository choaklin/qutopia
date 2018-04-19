// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import templates from './App'
import style from './assets/home.less'
import router from './router/'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#index1',
    /*router,*/
    components: {templates},
    template: '<templates/>',
    data: {
        name: 'vue',
        message: 'Vue的生命周期'
    }
})
