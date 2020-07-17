import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
import Home from '../components/Home.vue'
import CheckItem from '../components/checkitem.vue'
import CheckGroup from '../components/checkgroup.vue'
import Setmeal from '../components/setmeal.vue'
import OrderSetting from '../components/ordersetting.vue';


Vue.use(VueRouter)

const routes = [
{
    path: '/',
    name: 'Home',
    component: Home
}, {
    path: '/CheckItem',
    name: 'CheckItem',
    component: CheckItem
}, {
    path: '/CheckGroup',
    name: 'CheckGroup',
    component: CheckGroup
}, {
    path: '/Setmeal',
    name: 'Setmeal',
    component: Setmeal
}, {
    path: '/OrderSetting',
    name: 'OrderSetting',
    component: OrderSetting
}]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router