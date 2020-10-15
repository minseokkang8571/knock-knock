<template>
  <div class="container">
    <b-form
      @submit="onSubmit"
      id="input-form"
      class="container w-50 pt-5 pb-3 pl-3 pr-3 rounded border border-color-grey">

      <h2>Sign in</h2>
      <p>ID:</p>
      <b-form-input
        id="input-1"
        v-model="form.email"
        type="email"
        required
        placeholder="ID를 입력하세요."
        autocomplete="off"
      ></b-form-input>

      <p>Password:</p>
      <b-form-input
        id="input-2"
        v-model="form.password"
        type="password"
        required
        placeholder="비밀번호를 입력하세요."
      ></b-form-input>

      <button
        type="submit"
        class="btn btn-success w-100 mt-3"
      >Sign in</button>
    </b-form>
  </div>
</template>

<script>
import http from '@/util/http-common'
import jwtDecode from 'jwt-decode'
export default {
  data() {
    return {
      form: {
        email: '',
        password: ''
      },
      refreshInterval: 60 * 1000 * 25
    }
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault()
      // 이미 로그인한 경우, api요청없이 리다이렉션 (index.js에서도 처리되어있음)
      if (this.$store.state.isLogin === true) {
        alert('이미 로그인된 사용자입니다.')
        this.$router.push({ name: 'ArticleList' })
      }
      this.onSignin(this.form)
    },
    async onSignin(payload) {
      try {
        const res = await http.post('/user/login', payload, null)
        if (res.status === 200) {
          // 정상적으로 로그인 된 경우, 상태정보 저장 후 이전 페이지로 리다이렉트
          const token = res.data.accessToken
          const refresh = res.data.refreshToken
          localStorage.setItem('accessToken', token)
          localStorage.setItem('refreshToken', refresh)
          localStorage.setItem('userIdx', res.data.user.idx)

          this.$store.commit('SigninSuccess', res.data.user)
          this.$store.commit('setRefreshTimer', setInterval(this.refreshAccessToken, this.refreshInterval))
          this.$router.back()
        } else {
          // DB에 없는 데이터가 전달 된 경우
          alert('이메일과 비밀번호를 확인해주세요.')
        }
      } catch (err) {
        console.log(err)
      }
    },
    refreshAccessToken(context) {
      let accessToken = localStorage.getItem('accessToken')
      const refreshToken = localStorage.getItem('refreshToken')
      const decodedAccess = jwtDecode(accessToken)
      if (decodedAccess.exp < new Date().getTime() / 1000 - (60000 * 6)) {
        console.log('EXPIRED') // access Token 갱신 axios
        // 헤더에 accessToken 같이보내기
        const config = {}
        if (refreshToken) {
          config.headers = {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${refreshToken}`
          }
          http
            .get('/auth/access', config)
            .then((res) => {
              console.log(res)
              accessToken = res.data.accessToken
              localStorage.setItem('accessToken', accessToken)
            })
            .catch((err) => {
              console.log(err)
            })
        }
      }
    }
  }
}
</script>
