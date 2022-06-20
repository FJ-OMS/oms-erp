<template>
  <viewForm :is-add="true" :set-flag="GOODS_COMBO_TYPES.ORDINARY" />
</template>
<script>
import viewForm from '../components/viewForm';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSpuOrdinaryAdd',
  components: { viewForm },
  data() {
    return { GOODS_COMBO_TYPES };
  },
};
</script>
