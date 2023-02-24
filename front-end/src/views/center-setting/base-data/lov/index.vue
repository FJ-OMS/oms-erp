<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :auto-fetch="false"
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
          <el-form-item label="值集" prop="lovCode">
            <BjSelectLov v-model="queryParams.lovCode" :option-list="LOV_CODE" />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="名称" prop="meaning">
            <el-input
              v-model.trim="queryParams.meaning"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="colSpan">
          <BjFormItemCode v-model="queryParams.value" label="编码" prop="value" />
        </el-col> -->

        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabledFlag">
            <el-select
              v-model.trim="queryParams.enabledFlag"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in STATUS_ENABLED_NUM"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:BusinessChannelOnlineList_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessChannelOnlineList_Add
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

              <el-tag
                v-else-if="col.prop === 'enabledFlag'"
                :type="scope.row.enabledFlag ? 'success' : 'info'"
                >{{ STATUS_ENABLED_NUM.bjGet(scope.row.enabledFlag) }}</el-tag
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
            <template slot-scope="props">
              <el-button
                v-permission:BusinessChannelOnlineList_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.enabledFlag"
                v-permission:BusinessChannelOnlineList_Status
                type="text"
                @click="onStatus(0, props.row)"
                >禁用</el-button
              >
              <el-button
                v-if="!props.row.enabledFlag"
                v-permission:BusinessChannelOnlineList_Status
                type="text"
                @click="onStatus(1, props.row)"
                >启用</el-button
              >

              <!-- <el-button
                v-permission:BusinessChannelOnlineList_Set
                type="text"
                @click="onSet(props.row)"
                >设置</el-button
              > -->
              <el-button
                v-permission:BusinessChannelOnlineList_Delete
                :disabled="!!props.row.enabledFlag"
                type="text"
                class="delete-btn"
                @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { baseDataApi } from '@/api';
import { LOV_CODE, STATUS_ENABLED_NUM } from '@/utils/constants';
import DataForm from './components/DataForm.vue';

export default {
  name: 'BusinessChannelLovIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      LOV_CODE,
      STATUS_ENABLED_NUM,
      queryApi: baseDataApi.getLovValuesByLovId,
      // 查询参数
      queryParams: {
        lovCode: 'SCHL.CHANNEL_TYPE',
        lovId: null,
        value: null,
        meaning: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '名称', prop: 'meaning', isCheck: true, disabled: false },
        { label: '编码', prop: 'value', isCheck: true, disabled: false },
        { label: '排序', prop: 'orderSeq', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      selectedRow: null,
    };
  },
  watch: {
    'queryParams.lovCode': {
      handler() {
        this.getLovCode();
      },
      immediate: true,
    },
  },
  mounted() {},
  methods: {
    getLovCode() {
      baseDataApi.getLovHeadersByLovCode({ lovCode: this.queryParams.lovCode }).then(res => {
        if (res.lovId) {
          this.queryParams.lovId = res.lovId;
          this.reFresh();
        }
      });
    },
    /** 新增 */
    onAdd() {
      this.$refs.DataForm.show({
        lovCode: this.queryParams.lovCode,
        lovId: this.queryParams.lovId,
      });
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
      // baseDataApi.getOnlineShopDetails({ id: row.id }).then(res => {
      //   if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
      //     this.$refs.DataForm.show(this.$lodash.clone(res));
      //   }
      // });
    },
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_NUM.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.meaning}，是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            ...row,
            enabledFlag: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除 ${row.meaning}，是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi([row]);
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      if (this.selectedRows.some(e => e.enabledFlag == 1)) {
        this.$message.warning('您选中了正在启用的数据，不可删除');
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
    operationApi(API_DATA) {
      baseDataApi.updateLovValues(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      baseDataApi.deleteLovValues(API_DATA).then(res => {
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
