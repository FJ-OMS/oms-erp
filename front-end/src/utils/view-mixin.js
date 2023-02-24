/*
 * 通用混入viewForm 详情页面
 */
// import { commonApi } from '@/api';
export default {
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    isView: {
      type: Boolean,
      default: false,
    },
    isAdd: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      pageName: 'page_list_router_name',
    };
  },
  filters: {},
  computed: {},
  watch: {},
  created() {},
  mounted() {
    // if (document.querySelector('.resize-col')) {
    //   let oldWidth = document.getElementById('app').clientWidth;
    //   window.addEventListener('resize', () => {
    //     const WIDTH = document.getElementById('app').clientWidth;
    //     const isAdd = WIDTH > oldWidth;
    //     console.log(WIDTH);
    //     for (let i = 1; i <= 24; i++) {
    //       const THIS_CLASS = 'bj-item-col-' + i;
    //       const NEXT_I = isAdd ? i + 2 : i - 2;
    //       const NEXT_CLASS = 'bj-item-col-' + NEXT_I;
    //       const DOM = document.querySelector('.' + THIS_CLASS);
    //       if (DOM && NEXT_I > 1 && NEXT_I <= 24) {
    //         // TODO: 动态重置布局
    //         // if (WIDTH < 1400 && i > 7 && NEXT_I > 1 && NEXT_I <= 24) {
    //         //   DOM.classList.add(NEXT_CLASS);
    //         //   DOM.classList.remove(THIS_CLASS);
    //         // }
    //       }
    //     }
    //   });
    // }
  },
  beforeDestroy() {},
  methods: {
    jumper(ARG_ROUTER, GO_BY_NAME = false) {
      console.log(...arguments);
      const view = this.$route;
      let page = this.pageName;
      // 跳转到 route.name
      GO_BY_NAME = GO_BY_NAME === true;

      if (typeof ARG_ROUTER == 'string') {
        page = ARG_ROUTER;
      }
      if (ARG_ROUTER && typeof ARG_ROUTER.name == 'string') {
        page = ARG_ROUTER.name;
      }
      if (!GO_BY_NAME) this.$router.go(-1);
      this.$store.dispatch('tagsView/closeCurrentView', view).then(() => {
        // 目标路由 pageName
        //    1. 如果有被缓存，就调用 eventHub 的 刷新数据
        //    2. 如果没有被缓存，无需手动刷新。（防止多次API请求）
        const hasCached = this.$store.state.tagsView.cachedViews.map(e => e.name).includes(page);
        if (GO_BY_NAME) this.$router.push({ name: page }); // 【返回功能】 暂时使用router.go(-1)，不指定返回到router.name
        if (hasCached) {
          setTimeout(() => {
            this.$eventHub.$emit('refresh' + page, 1);
          }, 500);
        }
      });
    },
    // 完成后，按routerName跳转
    complete(ARG_ROUTER) {
      this.jumper(ARG_ROUTER, true);
    },
    // 取消/返回  上一页
    cancel(ARG_ROUTER) {
      this.jumper(ARG_ROUTER, false);
    },
    // 弹窗确定后，再取消/返回  上一页
    tryCancel(ARG_ROUTER, title = '当前页面数据未保存，是否放弃？') {
      this.$confirm(title, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.cancel(ARG_ROUTER);
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消',
          });
        });
    },
  },
};
