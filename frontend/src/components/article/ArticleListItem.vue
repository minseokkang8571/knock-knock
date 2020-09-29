<template>
  <div>
    <div class="mt-5 d-flex justify-content-between">
      <h2 @click="toDetail(article.idx)" class="common-title title-overflow">{{ article.title }}</h2>
      <span class="align-self-end">작성자 : ipsum {{ article.formatedRegDate }}</span>
    </div>
      <p class="text-left content-overflow">{{ article.contents }}</p>
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
      this.$router.push(`articles?articleIdx=${idx}`)
    }
  }
}
</script>

<style>

</style>
