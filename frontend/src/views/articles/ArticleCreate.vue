<template>
  <div class="container">
    <b-form
      @submit="onSubmit(form)"
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
          @keydown.tab.prevent="tabber($event)"
          @keydown.ctrl.66="toggleCtrlShortCut"
          @keydown.ctrl.73="toggleCtrlShortCut"
          @keydown.ctrl.191="onModal"
        ></b-form-textarea>

        <p>Tags:</p>
        <b-form-input
          id="input-3"
          v-model="form.tags"
          type="text"
          required
          placeholder="태그를 입력하세요."
          autocomplete="off"
        ></b-form-input>

      <div class="d-flex justify-content-end">
        <!-- preview for markdown -->
        <PreviewModal
          :contents="form.contents"
          :modalId="modalId"
          ref="previewModal" />
        <button
          type="submit"
          class="btn btn-success ml-2 pl-3 pr-3"
        >Submit</button>
      </div>
    </b-form>
    <!-- TO DO::
    v-html 대체할 방법 찾기
     -->
  </div>
</template>

<script>
import PreviewModal from '@/components/modal/PreviewModal'
import markdownMixin from '@/components/mixin/markdownMixin'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  mixins: [markdownMixin],
  components: {
    PreviewModal
  },
  props: {
    articleTitle: String,
    articleContents: String
  },
  data() {
    return {
      form: {
        title: '',
        contents: '',
        tags: ''
      },
      modalId: 'modalCreate'
    }
  },
  methods: {
    isUpdate() {
      // 수정시 필요한 파라미터를 갱신해주는 함수
      if (this.currentArticleIdx !== null) {
        this.form.idx = this.currentArticleIdx
        this.form.title = this.articleTitle
        this.form.contents = this.articleContents
      }
    },
    async onSubmit(payload) {
      event.preventDefault()
      payload.userIdx = this.$store.state.userInfo.idx

      try {
        const res = await http.post('/article/save', payload, null)

        console.log(res)
        this.$router.push(`articles?articleIdx=${res.data.idx}`)
      } catch (err) {
        console.log(err)
      }
    }
  },
  computed: {
    ...mapState(['currentArticleIdx'])
  },
  mounted() {
    this.isUpdate()
  }
}
</script>
