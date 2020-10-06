<template>
  <div class="container" v-if="article">
    <!-- 게시글 내용 -->
    <ArticleContent
      :article="article"
      @onUpdate="onUpdate"
      @onDelete="onDelete"
    />
    <!-- 댓글 리스트 -->
    <CommentList
      v-if="comments"
      :comments="comments"
      :article="article"
      @saveComment="getArticle(1)"
    />
    <hr class="article-hr">
    <!-- 하단 페이지네이션 -->
    <Pagination
      :pageInfo="pageInfo"
      @onPaging="onPaging"
    />
    <!-- 댓글 입력폼-->
    <CommentCreate
      :payload="commentCreatePayload"
      @saveComment="getArticle(1)"
    />
  </div>
</template>

<script>
import ArticleContent from '@/components/article/ArticleContent'
import CommentList from '@/components/comment/CommentList'
import CommentCreate from '@/components/comment/CommentCreate'
import Pagination from '@/components/Pagination'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  components: {
    ArticleContent,
    CommentList,
    CommentCreate,
    Pagination
  },
  data() {
    return {
      article: {
        title: '',
        contents: '',
        regDate: '',
        userIdx: '',
        username: '',
        idx: null
      },
      comments: null,
      commentCreatePayload: {
        articleIdx: null,
        groupLayer: -1,
        groupOrd: 0
      },
      pageInfo: {
        endPageNo: 0,
        totalCnt: 0,
        itemInPage: 10
      }
    }
  },
  methods: {
    getArticle(pageNo) {
      http
        .get(`article/view?idx=${this.$route.query.articleIdx}` +
        `&pageNo=${pageNo}` +
        `&pageSize=${this.pageInfo.itemInPage}` +
        `&userIdx=${this.userInfo.idx}`)
        .then((res) => {
          console.log(res)

          this.article.contents = res.data.article.contents
          this.article.title = res.data.article.title
          this.article.regDate = res.data.article.formatedRegDate
          this.article.userIdx = this.commentCreatePayload.userIdx = res.data.article.userIdx
          this.article.idx = this.commentCreatePayload.articleIdx = res.data.article.idx
          this.article.username = res.data.article.name

          this.comments = res.data.comment
          this.pageInfo.totalCnt = res.data.paging.totalCount
          this.pageInfo.endPageNo = Math.ceil(this.pageInfo.totalCnt / this.pageInfo.itemInPage)
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
    },
    onPaging(pageNo) {
      this.getArticle(pageNo)
    }
  },
  computed: {
    ...mapState(['userInfo'])
  },
  mounted() {
    this.getArticle(1)
  }
}
</script>

<style>
.article-hr {
  margin: 5px 0px 5px 0px;
}
</style>
