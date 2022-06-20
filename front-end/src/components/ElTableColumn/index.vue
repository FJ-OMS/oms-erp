<template>
  <vxe-column
    :type="cptType"
    :field="prop"
    :title="label"
    :width="width"
    :min-width="minWidth"
    :fixed="cptFixed"
    :align="align"
    :class-name="className"
    :show-overflow="showOverflow"
    :formatter="formatter"
  >
    <template v-if="$scopedSlots.default" v-slot:default="prop">
      <slot
        name="default"
        :$table="prop.$table"
        :seq="prop.seq"
        :rowid="prop.rowid"
        :row="prop.row"
        :rowIndex="prop.rowIndex"
        :$rowIndex="prop.$rowIndex"
        :$index="prop.$rowIndex"
        :_rowIndex="prop._rowIndex"
        :column="prop.column"
        :columnIndex="prop.columnIndex"
        :$columnIndex="prop.$columnIndex"
        :_columnIndex="prop._columnIndex"
        :fixed="prop.fixed"
        :type="prop.type"
        :isHidden="prop.isHidden"
        :level="prop.level"
        :visibleData="prop.visibleData"
        :data="prop.data"
        :items="prop.items"
      />
    </template>
    <template v-if="$scopedSlots.header" v-slot:header="prop">
      <slot name="header" :prop="prop" />
    </template>
  </vxe-column>
</template>
<script>
/**
 * 将 vxe-column 包装成 el-column，用来兼容旧代码
 *
 * vxe-column API 文档
 * https://vxetable.cn/v3/#/column/api
 * Table-column Attributes
 * https://element.eleme.cn/#/zh-CN/component/table
 */
export default {
  name: 'ElTableColumn',
  props: {
    type: {
      type: String,
    },
    prop: {
      type: String,
    },
    label: {
      type: String,
    },
    width: {
      type: [String, Number],
    },
    minWidth: {
      type: [String, Number],
      default: '80px',
    },
    fixed: {
      type: [String, Boolean],
    },
    align: {
      type: String,
    },
    className: {
      type: String,
    },
    showOverflow: {
      type: [String, Boolean],
      default: 'tooltip',
    },
    formatter: Function,
  },
  computed: {
    cptFixed() {
      if (typeof this.fixed == 'boolean') {
        if (this.fixed) return 'left';
        return '';
      }
      return this.fixed;
    },
    cptType() {
      // selection/index/expand        -    el
      // seq  序号  checkbox  复选框 radio  单选框 expand  展开行 html HTML 标签（动态渲染任意 HTML 是非常危险的，很容易导致 XSS 攻击，应该确保内容是可信的）
      if (this.type == 'selection') {
        return 'checkbox';
      }
      if (this.type == 'index') {
        return 'seq';
      }
      return this.type;
    },
  },
  methods: {
    show() {
      console.log(...arguments, this);
    },
  },
};
</script>
