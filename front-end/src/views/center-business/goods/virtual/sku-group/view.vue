<template>
  <ViewForm :is-view="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { goodsApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'BusinessGoodsVirtualSkuGroupView',
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
      goodsApi.detailSkuGroup({ groupId: this.id }).then(res => {
        this.info = res;
      });
    },
  },
};
</script>
