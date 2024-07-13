<template>
  <div id="app">
    <!-- 左侧菜单 -->
    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo custom-menu"
             style="width: 200px; height: 100%; position: fixed; left: 0; top: 0; bottom: 0; background-color: #2a3f54; z-index: 100; display: flex; flex-direction: column; justify-content: space-between; border-right: 1px solid #1c2b3a;">
      <div>
        <el-menu-item index="1-5">
          <router-link :to="{ name: 'userLayout-makeOrder' }">申请维修</router-link>
        </el-menu-item>
        <el-menu-item index="1-6">
          <router-link :to="{ name: 'userLayout-historyOrder' }">我的订单</router-link>
        </el-menu-item>
        <el-menu-item index="1-7">
          <router-link :to="{ name: 'userLayout-account' }">个人中心</router-link>
        </el-menu-item>
      </div>
      <div class="footer">
        天津工业大学软件学院8组
      </div>
    </el-menu>

    <!-- 主内容区域 -->
    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserLayout',
  data() {
    return {
      activeIndex: '' // 默认选中的菜单项
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
  background-color: #2a3f54;
}

.custom-menu .el-menu-item {
  font-size: 16px;
  padding: 12px 20px;
  color: #ffffff;
  transition: background-color 0.3s;
}

.custom-menu .el-menu-item:hover {
  background-color: #1c2b3a;
}

.custom-menu .el-menu-item a {
  text-decoration: none;
  color: inherit;
  display: block;
}

.custom-menu .el-menu-item a:hover {
  color: #409EFF;
}

.main-content {
  margin-left: 200px;
  padding: 20px;
  margin-top: 60px;
}

.footer {
  padding: 10px;
  font-size: 12px;
  text-align: center;
  color: #999999;
  border-top: 1px solid #1c2b3a;
}
</style>
