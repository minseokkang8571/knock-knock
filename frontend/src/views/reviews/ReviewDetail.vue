<template>
  <div class="container mt-1">
    <h4>ReviewSession</h4>
    <div class="row">
      <!-- 코드리뷰 -->
      <textarea
        id="textArea"
        class="col-8 scroll-area"
        v-model="review"
        @keydown.capture="onKeydown"
        @keyup.capture="keyupDebounce(onKeyup, 400)">
      </textarea>
      <!-- 채팅 -->
      <ReviewChat
        :chatList="chatList"
        :stompClient="stompClient"
        :roomIdx="roomIdx"
      />
    </div>
    <!-- 버튼리스트 -->
    <div class="d-flex justify-content-end mt-2">
      {{ debounce.isFirst }}
      <button class="btn btn-primary mr-2" @click="sendLock('lock')">수정 시작</button>
      <button class="btn btn-primary mr-2" @click="sendLock('unlock')">수정 완료</button>
      <button class="btn btn-primary mr-2" @click="save()">save</button>
      <router-link :to="{ name: 'ReviewList' }">
      <button class="btn btn-secondary" @click="disconnect()">exit</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import ReviewChat from '@/components/review/ReviewChat'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  components: {
    ReviewChat
  },
  data() {
    return {
      roomIdx: this.$route.query.roomIdx,
      chatList: [],
      codeList: [],
      stompClient: null,
      review: this.review,

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
  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    init() {
      console.log(this.roomIdx)
      this.connect()
      this.enterRoom()
    },
    enterRoom() {
      // this.$store.dispatch('getChat', this.roomNumber)
      const config = {}
      console.log(localStorage.getItem('accessToken'))
      if (localStorage.getItem('accessToken')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        }
        http
          .get('review/enterRoom/' + this.roomIdx, config)
          .then((res) => {
            if (res.status === 200) {
              console.log('in')
              this.chatList = res.data.chatList
              this.codeList = res.data.codeList
              this.review = this.codeList[0].reviewContents
            }
          })
          .catch((err) => {
            if (err.request.status === 444) {
              this.updateToken()
              this.enterRoom()
            }
          })
      }
    },
    save() {
      const config = {}
      if (localStorage.getItem('accessToken')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        }
      }
      http
        .put('review/saveCode/' + this.roomIdx, config)
        .then((res) => {
          this.sendOut()
        })
        .catch((err) => {
          if (err.request.status === 444) {
            this.updateToken()
            this.save()
          }
        })
    },
    modifyCode() {
      const config = {}
      const data = {
        roomIdx: this.roomIdx,
        codeIdx: this.codeList[0].idx,
        userIdx: this.userInfo.idx,
        contents: this.review
      }
      if (localStorage.getItem('accessToken')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        }
      }
      http
        .put(
          'review/modifyCode',
          data, config).then(res => {
          if (res.status === 200) {
            console.log('success')
            this.sendLock('unlock')
          }
        })
        .catch((err) => {
          if (err.request.status === 444) {
            this.updateToken()
            this.modifyCode()
          }
        })
    },
    updateToken() {
      const config = {}
      const refreshToken = localStorage.getItem('refreshToken')
      if (refreshToken) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${refreshToken}`
        }
        http
          .get('auth/access', config)
          .then((res) => {
            localStorage.removeItem('accessToken')
            localStorage.setItem('accessToken', res.data.accessToken)
            console.log('update accessToken')
          })
      }
    },
    connect() {
      const serverURL = 'http://localhost:4000/chat'
      const socket = new SockJS(serverURL)
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true
          console.log(frame)
          this.stompClient.subscribe('/send/' + this.roomIdx, res => {
            var tmp = document.getElementById('textArea')
            if (res.body === 'out') {
              this.disconnect()
              this.$router.push('/review')
              alert('코드리뷰가 종료되었습니다.')
            // } else if (JSON.parse(res.body).type === 'lock') {
            //   if (parseInt(JSON.parse(res.body).userIdx) !== this.userInfo.idx) {
            //     alert('다른 사용자가 입력중입니다.')
            //     tmp.readOnly = true
            //   }
            } else if (JSON.parse(res.body).type === 'unlock') {
              if (parseInt(JSON.parse(res.body).userIdx) === this.userInfo.idx) {
                console.log('ACK')
                tmp.readOnly = false
              } else {
                const text = this.review
                const otIdx = JSON.parse(res.body).otIdx
                const otString = JSON.parse(res.body).otString

                this.review = text.slice(0, otIdx) + otString + text.slice(otIdx)
              }
            } else {
              var date = new Date()
              var now = date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + ':' + date.getMilliseconds()
              console.log('응답 시간 : ' + now)
              this.chatList.push(JSON.parse(res.body))
            }
          })
        }
      )
    },
    disconnect() {
      if (this.stompClient !== null) {
        this.stompClient.disconnect()
      }
    },
    sendOut() {
      this.stompClient.send(
        '/out/' + this.roomIdx, {}
      )
    },
    sendLock() {
      var option = {
        type: 'unlock',
        articleIdx: this.codeList[0].articleIdx,
        roomIdx: this.roomIdx,
        codeIdx: this.codeList[0].idx,
        userIdx: this.userInfo.idx,
        // contents: this.review,
        otIdx: this.ot.idx,
        otString: this.ot.string
      }
      this.stompClient.send(
        '/unlock/' + this.roomIdx,
        JSON.stringify(option),
        {}
      )
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
        console.log(`insert@${this.changedText.start}'${insertText}'`)
        this.ot.idx = this.changedText.start
        this.ot.string = insertText
        // this.tmp.readOnly = true
        this.sendLock()
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
    }
  },
  mounted() {
    this.init()
  }
}
</script>

<style scoped>

</style>
