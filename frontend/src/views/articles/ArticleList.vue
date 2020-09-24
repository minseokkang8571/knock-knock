<template>
  <div class="container">
    <article>
      <div class="d-flex justify-content-between">
        <h2 @click="toDetail(articleId)" class="common-title">import Login from '@/views/Login.vue'</h2>
        <span class="align-self-end">작성자 : ipsum</span>
      </div>
        <div class="text-left">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eligendi non quis exercitationem culpa nesciunt nihil aut nostrum explicabo reprehenderit optio amet ab temporibus asperiores quasi cupiditate. Voluptatum ducimus voluptates voluptas?
        </div>
      <div class="d-flex justify-content-start">
        <button class="tag mt-1 mr-2">tag</button>
        <button class="tag mt-1">tag</button>
      </div>
    </article>
    <article>
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

export default {
  name: 'ArticleList',
  data() {
    return {
      articleId: 1
    }
  },
  components: {
  },
  methods: {
    toDetail(id) {
      this.$router.push(`/articles/${this.articleId}`)
    }
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
