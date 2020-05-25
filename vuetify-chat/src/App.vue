<template>
  <v-app>
    <v-navigation-drawer absolute temporary v-model="drawerToggle">
      <v-list>
        <v-list-item>
          <v-list-item-action>
            <v-icon>mdi-account</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            {{this.$store.getters.getUsername}}
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <v-list v-if="this.$store.getters.getUsername !== ''">
        <!-- <v-list-item avatar v-for="user in onlineUsers[1]" v-bind:key="user.user.username">
          <v-list-item-avatar>
            <img src="https://randomuser.me/api/portraits/men/85.jpg" />
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>{{user.user.username}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item> -->
         <v-list-item avatar v-for="(chat, index) in chats" v-bind:key="chat.name" :href="/chat/ + chat.name">
      <v-list-item-content>
        <v-list-item-title v-html="chat.name"></v-list-item-title>
      </v-list-item-content>
      <v-list-item-action>
        <v-icon>mdi-chat</v-icon>
      </v-list-item-action>
    </v-list-item>
        <chats></chats>
        <v-list-item>

        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar app class="light-blue darken-1">
      <v-app-bar-nav-icon @click.native.stop="drawerToggle = !drawerToggle"></v-app-bar-nav-icon>
      <v-toolbar-title>
        <router-link to="/chat/0" tag="span" style="cursor: pointer">Vuetify Chat</router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items >
        <v-btn text v-for="item in menuItems" v-bind:key="item.route"  :to="item.route">
          <v-icon left>{{ item.icon }}</v-icon>
          <div class="hidden-xs-only">{{ item.title }}</div>
        </v-btn>
        <v-btn v-if="this.$store.getters.getUsername !== ''" @click="logout()" text>
          <v-icon left>mdi-logout</v-icon>
          <div class="hidden-xs-only">Logout</div>
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import Chats from "./components/Chat/parts/Chats.vue";
import { AXIOS } from './main';
  export default {
    data () {
      return {
        drawerToggle: false,
        chats: []
      }
    },
    computed: {
      menuItems () {
        let items = [
          { icon: 'mdi-face', title: 'Register', route: '/register' },
          { icon: 'mdi-lock-open', title: 'Login', route: '/login' }
        ]
        if (this.userIsAuthenticated) {
          items = [
            {icon: 'mdi-forum', title: 'Create a Chat', route: '/create'},
            {icon: "mdi-chat", title: 'Chat List', route: '/discover'}
           
          ]
        }
        return items
      },
      userIsAuthenticated () {
        return this.$store.getters.getUsername !== ''
      },
      onlineUsers () {
        return this.$store.getters.onlineUsers
      }
    },
    methods: {
        logout() {
                this.$store.dispatch('logout')
                this.$router.push("/login")
            }
            
    },
    mounted() {
      let self = this
      console.log(4545);
      
      AXIOS.get("/user/chats/" + this.$store.getters.getUsername)
        .then(response => {
          
          self.chats = response.data 
          console.log(self.chats);
        })    
    }
  }
</script>