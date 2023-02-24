<template>
  <ViewForm :is-view="true" :set-flag="setFlag" parent-name="BusinessGoodsSkuPackage" />
</template>
<script>
import ViewForm from '../components/ViewForm.vue';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSkuPackageView',
  components: {
    ViewForm,
  },
  data() {
    return {};
  },
  computed: {
    setFlag() {
      return GOODS_COMBO_TYPES.PACKAGE;
    },
  },
};
</script>
