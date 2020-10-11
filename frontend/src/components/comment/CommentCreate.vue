<template>
  <div class="mb-2">
    <h4 class="text-left">Your Comment</h4>
    <b-form-textarea
      id="input-2"
      v-model="form.contents"
      type="textarea"
      required
      placeholder="댓글내용을 입력하세요."
      rows="15"
      @keydown.tab.prevent="tabber($event)"
      @keydown.ctrl.66="toggleCtrlShortCut"
      @keydown.ctrl.73="toggleCtrlShortCut"
      @keydown.ctrl.191="onModal"
    ></b-form-textarea>
    <div class="d-flex justify-content-end mt-1">
      <!-- preview for markdown -->
      <PreviewModal
        :contents="form.contents"
        :modalId="modalId"
        ref="previewModal" />
      <button
        @click="onCommentCreate"
        class="btn btn-success ml-2 pl-3 pr-3"
      >Submit</button>
    </div>
  </div>
</template>

<script>
import PreviewModal from '@/components/modal/PreviewModal'
import markdownMixin from '@/components/mixin/markdownMixin'
import http from '@/util/http-common'
export default {
  mixins: [markdownMixin],
  props: {
    article: Object,
    commentIdx: Number,
    payload: Object,
    modalId: String
  },
  components: {
    PreviewModal
  },
  data() {
    return {
      form: {
        contents: ''
      }
    }
  },
  methods: {
    isUpdate() {
      // 수정의 경우 기존의 값을 사용자에게 제공
      if (this.commentIdx !== null) {
        this.form.contents = this.payload.contents
      }
    },
    async onCommentCreate() {
      // 로그인이 되지 않은 사용자는 Signin으로 리다이렉트
      if (this.$store.state.isLogin === null || this.$store.state.userInfo.idx < 1) {
        alert('로그인이 필요합니다')
        this.$router.push({ name: 'Signin' })
      }

      this.payload.contents = this.form.contents

      try {
        const res = await http.post('article/commentSave', this.payload, null)

        console.log(res)
        this.form.contents = ''
        // 변경사항을 화면에 서브하기 위해 emit
        this.$emit('saveComment')
      } catch (err) {
        console.log(err)
      }
    }
  },
  mounted() {
    this.isUpdate()
  }
}
</script>

<style>

</style>
