<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item v-for="(item, i) in columns" :key="i" :label="item.label" :prop="item.prop">
      <el-input
        v-model.trim="form[item.prop]"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
const getDefaultForm = () => {
  return {};
};

export default {
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      columns: [],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      this.isBjGoodsTypeTreeSelectVisible = true;
      this.isParentIdDisabled = false;
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑' : '新建');
      this.columns = this.$lodash.clone(info);
      info.forEach(element => {
        this.$set(this.form, element.prop, '');
      });
    },

    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }

      this.$refs.BjDrawerForm.hide();
      this.$emit('handleDataFormConfirm', API_DATA);
    },
    reFresh() {
      this.$emit('reFresh');
    },
  },
};
</script>
