import { createRouter, createWebHistory } from 'vue-router'
import ResumeStepOne from '../components/resume/ResumeStepOne.vue'
import ResumeStepTwo from '../components/resume/ResumeStepTwo.vue'
import ResumeStepThree from '../components/resume/ResumeStepThree.vue'
import ResumeStepFour from '../components/resume/ResumeStepFour.vue'
import ResumeStepFive from '../components/resume/ResumeStepFive.vue'
import LoginView from '../views/user/LoginView.vue'
import SignupView from '../views/user/SignupView.vue'
import SignupCompleteView from '../views/user/SignupCompleteView.vue'
import FindPasswordView from '../views/user/FindPasswordView.vue'
import FindUsernameView from '../views/user/FindUsernameView.vue'

import HomeView from '../views/home/HomeView.vue'

import BoardMain from "../views/board/BoardMain.vue"
import BoardDetail from "../views/board/BoardDetail.vue"
import BoardCreate from "../views/board/BoardCreate.vue"
import BoardUpdate from "../views/board/BoardUpdate.vue"

import QuestionAnswer from "../views/practice/QuestionAnswer.vue"
import QuestionList from "../views/practice/QuestionList.vue"
import VideoPractice from "../views/practice/VideoPractice.vue"
import VideoPracticeList from "../views/practice/VideoPracticeList.vue"

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },

  //user
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupView
  },
  {
    path: '/signup/complete',
    name: 'SignupComplete',
    component: SignupCompleteView
  },
  {
    path: '/find/password',
    name: 'FindPassword',
    component: FindPasswordView
  },
  {
    path: '/find/username',
    name: 'FindUsername',
    component: FindUsernameView
  },


  // resume
  {
    path: '/resume/edit/1',
    component: ResumeStepOne,
    name: 'ResumeStepOne'
  },
  {
    path: '/resume/edit/2',
    component: ResumeStepTwo,
    name: 'ResumeStepTwo' 
  },
  {
    path: '/resume/edit/3',
    component: ResumeStepThree,
    name: 'ResumeStepThree' 
  },
  {
    path: '/resume/edit/4',
    component: ResumeStepFour,
    name: 'ResumeStepFour' 
  },
  {
    path: '/resume/edit/5',
    component: ResumeStepFive,
    name: 'ResumeStepFive' 
  },

  // jobopening
  {
    path: '/jobopening',
    name: 'Jobopening',
    component: () => import('@/views/jobopening/JobopeningView.vue')
  },
  {
    path: '/jobopening/:jobopeningId',
    name: 'JobopeningDetail',
    component: () => import('@/views/jobopening/JobopeningDetailView.vue')
  },

  //boards
  {
    path: '/boards',
    component: BoardMain,
    name: 'board',
  },
  {
    path: '/boards/:board_id',
    component:BoardDetail,
    name: 'boardDetail'
  },
  {
    path: '/boards/create',
    component: BoardCreate,
    name: 'boardCreate'
  },
  {
    path: '/boards/:board_id/update',
    component: BoardUpdate,
    name: 'boardUpdate'
  },

  //video practice
  {
    path: '/practice/questions',
    name:'QuestionList',
    component: QuestionList,
  },
  {
    path: '/practice/question/:question_id/answer',
    component: QuestionAnswer,
    name: 'QuestionAnswer'
  },
  {
    path: '/practice/videopractices',
    component: VideoPracticeList,
    name: 'VideoPracticeList',
  },
  {
    path: '/practice/videopractice',
    component: VideoPractice,
    name: 'VideoPractice'
  }


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
