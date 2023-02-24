<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { tenantApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'SettingTenantEdit',
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
      tenantApi.detailTenants({ id: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
