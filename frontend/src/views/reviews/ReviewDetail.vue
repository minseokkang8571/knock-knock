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
      <div class="col-4 d-flex flex-column">
        <div class="scroll-area chat-area">
          <div
            v-for="list in chatList"
            v-bind:key="list.idx"
            class="message"
            :class="{ 'message-out': list.name === userInfo.name, 'message-in': list.name !== userInfo.name }">
            <p class="font-weight-bold mb-0 mr-1 text-subtitle-1 pl-6">{{ list.name }}:</p>
            <p class="real mb-0">{{ list.contents }}</p>
          </div>
        </div>
        <textarea
          v-model="chatting"
          cols="30"
          rows="4"
          @keydown.enter="sendMsg()">
        </textarea>
      </div>
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
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  data() {
    return {
      roomIdx: this.$route.query.roomIdx,
      chatList: [],
      codeList: [],
      chatting: this.chatting,
      review: this.review,
      debounce: {
        timer: null,
        isFirst: true
      },
      changedText: {
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
      console.log(localStorage.getItem('token'))
      if (localStorage.getItem('token')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
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
          console.log(err)
        })
    },
    save() {
      const config = {}
      if (localStorage.getItem('token')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      http
        .put('review/saveCode/' + this.roomIdx, config)
        .then((res) => {
          this.sendOut()
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
      if (localStorage.getItem('token')) {
        config.headers = {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
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
            } else if (JSON.parse(res.body).type === 'lock') {
              if (parseInt(JSON.parse(res.body).userIdx) !== this.userInfo.idx) {
                alert('다른 사용자가 입력중입니다.')
                tmp.readOnly = true
              }
            } else if (JSON.parse(res.body).type === 'unlock') {
              if (parseInt(JSON.parse(res.body).userIdx) !== this.userInfo.idx) {
                alert('수정 하실 수 있습니다.')
                console.log(JSON.parse(res.body).contents)
                this.review = JSON.parse(res.body).contents
                tmp.readOnly = false
              }
            } else {
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
    sendMsg() {
      var option = {
        roomIdx: this.roomIdx,
        userIdx: this.userInfo.idx,
        contents: this.chatting,
        name: this.userInfo.name
      }
      this.stompClient.send(
        '/receive/' + this.roomIdx,
        JSON.stringify(option),
        {}
      )
      this.chatting = ''
    },
    sendLock(type) {
      var option = {
        type: type,
        articleIdx: this.codeList[0].articleIdx,
        roomIdx: this.roomIdx,
        codeIdx: this.codeList[0].idx,
        userIdx: this.userInfo.idx,
        contents: this.review
      }
      this.stompClient.send(
        '/' + type + '/' + this.roomIdx,
        JSON.stringify(option),
        {}
      )
    },
    keyupDebounce(fn, wait) {
      const keyboardEvent = event

      if (this.isAlnum(event.keyCode)) {
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
      this.changedText.end = event.target.selectionEnd
      const text = this.review
      const insertText = text.slice(this.changedText.start, this.changedText.end)
      console.log(`insert@${this.changedText.start}'${insertText}'`)
    },
    onKeydown() {
      if (this.debounce.isFirst && this.isAlnum(event.keyCode)) {
        this.changedText.start = event.target.selectionStart
        this.debounce.isFirst = false
      }
    },
    isAlpha(charCode) {
      return ((charCode >= 65) && (charCode <= 90))
    },
    isDigit(charCode) {
      return ((charCode >= 48) && (charCode <= 57))
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

.chat-area {
  height: 62vh;
  overflow-x: hidden;
}

.message {
  width: 45%;
  /* border-radius: 10px; */
  padding: 0.5em;
  /* margin-bottom: 0.5em; */
  font-size: 0.8em;
}

.message-in {
  /* background: #407fff; */
  color: black;
  margin-right: 50%;

  /* padding: 0; */
}

.message-out {
  /* background: #407fff; */
  color: black;
  margin-left: 50%;

  /* padding: 0; */
}

.message-out .real {
  background: #407fff;
  color: white;
  border-radius: 10px;
  /* margin-left: 50%; */
}

.message-in .real {
  background: #f1f0f0;
  color: black;
  border-radius: 10px;
}

.pl-6 {
  padding: 0px 0px 0px 6px !important;
}

</style>
