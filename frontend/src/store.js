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
    currentArticleIdx: null,
    refreshTimer: null
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
    },
    setRefreshTimer(state, timer) {
      state.refreshTimer = timer
    }
  },
  actions: {
    onSignout({ commit }) {
      const config = {}
      if (localStorage.getItem('refreshToken')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('refreshToken')}`
        }
        http
          .delete(
            'user/logout', config
          )
          .then(res => {
            if (res.status === 200) {
              console.log('logout')
              localStorage.removeItem('accessToken')
              localStorage.removeItem('refreshToken')
              localStorage.removeItem('userIdx')
              commit('Signout')
            }
          })
      }
    },
    async onSignup({ commit }, payload) {
      try {
        const res = await http.post('/user/signup', payload, null)

        if (res.data) {
          alert('회원가입이 완료되었습니다.')
          router.push({ name: 'ArticleList' })
        } else {
          alert('유효하지 않은 입력입니다.')
        }
      } catch (err) {
        console.log(err)
      }
    },
    async getUserInfo({ commit }) {
      try {
        const accessToken = localStorage.getItem('accessToken')
        const config = {}
        // let date = new Date()
        // let now = date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + ':' + date.getMilliseconds()
        // console.log('전송 시간 : ' + now)
        if (accessToken) {
          config.headers = {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`
          }
          http
            .get('/user/info', config)
            .then((res) => {
              // date = new Date()
              // now = date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + ':' + date.getMilliseconds()
              // console.log('응답 시간 : ' + now)
              commit('SigninSuccess', res.data.user)
            })
        }
      } catch (err) {
        console.log(err)
      }
    },
    async getChat(context, roomNumber) {
      try {
        const res = await http.get(`chat/list/${roomNumber}`)

        this.state.chats = res.data.chatList
      } catch (err) {
        console.log(err)
      }
    }
  }
})
