<template>
  <div class="record">
    <div class="add-button">
      <el-input placeholder="输入搜索内容" v-model="search" class="input-with-select">
        <el-select v-model="select" slot="prepend" placeholder="选择类型">
          <el-option label="车牌号" value="1"></el-option>
          <el-option label="车位号" value="3"></el-option>
          <el-option label="停车记录编号" value="2"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="searchrecord"></el-button>
      </el-input>
      <el-button type="primary" plain v-if="search" @click="clearSearch">
        <i class="el-icon-refresh-right"></i>
        重置搜索
      </el-button>
      <el-button type="success" @click="showaddpage">
        <i class="el-icon-circle-plus-outline"></i>
        添加记录
      </el-button>
    </div>
    <div class="content">
      <el-table stripe border :data="recorddata" style="width: 100%;">
        <el-table-column label="车辆信息" type="expand" width="100">
          <template slot-scope="props">
            <el-form label-position="left" inline class="table-expand">
              <el-form-item label="车牌号:">
                <span>{{ props.row.car.plate }}</span>
              </el-form-item>
              <el-form-item label="车位号:" v-if="props.row.car.carport">
                <span>{{ props.row.car.carport }}</span>
              </el-form-item>
              <el-form-item label="车主姓名:">
                <span>{{ props.row.car.ownerName }}</span>
              </el-form-item>
              <el-form-item label="车主联系电话:">
                <span>{{ props.row.car.ownerPhone }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column sortable prop="rid" label="停车记录编号" width="180"></el-table-column>
        <el-table-column sortable prop="car.plate" label="车牌号" width="180"></el-table-column>
        <el-table-column sortable prop="type" label="停车类型" width="120"></el-table-column>
        <el-table-column sortable prop="charge" label="收费金额" width="120"></el-table-column>
        <el-table-column sortable label="停车时间" width="200">
          <template slot-scope="scope">
            <span>{{formatTimestamp(scope.row.inTime)}}</span>
          </template>
        </el-table-column>
        <el-table-column sortable label="出场时间">
          <template slot-scope="scope">
            <span v-if="scope.row.outTime != 0">{{formatTimestamp(scope.row.outTime)}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="修改记录" :visible.sync="showupdaterecord" width="35%">
      <div class="input-bar">
        <span>停车记录编号:</span>
        <el-input v-model="rid" placeholder="请输入停车记录编号"></el-input>
      </div>
      <div class="input-bar">
        <span>车牌号:</span>
        <el-input v-model="plate" placeholder="请输入车牌号" maxlength="7" show-word-limit></el-input>
      </div>
      <div class="input-bar">
        <span>停车类型:</span>
        <el-select v-model="type" slot="prepend" placeholder="请选择停车类型">
          <el-option label="免费" value="1"></el-option>
          <el-option label="临时停车" value="0"></el-option>
        </el-select>
      </div>
      <div class="input-bar">
        <span>停车时间:</span>
        <el-date-picker v-model="intime" type="datetime" placeholder="选择停车时间"></el-date-picker>
      </div>
      <div class="input-bar">
        <span>收费金额:</span>
        <el-input v-model="charge" placeholder="请输入收费金额"></el-input>
      </div>
      <div class="input-bar">
        <span>出场时间:</span>
        <el-date-picker v-model="outtime" type="datetime" placeholder="选择出场时间"></el-date-picker>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showupdaterecord = false">取消</el-button>
        <el-button type="primary" @click="updaterecord">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="新增停车记录" :visible.sync="showaddrecord" width="35%">
      <div class="input-bar">
        <span>停车记录编号:</span>
        <el-input v-model="rid" placeholder="请输入停车记录编号"></el-input>
      </div>
      <div class="input-bar">
        <span>车牌号:</span>
        <el-input v-model="plate" placeholder="请输入车牌号" maxlength="7" show-word-limit></el-input>
      </div>
      <div class="input-bar">
        <span>停车类型:</span>
        <el-select v-model="type" slot="prepend" placeholder="请选择停车类型">
          <el-option label="免费" value="1"></el-option>
          <el-option label="临时停车" value="0"></el-option>
        </el-select>
      </div>
      <div class="input-bar">
        <span>停车时间:</span>
        <el-date-picker v-model="intime" type="datetime" placeholder="选择停车时间"></el-date-picker>
      </div>
      <div class="input-bar">
        <span>出场时间:</span>
        <el-date-picker v-model="outtime" type="datetime" placeholder="选择出场时间"></el-date-picker>
      </div>
      <div class="input-bar">
        <span>收费金额:</span>
        <el-input v-model="charge" placeholder="请输入收费金额"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showaddrecord = false">取消</el-button>
        <el-button type="primary" @click="addrecord">确定</el-button>
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
      showupdaterecord: false,
      showaddrecord: false,
      id: "",
      rid: "",
      plate: "",
      type: null,
      intime: "",
      outtime: "",
      charge: 0
    };
  },
  methods: {
    handleEdit(index, row) {
      this.id = (index, row).id;
      this.rid = (index, row).rid;
      this.plate = (index, row).car.plate;
      this.type = (index, row).type;
      this.charge = parseInt((index, row).charge);
      this.intime = (index, row).inTime * 1000;
      this.outtime = (index, row).outTime * 1000;
      this.showupdaterecord = true;
    },
    updaterecord() {
      if (this.type == "免费") {
        this.type = 1;
      } else if (this.type == "临时停车") {
        this.type = 0;
      }
      this.$store.commit("updaterecordinfo", {
        id: this.id,
        rid: this.rid,
        type: this.type,
        charge: this.charge,
        intime: this.intime,
        outtime: this.outtime,
        plate: this.plate
      });
    },
    showaddpage() {
      this.id = "";
      this.rid = "";
      this.plate = "";
      this.type = null;
      this.charge = 0;
      this.intime = parseInt(new Date().getTime() / 1000) * 1000;
      this.outtime = parseInt(new Date().getTime() / 1000) * 1000;
      this.showaddrecord = true;
    },
    addrecord() {
      this.$store.commit("addrecord", {
        rid: this.rid,
        type: this.type,
        intime: this.intime,
        outtime: this.outtime,
        plate: this.plate,
        charge: this.charge
      });
    },
    searchrecord() {
      if (this.select == 1) {
        if (this.search == "") {
          this.$store.commit("getrecordinfo");
        } else {
          this.$store.commit("searchrecordbyplate", this.search);
        }
      } else if (this.select == 2) {
        if (this.search == "") {
          this.$store.commit("getrecordinfo");
        } else {
          this.$store.commit("searchrecordbyrid", this.search);
        }
      } else if (this.select == 3) {
        if (this.search == "") {
          this.$store.commit("getrecordinfo");
        } else {
          this.$store.commit("searchrecordbycarport", this.search);
        }
      } else {
        Message.warning("请选择搜索方式");
      }
    },
    //格式化时间戳为标准时间格式
    formatTimestamp(timestamp) {
      timestamp *= 1000;
      var dt = new Date(timestamp);
      const y = dt.getFullYear();
      const m = (dt.getMonth() + 1 + "").padStart(2, "0");
      const d = (dt.getDate() + "").padStart(2, "0");
      const hh = (dt.getHours() + "").padStart(2, "0");
      const mm = (dt.getMinutes() + "").padStart(2, "0");
      const ss = (dt.getSeconds() + "").padStart(2, "0");
      return `${y}-${m}-${d} ${hh}:${mm}:${ss}`;
    },
    //重置搜索
    clearSearch() {
      this.search = "";
      this.$store.commit("getrecordinfo");
    }
  },
  computed: {
    recorddata() {
      return this.$store.state.record_info;
    }
  }
};
</script>

<style scoped>
.el-form {
  padding: 0 15px 0 0;
  display: flex;
  flex-direction: column;
}
.el-form-item {
  margin: 0;
}
.el-form-item {
  font-weight: 600 !important;
  color: #99a9bf;
}
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
  padding-top: 1rem;
}

.table-expand {
  padding-left: 15px;
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