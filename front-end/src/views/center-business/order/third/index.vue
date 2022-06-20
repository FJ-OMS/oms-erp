<template>
  <div class="app-container">
    <Page :business-type="ORDER_BUSINESS_TYPES.COMMON" />
  </div>
</template>
<script>
import Page from '@/views/center-business/order/third/components/PageIndex.vue';
import { ORDER_BUSINESS_TYPES } from '@/utils/constants.js';
export default {
  name: 'BusinessOrderThirdIndex',
  components: {
    Page,
  },
  data() {
    return {
      ORDER_BUSINESS_TYPES,
    };
  },
};
</script>
