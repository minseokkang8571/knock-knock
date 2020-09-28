<template>
  <div class="container mt-1">
    <h4>ReviewSession</h4>
    <div class="row">
      <div class="col-8 scroll-area">
        <h4>ReviewPart</h4>ion and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
      </div>
      <div class="col-4 scroll-area">
        <h4>ChatPart</h4>
      </div>
    </div>
    <div class="d-flex justify-content-end mt-2">
      <button class="btn btn-primary mr-2">save</button>
      <button class="btn btn-secondary">exit</button>
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
      content: null,
      chatList: [],
      codeList: []
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
          this.chatList = res.data.chatList
          this.codeList = res.data.codeList
        })
        .catch((err) => {
          console.log(err)
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
            this.chatList.push(JSON.parse(res.body))
          })
        }
        // error => {
        //   this.connected = false
        // }
      )
    },
    send() {
      var option = {
        roomIdx: this.roomIdx,
        id: this.userInfo.idx,
        contents: this.contents,
        name: this.userInfo.name
      }
      this.stompClient.send(
        'receive' + this.roomIdx,
        JSON.stringify(option),
        {}
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
