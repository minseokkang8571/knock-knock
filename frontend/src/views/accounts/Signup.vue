<template>
  <div class="container">
    <b-form
      id="input-form"
      class="container w-50 pt-5 pb-3 pl-3 pr-3 rounded border border-color-grey">
        <h2>Sign up</h2>
        <p>ID:</p>
        <b-form-input
          id="input-1"
          v-model="form.id"
          type="text"
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
        <p>Password Confirm:</p>
        <b-form-input
          id="input-3"
          v-model="form.passwordConfirm"
          type="password"
          required
          placeholder="비밀번호를 입력하세요."
        ></b-form-input>
        <p>name:</p>
        <b-form-input
          id="input-4"
          v-model="form.name"
          type="name"
          required
          placeholder="닉네임을 입력하세요."
        ></b-form-input>
      <button type="submit" class="btn btn-success mt-3 w-100">Sign up</button>
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        id: '',
        password: '',
        passwordConfirm: '',
        name: ''
      }
    }
  }
}
</script>

<style scoped>
/* #input-form p {
  text-align: left;
  margin-bottom: 0px;
  margin-top: 10px;
} */
</style>
