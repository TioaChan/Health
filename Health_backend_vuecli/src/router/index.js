import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
import Home from '../components/Home.vue'
import OrderSetting from '../components/ordersetting.vue'
import newCheckItem from '../views/checkitem/index.vue'
import newCheckGroup from '../views/checkgroup/index.vue'
import newSetmeal from '../views/setmeal/index.vue'
import login from '../views/login/login.vue'
import report_member from '../views/report/report_member.vue'
import report_setmeal from '../views/report/report_setmeal.vue'
import report_business from '../views/report/report_business.vue'


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    }, {
        path: '/CheckItem',
        name: 'CheckItem',
        component: newCheckItem
    }, {
        path: '/CheckGroup',
        name: 'CheckGroup',
        component: newCheckGroup
    }, {
        path: '/Setmeal',
        name: 'Setmeal',
        component: newSetmeal
    }, {
        path: '/OrderSetting',
        name: 'OrderSetting',
        component: OrderSetting
    }, {
        path: '/Login',
        name: 'login',
        component: login,
        meta: {
            keepalive: true
        }
    }, {
        path: '/report_member',
        name: 'report_member',
        component: report_member
    }, {
        path: '/report_setmeal',
        name: 'report_setmeal',
        component: report_setmeal
    }, {
        path: '/report_business',
        name: 'report_business',
        component: report_business
    }
    // , {
    //     path: '/newCheckItem',
    //     name: 'newCheckItem',
    //     component: newCheckItem
    // }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router