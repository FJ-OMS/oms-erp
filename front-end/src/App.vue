<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
import { Loading } from 'element-ui';
export default {
  name: 'App',
  data() {
    return {
      loadingInstance: null,
      loadingTimer: null,
    };
  },
  computed: {
    loadingCount() {
      return this.$store.getters.loadingCount;
    },
    loadingDelayed() {
      return this.$store.getters.loadingDelayed;
    },
  },
  watch: {
    loadingCount(n) {
      if (n > 0) {
        // 取消延时关闭的定时任务
        if (this.loadingTimer) {
          clearTimeout(this.loadingTimer);
          this.loadingTimer = null;
        }
        this.loadingInstance = Loading.service({ fullscreen: true });
      } else {
        // 以服务的方式调用的 Loading 需要异步关闭
        this.$nextTick(() => {
          // 延时关闭
          this.loadingTimer = setTimeout(() => {
            this.loadingInstance.close();
          }, this.loadingDelayed);
        });
      }
    },
    $route() {
      console.log('%c当前路由：' + this.$route.name + '', 'color:red');
    },
  },
  created() {
    // console.log('%c \nbiu~~ \nbiu~~ biu~~ \nbiu~~ biu~~ biu~~ \n\n', 'color:#f00');
    // console.log(process.env.NODE_ENV);
  },
};
</script>
