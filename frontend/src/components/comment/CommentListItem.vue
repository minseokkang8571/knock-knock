<template>
  <div>
    <hr>
    <!-- 댓글 -->
    <div v-if="comment.groupLayer === 0" class="row">
      <button class="col-1" @click="onLike">좋아요 {{ comment.commentLikeCount }}</button>
      <p class="col-11 text-left">{{ comment.contents }}</p>
    </div>
    <!-- 재댓글 -->
    <div v-if="comment.groupLayer === 1" class="row ml-4">
      <p class="col-1">userIdx = {{ comment.userIdx }}</p>
      <p class="col-11 text-left">{{ comment.contents }}</p>
    </div>
    <!-- 댓글의 작성자일 경우 수정,삭제 권한을 제공 -->
    <div class="d-flex justify-content-end">
      <span
        v-if="comment.groupLayer === 0"
        @click="showCommentForm"
        class="mr-2 detail-button">
        재댓글
      </span>
      <div v-show="comment.userIdx === userInfo.idx" class="d-inline">
        <span
          @click="showUpdateForm"
          class="mr-2 detail-button">
          수정
        </span>
        <span
          @click="onDelete"
          class="detail-button">
          삭제
        </span>
      </div>
    </div>
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
import CommentCreate from '@/components/comment/CommentCreate'
import http from '@/util/http-common'
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
    },
    // TODO:: 좋아요 명확하게 변경해야함.(백단에서부터)
    onLike() {
      const payload = {
        userIdx: this.$store.state.userInfo.idx,
        commentIdx: this.comment.idx
      }
      console.log(payload)
      http
        .post('article/commentLikeSave', payload, null)
        .then((res) => {
          console.log(res)
          if (res.data.httpCode === '300') {
            alert('이미 좋아요한 유저입니다.')
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}

</script>

<style scoped>
.detail-button {
  color: grey;
  cursor: pointer;
}
</style>
