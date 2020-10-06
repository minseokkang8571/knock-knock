<template>
  <div class="d-flex justify-content-between ml-4">
    <p class="ml-5 mt-1 col-10 text-left">└ {{ comment.contents }}</p>
    <div class="d-flex justify-content-end">
      <div
        v-if="comment.userIdx === userInfo.idx"
        class="d-flex align-items-end mb-1">
        <span
          @click="showUpdateForm"
          class="mr-2 pr-1 detail-button">
          수정
        </span>
        <span
          @click="onDelete"
          class="detail-button">
          삭제
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  props: {
    comment: Object
  },
  methods: {
    showCommentForm() {
      this.$emit('showCommentForm')
    },
    showUpdateForm() {
      this.$emit('showUpdateForm')
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
