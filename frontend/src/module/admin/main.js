// import admin module router rules
import Vue from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import store from './store'
import router from './router/router';
import 'vue-animate';
import App from './App';

Vue.use(VueAxios, axios)

Vue.config.productionTip = false

// import element-ui framework
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

// import vue markdown editor
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor);

// import style with less
import './assets/index.less';
import "./assets/style/theme/star.less";
import "./assets/style/theme/gradual.less";

new Vue({
    el: '#admin_root',
    store: store,
    router: router,
    render: h => h(App)
})

