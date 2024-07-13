<template>
  <div id="app">
    <!-- 右上角的 Logo -->
    <!-- <img src="./assets/logo.png" alt="Logo"
         style="position: fixed; top: 10px; right: 10px; width: 50px; height: auto; z-index: 1000;"> -->

    <!-- 左侧菜单 -->
    <el-menu  :default-active="activeIndex" class="el-menu-vertical-demo custom-menu"
              style="width: 200px; height: 100%; position: fixed; left: 0; top: 0; bottom: 0; background-color: #324157; z-index: 100; display: flex; flex-direction: column; justify-content: space-between; border-right: 1px solid #2a3f54;">
      <div>
        <el-menu-item index="1-1">
          <router-link to="/engineer">工程师管理</router-link>
        </el-menu-item>
        <el-menu-item index="1-2">
          <router-link to="/user">用户管理</router-link>
        </el-menu-item>
        <el-menu-item index="1-3">
          <router-link to="/order">订单管理</router-link>
        </el-menu-item>
        <el-menu-item index="1-4">
          <router-link to="/statistics">数据统计</router-link>
        </el-menu-item>
        <el-menu-item index="1-5">
          <router-link to="/makeOrder">申请维修</router-link>
        </el-menu-item>
        <el-menu-item index="1-6">
          <router-link to="/historyOrder">我的订单</router-link>
        </el-menu-item>
        <el-menu-item index="1-7">
          <router-link to="/account">我的钱包</router-link>
        </el-menu-item>
      </div>

    </el-menu>

    <!-- 主内容区域 -->
    <div style="margin-left: 200px; padding: 20px; margin-top: 60px;">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name:'DashBoard',
  data() {
    return {
      activeIndex: '', // 默认选中的菜单项
    };
  },
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
