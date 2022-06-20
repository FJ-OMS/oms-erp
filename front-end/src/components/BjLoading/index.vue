<template>
  <div>
    <loading type="spinner" :color="color" :size="size" :text-size="textSize" :text-color="textColor">{{ text }}</loading>
  </div>
</template>

<script>
  import { Loading } from 'vant';
  export default {
    components: { Loading },
    props: {
      /* 颜色 */
      color: {
        type: String,
        default: '#409EFF'
      },
      /* 加载图标大小，默认单位为 px */
      size: {
        type: [String, Number],
        default: '30px'
      },
      /* 文字大小，默认单位为 px */
      textSize: {
        type: [String, Number],
        default: '14px'
      },
      /* 文字颜色 */
      textColor: {
        type: String,
        default: '#409EFF'
      },
      /* 是否垂直排列图标和文字内容 */
      vertical: {
        type: Boolean,
        default: false
      },
      /* 加载文字 */
      text: {
        type: String,
        default: '拼命加载中'
      }
    }
  };
</script>
