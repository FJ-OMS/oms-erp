<!--
 * @Author: your name
 * @Date: 2021-06-23 10:00:11
 * @LastEditTime: 2022-04-12 16:56:51
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\test\index.vue
-->
<template>
  <div class="app-container">
    <div>
      <draggable
        v-model="myArray"
        ghost-class="ghost"
        chosen-class="chosen"
        force-fallback="true"
        group="people"
        animation="300"
        filter=".forbid"
        @start="onStart"
      >
        <transition-group :name="groupName" :mode="mode">
          <div v-for="(element, index) in myArray" :key="element.id" class="item">
            {{ element.name }}
            <el-switch v-model="element.check" class="switch" @change="onChange(element, index)" />
          </div>
        </transition-group>
      </draggable>
      <transition-group :name="groupName" :mode="mode">
        <div v-for="(element, index) in noArray" :key="element.id" class="item">
          {{ element.name }}
          <el-switch v-model="element.check" class="switch" @change="addChange(element, index)" />
        </div>
      </transition-group>
    </div>
    <!-- <div ref="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="date" label="日期" width="180" />
        <el-table-column prop="name" label="姓名" width="180" />
        <el-table-column prop="address" label="地址" />
      </el-table>
    </div>
    <br>
    <el-button  @click="toHtml">HTML</el-button>
    <el-button  @click="toExcel">toExcel</el-button>
    <el-button  @click="toWord">toWord</el-button>
    <el-button  @click="toPDF">toPDF</el-button> -->
  </div>
</template>

<script>
import { exportFile } from '@/utils/index';
import draggable from 'vuedraggable';
// 组件相关参数地址
// https://www.itxst.com/vue-draggable/qb2yyeu6.html
export default {
  components: {
    draggable,
  },
  data() {
    return {
      groupName: 'slide-fade',
      mode: 'out-in',
      // groupName: 'fade',
      // mode: 'in-out',
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
        },
      ],
      myArray: [
        { id: 1, name: 'www.itxst.com', check: true },
        { id: 2, name: 'www.jd.com', check: true },
        { id: 3, name: 'www.baidu.com', check: true },
        { id: 5, name: 'www.google.com', check: true },
      ],
      noArray: [{ id: 4, name: 'www.taobao.com（不允许拖拽）', check: false }],
    };
  },
  methods: {
    toPDF() {
      exportFile('a.pdf', this.$refs.table.innerHTML);
    },
    toHtml() {
      exportFile('a.html', this.$refs.table.innerHTML);
    },
    toWord() {
      exportFile('a.docx', this.tableData, ['日期', '姓名', '地址']);
    },
    toExcel() {
      exportFile('a.xlsx', this.tableData, ['日期', '姓名', '地址'], 'worksheet名称');
    },
    // 开始拖拽事件
    onStart() {
      this.drag = true;
    },
    onChange(item, index) {
      this.$nextTick(() => {
        this.myArray.splice(index, 1);
        this.noArray.push(item);
      });
    },
    addChange(item, index) {
      this.$nextTick(() => {
        this.noArray.splice(index, 1);
        this.myArray.push(item);
      });
    },
    // move回调方法
    onMove(e, originalEvent) {
      // 不允许停靠
      // if (e.relatedContext.element.id == 1) return false;
      // // 不允许拖拽
      // if (e.draggedContext.element.id == 4) return false;
      // return true;
    },
  },
};
</script>
<style>
/*被拖拽对象的样式*/
.item {
  padding: 6px;
  background-color: #fdfdfd;
  border: solid 1px #eee;
  margin-bottom: 10px;
  cursor: move;
}
/*选中样式*/
.chosen {
  /* border: solid 2px #3089dc !important; */
}
.switch {
  float: right;
}

/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all 0.3s ease;
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
