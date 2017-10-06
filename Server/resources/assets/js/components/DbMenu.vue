<template>
    <Menu mode="horizontal" theme="light" :active-name="activeName">
        <MenuItem name="dashboard">
            <a href="/dashboard">
                <Icon type="home"></Icon> 首页
            </a>
        </MenuItem>
        <MenuItem name="dashboard/type/1">
            <a href="/dashboard/type/1">
                <Icon type="ios-compose"></Icon> 人事招考
            </a>
        </MenuItem>
        <Submenu name="3">
            <template slot="title">
                <Icon type="ios-paper"></Icon> 公司招聘
            </template>
            <MenuItem name="dashboard/type/2">
                <a href="/dashboard/type/2">贵州招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/3">
                <a href="/dashboard/type/3">贵阳招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/4">
                <a href="/dashboard/type/4">毕节招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/5">
                <a href="/dashboard/type/5">遵义招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/6">
                <a href="/dashboard/type/6">铜仁招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/7">
                <a href="/dashboard/type/7">黔东南招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/8">
                <a href="/dashboard/type/8">黔西南招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/9">
                <a href="/dashboard/type/9">黔南招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/10">
                <a href="/dashboard/type/10">安顺招聘</a>
            </MenuItem>
            <MenuItem name="dashboard/type/11">
                <a href="/dashboard/type/11">六盘水招聘</a>
            </MenuItem>
        </Submenu>
    </Menu>
</template>
<script>
    export default {
        props: {
            activeName: {
                type: String,
                default: 'dashboard'
            } 
        },
        data () {
            return {
            }
        }
    }
</script>
