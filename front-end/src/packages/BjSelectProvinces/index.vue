<!--
 * @Author: your name
 * @Date: 2021-09-09 16:10:16
 * @LastEditTime: 2022-02-28 10:20:54
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjSelectProvinces\index.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="dialogVisible"
    :width="width"
    :before-close="handleClose"
  >
    <div class="Provinces">
      <el-checkbox v-model="checked" class="mb20" @change="checkAll">选择全部</el-checkbox>
      <el-tree
        ref="tree"
        :data="treeOptions"
        :default-checked-keys="defaultCheckedKeys"
        :props="props"
        node-key="areaCode"
        accordion
        show-checkbox
        @check="handleCheck"
      />
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { commonApi } from '@/api';
export default {
  name: 'BjSelectProvinces',
  props: {
    /** 标题 */
    title: {
      type: String,
      default: '选择区域',
    },
    /** 宽度 */
    width: {
      type: String,
      default: '50%',
    },
    // defaultCheckedKeys: {
    //   type: Array,
    //   default: () => {
    //     return [];
    //   }
    // }
  },
  data() {
    return {
      dialogVisible: false,
      props: {
        children: 'children',
        label: 'name',
        // isLeaf: (data, node) => {
        //   if (node.level === 2) {
        //     return true;
        //   }
        // },
      },
      /** 省市数据 */
      treeOptions: [],
      /** 选中数据数组 */
      defaultCheckedKeys: [],
      checked: false,
      changeData: [],
    };
  },
  created() {
    this.getDistrictTree();
  },
  methods: {
    getDistrictTree() {
      commonApi.getDistrictTree().then(res => {
        this.treeOptions = res;
        this.treeOptions.forEach((item, index) => {
          item.children.forEach((e, i) => {
            e.parentIndex = index;
          });
        });
      });
    },
    checkAll(check) {
      if (check) {
        const arr = this.treeOptions.map((item, index) => {
          return {
            name: item.name,
            areaCode: item.areaCode,
          };
        });
        this.$refs.tree.setCheckedNodes(arr);
        this.changeData = this.$lodash.clone(this.treeOptions);
      } else {
        this.$refs.tree.setCheckedKeys([]);
        this.changeData = [];
      }
    },
    show(list, disabledList) {
      // 注意：不能用forEach循环修改数据，否则不会触发视图更新
      /** 禁选部分 */
      this.treeOptions = this.treeOptions.map((item, index) => {
        let num = 0;
        item.disabled = false;
        // 选中的地区设置为禁选
        item.children = item.children.map((e, i) => {
          e.disabled = false;
          // 每个城市是否被选中
          if (disabledList.indexOf(e.areaCode) > -1) {
            num++;
            e.disabled = true;
            // 子集全部禁选，父级改为禁选
            if (item.children.length === num) {
              item.disabled = true;
            }
          }
          return {
            ...e,
          };
        });
        return {
          ...item,
        };
      });

      // 获取选中数据
      this.changeData = this.$lodash.cloneDeep(list);
      const arr = [];
      this.changeData.forEach(item => {
        item.children.forEach(i => {
          arr.push(i);
        });
      });
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes(arr);
      });
    },
    hide() {
      this.dialogVisible = false;
    },
    handleCheck(data, checkData) {
      if (typeof data.children === 'object') {
        // console.log('父集');
        const index = this.changeData.findIndex(e => e.areaCode === data.areaCode);
        if (checkData.checkedKeys.indexOf(data.areaCode) != -1) {
          console.log('父亲选中');
          const DATA = this.$lodash.cloneDeep(data);
          this.changeData.push(DATA);
        } else {
          // console.log('父亲取消');
          this.changeData.splice(index, 1);
        }
      } else {
        // console.log('子项');
        const parent = this.treeOptions[data.parentIndex];
        const index = this.changeData.findIndex(e => e.areaCode === parent.areaCode);
        if (checkData.checkedKeys.indexOf(data.areaCode) != -1) {
          // console.log('子选中');
          if (index === -1) {
            this.changeData.push({
              areaCode: parent.areaCode,
              name: parent.name,
              children: [data],
            });
          } else {
            this.changeData[index].children.push(data);
          }
        } else {
          console.log('子取消');
          const i = this.changeData[index].children.findIndex(e => e.areaCode === data.areaCode);
          this.changeData[index].children.splice(i, 1);
          if (this.changeData[index].children.length === 0) {
            this.changeData.splice(index, 1);
          }
        }
      }
    },
    handleClose(done) {
      done();
    },
    handleConfirm() {
      this.dialogVisible = false;
      this.$emit('confirm', this.$lodash.clone(this.changeData));
    },
  },
};
</script>
<style scoped>
.Provinces {
  height: 360px;
  overflow-y: auto;
}
</style>
