<template>
  <el-dialog :close-on-click-modal="false" title="确认收货" :visible.sync="dialogVisible">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="上传凭证" prop="voucher">
        <BjUpload
          ref="BjUpload"
          class="avatar-uploader"
          :limit="20"
          :limit-size="5 * 1024"
          multiple
          show-file-list
          list-type="picture-card"
          accept="image/png,image/jpeg"
          :accept-list="['image/jpeg', 'image/png']"
          auto-upload
          limit-hide
          @afterUpload="afterUpload"
          @remove="onRemoveUrl"
        >
          <template slot="default">
            <i class="el-icon-plus avatar-uploader-icon" />
          </template>
          <template slot="tip">
            <!-- 支持上传多附件，非必填 -->
            <!-- 可拖拽图片调整顺序,支持上传文件格式、尺寸根据各平台与定，大小不超过5M -->
          </template>
        </BjUpload>
      </el-form-item>
      <el-form-item label="说明" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          placeholder="请输入"
          :maxlength="MAX_LENGTH.REMARK"
          show-word-limit
          clearable
          rows="5"
          required
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import BjUpload from '@/components/BjUpload/index';
export default {
  name: 'BusinessOrderInternalViewFormO2oConfirmReceipt',
  components: {
    BjUpload,
  },
  props: {
    id: {
      type: String,
      default: null,
    },
    info: {
      type: Object,
      default: function () {
        return {};
      },
    },
  },
  data() {
    return {
      formItemKey: null,
      // 弹窗显示
      dialogVisible: false,

      form: {
        orderId: null,
        voucher: null,
        description: null,
      },
      tempUrlList: [],
      rules: {
        // voucher: [{ required: true, message: '请输入买家备注', trigger: 'blur' }],
        // description: [{ required: true, message: '请输入客服备注', trigger: 'blur' }],
      },
    };
  },
  methods: {
    // 获取 上传的 新数据 并 合并到旧数据
    afterUpload(url) {
      this.tempUrlList.push(url);
      // const LIST = this.getImgListForUploadShow(this.form.refundInfoDTO.accessory);
      // this.$refs.BjUpload.setFileList(LIST);
    },
    onRemoveUrl(file) {
      this.tempUrlList.splice(
        this.tempUrlList.findIndex(e => e == file.url),
        1,
      );
    },
    onEdit() {
      this.dialogVisible = true;
    },
    confirm() {
      this.form.voucher = this.tempUrlList.join();
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('onO2oConfirmReceipt', this.form);
        } else {
          return false;
        }
      });
    },
  },
};
</script>
