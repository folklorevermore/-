<template>
  <div class="index">
    <ve-bar-chart :data="countdata" :title="title" v-if="this.$store.state.coutreadytoshow" />
  </div>
</template>
<script>
export default {
  data() {
    return {
      show: false,
      title: {
        text: "车辆出入数据统计图",
        subtext: "最近7天车辆出入情况"
      }
    };
  },
  created() {
    this.$store.commit("get7daycountdata");
  },
  methods: {},
  computed: {
    countdata() {
      return this.$store.state.sevendaycount;
    }
  }
};
</script>

<style scoped>
</style>