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
          <el-form-item label="账户编码" prop="serverCode">
            <el-input
              v-model.trim="queryParams.serverCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="账户名称" prop="serverName">
            <el-input
              v-model.trim="queryParams.serverName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="服务类型" prop="serverTypeCode">
            <BjSelectLov
              v-model="queryParams.serverTypeCode"
              lov-code="HMSG.SMS_SERVER_TYPE"
            /> </el-form-item
        ></el-col>
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
              <el-button type="text" class="edit-btn" @click="onEdit(props.row)">编辑</el-button>
              <el-button type="text" class="delete-btn" @click="onDelete(props.row)"
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
import { messageApi } from '@/api';
import DataForm from './components/DataForm.vue';
export default {
  name: 'SettingMessageSmsIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: messageApi.getSMS,
      // 查询参数
      queryParams: {
        serverCode: null,
        serverName: null,
        serverTypeCode: null,
      },

      labelList: [
        // 账户编码：短信服务的唯一编码标识。
        // 账户名称：短信服务的名称。
        // 服务类型：短信服务类型，目前支持阿里云 腾讯云 百度云短信服务。
        // 短信签名：短信服务提供的功能，短信发送时会根据客户的设置，在短信内容里附加个性化签名，再发送给被叫手机客户。
        // 状态：短信服务的状态，包括启用 禁用。
        // 操作：可进行编辑操作。

        { label: '账户编码', prop: 'serverCode', isCheck: true, disabled: false },
        { label: '账户名称', prop: 'serverName', isCheck: true, disabled: false },
        {
          label: '服务类型',
          prop: 'serverTypeMeaning',
          isCheck: true,
          disabled: false,
          width: '500',
        },
        { label: '短信签名', prop: 'signName', isCheck: true, disabled: false },

        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    // /** 删除 */
    onDelete(row) {
      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi(row);
        })
        .catch(() => {});
    },
    // /** 批量删除操作 */
    // onDeleteSelected() {
    //   if (!this.selectedRows.length) {
    //     this.$message.warning('请选择');
    //     return;
    //   }
    //   const ids = this.selectedRows.map(e => e.id);
    //   this.$confirm(
    //     '您正在删除所选的数据, 确认所选类别没有关联商品, 如有请删除关联的商品方可删除。是否继续？',
    //     '提示',
    //     {
    //       confirmButtonText: '确定',
    //       cancelButtonText: '取消',
    //       type: 'warning',
    //     },
    //   )
    //     .then(() => {
    //       this.batchDeleteApi({ ids: ids });
    //     })
    //     .catch(() => {});
    // },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },

    // operationApi(API_DATA) {
    //   messageApi.operationOnlineShop(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    deleteApi(API_DATA) {
      messageApi.deleteSMS(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // batchDeleteApi(API_DATA) {
    //   messageApi.batchDeleteMaterial(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
