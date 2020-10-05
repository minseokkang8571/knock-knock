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
      },
      searchInfo: {
        text: '',
        tag: '',
        type: ''
      }
    }
  },
  methods: {
    init() {
      this.setSearchInfo()
      this.getArticleList(1)
    },
    getArticleList(pageNo) {
      http
        .get(`article/list?pageNo=${pageNo}` +
        `&searchText=${this.searchInfo.text}` +
        `&searchTag=${this.searchInfo.tag}` +
        `&searchType=${this.searchInfo.type}`)
        .then((res) => {
          console.log(res)
          this.articles = res.data.articleList
          this.pageInfo.totalCnt = res.data.totalCount
          this.pageInfo.endPageNo = Math.ceil(this.pageInfo.totalCnt / this.pageInfo.ItemInPage)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onPaging(pageNo) {
      this.getArticleList(pageNo)
    },
    setSearchInfo() {
      if (this.$route.name === 'ArticleSearchList') {
        // SearchBar를 통해 데이터가 입력된 경우
        this.searchInfo.text = this.$route.query.searchText
        this.searchInfo.tag = this.$route.query.searchTag
        this.searchInfo.type = this.$route.query.searchType
      }
    }
  },
  mounted() {
    this.init()
  },
  watch: {
    // 검색어 변경시, 변경된 검색어로 API를 다시 요청
    $route: 'init'
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
