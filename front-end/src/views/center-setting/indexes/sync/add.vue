<!--
 * @Author: your name
 * @Date: 2021-07-22 11:20:59
 * @LastEditTime: 2021-07-22 11:22:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\sync\add.vue
-->
<template>
  <div>
    <sync-operate />
  </div>
</template>
<script>
import syncOperate from './components/syncOperate.vue';
export default {
  name: 'SettingIndexesSyncAdd',
  components: {
    syncOperate,
  },
};
</script>
