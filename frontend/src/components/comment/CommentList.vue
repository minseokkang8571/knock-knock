<template>
<div class="mt-2">
  <h4 class="text-left">Answer</h4>
  <div v-for="(comment, idx) in comments" :key="idx" class="container mt-2">
    <Comment :comment="comment" :recomments="recomments" :article="article" @changeComment="changeComment"/>
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
  data() {
    return {
      classifiedComments: [],
      recomments: []
    }
  },
  methods: {
    changeComment() {
      this.$emit('changeComment')
    },
    classifyRecomment() {
      for (var i = 0; i < this.comments.length; i++) {
        if (this.comments[i].groupLayer === 1) {
          this.recomments.push(this.comments[i])
        } else {
          this.classifiedComments.push(this.comments[i])
        }
      }
    }
  },
  mounted() {
    this.classifyRecomment()
  }
}
</script>

<style>

</style>
