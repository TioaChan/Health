<template>
    <div id="app">
        <div class="content-header">
            <h1>统计分析<small>会员数量</small></h1>
            <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>统计分析</el-breadcrumb-item>
                <el-breadcrumb-item>会员数量</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="app-container">

            <div class="box" id="printMe">
                <div class="block">
                    <el-date-picker v-model="value1" type="monthrange" range-separator="至" start-placeholder="开始月份" end-placeholder="结束月份" @change="monthChanged()">
                    </el-date-picker>
                    <el-button v-print="printObj">导出为PDF</el-button>
                </div>
                <ecahrtLine :currentTitle="currentTitle" :currentXAxis="currentXAxis" :currentSeriesData="currentSeriesData"></ecahrtLine>
            </div>
        </div>
    </div>
</template>

<script>
    import ecahrtLine from './components/ecahrtLine.vue'
    export default {
        components: {
            ecahrtLine
        },
        data() {
            return {
                currentTitle: null,
                currentXAxis: [],
                currentSeriesData: [],
                value1: '',
                printObj: {
                    id: "printMe",
                    popTitle: 'good print',
                    extraCss: 'https://www.google.com,https://www.google.com',
                    extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>'
                }
            }
        },
        created() {
            let nowDate = new Date();
            let startDate = new Date();
            startDate.setFullYear(startDate.getFullYear() - 1);
            this.value1 = [startDate, nowDate];

            let param1 = this.value1[0].getFullYear() + "-" + (this.value1[0].getMonth() + 1);
            let param2 = this.value1[1].getFullYear() + "-" + (this.value1[1].getMonth() + 1);
            console.log("?")
            this.getMemberReport(param1, param2);
        },
        methods: {
            getMemberReport(param1, param2) {
                this.$http.get("http://127.0.0.1:82/report/getMemberReport.do?startDate=" + param1 + "&endDate=" + param2).then((res) => {
                    if (res.data.flag) {
                        this.currentXAxis = res.data.data.months;
                        this.currentSeriesData = res.data.data.memberCount;
                    }
                });
            },
            monthChanged() {
                let param1 = this.value1[0].getFullYear() + "-" + (this.value1[0].getMonth() + 1);
                let param2 = this.value1[1].getFullYear() + "-" + (this.value1[1].getMonth() + 1);

                this.getMemberReport(param1, param2);
            }
        }
    }
</script>

<style>

</style>