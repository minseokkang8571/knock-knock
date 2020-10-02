<template>
  <div>
    <div class="mt-5 d-flex justify-content-between">
      <h2 @click="toDetail(room.idx)" class="common-title">{{ room.title }}</h2>
      <span class="align-self-end">작성자 : {{ room.name }}</span>
    </div>
      <div class="text-left">{{ room.contents }}</div>
    <div class="d-flex justify-content-start">
      <button class="tag mt-1 mr-2">tag</button>
      <button class="tag mt-1">tag</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    room: Object
  },
  methods: {
    toDetail(roomIdx) {
      this.$router.push(`/reviews?roomIdx=${roomIdx}`)
    }
  }
}
</script>

<style>

</style>
