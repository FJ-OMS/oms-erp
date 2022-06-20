<template>
  <component
    :is="type"
    v-clipboard:copy="copy"
    v-clipboard:success="$onCopy"
    v-clipboard:error="$onError"
    type="text"
    :class="type === 'i' ? 'el-icon-document-copy' : ''"
    >{{ type === 'el-button' ? '复制' : '' }}</component
  >
</template>

<script>
export default {
  name: 'BjCopyIcon',
  props: {
    type: {
      type: String,
      default: 'i',
      validator: function(value) {
        return ['i', 'el-button'];
      },
    },
    copy: {
      type: [Number, String],
      default: null,
    },
  },
};
</script>

<style></style>
