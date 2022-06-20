<!--
 * @Author: your name
 * @Date: 2021-07-19 18:15:06
 * @LastEditTime: 2021-10-26 17:39:43
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjSelectTenant\index.vue
-->
<template>
  <!--
    isSuperAdmin: 是否为系统的超级管理员
    ————不是： 禁用下拉框，不可选择；如果id没有值，默认为登录用户的tanentId
    ———— 是 ： 启用下拉框，多租户模式下的超管可用
   -->
  <el-select
    v-model.trim="id"
    class="full-width"
    placeholder="请选择"
    :multiple="multiple"
    :disabled="disabled || !isSuperAdmin"
    filterable
    clearable
    @clear="onClear"
  >
    <el-option
      v-for="(item, index) in BJ_TENANTS"
      :key="index"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>
<script>
export default {
  name: 'BjSelectTenant',
  props: {
    value: {
      type: [String, Number],
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
  },
  data() {
    return {
      id: null,
    };
  },
  computed: {
    BJ_TENANTS() {
      return this.$store.state.values.BJ_TENANTS;
    },
    isSuperAdmin() {
      return this.$store.getters.isSuperAdmin;
    },
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
  mounted() {
    this.$store.dispatch('values/getTenants');
    if (!this.isSuperAdmin && !this.id) {
      this.id = this.$store.getters.tenantId;
    }
  },
  methods: {
    onClear() {
      this.$emit('clear');
    },
  },
};
</script>
