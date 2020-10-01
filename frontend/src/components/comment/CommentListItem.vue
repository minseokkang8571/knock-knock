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
    <div
      v-if="recommentFormVisibleIdx === comment.idx"
      class="w-100">
      <CommentCreate :payload="recommentPayload" @saveComment="saveComment" />
    </div>

    <div
      v-if="updateFormVisibleIdx === comment.idx"
      class="w-100">
      <CommentCreate :payload="updatePayload" @saveComment="saveComment"/>
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common'
import CommentCreate from '@/components/comment/CommentCreate'
import { mapState } from 'vuex'
export default {
  components: {
    CommentCreate
  },
  props: {
    comment: Object,
    article: Object,
    updateFormVisibleIdx: Number,
    recommentFormVisibleIdx: Number
  },
  data() {
    return {
      commentForm: {
        contents: ''
      },
      updateForm: {
        contents: ''
      },
      recommentPayload: {
        articleIdx: null,
        userIdx: null,
        groupLayer: 0,
        groupOrd: 0,
        originIdx: null,
        contents: ''
      },
      updatePayload: {
        idx: null
      }
    }
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
          // 변경사항을 화면에 서브하기 위해 emit
          this.$emit('saveComment')
        })
        .catch((err) => {
          console.log(err)
        })
    },
    showUpdateForm() {
      // commentCreate로 보낼 데이터를 저장
      this.updatePayload.idx = this.comment.idx

      // commentForm을 하나만 보여줄 수 있도록 commentList에서 관리
      this.$emit('onUpdate', this.comment.idx)
    },
    showCommentForm() {
      // commentCreate로 보낼 데이터를 저장
      this.recommentPayload.articleIdx = this.article.idx
      this.recommentPayload.userIdx = this.$store.state.userInfo.idx
      this.recommentPayload.groupLayer = this.comment.groupLayer
      this.recommentPayload.groupOrd = 0
      this.recommentPayload.originIdx = this.comment.idx

      // commentForm을 하나만 보여줄 수 있도록 commentList에서 관리
      this.$emit('onRecomment', this.comment.idx)
    },
    saveComment() {
      this.$emit('saveComment')
    }
  }
}

</script>

<style scoped>
.recomment {
  background-color: #f8f8f8;
}
</style>
