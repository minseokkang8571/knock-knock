<template>
  <div>
    <b-button @click="onModal">Preview</b-button>

    <b-modal size="xl" :id="modalId" @keydown.ctrl="myKeyDown">
      <template v-slot:modal-header="{}">
        <h5>Preview</h5>
      </template>
      <Preview :contents="contents" />

      <template v-slot:modal-footer="{ cancel }">
        <b-button class="btn" @click="cancel()">
          Cancel
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
import Preview from '@/components/viewer/Preview'
export default {
  components: {
    Preview
  },
  props: {
    contents: String,
    modalId: String // 댓글 생성, 재댓글 생성의 입력폼을 구분하기 위한 Id값, CommentCreate의 상위 컴포넌트로 부터 props
  },
  methods: {
    onModal() {
      this.$bvModal.show(this.modalId)
    }
  }
}
</script>

<style>

</style>
