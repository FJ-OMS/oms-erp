<!--
 * @Author: your name
 * @Date: 2021-07-22 11:21:06
 * @LastEditTime: 2021-07-22 17:17:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\sync\edit.vue
-->
<template>
  <div>
    <sync-operate />
  </div>
</template>
<script>
import syncOperate from './components/syncOperate.vue';
export default {
  name: 'SettingIndexesSyncEdit',
  components: {
    syncOperate,
  },
};
</script>
