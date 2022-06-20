<template>
  <el-image-viewer
    v-if="showViewer"
    :initial-index="initialIndex"
    :on-close="hide"
    :url-list="imageViewerSrcList"
  />
</template>
<script>
import ElImageViewer from 'element-ui/packages/image/src/image-viewer';
export default {
  name: 'BjPreviewList',
  components: {
    ElImageViewer,
  },

  props: {},

  data() {
    return {
      showViewer: false,
      initialIndex: 0,
      imageViewerSrcList: [],
    };
  },
  methods: {
    hide() {
      this.showViewer = false;
      this.imageViewerSrcList = [];
    },
    show(urlList, index) {
      if (!urlList) return;
      if (!Array.isArray(urlList)) urlList = [urlList];
      this.initialIndex = index || 0;
      if (this.initialIndex > urlList.length) this.initialIndex = 0;

      this.showViewer = true;

      urlList.forEach((url, index) => {
        this.$store
          .dispatch('fileUrl/getFileTokenUrl', {
            url: url,
            bucketName: process.env.VUE_APP_UPLOAD_BUCKET_NAME,
          })
          .then(fileTokenUrl => {
            this.$set(this.imageViewerSrcList, index, fileTokenUrl);
          })
          .catch(error => {
            console.warn(error);
            this.$set(this.imageViewerSrcList, index, '');
          });
      });
    },
  },
};
</script>
