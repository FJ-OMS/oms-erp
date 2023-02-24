<!--
 * @Author: your name
 * @Date: 2021-10-14 17:24:14
 * @LastEditTime: 2022-04-12 16:56:28
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\source\components\source.vue
-->
<template>
  <div style="width:400px" class="mt16">
    <el-link type="warning" :underline="false">拖动排序，越上面优先级越高</el-link>
    <draggable
      v-model="list"
      ghost-class="ghost"
      chosen-class="chosen"
      force-fallback="true"
      group="people"
      animation="300"
      filter=".forbid"
      :disabled="disabled"
      class="mt16"
      @start="onStart"
    >
      <transition-group :name="groupName" :mode="mode" tag="div">
        <div v-for="(item, index) in list" :key="index" class="item">
          <div>
            {{ item.name }}
          </div>
          <el-switch
            v-model="item.check"
            class="switch"
            :disabled="!list.some((e, index) => index != 0 && !!e.check)"
            @change="onChange(item, index)"
          />
        </div>
      </transition-group>
    </draggable>
    <transition-group :name="groupName" :mode="mode" tag="div">
      <div v-for="(item, index) in noList" :key="index" class="item">
        <div>
          {{ item.name }}
        </div>
        <el-switch v-model="item.check" class="switch" @change="addChange(item, index)" />
      </div>
    </transition-group>
  </div>
</template>
<script>
// import { settingApi } from '@/api';
import draggable from 'vuedraggable';
const getDefaultForm = () => {
  return {};
};

export default {
  components: {
    draggable,
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {},

      disabled: false,
      groupName: 'slide-fade',
      mode: 'out-in',
      list: [
        { id: 1, name: '发货仓数少', check: true },
        { id: 2, name: '收发距离近', check: true },
        { id: 3, name: '运费成本低', check: true },
      ],
      noList: [],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    // 开始拖拽事件
    onStart() {
      this.disabled = false;
    },
    onChange(item, index) {
      this.disabled = true;
      this.$nextTick(() => {
        this.list.splice(index, 1);
        this.noList.push(item);
        this.disabled = false;
      });
    },
    addChange(item, index) {
      this.disabled = true;
      this.$nextTick(() => {
        this.noList.splice(index, 1);
        this.list.push(item);
        this.disabled = false;
      });
    },
    handleConfirm() {
      console.log(1111111111111);
      // const API_DATA = this.$lodash.clone(this.form);
      // if (this.isEdit) {
      //   API_DATA['_innerMap'] = {};
      // }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    // addApi(API_DATA) {
    //   settingApi.saveOrderFlowConfig(API_DATA).then(res => {
    //     console.log(res);
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('保存成功');
    //       setTimeout(() => {
    //         this.$refs.BjDrawerForm.hide();
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
<style lang="scss" scoped>
.item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
  background-color: #fdfdfd;
  border: solid 1px #eee;
  margin-bottom: 10px;
  cursor: move;
  border-radius: 4px;
}
.slide-fade-enter-active {
  transition: all 0.5s ease;
}
.slide-fade-leave-active {
  transition: all 0.3s ease;
}
.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>
