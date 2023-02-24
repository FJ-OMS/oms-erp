<template>
  <div v-show="visible && !isLiked" class="hover-modal" @click.self="visible = false">
    <div class="hover-dialog">
      <p class="el-icon" @click="visible = false"><i class="el-icon-close" /></p>
      <div class="hover-content">
        <div v-for="(four, i) in item" :key="i" class="hover-col">
          <div v-for="(child, index) in four" :key="index" class="hover-item">
            <!-- 标题：二级菜单 -->
            <div class="title">
              {{ child.meta && child.meta.title }}
            </div>
            <!-- 列表1：没有第三级菜单，展示二级菜单-->
            <template
              v-if="
                !(
                  child.children &&
                  Array.isArray(child.children) &&
                  child.children.filter(e => !e.hidden).length
                )
              "
            >
              <div class="title-item hover">
                <Item
                  v-if="child.meta"
                  :right-icon="isLike(child.name) ? `el-icon-star-on` : `el-icon-star-off`"
                  :title="child.meta.title"
                  @titleClick="toName(child.name)"
                  @iconClick="handleClickIcon(e)"
                />
              </div>
            </template>
            <!-- 列表2：第三级菜单 -->
            <template v-for="(e, j) in (child.children || []).filter(e => !e.hidden)">
              <div
                :key="j"
                class="title-item hover"
                :class="e.component && e.component.name === 'TestEmpty' ? 'isEmpty' : ''"
              >
                <Item
                  v-if="e.meta"
                  :right-icon="isLike(e.name) ? `el-icon-star-on` : `el-icon-star-off`"
                  :title="e.meta.title"
                  @titleClick="toName(e.name)"
                  @iconClick="handleClickIcon(e)"
                />
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import path from 'path';
import { isExternal } from '@/utils/validate';
import Item from './Item';
export default {
  components: { Item },
  props: ['likedData', 'isLiked'],
  data() {
    return {
      code: 'myLikeMenuList',
      visible: false,
      item: null,
      myLikeMenu2RouteArray: [],
      labelsVersion: 11,
    };
  },
  mounted() {
    // 注册刷新事件
    this.$eventHub.$on('showSecondaryMenu', this.showSecondaryMenu);
  },
  methods: {
    showSecondaryMenu(item, name) {
      if (!item || !Array.isArray(item)) {
        this.visible = false;
        this.item = [];
        return;
      }
      // 过滤隐藏菜单后，再赋值
      let TEMP = item.filter(e => !e.hidden);
      if (TEMP.length) {
        this.visible = true;
        // 二级菜单只有一项，并且没有对应的三级菜单；
        if (TEMP.length === 1 && (!TEMP[0].children || !TEMP[0].children.length)) {
          this.visible = false;
          TEMP = [];
        }
      } else {
        this.visible = false;
        TEMP = [];
      }
      /** **** start 排序 ********** */
      // 子元素（三级菜单）长度，需要加上本身数组长度（因为二级菜单本身也占空间）
      const getMenuLength = TEMP => {
        return (
          TEMP.length +
          TEMP.reduce((pre, cur) => {
            return pre + (cur.children || []).length;
          }, 0)
        );
      };
      const sortByChildrenLengthFn = (TEMP, COLS_NUM = 4) => {
        // l： 二级菜单数组长度
        const l = TEMP.length;
        // 子元素（三级菜单）长度
        const cl = getMenuLength(TEMP);
        // 求平均值
        const MAX_LEN = cl / COLS_NUM;
        // 创建4列的空数组
        const NEW_TEMP = new Array(COLS_NUM).fill(0).map(() => []);
        let CLOS_INDEX = 0;
        for (let i = 0; i < l; i++) {
          const ITEM = TEMP[i];
          const OLD_ITEMS_LEN = getMenuLength(NEW_TEMP[CLOS_INDEX]);
          const THIS_ITEM_LEN = getMenuLength([ITEM]);
          const NEW_ITEMS_LEN = OLD_ITEMS_LEN + THIS_ITEM_LEN;

          let MIN_LEN_INDEX = false;
          //  再加一个菜单，子集数量超过了均值
          if (NEW_ITEMS_LEN > MAX_LEN) {
            const NEW_OVER_LEN = NEW_ITEMS_LEN - MAX_LEN; // 加上的话，超过部分所占空间
            const OLD_LESS_LEN = MAX_LEN - OLD_ITEMS_LEN; // 不加的话，不足部分所占空间
            // 对比 超过部分所占空间  不足部分所占空间
            if (NEW_OVER_LEN > OLD_LESS_LEN) {
              // 有剩余列，往下排
              if (CLOS_INDEX + 1 < COLS_NUM) {
                CLOS_INDEX++;
              } else {
                // 都排满了，找最短的列
                let MIN_LEN = 100;
                NEW_TEMP.forEach((column, index) => {
                  const LEN = getMenuLength(column);
                  if (LEN < MIN_LEN) {
                    MIN_LEN = LEN;
                    MIN_LEN_INDEX = index;
                  }
                });
              }
            }
          }
          if (MIN_LEN_INDEX === false) {
            NEW_TEMP[CLOS_INDEX].push(ITEM);
          } else {
            NEW_TEMP[MIN_LEN_INDEX].push(ITEM);
          }
        }
        return NEW_TEMP;
      };
      /** END */
      // 这里和二级路由的数量有关！！！
      this.item = [].concat(sortByChildrenLengthFn(TEMP));
    },

    resolvePath(routePath, basePath) {
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(basePath)) {
        return basePath;
      }
      // console.log(path.resolve(basePath, routePath));
      return path.resolve(basePath, routePath);
    },
    toName(name) {
      console.log('toName', name);
      this.$router.push({
        name: name,
      });
      this.visible = false;
    },
    handleClickIcon(route) {
      const name = route.name;
      console.log(route);
      // 原来是收藏状态
      if (this.isLike(name)) {
        // 点击为 去除收藏
        // this.myLikeMenu2RouteArray.remove(name, 'name');
        this.$store.commit('app/DELETE_SIDEBAR_LIKED_DATA', route);
      } else {
        // this.myLikeMenu2RouteArray.push({
        //   ...route,
        // });
        this.$store.commit('app/ADD_SIDEBAR_LIKED_DATA', route);
      }
      this.$nextTick(() => {
        this.$store.dispatch('app/saveLikeData');
      });
    },
    isLike(name) {
      return this.likedData.find(e => e.name == name);
    },
  },
};
</script>
<style lang="scss" scoped>
@import '~@/styles/variables.scss';
.hover-modal {
  width: calc(100% - #{$sideBarWidth});
  height: 100%;
  position: fixed;
  top: 0;
  left: $sideBarWidth;
  transform-origin: left center;
  z-index: 2001;
  background: rgba(0, 0, 0, 0.2);
}
.hideSidebar .hover-modal {
  left: $sideBarWidthHide;
  width: calc(100% - #{$sideBarWidthHide});
}
.hover-dialog {
  background: #ffffff;
  padding: 12px;
  color: #606266;
  line-height: 1.4;
  text-align: justify;
  font-size: 14px;
  // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  word-break: break-all;
  width: 800px;
  height: 100%;
  position: relative;
}

.hover-dialog > div {
  display: flex;
  flex-direction: row;
  width: 100%;
  flex-wrap: wrap;
}
.hover-dialog .hover-content {
  height: calc(100% - 30px);
  overflow-y: auto;
}
.hover-dialog .hover-content .hover-col {
  width: 25%;
}
.hover-dialog .hover-item {
  width: 100%;
  line-height: 2;
  margin-bottom: 16px;
  .title {
    font-weight: bold;
    font-size: 14px;
    color: $main-dark;
  }
  .title-item {
    font-size: 14px;
    line-height: 36px;
  }
  .title,
  .title-item {
    padding: 0 20px;
  }
}
.hover-dialog .hover-item:last-child {
  margin-bottom: 0;
}
.el-icon {
  text-align: right;
  padding: 0;
  margin: 0;
}
.el-icon-close {
  font-size: 24px;
  padding: 8px;
  cursor: pointer;
}
.pointer {
  cursor: pointer;
}
.hover:hover {
  color: $minor-dark;
  background-color: $background-dark;
}
.isEmpty.hover:hover {
  color: #c0c4cc;
  text-decoration: none;
}
.isEmpty.hover {
  color: #c0c4cc;
}
</style>
