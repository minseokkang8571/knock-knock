<template>
  <div class="container">
    <article v-for="(room, idx) in rooms" :key="idx">
      <div class="d-flex justify-content-between">
        <h2 @click="toDetail(room.idx)" class="common-title">{{ room.title }}</h2>
        <span class="align-self-end">작성자 : {{ room.name }}</span>
      </div>
        <div class="text-left">
          {{ room.contents }}
        </div>
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
          <a class="page-link" v-if="endPageNo >= idx" @click="getRoomList(idx)" href="#">{{ idx }}</a>
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
import http from '@/util/http-common'
export default {
  name: 'ReviewList',
  data() {
    return {
      rooms: [],
      roomTotalCnt: 0,
      roomInPage: 5,
      pageIdx: [],
      endPageNo: 0
    }
  },
  components: {
  },
  methods: {
    init() {
      this.setPageIdx()
      this.getRoomList(1)
    },
    getRoomList(counter) {
      http
        .get(`review/getRoom?pageNo=${counter}`)
        .then((res) => {
          this.rooms = res.data.roomList
          this.roomTotalCnt = res.data.totalCount
          this.endPageNo = this.roomTotalCnt / 5
        })
        .catch((err) => {
          console.log(err)
        })
    },
    toDetail(roomIdx) {
      this.$router.push(`/reviews?roomIdx=${roomIdx}`)
    },
    toNext() {
      console.log('toNext')
      if (this.pageIdx[0] + this.roomInPage < this.endPageNo) {
        for (var i = 0; i < this.pageIdx.length; i++) {
          Vue.set(this.pageIdx, i, this.pageIdx[i] + this.roomInPage)
        }
      } else {
        alert('End')
      }
    },
    toPrevious() {
      console.log('toPrevious')
      if (this.pageIdx[0] - this.roomInPage > 0) {
        for (var i = 0; i < this.pageIdx.length; i++) {
          Vue.set(this.pageIdx, i, this.pageIdx[i] - this.roomInPage)
        }
      } else {
        alert('End')
      }
    },
    setPageIdx() {
      const roomInPage = this.roomInPage
      for (var i = 1; i <= roomInPage; i++) {
        this.pageIdx.push(i)
      }
    }
  },
  mounted() {
    this.init()
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

.review-title {
  cursor: pointer;
}

.review-title:hover {
  color: #4ae7a3;
}

</style>
