<!--
 * @Author: your name
 * @Date: 2021-07-23 13:48:12
 * @LastEditTime: 2021-07-23 16:41:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\inquiry\components\condition.vue
-->
<template>
  <div class="Bj-condition">
    <div>
      <label>关系：</label>
      <el-select v-model="values">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </div>
    <div class="mt20">
      <el-select v-model="values">
        <el-option
          v-for="item in childOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-select v-model="values">
        <el-option
          v-for="item in childOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-input :maxlength="MAX_LENGTH.DEFAULT" type="text" />
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      values: '',
      options: [
        { label: 'must', value: 'must' },
        { label: 'shoud', value: 'shoud' },
        { label: 'must_not', value: 'must_not' },
      ],
      childOptions: [{ label: 'must', value: 'must' }],
    };
  },
};
</script>
<style lang="scss" scoped>
label {
  font-size: 13px;
  font-weight: 500;
}
.Bj-condition {
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  padding: 10px 15px;
}
</style>
