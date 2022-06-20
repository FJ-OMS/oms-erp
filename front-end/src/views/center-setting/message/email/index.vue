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
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" class="main-btn" @click="onAction('新建')">新增</el-button>
        </el-col>
      </template>
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="账户编码" prop="serverCode">
            <el-input
              v-model.trim="queryParams.serverCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="账户名称" prop="serverName">
            <el-input
              v-model.trim="queryParams.serverName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabledFlag">
            <BjSelectLov v-model="queryParams.enabledFlag" constant-key="STATUS_ENABLED_NUM" />
          </el-form-item>
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
                v-else-if="col.prop === 'enabledFlag'"
                v-model="scope.row[col.prop]"
                :active-value="1"
                :inactive-value="0"
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
            <template slot-scope="props">
              <el-button type="text" class="edit-btn" @click="onAction('编辑', props.row)"
                >编辑</el-button
              >
              <el-button type="text" @click="onAction('设置黑白名单', props.row)"
                >设置黑白名单</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <DrawerTable ref="DrawerTable" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { messageApi } from '@/api';
import DrawerTable from './components/DrawerTable.vue';
export default {
  name: 'SettingMessageEmailIndex',
  components: {
    DrawerTable,
  },
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: messageApi.getEmail,
      // 查询参数
      queryParams: {
        serverCode: null,
        serverName: null,
        enabledFlag: null,
      },
      title: '',
      labelList: [
        // 账户编码：邮箱账户的唯一编码标识。
        // 账户名称：邮箱账户的名称。
        // 邮件服务器：邮箱账户使用的服务器。
        // 端口：邮箱账户使用的服务器的端口。
        // 安全策略：邮箱账户的安全策略，包含黑名单和白名单。
        // 状态：邮箱账户的状态，包括启用 禁用。
        // 操作：可进行编辑和设置黑白名单。

        { label: '账户编码', prop: 'serverCode', isCheck: true, disabled: false },
        { label: '账户名称', prop: 'serverName', isCheck: true, disabled: false },
        { label: '邮件服务器', prop: 'host', isCheck: true, disabled: false },
        { label: '端口', prop: 'port', isCheck: true, disabled: false },
        { label: '安全策略', prop: 'filterStrategyMeaning', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },

        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    onAction(action, row) {
      switch (action) {
        case '新建':
          this.$router.push({ name: 'SettingMessageEmailAdd' });

          break;
        case '编辑':
          this.$router.push({ name: 'SettingMessageEmailEdit', query: { serverId: row.serverId } });

          break;
        case '设置黑白名单':
          this.$refs.DrawerTable.show(this.$lodash.clone(row));
          this.$refs.DrawerTable.setTitle('设置黑白名单');
          break;
      }
    },

    /** 批量删除操作 */
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

    batchDeleteApi(API_DATA) {
      messageApi.deleteEmail(API_DATA).then(res => {
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
