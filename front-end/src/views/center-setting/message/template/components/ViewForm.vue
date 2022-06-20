<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="消息模板编码" prop="templateCode">
            <el-input
              v-model.trim="form.templateCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              :disabled="isEdit"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="消息模板名称" prop="templateName">
            <el-input
              v-model.trim="form.templateName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="消息模板标题" prop="templateTitle">
            <el-input
              v-model.trim="form.templateTitle"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="语言" prop="lang">
            <BjSelectLov v-model="form.lang" lov-code="BJ_LANGUAGE" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="外部编码" prop="externalCode">
            <el-input
              v-model.trim="form.externalCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="消息类型" prop="messageSubcategoryCode">
            <BjSelectLov
              v-model="form.messageSubcategoryCode"
              lov-code="HMSG.MESSAGE_SUBCATEGORY"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="内容编辑器" prop="enabledFlag">
            <el-radio-group v-model="templateContentType">
              <el-radio :label="0">简单文本</el-radio>
              <el-radio :label="1">富文本</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="启用" prop="enabledFlag">
            <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="消息模板内容" prop="templateContent">
            <el-button v-show="isEdit" type="primary" @click="setArg">参数模板</el-button>
            <Tinymce
              v-if="templateContentType"
              v-model="form.templateContent"
              :toolbar="[
                'formatselect fontselect fontsizeselect bold italic underline strikethrough alignleft aligncenter alignright outdent indent  undo redo removeformat bullist numlist link image table fullscreen',
              ]"
              menubar=""
            />
            <el-input
              v-if="!templateContentType"
              v-model="form.templateContent"
              :maxlength="MAX_LENGTH.DEFAULT"
              type="textarea"
              :autosize="{ minRows: 10, maxRows: 20 }"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider />
    <span slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
    </span>

    <DrawerTable ref="DrawerTable" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { messageApi } from '@/api';
import Tinymce from '@/components/Tinymce';
import DrawerTable from './DrawerTable.vue';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    enabledFlag: 1,
    externalCode: null,
    lang: null,
    messageCategoryCode: null,
    messageSubcategoryCode: null,
    sqlValue: null, // 取值SQL
    templateCode: null,
    templateContent: null,
    templateId: null,
    templateName: null,
    templateTitle: null,
    tenantId: 'default',
  };
};

export default {
  name: 'SettingSchedulerRequestAdd',
  components: {
    Tinymce,
    DrawerTable,
  },
  mixins: [viewMiXin],
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      form: getDefaultForm(),
      // 表单校验
      rules: {
        templateCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        templateName: [{ required: true, message: '请输入', trigger: 'blur' }],
        templateTitle: [{ required: true, message: '请输入', trigger: 'blur' }],
        lang: [{ required: true, message: '请选择', trigger: 'change' }],
        templateContent: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
      templateContentType: 0,
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
        }
      },
    },
  },

  mounted() {},
  methods: {
    setArg() {
      this.$refs.DrawerTable.show(this.$lodash.clone(this.form));
      this.$refs.DrawerTable.setTitle('参数模板');
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      console.log(API_DATA);
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      messageApi.updateTemplate(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      messageApi.addTemplate(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
  },
};
</script>
