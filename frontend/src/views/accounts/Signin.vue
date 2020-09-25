<template>
  <div class="container">
    <b-form
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
        @click="onSignin(form)"
      >Sign in</button>
    </b-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
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
    ...mapActions(['onSignin'])
  }
}
</script>
