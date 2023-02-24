<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'100px'"
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
          <el-form-item label="用户组名称" prop="groupName">
            <el-input
              v-model.trim="queryParams.groupName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabledFlag">
            <el-select v-model.trim="queryParams.enabledFlag" placeholder="请选择" clearable>
              <el-option
                v-for="item in STATUS_ENABLED_NUM"
                :key="item.label"
                :label="item.label"
                :value="item.value"
              />
            </el-select> </el-form-item
        ></el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDelete"
            >删除</el-button
          >

          <el-button type="primary" class="main-btn" @click="handleAdd">新增</el-button>
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
              <span v-if="col.prop === 'enabledFlag'">
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
              <el-button type="text" class="edit-btn" @click="edit(props.row)">编辑</el-button>
              <!-- <el-dropdown size="mini" @command="command">
                <span class="el-dropdown-link">
                  操作<i class="el-icon-arrow-down el-icon--right" />
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="1">分配角色</el-dropdown-item>
                  <el-dropdown-item command="2">数据权限</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown> -->
              <el-button type="text" class="delete-btn" @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="dialogVisible"
      width="500px"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" status-icon :rules="rules" label-width="100px">
        <el-col :span="colSpan">
          <el-form-item label="用户组编码" prop="groupCode">
            <el-input
              v-model="form.groupCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="用户组名称" prop="groupName">
            <el-input
              v-model="form.groupName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            /> </el-form-item
        ></el-col>
        <!-- <el-col :span="colSpan">
              <el-form-item label="角色" prop="status">
          <el-select v-model.trim="queryParams.status" placeholder="请选择" multiple clearable>
            <el-option label="角色1" value="1" />
            <el-option label="角色2" value="0" />
          </el-select>
        </el-form-item></el-col>
        <el-col :span="colSpan">
              <el-form-item label="数据权限" prop="status">
          <el-select
            v-model.trim="queryParams.status"
            placeholder="请选择"
            multiple
            clearable
          >
            <el-option label="数据权限" value="1" />
          </el-select>
        </el-form-item></el-col> -->
        <el-col :span="colSpan">
          <el-form-item label="启用" prop="enabledFlag">
            <el-switch v-model="form.enabledFlag" /> </el-form-item
        ></el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { userGroupApi } from '@/api';
import { STATUS_ENABLED_NUM } from '@/utils/constants';
export default {
  name: 'BusinessUserGroupList',
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: userGroupApi.getUserGroupList,
      // 查询参数
      queryParams: {
        groupName: null,
        enabledFlag: null,
      },
      title: '',
      dialogVisible: false,
      // 表单参数
      form: {
        groupName: null,
        groupCode: null,
        enabledFlag: false,
      },
      // 表单校验
      rules: {
        groupCode: [{ required: true, message: '请输入用户组编码', trigger: 'blur' }],
        groupName: [{ required: true, message: '请输入用户组名称', trigger: 'blur' }],
      },
      labelList: [
        { label: '用户组编码', prop: 'groupCode', isCheck: true, disabled: false },
        { label: '用户组名称', prop: 'groupName', isCheck: true, disabled: false },
        { label: '描述', prop: 'remark', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '创建人', prop: 'tenantName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'TODO', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      STATUS_ENABLED_NUM,
    };
  },
  computed: {},
  created() {},
  methods: {
    reFresh(n = 1) {
      this.$refs.BjPage.fetchData(n);
    },
    handleSelectionChange(data) {
      console.log(data);
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.onDeleteApi(row);
        })
        .catch(() => {});
    },
    onDeleteApi(row) {
      const data = {
        _token: row._token,
        userGroupId: row.userGroupId,
      };
      userGroupApi
        .deleteUserGroup({
          ...data,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.$refs.BjPage.fetchData(1);
          }
        });
    },
    handleAdd() {
      this.dialogVisible = true;
    },
    /** 编辑 */
    edit(row) {
      this.$router.push({
        name: 'BusinessUserGroupEdit',
        query: {
          userGroupId: row.userGroupId,
        },
      });
    },
    command(item) {
      /** 分配角色 */
      // if (item === '1') {
      //   this.$router.push({
      //     name: 'AssignRoles',
      //     query: {},
      //   });
      // }
      // /** 数据权限 */
      // if (item === '2') {
      //   this.$router.push({
      //     name: 'AllotGroupData',
      //     query: {},
      //   });
      // }
    },
    /* **********************修改密码部分************************** */
    /**  */
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log(this.form);
          this.createUserGroupApi(this.form);
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    createUserGroupApi(formData) {
      const POST_DATA = {
        enabledFlag: formData.enabledFlag ? 1 : 0,
        groupCode: formData.groupCode,
        groupName: formData.groupName,
        remark: '',
      };
      userGroupApi
        .createUserGroup(POST_DATA)
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.dialogVisible = false;
            this.$refs.BjPage.fetchData(1);
          }
        })
        .catch(err => {
          console.error(err);
        });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClose() {
      this.dialogVisible = false;
    },
  },
};
</script>
