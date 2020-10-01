<template>
  <div>
      <div class="hljs" ref="hlDiv" v-html="convertMarkdown()"></div>
  </div>
</template>

<script>
import marked from 'marked'
import hljs from 'highlight.js'
export default {
  props: {
    contents: String
  },
  methods: {
    convertMarkdown() {
      marked.setOptions({
        renderer: new marked.Renderer(),
        highlight: function(code) {
          return hljs.highlightAuto(code).value
        },
        pedantic: false,
        gfm: true,
        tables: true,
        breaks: false,
        sanitize: false,
        smartLists: true,
        smartypants: false,
        xhtml: false
      })
      return marked(this.contents)
    }
  }

}
</script>

<style>

</style>
