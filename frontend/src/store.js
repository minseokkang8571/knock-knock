import Vue from 'vue'
import Vuex from 'vuex'
import http from './util/http-common'
import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: null,
    isLogin: false,
    articles: [],
    chats: []
  },
  // state get
  getters: {
    isSignin(state) {
      return state.isLogin
    },
    articles(state) {
      return state.articles
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
          commit('SigninSuccess', res.data.user)

          const token = res.data.token
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
    },
    getUserInfo({ commit }) {
      const token = localStorage.getItem('token')
      if (token) {
        http
          .post('/user/info', { token: token }, null)
          .then((res) => {
            console.log(res)
            commit('SigninSuccess', res.data.user.user)
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    createArticle(context, payload) {
      http
        .post('/user/save', payload, null)
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getArticle(context, counter) {
      http
        .get(`article/list?pageNo=${counter}`)
        .then((res) => {
          console.log(res)
          this.state.articles = res.data.articleList
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getChat(context, roomNumber) {
      http
        .get(`chat/list/${roomNumber}`)
        .then((res) => {
          this.state.chats = res.data.chatList
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
})
