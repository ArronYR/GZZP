<<style scoped>
    .download-modal{
        text-align: center;
    }
    .download-qrcode{
        width: 200px;
        max-width: 200px;
        display: block;
        margin: auto;
    }
    .download-link{
        text-align: center!important;
    }
</style>
<template>
    <span>
        <a @click="modal = true">{{text}}</a>
        <Modal v-model="modal" class="download-modal">
            <img src="/img/qr_code.png" alt="二维码" class="download-qrcode">
            <div slot="footer" class="download-link">
                <a href="https://www.pgyer.com/gzzp" target="_blank">前往下载页面 (Android)</a>
            </div>
        </Modal>
    </span>
</template>
<script>
    export default {
        props: {
            text: {
                type: String,
                default: '下载App'
            }
        },
        data () {
            return {
                modal: false,
            }            
        }
    }
</script>
