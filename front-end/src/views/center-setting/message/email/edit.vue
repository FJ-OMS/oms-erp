<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { messageApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'SettingMessageEmailEdit',
  components: {
    ViewForm,
  },
  data() {
    return {
      info: null,
    };
  },
  computed: {
    serverId() {
      return this.$route.query.serverId || '';
    },
  },
  watch: {},

  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      messageApi.detailEmail({ serverId: this.serverId }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
