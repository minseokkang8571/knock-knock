<template>
  <div class="d-flex">
    <div class="dropdown">
      <button
        type="button"
        class="btn btn-success dropdown-toggle mt-2 mb-2"
        data-toggle="dropdown"
        aria-haspopup="true"
        aria-expanded="false">
        Language
      </button>
      <div class="dropdown-menu">
        <a class="dropdown-item">python</a>
        <a class="dropdown-item">java</a>
      </div>
    </div>
    <div>
      <b-navbar type="light" variant="light">
        <b-nav-form>
          <b-form-input
            class="mr-sm-2"
            placeholder="Search"
            v-model="searchInfo.text">
          </b-form-input>
          <b-button
            variant="outline-success"
            class="my-2 my-sm-0"
            type="submit"
            @click="onSearch(searchInfo.text)">
            Search
          </b-button>
        </b-nav-form>
      </b-navbar>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchInfo: {
        text: '',
        tag: '',
        type: ''
      }
    }
  },
  methods: {
    onSearch(searchText) {
      event.preventDefault()
      this.$router.push(`search?searchText=${this.searchInfo.text}` +
      `&searchTag=${this.searchInfo.tag}` +
      `&searchType=${this.searchInfo.type}`)
        .catch((err) => {
          console.log(err)
        })
      this.searchInfo.text = ''
    }
  }
}
</script>

<style>

</style>
