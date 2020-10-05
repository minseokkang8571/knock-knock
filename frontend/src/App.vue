<template>
  <div id="app">
    <b-navbar toggleable="sm" type="light" variant="light">
      <b-navbar-toggle target="nav-text-collapse"></b-navbar-toggle>

      <router-link to="/">
        <b-navbar-brand>Knock Knock</b-navbar-brand>
      </router-link>
      <SearchBar />
      <b-collapse id="nav-text-collapse" is-nav class="d-flex justify-content-end">
        <b-button @click="onCreate" variant="outline-success" class="my-2 my-sm-0 mr-3">New issue</b-button>
        <b-navbar-nav>
          <router-link :to="{ name: 'ReviewList' }">
            <a class="mr-3">Review</a>
          </router-link>
          <div v-if="!isLogin">
            <router-link :to="{ name: 'Signin' }">
              <a class="mr-3">Sign in</a>
            </router-link>
            <router-link :to="{ name: 'Signup' }">
              <a>Sign up</a>
            </router-link>
          </div>
          <div v-if="isLogin">
            <a class="mr-3 cursor-pointer" @click="onSignout">Sign out</a>
          </div>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view />
  </div>
</template>

<script>
import SearchBar from '@/components/SearchBar'
import { mapState, mapActions } from 'vuex'
export default {
  components: {
    SearchBar
  },
  computed: {
    ...mapState(['isLogin', 'userInfo'])
  },
  methods: {
    ...mapActions(['onSignout']),
    onCreate() {
      // 기존 페이지가 ArticleCreate가 아닌 경우 Article생성
      this.$store.commit('setCurrentArticle', null)
      if (this.$route.name !== 'ArticleCreate') {
        this.$router.push({ name: 'ArticleCreate' })
      }
    }
  }
}
</script>

<style>
.tag {
  padding: 2px 15px 2px 15px;
  background-color: #90d39f;
  border-radius: 3px;
}
.common-title {
  cursor: pointer;
}

.common-title:hover {
  color: #4ae7a3;
}

.container {
  margin-top: 50px;
}

.scroll-area {
  /* background: white; */
  height: 75vh;
  padding: 1em 1em;
  /* margin: 1em; */
  overflow-y: scroll;
  box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, 0.3);
}

.cursor-pointer{
  cursor: pointer;
}

.title-overflow {
  display: inline-block;
  text-align: left;
  overflow: hidden;
  white-space: normal;
  width: 100%;
  line-height: 1.2;
}

.content-overflow {
  text-align: left;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  width: 100%;
  line-height: 1.2;
  height: 3.6em;
}

.color-grey {
  color: grey
}

#input-form p {
  text-align: left;
  margin-bottom: 0px;
  margin-top: 10px;
}

#input-form .btn {
  margin-top: 5px;
}
</style>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

a {
  color: #42b983;
}

a:hover{
  color: #043a22;
}
</style>
