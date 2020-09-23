<template>
  <div id="app">
    <b-navbar toggleable="sm" type="light" variant="light">
      <b-navbar-toggle target="nav-text-collapse"></b-navbar-toggle>

      <router-link to="/">
        <b-navbar-brand>Knock Knock</b-navbar-brand>
      </router-link>
      <SearchBar />
      <b-collapse id="nav-text-collapse" is-nav class="d-flex justify-content-end">
        <b-navbar-nav>
          <router-link :to="{ name: 'ReviewList' }">
            <a class="mr-3">Review</a>
          </router-link>
          <router-link :to="{ name: 'Login' }">
            <a class="mr-3">Login</a>
          </router-link>
          <router-link :to="{ name: 'Signup' }">
            <a>Sign up</a>
          </router-link>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view/>
  </div>
</template>

<script>
import SearchBar from '@/components/SearchBar'
export default {
  components: {
    SearchBar
  }
}
</script>

<style>
.tag {
  padding: 2px 15px 2px 15px;
  background-color: #90d39f;
  border-radius: 3px;
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
