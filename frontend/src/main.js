import Vue from 'vue'
import App from './App.vue'
import VueDOMPurifyHTML from 'vue-dompurify-html'
import store from './store'
import router from './router'

// Bootstrap-vue plugin start
import 'bootstrap/dist/css/bootstrap.min.css'
import {
  ModalPlugin,
  DropdownPlugin,
  PaginationPlugin,
  NavbarPlugin,
  ButtonPlugin,
  FormPlugin,
  FormInputPlugin,
  FormTextareaPlugin
} from 'bootstrap-vue'

Vue.use(ModalPlugin)
Vue.use(DropdownPlugin)
Vue.use(PaginationPlugin)
Vue.use(NavbarPlugin)
Vue.use(ButtonPlugin)
Vue.use(FormPlugin)
Vue.use(FormInputPlugin)
Vue.use(FormTextareaPlugin)
// Bootstrap-vue plugin end

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
