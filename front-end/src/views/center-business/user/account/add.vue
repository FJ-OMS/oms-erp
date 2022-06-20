<template>
  <div class="app-container">
    <viewForm ref="viewForm" :is-edit="false" @submit="submit" />
  </div>
</template>
<script>
import { userApi } from '@/api';
import viewForm from './components/viewForm';
export default {
  name: 'BusinessUserAccountAdd',
  components: {
    viewForm,
  },
  data() {
    return {};
  },
  created() {},
  methods: {
    submit(data) {
      userApi
        .createUser({
          ...data,
        })
        .then(res => {
          if (res.realName) {
            const msg = ` ${res.realName} 的登录账户为 ${res.loginName}`;
            this.$alert(msg, '创建成功！', {
              confirmButtonText: '确定',
              callback: () => {
                this.$refs.viewForm.complete({ name: 'BusinessUserAccountIndex' });
              },
            });
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
  },
};
</script>
<style scoped></style>
