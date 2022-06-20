<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
        <span
          v-if="item.redirect === 'noRedirect' || index == levelList.length - 1"
          class="no-redirect"
          >{{ reverseTitle(item.meta.title) }}</span
        >
        <a v-else @click.prevent="handleLink(item, index)">{{ reverseTitle(item.meta.title) }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import pathToRegexp from 'path-to-regexp';

export default {
  data() {
    return {
      levelList: null,
    };
  },
  watch: {
    $route(route) {
      // if you go to the redirect page, do not update the breadcrumbs
      if (route.path.startsWith('/redirect/')) {
        return;
      }
      this.getBreadcrumb();
    },
  },
  created() {
    this.getBreadcrumb();
  },
  methods: {
    reverseTitle(str) {
      return str
        .split('-')
        .reverse()
        .join('-');
    },
    getName(routes, name) {
      // console.log(routes, name);
      if (!Array.isArray(routes)) {
        return false;
      }
      let finder = false;
      routes.forEach(route => {
        if (route.name === name) {
          finder = route;
        } else {
          if (!finder) finder = this.getName(route.children, name);
        }
      });
      return finder;
    },
    getBreadcrumb() {
      // only show routes with meta.title
      const matched = this.$route.matched.filter(item => item.meta && item.meta.title);
      const first = matched[0];

      if (!this.isDashboard(first)) {
        // matched = [{ path: '/dashboard', meta: { title: '首页' }}].concat(matched);
      }

      this.levelList = matched.filter(
        item => item.meta && item.meta.title && item.meta.breadcrumb !== false,
      );
      // 面包屑：如果最后一级title与上一级重复，则删除就后一个
      const LEVEL_COUNT = this.levelList.length;
      if (LEVEL_COUNT >= 2) {
        if (
          this.levelList[LEVEL_COUNT - 1].meta.title == this.levelList[LEVEL_COUNT - 2].meta.title
        ) {
          this.levelList.splice(-1, 1);
        }
      }
      // console.log(this.levelList);
    },
    isDashboard(route) {
      const name = route && route.name;
      if (!name) {
        return false;
      }
      return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase();
    },
    pathCompile(path) {
      // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route;
      var toPath = pathToRegexp.compile(path);
      return toPath(params);
    },
    handleLink(item, index) {
      /**
       * TODO:点击面包屑事件
       *          先判断当前菜单级别
                      一级菜单：调用左侧菜单栏
                      二级菜单：弹出三级菜单
                      三级菜单：跳转默认页面
       *
       */

      const finder = this.getName(this.$store.state.permission.routes, item.name);
      // console.log(finder);
      let tempList = null;
      if (finder && Array.isArray(finder.children)) {
        tempList = (finder.children || []).filter(e => !e.hidden);
        // console.log(tempList);
      }

      if (index === 0) {
        // 调用左侧菜单栏
        this.$eventHub.$emit('showSecondaryMenu', tempList);
      }
      if (index === 1) {
        // 弹出三级菜单 （并过滤子集，防止btn功能加载到菜单）
        tempList = tempList.map(e => {
          return { ...e, children: null };
        });
        this.$eventHub.$emit('showSecondaryMenu', tempList);
      }
      // 三级菜单：跳转默认页面
      if (index === 2 && finder.children) {
        const path = finder.redirect.replace(finder.path + '/', '');
        const name = tempList.find(e => e.path === path).name;
        this.$router.push({ name });
      }

      // return;
      // const { redirect, path } = item;
      // if (redirect) {
      //   this.$router.push(redirect);
      //   return;
      // }
      // this.$router.push(this.pathCompile(path));
    },
  },
};
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 12px;
  height: 30px;
  line-height: 30px;
  margin-left: 25px;
  width: 100%;
  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
