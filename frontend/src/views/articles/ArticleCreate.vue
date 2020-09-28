<template>
  <div class="container">
    <b-form
      @submit="onSubmit"
      id="input-form"
      class="pt-5 pb-5 pl-3 pr-3 rounded border border-color-grey">
        <p>Title:</p>
        <b-form-input
          id="input-1"
          v-model="form.title"
          type="text"
          required
          placeholder="제목을 입력하세요."
          autocomplete="off"
        ></b-form-input>

        <p>Content:</p>
        <b-form-textarea
          id="input-2"
          v-model="form.contents"
          type="textarea"
          required
          placeholder="이슈내용을 입력하세요."
          rows="15"
        ></b-form-textarea>
      <button
        type="submit"
        class="btn btn-success float-right pl-3 pr-3">Submit</button>
    </b-form>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  data() {
    return {
      form: {
        title: '',
        contents: ''
      }
    }
  },
  methods: {
    onSubmit() {
      if (this.currentArticleIdx !== null) {
        this.form.idx = this.currentArticleIdx
      }
      this.$store.dispatch('createArticle', this.form)
    }
  },
  computed: {
    ...mapState(['currentArticleIdx'])
  }
}
</script>
