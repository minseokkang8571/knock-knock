<template>
<div class="mt-2">
  <h4 class="text-left">Answer</h4>
  <div v-for="(comment, idx) in comments" :key="idx" class="container mt-2">
    <CommentListItem
      :comment="comment"
      :article="article"
      :updateFormVisibleIdx="updateFormVisibleIdx"
      :recommentFormVisibleIdx="recommentFormVisibleIdx"
      @onRecomment="onRecomment"
      @onUpdate="onUpdate"
      @saveComment="saveComment"
    />
  </div>
</div>
</template>

<script>
import CommentListItem from '@/components/comment/CommentListItem'
export default {
  components: {
    CommentListItem
  },
  props: {
    comments: Array,
    article: Object
  },
  data() {
    return {
      recommentFormVisibleIdx: null,
      updateFormVisibleIdx: null
    }
  },
  methods: {
    onRecomment(payload) {
      if (this.recommentFormVisibleIdx === payload) {
        // 이미 commentForm이 보이는경우, commentForm을 닫음
        this.recommentFormVisibleIdx = null
      } else {
        // 특정 댓글에 대한 commentForm만 보이게 함
        this.recommentFormVisibleIdx = payload
        this.updateFormVisibleIdx = null
      }
    },
    onUpdate(payload) {
      if (this.updateFormVisibleIdx === payload) {
        // 이미 commentForm이 보이는경우, commentForm을 닫음
        this.updateFormVisibleIdx = null
      } else {
        // 특정 댓글에 대한 commentForm만 보이게 함
        this.updateFormVisibleIdx = payload
        this.recommentFormVisibleIdx = null
      }
    },
    saveComment() {
      // commentForm을 제거하고 변경사항 적용을 위해 emit
      this.recommentFormVisibleIdx = null
      this.updateFormVisibleIdx = null

      this.$emit('saveComment')
    }
  }
}
</script>

<style>

</style>
