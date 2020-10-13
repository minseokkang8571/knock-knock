<template>
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
</template>

<script>
import { mapState } from 'vuex'
export default {
  props: {
    chatList: Array,
    stompClient: Object,
    roomIdx: String
  },
  data() {
    return {
      chatting: ''
    }
  },
  methods: {
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
    }
  },
  computed: {
    ...mapState(['userInfo'])
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
