<template>
  <div class="d-flex">
    <div class="btn-group">
      <button type="button" class="btn btn-success dropdown-toggle mt-2 mb-2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
          <b-form-input class="mr-sm-2" placeholder="Search"></b-form-input>
          <b-button variant="outline-success" class="my-2 my-sm-0" type="submit">Search</b-button>
        </b-nav-form>
      </b-navbar>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>
