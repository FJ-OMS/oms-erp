<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    width="800px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="22">
      <el-form-item label="素材名称" prop="materialName">
        <el-input
          v-model.trim="form.materialName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>

    <el-col :span="22">
      <BjFormItemCode v-model="form.code" label="素材编码" prop="code" />
    </el-col>

    <el-col :span="22">
      <el-form-item label="商品描述" prop="detailHtml">
        <el-input
          v-model.trim="form.detailHtml"
          type="textarea"
          show-word-limit
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
          resize="none"
          size="medium"
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model.trim="form.remark"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="主图" prop="mainImage">
        <BjUpload
          ref="uploadMain"
          class="avatar-uploader"
          show-file-list
          list-type="picture-card"
          :limit="10"
          :image-size="5 * 1024"
          :video-size="300 * 1024"
          accept="image/png,image/jpeg,video/*"
          multiple
          limit-hide
          @afterUpload="afterUploadMain"
          @handleFileListChange="handleFileListChangeMain"
        >
          <template slot="default">
            <i class="el-icon-plus avatar-uploader-icon" />
          </template>
          <template slot="tip">
            视频：最大支持1段视频，支持上传时长、格式根据各平台写定，大小不超过300M<br />
            图片：可拖拽图片调整顺序，至少上传一张，支持上传文件格式、尺寸根据各平台与定，大小不超过5M<br />
            最多上传10份（图片+视频）
          </template>
        </BjUpload>
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="详情图" prop="detailImage">
        <BjUpload
          ref="uploadDetail"
          class="avatar-uploader"
          :limit="20"
          :limit-size="5 * 1024"
          multiple
          show-file-list
          list-type="picture-card"
          accept="image/png,image/jpeg"
          limit-hide
          @afterUpload="afterUploadDetail"
          @handleFileListChange="handleFileListChangeDetail"
        >
          <template slot="default">
            <i class="el-icon-plus avatar-uploader-icon" />
          </template>
          <template slot="tip">
            可拖拽图片调整顺序,支持上传文件格式、尺寸根据各平台与定，大小不超过5M
          </template>
        </BjUpload>
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { goodsApi } from '@/api';

import BjUpload from '@/components/BjUpload/index';
const getDefaultForm = () => {
  return {
    code: null,
    detailHtml: null,
    detailImage: [],
    id: null,
    mainImage: [],
    materialName: null,
    remark: null,
  };
};

export default {
  components: {
    BjUpload,
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),

      // 表单校验
      rules: {
        materialName: [{ required: true, message: '请输入素材名称', trigger: 'blur' }],
      },

      afterUploadCount: 0,
    };
  },
  computed: {},
  watch: {
    afterUploadCount(v) {
      console.log('afterUploadCount', v);
      if (v >= 2) {
        this.toConfirmApi();
      }
    },
  },
  mounted() {},
  methods: {
    // 获取 旧数据的 变更
    handleFileListChangeMain(fileList) {
      const TEMP_FILE_LIST = fileList.filter(file => !file.raw);
      this.form.mainImage = TEMP_FILE_LIST.map(e => e.url);
      console.log('获取 旧数据的 变更', fileList);
    },
    // 获取 旧数据的 变更
    handleFileListChangeDetail(fileList) {
      const TEMP_FILE_LIST = fileList.filter(file => !file.raw);
      this.form.detailImage = TEMP_FILE_LIST.map(e => e.url);
    },
    // 获取 上传的 新数据 并 替换旧数据
    afterUploadMain(res) {
      console.log('主图上传返回', res);
      this.form.mainImage = (this.form.mainImage || []).concat(res || []);
      this.afterUploadCount++;
    },
    // 获取 上传的 新数据 并 合并到旧数据
    afterUploadDetail(res) {
      console.log('详情图上传返回', res);
      this.form.detailImage = (this.form.detailImage || []).concat(res || []);
      this.afterUploadCount++;
    },
    getImgListForUploadShow(list) {
      return (list || []).map(e => {
        return {
          isOld: true,
          name: e, // 原始url
          url: e,
        };
      });
    },
    show(info) {
      this.afterUploadCount = 0;
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑素材' : '新建素材');
      this.form = Object.assign(getDefaultForm(), info || {});
      // 等待窗口弹出后
      this.$nextTick(async () => {
        /** ******主图***** */
        // 获取地址
        const MAIN_LIST = this.getImgListForUploadShow(this.form.mainImage);
        // 设定上传组件（主图）的已有图片
        this.$refs.uploadMain.setFileList(MAIN_LIST);
        /** ******详情图***** */
        // 获取地址
        const DETAIL_LIST = this.getImgListForUploadShow(this.form.detailImage);
        // 设定上传组件（详情图）的已有图片
        this.$refs.uploadDetail.setFileList(DETAIL_LIST);
      });
    },
    handleClose() {
      this.$refs.BjDrawerForm.hide();
    },
    handleConfirm() {
      console.log('确认', this.form);
      this.afterUploadCount = 0;

      if (this.$refs.uploadMain.fileList.length > 0) {
        this.$refs.uploadMain.submitUpload();
      } else {
        this.$message.warning('至少上传一张主图');
        return;
      }

      if (this.$refs.uploadDetail.fileList.length > 0) {
        this.$refs.uploadDetail.submitUpload();
      } else {
        this.form.detailImage = [];
        this.afterUploadCount++;
      }
    },
    toConfirmApi() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      } else {
        delete API_DATA.id;
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
      goodsApi.addMaterial(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      goodsApi.updateMaterial(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style scoped>
.el-tag {
  margin: 6px;
}
</style>
