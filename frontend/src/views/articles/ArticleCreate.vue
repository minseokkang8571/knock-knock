<template>
  <div class="container">
    <b-form
      id="input-form"
      class="pt-5 pb-5 pl-3 pr-3 rounded border border-color-grey">
        <p>Title:</p>
        <b-form-input
          id="input-1"
          v-model="form.id"
          type="text"
          required
          placeholder="제목을 입력하세요."
        ></b-form-input>

        <p>Content:</p>
        <b-form-textarea
          id="input-2"
          v-model="form.password"
          type="textarea"
          required
          placeholder="이슈내용을 입력하세요."
          rows="15"
        ></b-form-textarea>
      <button type="submit" class="btn btn-success float-right pl-3 pr-3">Submit</button>
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
