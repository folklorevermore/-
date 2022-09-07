<template>
  <div class="manager">
    <div v-if="showusername" class="manager-header">
      <div class="tit">
        <img src="../assets/logo.png" />
        <span>智能停车场系统</span>
      </div>
      <div class="logout">
        <el-button
          type="danger"
          icon="el-icon-switch-button"
          @click="logout"
          class="logout-btn"
          plain
        >退出登录</el-button>
      </div>
      <div class="user-bar">
        <h5>
          {{greetTip}}，
          <span>{{username}}</span>
        </h5>
      </div>
    </div>
    <div class="manager-button">
      <In />
      <Out />
    </div>
  </div>
</template>

<script>
import In from "../components/manager/In";
import Out from "../components/manager/Out.vue";

export default {
  components: {
    In,
    Out
  },
  data() {
    return {
      showusername: false
    };
  },
  methods: {
    logout() {
      this.$store.commit("logout");
    },
    //根据时间段问好
    getGreetTip() {
      const that = this;
      const date = new Date();
      if (date.getHours() >= 0 && date.getHours() < 12) {
        that.greetTip = "上午好";
      } else if (date.getHours() >= 12 && date.getHours() < 18) {
        that.greetTip = "下午好";
      } else {
        that.greetTip = "晚上好";
      }
    }
  },
  created() {
    this.getGreetTip();
  },
  mounted() {
    setTimeout((this.showusername = true), 1000);
  },
  computed: {
    username() {
      return sessionStorage.getItem("username");
    }
  }
};
</script>

<style scoped>
.manager {
  height: 100%;
  background-image: url("../assets/manager-background.jpg");
  background-size: 100% 100%;
}

.manager-header {
  height: 80px;
  line-height: 80px;
  padding: 0 20px;
}
.tit {
  height: 80px;
  line-height: 80px;
  background-color: transparent;
  cursor: pointer;
  float: left;
}

.tit img {
  height: 22px;
  vertical-align: -5px;
}
.tit span {
  font-weight: bold;
  font-size: 16px;
  padding-left: 7px;
  color: #fff;
  letter-spacing: 1px;
}

.user-bar {
  float: right;
}
.logout {
  height: 80px;
  float: right;
}
.logout-btn {
  height: 40px;
}
.user-bar h5 {
  font-size: 16px;
  margin: 0;
  font-weight: normal;
  color: #ffffff;
  font-weight: bold;
  padding-right: 15px;
}
.user-bar h5 span {
  color: #409eff;
}

.manager-button {
  display: flex;
  justify-content: space-around;
  height: calc(100% - 80px);
  /* padding-top: 10%; */
}
</style>
