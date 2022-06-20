<!--
 * @Author: your name
 * @Date: 2021-10-15 15:08:44
 * @LastEditTime: 2021-11-17 14:05:48
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
    <el-row :gutter="10" class="mb16">
      <el-col :span="12">
        <el-button type="primary" @click="$refs.dialogList.show()">添 加</el-button>
      </el-col>
      <el-col :span="12" class="text-right">
        <el-button @click="onClear">清 空</el-button>
        <el-button @click="onDelete">删 除</el-button>
      </el-col>
    </el-row>
    <el-form ref="form" :model="form" :rules="rules">
      <el-table
        :data="list"
        :header-cell-style="$rowStyle.DEFAULT"
        :max-height="maxHeight"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" :selectable="checkSelectable" width="55" align="center" />
        <el-table-column label="商品名称" prop="spuName" align="center"></el-table-column>
        <el-table-column label="分包数量" prop="num" align="center">
          <template slot-scope="scope">
            <BjInputNumber v-model="scope.row.num" />
          </template>
        </el-table-column>
        <el-table-column label="操 作" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="onDelete(scope.row, scope.$index)">删 除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>

    <div v-if="pagingInfo.total > 0" class="text-right mt16">
      <el-pagination
        :current-page.sync="pagingInfo.page"
        :page-size.sync="pagingInfo.size"
        :total="pagingInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <!-- 按钮 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
    <!-- 商品 -->
    <BjDialogList
      ref="dialogList"
      :config="dialogListConfig"
      multiple
      :disabled="disabled"
      @confirm="onConfirmDialogList"
    />
  </el-dialog>
</template>
<script>
import { goodsApi } from '@/api';
import { PAGE_SIZES } from '@/utils/constants';
const defaultPage = function () {
  return {
    total: 0,
    page: 1,
    size: PAGE_SIZES[0],
  };
};
export default {
  props: {
    title: {
      type: String,
      default: '标题',
    },
    width: {
      type: String,
      default: '800px',
    },
    pageSizes: {
      type: Array,
      default() {
        return PAGE_SIZES;
      },
    },
  },
  data() {
    return {
      visible: false,
      list: [],
      form: {},
      rules: {},
      selectedData: [],
      /** 选择商品 */
      dialogListConfig: {
        title: '选择商品',
        columns: [
          {
            prop: 'spuCode',
            label: '商品编码',
          },
          {
            prop: 'spuName',
            label: '商品名称',
          },
        ],
        search: [
          {
            placeholder: '请输入商品编码',
            key: 'spuCode',
          },
          {
            placeholder: '请输入商品名称',
            key: 'spuName',
          },
        ],
        actionApi: goodsApi.getSku,
        query: '',
      },
      maxHeight: '400px',
      /** 商品禁选项 */
      disabled: [],
      pagingInfo: defaultPage(),
    };
  },
  methods: {
    show() {
      this.visible = true;
    },
    hide() {
      this.visible = false;
    },
    /** 清空 */
    onClear() {
      this.$confirm('您正在永久删除该数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.list = [];
          this.msgSuccess('操作成功');
        })
        .catch(() => {});
      console.log('清空');
    },
    /** 删除  */
    onDelete(row, index) {
      this.$confirm('您正在永久删除该数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          // this.msgSuccess('操作成功');
          // console.log(index);
          if (index >= 0) this.list.splice(index, 1);
        })
        .catch(() => {});
    },
    onConfirmDialogList(data) {
      this.disabled = data.map(e => e.id);
      this.list = [
        ...this.list,
        ...data.map(e => {
          return {
            id: e.id,
            spuName: e.spuName,
            num: null,
          };
        }),
      ];
    },
    /** 选中数据 */
    handleSelectionChange(data) {
      this.selectedData = [...data];
      // console.log('BjDialogList：多选-选中数据', this.selectedData);
    },
    /** 设置禁止选中的条件 */
    checkSelectable(row, index) {
      return !this.disabled.includes(row[this.rowKeys]);
    },
    handleClose() {
      this.visible = false;
    },
    /** 确 定 */
    confirm() {
      this.hide();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
  },
};
</script>
<style lang="scss" scoped></style>
