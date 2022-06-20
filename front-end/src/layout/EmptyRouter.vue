<template>
  <keep-alive :include="cachedViews">
    <router-view :key="fullPath" />
  </keep-alive>
</template>

<script>
/**
 * 重要，keep-alive :include 要求的name数组其实是组件的name，不是路由的name
 * 所以，组件的name要与对应路由的name相同
 *
 * 废弃原来模块下index的路由，所有模块共用此路由
 */
export default {
  // 这个是组件的name
  name: 'EmptyRouter',
  computed: {
    cachedViews() {
      return (this.$store.state.tagsView.cachedViews || []).map(e => e.name);
    },
    fullPath() {
      return this.$route.fullPath;
    },
  },
  watch: {
    cachedViews(o, v) {
      console.log(o, v);
    },
  },
};
</script>

<style></style>
