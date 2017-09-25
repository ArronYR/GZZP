<template>
    <Form ref="registerForm" :model="registerForm" :rules="ruleForm" :action="action" :method="formMethod">
        <FormItem prop="username">
            <Input type="text" v-model="registerForm.username" placeholder="UserName" name="name">
                <Icon type="ios-person-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="email">
            <Input type="text" v-model="registerForm.email" placeholder="Email" name="email">
                <Icon type="ios-email-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="password">
            <Input type="password" v-model="registerForm.password" placeholder="Password" name="password">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="passwdCheck">
            <Input type="password" v-model="registerForm.passwdCheck" placeholder="RePassword" name="password_confirmation">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem>
            <input type="hidden" name="_token" :value="_token"/>
            <Button type="primary" @click="handleSubmit('registerForm')">注册</Button>
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
            oldName: String,
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
                    if (this.registerForm.passwdCheck !== '') {
                        // 对第二个密码框单独验证
                        this.$refs.registerForm.validateField('passwdCheck');
                    }
                    callback();
                }
            };
            const validatePassCheck = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.registerForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                registerForm: {
                    username: this.oldName,
                    email: this.oldEmail,
                    password: '',
                    passwdCheck: '',
                },
                ruleForm: {
                    username: [
                        { required: true, message: '请填写昵称', trigger: 'blur' },
                        { type: 'string', min: 2, message: '昵称长度不能小于2位', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请填写邮箱', trigger: 'blur' },
                        { type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
                    ],
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
