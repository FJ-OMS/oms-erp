<template>
  <ViewForm :set-flag="GOODS_COMBO_TYPES.PACKAGE" />
</template>
<script>
import ViewForm from '../components/ViewForm';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsAuditSkuPackageView',
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
