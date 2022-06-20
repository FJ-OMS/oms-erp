<template>
  <div class="app-container">
    <slot name="top" />
    <el-upload
      class="upload-demo-20210810"
      drag
      action=""
      :on-change="handleChange"
      :http-request="httpRequest"
      :before-upload="bjBeforeUpload"
      :multiple="false"
      :file-list="fileList"
      :accept="accept"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div slot="tip" class="el-upload__tip">
        <slot name="default" />

        <template v-if="!$slots.default">
          只能上传 {{ accept || acceptList.join(' ') || '' }} 文件，且不超过
          {{ this.limitSize / 1024 }}Mb
        </template>
      </div>
    </el-upload>
    <div
      v-if="result.success || result.error || result.total || result.message"
      class="upload-result"
    >
      <p>
        <span v-if="result.success">成功数量：{{ result.success }}</span>
        <span v-if="result.error">失败数量：{{ result.error }}</span>
        <span v-if="result.total">总数量：{{ result.total }}</span>
      </p>
      <p v-if="result.message">{{ result.message }}</p>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BjImportFile',
  props: {
    limitSize: {
      type: Number,
      default: 2048,
    },
    autoUpload: {
      type: Boolean,
      default: false,
    },
    limit: {
      type: Number,
      default: 1,
    },
    // file input的原生属性：https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/file#accept
    accept: {
      type: String,
      default: '',
    },
    // 自定义类型检查. 只用文件名后缀
    acceptList: {
      type: Array,
      default: function() {
        return [];
      },
    },
    beforeUpload: Function,
  },
  data() {
    return {
      fileList: [],
      result: {
        success: 0,
        error: 0,
        total: 0,
        message: '',
        url: '',
      },
    };
  },
  methods: {
    setResult(d) {
      this.result = { ...this.result, ...d };
    },
    bjBeforeUpload(file) {
      console.log(file);
      console.log('*************************************');

      if (typeof this.beforeUpload === 'function') {
        return this.beforeUpload(file);
      }

      let TYPE_FLAG = true;
      let SIZE_FLAG = true;
      if (this.acceptList.length) {
        const TYPE_FLAG_1 = this.acceptList.includes(file.type);
        const TYPE_FLAG_2 = this.acceptList.some(t => {
          return file.name.endsWith(t);
        });
        TYPE_FLAG = TYPE_FLAG_1 || TYPE_FLAG_2;
        if (!TYPE_FLAG) {
          this.$message.error(
            `仅支持 ${this.acceptList.join(' / ')} 格式，当前文件格式为${file.type}!`,
          );
          return false;
        }
      }

      if (this.limitSize) {
        console.log(file.size);
        SIZE_FLAG = file.size / 1024 < this.limitSize;
        if (!SIZE_FLAG) {
          this.$message.error(`大小不能超过 ${this.limitSize} KB!`);
          return false;
        }
      }

      console.log(
        '--beforeUpload 上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传。',
      );
      return TYPE_FLAG && SIZE_FLAG;
    },
    httpRequest() {
      if (this.autoUpload) {
        this.importFile();
      }
    },
    handleChange(file, fileList) {
      console.log('--handleChange 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用');
      console.log(file);
      console.log(fileList);
      this.fileList = fileList.slice(this.limit * -1);
    },
    handleRemove(file, fileList) {
      console.log('--handleRemove 文件列表移除文件时的钩子');
      console.log(file);
      console.log(fileList);
      this.fileList = fileList;
    },
    handleError(err, file, fileList) {
      console.log('--handleError 文件上传失败时的钩子');
      console.log(err);
      console.log(file);
      console.log(fileList);
      this.fileList = fileList;
    },
    handleSuccess(response, file, fileList) {
      console.log('--handleSuccess 文件上传成功时的钩子');
      console.log(response);
      console.log(file);
      console.log(fileList);
      this.fileList = fileList;
    },
    handlePropress(event, file, fileList) {
      console.log('--handlePropress 文件上传时的钩子');
      console.log(event);
      console.log(file);
      console.log(fileList);
      this.fileList = fileList;
    },

    importFile() {
      this.$emit('importFile', this.fileList[0].raw);
    },
  },
};
</script>
<style>
.upload-demo-20210810 {
  width: 100%;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.upload-demo-20210810 .upload-result {
  padding: 0 1.25rem;
  border: 1px solid rgba(0, 0, 0, 0.125);
  margin-top: 8px;
}
.upload-demo-20210810 .upload-result > p > span {
  margin-right: 16px;
}
</style>
