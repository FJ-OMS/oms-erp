<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { settingApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'SettingSchedulerRequestEdit',
  components: {
    ViewForm,
  },
  data() {
    return {
      info: null,
    };
  },
  computed: {
    concurrentId() {
      return this.$route.query.concurrentId || '';
    },
  },
  watch: {},

  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      settingApi.detailRequest({ concurrentId: this.concurrentId }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
