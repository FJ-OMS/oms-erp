<template>
  <ViewForm :is-edit="true" :info="info" @reFresh="reFresh" />
</template>
<script>
import { messageApi } from '@/api';
import ViewForm from './components/ViewForm.vue';
export default {
  name: 'SettingMessageNoticeEdit',
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
      messageApi.detailNotices({ noticeId: this.id }).then(res => {
        console.log(res);
        this.info = res;
      });
    },
  },
};
</script>
