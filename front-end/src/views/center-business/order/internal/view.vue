<template>
  <div class="app-container">
    <ViewForm :business-type="ORDER_BUSINESS_TYPES.COMMON" />
  </div>
</template>
<script>
import ViewForm from '@/views/center-business/order/internal/components/ViewForm.vue';
import { ORDER_BUSINESS_TYPES } from '@/utils/constants.js';
export default {
  name: 'BusinessOrderInternalView',
  components: {
    ViewForm,
  },
  data() {
    return {
      ORDER_BUSINESS_TYPES,
    };
  },
};
</script>
