<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { messageApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'SettingMessageSendEdit',
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
      messageApi.detailSend({ messageCode: this.id }).then(res => {
        console.log(res);
        if (!res.serverList || !res.serverList.length) {
          messageApi.detailLineSend({ messageCode: this.id }).then(res2 => {
            console.log(res2);
            this.info = {
              ...res,
              serverList: res2 || [],
            };
          });
        } else {
          this.info = res;
        }
      });
    },
  },
};
</script>
