<template>
  <div>
    <div class="mt-5 d-flex justify-content-between">
      <h2 @click="toDetail(article.idx)" class="common-title title-overflow col-9">{{ article.title }}</h2>
      <span class="align-self-end color-grey">작성자 : {{ article.name }} | {{ article.formatedRegDate }}</span>
    </div>
      <!-- TODO:: 글 내용 프리뷰할지 논의 -->
      <!-- <p class="text-left content-overflow">{{ article.contents }}</p> -->
    <div class="d-flex justify-content-start">
      <button class="tag mt-1 mr-2">tag</button>
      <button class="tag mt-1">tag</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    article: Object
  },
  methods: {
    toDetail(idx) {
      this.$router.push(`article?articleIdx=${idx}`)
    }
  }
}
</script>

<style scoped>

</style>
