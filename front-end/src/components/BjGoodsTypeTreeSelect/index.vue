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
    clearable
    class="bj-tree-select"
    :class="[size]"
    :flat="flat"
    :limit="limit"
    :limit-text="limitText"
    :disable-branch-nodes="onlyLeaf"
  />
</template>
<script>
//
// import TreeSelect from '@riophae/vue-treeselect';
import TreeSelect from '@/components/BjTreeSelect/vue-treeselect.cjs'; // 源码修改 1356行   2022-1-25
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
import { commonApi } from '@/api';
export default {
  name: 'BjGoodsTypeTreeSelect',
  components: {
    TreeSelect,
  },
  props: {
    // 限制所选选项的显示。其余的将隐藏在limitText 字符串中。
    limit: {
      type: Number,
      default: 2, // Infinity
    },
    // 所选元素超过定义的限制时处理显示的消息的功能。
    limitText: {
      type: Function,
      default: count => `+${count}`,
    },
    // 前端增加根目录节点；默认false，不需要添加根目录
    hasRoot: {
      type: Boolean,
      default: false,
    },
    // 平面模式: 分支节点和叶子节点不会相互影响
    flat: {
      type: Boolean,
      default: false,
    },
    // 只能选择叶子节点（node.subFlag=true时才能选中），需要在flat=true下使用
    onlyLeaf: {
      type: Boolean,
      default: false,
    },
    // 默认展开所有选项
    expandAll: {
      type: Boolean,
      default: false,
    },
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
    /** 接口参数 */
    query: {
      type: Object,
      default: () => {
        return {};
      },
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      id: null,
      treeData: [],
      selectedLevel: false,
    };
  },
  watch: {
    value: {
      handler(v) {
        this.id = v;
        // this.tryRoot();
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
    if (this.hasRoot) {
      this.treeData.push({
        id: 'root',
        level: 0,
        name: '根目录',
        children: [],
      });
    }
    this.getData();
  },
  methods: {
    getData() {
      const API_DATA = this.onlyLeaf
        ? Object.assign({ removeNotSubFlag: true }, this.query)
        : Object.assign({}, this.query);

      commonApi.getAllCategory(API_DATA).then(res => {
        // 是否显示根节点
        if (this.hasRoot) {
          this.treeData[0].children = res || [];
        } else {
          this.treeData = res || [];
        }
        // this.tryRoot();
      });
    },
    // tryRoot() {
    //   if (this.id === 'root' && this.treeData.length) {
    //     this.id = this.treeData[0].id;
    //   }
    // },
    normalizer(node) {
      // 去掉children=[]的children属性
      node['label'] = node.name;

      if (this.expandAll) {
        node.isDefaultExpanded = true;
      }
      if (node.children && !node.children.length && node.level != 0) {
        console.log(node);
        delete node.children;
      }
      if (!node.children && Object.prototype.hasOwnProperty.call(node, 'children')) {
        delete node.children;
      }
    },
  },
};
</script>
