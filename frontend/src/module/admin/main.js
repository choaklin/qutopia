// import style with less
import './assets/index.less';

// import admin module router rules
import Vue from 'vue';
import router from './router/router';
import 'vue-animate';
import App from './App';

Vue.config.productionTip = false

// import element-ui framework
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

new Vue({
    el: '#admin_root',
    router: router,
    render: h => h(App)
})

