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
          <el-form-item label="消息编码" prop="messageCode">
            <el-input
              v-model.trim="queryParams.messageCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="消息名称" prop="messageName">
            <el-input
              v-model.trim="queryParams.messageName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="消息类型" prop="subcategoryCode">
            <BjSelectLov
              v-model="queryParams.subcategoryCode"
              lov-code="HMSG.MESSAGE_SUBCATEGORY"
            /> </el-form-item
        ></el-col>
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
              <template v-else-if="col.prop === 'serverList'">
                <el-tag
                  v-for="(server, key) in scope.row.serverList"
                  :key="key"
                  :class="`server-tag server-${server.typeCode}`"
                  :type="
                    server.typeCode == 'SMS' ? 'success' : server.typeCode == 'EMAIL' ? '' : 'info'
                  "
                  >{{ server.typeMeaning }}</el-tag
                >
              </template>
              <el-switch
                v-else-if="col.prop === 'enabledFlag'"
                v-model="scope.row[col.prop]"
                disabled
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
              <el-button type="text" class="delete-btn" @click="onAction('删除', props.row)"
                >删除</el-button
              >
              <el-button type="text" @click="onAction('测试发送', props.row)">测试发送</el-button>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <DataForm ref="DataForm" @reFresh="reFresh" @handleConfirm="handleDataFormConfirm" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { messageApi } from '@/api';
import DataForm from './components/DataForm.vue';
export default {
  name: 'SettingMessageSendIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: messageApi.getSend,
      // 查询参数
      queryParams: {
        messageCode: null,
        messageName: null,
        serverName: null,
      },
      title: '',
      labelList: [
        // 账户编码：邮箱账户的唯一编码标识。
        // 账户名称：邮箱账户的名称。
        // 邮件服务器：邮箱账户使用的服务器。
        // 端口：邮箱账户使用的服务器的端口。
        // 安全策略：邮箱账户的安全策略，包含黑名单和白名单。
        // 状态：邮箱账户的状态，包括启用 禁用。
        // 操作：可进行编辑和测试发送。

        { label: '消息编码', prop: 'messageCode', isCheck: true, disabled: false },
        { label: '消息名称', prop: 'messageName', isCheck: true, disabled: false },
        { label: '启用服务', prop: 'serverList', isCheck: true, disabled: false },
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
          this.$router.push({ name: 'SettingMessageSendAdd' });

          break;
        case '编辑':
          this.$router.push({ name: 'SettingMessageSendEdit', query: { id: row.messageCode } });

          break;
        case '删除':
          this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
            .then(() => {
              this.batchDeleteApi(row);
            })
            .catch(() => {});

          break;

        case '测试发送':
          this.$refs.DataForm.show(this.$lodash.clone(row));

          break;
      }
    },
    handleDataFormConfirm(obj) {},
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
      messageApi.deleteSend(API_DATA).then(res => {
        console.log(res);
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
<style lang="scss" scoped>
.server-tag {
  margin-right: 8px;
}
.server-tag:last-child {
  margin-right: 0;
}
</style>
