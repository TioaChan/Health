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
                memberData: {}
            }
        },
        props: ["currentMemberData"],
        watch: {
            currentMemberData: {
                deep: true,
                handler(nv, ov) {
                    this.memberData = this.currentMemberData;
                    this.drawLine();
                }
            }
        },
        mounted() {
            this.memberData = this.currentMemberData;
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
                        data: this.memberData.months
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '会员数量',
                        type: 'line',
                        data: this.memberData.memberCount
                    }]
                });
            }
        }
    }
</script>

<style>

</style>