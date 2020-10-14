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
      changedText: {
        start: null,
        end: null
      },
      ot: {
        idx: 0,
        string: ''
      },
      deleteIdx: {
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
        otIdx: this.ot.idx,
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
        if (this.deleteIdx.start === this.deleteIdx.end) {
          // 블럭단위 삭제가 아닌경우
          this.deleteIdx.end = event.target.selectionEnd
        }
        console.log(`delete@${this.deleteIdx.end}-${this.deleteIdx.start}`)
      } else {
        // 삽입
        this.changedText.end = event.target.selectionEnd
        const text = this.review
        const insertText = text.slice(this.changedText.start, this.changedText.end)
        this.ot.idx = this.changedText.start
        this.ot.string = insertText
        console.log(`insert@${this.changedText.start}'${insertText}'`)
        this.sendOperation()
      }
    },
    onKeydown() {
      if (this.debounce.isFirst && (this.isAlnum(event.keyCode) || this.isDel(event.keyCode))) {
        this.changedText.start = this.deleteIdx.end = event.target.selectionStart
        this.deleteIdx.start = event.target.selectionEnd
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
      const otIdx = this.operation.idx
      const otString = this.operation.string

      this.review = text.slice(0, otIdx) + otString + text.slice(otIdx)
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
