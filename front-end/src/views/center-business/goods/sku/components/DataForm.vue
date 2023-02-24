<!--  批量设置SKU的属性 ----->
<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    :label-width="labelWidth"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="空值处理">
      <el-radio-group v-model="ignoreEmpty">
        <el-radio :label="true">空值不覆盖</el-radio>
        <el-radio :label="false">空值覆盖</el-radio>
      </el-radio-group>
    </el-form-item>

    <BjDivider></BjDivider>
    <template v-for="(item, i) in columns">
      <el-form-item
        v-if="!(isPackAgeSku && item.prop == 'costPrice')"
        :key="i"
        :label="item.label"
        :prop="item.prop"
      >
        <AttributeInput
          v-model="form[item.prop]"
          :is-price="item.prop.includes('Price')"
          select
          :type="item.formType"
          :col-prop="item.prop"
          :value-list="item.attributeValueList"
        />
      </el-form-item>
    </template>
  </BjDrawerForm>
</template>
<script>
import AttributeInput from './AttributeInput.vue';
const getDefaultForm = () => {
  return {};
};

export default {
  components: {
    AttributeInput,
  },
  props: {
    labelWidth: {
      type: String,
      default: '120px',
    },
    isPackAgeSku: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      columns: [],
      ignoreEmpty: true,
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
      this.$refs.BjDrawerForm.setTitle('批量设置');
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
      this.$emit('handleDataFormConfirm', API_DATA, this.ignoreEmpty);
    },
    reFresh() {
      this.$emit('reFresh');
    },
  },
};
</script>
