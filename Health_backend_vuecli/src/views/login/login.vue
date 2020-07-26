<template>
    <div id="app">
        <div class="login-container">
            <div class="loginBox">
                <!-- <form method="post" class="login-form" action="/login.do" label-position="left">
                    <div class="title-container">
                        <div class="logoInfo clearfix">
                            <em class="logo"></em>
                        </div>
                    </div>
                    <div>
                        <span class="svg-container svg-container_login">
                            <span class="user"></span>
                        </span>
                        <input type="text" name="username" placeholder="请输入用户名" />
                    </div>
                    <div>
                        <span class="svg-container">
                            <span class="username"></span>
                        </span>
                        <input type="password" name="password" placeholder="请输入密码" />
                    </div>
                    <input type="submit" style="width:100%;margin-bottom:30px;" value="登录" />
                </form> -->

                <el-form :model="loginForm" :rules="rules" status-icon ref="loginForm" label-width="100px">
                    <el-form-item>
                        <div class="logoInfo clearfix">
                            <em class="logo"></em>
                        </div>
                    </el-form-item>
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="loginForm.username" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                        <!-- <el-button @click="resetForm('loginForm')">重置</el-button> -->
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        data() {
            // var checkAge = (rule, value, callback) => {
            //     if (!value) {
            //         return callback(new Error('年龄不能为空'));
            //     }
            //     setTimeout(() => {
            //         if (!Number.isInteger(value)) {
            //             callback(new Error('请输入数字值'));
            //         } else {
            //             if (value < 18) {
            //                 callback(new Error('必须年满18岁'));
            //             } else {
            //                 callback();
            //             }
            //         }
            //     }, 1000);
            // };
            var validateUsername = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入用户名'));
                }
                // else if (value !== this.loginForm.pass) {
                //     callback(new Error('两次输入密码不一致!'));
                // } else {
                //     callback();
                // }
            };
            var validatePassword = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                }
                // else {
                //     if (this.loginForm.checkPass !== '') {
                //         this.$refs.loginForm.validateField('password');
                //     }
                //     callback();
                // }
            };
            return {
                loginForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        { validator: validateUsername, trigger: 'blur' }
                    ],
                    password: [
                        { validator: validatePassword, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitForm(loginForm) {
                this.$refs['loginForm'].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            // resetForm(formName) {
            //     this.$refs[formName].resetFields();
            // }
        }
    }
</script>

<style scoped>
    @import "../../assets/css/login.css";
</style>