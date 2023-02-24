<template>
  <viewForm
    :is-view="true"
    :info="info"
    :set-flag="GOODS_COMBO_TYPES.ORDINARY"
    @reFresh="reFresh"
  />
</template>
<script>
import { goodsApi } from '@/api';
import viewForm from '../components/viewForm';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSpuPackageView',
  components: {
    viewForm,
  },
  data() {
    return {
      GOODS_COMBO_TYPES,
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
      this.info = {
        id: this.$route.query.spuId || '',
      };
      goodsApi.detail({ id: this.id }).then(res => {
        this.info = res;
      });
    },
  },
};
</script>
