<template>
  <nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
      <li class="page-item">
        <a class="page-link cursor-pointer" @click="toPrevious" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li class="page-item" v-for="(idx) in pageIdx" :key="idx">
        <a class="page-link"
          v-if="pageInfo.endPageNo >= idx"
          @click="onPaging(idx)">
          {{ idx }}
        </a>
      </li>
      <li class="page-item">
        <a class="page-link cursor-pointer" @click="toNext" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
import Vue from 'vue'
export default {
  props: {
    pageInfo: Object
  },
  data() {
    return {
      pageIdx: [],
      pageIndexNum: 5
    }
  },
  methods: {
    toNext() {
      // 페이지네이션 idx를 ItemInPage만큼 증가
      if (this.pageIdx[0] + this.pageInfo.ItemInPage < this.pageInfo.endPageNo) {
        for (var i = 0; i < this.pageIdx.length; i++) {
          Vue.set(this.pageIdx, i, this.pageIdx[i] + this.pageInfo.ItemInPage)
        }
      } else {
        alert('End')
      }
    },
    toPrevious() {
      // 페이지네이션 idx를 ItemInPage만큼 감소
      if (this.pageIdx[0] - this.pageInfo.ItemInPage > 0) {
        for (var i = 0; i < this.pageIdx.length; i++) {
          Vue.set(this.pageIdx, i, this.pageIdx[i] - this.pageInfo.ItemInPage)
        }
      } else {
        alert('End')
      }
    },
    setPageIdx() {
      // 초기 idx 설정
      for (var i = 1; i <= this.pageIndexNum; i++) {
        this.pageIdx.push(i)
      }
    },
    onPaging(pageNo) {
      // 인덱스 선택시 상위 컴포넌트로 인덱스를 전송
      this.$emit('onPaging', pageNo)
    }
  },
  mounted() {
    this.setPageIdx()
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

.page-link {
  cursor: pointer;
}
</style>
