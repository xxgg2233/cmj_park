import '@mdi/font/css/materialdesignicons.css' // Ensure you are using css-loader
import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    icons: {
        iconfont: 'mdi', // 默认值 - 仅用于展示目的
        values: {
            user: 'mdi-account',
            article: 'mdi-notebook-edit',
            search: 'mdi-text-search',
            refresh: 'mdi-refresh',
            click: 'mdi-cursor-default-click',
            realname: 'mdi-card-account-details-outline',
            login: 'mdi-login',
            register: 'mdi-account-plus',
            password: 'mdi-form-textbox-password',
            email: 'mdi-email',
            phone: 'mdi-phone',
            edit: 'mdi-pencil',
            delete: 'mdi-delete',
            car: 'mdi-car',
            park: 'mdi-parking',
            card: 'mdi-credit-card-outline',
            note: 'mdi-message-bulleted',
            history: 'mdi-history',
            admin: 'mdi-account-tie',
            set: 'mdi-comment-edit-outline',
            pay: 'mdi-currency-usd',
        },
    },
});
