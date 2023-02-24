<template>
  <div class="content">
    <h1>title：{{ title }}</h1>
    <h1>name：{{ name }}</h1>
    <h1>path：{{ fullPath }}</h1>
    <h1>tip：页面未完成</h1>
  </div>
</template>
<script>
export default {
  name: 'TestEmpty',
  computed: {
    name() {
      return this.$route.name;
    },
    title() {
      return this.$route.meta.title;
    },
    fullPath() {
      return this.$route.fullPath;
    },
  },
  created() {
    console.log(this.$route);
  },
};
</script>
<style scoped>
.content {
  text-align: left;
  padding: 4em;
}
</style>
