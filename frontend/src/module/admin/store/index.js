import Vue from 'vue'
import Vuex from 'vuex'
// import app from './modules/app'
import authentication from './modules/authentication'
// import getters from './getters'

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        authentication
    },
    // getters
});

export default store
