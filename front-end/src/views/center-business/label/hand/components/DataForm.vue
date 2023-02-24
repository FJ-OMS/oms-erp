<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    class="bj-label-colon"
    :form="form"
    :rules="rules"
    width="628px"
    @handleConfirm="handleConfirm"
  >
    <BjDivider>基础信息</BjDivider>
    <el-row>
      <el-col :span="21">
        <el-form-item label="标签名称" prop="labelName">
          <el-input
            v-model.trim="form.labelName"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="21">
        <el-form-item label="标签类型" prop="labelType">
          <BjSelectLov
            v-model="form.labelType"
            :disabled="isEdit"
            :option-list="LABEL_SYS_TYPES_STRING"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <BjDivider>标签样式</BjDivider>
    <el-row>
      <el-col :span="21">
        <el-form-item label="文字内容" prop="textContent">
          <el-input
            v-model.trim="form.textContent"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请控制文字在6字以内"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="21" class="bj-tag-color-20211104">
        <el-form-item label="选择颜色" prop="textColor">
          <BjRadioLov v-model="form.textColor" :option-list="LABEL_HAND_COLORS">
            <template #default="{ item }">
              <BjTag :color="item.value" class="tag-color" /> </template
          ></BjRadioLov>
        </el-form-item>
      </el-col>

      <el-col :span="21">
        <el-form-item label="标签预览" prop="yulan">
          <BjTag :color="form.textColor" :text="form.textContent" />
        </el-form-item>
      </el-col>
    </el-row>
  </BjDrawerForm>
</template>
<script>
import { LABEL_HAND_COLORS, LABEL_SYS_TYPES } from '@/utils/constants';

const LABEL_SYS_TYPES_STRING = LABEL_SYS_TYPES.map(e => {
  const { label, value } = e;
  return {
    label,
    value: String(value),
  };
});
import { tagsApi } from '@/api';
const getDefaultForm = () => {
  return {
    id: null,

    systemLabelFlag: false,
    changeFlag: true,
    status: true,

    moduleCode: null,

    labelCode: null,
    labelName: null,
    labelType: null,

    textColor: 'orange',
    textContent: null,
  };
};

export default {
  components: {},
  data() {
    return {
      LABEL_HAND_COLORS,
      LABEL_SYS_TYPES_STRING,
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        labelCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        labelName: [{ required: true, message: '请输入', trigger: 'blur' }],
        labelType: [{ required: true, message: '请选择', trigger: 'change' }],
        status: [{ required: true, message: '请选择', trigger: 'change' }],
        textContent: [{ required: true, message: '请输入', trigger: 'blur' }],
        textColor: [{ required: true, message: '请选择', trigger: 'change' }],
      },
    };
  },
  computed: {},
  watch: {
    // 'form.orderSeq'(v) {
    //   this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    // },
  },

  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑标签' : '新建标签');
      this.form = Object.assign(getDefaultForm(), info || {});
    },
    handleConfirm() {
      console.log('确认', this.form);
      const API_DATA = this.$lodash.clone(this.form);

      if (this.isEdit) {
        this.updateApi(API_DATA);
      } else {
        this.addApi(API_DATA);
      }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    addApi(API_DATA) {
      tagsApi.addLabels(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      tagsApi.updateLabels(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style>
.bj-tag-color-20211104 .tag-color {
  display: inline-block;
  width: 16px;
  height: 16px;
  position: absolute;
}
.bj-tag-color-20211104 .el-radio__label {
  padding-right: 20px;
  padding-left: 6px;
}

.bj-tag-color-20211104 .el-radio {
  margin-bottom: 10px;
}
</style>
