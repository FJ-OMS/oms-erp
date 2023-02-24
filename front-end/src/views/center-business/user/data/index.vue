<template>
  <div class="app-container">
    <el-form ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input
          :maxlength="MAX_LENGTH.DEFAULT"
          :value="roleName"
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
          v-permission:BusinessUserDataList_Delete
          type="danger"
          icon="el-icon-delete"
          :disabled="multiple"
          class="delete-btn"
          @click="onDeleteSelected"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button v-permission:BusinessUserDataList_AllotData type="primary" @click="handleAdd"
          >分配数据权限</el-button
        >
      </el-col>
    </el-row>
    <el-table
      ref="table"
      v-loading="loading"
      :data="userTableData"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="数据权限编码" align="center" prop="loginName" />
      <el-table-column label="数据权限名称" align="center" prop="realName" />
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
      <el-button @click="cancel">返 回</el-button>
    </div>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import pageMiXin from '@/utils/page-mixin.js';
import { roleApi, userApi } from '@/api';
export default {
  name: 'AllotUser',
  mixins: [viewMiXin, pageMiXin],
  data() {
    return {
      dialogListConfig: {
        title: '分配数据权限',
        columns: [
          {
            prop: 'loginName',
            label: '数据权限编码',
          },
          {
            prop: 'realName',
            label: '数据权限名称',
          },
        ],
        search: [
          {
            placeholder: '请输入数据权限名称',
            key: 'realName',
          },
        ],
        actionApi: userApi.getUserList,
        query: 'userGroupId',
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
      dialogData: [],
      /** 标题 */
      title: '选择用户',
      userGroupInfo: null,
    };
  },
  computed: {
    roleId() {
      return this.$route.query.roleId || '';
    },
    roleName() {
      return this.$route.query.roleName || '';
    },
  },
  watch: {
    roleId: {
      handler(v) {
        if (v) {
          this.getUserListByRoleId(v);
        }
      },
      immediate: true,
    },
  },
  methods: {
    getList() {
      this.getUserListByRoleId();
    },
    /** 保存 */

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
      const USERLIST = data.map(e => {
        return {
          _innerMap: {},
          memberId: e.id,
          roleId: this.roleId,
        };
      });
      userApi
        .addUserRoles({
          BjBody: USERLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserListByRoleId();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /** END */
    /** 删除操作 */
    onDeleteSelected() {
      const USERLIST = this.selectedRow.map(e => {
        return {
          _innerMap: {},
          memberId: e.id,
          roleId: this.roleId,
        };
      });
      userApi
        .deleteUserRoles({
          BjBody: USERLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserListByRoleId();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /** 获取列表 */
    getUserListByRoleId(v) {
      v = v || this.roleId;
      roleApi
        .getUserListByRoleId({
          roleId: v,
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
