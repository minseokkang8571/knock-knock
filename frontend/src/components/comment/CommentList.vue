<template>
<div class="mt-2">
  <h4 class="text-left">Answer</h4>
  <div v-for="(comment, idx) in comments" :key="idx" class="container mt-2">
    <CommentListItem
      :comment="comment"
      :article="article"
      :updateFormVisibleIdx="updateFormVisibleIdx"
      :recommentFormVisibleIdx="recommentFormVisibleIdx"
      @changeComment="changeComment"
      @onRecomment="onRecomment"
      @onUpdate="onUpdate"
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
    changeComment() {
      this.recommentFormVisibleIdx = null
      this.$emit('changeComment')
    },
    onRecomment(payload) {
      console.log(payload)
      this.recommentFormVisibleIdx = payload
    },
    onUpdate(payload) {
      this.updateFormVisibleIdx = payload
    }
  }
}
</script>

<style>

</style>
