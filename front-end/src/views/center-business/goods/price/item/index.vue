<template>
  <div class="app-container">
    <!--  sort="orderSeq" -->
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      is-list
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableColumn>
        <template v-for="(col, index) in dropCols">
          <el-table-column
            v-if="col.prop !== 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || false"
            align="center"
            class-name="item"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <span v-else-if="col.prop === 'constant'">{{
                GOODS_PRICE_ITEMS.bjGet(scope.row.value)
              }}</span>

              <el-tag
                v-else-if="col.prop === 'enabledFlag'"
                :type="scope.row[col.prop] ? 'success' : 'info'"
                >{{ STATUS_ENABLED_NUM.bjGet(scope.row[col.prop]) }}</el-tag
              >
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="scope">
              <el-button
                v-permission:BusinessGoodsPriceItemIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >
              <el-button
                v-if="!scope.row.enabledFlag"
                v-permission:BusinessGoodsPriceItemIndex_Status
                type="text"
                @click="onStatus(1, scope.row)"
                >启用</el-button
              >
              <el-button
                v-if="scope.row.enabledFlag"
                v-permission:BusinessGoodsPriceItemIndex_Status
                :disabled="scope.row.value == 'costPrice' || scope.row.value == 'salesPrice'"
                type="text"
                @click="onStatus(0, scope.row)"
                >禁用</el-button
              >
              <!-- <el-button v-permission:BusinessGoodsPriceItemIndex_Delete type="text"  @click="onDelete(scope.row)" class='delete-btn'>删除</el-button> -->
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="500px"
      append-to-body
    >
      <el-form ref="form" class="bj-label-colon" :model="form" :rules="rules" label-width="120px">
        <el-col :span="22">
          <BjFormItemCode v-model="form.value" :readonly="!isAdd" label="价格项编码" prop="value" />
        </el-col>
        <el-col :span="22">
          <el-form-item label="通用价格项" prop="constant">
            <el-input
              v-if="isAdd"
              v-model.trim="form.constant"
              :maxlength="MAX_LENGTH.SHORT_NAME"
              placeholder="请输入"
              clearable
            />
            <span v-if="!isAdd">{{ GOODS_PRICE_ITEMS.bjGet(form.value) }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="平台使用名" prop="meaning">
            <el-input
              v-model.trim="form.meaning"
              :maxlength="MAX_LENGTH.SHORT_NAME"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="备注" prop="description">
            <el-input
              v-model.trim="form.description"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="22">
          <el-form-item label="状态" prop="enabledFlag">
            <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
          </el-form-item>
        </el-col> -->
        <!-- <el-col :span="22">
          <el-form-item label="排序" prop="orderSeq">
            <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model.trim="form.orderSeq" placeholder="请输入" clearable />
          </el-form-item>
        </el-col> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleConfirm('form')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
// TODO:  系统维护好价格项，不能添加修改，只能隐藏/开启，默认成本价、销售价开启
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import { GOODS_PRICE_ITEMS, STATUS_ENABLED_NUM } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    orderSeq: 0,
    value: null,
    meaning: null,
    description: null,
    enabledFlag: 1,
  };
};

export default {
  name: 'BusinessGoodsPriceItemIndex',
  mixins: [pageMiXin],
  data() {
    return {
      GOODS_PRICE_ITEMS,
      STATUS_ENABLED_NUM,
      selection: false,
      queryApi: goodsApi.getListPriceItem,
      // 查询参数
      queryParams: {},
      title: '',
      dialogVisible: false,
      // 表单参数
      isAdd: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        value: [{ required: true, message: '请输入价格项编码', trigger: 'blur' }],
        meaning: [{ required: true, message: '请输入价格项名称', trigger: 'blur' }],
        // orderSeq: [{ required: true, message: '请输入排序值（纯数字）', trigger: 'blur' }],
      },
      labelList: [
        { label: '价格项编码', prop: 'value', isCheck: true, disabled: false },
        { label: '通用价格项', prop: 'constant', isCheck: true, disabled: false },
        { label: '平台使用名', prop: 'meaning', isCheck: true, disabled: false },
        { label: '备注', prop: 'description', isCheck: true, disabled: false },
        // { label: '排序', prop: 'orderSeq', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {
    'form.orderSeq'(v) {
      this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    },
    'form.value'(v) {
      this.form.value = this.form.value.replace(/\s+/g, '');
    },
  },
  mounted() {
    // this.$refs.BjPage.tableData = [{}];
  },
  methods: {
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_NUM.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.meaning}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.updateApi({
            ...row,
            enabledFlag: flag,
          });
        })
        .catch(() => {});
    },
    handleConfirm(formName) {
      console.log('确认', this.form);

      if (
        this.$refs.BjPage.tableData.find(
          row => row.meaning == this.form.meaning && row.value != this.form.value,
        )
      ) {
        this.$message.warning('平台使用名重复，请重新输入');
        return;
      }

      const API_DATA = this.$lodash.clone(this.form);
      // if (!this.isAdd) {
      //   API_DATA['_innerMap'] = {};
      // }
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isAdd) {
          this.addApi(API_DATA);
        } else {
          this.updateApi(API_DATA);
        }
      });
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ value: row.value });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      this.$message.warning('TODO: no api');
      // if (!this.selectedRows.length) {
      //   this.$message.warning('请选择');
      //   return;
      // }
      // const ids = this.selectedRows.map(e => e.id);
      // this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning',
      // })
      //   .then(() => {
      //     this.batchDeleteApi({ ids: ids });
      //   })
      //   .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      this.isAdd = true;
      this.title = '添加价格项';
      this.form = getDefaultForm();
      this.dialogVisible = true;
    },
    /** 编辑操作 */
    onEdit(row) {
      this.isAdd = false;
      this.title = '编辑价格项';
      this.dialogVisible = true;
      this.selectedRow = this.$lodash.clone(row);
      this.form = this.$lodash.clone(row);
    },
    /* 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false;
    },
    // 修改API
    updateApi(API_DATA) {
      goodsApi.updatePriceItem(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.dialogVisible = false;
            this.reFresh();
            // 刷新价格项值集的缓存
            this.$store.dispatch('values/getValueList', {
              POST_KEY: 'PRICE.ITEM.TYPE',
              isCache: false,
            });
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      goodsApi.addPriceItem(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.dialogVisible = false;
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      goodsApi.deletePriceItem(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
            // 刷新价格项值集的缓存
            this.$store.dispatch('values/getValueList', {
              POST_KEY: 'PRICE.ITEM.TYPE',
              isCache: false,
            });
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
