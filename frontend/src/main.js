import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import VueDOMPurifyHTML from 'vue-dompurify-html'
import store from './store'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import router from './router'

Vue.use(BootstrapVue)
Vue.use(VueDOMPurifyHTML)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  beforeCreate() {
    store.dispatch('getUserInfo')
  },
  render: h => h(App)
}).$mount('#app')
