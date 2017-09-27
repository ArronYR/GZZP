<style scoped>
    .stat-wrapper{
        padding: 20px;
    }
    .count-wrapper{
        text-align: center;
    }
    .count-card{
        padding: 2rem;
        width: 320px;
        margin: auto;
    }
    .count-value{
        color: #F66967;
        font-size: 4rem;
        font-weight: 100;
    }
    .pie-wrapper{
        margin-top: 20px;
        padding: 20px;
        background: #fff;
        border-radius: 4px;
    }
    .count-pie{
        height: 440px;
    }
</style>

<template>
    <div class="stat-wrapper">
        <Row class="top-wrapper">
            <Col :xs="24" :sm="24" :md="8" :lg="8" class="count-wrapper">
                <Card class="count-card">
                    <h1 class="count-value">{{total}}</h1>
                    <p class="count-title">信息总数</p>
                </Card>
            </Col>
            <Col :xs="24" :sm="24" :md="8" :lg="8" class="count-wrapper">
                <Card class="count-card">
                    <h1 class="count-value">{{personnel}}</h1>
                    <p class="count-title">人事招考</p>
                </Card>
            </Col>
            <Col :xs="24" :sm="24" :md="8" :lg="8" class="count-wrapper">
                <Card class="count-card">
                    <h1 class="count-value">{{recruit}}</h1>
                    <p class="count-title">公司招聘</p>
                </Card>
            </Col>
        </Row>
        <div class="pie-wrapper">
            <div id="count-pie" class="count-pie"></div>
        </div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                total: 0,
                personnel: 0,
                recruit: 0
            }
        },
        mounted () {
            this.getMessagesCount();
            this.getStatCount();
        },
        methods: {
            getMessagesCount() {
                axios.all([
                    axios.get('/api/messages/count'),
                    axios.get('/api/stat/countByType', {
                        params: {
                            type: 1
                        }
                    })
                ]).then(axios.spread( (firstResp, secondResp) => {
                    this.total = firstResp.data.result;
                    this.personnel = secondResp.data.result;
                    this.recruit = this.total - this.personnel;
                }));
            },
            getStatCount(){
                axios.get('/api/stat/recruit').then( response => {
                    var countPie = echarts.init(document.getElementById('count-pie'));
                    var option = {
                        title : {
                            text: '公司招聘区域统计',
                            subtext: '共10类',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{b} :<br/> {c} 条 ({d}%)"
                        },
                        legend: {
                            x : 'center',
                            y : 'bottom',
                            data:['贵州招聘', '贵阳招聘', '毕节招聘', '遵义招聘', '铜仁招聘', '黔东南招聘', '黔西南招聘', '黔南招聘', '安顺招聘', '六盘水招聘']
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {
                                    show: true,
                                    type: ['pie', 'funnel']
                                },
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        series : [
                            {
                                name:'面积模式',
                                type:'pie',
                                radius : [30, 110],
                                center : ['50%', '50%'],
                                roseType : 'area',
                                data: response.data.result
                            }
                        ]
                    };
                    countPie.setOption(option);
                });
            }
        }
    }
</script>