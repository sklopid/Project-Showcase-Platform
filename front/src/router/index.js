import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/',redirect: '/login'},
    {path: '/manager',component:()=> import('../views/Manager.vue'),
      children:[
      {path: '',name: 'manager',component:()=> import('../views/Home.vue'),}, 
      {path:'home',meta:{name:'main'}, name:'home',component:()=> import('../views/Home.vue'),},
      {path:'admin',meta:{name:'admin message'},name:'admin',component:()=> import('../views/Admin.vue'),},
      {path:'user',meta:{name:'user message'},name:'user',component:()=> import('../views/User.vue'),},
      {path:'person',meta:{name:'person page'},name:'person',component:()=> import('../views/Person.vue'),},
      {path:'updatePassword',meta:{name:'update password page'},name:'person',component:()=> import('../views/UpdatePassword.vue'),},
  ]},
    {path: '/login',name: 'login',  component: () => import('../views/Login.vue')},
    {path: '/register',name: 'register',  component: () => import('../views/Register.vue')},
    {path: '/:pathMatch(.*)', name: 'notfound',component:()=> import('../views/404.vue'),},
  ],
})

export default router
