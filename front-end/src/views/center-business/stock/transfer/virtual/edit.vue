<template>
  <ViewForm is-edit :info="info" @reFresh="reFresh" />
</template>
<script>
import { stockApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'BusinessStockTransferVirtualEdit',
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
      stockApi.getDistributeVirtualsDetail({ id: this.id }).then(res => {
        console.log(res);
        this.info = res;
        this.info.typeCode = res.receiveWarehouseTypeCode;
      });
    },
  },
};
</script>
