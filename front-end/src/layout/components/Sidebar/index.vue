<template>
  <div :class="{ 'has-logo': showLogo }">
    <Logo v-if="showLogo" :collapse="isCollapse" :is-liked="isLiked" />
    <div class="toggle-sidebar-tool" @click="toggleSideBar">
      <i :class="isCollapse ? 'el-icon-arrow-right' : 'el-icon-arrow-left'"></i>
    </div>
    <el-scrollbar slot="reference" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <SidebarItem
          v-for="route in leftMenuRoutes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
    <!-- 仿阿里云菜单 -->
    <HoverItem :liked-data="LIKED_DATA" :is-liked="isLiked" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Logo from './Logo';
import SidebarItem from './SidebarItem';
import variables from '@/styles/variables.scss';
import HoverItem from './HoverItem.vue';
export default {
  components: { SidebarItem, Logo, HoverItem },
  computed: {
    ...mapGetters(['permission_routes', 'sidebar']),
    leftMenuRoutes() {
      if (this.isLiked) {
        console.log(this.LIKED_DATA);
        return this.LIKED_DATA;
      }
      return this.permission_routes;
    },
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }

      /** ******   Business  Setting  Old  **** */
      const rootRoute = (route.matched || [])[0];
      if (
        rootRoute &&
        (rootRoute.name === 'Business' || rootRoute.name === 'Setting' || rootRoute.name === 'Old')
      ) {
        return rootRoute.path;
      }
      /** ****  END ****** */
      return path;
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
    isLiked() {
      return this.sidebar.liked;
    },
    LIKED_DATA() {
      return this.sidebar.likedData;
    },
  },
  mounted() {
    this.$store.dispatch('app/getLikeData');
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar');
    },
  },
};
</script>
