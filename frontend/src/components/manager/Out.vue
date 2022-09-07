<template>
  <div class="out">
      <el-button type="primary" class="out-button" @click="open">出场</el-button>
  </div>
</template>

<script>
export default {
  methods:{
      open() {
        this.$prompt('请输入车牌号', '出场', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$)/,
          inputErrorMessage: '车牌号格式不正确'
        }).then(({ value }) => {
          this.$store.commit('outcar',value);
        })
      }
  }
}
</script>

<style scoped>
.out{
  width: 50%;
  text-align: left;
  margin-left: 10%;
  margin-top: 10%;
}
.out-button{
  height: 200px;
  width: 300px;
  font-size: 32px;
  border-radius: 15px;
}
</style>