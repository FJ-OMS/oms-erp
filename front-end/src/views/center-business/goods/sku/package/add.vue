<template>
  <AddForm :is-add="true" :set-flag="setFlag" parent-name="BusinessGoodsSkuPackage" />
</template>
<script>
import AddForm from '../components/addForm.vue';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSkuPackageAdd',
  components: {
    AddForm,
  },
  data() {
    return {};
  },
  computed: {
    setFlag() {
      return GOODS_COMBO_TYPES.PACKAGE;
    },
  },
  watch: {},

  mounted() {},
  methods: {},
};
</script>
