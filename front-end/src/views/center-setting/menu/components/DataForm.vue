<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="父级ID" prop="parentId">
      <!-- <el-input v-model.trim="form.parentId" placeholder="不填为根目录" clearable /> -->

      <PermissionSelect v-model="form.parentId" placeholder="不选，默认为根目录" />
    </el-form-item>
    <el-form-item label="启用" prop="enabledFlag">
      <BjRadioLov v-model="form.enabledFlag" class="width-full" :option-list="STATUS_YES_TRUE" />
    </el-form-item>
    <el-form-item label="隐藏" prop="hiddenFlag">
      <BjRadioLov v-model="form.hiddenFlag" class="width-full" :option-list="STATUS_YES_TRUE" />
    </el-form-item>

    <el-form-item label="标题" prop="title">
      <el-input v-model.trim="form.title" placeholder="请输入" clearable />
    </el-form-item>

    <el-form-item label="编码" prop="code">
      <el-input v-model.trim="form.code" placeholder="请输入" clearable />
    </el-form-item>

    <el-form-item label="icon" prop="icon">
      <el-input v-model.trim="form.icon" placeholder="请输入" clearable />
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <BjInputNumber v-model.trim="form.sort" clearable />
    </el-form-item>
    <el-form-item label="权限类型" prop="permissionType">
      <BjRadioLov
        v-model="form.permissionType"
        class="width-full"
        :option-list="SETTING_PERMISSION_TYPE_LIST"
      />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import PermissionSelect from './PermissionSelect.vue';
import { SETTING_PERMISSION_TYPE_LIST, STATUS_YES_TRUE } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    parentId: 0,
    id: null,
    enabledFlag: true,
    hiddenFlag: false,

    code: null,
    icon: null,
    permissionType: 'api',
    sort: 0,
    title: null,
  };
};

export default {
  components: {
    PermissionSelect,
  },
  props: {
    API: Object,
  },
  data() {
    return {
      SETTING_PERMISSION_TYPE_LIST,
      STATUS_YES_TRUE,
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        // change,blur
        title: [{ required: true, message: '请输入', trigger: 'blur' }],
        code: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = info && info.id;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑' : '新建');

      this.form = Object.assign(getDefaultForm(), info || {});
      // if (this.isEdit) {
      // }
      this.$nextTick(() => {
        this.$refs.BjDrawerForm.$refs.form.clearValidate();
      });
    },

    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
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
      this.API.addMenuTree(API_DATA).then(res => {
        if (res.id || res._token || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      this.API.updateMenuTree(API_DATA).then(res => {
        if (res.id || res._token || Array.isArray(res) || res.failed === false) {
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
