<template>
  <div class="app-container">
    <BjRouteTabs />
    <PageOrdinaryPackage set-flag="draft" />
  </div>
</template>
<script>
import PageOrdinaryPackage from '../components/PageOrdinaryPackage.vue';
export default {
  name: 'BusinessGoodsSkuDraftIndex',
  components: {
    PageOrdinaryPackage,
  },
  data() {
    return {};
  },
};
</script>
