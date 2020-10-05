<template>
  <div>
    <article>
      <div class="d-flex justify-content-between">
        <h2 class="title-overflow col-9">{{ article.title }}</h2>
        <span class="align-self-end color-grey">작성자 : {{ article.username }} | {{ article.regDate }}</span>
      </div>
      <hr>
      <Preview :contents="article.contents" />
      <div class="d-flex justify-content-start">
        <button class="tag mt-1 mr-2">tag</button>
        <button class="tag mt-1">tag</button>
      </div>
      <div v-show="article.userIdx === userInfo.idx">
        <button @click="onUpdate">수정</button>
        <button @click="onDelete">삭제</button>
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
