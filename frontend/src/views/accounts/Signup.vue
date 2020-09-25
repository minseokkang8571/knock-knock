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
      this.$store.dispatch('onSignup', this.form)
    }
  },
  computed: {
    validation() {
      return this.form.password === this.form.passwordConfirm
    }
  }
}
</script>
