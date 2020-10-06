<template>
  <div class="d-flex justify-content-between no-gutters">
    <div class="col-1">
      <div v-if="comment.likeYn">
        <em
          class="mt-2 fas fa-thumbs-up fa-lg h-10 cursor-pointer"
          @click="onDislike">
        </em>
        {{ comment.commentLikeCount }}
      </div>
      <div v-else>
        <em
          class="mt-2 far fa-thumbs-up fa-lg h-10 cursor-pointer"
          @click="onLike">
        </em>
        {{ comment.commentLikeCount }}
      </div>
    </div>
    <div class="mt-1 col-11">
      <Preview :contents="comment.contents" />
      <div class="d-flex justify-content-between">
        <span>강민석</span>
        <div>
          <span
            v-if="comment.groupLayer === 0"
            @click="showCommentForm"
            class="mr-2 detail-button">
            재댓글
          </span>
          <div
            v-if="comment.userIdx === userInfo.idx"
            class="d-inline">
            <span
              @click="showUpdateForm"
              class="mr-2 detail-button">
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
    </div>
  </div>
</template>

<script>
import Preview from '@/components/viewer/Preview'
import { mapState } from 'vuex'
export default {
  props: {
    comment: Object
  },
  components: {
    Preview
  },
  methods: {
    onDislike() {
      this.$emit('onDislike')
    },
    onLike() {
      this.$emit('onLike')
    },
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
