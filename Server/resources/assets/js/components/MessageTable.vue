<style scoped>
    .msg-wrapper{
        padding: 20px;
    }
    .action-wrapper > div{
        padding: 10px 0;
    }
    .page-wrapper{
        text-align: right;
        padding-top: 13px !important;
    }
</style>
<template>
    <div class="msg-wrapper">
        <Row class="action-wrapper">
            <Col :xs="24" :sm="24" :md="12" :lg="12" class="search-wrapper">
                <Input v-model="keyword" placeholder="输入关键词" icon="ios-search" style="max-width: 400px" @on-change="handleKwChange"></Input>
            </Col>
            <Col :xs="24" :sm="24" :md="12" :lg="12" class="page-wrapper">
                <Page :total="total" :page-size="20" size="small" @on-change="handlePage"></Page>
            </Col>
        </Row>
        <Table :loading="loading" :columns="columns" :data="messages"></Table>
    </div>
</template>
<script>
    export default {
        props: {
            typeId: {
                type: Number,
                default: 1
            }
        },
        data () {
            return {
                loading: true,
                columns: [
                    {
                        title: '#',
                        key: 'id',
                        width: 80,
                        sortable: true,
                    },
                    {
                        title: '标题',
                        key: 'title',
                        ellipsis: true,
                        className: 'td-title',
                        render: (h, params) => {
                            return h('div', [
                                h('a', {
                                    attrs: {
                                        href: "/detail/" + params.row.id,
                                        target: '_blank'
                                    }
                                }, params.row.title)
                            ]);
                        }
                    },
                    {
                        title: '类型',
                        key: 'type_text',
                        width: 90,
                        className: 'td-type'
                    },
                    {
                        title: '发布时间',
                        key: 'published_at',
                        width: 120,
                        sortable: true
                    }
                ],
                messages: [],
                total: 0,
                keyword: ''
            }
        },
        mounted () {
            this.getData(this.typeId, 1, this.keyword);
        },
        methods: {
            handleKwChange(event) {
                this.getData(this.typeId, 1, this.keyword);
            },
            handlePage(page) {
                this.getData(this.typeId, page, this.keyword);
            },
            getData(type, page, keyword) {
                axios.get('/api/messages', {
                    params: {
                        type: type,
                        page: page,
                        keyword: keyword
                    }
                })
                .then( response => {
                    if (response.data.error) {
                        this.$Message.error(response.data.msg);
                    }else{
                        this.messages = response.data.result.data;
                        this.total = response.data.result.total;
                    }
                })
                .catch( error => {
                    this.$Message.error("发生错误");
                })
                .then( () => {
                    this.loading = false;
                });
            }
        }
    }
</script>
