<template>
  <div class="container">
    <article>
      <div class="d-flex justify-content-between">
        <h2 class="title-overflow">{{ article.title }}</h2>
        <span class="align-self-end">작성자: username백에서줘야함 작성시간: {{ article.regDate}}</span>
      </div>
      <hr>
      <p class="text-left mt-3">{{ article.content }}</p>
      <div class="d-flex justify-content-start">
        <button class="tag mt-1 mr-2">tag</button>
        <button class="tag mt-1">tag</button>
      </div>
      <div v-if="article.userIdx === userInfo.idx">
        <button @click="onUpdate">수정</button>
        <button @click="onDelete">삭제</button>
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
import { mapState } from 'vuex'
export default {
  data() {
    return {
      article: {
        title: '',
        content: '',
        regDate: '',
        userIdx: ''
      },
      comments: []
    }
  },
  components: {
    CommentList,
    CommentCreate
  },
  methods: {
    // TODO:: 타임아웃 외의 방법 찾아보기
    getArticle() {
      setTimeout(() => {
        http
          .get(`article/view?idx=${this.$route.query.articleIdx}`)
          .then((res) => {
            console.log(res)

            this.article.content = res.data.article.contents
            this.article.title = res.data.article.title
            this.article.regDate = res.data.article.formatedRegDate
            this.article.userIdx = res.data.article.userIdx
          })
          .catch((err) => {
            console.log(err)
          })
      }, 400)
    },
    onUpdate() {
      this.$store.commit('setCurrentArticle', this.$route.query.articleIdx)
      this.$router.push({ name: 'ArticleCreate' })
    },
    onDelete() {
      http
        .delete(`article/delete?idx=${this.$route.query.articleIdx}`)
        .then((res) => {
          console.log(res)
          this.$router.push({ name: 'ArticleList' })
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  mounted() {
    this.getArticle()
  },
  computed: {
    ...mapState(['userInfo'])
  }
}
</script>

<style>

</style>
