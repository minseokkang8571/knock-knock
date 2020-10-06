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

const rejectAuthUser = (to, from, next) => {
  // 로그인 상태에서 사용자가 직접 signin, signup url을 주소창에 쳐서 들어온 경우
  // ArticleList로 리다이렉트
  // 주소창에 직접 검색시 새로고침이 되며 store에 유저정보가 사라져, 임시로 비동기 처리를 타임아웃으로 처리
  setTimeout(function() {
    if (store.state.isLogin === true) {
      alert('로그인 상태입니다.')
      next({ name: 'ArticleList' })
    } else {
      next()
    }
  }, 300)
}

const onlyAuthUser = (to, from, next) => {
  // 로그인이 필요한 페이지의 경우, 로그인 페이지로 리다이렉트
  setTimeout(function() {
    if (store.state.isLogin === true) {
      next()
    } else {
      alert('로그인을 해야합니다.')
      next({ name: 'Signin' })
    }
  }, 300)
}

const routes = [
  {
    path: '/',
    name: 'ArticleList',
    component: ArticleList
  },
  {
    path: '/search',
    name: 'ArticleSearchList',
    component: ArticleList
  },
  {
    path: '/articles',
    name: 'ArticleDetail',
    component: ArticleDetail
  },
  {
    path: '/article',
    name: 'ArticleCreate',
    component: ArticleCreate,
    beforeEnter: onlyAuthUser,
    props: true
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin,
    beforeEnter: rejectAuthUser
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
    beforeEnter: rejectAuthUser
  },
  {
    path: '/review',
    name: 'ReviewList',
    component: ReviewList
  },
  {
    path: '/reviews',
    name: 'ReviewDetail',
    component: ReviewDetail,
    beforeEnter: onlyAuthUser
  }
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
