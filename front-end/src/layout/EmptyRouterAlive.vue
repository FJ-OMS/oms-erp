<template>
  <div class="empty-router-alive">
    <!-- EmptyRouterAlive -->
    <keep-alive>
      <router-view />
    </keep-alive>
  </div>
</template>

<script>
export default {
  // 这个是组件的name
  name: 'EmptyRouterAlive',
};
</script>

<style lang="scss" scoped>
.app-main {
  .empty-router-alive {
    min-height: calc(100vh - 50px);
  }
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    .empty-router-alive {
      min-height: calc(100vh - 87px);
    }
  }
}
</style>
