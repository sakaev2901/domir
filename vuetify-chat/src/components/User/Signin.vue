<template>
  <v-container>
    <v-layout row v-if="error">
      <v-flex xs12 sm6 offset-sm3>
        <app-alert @dismissed="onDismissed" :text="error.message"></app-alert>
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <v-card-text>
            <v-container>
              <form @submit.prevent="onSignin">
                <v-layout row>
                  <v-flex xs12>
                    <v-text-field
                      name="username"
                      label="Username"
                      id="email"
                      v-model="username"
                      type="text"
                      required></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout row>
                  <v-flex xs12>
                    <v-text-field
                      name="password"
                      label="Password"
                      id="password"
                      v-model="password"
                      type="password"
                      required></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout>
                  <v-flex xs12>
                    <v-btn type="submit" :loading="loading">Login</v-btn>
                  </v-flex>
                </v-layout>
              </form>
            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { AXIOS } from '../../main'
  export default {
    data () {
      return {
        username: '',
        password: ''
      }
    },
    // computed: {
    //   user () {
    //     return this.$store.getters.user
    //   },
    //   error () {
    //     return this.$store.getters.error
    //   },
    //   loading () {
    //     return this.$store.getters.loading
    //   }
    // },
    // watch: {
    //   user (value) {
    //     if (value !== null && value !== undefined) {
    //       this.$router.push('/chat/0')
    //     }
    //   }
    // },
    methods: {
      onSignin () {
        // this.$store.dispatch('signUserIn', {email: this.email, password: this.password})
        AXIOS.post("/signIn", {username: this.username, password: this.password})
          .then(response => {
            this.$store.state.username = response.data.username
            this.$store.state.token = response.data.token
            console.log(this.$store.state.username);
            console.log(this.$store.state.token);
            this.$store.dispatch('login', {
                                token: response.data.token,
                                username: response.data.username,
                                role: response.data.role,
                                name: response.data.name,
                                surname: response.data.surname,
                                avatar: response.data.avatar
                            })
            
            
          })
          .catch(err => {
            console.log(err);
            
          })
      },
      onDismissed () {
        this.$store.dispatch('clearError')
      }
    }
  }
</script>
