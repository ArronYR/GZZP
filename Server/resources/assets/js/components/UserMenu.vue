<template>
    <Menu mode="horizontal" theme="light" :active-name="activeName">
        <MenuItem name="home">
            <a href="/home">
                <Icon type="ios-paper"></Icon> 收藏列表
            </a>
        </MenuItem>
        <MenuItem name="password.reset">
            <a href="/password/reset">
                <Icon type="settings"></Icon> 修改密码
            </a>
        </MenuItem>
    </Menu>
</template>
<script>
    export default {
        props: {
            activeName: {
                type: String,
                default: 'home'
            } 
        },
        data () {
            return {
            }
        }
    }
</script>
