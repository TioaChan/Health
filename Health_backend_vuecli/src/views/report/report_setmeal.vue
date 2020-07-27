<template>
    <div id="app">
        <div class="content-header">
            <h1>统计分析<small>套餐占比</small></h1>
            <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>统计分析</el-breadcrumb-item>
                <el-breadcrumb-item>套餐占比</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="app-container">
            <div class="box" id="printMe">
                <div class="block">
                    <!-- <el-date-picker v-model="value1" type="monthrange" range-separator="至" start-placeholder="开始月份" end-placeholder="结束月份" @change="monthChanged()">
						</el-date-picker> -->
                    <el-button v-print="printObj">导出为PDF</el-button>
                </div>
                <echartPie :currentData="currentData"></echartPie>
            </div>
        </div>
    </div>
</template>

<script>
    import echartPie from './components/echartPie.vue'
    export default {
        components: {
            echartPie
        },
        data() {
            return {
                currentData: {},
                printObj: {
                    id: "printMe",
                    popTitle: 'good print',
                    extraCss: 'https://www.google.com,https://www.google.com',
                    extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>'
                }
            }
        },
        created() {
            this.getSetmealReportData();
        },
        methods: {
            getSetmealReportData() {
                this.$http.get("http://127.0.0.1:82/report/getSetmealReport.do").then((resp) => {
                    if (resp.data.flag) {
                        this.currentData = resp.data.data;
                    }
                });
            },

        }
    }
</script>

<style>

</style>