<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="公告标题" prop="title">
            <el-input
              v-model.trim="form.title"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="公告类型" prop="noticeTypeCode">
            <BjSelectLov
              v-model="form.noticeTypeCode"
              :disabled="isEdit"
              lov-code="HMSG.NOTICE.NOTICE_TYPE.CH"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布对象类型" prop="receiverTypeCode">
            <BjSelectLov
              v-model="form.receiverTypeCode"
              :disabled="isEdit"
              lov-code="HMSG.NOTICE.RECERVER_TYPE"
            />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="语言" prop="lang">
            <BjSelectLov v-model="form.lang" lov-code="BJ_LANGUAGE" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="有效日期从" prop="startDate">
            <el-date-picker
              v-model="form.startDate"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="有效日期至" prop="endDate">
            <el-date-picker
              v-model="form.endDate"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="上传附件" prop="attachmentUuid ">
            <BjUpload ref="upload" auto-upload @afterUpload="afterUpload" />

            <BjViewUrl v-if="form.attachmentUuid" :url="form.attachmentUuid" style="width: 100px" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="公告内容" prop="tinymceContent">
            <Tinymce
              v-model="tinymceContent"
              :toolbar="[
                'formatselect fontselect fontsizeselect bold italic underline strikethrough alignleft aligncenter alignright outdent indent  undo redo removeformat bullist numlist link image table fullscreen',
              ]"
              menubar=""
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
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { messageApi } from '@/api';
import BjUpload from '@/components/BjUpload/index';
import Tinymce from '@/components/Tinymce';
const getDefaultForm = () => {
  return {
    title: null,
    receiverTypeCode: null, // HMSG.NOTICE.RECERVER_TYPE
    // noticeCategoryCode: null,
    noticeTypeCode: null,
    lang: 'zh_CN',
    startDate: null,
    endDate: null,
    attachmentUuid: null,
    noticeContent: {
      noticeBody: '6666',
    },
    tenantId: 'default',
  };
};

export default {
  name: 'SettingSchedulerRequestAdd',
  components: {
    BjUpload,
    Tinymce,
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
        title: [{ required: true, message: '请输入', trigger: 'blur' }],
        receiverTypeCode: [{ required: true, message: '请选择', trigger: 'change' }],
        noticeTypeCode: [{ required: true, message: '请选择', trigger: 'change' }],
        lang: [{ required: true, message: '请选择', trigger: 'change' }],

        startDate: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      tinymceContent: '',
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
          this.tinymceContent = info.noticeContent && info.noticeContent.noticeBody;
        }
      },
    },
    tinymceContent(v) {
      this.form.noticeContent.noticeBody = v;
    },
  },

  mounted() {},
  methods: {
    afterUpload(src) {
      this.form.attachmentUuid = src;
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
      messageApi.updateNotices(API_DATA).then(res => {
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
      messageApi.addNotices(API_DATA).then(res => {
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
