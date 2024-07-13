<template>
  <div id="app">
    <!-- <LoginView/> -->
     <!-- <DashBoard/> -->
      <!-- <OrderView/> -->
      <router-view></router-view>

  </div>
</template>

<script>
import LoginView from "./views/LoginView.vue";
import DashBoard from "./components/DashBoard.vue";
import OrderView from './views/OrderView.vue';
export default {
  data() {
    return {
      activeIndex: '', // 默认选中的菜单项
    };
  },
  components:{
    // eslint-disable-next-line vue/no-unused-components
    LoginView,
    // eslint-disable-next-line vue/no-unused-components
    DashBoard,
    // eslint-disable-next-line vue/no-unused-components
    OrderView
  }
};
</script>

<style scoped>
#app {
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}

.el-menu-vertical-demo {
  background-color: #324157;
}

.custom-menu .el-menu-item {
  font-size: 16px;
  padding: 12px 20px;
  color: #ffffff;
  transition: background-color 0.3s;
}

.custom-menu .el-menu-item:hover {
  background-color: #2a3f54;
}

.custom-menu .el-menu-item a {
  text-decoration: none;
  color: inherit;
  display: block;
}

.custom-menu .el-menu-item a:hover {
  color: #409EFF;
}

.custom-menu .footer {
  padding: 10px;
  font-size: 12px;
  text-align: center;
  color: #999999;
  border-top: 1px solid #2a3f54;
}
</style>
