<template>
  <div class="admin">
    <el-container>
      <el-aside width="200px">
        <el-row class="tac">
          <el-col :span="24">
            <div class="tit">
              <img src="../assets/logo.png" />
              <span>智能停车场系统</span>
            </div>
            <el-menu
              :default-active="menu_num"
              class="el-menu-vertical"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#409eff"
            >
              <el-menu-item index="0" @click="changeCurrentTab(0)">
                <i class="el-icon-s-home"></i>
                <span slot="title">首页&ensp;&ensp;&ensp;&ensp;</span>
              </el-menu-item>
              <el-menu-item index="1" @click="changeCurrentTab(1)">
                <i class="el-icon-user"></i>
                <span slot="title">用户管理</span>
              </el-menu-item>
              <el-menu-item index="2" @click="changeCurrentTab(2)">
                <i class="el-icon-truck"></i>
                <span slot="title">车辆管理</span>
              </el-menu-item>
              <el-menu-item index="3" @click="changeCurrentTab(3)">
                <i class="el-icon-document"></i>
                <span slot="title">记录管理</span>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </el-aside>
      <el-container>
        <el-header>
          <div v-if="showusername" class="admin-header">
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
            <div class="breadcrumb">
              <el-breadcrumb class="breadcrumb-main" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{currentBreadcrumb}}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
          </div>
        </el-header>
        <el-main>
          <index v-show="currentTab==0" />
          <User v-show="currentTab==1" />
          <Car v-show="currentTab==2" />
          <Record v-show="currentTab==3" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import index from "../components/admin/index";
import User from "../components/admin/User.vue";
import Car from "../components/admin/Car.vue";
import Record from "../components/admin/Record.vue";

export default {
  components: {
    index,
    User,
    Car,
    Record
  },
  data() {
    return {
      currentTab: sessionStorage.getItem("menunumber"),
      currentBreadcrumb: sessionStorage.getItem("currentBreadcrumb"),
      showusername: false,
      greetTip: "你好"
    };
  },
  created() {
    this.$store.commit("getuserinfo");
    this.$store.commit("getcarinfo");
    this.$store.commit("getrecordinfo");
    this.$store.commit("get7daycountdata");
    this.getGreetTip();
  },
  methods: {
    logout() {
      this.$store.commit("logout");
    },
    changeCurrentTab(tab) {
      this.currentTab = tab;
      sessionStorage.setItem("menunumber", tab);
      switch (tab) {
        case 0:
          this.currentBreadcrumb = "";
          sessionStorage.setItem("currentBreadcrumb", "");
          break;
        case 1:
          this.currentBreadcrumb = "用户管理";
          sessionStorage.setItem("currentBreadcrumb", "用户管理");
          break;
        case 2:
          this.currentBreadcrumb = "车辆管理";
          sessionStorage.setItem("currentBreadcrumb", "车辆管理");
          break;
        case 3:
          this.currentBreadcrumb = "记录管理";
          sessionStorage.setItem("currentBreadcrumb", "记录管理");
          break;
      }
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
  mounted() {
    setTimeout((this.showusername = true), 1000);
  },
  computed: {
    username() {
      return sessionStorage.getItem("username");
    },
    menu_num() {
      return sessionStorage.getItem("menunumber");
    }
  }
};
</script>

<style scoped>
.tac {
  position: fixed;
  width: 200px;
}
.el-header {
  height: 80px !important;
}
.el-main {
  padding: 20px;
  padding-top: 10px;
}
.el-menu-vertical {
  height: 100%;
}
.tit {
  height: 80px;
  line-height: 80px;
  background-color: #545c64;
  width: 199px;
  cursor: pointer;
  border-right: 1px solid #e6e6e6;
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
.el-menu {
  height: 1000px;
}

.admin-header {
  height: 80px;
  line-height: 80px;
  width: calc(100% - 240px);
  position: fixed;
  background-color: #fff;
  z-index: 999;
}
.user-bar {
  float: right;
}
.logout {
  height: 80px;
  float: right;
}
.logout-btn{
  height: 40px;
}
.user-bar h5 {
  font-size: 16px;
  margin: 0;
  font-weight: normal;
  color: #606266;
  font-weight: bold;
  padding-right: 15px;
}
.user-bar h5 span {
  color: #409eff;
}

.breadcrumb {
  float: left;
  height: 80px;
  line-height: 80px;
}
.breadcrumb-main {
  height: 80px;
  line-height: 80px;
}
</style>
