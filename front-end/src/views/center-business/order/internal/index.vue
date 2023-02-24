<template>
  <div class="app-container">
    <Page :business-type="ORDER_BUSINESS_TYPES.COMMON" />
  </div>
</template>
<script>
import Page from '@/views/center-business/order/internal/components/PageIndex.vue';

import { ORDER_BUSINESS_TYPES } from '@/utils/constants.js';
export default {
  name: 'BusinessOrderInternalIndex',
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
