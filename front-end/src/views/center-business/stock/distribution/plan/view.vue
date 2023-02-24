<template>
  <ViewForm is-view :info="info" @reFresh="reFresh" />
</template>
<script>
import { stockApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'BusinessStockDistributionPlanView',
  components: {
    ViewForm,
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
      stockApi.getDistributePlansDetail({ id: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
