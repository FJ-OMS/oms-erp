<template>
  <div class="bj-tag-2022-2-10-09-45-54" :class="size">
    <el-tag
      :class="color"
      :closable="closable"
      :disable-transitions="disableTransitions"
      :size="size"
      @close="handleCloseTag"
      ><slot />{{ text }}</el-tag
    >
  </div>
</template>
<script>
export default {
  name: 'BjTag',
  props: {
    size: {
      type: String,
      default: 'medium',
    },
    text: {
      type: String,
      default: '',
    },
    color: {
      type: String,
      default: '',
    },
    disableTransitions: {
      type: Boolean,
      default: false,
    },
    closable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      id: null,
      preValue: null,
    };
  },
  mounted() {},
  methods: {
    handleCloseTag() {
      this.$emit('close');
    },
  },
};
</script>
<style lang="scss">
.bj-tag-2022-2-10-09-45-54 + .bj-tag-2022-2-10-09-45-54 {
  margin-right: 10px;
  margin-left: 10px;
}
.bj-tag-2022-2-10-09-45-54.mini + .bj-tag-2022-2-10-09-45-54.mini {
  margin-right: 0;
  margin-left: 0;
}
.bj-tag-2022-2-10-09-45-54 {
  .el-icon-close,
  .el-tag__close,
  .el-tag {
    color: #fff !important;
    max-width: 100%;
    max-height: 100%;
  }
  .el-tag {
    border-color: #fff;
  }

  .orange {
    background-color: #f79c01;
    color: #744c06;
  }
  .yellow-dark {
    background-color: #f3c226;
    color: #624e09;
  }
  .purple-light {
    background-color: #e263ff;
    color: #7c0199;
  }

  .brown-light {
    background-color: #bf6d4f;
    color: #ffffff;
  }
  .blue-purple {
    background-color: #857bff;
    color: #1303bd;
  }

  .blue-light {
    background-color: #6393ff;
    color: #023cc5;
  }
  .green-light {
    background-color: #58d071;
    color: #015212;
  }
  .orange-red {
    background-color: #ff4500;
    color: #862602;
  }
}
</style>
