<template>
  <div class="mt-2 mb-2">
    <!-- 댓글 -->
    <div v-if="comment.groupLayer === 0" class="row">
      <p class="col-1">userIdx = {{ comment.userIdx }} </p>
      <p class="col-11 text-left">{{ comment.contents }}</p>
    </div>
    <!-- 재댓글 -->
    <div v-if="comment.groupLayer === 1" class="row recomment ml-4">
      <p class="col-1">userIdx = {{ comment.userIdx }}</p>
      <p class="col-11 text-left">{{ comment.contents }}</p>
    </div>

    <!-- 댓글의 작성자일 경우 수정,삭제 권한을 제공 -->
    <div v-show="comment.userIdx === userInfo.idx">
      <button @click="showUpdateForm">수정</button>
      <button @click="onDelete">삭제</button>
    </div>
    <button v-if="comment.groupLayer === 0" @click="showCommentForm">재댓글</button>

    <!-- 수정의 경우 markdown, 재댓글의 경우 text이기 때문에 서로 다른 폼을 사용 -->
    <div v-if="recommentFormVisibleIdx === comment.idx" class="w-100">
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
        class="btn btn-primary mt-2 ml-auto d-block"
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
        class="btn btn-primary text-right mt-2 ml-auto d-block"
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
        contents: ''
      },
      updateForm: {
        contents: ''
      }
    }
  },
  props: {
    comment: Object,
    article: Object,
    updateFormVisibleIdx: Number,
    recommentFormVisibleIdx: Number
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
      this.$emit('onUpdate', this.comment.idx)
      alert('구현예정')
    },
    showCommentForm() {
      // this.commentForm.isVisible = true
      this.$emit('onRecomment', this.comment.idx)
    },
    onComment() {
      if (this.$store.state.isLogin === null || this.$store.state.userInfo.idx < 1) {
        alert('로그인이 필요합니다')
        this.$router.push({ name: 'Signin' })
      }
      const payload = {
        articleIdx: this.article.idx,
        userIdx: this.$store.state.userInfo.idx,
        groupLayer: this.comment.groupLayer,
        groupOrd: 0,
        originIdx: this.comment.idx,
        contents: this.commentForm.contents
      }
      console.log(payload)
      http
        .post('article/commentSave', payload, null)
        .then((res) => {
          console.log(res)
          this.commentForm.isVisible = false
          this.updateForm.isVisible = false
          this.commentForm.contents = ''
          this.updateForm.contents = ''
          this.$emit('changeComment')
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}

</script>

<style scoped>
.recomment {
  background-color: #f8f8f8;
}
</style>
