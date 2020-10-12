<template>
  <div>
    <article>
      <div class="d-flex justify-content-between">
        <h2 class="title-overflow col-9">{{ article.title }}</h2>
        <span class="align-self-end color-grey">작성자 : {{ article.username }} | {{ article.regDate }}</span>
      </div>
      <hr>
      <Preview :contents="article.contents" />

      <div class="d-flex flex-row">
        <button v-for="(tag, idx) in article.hashtagList" :key="idx"
          class="tag mt-1 mr-2">
          {{ tag.tag }}
        </button>
      </div>

      <div
        v-if="article.userIdx == userInfo.idx"
        class="d-flex justify-content-end">
        <span
          @click="onUpdate"
          class="mr-2 detail-button">
          수정
        </span>
        <span
          @click="onDelete"
          class="detail-button">
          삭제
        </span>
      </div>
    </article>
  </div>
</template>

<script>
import Preview from '@/components/viewer/Preview'
import { mapState } from 'vuex'
export default {
  components: {
    Preview
  },
  props: {
    article: Object
  },
  methods: {
    onUpdate() {
      this.$emit('onUpdate')
    },
    onDelete() {
      this.$emit('onDelete')
    }
  },
  computed: {
    ...mapState(['userInfo'])
  }
}
</script>

<style>

</style>
