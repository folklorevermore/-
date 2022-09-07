<template>
  <div id="app">
    <div id="nav">
      <Login v-if="usertype==null"/>
      <Admin v-if="usertype==1"/>
      <Manager v-if="usertype==0"/>
    </div>
    <router-view/>
  </div>
</template>

<script>
import Login from './views/Login.vue'
import Admin from './views/Admin.vue'
import Manager from './views/Manager.vue'

export default {
  components:{
    Login,
    Admin,
    Manager,
  },
  computed:{
    loginstatus(){
      return this.$store.state.isloged;
    },
    usertype(){
      return sessionStorage.getItem('isloged');
    },
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  cursor: default;
  box-sizing: border-box;
}

html,
body,
#app,
#nav {
  height: 100%;
  width: 100%;
  padding: 0;
  margin: 0;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
