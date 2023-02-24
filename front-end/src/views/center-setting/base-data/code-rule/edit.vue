<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { baseDataApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'SettingBaseDataCodeRuleEdit',
  components: {
    ViewForm,
  },
  data() {
    return {
      info: null,
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
      baseDataApi.detailCodeRule({ ruleId: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
