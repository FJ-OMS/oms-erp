<template>
  <div class="app-container">
    <div class="dashboard">
      <BjKeyImage :url="require('@/assets/image/welcome.png')" style="width: 600px;" />
    </div>
    <!-- <h1>您好，{{ $store.getters.userInfo.realName }}</h1> -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
// import adminDashboard from './admin'
// import editorDashboard from './editor'

export default {
  name: 'Dashboard',
  // components: { adminDashboard, editorDashboard },
  data() {
    return {
      currentRole: 'adminDashboard',
    };
  },
  computed: {
    ...mapGetters(['roles']),
  },
  created() {
    if (!this.roles.includes('admin')) {
      this.currentRole = 'editorDashboard';
    }
  },
};
</script>
<style lang="scss" scoped>
.dashboard {
  text-align: center;
  padding-top: 100px;
}
</style>
