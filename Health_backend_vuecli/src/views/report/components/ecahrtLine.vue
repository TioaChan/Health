<template>
    <div>
        <h3>{{title}}</h3>
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="chart1" style="height:600px;"></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                title: null,
                xAxis: [],
                seriesData: [],
            }
        },
        props: ["currentTitle", "currentXAxis", "currentSeriesData"],
        watch: {
            currentTitle: {
                deep: true,
                handler(nv, ov) {
                    this.title = this.currentTitle;
                    this.drawLine();
                }
            },
            currentXAxis: {
                deep: true,
                handler(nv, ov) {
                    this.xAxis = this.currentXAxis;
                    this.drawLine();
                }
            },
            currentSeriesData: {
                deep: true,
                handler(nv, ov) {
                    this.seriesData = this.currentSeriesData;
                    this.drawLine();
                }
            }
        },
        mounted() {
            this.title = this.currentTitle;
            this.xAxis = this.currentXAxis;
            this.seriesData = this.currentSeriesData;
            this.drawLine();
        },
        methods: {
            drawLine() {
                let myChart1 = this.$echarts.init(document.getElementById('chart1'));
                myChart1.clear();
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
                        data: this.xAxis
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '会员数量',
                        type: 'line',
                        data: this.seriesData
                    }]
                });
            }
        }
    }
</script>

<style>

</style>