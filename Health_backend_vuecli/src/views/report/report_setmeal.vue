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
                <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                <div id="chart1" style="height:600px;"></div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        created() {
            // 基于准备好的dom，初始化echarts实例
            var myChart1 = this.$echarts.init(document.getElementById('chart1'));

            // 使用刚指定的配置项和数据显示图表。
            //myChart.setOption(option);

            axios.get("/report/getSetmealReport.do").then((res) => {
                myChart1.setOption({
                    title: {
                        text: '套餐预约占比',
                        subtext: '',
                        x: 'center'
                    },
                    tooltip: { //提示框组件
                        trigger: 'item', //触发类型，在饼形图中为item
                        formatter: "{a} <br/>{b} : {c} ({d}%)" //提示内容格式
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: res.data.data.setmealNames
                    },
                    series: [
                    {
                        name: '套餐预约占比',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: res.data.data.setmealCount,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }]
                });
            });
        }
    }
</script>

<style>

</style>