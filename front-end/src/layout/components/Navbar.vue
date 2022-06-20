<template>
  <div class="navbar">
    <!-- <hamburger
      id="hamburger-container"
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    /> -->

    <div class="navbar-tags-view">
      <TagsView v-if="needTagsView" />
    </div>

    <!-- <Breadcrumb id="breadcrumb-container" class="breadcrumb-container" /> -->

    <div class="right-menu">
      <!-- <template v-if="device !== 'mobile'"> -->
      <!-- <search id="header-search" class="right-menu-item" /> -->

      <!-- <error-log class="errLog-container right-menu-item hover-effect" /> -->

      <!-- <screenfull id="screenfull" class="right-menu-item hover-effect" /> -->

      <!-- <el-tooltip content="Global Size" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip> -->
      <!-- </template> -->

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <BjKeyImage
            class="user-avatar"
            :url="avatar || require('@/assets/image/logo-circle.png')"
          />
          <!-- <span>{{ name }}</span> -->
        </div>
        <el-dropdown-menu slot="dropdown">
          <!-- <router-link to="/profile/index">
            <el-dropdown-item>Profile</el-dropdown-item>
          </router-link> -->
          <!-- <router-link to="/">
            <el-dropdown-item>首页</el-dropdown-item>
          </router-link> -->
          <el-dropdown-item>
            <span class="role-name">{{ userInfo.currentRoleName }}</span>
            <span style="display:block;">账号：{{ userInfo.loginName }}</span>
          </el-dropdown-item>
          <el-dropdown-item
            class="el-dropdown-menu__item--divided"
            @click.native="$toPage('CommonPersonal')"
            ><svg-icon icon-class="user" />个人中心</el-dropdown-item
          >
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;"><svg-icon icon-class="logout" />退出系统</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex';
// import Breadcrumb from '@/components/Breadcrumb';
// import Hamburger from '@/components/Hamburger';
// import ErrorLog from '@/components/ErrorLog';
// import Screenfull from '@/components/Screenfull';
// import SizeSelect from '@/components/SizeSelect'
// import Search from '@/components/HeaderSearch'

import TagsView from './TagsView';

export default {
  components: {
    // Breadcrumb,
    // // Hamburger,
    // ErrorLog,
    // Screenfull,
    // SizeSelect,
    // Search

    TagsView,
  },
  computed: {
    ...mapGetters(['sidebar', 'avatar', 'device', 'name', 'userInfo']),
    ...mapState({ needTagsView: state => state.settings.tagsView }),
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar');
    },
    async logout() {
      await this.$store.dispatch('user/logout');
      sessionStorage.removeItem('bjWelcomeDialogVisible');
    },
  },
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  // overflow: hidden;
  position: relative;
  background: #fff;
  // box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  background: $background-dark;
  .navbar-tags-view {
    width: calc(100% - 96px);
    float: left;
    margin-top: 18px;
  }

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    width: 96px;
    height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      // margin-right: 15px;
      line-height: 50px;
      display: flex;
      justify-content: center;

      .avatar-wrapper {
        position: relative;
        display: flex;
        align-items: center;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 50%;
          // margin-right: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.el-dropdown-menu svg {
  margin-right: 5px;
  font-size: 12px;
}
.role-name {
  color: #333333;
  font-weight: 700;
  font-size: 12px;
}
.el-dropdown-menu__item--divided {
  position: relative;
  margin-top: 6px;
  border-top: 1px solid #e6ebf5;
}
</style>
