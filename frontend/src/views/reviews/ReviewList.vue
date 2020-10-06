<template>
  <div class="container">
    <!-- 리뷰 리스트 -->
    <div v-for="(room, idx) in rooms" :key="idx">
      <ReviewListItem :room="room" />
    </div>
    <!-- 하단 페이지네이션 -->
    <Pagination
      :pageInfo="pageInfo"
      @onPaging="onPaging"
    />
  </div>
</template>

<script>
import ReviewListItem from '@/components/review/ReviewListItem'
import Pagination from '@/components/Pagination'
import http from '@/util/http-common'
export default {
  name: 'ReviewList',
  components: {
    ReviewListItem,
    Pagination
  },
  data() {
    return {
      rooms: [],
      pageInfo: {
        endPageNo: 0,
        totalCnt: 0,
        itemInPage: 5
      }
    }
  },
  methods: {
    init() {
      this.getRoomList(1)
    },
    getRoomList(pageNo) {
      http
        .get(`review/getRoom?pageNo=${pageNo}` +
        `&pageSize=${this.pageInfo.itemInPage}`)
        .then((res) => {
          console.log(res)
          this.rooms = res.data.roomList
          this.pageInfo.totalCnt = this.rooms.length
          this.pageInfo.endPageNo = Math.ceil(this.pageInfo.totalCnt / this.pageInfo.itemInPage)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onPaging(pageNo) {
      this.getRoomList(pageNo)
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
