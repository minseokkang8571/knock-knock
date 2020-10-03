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
export default {
  data() {
    return {
      form: {
        email: '',
        password: ''
      }
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
      this.$store.dispatch('onSignin', this.form)
    }
  }
}
</script>
