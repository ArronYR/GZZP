<template>
    <Form ref="resetPwForm" :model="resetPwForm" :rules="ruleForm" :action="action" :method="formMethod">
        <FormItem prop="email">
            <Input type="text" readonly placeholder="Email" name="email" :value="email">
                <Icon type="ios-email-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="password">
            <Input type="password" v-model="resetPwForm.password" placeholder="Password" name="password">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="passwdCheck">
            <Input type="password" v-model="resetPwForm.passwdCheck" placeholder="RePassword" name="password_confirmation">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem>
            <input type="hidden" name="_token" :value="_token"/>
            <Button type="primary" @click="handleSubmit('resetPwForm')">注册</Button>
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
            email: String,
            _token: {
                type: String,
                default: window.Laravel.csrfToken
            }
        },
        data () {
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else if (value.length < 6) {
                    callback(new Error('密码长度不能小于6位'));
                } else {
                    if (this.resetPwForm.passwdCheck !== '') {
                        // 对第二个密码框单独验证
                        this.$refs.resetPwForm.validateField('passwdCheck');
                    }
                    callback();
                }
            };
            const validatePassCheck = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.resetPwForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                resetPwForm: {
                    password: '',
                    passwdCheck: '',
                },
                ruleForm: {
                    password: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    passwdCheck: [
                        { validator: validatePassCheck, trigger: 'blur' }
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
