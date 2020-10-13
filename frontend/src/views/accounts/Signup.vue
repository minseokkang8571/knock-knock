<template>
  <div class="container">
    <b-form
      @submit="onSubmit"
      id="input-form"
      class="container w-50 pt-5 pb-3 pl-3 pr-3 rounded border border-color-grey">

      <h2>Sign up</h2>

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
      <b-form-invalid-feedback :state="pwChk">
        (영문 + 숫자 + 특수기호) 8자리 이상으로 입력해주세요.
      </b-form-invalid-feedback>

      <p>Password Confirm:</p>
      <b-form-input
        id="input-3"
        v-model="form.passwordConfirm"
        type="password"
        required
        placeholder="비밀번호를 입력하세요."
      ></b-form-input>
      <b-form-invalid-feedback :state="validation">
        Password and Password Confirm are not match
      </b-form-invalid-feedback>

      <p>name:</p>
      <b-form-input
        id="input-4"
        v-model="form.name"
        type="text"
        required
        placeholder="닉네임을 입력하세요."
      ></b-form-input>

      <button
        type="submit"
        class="btn btn-success mt-3 w-100"
      >Sign up</button>
    </b-form>
  </div>
</template>

<script>
import http from '@/util/http-common'
export default {
  data() {
    return {
      form: {
        email: '',
        password: '',
        passwordConfirm: '',
        name: ''
      }
    }
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault()
      if (!this.validation || !this.pwChk) {
        alert('비밀번호를 확인해주세요.')
      } else {
        this.onSignup(this.form)
      }
    },
    async onSignup(payload) {
      try {
        const res = await http.post('/user/signup', payload, null)

        if (res.data) {
          alert('회원가입이 완료되었습니다.')
          this.$router.push({ name: 'ArticleList' })
        } else {
          alert('유효하지 않은 입력입니다.')
        }
      } catch (err) {
        console.log(err)
      }
    }
  },
  computed: {
    validation() {
      return this.form.password === this.form.passwordConfirm
    },
    pwChk() {
      var pw = this.form.password
      var pattern1 = /[0-9]/
      var pattern2 = /[a-zA-Z]/
      var pattern3 = /[!@#$%^&*()_+~=-]/
      if (pw.length === 0) {
        return true
      }
      return pw.length > 8 && pattern1.test(pw) && pattern2.test(pw) && pattern3.test(pw) && pw.length < 20
    }
  }
}
</script>
