<template>
  <textarea
    id="textArea"
    class="col-8 scroll-area"
    v-model="review"
    @keydown.capture="onKeydown"
    @keyup.capture="keyupDebounce(onKeyup, 400)">
  </textarea>
</template>

<script>
export default {
  props: {
    stompClient: Object,
    operation: Object,
    codeList: Array,
    roomIdx: String
  },
  data() {
    return {
      review: '',
      debounce: {
        timer: null,
        isFirst: true
      },
      ot: {
        type: null,
        startIdx: 0,
        endIdx: 0,
        string: ''
      },
      deleteIdx: {
        start: null,
        end: null
      },
      insertIdx: {
        start: null,
        end: null
      }
    }
  },
  methods: {
    sendOperation() {
      var option = {
        type: 'operation',
        articleIdx: this.codeList[0].articleIdx,
        roomIdx: this.roomIdx,
        codeIdx: this.codeList[0].idx,
        userIdx: localStorage.getItem('userIdx'),
        otStartIdx: this.ot.startIdx,
        otEndIdx: this.ot.endIdx,
        otString: this.ot.string
      }
      this.stompClient.send(
        '/operation/' + this.roomIdx,
        JSON.stringify(option),
        {}
      )
      // Ack를 받기전까지 코드를 수정할 수 없음
      const codeArea = document.getElementById('textArea')
      codeArea.readOnly = true
    },
    keyupDebounce(fn, wait) {
      const keyboardEvent = event

      if (this.isAlnum(event.keyCode) || this.isDel(event.keyCode)) {
        if (this.debounce.timer) {
          clearTimeout(this.debounce.timer)
          this.debounce.timer = null
        }
        this.debounce.timer = setTimeout(() => {
          fn(keyboardEvent)
          this.debounce.isFirst = true
        }, wait)
      }
    },
    onKeyup(event) {
      if (this.isDel(event.keyCode)) {
        // 삭제
        if (this.ot.startIdx === this.ot.endIdx) {
          // 블럭단위 삭제가 아닌경우
          this.ot.startIdx = event.target.selectionEnd
        }
        console.log(`delete@${this.ot.startIdx}-${this.ot.endIdx}`)
        this.ot.string = ''
      } else {
        // 삽입
        const text = this.review
        let insertText = ''
        console.log(this.ot.startIdx, this.ot.endIdx)
        if (this.ot.startIdx === this.ot.endIdx) {
          insertText = text.slice(this.ot.startIdx, event.target.selectionEnd)
        }
        console.log(`insert@${this.ot.startIdx}-${this.ot.endIdx}'${insertText}'`)
        this.ot.string = insertText
      }
      this.sendOperation()
    },
    onKeydown() {
      if (this.debounce.isFirst && (this.isAlnum(event.keyCode) || this.isDel(event.keyCode))) {
        this.ot.startIdx = event.target.selectionStart
        this.ot.endIdx = event.target.selectionEnd
        this.debounce.isFirst = false
      }
    },
    isAlpha(charCode) {
      return ((charCode >= 65) && (charCode <= 90))
    },
    isDigit(charCode) {
      return ((charCode >= 48) && (charCode <= 57))
    },
    isDel(charCode) {
      return (charCode === 8)
    },
    isAlnum(charCode) {
      return (this.isAlpha(charCode) || this.isDigit(charCode) || charCode === 32)
    },
    receiveOperation() {
      // operation을 받았을 때 client의 코드데이터를 변경
      const text = this.review
      const otStartIdx = this.operation.startIdx
      const otEndIdx = this.operation.endIdx
      const otString = this.operation.string

      this.review = text.slice(0, otStartIdx) + otString + text.slice(otEndIdx)
    },
    receiveAck() {
      // Ack를 받아 코드변경권한을 얻음
      const codeArea = document.getElementById('textArea')
      codeArea.readOnly = false
    },
    setReview() {
      this.review = this.codeList[0].reviewContents
    }
  },
  watch: {
    codeList: 'setReview'
  }
}
</script>

<style>

</style>
