import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import Toasted from 'vue-toasted'

Vue.config.productionTip = false
Vue.use(mavonEditor)
Vue.use(Toasted, {router})

new Vue({
    router,
    vuetify,
    render: h => h(App)
}).$mount('#app')
