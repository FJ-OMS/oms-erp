<template>
  <video v-if="src" :width="width" :height="height">
    <source :src="src" />
  </video>
</template>
<script>
import { settingApi } from '@/api';
export default {
  name: 'BjKeyVideo',
  props: {
    url: {
      type: String,
      default: '',
    },
    fileKey: {
      type: String,
      default: '',
    },
    height: {
      type: String,
      default: '100%',
    },
    width: {
      type: String,
      default: '100%',
    },
  },
  data() {
    return {
      src: null,
    };
  },
  computed: {},
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
        if (this.url.indexOf('blob:') == 0) {
          this.src = this.url;
          return;
        }
        this.$store
          .dispatch('fileUrl/getFileTokenUrl', {
            url: this.url,
            bucketName: this.bucketName || process.env.VUE_APP_UPLOAD_BUCKET_NAME,
          })
          .then(url => {
            console.log('fileUrl/getFileTokenUrl', url);
            this.src = url;
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
<style scoped></style>
