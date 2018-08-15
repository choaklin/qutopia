// import style with less
import './assets/index.less'
import "vue-animate";

// import admin module router rules
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/index'

Vue.config.productionTip = false

//== define variables  only for index pages
let data = {
    hideSub: false
}

//== define event methods only for index pages
const  methods = {
    collapseNav: function (e) {
        this.hideSub = !this.hideSub;
    }
}

new Vue({
    el: '#admin',
    router: router,
    data: data,
    methods: methods
})

