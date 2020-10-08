<template>
  <div class="d-flex">
    <div>
      <b-dropdown
        id="dropdown-1"
        text="search type"
        class="m-md-2"
        size="sm">
        <b-dropdown-item
          v-for="(type, idx) in dropDownList" :key="idx"
          @click="setSearchType(type)">
          {{ type }}
        </b-dropdown-item>
      </b-dropdown>
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
      },
      dropDownList: ['contents', 'name', 'title', 'tag']
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
    },
    setSearchType(type) {
      this.searchInfo.type = type
    }
  }
}
</script>

<style>

</style>
