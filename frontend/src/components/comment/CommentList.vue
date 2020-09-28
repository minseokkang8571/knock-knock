<template>
<div class="mt-2">
  <h4 class="text-left">Answer</h4>
  <div v-for="(comment, idx) in comments" :key="idx" class="container mt-2">
    <Comment :comment="comment" :article="article" @changeComment="changeComment"/>
  </div>
</div>
</template>

<script>
import Comment from '@/components/comment/Comment'
export default {
  components: {
    Comment
  },
  props: {
    comments: Array,
    article: Object
  },
  methods: {
    changeComment() {
      this.$emit('changeComment')
    }
  }
}
</script>

<style>

</style>
