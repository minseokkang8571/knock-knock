import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import ArticleList from '@/views/articles/ArticleList'
import ArticleDetail from '@/views/articles/ArticleDetail'
import ArticleCreate from '@/views/articles/ArticleCreate'
import Signin from '@/views/accounts/Signin'
import Signup from '@/views/accounts/Signup'
import ReviewList from '@/views/reviews/ReviewList'
import ReviewDetail from '@/views/reviews/ReviewDetail'

Vue.use(VueRouter)

const onlyAuthUser = (to, from, next) => {
  if (store.state.isLogin === true || localStorage.getItem('token') !== null) {
    next()
  } else {
    alert('로그인을 해야합니다.')
    next('/signin')
  }
}

const routes = [
  {
    path: '/',
    name: 'ArticleList',
    component: ArticleList
  },
  {
    path: '/articles',
    name: 'ArticleDetail',
    component: ArticleDetail,
    props: true
  },
  {
    path: '/articles',
    name: 'ArticleCreate',
    component: ArticleCreate,
    beforeEnter: onlyAuthUser,
    props: true
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/review',
    name: 'ReviewList',
    component: ReviewList
  },
  {
    path: '/reviews',
    name: 'ReviewDetail',
    component: ReviewDetail
  }
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
