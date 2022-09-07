<template>
  <div class="in">
    <el-button type="primary" class="in-button" @click="open">停车</el-button>
    <el-dialog title="新增车辆入库" class="add-dialog" :visible.sync="showinfo" width="35%">
      <div class="input-bar">
        <span>车主姓名:</span>
        <el-input v-model="name" placeholder="请输入车主姓名"></el-input>
      </div>
      <div class="input-bar">
        <span>车主联系电话:</span>
        <el-input v-model="phone" placeholder="请输入车主联系电话"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showinfo = false">取消</el-button>
        <el-button type="primary" @click="addcar(); showinfo = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      phone: "",
      showinfo: false
    };
  },
  methods: {
    open() {
      this.$prompt("请输入车牌号", "停车", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$)/,
        inputErrorMessage: "车牌号格式不正确"
      }).then(({ value }) => {
        this.$store.commit("checkplatein", value);
      });
    },
    addcar() {
      this.$store.commit("addoutcar", { name: this.name, phone: this.phone });
    }
  },
  computed: {
    plateexist() {
      return this.$store.state.plateexist;
    }
  },
  watch: {
    plateexist() {
      this.showinfo = true;
    }
  }
};
</script>

<style scoped>
.in {
  width: 50%;
  text-align: right;
  margin-right: 10%;
  margin-top: 10%;
}
.in-button {
  height: 200px;
  width: 300px;
  font-size: 32px;
  border-radius: 15px;
}
.add-dialog{
  text-align: left;
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