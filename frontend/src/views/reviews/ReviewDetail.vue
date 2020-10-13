<template>
  <div class="container mt-1">
    <h4>ReviewSession</h4>
    <div class="row">
      <!-- 코드리뷰 -->
      <ReviewCode
        :operation="operation"
        :stompClient="stompClient"
        :codeList="codeList"
        :roomIdx="roomIdx"
        ref="reviewCode"
      />
      <!-- 채팅 -->
      <ReviewChat
        :chatList="chatList"
        :stompClient="stompClient"
        :roomIdx="roomIdx"
      />
    </div>
    <!-- 버튼리스트 -->
    <div class="d-flex justify-content-end mt-2">
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
import ReviewCode from '@/components/review/ReviewCode'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import http from '@/util/http-common'
import { mapState } from 'vuex'
export default {
  components: {
    ReviewChat,
    ReviewCode
  },
  data() {
    return {
      roomIdx: this.$route.query.roomIdx,
      chatList: [],
      codeList: [],
      stompClient: null,
      operation: {
        idx: null,
        string: null
      },
      review: ''
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
            if (res.body === 'out') {
              this.disconnect()
              this.$router.push('/review')
              alert('코드리뷰가 종료되었습니다.')
            } else if (JSON.parse(res.body).type === 'operation') {
              if (parseInt(JSON.parse(res.body).userIdx) === this.userInfo.idx) {
                this.$refs.reviewCode.receiveAck()
              } else {
                this.operation.idx = JSON.parse(res.body).otIdx
                this.operation.string = JSON.parse(res.body).otString
                this.$refs.reviewCode.receiveOperation()
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
    }
  },
  mounted() {
    this.init()
  }
}
</script>

<style scoped>

</style>
