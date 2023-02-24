<template>
  <div class="app-container">
    <BjDivider right-fixed
      >基础信息
      <template slot="right"
        ><el-button @click="handleClose">返 回</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="reFresh">刷新</el-button>
        <el-button v-if="isEdit" type="primary" @click="handleConfirm">保 存</el-button>
      </template>
    </BjDivider>
    <el-form ref="form" :model="form" label-width="130px" class="gray">
      <el-row>
        <el-col :span="24">
          <el-form-item label="供货仓名称：">
            <span> {{ form.supportName }}</span>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="供货仓类型：">
            <span> {{ form.supportTypeMeaning }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="供货仓编码：">
            <span> {{ form.supportCode }}</span>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="接收仓名称：">
            <span> {{ form.receiveName }}</span>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="供货仓类型：">
            <span> {{ form.receiveTypeMeaning }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="供货仓编码：">
            <span> {{ form.receiveCode }}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <BjDivider
      >商品明细
      <template v-if="!isView"
        ><el-button type="primary" @click="showAddSkuDialog">选择供货仓商品</el-button>
      </template></BjDivider
    >

    <BjPagingTable
      ref="BjPagingTable"
      :auto-fetch="false"
      :api="tableApi"
      :default-params="{
        receiveWarehouseId: form.receiveWarehouseId,
        supportWarehouseId: form.supportWarehouseId,
      }"
      :reset-data="resetData"
      :before-modify-paging-info="beforeModifyPagingInfo"
    >
      <template v-slot:tableColumn>
        <el-table-column type="index" width="55" align="center" label="序号" />
        <el-table-column prop="categoryName" label="商品分类" />
        <el-table-column prop="spuCode" label="商品编码" />
        <el-table-column prop="spuName" label="商品名称" />
        <el-table-column prop="skuCode" label="SKU编码" />
        <el-table-column prop="warehouseStructuralQty" label="仓库结构比例" align="center">
          <template slot-scope="props"> {{ props.row.warehouseStructuralQty }} % </template>
        </el-table-column>

        <el-table-column prop="qty" label="商品仓位比例">
          <template slot-scope="props">
            <el-input
              v-if="!isView && isModifyRow(props.row)"
              v-model="props.row.qty"
              :maxlength="MAX_LENGTH.DEFAULT"
              style="width: 50px"
            />
            <span v-else>{{ props.row.qty }}</span> %
          </template>
        </el-table-column>
        <el-table-column v-if="!isView" prop="action" label="操作">
          <template slot-scope="props">
            <el-button
              v-show="!isModifyRow(props.row)"
              :disabled="!props.row.id"
              type="text"
              class="delete-btn"
              @click="onDelete(props.row, props.$index)"
              >删除</el-button
            >
            <el-button
              v-show="!isModifyRow(props.row)"
              type="text"
              class="edit-btn"
              @click="onEdit(props.row)"
              >编辑</el-button
            >

            <el-button v-show="isModifyRow(props.row)" type="text" @click="onSave(props.row)"
              >保存</el-button
            >

            <el-button v-show="isModifyRow(props.row)" type="text" @click="onCancel(props.row)"
              >取消</el-button
            >
          </template>
        </el-table-column>
      </template>
    </BjPagingTable>

    <template v-if="isView">
      <BjDivider>操作日志 </BjDivider>
      <BjLogTable :table-data="info.logList" />
    </template>

    <BjDialogList
      ref="dialogListGoods"
      :config="dialogListGoodsConfig"
      row-keys="skuCode"
      :disabled="disabledIds"
      multiple
      @confirm="onConfirmDialogListGoods"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
import { randomID } from '@/utils';

const getDefaultForm = () => {
  return {
    logList: [
      // {
      //   details: null,
      //   freightSpaceId: null,
      //   id: null,
      //   message: null,
      // },
    ],
    receiveCode: null,
    receiveName: null,
    receiveTypeCode: null,
    receiveTypeMeaning: null,
    receiveWarehouseId: null,
    supportCode: null,
    supportName: null,
    supportTypeCode: null,
    supportTypeMeaning: null,
    supportWarehouseId: null,
  };
};
export default {
  name: 'BusinessStockDistributionRatioViewForm',
  mixins: [viewMiXin],
  props: {
    isAdd: {
      type: Boolean,
      default: false,
    },
    isEdit: {
      type: Boolean,
      default: false,
    },
    isView: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      form: getDefaultForm(),
      tableApi: stockApi.getDistributeSpacesGoods,
      dialogListGoodsConfig: {
        title: '选择商品',
        columns: [
          {
            prop: 'categoryName',
            label: '商品分类',
          },
          {
            prop: 'spuCode',
            label: '商品编码',
          },
          {
            prop: 'spuName',
            label: '商品名称',
          },
          {
            prop: 'skuCode',
            label: 'SKU编码',
          },
        ],
        search: [
          {
            placeholder: '请输入商品名称',
            key: 'spuName',
          },
          {
            placeholder: '请输入商品编码',
            key: 'spuCode',
          },
          {
            placeholder: '请输入SKU编码',
            key: 'skuCode',
          },

          {
            placeholder: '请选择分类',
            key: 'categoryId',
            component: 'BjGoodsTypeTreeSelect',
          },
        ],
        defaultQuery: { supportWarehouseId: null },
        actionApi: stockApi.getMayDistributeSpacesGoods,
        query: '',
      },
      addList: [
        // {id,oldValue:''}
      ],
      editList: [
        // {id,oldValue}
      ],
      disabledIds: [],
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          // this.editRow.init();
          // this.newRow.init();

          this.form = this.$lodash.clone(info);
          this.$nextTick(() => {
            if (this.form.supportWarehouseId) this.$refs.BjPagingTable.fetchData();
          });
        }
      },
    },
  },
  mounted() {},
  methods: {
    isModifyRow(row) {
      return (
        this.addList.find(e => e.BJ_EBC_ID == row.BJ_EBC_ID) ||
        this.editList.find(e => e.BJ_EBC_ID == row.BJ_EBC_ID)
      );
    },
    // 从临时数据中删除
    deleteTempListByRow(row, isSave) {
      if (!row) {
        // 清空
        this.editList = [];
        this.addList = [];
        return;
      }
      const ADD_I = this.addList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
      const EDIT_I = this.editList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
      if (ADD_I != -1) {
        if (isSave) row.qty = Number(row.qty || 0);
        this.addList.splice(ADD_I, 1);
      }
      if (EDIT_I != -1) {
        if (!isSave) row.qty = this.editList[EDIT_I].OLD_VALUE;
        this.editList.splice(EDIT_I, 1);
      }
    },
    // 往【新增】临时数据中添加
    onAdd(row) {
      if (this.addList.find(e => e.BJ_EBC_ID == row.BJ_EBC_ID)) return;
      this.addList.push({
        BJ_EBC_ID: row.BJ_EBC_ID,
        OLD_VALUE: 0,
      });
    },
    // 往【编辑】临时数据中添加
    onEdit(row) {
      if (this.editList.find(e => e.BJ_EBC_ID == row.BJ_EBC_ID)) return;
      this.editList.push({
        BJ_EBC_ID: row.BJ_EBC_ID,
        OLD_VALUE: row.qty,
      });
    },
    // 用API保存当前行数据，同时 从临时数据中删除 当前行
    onSave(row) {
      this.toAddByRowList([row]);
      this.deleteTempListByRow(row, true);
    },
    // 取消操作，同时 从临时数据中删除 当前行
    onCancel(row) {
      if (this.addList.find(e => e.BJ_EBC_ID == row.BJ_EBC_ID)) {
        this.$refs.BjPagingTable.deleteTableData(row);
      }
      this.deleteTempListByRow(row, false);
    },

    resetData(d) {
      this.deleteTempListByRow();
      this.disabledIds = (this.tableData() || []).map(e => e.skuCode);
      return d.map(e => {
        return {
          ...e,
          BJ_EBC_ID: randomID(),
        };
      });
    },
    tableData() {
      return this.$refs.BjPagingTable && this.$refs.BjPagingTable.tableData;
    },
    // 显示 选择商品
    showAddSkuDialog() {
      this.dialogListGoodsConfig.defaultQuery.supportWarehouseId = this.form.supportWarehouseId;
      this.$refs.dialogListGoods.show();
    },
    beforeModifyPagingInfo() {
      // 编辑中的行，判断行数据有没有更改过
      const isUnequal = this.editList.some(e => {
        const FINDER = (this.tableData() || []).find(row => row.BJ_EBC_ID == e.BJ_EBC_ID);
        return FINDER.qty != e.OLD_VALUE;
      });
      if (this.addList.length || (this.editList.length && isUnequal)) {
        return this.$confirm('此操作会丢失临时新增的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            return true;
          })
          .catch(() => {
            return false;
          });
      } else {
        return true;
      }
    },
    // 选中商品回写
    onConfirmDialogListGoods(data) {
      data.forEach(row => {
        row['BJ_EBC_ID'] = randomID();
        if (!this.tableData().find(e => e.skuCode === row.skuCode)) {
          this.onAdd(row);
          this.$refs.BjPagingTable.pushTableData({
            ...row,
          });
        }
      });
    },
    onDelete(row, index) {
      this.$confirm(`您正在删除 ${row.skuCode}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$refs.BjPagingTable.tableData.splice(index, 1);
          this.deleteApi({
            ids: [row.id],
          });
        })
        .catch(() => {});
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm() {
      if (this.tableData().length == 0) {
        this.$message.warning('商品不能为空');
        return;
      }
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.toAddByRowList(this.tableData());
      });
    },
    toAddByRowList(list) {
      const API_DATA = {
        freightSpaceList: list.map(e => {
          return {
            qty: Number(e.qty || 0),
            skuId: e.skuId,
            skuCode: e.skuCode,
          };
        }),
        receiveWarehouseId: this.form.receiveWarehouseId,
        supportWarehouseId: this.form.supportWarehouseId,
      };
      console.log('确认', API_DATA);
      this.addApi(API_DATA);
    },

    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
    addApi(API_DATA) {
      stockApi.addDistributeSpacesRatio(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
        }
      });
    },
    deleteApi(API_DATA) {
      stockApi.deleteDistributeSpacesGoods(API_DATA).then(res => {});
    },
  },
};
</script>
<style lang="scss" scoped></style>
