<template>
  <div class="container mt-5">
    <article>
      <div class="d-flex justify-content-between">
        <h2>import Login from '@/views/Login.vue'</h2>
        <span class="align-self-end">작성자: ipsum 작성시간: 2020-09-23</span>
      </div>
      <hr>
      <div class="text-left mt-3">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</div>
    </article>
    <CommentList />
  </div>
</template>

<script>
import CommentList from '@/components/CommentList'
export default {
  components: {
    CommentList
  }
}
</script>

<style>

</style>
