<template>
  <div :style="`width:${width};height:${height}`">
    <BjKeyImage
      v-if="fileType === 'image'"
      :height="height"
      :width="width"
      :url="fileUrl"
      :preview="preview"
    />
    <BjKeyVideo v-else-if="fileType === 'video'" :height="height" :width="width" :url="fileUrl" />
    <div v-else-if="fileType === 'application'">
      <i class="font-icon el-icon-folder-checked" />
      <p class="text-center">{{ fileUrl && fileUrl.split('/').slice(-1)[0] }}</p>
    </div>
    <div v-else-if="fileType === 'text'">
      <i class="font-icon el-icon-document" />
      <span class="text-center">{{ fileUrl && fileUrl.split('/').slice(-1)[0] }}</span>
    </div>

    <span v-else>{{ fileUrl }}</span>
  </div>
</template>
<script>
import { getFileTypeByUrl } from '@/utils/index';
export default {
  name: 'BjViewUrl',
  props: {
    url: {
      type: [String, Object],
      default: '',
    },
    fileKey: {
      type: String,
      default: '',
    },
    height: {
      type: String,
      default: 'auto',
    },
    width: {
      type: String,
      default: 'auto',
    },
    preview: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      fileUrl: '',
      fileType: '',
    };
  },
  computed: {},
  watch: {
    url(v) {
      this.checkUrl(v);
    },
  },
  mounted() {
    this.checkUrl(this.url);
  },
  methods: {
    checkUrl(v) {
      if (typeof v === 'string') {
        this.fileUrl = v;
        this.fileType = getFileTypeByUrl(v);
      } else {
        this.fileUrl = v.url;

        if (v && v.raw) {
          let res = v.raw.type.split('/')[0];
          if (res != 'image' && res != 'video') {
            res = 'application';
          }
          this.fileType = res;
        } else {
          this.fileType = getFileTypeByUrl(this.fileUrl) || 'application';
        }
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.font-icon {
  font-size: 80px;
  text-align: center;
  line-height: 100px;
  width: 100%;
}
.text-center {
  display: inline-block;
  width: 100%;
}
</style>
