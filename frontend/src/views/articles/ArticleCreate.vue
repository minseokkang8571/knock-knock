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
        ></b-form-textarea>
      <button
        type="submit"
        class="btn btn-success float-right pl-3 pr-3"
      >Submit</button>

    </b-form>
    <div class="hljs" ref="hlDiv" v-html="previewMarkdown"></div>
    <!-- TO DO::
    v-html 대체할 방법 찾기
    preview 추가
     -->
  </div>
</template>

<script>
import marked from 'marked'
import http from '@/util/http-common'
import hljs from 'highlight.js'
import { mapState } from 'vuex'
import '@/assets/styles/github-gist.css'
export default {
  data() {
    return {
      form: {
        title: '',
        contents: ''
      }
    }
  },
  props: {
    articleTitle: String,
    articleContents: String
  },
  methods: {
    // 수정시 필요한 파라미터를 갱신해주는 함수
    isUpdate() {
      if (this.currentArticleIdx !== null) {
        this.form.idx = this.currentArticleIdx
        this.form.title = this.articleTitle
        this.form.contents = this.articleContents
      }
    },
    onSubmit(payload) {
      event.preventDefault()
      payload.userIdx = this.$store.state.userInfo.idx
      console.log(payload)
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
    }
  },
  computed: {
    ...mapState(['currentArticleIdx']),
    previewMarkdown() {
      marked.setOptions({
        renderer: new marked.Renderer(),
        highlight: function(code) {
          return hljs.highlightAuto(code).value
        },
        pedantic: false,
        gfm: true,
        tables: true,
        breaks: false,
        sanitize: false,
        smartLists: true,
        smartypants: false,
        xhtml: false
      })
      return marked(this.form.contents)
    }
  },
  mounted() {
    this.isUpdate()
  }
}
</script>
