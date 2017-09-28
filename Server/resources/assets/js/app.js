
/**
 * First we will load all of this project's JavaScript dependencies which
 * includes Vue and other libraries. It is a great starting point when
 * building robust, powerful web applications using Vue and Laravel.
 */

require('./bootstrap');

window.Vue = require('vue');

const iView = require('iview');
window.Vue.use(iView);

/**
 * Next, we will create a fresh Vue application instance and attach it to
 * the page. Then, you may begin adding components to this application
 * or customize the JavaScript scaffolding to fit your unique needs.
 */

Vue.component('register-form', require('./components/RegisterForm.vue'));
Vue.component('login-form', require('./components/LoginForm.vue'));
Vue.component('repw-form', require('./components/RepwForm.vue'));
Vue.component('user-menu', require('./components/UserMenu.vue'));
Vue.component('db-menu', require('./components/DbMenu.vue'));
Vue.component('collections', require('./components/Collection.vue'));
Vue.component('message-table', require('./components/MessageTable'));
Vue.component('message-stat', require('./components/MessageStat'));
Vue.component('download-modal', require('./components/DownloadModal'));

const app = new Vue({
    el: '#app'
});