<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { goodsApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'BusinessGoodsOtherInfoEdit',
  components: {
    ViewForm,
  },
  data() {
    return {
      info: null,
    };
  },
  computed: {
    id() {
      return this.$route.query.id || '';
    },
  },
  watch: {},

  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      goodsApi.detailsOtherInfo({ id: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
