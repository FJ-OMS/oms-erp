<template>
  <div class="app-container">
    <el-form ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="账户" prop="groupCode">
        <el-input
          :maxlength="MAX_LENGTH.DEFAULT"
          :value="loginName"
          disabled
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="realName">
        <el-input
          :maxlength="MAX_LENGTH.DEFAULT"
          :value="realName"
          disabled
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          :disabled="multiple"
          class="delete-btn"
          @click="onDeleteSelected"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" @click="handleAdd">分配用户组</el-button>
      </el-col>
    </el-row>
    <el-table
      ref="table"
      v-loading="loading"
      :data="userTableData"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户组编码" align="center" prop="groupCode" />
      <el-table-column label="用户组名称" align="center" prop="groupName" />
    </el-table>
    <pagination
      v-show="queryParams.total > 0"
      :total="queryParams.total"
      :page.sync="queryParams.page"
      :limit.sync="queryParams.size"
      @pagination="getList"
    />

    <BjDialogList
      ref="dialogList"
      :config="dialogListConfig"
      multiple
      @confirm="onConfirmDialogList"
    />

    <div class="footer">
      <el-button type="primary" @click="cancel">返 回</el-button>
      <!-- <el-button type="primary"  @click="submit">确 定</el-button> -->
    </div>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import pageMiXin from '@/utils/page-mixin.js';
import { userGroupApi } from '@/api';
export default {
  name: 'BusinessUserAccountAllot',
  mixins: [viewMiXin, pageMiXin],
  data() {
    return {
      dialogListConfig: {
        title: '分配用户组',
        columns: [
          {
            prop: 'groupCode',
            label: '用户组编码',
          },
          {
            prop: 'groupName',
            label: '用户组名称',
          },
        ],
        search: [
          {
            placeholder: '请输入用户组名称',
            key: 'groupName',
          },
        ],
        actionApi: userGroupApi.getUserGroupListByUserIdExclude,
        query: 'userId',
      },
      // 遮罩层
      loading: false,
      queryParams: {
        page: 1,
        size: 20,
        total: 0,
      },
      userTableData: [],
      // 选中数组
      ids: [],
      selectedRow: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      /** 弹窗 */
      dialogVisible: false,
      /** 弹窗数据 */
      dialogData: [{ code: 'TUBKO0', name: '张三' }],
      /** 标题 */
      title: '选择用户',
      userGroupInfo: null,
    };
  },
  computed: {
    userId() {
      return this.$route.query.userId || '';
    },
    loginName() {
      return this.$route.query.loginName || '';
    },
    realName() {
      return this.$route.query.realName || '';
    },
  },
  watch: {
    userId: {
      handler(v) {
        if (v) {
          this.getUserGroupListByUserId(v);
        }
      },
      immediate: true,
    },
  },
  methods: {
    getList() {
      this.getUserGroupListByUserId();
    },
    cancel() {
      this.cancel();
    },
    /**
     * START 添加操作
     */
    handleAdd() {
      this.$refs.dialogList.show();
    },
    onConfirmDialogList(data) {
      const USERGROUPLIST = data.map(e => {
        return {
          _innerMap: {},
          userGroupId: e.userGroupId,
        };
      });
      userGroupApi
        .addUserGroupListToUserId({
          userId: this.userId,
          BjBody: USERGROUPLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserGroupListByUserId();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /** END */
    /** 删除操作 */
    onDeleteSelected() {
      const USERGROUPLIST = this.selectedRow.map(e => {
        return {
          _innerMap: {},
          ...e,
        };
      });
      userGroupApi
        .deleteUserGroupListToUserId({
          userId: this.userId,
          BjBody: USERGROUPLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserGroupListByUserId();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /** 获取列表 */
    getUserGroupListByUserId(v) {
      v = v || this.userId;
      userGroupApi
        .getUserGroupListByUserId({
          userId: v,
          ...this.queryParams,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.userTableData = res.content;
            this.queryParams.total = res.totalElements || res.content.length;
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    handleDelete() {
      console.log('删除');
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      console.log(selection);
      this.selectedRow = [].concat(selection);
      this.ids = selection.map(item => item.userId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 取消 */
    handleClose() {
      this.dialogVisible = false;
    },
    /** 修改size */
    getPageList() {
      console.log('size', this.queryParams.size);
    },
    /** 搜索 */
    dialogSearch() {
      console.log('搜索');
    },
    /** 多选 */
    dialogSelectionChange(data) {
      console.log('多选', data);
    },
    dialogConfirm() {
      this.dialogVisible = false;
    },
  },
};
</script>
