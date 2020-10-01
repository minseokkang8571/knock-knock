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
    ></b-form-textarea>
    <button class="btn btn-primary mt-2 ml-auto d-block" @click="onCommentCreate">submit</button>
  </div>
</template>

<script>
import http from '@/util/http-common'
export default {
  props: {
    article: Object,
    commentContents: String,
    commentIdx: Number,
    payload: Object
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
        this.form.contents = this.commentContents
      }
    },
    onCommentCreate() {
      // 로그인이 되지 않은 사용자는 Signin으로 리다이렉트
      if (this.$store.state.isLogin === null || this.$store.state.userInfo.idx < 1) {
        alert('로그인이 필요합니다')
        this.$router.push({ name: 'Signin' })
      }

      this.payload.contents = this.form.contents

      http
        .post('article/commentSave', this.payload, null)
        .then((res) => {
          console.log(res)
          this.form.contents = ''
          this.$emit('saveComment')
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  mounted() {
    this.isUpdate()
  }
}
</script>

<style>

</style>
