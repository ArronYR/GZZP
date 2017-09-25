<style scoped>
    .ivu-spin{
        background-color: transparent;
    }
    .items-wrapper{
        position: relative;
    }
    .time{
        font-size: 16px;
        font-weight: normal;
    }
    .content{
        padding: 10px 5px 5px;
    }
    .msg-time{
        color: #F66967;
    }
    .type{
        font-weight: normal;
        font-size: 12px;
        float: right;
        color: #C41121;;
    }
    .empty-card{
        width: 40%;
        margin: 0 auto;
    }
    .card-content{
        text-align: center;
    }
    .card-content img{
        width: 100%;
    }
</style>
<template>
    <div class="items-wrapper">
        <Timeline>
            <TimelineItem v-for="(item, indx) in items" :key="indx">
                <p class="time">
                    {{item.created_at}}
                    <span class="type">{{item.message.type_text}}</span>
                </p>
                <a :href="'/detail/' + item.message.id" class="content" target="_blank">
                    <span class="msg-time">{{item.message.published_at}}</span>
                    <span class="msg-title">{{item.message.title}}</span>
                </a>
            </TimelineItem>
        </Timeline>
        <Page :total="total" :page-size="15" size="small" show-total show-elevator @on-change="handlePage"></Page>
        <Card class="empty-card" v-if="items.length == 0">
            <div class="card-content">
                <img src="/img/qr_code.png">
                <h3>你还没在App中收藏任何内容</h3>
                <p>（扫描二维码，下载App）</p>
            </div>
        </Card>
        <Spin size="large" fix v-if="spinShow"></Spin>
    </div>
</template>
<script>
    export default {
        props: {
            uid: {
                type: String,
                default: '0'
            }
        },
        data () {
            return {
                spinShow: true,
                items: [],
                total: 0,
            }
        },
        created () {
            this.$Loading.start();
        },
        mounted () {
            this.handlePage(1);
        },
        methods: {
            handlePage(page) {
                axios.get('/api/collections/' + this.uid, {
                        params: {
                            page: page
                        }
                    })
                    .then( response => {
                        if (response.data.error) {
                            this.$Message.error(response.data.msg);
                        }else{
                            this.items = response.data.result.data;
                            this.total = response.data.result.total;
                        }
                        this.$Loading.finish();
                    })
                    .catch( error => {
                        this.$Message.error("发生错误");
                        this.$Loading.error();
                    })
                    .then( () => {
                        this.spinShow =  false;
                    });
            }
        },
    }
</script>
