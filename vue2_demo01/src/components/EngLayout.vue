<template>
  <div id="app">
    <!-- 左侧菜单 -->
    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo custom-menu"
             style="width: 200px; height: 100%; position: fixed; left: 0; top: 0; bottom: 0; background-color: #324157; z-index: 100; display: flex; flex-direction: column; justify-content: space-between; border-right: 1px solid #2a3f54;">
      <div>
        <el-menu-item index="1-3">
          <router-link :to="{ name: 'engLayout-order' }">订单广场</router-link>
        </el-menu-item>
        <el-menu-item index="1-6">
          <router-link :to="{ name: 'engLayout-historyOrder' }">我的订单</router-link>
        </el-menu-item>
        <el-menu-item index="1-7">
          <router-link :to="{ name: 'engLayout-account' }">个人中心</router-link>
        </el-menu-item>
      </div>

      <div class="footer">
        天津工业大学软件学院8组
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
  name: 'DashBoard',
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
  background-color: #324157; /* 左侧菜单背景色 */
}

.custom-menu .el-menu-item {
  font-size: 16px;
  padding: 12px 20px;
  color: #ffffff; /* 菜单项文本颜色 */
  transition: background-color 0.3s;
}

.custom-menu .el-menu-item:hover {
  background-color: #2a3f54; /* 菜单项hover背景色 */
}

.custom-menu .el-menu-item a {
  text-decoration: none;
  color: inherit;
  display: block;
}

.custom-menu .el-menu-item a:hover {
  color: #409EFF; /* 菜单项链接hover颜色 */
}

.main-content {
  margin-left: 200px; /* 主内容区域与左侧菜单的间距 */
  padding: 20px;
  margin-top: 60px; /* 主内容区域与顶部的间距 */
}
</style>
