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
            <div class="box">
                <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                <div id="chart1" style="height:600px;"></div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                date: {
                    year: null,
                    month: null,
                    date: null,
                },
                months: [],
                memberCount: [],
            }
        },
        methods: {

        },
        created() {
            let nowDate = new Date();
            this.date.year = nowDate.getFullYear();
            this.date.month = nowDate.getMonth() + 1;
            this.date.date = nowDate.getDate();
            // console.log(this.date);
            this.$http.get("http://127.0.0.1:82/report/getMemberReport.do?date=" + this.date.year + "-" + this.date.month).then((res) => {
                if (res.data.flag) {
                    this.months = res.data.data.months;
                    this.memberCount = res.data.data.memberCount;
                    console.log(this.months);
                    console.log(this.memberCount);
                    var myChart1 = this.$echarts.init(document.getElementById('chart1'));

                    myChart1.setOption(
                    {
                        title: {
                            text: '会员数量'
                        },
                        tooltip: {},
                        legend: {
                            data: ['会员数量']
                        },
                        xAxis: {
                            data: this.months
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            name: '会员数量',
                            type: 'line',
                            data: this.memberCount
                        }]
                    });
                }
            });
        }
    }
</script>

<style>

</style>