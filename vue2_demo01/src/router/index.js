import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/adminLayout',
    name:'adminLayout',
    component: () => import('../components/AdminLayout.vue'),
    children: [
      {
        path: '/',
        name: '/',
        component: () => import('../views/admin/EngineerView.vue')
      },
      {
        path: 'engineer',
        name: 'adminLayout-engineer',
        component: () => import('../views/admin/EngineerView.vue')
      },
      {
        path: 'order',
        name: 'adminLayout-order',
        component: () => import('../views/admin/OrderView.vue')
      },
      {
        path: 'statistics',
        name: 'adminLayout-statistics',
        component: () => import('../views/admin/StatisticsView.vue')
      },

      {
        path: 'user',
        name: 'adminLayout-user',
        component: () => import('../views/admin/UserView.vue')
      }
    ]
  },
  {
    path: '/userLayout',
    name:'userLayout',
    component: () => import('../components/UserLayout.vue'),
    children: [
      {
        path: '/',
        name: '/',
        component: () => import('../views/user/MakeOrderView.vue')
      },
      {
        path: 'makeOrder',
        name: 'userLayout-makeOrder',
        component: () => import('../views/user/MakeOrderView.vue')
      },
      {
        path: 'historyOrder',
        name: 'userLayout-historyOrder',
        component: () => import('../views/user/HistoryOrderView.vue')
      },
      {
        path: 'account',
        name: 'userLayout-account',
        component: () => import('../views/user/PersonalView.vue')
      },
    ]
  },
  {
    path: '/engLayout',
    name: 'engLayout',
    component: () => import('../components/EngLayout.vue'),
    children: [
      {
        path: '/',
        name: '/',
        component: () => import('../views/eng/OrderView.vue')
      },
      {
        path: 'order',
        name: 'engLayout-order',
        component: () => import('../views/eng/OrderView.vue')
      },
      {
        path: 'historyOrder',
        name: 'engLayout-historyOrder', // 使用父级路由名称作为前缀
        component: () => import('../views/eng/HistoryOrderView.vue')
      },
      {
        path: 'account',
        name: 'engLayout-account', // 使用父级路由名称作为前缀
        component: () => import('../views/eng/PersonalView.vue')
      },
    ]
  }
];

const router = new VueRouter({
  routes
});

export default router;
