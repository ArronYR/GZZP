<template>
    <Form ref="loginForm" :model="loginForm" :rules="ruleForm" :action="action" :method="formMethod">
        <FormItem prop="email">
            <Input type="text" v-model="loginForm.email" placeholder="Email" name="email">
                <Icon type="ios-email-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="password">
            <Input type="password" v-model="loginForm.password" placeholder="Password" name="password">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem>
            <input type="hidden" name="_token" :value="_token"/>
            <Button type="primary" @click="handleSubmit('loginForm')">登录</Button>
        </FormItem>
    </Form>
</template>
<script>
    export default {
        props: {
            formMethod: {
                type: String,
                default: 'POST'
            },
            action: String,
            oldEmail: String,
            _token: {
                type: String,
                default: window.Laravel.csrfToken
            }
        },
        data () {
            return {
                loginForm: {
                    email: this.oldEmail,
                    password: ''
                },
                ruleForm: {
                    email: [
                        { required: true, message: '请填写邮箱', trigger: 'blur' },
                        { type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
                    ],
                    password: [
                        { required: true, message: '请填写密码', trigger: 'blur' },
                        { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$refs[name].$el.submit();
                    }
                })
            }
        }
    }
</script>
