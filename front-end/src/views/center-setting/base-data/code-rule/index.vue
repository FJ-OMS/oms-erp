<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="规则编码" prop="ruleCode">
            <el-input
              v-model.trim="queryParams.ruleCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="规则名称" prop="ruleName">
            <el-input
              v-model.trim="queryParams.ruleName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>

      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:SettingBaseDataCodeRule_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >

          <el-button
            v-permission:SettingBaseDataCodeRule_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增</el-button
          >
        </el-col>
      </template>
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
              <el-switch
                v-else-if="col.prop === 'status'"
                v-model="scope.row.status"
                @change="onStatus($event, scope.row)"
              />
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
                v-permission:SettingBaseDataCodeRuleEdit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:SettingBaseDataCodeRule_Delete
                :disabled="scope.row.status"
                type="text"
                class="delete-btn"
                @click="onDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { baseDataApi } from '@/api';
import DataForm from './components/DataForm.vue';
import { STATUS_ENABLED_TRUE } from '@/utils/constants';
export default {
  name: 'SettingBaseDataCodeRuleIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      queryApi: baseDataApi.getCodeRule,
      // 查询参数
      queryParams: {
        ruleCode: null,
        ruleName: null,
      },

      labelList: [
        { label: '级别', prop: 'meaning', isCheck: true, disabled: false },
        { label: '租户', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '规则编码', prop: 'ruleCode', isCheck: true, disabled: false },
        { label: '规则名称', prop: 'ruleName', isCheck: true, disabled: false },
        { label: '规则描述', prop: 'description', isCheck: true, disabled: false },

        // { label: '操作人员', prop: 'createdBy', isCheck: true, disabled: false, width: 135 },
        // { label: '创建时间', prop: 'createDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    /** 禁用 */
    onStatus(flag, row) {
      console.log(flag, row);
      this.operationApi({
        id: row.id,
        flag: flag,
        objectVersionNumber: row.objectVersionNumber,
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
          this.batchDeleteApi([row]);
        })
        .catch(() => {});
    },
    // /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi(this.selectedRows);
        })
        .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$router.push({
        name: 'SettingBaseDataCodeRuleEdit',
        query: {
          id: row.ruleId,
          code: row.ruleCode,
        },
      });
    },

    operationApi(API_DATA) {
      baseDataApi.operationSurplusQtyReplenish(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // deleteApi(API_DATA) {
    //   baseDataApi.deleteOnlineShop(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    batchDeleteApi(API_DATA) {
      baseDataApi.deleteCodeRule(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
