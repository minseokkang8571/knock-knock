<template>
  <div>
    <b-button @click="$bvModal.show('modal-scoped')">Preview</b-button>

    <b-modal size="xl" id="modal-scoped">
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
    contents: String
  }
}
</script>

<style>

</style>
