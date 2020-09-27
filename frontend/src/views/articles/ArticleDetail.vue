<template>
  <div class="container">
    <article>
      <div class="d-flex justify-content-between">
        <h2 class="title-overflow">{{ article.title }}</h2>
        <span class="align-self-end">작성자: username백에서줘야함 작성시간: {{ article.regDate}}</span>
      </div>
      <hr>
      <div class="text-left mt-3">{{ article.content }}</div>
      <div class="d-flex justify-content-start">
        <button class="tag mt-1 mr-2">tag</button>
        <button class="tag mt-1">tag</button>
      </div>
    </article>
    <CommentList />
    <hr>
    <CommentCreate />
  </div>
</template>

<script>
import CommentList from '@/components/comment/CommentList'
import CommentCreate from '@/components/comment/CommentCreate'
import http from '@/util/http-common'
export default {
  data() {
    return {
      article: {
        title: '',
        content: '',
        regDate: ''
      },
      comments: []
    }
  },
  components: {
    CommentList,
    CommentCreate
  },
  methods: {
    getArticle() {
      setTimeout(() => {
        http
          .get(`article/view?idx=${this.$route.query.articleIdx}`)
          .then((res) => {
            console.log(res)

            this.article.content = res.data.article.contents
            this.article.title = res.data.article.title
            this.article.regDate = res.data.article.formatedRegDate
          })
          .catch((err) => {
            console.log(err)
          })
      }, 400)
    }
  },
  mounted() {
    this.getArticle()
  }
}
</script>

<style>

</style>
