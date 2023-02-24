<template>
  <AddForm :is-add="true" :set-flag="setFlag" parent-name="BusinessGoodsSkuOrdinary" />
</template>
<script>
import AddForm from '../components/addForm.vue';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSkuOrdinaryAdd',
  components: {
    AddForm,
  },
  data() {
    return {};
  },
  computed: {
    setFlag() {
      return GOODS_COMBO_TYPES.ORDINARY;
    },
  },
  watch: {},

  mounted() {},
  methods: {},
};
</script>
