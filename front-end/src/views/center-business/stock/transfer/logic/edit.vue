<template>
  <ViewForm is-edit :info="info" @reFresh="reFresh" />
</template>
<script>
import { stockApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'BusinessStockTransferLogicEdit',
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
      stockApi.getAllotRecordsDetail({ allotId: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
