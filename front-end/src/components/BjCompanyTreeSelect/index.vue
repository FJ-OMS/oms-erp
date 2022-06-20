<template>
  <TreeSelect
    ref="tree"
    v-model="id"
    :multiple="multiple"
    :options="treeData"
    no-results-text="暂无结果"
    clear-value-text="删除"
    :placeholder="placeholder"
    :normalizer="normalizer"
    :disabled="disabled"
    :always-open="alwaysOpen"
    :open-on-focus="true"
    clearable
    class="bj-tree-select"
    :class="[size]"
    @open="handleOpen"
  />
</template>
<script>
// import TreeSelect from '@riophae/vue-treeselect';
import TreeSelect from '@/components/BjTreeSelect/vue-treeselect.cjs'; // 源码修改 1356行   2022-1-25
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
import { channelApi } from '@/api';
export default {
  name: 'BjCompanyTreeSelect',
  components: {
    TreeSelect,
  },
  props: {
    placeholder: {
      type: String,
      default: '请选择',
    },
    size: {
      type: String,
      default: 'small',
    },
    value: {
      type: [String, Array],
      default: null,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    flag: {
      type: Boolean,
      default: true,
    },
    alwaysOpen: {
      type: Boolean,
      default: false,
    },
    // 默认展开所有选项
    expandAll: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      id: null,
      treeData: [
        {
          id: '=A5R_cnBFI66je2NoauxyIQ===',
          level: 0,
          name: '根目录',
          sonLevelCategory: [],
        },
      ],
      selectedLevel: false,
    };
  },
  watch: {
    value: {
      handler(v) {
        this.id = v;
        this.tryRoot();
      },
      immediate: true,
    },
    id(v) {
      this.$emit('input', v);
      this.$emit('change', v);

      try {
        //  //非el的输入组件，值变化时不会触发el-from-item的重新校验，需要手动触发el.form.change事件
        this.$refs.tree.$parent.$parent.$emit('el.form.change');
      } catch (error) {
        console.warn(error);
      }
    },
  },
  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      channelApi.getSalesOrganizationTree({ flag: this.flag }).then(res => {
        this.treeData = (res || []).map(e => {
          if (!e.children) {
            delete e.children;
          }
          if (e.children && !e.children.length) {
            delete e.children;
          }
          return e;
        });
        this.tryRoot();
      });
    },
    tryRoot() {
      // if (this.id === 'root' && this.treeData.length) {
      //   this.id = this.treeData[0].id;
      // }
    },
    normalizer(node) {
      // 去掉children=[]的children属性
      node['label'] = node.orgName;

      // console.log('node', node);

      if (this.expandAll) {
        node.isDefaultExpanded = true;
      }
      if (node.children && !node.children.length) {
        delete node.children;
      }
      if (!node.children && Object.prototype.hasOwnProperty.call(node, 'children')) {
        delete node.children;
      }
    },
    handleOpen() {
      this.$emit('open');
    },
  },
};
</script>
