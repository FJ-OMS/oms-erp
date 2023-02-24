<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="新建服务器配置项"
    :visible.sync="visible"
  >
    <el-form v-if="visible" ref="form" :model="form" :rules="rules">
      <el-form-item label="属性编码">
        <el-input v-model="form.propertyCode" :maxlength="MAX_LENGTH.DEFAULT" autocomplete="off" />
      </el-form-item>
      <el-form-item label="属性值">
        <el-input v-model="form.propertyValue" :maxlength="MAX_LENGTH.DEFAULT" autocomplete="off" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="hide">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      visible: false,
      form: {
        propertyValue: null,
        propertyCode: null,
      },
      rules: {
        propertyValue: [{ required: true, message: '请输入', trigger: 'blur' }],
        propertyCode: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
    };
  },
  methods: {
    confirm() {
      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.hide();
        this.$emit('confirm', { ...this.form });
      });
    },
    show() {
      this.visible = true;
      Object.keys(this.form).forEach(key => {
        this.form[key] = null;
      });
    },
    hide() {
      this.visible = false;
    },
  },
};
</script>
