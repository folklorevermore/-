<template>
  <div class="car">
    <div class="add-button">
      <el-input placeholder="输入搜索内容" v-model="search" class="input-with-select">
        <el-select v-model="select" slot="prepend" placeholder="选择类型">
          <el-option label="车牌号" value="1"></el-option>
          <el-option label="车位号" value="2"></el-option>
          <el-option label="车主姓名" value="3"></el-option>
          <el-option label="车主电话" value="4"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="searchcar"></el-button>
      </el-input>
      <el-button type="primary" plain v-if="search" @click="clearSearch">
        <i class="el-icon-refresh-right"></i>
        重置搜索
      </el-button>
      <el-button type="success" @click="showaddpage">
        <i class="el-icon-circle-plus-outline"></i>
        添加车辆
      </el-button>
    </div>
    <div class="content">
      <el-table stripe border :data="cardata" style="width: 100%">
        <el-table-column sortable prop="plate" label="车牌号" width="180"></el-table-column>
        <el-table-column sortable prop="carport" label="车位号" width="180"></el-table-column>
        <el-table-column sortable prop="ownerName" label="车主姓名" width="180"></el-table-column>
        <el-table-column sortable prop="ownerPhone" label="车主联系电话"></el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="修改车辆" :visible.sync="showupdatecar" width="35%">
      <div class="input-bar">
        <span>车牌号:</span>
        <el-input v-model="carplate" placeholder="请输入车牌号"></el-input>
      </div>
      <div class="input-bar">
        <span>车位号:</span>
        <el-input v-model="port" placeholder="请输入车位号" maxlength="16" show-word-limit></el-input>
      </div>
      <div class="input-bar">
        <span>车主姓名:</span>
        <el-input v-model="ownername" placeholder="请输入车主姓名"></el-input>
      </div>
      <div class="input-bar">
        <span>车主联系电话:</span>
        <el-input v-model="ownerphone" placeholder="请输入车主联系电话"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showupdatecar = false">取 消</el-button>
        <el-button type="primary" @click="updatecar">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="新增车辆入库" :visible.sync="showaddcar" width="35%">
      <div class="input-bar">
        <span>车牌号:</span>
        <el-input v-model="carplate" placeholder="请输入车牌号"></el-input>
      </div>
      <div class="input-bar">
        <span>车位号:</span>
        <el-input v-model="port" placeholder="请输入车位号" maxlength="16" show-word-limit></el-input>
      </div>
      <div class="input-bar">
        <span>车主姓名:</span>
        <el-input v-model="ownername" placeholder="请输入车主姓名"></el-input>
      </div>
      <div class="input-bar">
        <span>车主联系电话:</span>
        <el-input v-model="ownerphone" placeholder="请输入车主联系电话"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showaddcar = false">取消</el-button>
        <el-button type="primary" @click="addcar">确定</el-button>
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
      showupdatecar: false,
      showaddcar: false,
      carid: "",
      carplate: "",
      port: "",
      cartype: "",
      ownername: "",
      ownerphone: ""
    };
  },
  methods: {
    handleEdit(index, row) {
      this.carid = (index, row).id;
      this.carplate = (index, row).plate;
      this.port = (index, row).carport;
      this.cartype = (index, row).type;
      this.ownername = (index, row).ownerName;
      this.ownerphone = (index, row).ownerPhone;
      this.showupdatecar = true;
    },
    updatecar() {
      if (this.port == "") {
        this.cartype = 0;
      } else if (this.port != "") {
        this.cartype = 1;
      }
      if (
        !/(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$)/.test(
          this.carplate
        )
      ) {
        Message.error("车牌号格式错误");
      } else {
        this.$store.commit("updatecarinfo", {
          id: this.carid,
          plate: this.carplate,
          carport: this.port,
          type: this.cartype,
          name: this.ownername,
          phone: this.ownerphone
        });
        this.showupdateuser = false;
      }
    },
    showaddpage() {
      this.carplate = "";
      this.port = "";
      this.cartype = null;
      this.ownername = "";
      this.ownerphone = "";
      this.showaddcar = true;
    },
    addcar() {
      if (this.port == "") {
        this.cartype = 0;
      } else if (this.port != "") {
        this.cartype = 1;
      }
      if (
        !/(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$)/.test(
          this.carplate
        )
      ) {
        Message.error("车牌号格式错误");
      } else {
        this.$store.commit("addcar", {
          plate: this.carplate,
          carport: this.port,
          type: this.cartype,
          name: this.ownername,
          phone: this.ownerphone
        });
        this.showaddcar = false;
      }
    },
    searchcar() {
      if (this.select == 1) {
        if (this.search == "") {
          this.$store.commit("getcarinfo");
        } else {
          this.$store.commit("searchbyplate", this.search);
        }
      } else if (this.select == 2) {
        if (this.search == "") {
          this.$store.commit("getcarinfo");
        } else {
          this.$store.commit("searchbycarport", this.search);
        }
      } else if (this.select == 3) {
        if (this.search == "") {
          this.$store.commit("getcarinfo");
        } else {
          this.$store.commit("searchbyownername", this.search);
        }
      } else if (this.select == 4) {
        if (this.search == "") {
          this.$store.commit("getcarinfo");
        } else {
          this.$store.commit("searchbyownerphone", this.search);
        }
      } else {
        Message.warning("请选择搜索方式");
      }
    },
    //重置搜索
    clearSearch(){
      this.search = '';
      this.$store.commit("getcarinfo");
    }
  },
  computed: {
    cardata() {
      return this.$store.state.car_info;
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