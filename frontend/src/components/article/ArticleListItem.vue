<template>
  <div>
    <div class="mt-5 d-flex justify-content-between">
      <h2 @click="toDetail(article.idx)" class="common-title title-overflow col-9">{{ article.title }}</h2>
      <span class="align-self-end color-grey">작성자 : {{ article.name }} | {{ article.formatedRegDate }}</span>
    </div>
    <div class="d-flex flex-row">
      <button v-for="(tag, idx) in article.articleHashtagList" :key="idx"
        class="tag mt-1 mr-2">
        {{ tag.tag }}
      </button>
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

<style scoped>

</style>
