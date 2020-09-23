import Vue from 'vue'
import VueRouter from 'vue-router'
import ArticleList from '@/views/articles/ArticleList.vue'
import ArticleDetail from '@/views/articles/ArticleDetail.vue'
import Login from '@/views/accounts/Login.vue'
import Signup from '@/views/accounts/Signup.vue'
import ReviewList from '@/views/reviews/ReviewList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'ArticleList',
    component: ArticleList
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
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
    path: '/articles/:articleId',
    name: 'ArticleDetail',
    component: ArticleDetail
  }
]

const router = new VueRouter({
  routes
})

export default router
