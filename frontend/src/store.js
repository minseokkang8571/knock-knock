import Vue from 'vue'
import Vuex from 'vuex'
import http from './util/http-common'
import router from './router'
import jwtDecode from 'jwt-decode'

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
    tmp({ commit }) {
      const token = localStorage.getItem('token')
      const refresh = localStorage.getItem('refresh')
      var decodedToken = jwtDecode(token)
      var decodedRefresh = jwtDecode(refresh)
      if (decodedToken.exp < new Date().getTime() / 1000 - (60000 * 6)) {
        console.log('EXPIRED') // access Token 갱신 axios
        const config = {}
        if (token) {
          config.headers = {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          }
          http
            .get('/auth/access', config)
            .then((res) => {
              console.log(res)
              const token = res.data.accessToken
              localStorage.setItem('token', token)
            })
            .catch((err) => {
              console.log(err)
            })
        }
      } else if (decodedRefresh.exp < new Date().getTime() / 1000 - (60000 * 6)) {
        console.log('Refresh Expired') // refresh 갱신 axios
        const config = {}
        if (refresh) {
          config.headers = {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${refresh}`
          }
          http
            .get('/auth/refresh', config)
            .then((res) => {
              console.log(res)
              const token = res.data.accessToken
              const refresh = res.data.refreshToken
              localStorage.setItem('token', token)
              localStorage.setItem('refresh', refresh)
            })
            .catch((err) => {
              console.log(err)
            })
        }
      }
    },
    async onSignin({ commit }, payload) {
      try {
        const res = await http.post('/user/login', payload, null)
        if (res.status === 200) {
          // 정상적으로 로그인 된 경우, 상태정보 저장 후 이전 페이지로 리다이렉트
          const token = res.data.accessToken
          const refresh = res.data.refreshToken
          localStorage.setItem('token', token)
          localStorage.setItem('refresh', refresh)
          localStorage.setItem('userIdx', res.data.user.idx)
          commit('SigninSuccess', res.data.user)
          router.back()
        } else {
          // DB에 없는 데이터가 전달 된 경우
          alert('이메일과 비밀번호를 확인해주세요.')
        }
      } catch (err) {
        console.log(err)
      }
    },
    onSignout({ commit }) {
      commit('Signout')
      localStorage.removeItem('token')
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
      const token = localStorage.getItem('token')
      const config = {}

      try {
        if (token) {
          config.headers = {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
          const res = await http.get('/user/info', config)

          console.log(res)
          commit('SigninSuccess', res.data.user)
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
