<template>
  <div class="user">
    <div class="add-button">
      <el-input placeholder="输入搜索内容" v-model="search" class="input-with-select">
        <el-select v-model="select" slot="prepend" placeholder="选择类型">
          <el-option label="用户账号" value="1"></el-option>
          <el-option label="用户名" value="2"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="searchuser"></el-button>
      </el-input>
      <el-button type="primary" plain v-if="search" @click="clearSearch">
        <i class="el-icon-refresh-right"></i>
        重置搜索
      </el-button>
      <el-button type="success" @click="showaddpage">
        <i class="el-icon-circle-plus-outline"></i>
        新增停车管理员
      </el-button>
    </div>
    <div class="content">
      <el-table stripe border :data="userdata" style="width: 100%">
        <el-table-column sortable prop="uid" label="用户账号" width="180"></el-table-column>
        <el-table-column sortable prop="userName" label="用户姓名" width="250"></el-table-column>
        <el-table-column sortable prop="role" label="用户角色"></el-table-column>
        <!-- <el-table-column sortable prop="password" label="用户密码"></el-table-column> -->
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="修改用户" :visible.sync="showupdateuser" width="35%">
      <div class="input-bar">
        <span>用户名:</span>
        <el-input v-model="username" placeholder="请输入用户名"></el-input>
      </div>
      <div class="input-bar">
        <span>用户账号:</span>
        <el-input v-model="useraccount" placeholder="请输入账号" maxlength="6" show-word-limit></el-input>
      </div>
      <div class="input-bar">
        <span>用户密码:</span>
        <el-input v-model="userpassword" placeholder="请重设密码"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showupdateuser = false">取消</el-button>
        <el-button type="primary" @click="updateuser">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="新增用户" :visible.sync="showadduser" width="35%">
      <div class="input-bar">
        <span>用户名:</span>
        <el-input v-model="username" placeholder="请输入用户名"></el-input>
      </div>
      <div class="input-bar">
        <span>用户账号:</span>
        <el-input v-model="useraccount" placeholder="请输入账号" maxlength="6" show-word-limit></el-input>
      </div>
      <div class="input-bar">
        <span>用户密码:</span>
        <el-input v-model="userpassword" placeholder="请设置密码"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showadduser = false">取 消</el-button>
        <el-button type="primary" @click="adduser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from "element-ui";

export default {
  data() {
    return {
      search: "",
      select: "",
      showupdateuser: false,
      showadduser: false,
      userid: "",
      username: "",
      useraccount: "",
      userpassword: ""
    };
  },
  methods: {
    handleEdit(index, row) {
      this.userid = (index, row).id;
      this.username = (index, row).userName;
      this.useraccount = (index, row).uid;
      this.userpassword = (index, row).password;
      this.showupdateuser = true;
    },
    updateuser() {
      if (!/^\d+$/.test(this.useraccount)) {
        Message.error("用户账号应为6位以内数字");
      } else {
        this.$store.commit("updateuserinfo", {
          uid: this.useraccount,
          username: this.username,
          password: this.userpassword,
          id: this.userid
        });
        this.showupdateuser = false;
      }
    },
    showaddpage() {
      this.username = "";
      this.useraccount = "";
      this.userpassword = "";
      this.showadduser = true;
    },
    adduser() {
      if (!/^\d+$/.test(this.useraccount)) {
        Message.error("用户账号应为6位以内数字");
      } else {
        this.$store.commit("adduser", {
          uid: this.useraccount,
          username: this.username,
          password: this.userpassword
        });
        this.showadduser = false;
      }
    },
    searchuser() {
      if (this.select == 1) {
        if (this.search == "") {
          this.$store.commit("getuserinfo");
        } else {
          this.$store.commit("searchuserbyaccount", this.search);
        }
      } else if (this.select == 2) {
        if (this.search == "") {
          this.$store.commit("getuserinfo");
        } else {
          this.$store.commit("searchuserbyname", this.search);
        }
      } else {
        Message.warning("请选择搜索方式");
      }
    },
    //重置搜索
    clearSearch(){
      this.search = '';
      this.$store.commit("getuserinfo");
    }
  },
  computed: {
    userdata() {
      return this.$store.state.user_info;
    }
  }
};
</script>

<style scoped>
.input-with-select {
  padding: 0 15px 0 0;
}
.el-select {
  width: 8rem;
}
.add-button {
  height: 40px;
  display: flex;
  justify-content: flex-end;
}
.content {
  padding-top: 15px;
}
.input-bar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  padding: 8px 0;
}
.input-bar span {
  height: 32px;
  line-height: 32px;
  font-weight: bold;
  padding-right: 12px;
}
</style>