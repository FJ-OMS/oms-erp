<!--
 * @Author: your name
 * @Date: 2021-08-31 17:18:40
 * @LastEditTime: 2022-03-08 17:41:37
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjSelectLogisticCompany\index.vue
-->
<template>
  <div>
    <el-col :span="span">
      <el-form-item :label="queryLabels[0]" :prop="cptQueryProps[0]" :label-width="labelWidth[0]">
        <BjSelectLov
          v-model="queryParams[cptQueryKeys[0]]"
          lov-code="SCHL.CHANNEL_TYPE"
          :disabled="disabled"
          @change="handleChannelChange"
        />
      </el-form-item>
    </el-col>

    <el-col :span="span">
      <el-form-item :label="queryLabels[1]" :prop="cptQueryProps[1]" :label-width="labelWidth[1]">
        <!-- 、BjSelectStoreOnline  status: true,

        1.如果渠道是第三方电商平台，则店铺取关联的第三方电商平台的网店。

2.如果渠道是后台或者其他，则取已启用的门店列表。
-->
        <BjSelectStore
          v-model="queryParams[cptQueryKeys[1]]"
          :collapse-tags="collapseTags"
          :option-value="storeOptionValue"
          :multiple="multiple"
          :default-params="{ channelTypeCode: queryParams[cptQueryKeys[0]] }"
          :placeholder="queryParams[cptQueryKeys[0]] ? '请选择' : '请先选择渠道'"
          :disabled="!queryParams[cptQueryKeys[0]] || disabled"
          @change="handleStoreChange"
        />
      </el-form-item>
    </el-col>
  </div>
</template>
<script>
// 渠道 和 店铺 联动的组件，一般用于 BjPage 的 baseForm Slot 中，例：
/**
    <BjSelectChannelStore
      :span="colSpan"
      :query-params="queryParams"
      :query-props="['channel', 'storeIdList']"
      store-option-value="code"
      multiple
    />
 */
export default {
  name: 'BjSelectChannelStore',
  props: {
    // el-col 的 span 占位宽度
    span: {
      type: Number,
      default: 8,
    },
    labelWidth: {
      type: Array,
      default: () => {
        return [null, null];
      },
    },
    // 店铺多选时，选中的结果显示为 tags
    collapseTags: {
      type: Boolean,
      default: true,
    },
    // 入参JSON
    queryParams: {
      type: Object,
      default: function () {
        return {};
      },
    },
    // 入参JSON对应的key，未传入时，默认取 queryProps
    queryKeys: {
      type: Array,
      default: function () {
        return null;
      },
    },
    // 用于el-form-item的prop，未传入时，默认取 queryKeys
    queryProps: {
      type: Array,
      default: function () {
        return ['channel', 'storeId'];
      },
    },
    // 用于el-form-item的 label
    queryLabels: {
      type: Array,
      default: function () {
        return ['销售渠道', '销售店铺'];
      },
    },
    // 用于 店铺组件 BjSelectStoreOnline 的option-value 即，一般取 code 或 id
    storeOptionValue: {
      type: String,
      default: 'id',
    },
    // 店铺是否多选
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {};
  },
  computed: {
    cptQueryKeys() {
      if (this.queryKeys && this.queryKeys.length) {
        return this.queryKeys;
      }
      return this.queryProps;
    },
    cptQueryProps() {
      if (this.queryProps && this.queryProps.length) {
        return this.queryProps;
      }
      return this.queryKeys;
    },
  },
  watch: {},
  mounted() {},
  methods: {
    // 渠道change回调
    handleChannelChange(v, item) {
      console.log('渠道change回调', v);
      this.queryParams[this.cptQueryKeys[1]] = null;
      this.$emit('handleChannelChange', v, item);
    },
    // 店铺change回调
    handleStoreChange(v, item) {
      console.log('店铺change回调', v);
      this.$emit('handleStoreChange', v, item);
    },
  },
};
</script>
