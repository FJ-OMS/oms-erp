# ScrollLoad 表格的滚动加载

## 使用示例

### res-key

res-key 目标数据 在 接口返回体的位置。多层嵌套 json。 如 response:

```javascript
{
  data: {
    title: '',
    content: {
      list: [{ a: '1' }, { b: '2' }],
    },
  },
}

```

则：`res-key = 'data/content/list'`

###

```js
<template>
  <ScrollSelect
    v-model="id"
    :api="api"
    placeholder="请选择"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :remote="remote"
    option-label="name"
    option-value="id"
    res-key="content"
    search-key="name"
    @change="onChange"
  />
</template>
<script>
import { channelApi } from '@/api';
export default {
  name: 'BjSelectStore',
  props: {
    value: {
      type: [String, Number, Array],
      default: null,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: 'mini',
    },
    // 每页加载几条数据
    pageSize: {
      type: Number,
      default: 10,
    },
    // 开启远程搜索； 需要与搜索参数 search-key 配合
    remote: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      api: channelApi.getAllShopPage,
      id: null,
    };
  },
  watch: {
    value: {
      handler(v) {
        this.id = v;
      },
      immediate: true,
    },
    id(v) {
      this.$emit('input', v);
    },
  },
  mounted() {},
  methods: {
    onChange(v, item) {
      this.$emit('change', v, item);
    },
  },
};
</script>

```
