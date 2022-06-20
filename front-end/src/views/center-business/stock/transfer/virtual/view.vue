<template>
  <ViewForm is-view :is-verify="isVerify" :info="info" @reFresh="reFresh" />
</template>
<script>
import { stockApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'BusinessStockTransferVirtualView',
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
      stockApi.getDistributeVirtualsDetail({ id: this.id }).then(res => {
        console.log(res);
        this.info = res;
        this.info.typeCode = res.receiveWarehouseTypeCode;
      });
    },
  },
};
</script>
