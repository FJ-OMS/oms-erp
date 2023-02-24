<!--
 * @Author: your name
 * @Date: 2021-09-30 17:50:43
 * @LastEditTime: 2021-10-04 11:23:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\packages\BjUpload\index.vue
-->
<template>
  <div>
    <el-upload
      :action="action"
      :multiple="multiple"
      :data="data"
      :name="name"
      :show-file-list="showFileList"
      :drag="drag"
      :list-type="listType"
      :auto-upload="autoUpload"
      :file-list="fileList"
      :disabled="disabled"
      :limit="limit"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :on-exceed="onExceed"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'BjUpload',
  props: {
    /** 必选参数，上传的地址 */
    action: {
      type: String,
      default: 'https://jsonplaceholder.typicode.com/posts/',
    },
    /** 上传类型 */
    type: {
      type: Array,
      default: () => {
        return ['jpeg', 'jpg', 'png'];
      },
    },
    /** 限制上传大小，默认单位M */
    size: {
      type: Number,
      default: 2,
    },
    /** 是否支持多选文件 */
    multiple: {
      type: Boolean,
      default: null,
    },
    /** 上传时附带的额外参数 */
    data: {
      type: Object,
      default: () => {
        return {};
      },
    },
    /** 上传的文件字段名 */
    name: {
      type: String,
      default: 'file',
    },
    /** 是否显示已上传文件列表 */
    showFileList: {
      type: Boolean,
      default: true,
    },
    /** 是否启用拖拽上传 */
    drag: {
      type: Boolean,
      default: false,
    },
    /** 文件列表的类型 text/picture/picture-card */
    listType: {
      type: String,
      default: 'text',
    },
    /** 是否在选取文件后立即进行上传 */
    autoUpload: {
      type: Boolean,
      default: true,
    },
    /** 上传的文件列表, 例如: [{name: 'food.jpg', url: 'https://xxx.cdn.com/xxx.jpg'}] */
    fileList: {
      type: Array,
      default: () => {
        return [];
      },
    },
    /** 是否禁用 */
    disabled: {
      type: Boolean,
      default: false,
    },
    /** 最大允许上传个数 */
    limit: {
      type: Number,
      default: null,
    },
    /** 文件超出个数限制时的钩子 */
    onExceed: {
      type: Function,
    },
  },
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.$emit('success', res, file);
    },
    beforeAvatarUpload(file) {
      console.log(file);
      const type = file.type.split('/')[1];
      const isLt = file.size / 1024 / 1024 < this.size;

      if (this.type.indexOf(type) === -1) {
        this.msgError('上传图片格式不正确，请重新上传');
      }
      if (!isLt) {
        this.msgError(`上传头像图片大小不能超过 ${this.size}MB!`);
      }
      return this.type.indexOf(type) !== -1 && isLt;
    },
    /** 文件列表移除文件时的钩子 */
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    /** 点击文件列表中已上传的文件时的钩子 */
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
  },
};
</script>
<style lang="scss" scoped></style>
