<template>
  <v-container >
    <v-row no-gutters>
      <v-col v-for="chat in chats" :key="chat.name" cols="12" sm="4">
        <v-card class="mx-auto" max-width="344" outlined>
          <v-list-item three-line>
            <v-list-item-content>
              <div class="overline mb-4">{{chat.key}}</div>
              <v-list-item-title class="headline mb-1">{{chat.name}}</v-list-item-title>
              <v-list-item-subtitle>{{chat.members.length}} members have joined this chat.</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-card-actions>
            <v-btn text @click="enterChat(chat)" v-if="!chat.isAlreadyJoined">Join</v-btn>
            <v-btn text disabled v-if="chat.isAlreadyJoined">Joined</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import * as firebase from 'firebase'
import { AXIOS } from '../../main'

export default {
  data () {
    return {
      chats: [],
      loading: false
    }
  },
  mounted () {
    // this.loadRecentChats()
    this.loadChats()
  },
  // computed: {
  //   user () {
  //     return this.$store.getters.user
  //   },
  //   chats () {
  //     return this.loadedChats
  //   }
  // },
  methods: {
    loadChats() {
      self = this
      AXIOS.get("/allChats")
        .then(response => {
          console.log(response)
          self.chats = response.data
          self.chats.forEach(element => {
            console.log(element)
            element.members.forEach(
              member => {
                console.log(self.$store.getters.getUsername);
                if (self.$store.getters.getUsername == member.username) {
                  element.isAlreadyJoined = true
                }
              }
            )
          });
        }
        )
        .catch(err => console.log(err)
        )
    },
    // loadRecentChats (lastKey) {
    //   let that = this
    //   firebase.database().ref('chats').orderByKey().limitToLast(20).once("value", function(snapshot) {
    //     snapshot.forEach(function(childSnapshot) {
    //       let chat = childSnapshot.val()
    //       chat.key = childSnapshot.key
    //       that.getUserCountForChat(chat)
    //       that.loadedChats.unshift(chat)
    //     })
    //   })
    // },
    // loadRecentChatsByLastKey (lastKey) {
    //   let that = this
    //   that.loading = true
    //   firebase.database().ref('chats').orderByKey().endAt(lastKey).limitToLast(20).once("value", function(snapshot) {
    //     let tempArray = []
    //     snapshot.forEach(function (item) {
    //       if(item.key != lastKey) {
    //         let newChat = item.val()
    //         newChat.key = item.key
    //         tempArray.push(newChat)
    //       }
    //     })
    //     if (tempArray[0].key === tempArray[1].key) return
    //     tempArray.reverse()
    //     tempArray.forEach(function (child) {
    //       that.getUserCountForChat(child)
    //       that.loadedChats.push(child)
    //     })
    //     that.loading = false
    //   })
    // },
    enterChat (chat) {
        AXIOS.post("/addMember", {username: this.$store.getters.getUsername, chatName: chat.name})
        this.$router.push('/chat/' + chat.name)
      }
    }
    // onScroll() {
    //   if(window.top.scrollY + window.innerHeight >= document.body.scrollHeight - 100 && !this.loading) {
    //     this.loadRecentChatsByLastKey(this.loadedChats[this.loadedChats.length - 1].key)
    //   }
    // },
    // getUserCountForChat(chat) {
    //   let that = this
    //   firebase.database().ref('chat_members').child(chat.key).child("users").once("value", function(snapshot) {
    //     that.$set(chat, "userCount", snapshot.numChildren())
    //     snapshot.forEach((user) => {
    //       if(user.key == that.user.id) {
    //         that.$set(chat, "isAlreadyJoined", true)
    //       }
    //     })
    //   })
    // }
  
  // created () {
  //   window.addEventListener('scroll', this.onScroll)
  // },
  // destroyed () {
  //   window.removeEventListener('scroll', this.onScroll)
  // },
  // watch: {
  //   loadedChats: {
  //     deep: true,
  //     handler() {
  //     }
  //   }
  // }
}
</script>