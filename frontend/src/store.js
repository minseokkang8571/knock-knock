import Vue from 'vue'
import Vuex from 'vuex'
import http from './util/http-common'
import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: {
      idx: 0,
      name: null,
      email: null
    },
    isLogin: false,
    chats: [],
    currentArticleIdx: null
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
    },
    setCurrentArticle(state, articleIdx) {
      state.currentArticleIdx = articleIdx
    }
  },
  actions: {
    onSignin({ commit }, payload) {
      http
        .post('/user/login', payload, null)
        .then((res) => {
          console.log(res)
          if (res.data.status) {
            // 정상적으로 로그인 된 경우, 상태정보 저장 후 이전 페이지로 리다이렉트
            const token = res.data.token
            localStorage.setItem('token', token)
            commit('SigninSuccess', res.data.user)
            router.back()
          } else {
            // DB에 없는 데이터가 전달 된 경우
            alert('이메일과 비밀번호를 확인해주세요.')
          }
        })
        .catch((err) => {
          console.log(err)
        })
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
          router.push({ name: 'ArticleList' })
        })
        .catch((err) => {
          console.log(err)
        })
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
