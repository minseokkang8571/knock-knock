<template>
  <div>
    <hr class="article-hr">
    <!-- 댓글 -->
    <div v-if="comment.groupLayer === 0">
      <CommentContent
        :comment="comment"
        @onDislike="onDislike"
        @onLike="onLike"
        @showCommentForm="showCommentForm"
        @showUpdateForm="showUpdateForm"
        @onDelete="onDelete"/>
    </div>
    <!-- 재댓글 -->
    <div v-if="comment.groupLayer === 1">
      <RecommentContent
        :comment="comment"
        @showCommentForm="showCommentForm"
        @showUpdateForm="showUpdateForm"
        @onDelete="onDelete"/>
    </div>
    <!-- 재댓글 입력 -->
    <div
      v-if="recommentFormVisibleIdx === comment.idx"
      class="w-100">
      <CommentCreate
        :payload="recommentPayload"
        :modalId="modalReCreate"
        @saveComment="saveComment"/>
    </div>
    <!-- 수정 입력 -->
    <div
      v-if="updateFormVisibleIdx === comment.idx"
      class="w-100">
      <CommentCreate
        :payload="updatePayload"
        :modalId="modalReCreate"
        @saveComment="saveComment"/>
    </div>
  </div>
</template>

<script>
import CommentContent from '@/components/comment/CommentContent'
import CommentCreate from '@/components/comment/CommentCreate'
import RecommentContent from '@/components/comment/RecommentContent'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  components: {
    CommentContent,
    CommentCreate,
    RecommentContent
  },
  props: {
    comment: Object,
    article: Object,
    updateFormVisibleIdx: Number,
    recommentFormVisibleIdx: Number
  },
  data() {
    return {
      recommentPayload: {
        articleIdx: null,
        userIdx: null,
        groupLayer: 0,
        groupOrd: 0,
        originIdx: null,
        contents: ''
      },
      updatePayload: {
        contents: '',
        idx: null
      },
      modalReCreate: 'modal-recreate'
    }
  },
  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    async onDelete() {
      try {
        const res = await http.delete(`article/commentDelete?idx=${this.comment.idx}`)

        console.log(res)
        // 변경사항을 화면에 서브하기 위해 emit
        this.$emit('saveComment')
      } catch (err) {
        console.log(err)
      }
    },
    showUpdateForm() {
      // commentCreate로 보낼 데이터를 저장
      this.updatePayload.idx = this.comment.idx
      this.updatePayload.contents = this.comment.contents

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
    async onLike() {
      const payload = {
        userIdx: this.$store.state.userInfo.idx,
        commentIdx: this.comment.idx
      }

      try {
        const res = await http.post('article/commentLikeSave', payload, null)

        console.log(res)
        if (res.data.httpCode === '300') {
          // alert('이미 좋아요한 유저입니다.')
        } else {
          this.$emit('saveComment')
        }
      } catch (err) {
        console.log(err)
      }
    },
    async onDislike() {
      const payload = {
        userIdx: this.$store.state.userInfo.idx,
        commentIdx: this.comment.idx
      }

      try {
        const res = await http.delete(`article/commentLikeDelete?userIdx=${payload.userIdx}` +
          `&commentIdx=${payload.commentIdx}`)

        console.log(res)
        this.$emit('saveComment')
      } catch (err) {
        console.log(err)
      }
    }
  }
}

</script>

<style>
.detail-button {
  color: grey;
  cursor: pointer;
}
</style>
