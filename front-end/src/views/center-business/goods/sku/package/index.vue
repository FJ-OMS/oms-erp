<template>
  <div class="app-container">
    <BjRouteTabs />
    <PageOrdinaryPackage :set-flag="GOODS_COMBO_TYPES.PACKAGE" />
  </div>
</template>
<script>
import PageOrdinaryPackage from '../components/PageOrdinaryPackage.vue';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSkuPackageIndex',
  components: {
    PageOrdinaryPackage,
  },
  data() {
    return {
      GOODS_COMBO_TYPES,
    };
  },
};
</script>
