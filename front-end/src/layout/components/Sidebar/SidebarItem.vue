<!--
      侧边栏，展示二、三级菜单
      @mouseover="onSidebarMenuAction(item)
      @click="onSidebarMenuAction(item)
       -->
<template>
  <div v-if="!item.hidden">
    <div
      v-if="
        hasOneShowingChild(item.children, item) &&
          (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
          !item.alwaysShow
      "
      @click="onSidebarMenuAction(item)"
    >
      <AppLink
        v-if="onlyOneChild.meta"
        :to="onlyOneChild.name ? { name: onlyOneChild.name } : resolvePath(onlyOneChild.path)"
      >
        <el-menu-item
          :index="onlyOneChild.name ? onlyOneChild.name : resolvePath(onlyOneChild.path)"
          :class="{ 'submenu-title-noDropdown': !isNest }"
        >
          <Item
            :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)"
            :title="onlyOneChild.meta.title"
          />
        </el-menu-item>
      </AppLink>
    </div>
    <template v-else>
      <div v-if="isShowChildrenByHover" @click="onSidebarMenuAction(item)">
        <el-menu-item
          :index="resolvePath(item.path)"
          :class="{ 'submenu-title-noDropdown': !isNest }"
        >
          <Item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
        </el-menu-item>
      </div>
      <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
        <template slot="title">
          <Item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
        </template>

        <sidebar-item
          v-for="child in item.children"
          :key="child.path"
          :is-nest="true"
          :item="child"
          :base-path="resolvePath(child.path)"
          class="nest-menu"
        />
      </el-submenu>
    </template>
  </div>
</template>

<script>
import path from 'path';
import { isExternal } from '@/utils/validate';
import Item from './Item';
import AppLink from './Link';
import FixiOSBug from './FixiOSBug';

export default {
  name: 'SidebarItem',
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    // route object
    item: {
      type: Object,
      required: true,
    },
    isNest: {
      type: Boolean,
      default: false,
    },
    basePath: {
      type: String,
      default: '',
    },
  },
  data() {
    // To fix https://github.com/PanJiaChen/vue-admin-template/issues/237
    // TODO: refactor with render function
    this.onlyOneChild = null;
    return {
      // isShowChildrenByHover: true,
    };
  },
  computed: {
    // true：鼠标在一级菜单上时，展示所有的二、三级菜单
    // false: 正常下拉展示 二、三级菜单
    isShowChildrenByHover() {
      return this.item.meta && this.item.meta.isShowChildrenByHover;
    },
  },
  methods: {
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false;
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item;
          return true;
        }
      });

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true;
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: '', noShowingChildren: true };
        return true;
      }

      return false;
    },
    resolvePath(routePath) {
      console.log(routePath);
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(this.basePath)) {
        return this.basePath;
      }
      return path.resolve(this.basePath, routePath);
    },
    onSidebarMenuAction(item) {
      // console.log('一级菜单：', item.name);
      // console.log('二级菜单：', item.children);
      this.$eventHub.$emit('showSecondaryMenu', item.children, item.name);
    },
  },
};
</script>
