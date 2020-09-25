import Vue from 'vue'
import Vuex from 'vuex'
import http from './util/http-common'
import router from './router'
// import http from '@/util/http-common'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: null,
    isLogin: false
  },
  // state get
  getters: {
    isSignin(state) {
      return state.isLogin
    }
  },
  // state 변경
  mutations: {
    SigninSuccess(state, payload) {
      state.isLogin = true
      state.userInfo = payload
    },
    Signout(state) {
      state.isLogin = false
      state.userInfo = null
    }
  },
  actions: {
    onSignin({ commit }, payload) {
      http
        .post('/user/login', payload, null)
        .then((res) => {
          console.log(res)
          commit('SigninSuccess', res.data.userInfo)

          const token = 'asdfasdfjlawejrljk'
          localStorage.setItem('token', token)
        })
        .catch((err) => {
          console.log(err)
        })
      router.push({ name: 'ArticleList' })
    },
    onSignout({ commit }) {
      commit('Signout')
      localStorage.removeItem('token')
    },
    onSignup({ commit }, payload) {
      http
        .post('/user/signup', payload, null)
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
      router.push({ name: 'ArticleList' })
    }
  }
})
