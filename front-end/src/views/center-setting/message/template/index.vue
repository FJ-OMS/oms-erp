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
          <el-form-item label="消息模板编码" prop="templateCode">
            <el-input
              v-model.trim="queryParams.templateCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="消息模板名称" prop="templateName">
            <el-input
              v-model.trim="queryParams.templateName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="语言" prop="lang">
            <BjSelectLov v-model="queryParams.lang" lov-code="BJ_LANGUAGE" /> </el-form-item
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
              <span v-else-if="col.prop === 'lang'">
                {{ $store.state.values.BJ_LANGUAGE.bjGet(scope.row.lang) }}
              </span>
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
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { messageApi } from '@/api';

export default {
  name: 'SettingMessageTemplate',
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: messageApi.getTemplates,
      // 查询参数
      queryParams: {
        templateCode: null,
        templateName: null,
        lang: null,
      },
      title: '',
      labelList: [
        { label: '消息模板编码', prop: 'templateCode', isCheck: true, disabled: false, width: 300 },
        {
          label: '消息模板名称',
          prop: 'templateName',
          isCheck: true,
          disabled: false,
          width: 'auto',
        },
        { label: '语言', prop: 'lang', isCheck: true, disabled: false },
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
          this.$router.push({ name: 'SettingMessageTemplateAdd' });

          break;
        case '编辑':
          this.$router.push({
            name: 'SettingMessageTemplateEdit',
            query: { id: row.templateId },
          });

          break;
      }
    },

    // /** 批量删除操作 */
    // onDeleteSelected() {
    //   if (!this.selectedRows.length) {
    //     this.$message.warning('请选择');
    //     return;
    //   }

    //   this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi(this.selectedRows);
    //     })
    //     .catch(() => {});
    // },

    // batchDeleteApi(API_DATA) {
    //   messageApi.deleteTemplates(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    // operationApi(API_DATA, KEY = 'publishTemplates') {
    //   messageApi[KEY](API_DATA).then(res => {
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
<style lang="scss" scoped></style>
