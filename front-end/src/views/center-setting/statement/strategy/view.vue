<template>
  <ViewForm is-view :is-verify="isVerify" :info="info" @reFresh="reFresh" />
</template>
<script>
import { stockApi } from '@/api';
import ViewForm from './components/DataForm.vue';
export default {
  name: 'SettingStatementStrategyView',
  components: {
    ViewForm,
  },
  props: {
    isVerify: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      info: {},
    };
  },
  computed: {
    id() {
      return this.$route.query.id || '';
    },
  },
  watch: {},

  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      stockApi.detailAllotRecords({ moveId: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
