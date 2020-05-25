import {store} from '../store/index'

export default (to, from, next) => {
  console.log(store.getters.getUsername);
  
  if (store.getters.getUsername) {
    next()
  } else {
    next('/login')
  }
}
