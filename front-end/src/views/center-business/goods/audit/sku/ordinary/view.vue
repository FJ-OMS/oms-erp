<template>
  <ViewForm :set-flag="GOODS_COMBO_TYPES.ORDINARY" />
</template>
<script>
import ViewForm from '../components/ViewForm';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsAuditSkuOrdinaryView',
  components: {
    ViewForm,
  },
  data() {
    return { GOODS_COMBO_TYPES };
  },
  created() {},
  methods: {},
};
</script>

<style></style>
