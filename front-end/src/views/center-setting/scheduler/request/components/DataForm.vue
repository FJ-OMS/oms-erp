<template>
  <div>
    <BjDrawerForm ref="BjDrawerForm" :form="form" :rules="rules" @handleConfirm="handleConfirm">
      <el-form-item label="租户" prop="tenantId">
        <BjSelectTenant v-model.trim="form.tenantId" clearable />
      </el-form-item>

      <el-form-item label="角色" prop="roleId">
        <!-- <el-select
                v-model.trim="form.roleId"
                class="full-width"
                placeholder="请选择"
                :disabled="isEdit"
                clearable
              >
                <el-option
                  v-for="(item, index) in EXECUTOR_TYPE_LIST"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select> -->

        <el-input
          v-model="form.roleName"
          :maxlength="MAX_LENGTH.DEFAULT"
          readonly
          placeholder="请选择"
          class="input-with-select"
        >
          <el-button slot="append" icon="el-icon-search" @click="onAddSelected" />
        </el-input>
      </el-form-item>

      <el-form-item label="有效期从" prop="startDate">
        <el-date-picker
          v-model="form.startDate"
          class="full-width"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="请选择"
        />
      </el-form-item>

      <el-form-item label="有效期至" prop="endDate">
        <el-date-picker
          v-model="form.endDate"
          class="full-width"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="请选择"
        />
      </el-form-item>

      <el-form-item label="限制次数" prop="limitQuantity">
        <el-input
          v-model.trim="form.limitQuantity"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>

      <el-form-item label="状态" prop="enabledFlag">
        <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
      </el-form-item>
    </BjDrawerForm>

    <BjDialogList ref="dialogList" :config="dialogListConfig" @confirm="onConfirmDialogList" />
  </div>
</template>
<script>
import { settingApi, roleApi } from '@/api';
const getDefaultForm = () => {
  return {
    tenantId: 'default',
    concurrentId: null,
    permissionId: null,
    roleId: null,
    roleName: null,
    limitQuantity: null,
    startDate: null,
    endDate: null,
    enabledFlag: null,
  };
};

export default {
  data() {
    return {
      dialogListConfig: {
        title: '选择角色',
        columns: [
          {
            prop: 'code',
            label: '角色编码',
          },
          {
            prop: 'name',
            label: '角色名称',
          },
        ],
        search: [
          {
            placeholder: '请输入角色名称',
            key: 'name',
          },
        ],
        actionApi: roleApi.getRoleList,
        query: '',
      },
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        orderSeq: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
    };
  },
  watch: {
    'form.limitQuantity'(v) {
      this.form.limitQuantity = Number(String(v).replace(/[^0-9]/g, ''));
    },
  },
  methods: {
    onAddSelected() {
      this.$refs.dialogList.show();
    },
    onConfirmDialogList(data) {
      const role = (data || [])[0];
      console.log(role);
      this.form.roleName = role.name;
      this.form.roleId = role.id;
      3;
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      //  this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑存储配置' : '新建存储配置');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm(formName) {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      API_DATA['concurrentId'] = this.$route.query.concurrentId || '';

      if (this.isEdit) {
        this.updateApi(API_DATA);
      } else {
        this.addApi(API_DATA);
      }
    },

    reFresh() {
      this.$emit('reFresh');
    },
    // 修改API
    updateApi(API_DATA) {
      settingApi.updateRequestPermissions(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      settingApi.addRequestPermissions(API_DATA).then(res => {
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
