import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
import Home from '../components/Home.vue'
import Setmeal from '../components/setmeal.vue'
import OrderSetting from '../components/ordersetting.vue'
import newCheckItem from '../views/checkitem/index.vue'
import newCheckGroup from '../views/checkgroup/index.vue'



Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
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
        component: Setmeal
    }, {
        path: '/OrderSetting',
        name: 'OrderSetting',
        component: OrderSetting
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