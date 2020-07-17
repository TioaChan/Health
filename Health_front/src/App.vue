<template>
    <div id="app" class="hold-transition skin-purple sidebar-mini">
        <!-- <a href="/">Index</a>
        <a href="/CheckItem">CheckItem</a>
        <a href="/CheckGroup">CheckGroup</a>
        <a href="/Setmeal">CheckGroup</a>
        <router-view /> -->

        <el-container>
            <el-header class="main-header" style="height:70px;">
                <nav class="navbar navbar-static-top">
                    <!-- Logo -->
                    <a href="#" class="logo" style="text-align:center">
                        <span class="logo-lg"><img :src="itcastLogoUrl"></span>
                    </a>
                    <div class="right-menu">
                        <span class="help"><i class="fa fa-exclamation-circle" aria-hidden="true"></i>帮助</span>
                        <el-dropdown class="avatar-container right-menu-item" trigger="click">
                            <div class="avatar-wrapper">
                                <img :src="userAvatarUrl" class="user-avatar">
                                Jay.Liu
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item divided>
                                    <span style="display:block;">修改密码</span>
                                </el-dropdown-item>
                                <el-dropdown-item divided>
                                    <span style="display:block;">退出</span>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </nav>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu>
                        <el-submenu v-for="menu in menuList" :index="menu.path">
                            <template slot="title">
                                <i class="fa" :class="menu.icon"></i>
                                {{menu.title}}
                            </template>
                            <template v-for="child in menu.children">
                                <el-menu-item :index="child.path">
                                    <a :href="child.linkUrl">{{child.title}}</a>
                                </el-menu-item>
                            </template>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-container class="el-main">
                    <router-view />
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    // import HelloWorld from './components/HelloWorld.vue'

    export default {
        name: 'app',
        components: {
            // HelloWorld
        },
        data() {
            return {
                menuList: [{
                    "path": "1",
                    "title": "工作台",
                    "icon": "fa-dashboard",
                    "children": []
                }, {
                    "path": "2",
                    "title": "会员管理",
                    "icon": "fa-user-md",
                    "children": [{
                        "path": "/2-1",
                        "title": "会员档案",
                        "linkUrl": "member.html",
                        "children": []
                    }, {
                        "path": "/2-2",
                        "title": "体检上传",
                        "children": []
                    }, {
                        "path": "/2-3",
                        "title": "会员统计",
                        "linkUrl": "all-item-list.html",
                        "children": []
                    }]
                }, {
                    "path": "3",
                    "title": "预约管理",
                    "icon": "fa-tty",
                    "children": [{
                        "path": "/3-1",
                        "title": "预约列表",
                        "linkUrl": "ordersettinglist.html",
                        "children": []
                    }, {
                        "path": "/3-2",
                        "title": "预约设置",
                        "linkUrl": "ordersetting.html",
                        "children": []
                    }, {
                        "path": "/3-3",
                        "title": "套餐管理",
                        "linkUrl": "Setmeal",
                        "children": []
                    }, {
                        "path": "/3-4",
                        "title": "检查组管理",
                        "linkUrl": "CheckGroup",
                        "children": []
                    }, {
                        "path": "/3-5",
                        "title": "检查项管理",
                        "linkUrl": "CheckItem",
                        "children": []
                    }]
                }, {
                    "path": "4",
                    "title": "健康评估",
                    "icon": "fa-stethoscope",
                    "children": [{
                        "path": "/4-1",
                        "title": "中医体质辨识",
                        "linkUrl": "all-medical-list.html",
                        "children": []
                    }]
                }, {
                    "path": "5", //菜单项所对应的路由路径
                    "title": "统计分析", //菜单项名称
                    "icon": "fa-heartbeat",
                    "children": [{ //是否有子菜单，若没有，则为[]
                        "path": "/5-1",
                        "title": "工作量统计",
                        "linkUrl": "all-medical-list.html",
                        "children": []
                    }]
                }],
                "itcastLogoUrl": require("../src/assets/img/logo.png"),
                "userAvatarUrl": require("../src/assets/img/user2-160x160.jpg"),
            }
        }
    }
</script>

<style scoped>
    @import "assets/css/style.css";

    .el-main {
        position: absolute;
        top: 70px;
        bottom: 0px;
        left: 200px;
        right: 10px;
        padding: 0;
    }
</style>