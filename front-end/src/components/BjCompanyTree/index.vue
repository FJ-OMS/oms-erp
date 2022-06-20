<template>
  <el-tree
    ref="tree"
    :data="data"
    show-checkbox
    :default-expand-all="defaultExpandAll"
    :default-checked-keys="defaultCheckedKeys"
    empty-text="暂无数据"
    node-key="id"
    highlight-current
    :props="defaultProps"
    @check="checkTree"
  >
  </el-tree>
</template>

<script>
import { channelApi } from '@/api';
export default {
  props: {
    // 默认勾选的节点的 key 的数组
    defaultCheckedKeys: {
      type: Array,
      default: () => {
        return [];
      },
    },
    // 是否默认展开所有节点
    defaultExpandAll: {
      type: Boolean,
      default: false,
    },
    flag: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      data: [],
      defaultProps: {
        children: 'children',
        label: 'orgName',
      },
      childrenIds: [],
    };
  },
  watch: {
    defaultCheckedKeys: {
      handler(v) {
        const ids = [];
        this.defaultCheckedKeys.forEach(id => {
          if (this.childrenIds.indexOf(id) !== -1) {
            ids.push(id);
          }
        });
        this.$nextTick(() => {
          this.$refs.tree.setCheckedKeys(ids);
        });
      },
      immediate: true,
      deep: true,
    },
  },
  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      channelApi.getSalesOrganizationTree({ flag: this.flag }).then(res => {
        this.data = (res || []).map(e => {
          if (!e.children) {
            delete e.children;
          }
          if (e.children && !e.children.length) {
            delete e.children;
          }
          return e;
        });
        this.filterData(this.data);
      });
    },
    checkTree(data, Node) {
      this.$emit('check', data, Node);
    },
    /** 清空 */
    resetChecked() {
      this.$refs.tree.setCheckedKeys([]);
    },
    /**
     * 筛选出数据的最后一层id
     */
    filterData(list) {
      list.forEach(item => {
        if (item.children === undefined || item.children.length === 0) {
          this.childrenIds.push(item.id);
        } else {
          this.filterData(item.children);
        }
      });
    },
  },
};
</script>

<style></style>
