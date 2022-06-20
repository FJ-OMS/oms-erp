<template>
  <ViewForm :is-edit="true" :set-flag="setFlag" parent-name="BusinessGoodsSkuPackage" />
</template>
<script>
import ViewForm from '../components/ViewForm.vue';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
export default {
  name: 'BusinessGoodsSkuPackageEdit',
  components: {
    ViewForm,
  },
  data() {
    return {};
  },
  computed: {
    id() {
      return this.$route.query.id || '';
    },
    setFlag() {
      return GOODS_COMBO_TYPES.PACKAGE;
    },
  },
  watch: {},

  mounted() {},
  methods: {},
};
</script>
