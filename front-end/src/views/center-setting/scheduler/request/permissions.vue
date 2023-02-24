<template>
  <div class="app-container">
    <!-- sort="orderSeq" -->
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :auto-fetch="false"
      search-btn-hide
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableOperation>
        <el-form :inline="true" label-width="100px">
          <el-form-item label="请求编码：">
            {{ $route.query.concurrentCode }}
          </el-form-item>
          <el-form-item label="请求名称：">
            {{ $route.query.concurrentName }}
          </el-form-item>
        </el-form>
        <br />
        <el-button type="primary" class="main-btn" @click="onAdd">新增</el-button>
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
              <span v-else-if="col.prop === 'enabledFlag'">
                {{ scope.row.enabledFlag }},
                {{ STATUS_ENABLED_NUM.bjGet(scope.row.enabledFlag) }}
              </span>
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
              <!-- <el-button type="text"  @click="onAction('删除', props.row)"
                 class='delete-btn'>删除</el-button
              > -->
              <el-button type="text" class="edit-btn" @click="onAction('编辑', props.row)"
                >编辑</el-button
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
import { settingApi } from '@/api';
import { STATUS_ENABLED_NUM } from '@/utils/constants';

import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingSchedulerRequestPermissions',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_NUM,
      queryApi: settingApi.getRequestPermissions,
      // 查询参数
      queryParams: {
        concurrentId: null,
        concurrentName: null,
      },

      labelList: [
        { label: '租户', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '角色', prop: 'roleName', isCheck: true, disabled: false },
        { label: '有效期从', prop: 'startDate', isCheck: true, disabled: false, width: 135 },
        { label: '有效期至', prop: 'endDate', isCheck: true, disabled: false, width: 135 },
        { label: '限制次数', prop: 'limitQuantity', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag ', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],

      info: null,
    };
  },
  computed: {},

  mounted() {
    this.queryParams.concurrentId = this.$route.query.concurrentId || '';
    this.$refs.BjPage.fetchData();
  },
  methods: {
    onAction(action, row) {
      switch (action) {
        case '编辑':
          this.onEdit(row);
          break;
      }
    },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },
    /** ******* API ************ */
  },
};
</script>
<style lang="scss" scoped></style>
