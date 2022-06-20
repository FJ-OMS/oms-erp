<template>
  <div class="bj-upload-20210723" :class="classID">
    <el-upload
      ref="upload"
      action=""
      class="upload-demo"
      :class="{ 'hide-upload': hideUpload }"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
      :on-error="handleError"
      :on-progress="handlePropress"
      :on-exceed="handleExceed"
      :file-list="fileList"
      :auto-upload="autoUpload"
      :on-change="handleChange"
      :before-upload="bjBeforeUpload"
      :http-request="httpRequest"
      :multiple="multiple"
      :list-type="listType"
      :show-file-list="showFileList"
      :accept="accept"
      :disabled="disabled"
    >
      <slot name="default" />
      <el-button v-if="!$slots.default" slot="trigger" size="small" type="primary"
        >选取文件</el-button
      >
      <div slot="tip" class="el-upload__tip">
        <slot name="tip" />
      </div>
      <!-- 文件列表 重新定义 -->
      <div slot="file" slot-scope="{ file }" class="el-upload-list__item-thumbnail">
        <!-- 缩略图  -->
        <BjViewUrl :key="file.url" :url="file" class="el-upload-list__item-thumbnail" />
        <!-- 删除图标 -->
        <span class="el-upload-list__item-actions">
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="onRemove(file)">
            <i class="el-icon-delete" />
          </span>
        </span>
      </div>
    </el-upload>
  </div>
</template>
<script>
import { settingApi } from '@/api';
import Sortable from 'sortablejs';
export default {
  name: 'BjUpload',
  props: {
    // 多图上传时，使用异步并发请求（Promise.all）。默认false，递归请求接口按顺序上传。
    concurrent: {
      type: Boolean,
      default: false,
    },
    // 文件列表的类型	string	text/picture/picture-card
    listType: {
      type: String,
      default: 'text',
    },
    limit: {
      type: Number,
      default: 1,
    },
    // 文件数量超过limit时的行为。默认false。
    // false : 删除最早之前的文件，加入新的文件，使文件总数=limit
    // true  : 当 文件总数=limit 时，隐藏上传按钮。
    limitHide: {
      type: Boolean,
      default: false,
    },
    autoUpload: {
      type: Boolean,
      default: false,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    query: {
      type: Object,
      default: () => {
        return {
          bucketName: process.env.VUE_APP_UPLOAD_BUCKET_NAME,
        };
      },
    },
    // 是否显示已上传文件列表
    showFileList: {
      type: Boolean,
      default: false,
    },
    // 返回解密的路径
    decrypt: {
      type: Boolean,
      default: false,
    },
    // 解密路径的授权时间  单位秒; 最长时间是7天
    expires: {
      type: Number,
      default: 7 * 24 * 60 * 60,
    },
    // file input的原生属性：https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/file#accept
    accept: {
      type: String,
      default: '',
    },
    // 自定义类型检查
    acceptList: {
      type: Array,
      default: function() {
        return [];
      },
    },
    // 限制文件大小：单位 kb  （通用）
    limitSize: {
      type: Number,
      default: 0,
    },
    // 限制视频大小：单位 kb  （当file.raw.type == type: "video/**" 时）
    videoSize: {
      type: Number,
      default: 0,
    },
    // 限制视频大小：单位 kb  （当file.raw.type == type: "image/**" 时）
    imageSize: {
      type: Number,
      default: 0,
    },
    outSizeText: {
      type: String,
      default: '',
    },
    resKey: {
      type: String,
      default: 'message',
    },
    beforeUpload: Function,
  },
  data() {
    return {
      hideUpload: false,
      fileList: [],
    };
  },
  computed: {
    classID() {
      return 'bj-upload-' + this.randomFn();
    },
    // fileRawList() {
    //   return this.fileList.map(file => file.raw);
    // },
  },
  watch: {
    fileList: {
      handler(v) {
        console.log(v);
        if (this.limitHide && v.length >= this.limit) {
          this.hideUpload = true;
        } else {
          this.hideUpload = false;
        }
        this.$emit('handleFileListChange', v);

        this.drop();
      },
      deep: true,
    },
  },
  methods: {
    randomFn() {
      return Number(
        Math.random()
          .toString()
          .substr(3, 12) + Date.now(),
      )
        .toString(36)
        .substr(0, 8)
        .toUpperCase();
    },
    drop() {
      if (!this.showFileList) return;
      this.$nextTick(() => {
        // 此时找到的元素是要拖拽元素的父容器
        const tbody = document.querySelector(`.${this.classID} .el-upload-list`);
        const _this = this;
        Sortable.create(tbody, {
          //  指定父元素下可被拖拽的子元素
          draggable: `.${this.classID} .el-upload-list .el-upload-list__item`,
          // handle: '.virtualReplenishPriorityRule-handle',
          onEnd({ newIndex, oldIndex }) {
            const currRow = _this.fileList.splice(oldIndex, 1)[0];
            _this.fileList.splice(newIndex, 0, currRow);
          },
        });
      });
    },
    onRemove(file) {
      // ⭐ 获取到该组件调用handleRemove方法删除file对象
      this.$refs.upload.handleRemove(file);
      this.$emit('remove', file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
    },
    checkFileType(file) {
      if (file && file.raw) {
        let res = file.raw.type.split('/')[0];
        if (res != 'image' && res != 'video') {
          res = 'application';
        }

        return res;
      } else {
        return this.$getFileTypeByUrl(file.url) || 'application';
      }
    },
    /** ********* */
    setFileList(v = []) {
      if (Array.isArray(v)) {
        this.fileList = [].concat(v);
      } else {
        this.fileList = [v];
      }
    },
    addFileList(v) {
      if (!v) return;
      if (Array.isArray(v)) {
        this.fileList = this.fileList.concat(v);
      } else {
        this.fileList.push(v);
      }
    },
    submitUpload() {
      console.log('--submitUpload 手动提交上传，多文件');
      if (this.fileList.length) {
        this.uploadFileAll();
      } else {
        this.$message.warning('请选择文件');
      }
    },

    // 默认的上传行为
    httpRequest(params) {
      console.log('-- 覆盖默认的上传行为，可以自定义上传的实现 每个文件触发一次');
      if (this.autoUpload) {
        this.uploadFileOne(params.file);
      }
    },

    handleChange(file, fileList) {
      console.log('--handleChange 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用');
      console.log(file);
      console.log(fileList);
      //  file.raw.type
      // video/ogg
      // type: "application/x-msdownload"
      // type: "image/jpeg"
      // type: "image/png"
      this.fileList = fileList.slice(this.limit * -1);
    },
    handleExceed(files, fileList) {
      console.log('--handleExceed 文件超出个数限制时的钩子');
      console.log(files);
      console.log(fileList);
      // this.$message.warning(
      //   `当前限制选择 ${this.limit} 个文件，本次选择了 ${
      //     files.length
      //   } 个文件，共选择了 ${files.length + fileList.length} 个文件`,
      // );
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
    handlePreview(file) {
      console.log('--handlePreview 点击文件列表中已上传的文件时的钩子');
      console.log(file);
    },
    bjBeforeUpload(file) {
      // 当auto-upload为true时 before-upload方法才会自动被触发
      // 当其它情况时，手动调用
      console.log('*************************************');

      const FILE_TYPE = file.type || (file.raw && file.raw.type);
      const FILE_SIZE = (file.size || (file.raw && file.raw.size)) / 1024; // KB
      console.log(
        '--beforeUpload 上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传。',
        FILE_TYPE,
        FILE_SIZE,
      );

      if (typeof this.beforeUpload === 'function') {
        return this.beforeUpload(file);
      }

      let TYPE_FLAG = true;
      let SIZE_FLAG = true;
      if (this.acceptList.length) {
        const TYPE_FLAG_1 = this.acceptList.includes(FILE_TYPE);
        const TYPE_FLAG_2 = this.acceptList.some(t => {
          return file.name.endsWith(t);
        });
        TYPE_FLAG = TYPE_FLAG_1 || TYPE_FLAG_2;
        if (!TYPE_FLAG) {
          this.$message.error(
            `仅支持 ${this.acceptList.join(' / ')} 格式，${file.name}的格式为${FILE_TYPE}!`,
          );
          return false;
        }
      }

      let LIMIT_SIZE = Infinity;
      if (this.videoSize && FILE_TYPE.indexOf('video/') == 0) {
        LIMIT_SIZE = this.videoSize;
      } else if (this.imageSize && FILE_TYPE.indexOf('image/') == 0) {
        LIMIT_SIZE = this.imageSize;
      } else if (this.limitSize) {
        LIMIT_SIZE = this.limitSize;
      }
      SIZE_FLAG = FILE_SIZE < LIMIT_SIZE;
      if (!SIZE_FLAG) {
        if (this.outSizeText) {
          this.$message.error(this.outSizeText);
        } else {
          this.$message.error(`${file.name}的大小不能超过 ${LIMIT_SIZE} KB!`);
        }

        return false;
      }

      return TYPE_FLAG && SIZE_FLAG;
    },
    uploadFileApi(file) {
      return new Promise((resolve, reject) => {
        if (!file) {
          reject(file);
          return;
        }
        console.log(file);

        const formData = new FormData();
        formData.append('file', file);

        const QUERY = Object.assign({}, this.query, {
          // 文件名加随机数
          fileName: Date.now() + '_' + this.randomFn() + '_' + file.name,
        });

        const API_DATA = { BjBody: formData, BjQuery: QUERY };
        const bucketName = this.query.bucketName;
        settingApi
          .uploadFiles(API_DATA)
          .then(res => {
            if (res.failed === true) {
              reject(res);
            }
            if (this.resKey) {
              const resKeyArray = this.resKey.split('/');
              let temp = res;
              resKeyArray.forEach((k, i) => {
                temp = temp && temp[k];
              });
              return temp;
            } else {
              return res;
            }

            // if (res && typeof res == 'string' && bucketName && res.indexOf(bucketName) !== -1) {
            //   return res;
            // } else if (res.failed === false) {
            //   return res.data || res.message;
            // } else if (res.failed === true) {
            //   reject(res);
            // } else {
            //   return res;
            // }
          })
          .then(url => {
            if (this.decrypt) {
              // 去授权
              settingApi
                .getFileUrlByUrl({
                  bucketName: bucketName || process.env.VUE_APP_UPLOAD_BUCKET_NAME,
                  url: url,
                  expires: this.expires,

                  // 授权有效时长(单位秒)
                })
                .then(res => {
                  if (res) {
                    let url = res.fileTokenUrl || res;
                    if (url.indexOf('http') !== 0) {
                      url = 'http://' + url;
                    }
                    resolve(url);
                  } else {
                    reject(url);
                  }
                });
            } else {
              // 不解密
              resolve(url);
            }
          });
      });
    },
    uploadFileOne(file) {
      if (!file) {
        this.$emit('afterUpload', file);
        return;
      }
      // 开启自动上传（autoUpload）需要等待上传成功之后再显示图片
      let index = null;
      let uploadData = null;
      if (this.autoUpload) {
        index = this.fileList.findIndex(e => e.uid === file.uid);
        uploadData = this.$lodash.clone(this.fileList[index]);
        this.fileList.splice(index, 1);
      }
      // console.log(index, uploadData);
      this.uploadFileApi(file).then(res => {
        uploadData.url = res;
        this.fileList.splice(index, 1, uploadData);
        // console.log(this.fileList);
        this.$emit('afterUpload', res);
      });
    },
    uploadFileAll() {
      if (this.concurrent) {
        this.uploadFileAllConcurrent();
      } else {
        this.uploadFileAllSequential();
      }
    },

    // 顺序请求
    uploadFileAllSequential() {
      const FILTER_LIST = this.fileList.filter(file => file.raw);

      if (!FILTER_LIST.every(file => this.bjBeforeUpload(file))) {
        // this.$emit('afterUpload', []);
        return;
      }

      if (!FILTER_LIST.length) {
        this.$emit('afterUpload', []);
        return;
      }

      let index = 0;
      const len = FILTER_LIST.length;
      const resultArr = [];

      const func = file => {
        this.uploadFileApi(file)
          .then(res => {
            index++;

            resultArr.push(res);

            if (index < len) {
              func(FILTER_LIST[index].raw);
            } else {
              this.$emit('afterUpload', resultArr);
            }
          })
          .catch(err => {
            index++;

            console.warn(err);
            // 有一个错误,就不再继续,直接返回
            this.$emit('afterUpload', resultArr);
          });
      };

      func(FILTER_LIST[index].raw);
    },
    // 并发请求
    uploadFileAllConcurrent() {
      const FILTER_LIST = this.fileList.filter(file => file.raw);

      if (!FILTER_LIST.every(file => this.bjBeforeUpload(file))) {
        // this.$emit('afterUpload', []);
        return;
      }

      const PROMISE_LIST = FILTER_LIST.map(file => this.uploadFileApi(file.raw));
      if (!PROMISE_LIST.length) {
        this.$emit('afterUpload', []);
        return;
      }
      Promise.all(PROMISE_LIST)
        .then(res => {
          // 这里输出的 res 是testPromise()，testPromise1()  resolve 返回值的 集合
          console.log(res);
          this.$emit(
            'afterUpload',
            res.filter(e => typeof e === 'string'),
          );
        })
        .catch(err => {
          console.warn(err);
        });
    },
  },
};
</script>
<style lang="scss">
.bj-upload-20210723 {
  .el-upload-list__item-thumbnail {
    .el-image {
      height: 100%;
      width: 100%;
    }
  }

  .hide-upload .el-upload--picture-card {
    display: none;
  }
}
</style>
