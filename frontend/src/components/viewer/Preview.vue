<template>
  <div>
    <!-- v-html을 통한 XSS공격을 막기위해 sanitizer 라이브러리를 사용 -->
    <div
      class="hljs"
      v-dompurify-html="convertMarkdown()">
    </div>
  </div>
</template>

<script>
import marked from 'marked'
import hljs from 'highlight.js'
import '@/assets/styles/github-gist.css'
export default {
  props: {
    contents: String
  },
  methods: {
    convertMarkdown() {
      if (this.contents) {
        const tags = ['pre', 'code']
        // https://marked.js.org/using_advanced#options 옵션에 대한 문서
        marked.setOptions({
          renderer: new marked.Renderer(),
          highlight: function(code) {
            return hljs.highlightAuto(code).value
          },
          breaks: true,
          smartLists: true,
          smartypants: false
        })

        let changedText = marked(this.contents)
        // marked.js 변환 태그에 클래스 부여
        for (const tag of tags) {
          let className
          const regex = new RegExp('<' + tag + '>', 'g')

          switch (tag) {
            case 'pre': className = 'code-wide'; break
            case 'code': className = 'code-narrow'; break
          }
          changedText = changedText.replace(regex, `<${tag} class="${className}">`)
        }

        return changedText
      }
    }
  }

}
</script>

<style scoped>
.hljs {
  padding: 0px 0px 0px 0px;
}
</style>
