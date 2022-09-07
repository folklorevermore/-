import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Vant from 'vant';
import 'vant/lib/index.css';
import { Popup } from 'vant';
import { Form } from 'vant';
import { Field } from 'vant';
import VeCharts from 've-charts'

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(Vant);
Vue.use(Popup);
Vue.use(Form);
Vue.use(Field);
Vue.use(VeCharts)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
