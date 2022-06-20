<template>
  <div class="secure-item">
    <div class="secure-l">
      <span class="text">{{ title }}</span>
      <span>{{ content }}</span>
      <slot name="content" />
    </div>
    <div class="secure-r">
      <el-button :type="btnType" :icon="icon" circle />
      <span class="ml0 mr20">{{ iconText }}</span>
    </div>
    <el-divider direction="vertical" /><el-button class="m20" @click="onclick">{{
      btnText
    }}</el-button>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: 'title',
    },
    content: {
      type: String,
      default: '',
    },
    icon: {
      type: String,
      default: '',
    },
    iconText: {
      type: String,
      default: '',
    },
    btnType: {
      type: String,
      default: '',
    },
    btnText: {
      type: String,
      default: '按 钮',
    },
  },
  data() {
    return {};
  },
  methods: {
    onclick() {
      this.$emit('btnClick');
    },
  },
};
</script>

<style lang="scss" scoped>
.secure-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 20px 0;
  font-size: 13px;
  .secure-l {
    flex: 1;
    .text {
      margin: 0 30px;
      font-weight: 700;
    }
  }
  .secure-r {
    .el-button {
      padding: 3px;
      font-size: 14px;
    }
  }
}
.icon {
  font-size: 18px;
}
</style>
