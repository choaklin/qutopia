// import style with less
import './assets/index.less'
import "vue-animate";

import Vue from 'vue'

Vue.config.productionTip = false


new Vue({
    el: '#admin',
    data: {
        hideSub: true
    },
    methods: {
        collapseNav: function (e) {
            this.hideSub = !this.hideSub;
        }
    }
})
