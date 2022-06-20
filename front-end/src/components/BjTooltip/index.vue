<template>
  <el-tooltip :content="content + ''" :placement="placement" :class="link ? 'blue' : ''">
    <div class="ellipsis" @click="onTooltip">{{ content }}</div>
  </el-tooltip>
</template>

<script>
export default {
  name: 'BjTooltip',
  props: {
    /* 显示的内容 */
    content: {
      type: [String, Number],
      default: '',
    },
    /* Tooltip 的出现位置 */
    placement: {
      type: String,
      default: 'top',
    },
    /* 是否蓝色 */
    link: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    onTooltip() {
      this.$emit('tooltip');
    },
  },
};
</script>

<style lang="scss" scoped>
.blue {
  color: #1890ff;
  cursor: pointer;
}
</style>
