import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    role: ['admin','editor']     will control the page role (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if fasle ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      component: _import('dashboard/index'),
      name: 'dashboard',
      meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
    }]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/earning',
    component: Layout,
    redirect: '/earning/index',
    meta: {
      title: 'earning',
      icon: 'earning',
      role: ['agent']
    },
    children: [
      { path: 'index', component: _import('agent/earning/index'), name: 'earning', meta: { title: 'earning', icon: 'earning' }}
    ]
  },
  {
    path: '/earning',
    component: Layout,
    hidden: true,
    redirect: '/earning/index',
    meta: {
      title: 'earning',
      icon: 'earning',
      role: ['agent']
    },
    children: [
      {
        path: 'earning_detail',
        component: _import('agent/earning/earning_detail'),
        name: 'earning_detail',
        meta: { title: 'earning_detail' }
      },
      {
        path: 'withDraw_detail',
        component: _import('agent/earning/withDraw_detail'),
        name: 'withDraw_detail',
        meta: { title: 'withDraw_detail' }
      }
    ]
  },
  {
    path: '/consumed',
    component: Layout,
    redirect: '/consumed/index',
    meta: {
      title: 'consumed',
      icon: 'consumed',
      role: ['agent']
    },
    children: [
      { path: 'index', component: _import('agent/consumed/index'), name: 'consumed', meta: { title: 'consumed', icon: 'consumed' }}
    ]
  },
  {
    path: '/consumed',
    component: Layout,
    hidden: true,
    redirect: '/consumed/consumed_detail',
    meta: {
      title: 'consumed_detail',
      icon: 'consumed_detail',
      role: ['agent']
    },
    children: [
      {
        path: 'consumed_detail',
        component: _import('agent/consumed/consumed_detail'),
        name: 'consumed_detail',
        meta: { title: 'consumedDetail' }
      }
    ]
  },
  {
    path: '/userStatistics',
    component: Layout,
    redirect: '/userStatistics/index',
    meta: {
      title: 'userStatistics',
      icon: 'userStatistics',
      role: ['agent']
    },
    children: [
      { path: 'index', component: _import('agent/userStatistics/index'), name: 'userStatistics', meta: { title: 'userStatistics', icon: 'userStatistics' }}
    ]
  },
  {
    path: '/advertiser',
    component: Layout,
    redirect: '/advertiser/index',
    meta: {
      title: 'advertiser',
      icon: 'check002',
      role: ['checker']
    },
    children: [
      { path: 'index', component: _import('checker/advertiser/index'), name: '广告主审核', meta: { title: 'advertiserCheck', icon: 'check002' }}
    ]
  },
  {
    path: '/publicity',
    component: Layout,
    redirect: '/publicity/index',
    meta: {
      title: 'publicity',
      icon: 'check001',
      role: ['checker']
    },
    children: [
      { path: 'index', component: _import('checker/publicity/index'), name: '广告审核', meta: { title: 'publicityCheck', icon: 'check001' }}
    ]
  },
  {
    path: '/agentList',
    component: Layout,
    redirect: '/agentList/index',
    meta: {
      title: 'agentList',
      icon: 'agentList',
      role: ['administrator']
    },
    children: [
      { path: 'index', component: _import('administrator/agentList/index'), name: 'agentList', meta: { title: 'agentList', icon: 'agentList' }}
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]
