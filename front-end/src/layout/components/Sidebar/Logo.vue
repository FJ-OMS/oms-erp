<template>
  <div class="sidebar-logo-container" :class="{ collapse: collapse }">
    <transition name="sidebarLogoFade">
      <div class="flex justify-between">
        <!-- <Hamburger
          id="sidebar-logo-toggle"
          :is-active="!collapse"
          width="54"
          height="50"
          class="sidebar-logo-toggle"
          @toggleClick="toggleSideBar"
        /> -->
        <div id="toggle-sidebar-like" class="toggle-sidebar-like" @click="toggleSideBarLike">
          <svg-icon v-if="isLiked" icon-class="more" />
          <i v-if="!isLiked" class="el-icon-star-on"></i>
        </div>
        <div class="sidebar-logo-img no-user-select" @click="toHome">
          <img :src="logo" />
        </div>
      </div>
      <!-- <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 v-else class="sidebar-title">{{ title }}</h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 class="sidebar-title">{{ title }}</h1>
      </router-link> -->
    </transition>
  </div>
</template>

<script>
// import Hamburger from '@/components/Hamburger';
export default {
  name: 'SidebarLogo',
  components: {
    // Hamburger,
  },
  props: {
    collapse: {
      type: Boolean,
      required: true,
    },
    isLiked: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      title: '飞骥管理系统',
      logo: require('@/assets/image/logo.png'),
    };
  },
  methods: {
    toggleSideBarLike() {
      this.$store.dispatch('app/toggleSideBarLike');
    },
    toHome() {
      this.$router.push({ name: 'Dashboard' });
    },
  },
};
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: width 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  width: 54px;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  // background: #2b2f3a;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 14px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}

.sidebar-logo-toggle {
  line-height: 46px;
  height: 100%;
  cursor: pointer;
  transition: background 0.3s;
  -webkit-tap-highlight-color: transparent;
  &:hover {
    background: minor-light; // rgba(0, 0, 0, 0.025);
  }
}
.toggle-sidebar-like {
  width: 54px;
  height: 50px;
  line-height: 50px;
  background-color: $main-dark;
  color: #fff;
  cursor: pointer;
  i,
  svg {
    font-size: 24px;
    vertical-align: middle;
    text-align: center;
    margin-right: 0 !important;
  }
}
.sidebar-logo-img {
  cursor: pointer;
  width: calc(#{$sideBarWidth} - 54px);
  height: 50px;
  img {
    vertical-align: middle;
    max-height: 40px;
  }
}
</style>
