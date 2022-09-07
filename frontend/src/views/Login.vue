<template>
  <div class="login">
    <div class="login-form">
      <h1 class="login-tit">智能停车场系统</h1>
      <el-input class="input-bar" placeholder="账号 / 用户名" v-model="account" clearable prefix-icon="el-icon-user"></el-input>
      <el-input class="input-bar" placeholder="密码" v-model="password" show-password prefix-icon="el-icon-lock"></el-input>
      <el-button id="login-btn" type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import { Message } from "element-ui";
export default {
  data() {
    return {
      account: "",
      password: ""
    };
  },
  created() {
    Message.warning({
      message: "请登录"
    });
  },
  methods: {
    login() {
      this.$store.commit("checkuserpassword", {
        uid: this.account,
        password: this.password
      });
    }
  }
};
</script>


<style scoped>
* {
  box-sizing: border-box;
}
.login {
  padding: 0 15px;
  height: 100%;
  background-image: url('../assets/background.png');
  background-size:100% 100%;
}

.login-form {
  width: 400px;
  padding: 40px 30px;
  /* background-color: #40a0ff25; */
  background-color: #fff;
  border-radius: 6px;
  position: absolute;
  right: 15%;
  top: calc(50% - 210px);
}
.login-tit {
  font-size: 28px;
  color: #409eff;
  margin: 0;
  margin-bottom: 20px;
  text-align: center;
}
.input-bar {
  height: 80px;
  line-height: 80px;
}
#login-btn {
  margin-top: 20px;
  width: 100%;
  letter-spacing: 5px;
}
</style>
