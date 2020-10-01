<template>
  <div class="container" v-if="article">
    <article>
      <div class="d-flex justify-content-between">
        <h2 class="title-overflow">{{ article.title }}</h2>
        <span class="align-self-end">작성자: username백에서줘야함 작성시간: {{ article.regDate}}</span>
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
    <CommentList v-if="comments" :comments="comments" :article="article" @saveComment="getArticle(1)"/>
    <hr>
    <CommentCreate :payload="commentCreatePayload" @saveComment="getArticle(1)"/>
  </div>
</template>

<script>
import CommentList from '@/components/comment/CommentList'
import CommentCreate from '@/components/comment/CommentCreate'
import Preview from '@/components/viewer/Preview'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  components: {
    CommentList,
    CommentCreate,
    Preview
  },
  data() {
    return {
      article: {
        title: '',
        contents: '',
        regDate: '',
        userIdx: '',
        idx: null
      },
      comments: null,
      commentCreatePayload: {
        articleIdx: null,
        groupLayer: -1,
        groupOrd: 0
      }
    }
  },
  methods: {
    getArticle(pageNo) {
      http
        .get(`article/view?idx=${this.$route.query.articleIdx}&pageNo=${pageNo}`)
        .then((res) => {
          console.log(res)

          this.article.contents = res.data.article.contents
          this.article.title = res.data.article.title
          this.article.regDate = res.data.article.formatedRegDate
          this.article.userIdx = this.commentCreatePayload.userIdx = res.data.article.userIdx
          this.article.idx = this.commentCreatePayload.articleIdx = res.data.article.idx
          this.comments = res.data.comment
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onUpdate() {
      this.$store.commit('setCurrentArticle', this.$route.query.articleIdx)
      this.$router.push({ name: 'ArticleCreate', params: { articleTitle: this.article.title, articleContents: this.article.contents } })
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
    this.getArticle(1)
  },
  computed: {
    ...mapState(['userInfo'])
  }
}
</script>

<style>

</style>
