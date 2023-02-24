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
          <el-form-item label="发布类别" prop="receiverTypeCode">
            <BjSelectLov
              v-model="queryParams.receiverTypeCode"
              lov-code="HMSG.NOTICE.RECERVER_TYPE"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="公告类型" prop="noticeTypeCode">
            <BjSelectLov
              v-model="queryParams.noticeTypeCode"
              lov-code="HMSG.NOTICE.NOTICE_TYPE.CH"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="公告标题" prop="title">
            <el-input
              v-model.trim="queryParams.title"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <!--公告状态，值集：HMSG.NOTICE.STATUS ,  发布、已删除、已发布 -->
        <el-col :span="colSpan">
          <el-form-item label="公告状态" prop="statusCode">
            <BjSelectLov
              v-model="queryParams.statusCode"
              lov-code="HMSG.NOTICE.STATUS"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="公告内容" prop="noticeContent">
            <el-input
              v-model.trim="queryParams.noticeContent"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="创建日期从" prop="fromDate">
            <el-date-picker
              v-model="queryParams.fromDate"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="创建日期至" prop="toDate">
            <el-date-picker
              v-model="queryParams.toDate"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="发布日期从" prop="publishedDateFrom">
            <el-date-picker
              v-model="queryParams.publishedDateFrom"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="发布日期至" prop="publishedDateTo">
            <el-date-picker
              v-model="queryParams.publishedDateTo"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
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
              <el-switch v-else-if="col.prop === 'status'" v-model="scope.row[col.prop]" />
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
              <el-button
                v-if="props.row.statusCode === 'DRAFT'"
                type="text"
                @click="onAction('发布', props.row)"
                >发布</el-button
              >
              <el-button
                v-if="props.row.statusCode === 'PUBLISHED'"
                type="text"
                @click="onAction('撤消', props.row)"
                >撤消</el-button
              >
              <el-button type="text" class="delete-btn" @click="onAction('删除', props.row)"
                >删除</el-button
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
  name: 'SettingMessageNoticeIndex',
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: messageApi.getNotices,
      // 查询参数
      queryParams: {
        receiverTypeCode: null,
        noticeTypeCode: null,
        title: null,
        statusCode: null,
        noticeContent: null,
        fromDate: null,
        toDate: null,
        publishedDateFrom: null,
        publishedDateTo: null,
      },
      title: '',
      labelList: [
        { label: '发布类别', prop: 'receiverTypeMeaning', isCheck: true, disabled: false },
        { label: '公告类型', prop: 'noticeTypeMeaning', isCheck: true, disabled: false },
        { label: '公告标题', prop: 'title', isCheck: true, disabled: false },
        { label: '发布人', prop: 'publishedByUser', isCheck: true, disabled: false, width: 135 },
        { label: '公告状态', prop: 'statusMeaning', isCheck: true, disabled: false },
        { label: '发布时间', prop: 'publishedDate', isCheck: true, disabled: false, width: 135 },

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
          this.$router.push({ name: 'SettingMessageNoticeAdd' });

          break;
        case '编辑':
          this.$router.push({
            name: 'SettingMessageNoticeEdit',
            query: { id: row.noticeId },
          });

          break;
        case '删除':
          this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
            .then(() => {
              this.batchDeleteApi(row);
            })
            .catch(err => {
              console.error(err);
            });
          break;
        case '发布':
          this.$confirm('确定发布吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
            .then(() => {
              this.operationApi({ noticeId: row.noticeId }, 'publishNotices');
            })
            .catch(err => {
              console.error(err);
            });

          break;
        case '撤消':
          this.$confirm('确定撤销吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
            .then(() => {
              this.operationApi({ noticeId: row.noticeId }, 'revokeNotices');
            })
            .catch(err => {
              console.error(err);
            });

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
      messageApi.deleteNotices(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    operationApi(API_DATA, KEY = 'publishNotices') {
      messageApi[KEY](API_DATA).then(res => {
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
