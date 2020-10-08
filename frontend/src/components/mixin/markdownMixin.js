// 마크다운 에디터 사용시 사용하는 공통 기능

const markdownMixin = {
  methods: {
    tabber(event) {
      // 탭을 눌렀을 때, 4space만큼 커서와 내용이 이동하도록 하는 함수
      console.log(event)
      const text = this.form.contents
      const originalSelectionStart = event.target.selectionStart
      const startText = text.slice(0, originalSelectionStart)
      const endText = text.slice(originalSelectionStart)
      this.form.contents = `${startText}\t${endText}`
      // TO DO:: two space 추가

      event.target.value = this.form.contents
      event.target.selectionEnd = event.target.selectionStart = originalSelectionStart + 1
    },
    toggleCtrlShortCut(event) {
      // ctrl 숏컷을 사용해 커서블록부분을 Bold, Italic처리하는 함수
      const text = this.form.contents
      const selectionStart = event.target.selectionStart
      const selectionEnd = event.target.selectionEnd
      const startText = text.slice(0, selectionStart)
      const targetText = text.slice(selectionStart, selectionEnd)
      const endText = text.slice(selectionEnd)
      let specialChr = ''
      let changedText = ''

      if (selectionStart === selectionEnd) {
        // 커서블록이 없는 경우
        specialChr = ''
      } else if (event.keyCode === 66) {
        // ctrl+b
        specialChr = '**'
      } else if (event.keyCode === 73) {
        // ctrl+i
        specialChr = '*'
      }

      const targetTextLen = targetText.length
      const specialChrLen = specialChr.length
      let endCursorChange = specialChrLen * 2
      if (targetText.substr(0, specialChrLen) === specialChr &&
      targetText.substr(targetTextLen - specialChrLen, specialChrLen) === specialChr) {
        // 활성화 상태인 경우, 양쪽 특수문자를 제거
        changedText = targetText.slice(specialChrLen, targetTextLen - specialChrLen)
        endCursorChange = endCursorChange * -1
      } else {
        // 양쪽 특수문자를 삽입
        changedText = specialChr + targetText + specialChr
      }
      this.form.contents = startText + changedText + endText

      event.target.value = this.form.contents
      event.target.selectionStart = selectionStart
      event.target.selectionEnd = selectionEnd + endCursorChange
    },
    onModal(event) {
      // ctrl + / 입력시 previewModal을 띄움
      // 하위 컴포넌트 PreviewModal의 함수실행
      this.$refs.previewModal.onModal()
    }
  }
}
export default markdownMixin
