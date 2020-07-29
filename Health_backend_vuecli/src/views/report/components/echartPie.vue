<template>
    <div>
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="chart1" style="height:600px;"></div>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                legendData: [],
                seriesData: [],
                chartData: {},
            }
        },
        // props: ["currentLegendData", "currentSeriesData"],
        props: ["currentData"],
        watch: {
            // currentLegendData: {
            //     deep: true,
            //     handler(nv, ov) {
            //         this.legendData = this.currentLegendData;
            //         this.drawPie();
            //     }
            // },
            // currentSeriesData: {
            //     deep: true,
            //     handler(nv, ov) {
            //         this.sriesData = this.currentSeriesData;
            //         this.drawPie();
            //     }
            // },
            currentData: {
                deep: true,
                handler(nv, ov) {
                    this.chartData = this.currentData;
                    this.drawPie();
                }
            }
        },
        mounted() {
            // this.legendData = this.currentLegendData;
            // this.sriesData = this.currentSeriesData;
            this.chartData = this.currentData;
            this.drawPie();
        },
        methods: {
            drawPie() {
                // console.log(this.chartData);
                // 基于准备好的dom，初始化echarts实例
                let myChart1 = this.$echarts.init(document.getElementById('chart1'));
                console.log(this.chartData);
                myChart1.clear();
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
                        data: this.currentData.setmealNames
                    },
                    series: [ //数据系列
                        {
                            name: '套餐预约占比',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: this.currentData.setmealCount,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                });
            }
        }
    }
</script>

<style>

</style>