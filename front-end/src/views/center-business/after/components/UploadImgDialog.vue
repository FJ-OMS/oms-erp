<template>
  <el-dialog
    v-if="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="上传附件"
    :visible.sync="visible"
    width="85%"
  >
    <BjUpload
      ref="BjUpload"
      class="avatar-uploader"
      :limit="20"
      :limit-size="5 * 1024"
      multiple
      auto-upload
      show-file-list
      list-type="picture-card"
      accept="image/png,image/jpeg"
      :accept-list="['image/jpeg', 'image/png']"
      @afterUpload="afterUpload"
      @remove="onRemoveUrl"
    >
      <template slot="default">
        <i class="el-icon-plus avatar-uploader-icon" />
      </template>
      <template slot="tip">
        <!-- 可拖拽图片调整顺序, 支持PNG/JPG格式 -->
        可拖拽图片调整顺序,支持上传文件格式、尺寸根据各平台与定，大小不超过5M
      </template>
    </BjUpload>

    <span slot="footer" class="dialog-footer">
      <el-button @click="hide">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import BjUpload from '@/components/BjUpload/index';
export default {
  name: 'BusinessAfterThirdExchangeViewUploadImgDialog',
  components: {
    BjUpload,
  },
  data() {
    return {
      visible: false,
      imgs: [],
      rules: {},
    };
  },
  methods: {
    show(imgs) {
      this.visible = true;
      this.imgs = [...imgs];
      this.$nextTick(() => {
        // 获取地址
        const LIST = this.getImgListForUploadShow(this.imgs);
        this.$refs.BjUpload.setFileList(LIST);
      });
    },
    hide() {
      this.visible = false;
    },
    afterUpload(url) {
      // console.log('详情图上传返回', res);
      // this.imgs = (this.imgs || []).concat(res || []);
      this.imgs.push(url);
      const LIST = this.getImgListForUploadShow(this.imgs);
      this.$refs.BjUpload.setFileList(LIST);
    },
    onRemoveUrl(file) {
      this.imgs.splice(
        this.imgs.findIndex(e => e == file.url),
        1,
      );
    },
    confirm() {
      this.$confirm('确认修改附件？')
        .then(_ => {
          // this.$refs.form.validate(valid => {
          //   if (valid) {
          this.$emit('confirm', { accessory: this.imgs.join() });
          this.hide();
          //     this.imgs = [];
          //   } else {
          //     return false;
          //   }
          // });
        })
        .catch(_ => {});
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
  },
};
</script>

<style></style>
