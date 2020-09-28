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
    <!-- <article>
      <div class="mt-5 d-flex justify-content-between">
        <h2>import Login from '@/views/Login.vue'</h2>
        <span class="align-self-end">작성자 : ipsum</span>
      </div>
        <div class="text-left">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eligendi non quis exercitationem culpa nesciunt nihil aut nostrum explicabo reprehenderit optio amet ab temporibus asperiores quasi cupiditate. Voluptatum ducimus voluptates voluptas?
        </div>
      <div class="d-flex justify-content-start">
        <button class="tag mt-1 mr-2">tag</button>
        <button class="tag mt-1">tag</button>
      </div>
    </article> -->
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
import http from '@/util/http-common'
export default {
  name: 'ReviewList',
  data() {
    return {
      rooms: []
    }
  },
  components: {
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
