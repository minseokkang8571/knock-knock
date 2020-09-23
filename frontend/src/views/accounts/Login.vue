<template>
  <div>
    <b-form
      class="container mt-5 pt-5 pb-5 pl-3 pr-3 rounded border border-color-grey"
      style="max-width:450px;">
      <b-form-group
        id="input-group-1"
        label-for="input-1"
      >
        <p class="text-left mb-0">ID:</p>
        <b-form-input
          id="input-1"
          v-model="form.id"
          type="text"
          required
          placeholder="ID를 입력하세요."
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label-for="input-2">
        <p class="text-left mb-0">Password:</p>
        <b-form-input
          id="input-2"
          v-model="form.password"
          type="password"
          required
          placeholder="비밀번호를 입력하세요."
        ></b-form-input>
      </b-form-group>
      <button type="submit" class="btn btn-success float-right">Submit</button>
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        id: '',
        password: ''
      }
    }
  }
}
</script>
