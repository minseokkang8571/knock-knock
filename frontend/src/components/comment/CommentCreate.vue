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
    article: Object
  },
  data() {
    return {
      form: {
        contents: ''
      }
    }
  },
  methods: {
    onCommentCreate() {
      if (this.$store.state.isLogin === null || this.$store.state.userInfo.idx < 1) {
        alert('로그인이 필요합니다')
        this.$router.push({ name: 'Signin' })
      }

      const payload = {
        articleIdx: this.article.idx,
        groupLayer: -1,
        groupOrd: 0,
        userIdx: this.$store.state.userInfo.idx,
        contents: this.form.contents
      }
      console.log(payload)
      http
        .post('article/commentSave', payload, null)
        .then((res) => {
          console.log(res)
          this.$emit('saveComment')
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>

<style>

</style>
