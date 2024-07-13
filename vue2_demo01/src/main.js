import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI  from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
Vue.config.productionTip = false
Vue.use(ElementUI)
const axiosInstance = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL || 'http://localhost:8080/'
});

// 将 axios 实例添加到 Vue 原型链中
Vue.prototype.$http = axiosInstance;

// 添加请求拦截器
axiosInstance.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么，比如设置请求头
  const token = sessionStorage.getItem('token'); // 假设 token 存储在 localStorage 中

  if (token) {
    config.headers['Authorization'] = token;
  }

  return config;
}, function (error) {
  // 处理请求错误
  return Promise.reject(error);
});
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')


