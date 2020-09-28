<template>
  <div class="row mt-2 mb-2">
    <div class="col-1">{{ comment.userIdx }}</div>
    <div class="col-11 text-left">{{ comment.contents }}</div>
    <div v-show="article.userIdx === userInfo.idx">
      <button @click="showUpdateForm">수정</button>
      <button @click="onDelete">삭제</button>
      <button @click="showCommentForm">재댓글</button>
    </div>
    <div v-if="commentForm.isVisible" class="w-100">
      <b-form-textarea
        id="input-2"
        v-model="commentForm.contents"
        type="textarea"
        required
        placeholder="댓글내용을 입력하세요."
        rows="2"
        class="mt-2"
      ></b-form-textarea>
      <button
        class="btn btn-primary float-right mt-2"
        @click="onComment"
      >submit</button>
    </div>
    <!-- <div v-if="updateForm.isVisible" class="w-100">
      <b-form-textarea
        id="input-2"
        v-model="commentForm.contents"
        type="textarea"
        required
        placeholder="댓글내용을 입력하세요."
        rows="2"
        class="mt-2"
      ></b-form-textarea>
      <button
        class="btn btn-primary float-right mt-2"
        @click="onSubmit"
      >submit</button>
    </div> -->
  </div>
</template>

<script>
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  data() {
    return {
      commentForm: {
        contents: '',
        isVisible: false
      },
      updateForm: {
        contents: '',
        isVisible: false
      }
    }
  },
  props: {
    comment: Object,
    article: Object
  },
  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    onDelete() {
      http
        .delete(`article/commentDelete?idx=${this.comment.idx}`)
        .then((res) => {
          console.log(res)
          this.$emit('changeComment')
        })
        .catch((err) => {
          console.log(err)
        })
    },
    showUpdateForm() {
      this.updateForm.isVisible = true
    },
    showCommentForm() {
      this.commentForm.isVisible = true
    },
    onComment() {
      if (this.$store.state.isLogin === null || this.$store.state.userInfo.idx < 1) {
        alert('로그인이 필요합니다')
        this.$router.push({ name: 'Signin' })
      }
      const payload = {
        articleIdx: this.article.idx,
        groupLayer: this.comment.groupLayer,
        userIdx: this.$store.state.userInfo.idx,
        originIdx: this.comment.idx,
        contents: this.commentForm.contents
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
