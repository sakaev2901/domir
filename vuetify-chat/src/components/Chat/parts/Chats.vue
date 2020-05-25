<template>
  <v-list subheader>
    <v-subheader>Your Chats</v-subheader>
    <v-list-item avatar v-for="(chat, index) in chats" v-bind:key="chat.name" :href="/chat/ + chat.name">
      <v-list-item-content>
        <v-list-item-title v-html="chat.name"></v-list-item-title>
      </v-list-item-content>
      <v-list-item-action>
        <v-icon>mdi-chat</v-icon>
      </v-list-item-action>
    </v-list-item>
  </v-list>
</template>

<script>
import { AXIOS } from '../../../main'
  export default{
    data: () => ({
      chats: []
    }),
    // created () {
    //   this.$store.dispatch('loadUserChats')
    // },
    // computed: {
    //   chats () {
    //     return this.$store.getters.chats
    //   }
    // }
    mounted() {
      let self = this
      AXIOS.get("/user/chats/" + this.$store.getters.getUsername)
        .then(response => {
          self.chats = response.data 
        })    
    }
  }
</script>
