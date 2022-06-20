<template>
  <div class="app-container">
    <AddForm :business-type="ORDER_BUSINESS_TYPES.COMMON" />
  </div>
</template>
<script>
import AddForm from '@/views/center-business/order/third/components/AddForm.vue';
import { ORDER_BUSINESS_TYPES } from '@/utils/constants.js';
export default {
  name: 'BusinessOrderThirdAdd',
  components: {
    AddForm,
  },
  data() {
    return {
      ORDER_BUSINESS_TYPES,
    };
  },
};
</script>
