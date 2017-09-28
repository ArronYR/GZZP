<style scoped>
    .stat-wrapper{
        padding: 20px;
    }
    .count-wrapper{
        text-align: center;
    }
    .count-card{
        margin: auto;
    }
    .count-value{
        color: #F66967;
        font-size: 4rem;
        font-weight: 100;
    }
    .bar-wrapper,
    .pie-wrapper,
    .date-bar-wrapper{
        margin-top: 20px;
        padding: 20px 5px;
        background: #fff;
        border-radius: 4px;
    }
    .count-bar,
    .count-pie,
    .count-date-bar{
        height: 440px;
    }
    .action-wrapper{
        padding-left: 10px;
        text-align: center;
    }
    .action-wrapper > div {
        padding: 0 0 10px;
        vertical-align: middle;
    }
    @media screen and (max-width: 640px){
        .count-card{
            width: auto;
            padding: 0;
        }
        .count-value{
            font-size: 2rem;
        }
    }
</style>

<template>
    <div class="stat-wrapper">
        <Row class="top-wrapper">
            <Col :xs="8" :sm="8" :md="8" :lg="8" class="count-wrapper">
                <Card class="count-card">
                    <h1 class="count-value">{{total}}</h1>
                    <p class="count-title">信息总数</p>
                </Card>
            </Col>
            <Col :xs="8" :sm="8" :md="8" :lg="8" class="count-wrapper">
                <Card class="count-card">
                    <h1 class="count-value">{{personnel}}</h1>
                    <p class="count-title">人事招考</p>
                </Card>
            </Col>
            <Col :xs="8" :sm="8" :md="8" :lg="8" class="count-wrapper">
                <Card class="count-card">
                    <h1 class="count-value">{{recruit}}</h1>
                    <p class="count-title">公司招聘</p>
                </Card>
            </Col>
        </Row>
        <div class="bar-wrapper">
            <div id="count-bar" class="count-bar"></div>
        </div>
        <div class="pie-wrapper">
            <div id="count-pie" class="count-pie"></div>
        </div>
        <div class="date-bar-wrapper">
            <div class="action-wrapper">
                <Select v-model="type" size="small" clearable style="width:100px" @on-change="handleType">
                    <Option v-for="item in types" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
                <Input v-model="keyword" size="small" placeholder="关键词" icon="ios-search" style="max-width: 100px" @on-change="handleKwChange"></Input>
                <RadioGroup v-model="latest" size="small" type="button" @on-change="handleLatest">
                    <Radio label="最近7天"></Radio>
                    <Radio label="最近30天"></Radio>
                    <Radio label="自定义"></Radio>
                </RadioGroup>
                <DatePicker type="daterange" v-show="custom" size="small" placement="bottom-start" placeholder="选择日期" style="width: 200px" @on-change="handleDate"></DatePicker>
            </div>
            <div id="count-date-bar" class="count-date-bar"></div>
        </div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                total: 0,
                personnel: 0,
                recruit: 0,

                latest: '最近7天',
                custom: false,
                
                type: null,
                start: moment().subtract(7, 'days').format('YYYY-MM-DD'),
                end: moment().format('YYYY-MM-DD'),
                keyword: '',

                types: [
                    {
                        value: 1,
                        label: '人事招考'
                    },
                    {
                        value: 2,
                        label: '贵州招聘'
                    },
                    {
                        value: 3,
                        label: '贵阳招聘'
                    },
                    {
                        value: 4,
                        label: '毕节招聘'
                    },
                    {
                        value: 5,
                        label: '遵义招聘'
                    },
                    {
                        value: 6,
                        label: '铜仁招聘'
                    },
                    {
                        value: 7,
                        label: '黔东南招聘'
                    },
                    {
                        value: 8,
                        label: '黔西南招聘'
                    },
                    {
                        value: 9,
                        label: '黔南招聘'
                    },
                    {
                        value: 10,
                        label: '安顺招聘'
                    },
                    {
                        value: 11,
                        label: '六盘水招聘'
                    }
                ]
            }
        },
        mounted () {
            this.getMessagesCount();
            this.getAllCount();
            this.getRecruitCount();
            this.getCountByDate();
        },
        methods: {
            handleType(value) {
                this.type = value !== '' ? value : null;
                this.getCountByDate();
            },
            handleKwChange(event) {
                this.getCountByDate();
            },
            handleLatest(value) {
                if (value === '最近7天') {
                    this.custom = false;
                    this.start = moment().subtract(7, 'days').format('YYYY-MM-DD');
                } else if (value === '最近30天') {
                    this.custom = false;
                    this.start = moment().subtract(30, 'days').format('YYYY-MM-DD');
                }else{
                    this.start = null;
                    this.end = null;
                    this.custom = true;
                }
                this.getCountByDate();
            },
            handleDate(values) {
                if (values[0] !== "" && values[1] !== 'undefined') {
                    this.start = values[0];
                    this.end = values[1];
                }else{
                    this.start = null;
                    this.end = null;
                }
                this.getCountByDate();
            },
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
            getAllCount() {
                axios.get('/api/stat/allCount').then( response => {
                    var countBar = echarts.init(document.getElementById('count-bar'));
                    var option = {
                        title: {
                            text: '全部类型消息总数统计',
                            subtext: '共11类',
                            x: 'left'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {
                                    show: true,
                                    type: ['line', 'bar']
                                },
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: {
                            type: 'value',
                            boundaryGap: [0, 0.01]
                        },
                        yAxis: {
                            type: 'category',
                            data: response.data.result.keys.reverse()
                        },
                        series: [
                            {
                                name: '总数',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'right'
                                    }
                                },
                                data: response.data.result.values.reverse()
                            }
                        ]
                    };
                    countBar.setOption(option);
                });
            },
            getRecruitCount(){
                axios.get('/api/stat/recruitCount').then( response => {
                    var countPie = echarts.init(document.getElementById('count-pie'));
                    var option = {
                        title : {
                            text: '公司招聘区域统计',
                            subtext: '共10类',
                            x:'left'
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
            },
            getCountByDate() {
                axios.get('/api/stat/countByDate', {
                    params: {
                        type: this.type,
                        start: this.start,
                        end: this.end,
                        keyword: this.keyword
                    }
                })
                .then( response => {
                    var countDateBar = echarts.init(document.getElementById('count-date-bar'));
                    var option = {
                        title : {
                            text: '每日信息数统计',
                            subtext: '（单位：条）',
                            x:'left'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                crossStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        toolbox: {
                            feature: {
                                dataView: {show: true, readOnly: false},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        legend: {
                            x : 'center',
                            y : 'bottom',
                            data:['柱状图', '折线图']
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: response.data.result.keys,
                                axisPointer: {
                                    type: 'shadow'
                                },
                                axisTick: {
                                    alignWithLabel: true
                                }
                            }
                        ],
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                name: '柱状图',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: response.data.result.values
                            },
                            {
                                name: '折线图',
                                type: 'line',
                                data: response.data.result.values,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            }
                        ]
                    };
                    countDateBar.setOption(option);
                });
            }
        }
    }
</script>