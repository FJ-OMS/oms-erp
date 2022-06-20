<template>
  <el-form
    v-show="showSearch"
    ref="queryForm"
    :label-position="labelPosition"
    :label-suffix="labelSuffix"
    :model="queryParams"
    :rules="rules"
    :inline="inline"
    :label-width="labelWidth"
    :size="size"
    class="bj-query-form bj-query-form-for-cal-height"
  >
    <!--  独占一行 -->
    <template v-if="inline">
      <slot name="baseForm" />

      <slot v-if="showSeniorSearch" name="seniorForm" />

      <el-form-item v-if="'{}' !== JSON.stringify(queryParams) && !searchBtnHide" label="">
        <slot name="startBtnGroup" />
        <el-button :type="btnSearchStyle.type" plain @click="handleQuery">{{
          btnSearchStyle.text
        }}</el-button>

        <el-button v-if="$slots.seniorForm" type="cyan" plain @click="toggleSeniorSearch">{{
          showSeniorSearch ? '关闭高级查询' : '高级查询'
        }}</el-button>

        <el-button :type="btnResetStyle.type" plain @click="resetQuery">{{
          btnResetStyle.text
        }}</el-button>

        <slot name="endBtnGroup" />
      </el-form-item>
    </template>
    <!-- 有COL，计算宽度 -->
    <template v-else>
      <el-row :gutter="20">
        <slot name="baseForm" />

        <slot v-if="showSeniorSearch" name="seniorForm" />

        <slot name="beforeSearchBtn" />

        <el-col
          v-if="'{}' !== JSON.stringify(queryParams) && !searchBtnHide"
          :span="remainderSpan"
          style="margin-bottom: 18px; display: flex; justify-content: flex-end"
        >
          <slot name="startBtnGroup" />

          <el-button v-if="$slots.seniorForm" type="cyan" plain @click="toggleSeniorSearch">{{
            showSeniorSearch ? '关闭高级查询' : '高级查询'
          }}</el-button
          ><el-button :type="btnSearchStyle.type" plain @click="handleQuery">{{
            btnSearchStyle.text
          }}</el-button>

          <el-button v-if="!resetBtnHide" :type="btnResetStyle.type" plain @click="resetQuery">{{
            btnResetStyle.text
          }}</el-button>

          <slot name="endBtnGroup" />
        </el-col>
      </el-row>
    </template>
  </el-form>
</template>

<script>
export default {
  name: 'BjForm',
  props: {
    // 表单域标签的位置，如果值为 left 或者 right 时，则需要设置 label-width
    labelPosition: {
      type: String,
      default: 'left',
      validator: function (value) {
        return ['right', 'left', 'top'].indexOf !== -1;
      },
    },
    // 表单域标签的后缀
    labelSuffix: {
      type: String,
      default: '',
    },
    btnSearchStyle: {
      type: Object,
      default: () => {
        return { text: '搜索', type: 'primary', icon: 'el-icon-search' };
      },
    },
    btnResetStyle: {
      type: Object,
      default: () => {
        return { text: '重置', type: 'default', icon: 'el-icon-refresh' };
      },
    },

    /* 是否显示表单 */
    showSearch: {
      type: Boolean,
      default: true,
    },
    /* 表单数据对象 */
    queryParams: {
      type: Object,
      default: () => {
        return {};
      },
    },
    /** 表单校验对象 */
    rules: {
      type: Object,
      default: () => {
        return {};
      },
    },
    /* 行内表单模式 */
    inline: {
      type: Boolean,
      default: true,
    },
    /** 搜索重置按钮的宽度样式，会覆盖计算值 */
    searchBtnSpan: {
      type: Number,
      default: 0,
    },
    /** formItem的宽度样式 */
    colSpan: {
      type: Number,
      default: 6,
    },
    searchBtnHide: {
      type: Boolean,
      default: false,
    },
    resetBtnHide: { type: Boolean, default: false },
    /* 表单域标签的宽度，例如 '50px'。作为 Form 直接子元素的 form-item 会继承该值。支持 auto */
    labelWidth: {
      type: String,
      default: '100px',
    },
    size: {
      type: String,
      default: 'mini',
    },
  },
  data() {
    return {
      showSeniorSearch: null,
    };
  },
  computed: {
    // 根据slot(baseForm和seniorForm)所占的span，动态计算剩余的span宽度，作为搜索按钮的span
    remainderSpan() {
      // 有自定义的宽度prop
      if (this.searchBtnSpan) {
        return this.searchBtnSpan;
      }
      let cols = [];
      let remainder = 6; // 默认 col 的 span = 6;

      // 获取 插槽 baseForm 的el-col数量
      if (Array.isArray(this.$slots.baseForm)) {
        cols = cols.concat(
          this.$slots.baseForm.filter(VNode => VNode.tag && VNode.tag.includes('ElCol')),
        );

        // fix BjSelectChannelStore 组件内含有2个ElCol
        const BjSelectChannelStores = this.$slots.baseForm.filter(
          VNode => VNode.tag && VNode.tag.includes('BjSelectChannelStore'),
        );
        cols = cols.concat(new Array(BjSelectChannelStores.length * 2));
      }
      // 获取 插槽 seniorForm 的el-col数量，当 this.showSeniorSearch = true 时，加入计算量
      if (Array.isArray(this.$slots.seniorForm) && this.showSeniorSearch) {
        cols = cols.concat(
          this.$slots.seniorForm.filter(VNode => VNode.tag && VNode.tag.includes('ElCol')),
        );
        // fix BjSelectChannelStore 组件内含有2个ElCol
        const BjSelectChannelStores = this.$slots.baseForm.filter(
          VNode => VNode.tag && VNode.tag.includes('BjSelectChannelStore'),
        );
        cols = cols.concat(new Array(BjSelectChannelStores.length * 2));
      }
      // 计算余数，补齐到24的倍数作为一整行 （只是按colSpan计算，未深入各个元素的class:el-col-*，对于col的span值不完全相同的情况计算不准确，需要手动指定searchBtnSpan）
      if (cols.length) {
        remainder = 24 - ((this.colSpan * cols.length) % 24);
        remainder = remainder || 24;
      }

      console.log(
        `自动计算搜索按钮col span = 24 - (${this.colSpan} * ${cols.length}) % 24 = ${remainder}`,
      );

      return remainder;
    },
  },
  created() {},
  mounted() {},
  methods: {
    /* 搜索 */
    handleQuery() {
      this.$emit('handleQuery');
    },
    /** 开关显示高级搜索 */
    toggleSeniorSearch() {
      this.showSeniorSearch = !this.showSeniorSearch;
    },
    /* 重置搜索条件 */
    resetQuery() {
      this.resetForm('queryForm');
      this.$emit('resetQuery');
    },

    // 表单重置
    resetForm(formName = 'queryForm') {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
<style lang="scss">
// 两端对齐
// .bj-query-form {
//   .el-col {
//     .el-form-item__label {
//       padding-right: 8px;
//       text-align: justify;
//       text-align-last: justify; //https://caniuse.com/?search=text-align-last
//     }
//   }
// }
</style>
