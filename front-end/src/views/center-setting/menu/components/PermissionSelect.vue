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
  />
</template>
<script>
//
import TreeSelect from '@riophae/vue-treeselect';
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
import { userApi } from '@/api';
export default {
  name: 'PermissionSelect',
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
        this.tryRoot();
      },
      immediate: true,
    },
    id(v) {
      this.$emit('input', v);
    },
  },
  mounted() {
    userApi.getUserMenuTree().then(res => {
      this.treeData = res || [];
      this.tryRoot();
    });
  },
  methods: {
    tryRoot() {
      if (this.id === 'root' && this.treeData.length) {
        this.id = this.treeData[0].id;
      }
    },
    normalizer(node) {
      // 去掉children=[]的children属性
      const NODE = {
        id: node.id,
        label: node.title,
        children: node.children,
      };
      if (NODE.children && !NODE.children.length) {
        delete NODE.children;
      }
      if (!NODE.children && Object.prototype.hasOwnProperty.call(NODE, 'children')) {
        delete NODE.children;
      }
      return NODE;
    },
  },
};
</script>
<style lang="scss">
.vue-treeselect.bj-tree-select,
.bj-tree-select .vue-treeselect__control {
  height: 36px;
  width: 100%;
}

.el-form-item--mini {
  div.vue-treeselect.bj-tree-select {
    height: 28px;
    width: 100%;
    div.vue-treeselect__control {
      height: 28px;
      width: 100%;
    }
  }
}

.vue-treeselect.bj-tree-select.mini {
  height: 28px;
  width: 100%;
  font-size: 12px;
  .vue-treeselect__control,
  .vue-treeselect__value-container,
  .vue-treeselect__input-container,
  .vue-treeselect__placeholder,
  .vue-treeselect__input {
    height: 28px;
    width: 100%;
  }
  .vue-treeselect__placeholder,
  .vue-treeselect__input {
    line-height: 28px;
  }
}
</style>
