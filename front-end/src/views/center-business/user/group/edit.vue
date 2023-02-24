<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="formData" :rules="rules" :inline="true" label-width="120px">
      <el-form-item label="用户组编码" prop="groupCode">
        <el-input
          v-model="formData.groupCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          disabled
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户组名称" prop="name">
        <el-input
          v-model="formData.groupName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="角色" prop="status">
        <el-select v-model.trim="formData.status" placeholder="请选择" multiple clearable>
          <el-option label="角色1" value="1" />
          <el-option label="角色2" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="数据权限" prop="status">
        <el-select v-model.trim="formData.status" placeholder="请选择" multiple clearable>
          <el-option label="数据权限" value="1" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="启用" prop="enabledFlag">
        <el-switch v-model="formData.enabledFlag" />
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
        <el-button type="primary" @click="handleAdd">添加用户</el-button>
      </el-col>
    </el-row>
    <el-table
      ref="table"
      v-loading="loading"
      :data="userTableData"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账号" align="center" prop="loginName" />
      <el-table-column label="用户名称" align="center" prop="realName" />
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
      tag-name="realName"
      row-keys="userId"
      @confirm="onConfirmDialogList"
    />

    <div class="footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </div>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import pageMiXin from '@/utils/page-mixin.js';
import { userGroupApi } from '@/api';
export default {
  name: 'BusinessUserGroupEdit',
  mixins: [viewMiXin, pageMiXin],
  data() {
    return {
      name: 'BusinessUserGroupList',
      dialogListConfig: {
        title: '添加用户',
        columns: [
          {
            prop: 'loginName',
            label: '账号',
          },
          {
            prop: 'realName',
            label: '用户名称',
          },
        ],
        search: [
          {
            placeholder: '请输入用户名称',
            key: 'realName',
          },
        ],
        actionApi: userGroupApi.getUserListByGroupIdExclude,
        query: 'userGroupId',
      },
      // 遮罩层
      loading: false,
      formData: {
        enabledFlag: false,
        groupCode: '',
        groupName: '',
        // objectVersionNumber: 1,
        // remark: '',
        // tenantId: 'default',
        // tenantName: 'HZERO平台',
        // userGroupId: '195204924679532544',
      },
      // 表单校验
      rules: {
        groupCode: [{ required: true, message: '请输入用户组编码', trigger: 'blur' }],
        groupName: [{ required: true, message: '请输入用户组名称', trigger: 'blur' }],
      },
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
      /** 详情 */
      userGroupInfo: null,
    };
  },
  computed: {
    userGroupId() {
      return this.$route.query.userGroupId || '';
    },
  },
  watch: {
    userGroupId: {
      handler(v) {
        if (v) {
          this.getUserListByGroupId(v);
          this.getUserGroupInfo(v);
        }
      },
      immediate: true,
    },
  },
  methods: {
    getList() {
      this.getUserListByGroupId();
    },
    /** 保存 */
    submit() {
      this.$refs['queryForm'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        console.log('确定', this.formData);
        const data = Object.assign(this.formData, {});
        data['userGroupId'] = this.userGroupInfo.userGroupId;
        data['_token'] = this.userGroupInfo._token;
        data['objectVersionNumber'] = Number(this.userGroupInfo.objectVersionNumber);
        data['enabledFlag'] = this.formData.enabledFlag ? 1 : 0;

        userGroupApi
          .updateUserGroup({
            ...data,
          })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              this.$message.success(res.message || '操作成功');
              this.cancel();
            }
          })
          .catch(err => {
            console.error(err);
          });
      });
    },
    /** 获取详情 */
    getUserGroupInfo(v) {
      v = v || this.userGroupId;
      userGroupApi
        .getUserGroupInfo({
          userGroupId: v,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.userGroupInfo = res;
            this.formData.groupCode = res.groupCode;
            this.formData.groupName = res.groupName;
            this.formData.enabledFlag = !!res.enabledFlag;
          }
        })
        .catch(err => {
          console.error(err);
        });
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
          userId: e.userId,
        };
      });
      userGroupApi
        .addUserListToGroupId({
          userGroupId: this.userGroupId,
          BjBody: USERLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserListByGroupId();
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
          ...e,
        };
      });
      userGroupApi
        .deleteUserListToGroupId({
          userGroupId: this.userGroupId,
          BjBody: USERLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserListByGroupId();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /** 获取列表 */
    getUserListByGroupId(v) {
      v = v || this.userGroupId;
      userGroupApi
        .getUserListByGroupId({
          userGroupId: v,
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
  },
};
</script>
