<template>
  <viewForm :is-add="true" :set-flag="GOODS_COMBO_TYPES.PACKAGE" />
</template>
<script>
import viewForm from '../components/viewForm';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSpuPackageAdd',
  components: { viewForm },
  data() {
    return { GOODS_COMBO_TYPES };
  },
};
</script>
