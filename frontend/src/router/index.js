import Vue from 'vue'
import VueRouter from 'vue-router'
import ArticleList from '@/views/articles/ArticleList'
import ArticleDetail from '@/views/articles/ArticleDetail'
import ArticleCreate from '@/views/articles/ArticleCreate'
import Signin from '@/views/accounts/Signin'
import Signup from '@/views/accounts/Signup'
import ReviewList from '@/views/reviews/ReviewList'
import ReviewDetail from '@/views/reviews/ReviewDetail'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'ArticleList',
    component: ArticleList
  },
  {
    path: '/articles/:articleId',
    name: 'ArticleDetail',
    component: ArticleDetail
  },
  {
    path: '/articles/',
    name: 'ArticleCreate',
    component: ArticleCreate
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
    path: '/reviews/:reviewId',
    name: 'ReviewDetail',
    component: ReviewDetail
  }
]

const router = new VueRouter({
  routes
})

export default router
