<template>
  <div class="container">
    <!-- 게시글 리스트 -->
    <div v-for="(article, idx) in articles" :key="idx">
      <ArticleListItem :article="article" />
    </div>
    <!-- 하단 페이지네이션 -->
    <Pagination
      :pageInfo="pageInfo"
      @onPaging="onPaging"
    />
  </div>
</template>

<script>
import ArticleListItem from '@/components/article/ArticleListItem'
import Pagination from '@/components/Pagination'
import http from '@/util/http-common'
export default {
  name: 'ArticleList',
  components: {
    ArticleListItem,
    Pagination
  },
  data() {
    return {
      articles: [],
      pageInfo: {
        endPageNo: 0,
        totalCnt: 0,
        ItemInPage: 5
      }
    }
  },
  methods: {
    getArticleList(pageNo) {
      http
        .get(`article/list?pageNo=${pageNo}`)
        .then((res) => {
          console.log(res)
          this.articles = res.data.articleList
          this.pageInfo.totalCnt = res.data.totalCount
          this.pageInfo.endPageNo = Math.ceil(res.data.totalCount / 5)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onPaging(pageNo) {
      this.getArticleList(pageNo)
    }
  },
  mounted() {
    this.getArticleList(1)
  }
}
</script>

<style scoped>
a {
  color: #42b983;
}

a:hover {
  color: #043a22;
}
</style>
