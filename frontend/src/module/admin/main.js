import vue from 'vue'

vue.config.productionTip = false


new vue({
    el: '#admin',
    /*router,*/
    components: {templates},
    template: '<templates/>',
    data: {
        name: 'vue',
        message: 'Vue的生命周期'
    }
})
