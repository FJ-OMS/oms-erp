<!--
 * @Author: your name
 * @Date: 2021-10-15 15:08:44
 * @LastEditTime: 2021-10-18 17:43:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\source\parcel\components\dialogList.vue
-->
<template>
  <el-dialog
    v-if="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    :width="width"
    append-to-body
  >
    <div class="text-right mb16">
      <el-button type="primary" @click="$refs.dialogList.show()">选择套餐</el-button>
    </div>
    <BjDivider>套 餐</BjDivider>
    <el-table :data="list" border>
      <el-table-column label="套餐编码" prop="" align="center"></el-table-column>
      <el-table-column label="套餐名称" prop="" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="onDelete(scope.row)">删 除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <BjDivider>分包明细</BjDivider>
    <el-table :data="list" border>
      <el-table-column label="商品编码" prop="" align="center"></el-table-column>
      <el-table-column label="商品名称" prop="" align="center"></el-table-column>
      <el-table-column label="套餐内数量" prop="" align="center"></el-table-column>
      <el-table-column label="分包数量" prop="" align="center">
        <template slot-scope="scope">
          <BjInputNumber v-model="scope.row.num" />
        </template>
      </el-table-column>
    </el-table>
    <!-- 按钮 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
    <!-- 商品 -->
    <BjDialogList
      ref="dialogList"
      :config="$BjDialogListConfig.skuDefault()"
      @confirm="onConfirmDialogList"
    />
  </el-dialog>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: '添加套餐商品',
    },
    width: {
      type: String,
      default: '800px',
    },
  },
  data() {
    return {
      visible: false,
    };
  },
  methods: {
    show() {
      this.visible = true;
    },
    hide() {
      this.visible = false;
    },
    onDelete(row) {},
    handleClose() {
      this.hide();
    },
    onConfirmDialogList(data) {
      console.log(data);
    },
    confirm() {
      this.hide();
    },
  },
};
</script>
<style lang="scss" scoped></style>
