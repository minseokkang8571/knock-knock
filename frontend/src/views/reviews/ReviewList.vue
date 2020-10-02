<template>
  <div class="container">
    <article v-for="(room, idx) in rooms" :key="idx">
      <ReviewListItem :room="room" />
    </article>

    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li class="page-item">
          <a class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
          <a class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import ReviewListItem from '@/components/review/ReviewListItem'
import http from '@/util/http-common'
export default {
  name: 'ReviewList',
  components: {
    ReviewListItem
  },
  data() {
    return {
      rooms: []
    }
  },
  methods: {
    init() {
      this.getRoomList()
    },
    getRoomList() {
      http
        .get('review/getRoom')
        .then((res) => {
          console.log(res)
          this.rooms = res.data.roomList
        })
        .catch((err) => {
          console.log(err)
        })
    },
    toDetail(roomIdx) {
      this.$router.push(`/reviews?roomIdx=${roomIdx}`)
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
