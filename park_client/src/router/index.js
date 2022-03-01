import Vue from 'vue'
import VueRouter from 'vue-router'

import { getToken, getTokenAdmin } from "@/utils/auth";
import Register from "@/views/Register"
import Login from "@/views/Login"
import Error404 from "@/views/error/404"

import Member from "@/views/member"
import CarMsg from "@/views/member/CarMsg"
import CarEditor from "@/views/member/CarEditor"
import User from "@/views/member/User"
import ChangePwd from "@/views/member/ChangePwd"
import CardMsg from "@/views/member/CardMsg"
import ParkMsg from "@/views/member/ParkMsg"
import Record from "@/views/member/Record"
import Announ from "@/views/member/Announ"

import AdminLogin from "@/views/Login_admin"
import Member_admin from "@/views/member_ad"
import AdminMsg from "@/views/member_ad/AdminMsg"
import UserMsg from "@/views/member_ad/UserMsg"
import CarPortMsg from "@/views/member_ad/CarPortMsg"
import PayFee from "@/views/member_ad/PayFee"
import Announcement from "@/views/member_ad/Announcement"
import AllRecord from "@/views/member_ad/AllRecord"


Vue.use(VueRouter)

// 子路由的path不能有"/"
const routes = [
    {
        path: '',
        redirect: Login,
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/error404',
        name: 'Error404',
        component: Error404
    },
    {
        path: '/member',
        name: 'Member',
        component: Member,
        redirect: '/member/announ',
        children: [
            {
                path: 'carMsg',
                name: 'CarMsg',
                component: CarMsg,
            },
            {
                path: '/member/carEditor/:carId',
                name: 'CarEditor',
                component: CarEditor,
                props: true
            },
            {
                path: 'user',
                name: 'User',
                component: User,
            },
            {
                path: 'changePwd',
                name: 'ChangePwd',
                component: ChangePwd,
            },
            {
                path: 'cardmsg',
                name: 'CardMsg',
                component: CardMsg,
            },
            {
                path: 'parkMsg',
                name: 'ParkMsg',
                component: ParkMsg,
            },
            {
                path: 'record',
                name: 'Record',
                component: Record,
            },
            {
                path: 'announ',
                name: 'Announ',
                component: Announ,
            },
        ]
    },

    {
        path: '/login/admins',
        name: 'AdminLogin',
        component: AdminLogin
    },
    {
        path: '/admin',
        name: 'Member_ad',
        component: Member_admin,
        redirect: '/admin/carPortMsg',
        children: [
            {
                path: 'adminMsg',
                name: 'AdminMsg',
                component: AdminMsg,
            },
            {
                path: 'userMsg',
                name: 'UserMsg',
                component: UserMsg,
            },
            {
                path: 'carPortMsg',
                name: 'CarPortMsg',
                component: CarPortMsg,
            },
            {
                path: 'payFee',
                name: 'PayFee',
                component: PayFee,
            },
            {
                path: 'announcement',
                name: 'Announcement',
                component: Announcement,
            },
            {
                path: 'allRecord',
                name: 'AllRecord',
                component: AllRecord,
            },
        ]
    }

]

const router = new VueRouter({
    // mode: "history",
    // base: "/blog_client",
    routes
})


router.beforeEach((to, from, next) => {// 路由守卫
    if (to.matched) {
        if (to.path.startsWith('/member') && !getToken())
            next({ path: '/login', query: { fromPath: to.path } })
        else if (to.path.startsWith('/admin') && !getTokenAdmin())
            next({ path: '/login/admins', query: { fromPath: to.path } })
        else if (to.matched.length == 0 && !getToken() && !getTokenAdmin())
            next({ path: '/login', query: { fromPath: to.path } })
        else if (to.matched.length == 0 && getToken() && getTokenAdmin())
            router.replace('/error404')
        else next()
    } else {
        next()
    }
})

export default router
