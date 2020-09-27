<template>
  <div class="container">
    <article v-for="(article, idx) in articles" :key="idx">
      <div class="mt-5 d-flex justify-content-between">
        <h2 @click="toDetail(article.idx)" class="common-title title-overflow">{{ article.title }}</h2>
        <span class="align-self-end">작성자 : ipsum {{ article.formatedRegDate }}</span>
      </div>
        <p class="text-left content-overflow">{{ article.contents }}</p>
      <div class="d-flex justify-content-start">
        <button class="tag mt-1 mr-2">tag</button>
        <button class="tag mt-1">tag</button>
      </div>
    </article>

    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li class="page-item">
          <a class="page-link cursor-pointer" @click="toPrevious" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <li class="page-item" v-for="(idx) in pageIdx" :key="idx">
          <a class="page-link" v-if="endPageNo >= idx" @click="onPaging(idx)" href="#">{{ idx }}</a>
        </li>
        <li class="page-item">
          <a class="page-link cursor-pointer" @click="toNext" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import Vue from 'vue'
// import { mapState } from 'vuex'
import http from '@/util/http-common'
export default {
  name: 'ArticleList',
  data() {
    return {
      articles: [],
      articleTotalCnt: 0,
      articleInPage: 5,
      pageIdx: [],
      endPageNo: 0
    }
  },
  methods: {
    getArticleList(counter) {
      http
        .get(`article/list?pageNo=${counter}`)
        .then((res) => {
          console.log(res)
          this.articles = res.data.articleList
          this.articleTotalCnt = res.data.totalCount
          this.endPageNo = this.articleTotalCnt / 5
        })
        .catch((err) => {
          console.log(err)
        })
    },
    toDetail(idx) {
      this.$router.push(`articles?articleIdx=${idx}`)
    },
    onPaging(idx) {
      this.getArticleList(idx)
    },
    toNext() {
      console.log('toNext')
      if (this.pageIdx[0] + this.articleInPage < this.endPageNo) {
        for (var i = 0; i < this.pageIdx.length; i++) {
          Vue.set(this.pageIdx, i, this.pageIdx[i] + this.articleInPage)
        }
      } else {
        alert('End')
      }
    },
    toPrevious() {
      console.log('toPrevious')
      if (this.pageIdx[0] - this.articleInPage > 0) {
        for (var i = 0; i < this.pageIdx.length; i++) {
          Vue.set(this.pageIdx, i, this.pageIdx[i] - this.articleInPage)
        }
      } else {
        alert('End')
      }
    },
    setPageIdx() {
      const articleInPage = this.articleInPage
      for (var i = 1; i <= articleInPage; i++) {
        this.pageIdx.push(i)
      }
    }
  },
  mounted() {
    this.setPageIdx()
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
