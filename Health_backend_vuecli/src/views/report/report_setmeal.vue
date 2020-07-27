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
            <div class="box">
                <echartPie :currentData="currentData" :currentLegendData="currentLegendData" :currentSeriesData="currentSeriesData"></echartPie>
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
                currentLegendData: [],
                currentSeriesData: [],
                currentData: {},
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
                        this.currentLegendData = resp.data.data.setmealNames;
                        this.currentSeriesData = resp.data.data.setmealCount;
                    }
                });
            },

        }
    }
</script>

<style>

</style>