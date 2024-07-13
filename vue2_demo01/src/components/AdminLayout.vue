<template>
  <div id="app">
    <!-- 左侧菜单 -->
    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo custom-menu"
             style="width: 200px; height: 100%; position: fixed; left: 0; top: 0; bottom: 0; background-color: #283238; z-index: 100; display: flex; flex-direction: column; justify-content: space-between; border-right: 1px solid #37474f;">
      <div>
        <el-menu-item index="1-1">
          <router-link :to="{ name: 'adminLayout-engineer' }">工程师管理</router-link>
        </el-menu-item>
        <el-menu-item index="1-2">
          <router-link :to="{ name: 'adminLayout-user' }">用户管理</router-link>
        </el-menu-item>
        <el-menu-item index="1-3">
          <router-link :to="{ name: 'adminLayout-order' }">订单管理</router-link>
        </el-menu-item>
        <el-menu-item index="1-4">
          <router-link :to="{ name: 'adminLayout-statistics' }">数据统计</router-link>
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
  background-color: #283238; /* 左侧菜单背景色 */
}

.custom-menu .el-menu-item {
  font-size: 16px;
  padding: 12px 20px;
  color: #ffffff; /* 菜单项文本颜色 */
  transition: background-color 0.3s;
}

.custom-menu .el-menu-item:hover {
  background-color: #37474f; /* 菜单项hover背景色 */
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
