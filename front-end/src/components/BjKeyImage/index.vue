<template>
  <el-image v-if="src" :style="style" :src="src" :fit="fit" :preview-src-list="list">
    <div slot="error" class="image-slot">
      <i class="el-icon-picture-outline"></i>
    </div>
  </el-image>
</template>
<script>
import { settingApi } from '@/api';
export default {
  name: 'BjKeyImage',
  props: {
    url: {
      type: String,
      default: '',
    },
    fileKey: {
      type: String,
      default: '',
    },
    fit: {
      type: String,
      default: 'contain', // 确定图片如何适应容器框，同原生 object-fit	string	fill / contain / cover / none / scale-down
    },
    preview: {
      type: Boolean,
      default: false,
    },
    height: {
      type: String,
      default: 'auto',
    },
    width: {
      type: String,
      default: 'auto',
    },
  },
  data() {
    return {
      src: null,
    };
  },
  computed: {
    list() {
      return this.src && this.preview ? [this.src] : [];
    },
    style() {
      return {
        '--height': this.height,
        '--width': this.width,
      };
    },
  },
  watch: {
    fileKey: {
      handler(v) {
        this.getUrl();
      },
      immediate: false,
    },
    url: {
      handler(v) {
        this.getUrl();
      },
      immediate: false,
    },
  },
  mounted() {
    // console.log('this.fileKey', this.fileKey);
    // console.log('this.url', this.url);
    this.getUrl();
  },
  methods: {
    getUrl() {
      if (this.fileKey) {
        settingApi
          .getFileUrlByKey({
            fileKey: this.fileKey,
            expires: 60,
          })
          .then(res => {
            if (res.failed !== true) {
              let url = res.fileTokenUrl || res.message || res;
              if (res.fileTokenUrl.indexOf('http') !== 0) {
                url = 'http://' + url;
              }
              this.src = url;
            }
          });
      }
      if (this.url) {
        if (
          this.url.indexOf('static/') == 0 ||
          this.url.indexOf('http') == 0 ||
          this.url.indexOf('blob:') == 0 ||
          this.url.indexOf('data:') == 0
        ) {
          this.src = this.url;
          return;
        }
        this.$store
          .dispatch('fileUrl/getFileTokenUrl', {
            url: this.url,
            bucketName: this.bucketName || process.env.VUE_APP_UPLOAD_BUCKET_NAME,
          })
          .then(url => {
            this.src = url;
          })
          .catch(error => {
            this.src = typeof error == 'string' ? error : 'boji.err';
            console.log(this.src);
          });
        // settingApi
        //   .getFileUrlByUrl({
        //     bucketName: this.bucketName || process.env.VUE_APP_UPLOAD_BUCKET_NAME,
        //     url: this.url,
        //     // expires: 60,
        //   })
        //   .then(res => {
        //     if (res) {
        //       let url = res.fileTokenUrl || res;
        //       if (url.indexOf('http') !== 0) {
        //         url = 'http://' + url;
        //       }
        //       this.src = url;
        //     }
        //   });
      }
    },
  },
};
</script>
<style lang="scss" scoped vars="{height}">
.el-image {
  background-color: inherit;
  height: var(--height);
  width: var(--width);
}
</style>
