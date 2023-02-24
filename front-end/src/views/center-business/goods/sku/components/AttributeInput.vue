<!--  属性输入框 ----->
<template>
  <div class="width-full">
    <template v-if="INPUT_BOX_TYPE_JSON.RADIO == type && !select">
      <el-radio-group v-model="id">
        <el-radio v-for="(vItem, j) in valueList" :key="j" :label="vItem[nameKey]">{{
          vItem[nameKey]
        }}</el-radio>
      </el-radio-group>
      <el-button
        v-show="!disabled && !readonly && id"
        style="margin-left: 8px"
        type="primary"
        @click="id = null"
        >清除选中</el-button
      >
    </template>
    <template v-if="INPUT_BOX_TYPE_JSON.CHECKBOX == type && !select">
      <el-checkbox-group v-model="id" :disabled="readonly">
        <el-checkbox v-for="(vItem, j) in valueList" :key="j" :label="vItem[nameKey]">{{
          vItem[nameKey]
        }}</el-checkbox>
      </el-checkbox-group>
    </template>
    <template v-if="INPUT_BOX_TYPE_JSON.INPUT == type">
      <template v-if="isPrice">
        <BjInputMoney v-if="!readonly" v-model.trim="id" :readonly="readonly" />
        <span v-else>{{ $number2money(Number(id || 0)) }}</span>
      </template>
      <template v-else>
        <!-- 默认属性，2位小数， 带单位-->
        <BjInputNumber
          v-if="checkDefaultAttributeList && !readonly"
          v-model="id"
          :precision="2"
          :disabled="readonly"
        >
          <template v-if="checkDefaultAttributeList" #append>
            {{ colProp === 'weight' ? 'g' : 'cm' }}
          </template>
        </BjInputNumber>
        <!-- 其它属性：附加等 -->
        <el-input
          v-else-if="!checkDefaultAttributeList && !readonly"
          v-model="id"
          class="width-full"
          :maxlength="MAX_LENGTH.DEFAULT"
          :readonly="readonly"
        >
        </el-input>
        <span v-else>{{ id }}</span>
      </template>
    </template>
    <template
      v-if="
        INPUT_BOX_TYPE_JSON.SELECT == type ||
          (INPUT_BOX_TYPE_JSON.RADIO == type && select) ||
          (INPUT_BOX_TYPE_JSON.CHECKBOX == type && select)
      "
    >
      <el-select
        v-model="id"
        placeholder="请选择"
        :multiple="INPUT_BOX_TYPE_JSON.CHECKBOX == type && select"
        class="full-width"
        style="max-width: 416px"
      >
        <el-option
          v-for="(vItem, j) in valueList"
          :key="j"
          :label="vItem[nameKey]"
          :value="vItem[nameKey]"
        />
      </el-select>
    </template>
  </div>
</template>
<script>
import { INPUT_BOX_TYPE_JSON } from '@/utils/constants';
import DefaultAttributeList from '@/views/center-business/goods/components/DefaultAttributeList.json';
export default {
  name: 'AttributeInput',
  props: {
    nameKey: {
      type: [String],
      default: 'name',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    type: {
      type: [String, Number],
      default: INPUT_BOX_TYPE_JSON.INPUT,
    },
    value: {
      type: [String, Number, Array],
      default: null,
    },
    valueList: {
      type: Array,
      default: () => {
        return [];
      },
    },
    colProp: {
      type: String,
      default: null,
    },
    /** */
    readonly: {
      type: Boolean,
      default: false,
    },
    isPrice: {
      type: Boolean,
      default: false,
    },
    // 固定以 select 下拉框展示； 当type是checkbox时 为多选的下拉框
    select: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: '请输入',
    },
    size: {
      type: String,
      default: 'mini',
    },
    clearable: {
      type: Boolean,
      default: true,
    },
    required: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      INPUT_BOX_TYPE_JSON,
      id: null,
    };
  },
  computed: {
    // 验证是否是系统默认属性
    checkDefaultAttributeList() {
      return DefaultAttributeList.map(item => {
        return item.code;
      }).includes(this.colProp);
    },
  },
  watch: {
    INPUT_BOX_TYPE_JSON: {
      handler() {},
      deep: true,
      immediate: true,
    },
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
  created() {},
  mounted() {},
  methods: {},
};
</script>
<style lang="scss" scoped>
.flex {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
</style>
