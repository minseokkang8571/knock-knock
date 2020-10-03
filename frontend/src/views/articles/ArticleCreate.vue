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
          @keydown.ctrl.66="toggleBold"
        ></b-form-textarea>
      <div class="d-flex justify-content-end">
        <!-- preview for markdown -->
        <PreviewModal :contents="form.contents" />
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
import http from '@/util/http-common'
import { mapState } from 'vuex'
import '@/assets/styles/github-gist.css'
export default {
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
        contents: ''
      }
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
    onSubmit(payload) {
      event.preventDefault()
      payload.userIdx = this.$store.state.userInfo.idx
      http
        .post('/article/save', payload, null)
        .then((res) => {
          console.log(res)
          this.$router.push(`articles?articleIdx=${res.data.idx}`)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    tabber(event) {
      // 탭을 눌렀을 때, 4space만큼 커서와 내용이 이동하도록 하는 함수
      if (event) {
        event.preventDefault()
        const text = this.form.contents
        const originalSelectionStart = event.target.selectionStart
        const startText = text.slice(0, originalSelectionStart)
        const endText = text.slice(originalSelectionStart)
        this.form.contents = `${startText}\t${endText}`
        // TO DO:: two space 추가

        event.target.value = this.form.contents
        event.target.selectionEnd = event.target.selectionStart = originalSelectionStart + 1
      }
    },
    toggleBold(event) {
      // ctrl+b를 눌렀을 때, 커서블록부분을 Bold처리하는 함수
      const text = this.form.contents
      const selectionStart = event.target.selectionStart
      const selectionEnd = event.target.selectionEnd
      const startText = text.slice(0, selectionStart)
      let endText = text.slice(selectionStart)
      const boldText = text.slice(selectionStart, selectionEnd)

      endText = text.slice(selectionEnd)
      if (boldText.substr(0, 2) === '**' && boldText.substr(boldText.length - 2, 2) === '**') {
        // 이미 Bold인 경우 해제
        const boldRemovedText = boldText.slice(2, boldText.length - 2)
        this.form.contents = `${startText}${boldRemovedText}${endText}`
      } else {
        // ** **로 Bold처리
        this.form.contents = `${startText}**${boldText}**${endText}`
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
