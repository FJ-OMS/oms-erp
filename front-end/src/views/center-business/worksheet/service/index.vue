<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'90px'"
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
          <el-form-item label="仓储名称" prop="installServiceCode">
            <el-input
              v-model.trim="queryParams.installServiceCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="name">
            <el-select v-model.trim="queryParams.name" placeholder="请选择" clearable>
              <el-option
                v-for="(item, index) in STATUS_ENABLED_TRUE"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" class="main-btn" @click="onAdd">新增</el-button>
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
              <span v-else-if="col.prop === 'status'">{{
                STATUS_ENABLED_TRUE.bjGet(scope.row.status)
              }}</span>
              <span v-else-if="col.prop === 'money'">{{ $number2money(scope.row.money) }}</span>

              <span v-else-if="col.prop === 'serviceZone'">{{
                (scope.row.serviceZone || [])
                  .map(e => e.cityDetail.levelTwoAreaName + e.cityDetail.levelThreeAreaName)
                  .join('，')
              }}</span>

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
              <el-button type="text" @click="onView(props.row)">查看</el-button>
              <el-button type="text" class="edit-btn" @click="onEdit(props.row)">编辑</el-button>
              <el-button type="text" class="delete-btn" @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { worksheetApi } from '@/api';
import { STATUS_ENABLED_TRUE } from '@/utils/constants';
import DataForm from './components/DataForm.vue';

export default {
  name: 'BusinessWorksheetServiceIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      queryApi: worksheetApi.getInstallServices,
      // 查询参数
      queryParams: {
        installServiceCode: null,
        name: null,
      },

      labelList: [
        { label: '安装服务编码', prop: 'installServiceCode', isCheck: true, disabled: false },
        { label: '安装服务名称', prop: 'name', isCheck: true, disabled: false },
        { label: '安装类别', prop: 'type', isCheck: true, disabled: false },
        { label: '配备人员/人', prop: 'peopleAmount', isCheck: true, disabled: false },
        { label: '服务费用/元', prop: 'money', isCheck: true, disabled: false },
        { label: '服务地区', prop: 'serviceZone', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdBy', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    /** 新增门店 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      worksheetApi.DetailInstallServices({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$refs.DataForm.show(this.$lodash.clone(res));
        }
      });
    },
    onView(row) {
      this.$toPage('BusinessWorksheetServiceView', { id: row.id });
    },
    // /** 禁用 */
    // onStatus(flag, row) {
    //   this.operationWarehouse({
    //     id: row.id,
    //     objectVersionNumber: row.objectVersionNumber,
    //     flag: flag,
    //   });
    // },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteWarehouse({
            ids: [row.id],
          });
        })
        .catch(() => {});
    },

    /**  */

    // 删除API
    deleteWarehouse(API_DATA) {
      worksheetApi.deleteInstallServices(API_DATA).then(res => {
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
<style scoped lang="scss">
.dialogForm {
  .el-input {
    width: 240px;
  }
}
</style>
