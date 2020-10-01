<template>
  <div class="container mt-1">
    <h4>ReviewSession</h4>
    <div class="row">
      <div class="col-8 scroll-area">
        <h4>ReviewPart</h4>
        <textarea id="textArea" v-model="review">
        </textarea>
        <button class="btn btn-primary mr-2" @click="sendLock('lock')">수정 시작</button>
        <button class="btn btn-primary mr-2" @click="modifyCode()">수정 완료</button>
      </div>
      <div class="col-4 scroll-area">
        <b-row v-for="list in chatList" v-bind:key="list.idx">
          <b-col cols="2" class="text-align:right" v-if="userInfo.idx == list.userIdx">
            <div class="outgoing-chats-msg">
              <p>{{ list.contents }}</p>
            </div>
          </b-col>
          <b-col v-if="userInfo.idx != list.userIdx">
            <div class="received-msg-inbox">
            <p>{{ list.name }} : {{ list.contents }}</p>
            </div>
            <!-- <div class="received-msg-inbox">
              <p>{{ list.contents }}</p>
            </div> -->
          </b-col>
        </b-row>
        <footer absolute class="msg-bottom">
          <b-col cols="10">
            <input
              type="text"
              class="form-control"
              id="chatting"
              v-model="chatting"
              placeholder="메세지를 입력해주세요."
            />
          </b-col>
          <b-col cols="2" class="hover" @click="sendMsg()" @keypress.enter="sendMsg()"
            >전송</b-col
          >
        </footer>
      </div>
    </div>
    <div class="d-flex justify-content-end mt-2">
      <button class="btn btn-primary mr-2" @click="save()">save</button>
      <router-link :to="{ name: 'ReviewList' }">
      <button class="btn btn-secondary">exit</button>
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
      review: this.review
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
          if (res.data.state) {
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
          if (res.data.status) {
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
                this.review = JSON.parse(res.body).contents
                tmp.readOnly = false
              }
            } else {
              this.chatList.push(JSON.parse(res.body))
            }
          })
        }
        // error => {
        //   this.connected = false
        // }
      )
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
        contents: this.review,
        userIdx: this.userInfo.idx
      }
      this.stompClient.send(
        '/lock/' + this.roomIdx,
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
.list-form {
  height: 530px;
  overflow: scroll;
}
.received-msg-inbox {
  width: 57%;
}
.received-msg-inbox p {
  background: #efefef none repeat scroll 0 0;
  border-radius: 10px;
  color: #646464;
  font-size: 12px;
  margin: 0;
  padding: 5px 10px 5px 12px;
  width: 100%;
}
/* .outgoing-chats {
  overflow: hidden;
  margin: 26px 20px;
}
.outgoing-chats-msg p {
  background: #b2ebf2 none repeat scroll 0 0;
  color: black;
  border-radius: 10px;
  font-size: 12px;
  margin: 0;
  padding: 5px 10px 5px 12px;
  width: 100%;
}
.outgoing-chats-msg {
  float: left;
  width: 46%;
  margin-left: 45%;
} */
</style>
