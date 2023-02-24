<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'80px'"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :row-style="rowStyleFun"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="账户" prop="loginName">
            <el-input
              v-model.trim="queryParams.loginName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="名称" prop="realName">
            <el-input
              v-model.trim="queryParams.realName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabled">
            <BjSelectLov
              v-model="queryParams.enabled"
              :option-list="STATUS_ENABLED_TRUE"
              placeholder="请选择"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="是否锁定" prop="locked">
            <BjSelectLov
              v-model="queryParams.locked"
              :option-list="STATUS_YES_TRUE"
              placeholder="请选择"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="是否过期" prop="activeFlag">
            <BjSelectLov
              v-model="queryParams.activeFlag"
              :option-list="STATUS_YES_TRUE"
              placeholder="请选择"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="有效期从" prop="startDateActive">
            <el-date-picker
              v-model="queryParams.startDateActive"
              class="full-width"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择"
              @change="handleChangeStartTime"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="有效期至" prop="endDateActive">
            <el-date-picker
              v-model="queryParams.endDateActive"
              class="full-width"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择"
              :picker-options="$disabledPickerDate(queryParams.startDateActive)"
            /> </el-form-item
        ></el-col>
      </template>
      <template v-slot:tableOperation="">
        <el-col>
          <el-button
            v-permission:BusinessUserAccountAdd
            type="primary"
            class="main-btn"
            @click="toPage()"
            >新增账户</el-button
          >
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
              <el-tag
                v-else-if="col.prop === 'enabled'"
                :type="scope.row.enabled ? 'success' : 'info'"
                >{{ STATUS_ENABLED_TRUE.bjGet(scope.row.enabled) }}</el-tag
              >
              <el-tag
                v-else-if="col.prop === 'locked'"
                :type="scope.row.locked ? 'danger' : 'info'"
                >{{ STATUS_YES_TRUE.bjGet(scope.row.locked) }}</el-tag
              >
              <el-tag
                v-else-if="col.prop === 'activeFlag'"
                :type="scope.row.activeFlag ? 'danger' : 'info'"
                >{{ STATUS_YES_TRUE.bjGet(scope.row.activeFlag) }}</el-tag
              >
              <!-- <span v-else-if="col.prop === 'userType'">{{
                USER_TYPE_LIST.bjGet(scope.row.userType)
              }}</span> -->

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
            <template slot-scope="scope">
              <el-button
                v-permission:BusinessUserAccountEdit
                type="text"
                class="edit-btn"
                @click="toPage(scope.row)"
                >编辑</el-button
              >
              <el-dropdown v-hasDropdownChildren size="mini" @command="command($event, scope.row)">
                <span class="el-dropdown-link">
                  操作<i class="el-icon-arrow-down el-icon--right" />
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    v-permission:BusinessUserAccountIndex_RelatedCompany
                    command="关联组织架构"
                    >关联组织架构</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-permission:BusinessUserAccountIndex_ChangePassword
                    command="修改密码"
                    >修改密码</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-if="!scope.row.enabled"
                    v-permission:BusinessUserAccountIndex_UseStatus
                    command="启用"
                    >启用</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-else
                    v-permission:BusinessUserAccountIndex_UseStatus
                    command="禁用"
                    >禁用</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-if="!scope.row.locked"
                    v-permission:BusinessUserAccountIndex_Locked
                    command="锁定"
                    >锁定</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-else
                    v-permission:BusinessUserAccountIndex_Locked
                    command="解锁"
                    >解锁</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-permission:BusinessUserAccountIndex_Delete
                    command="删除"
                    class="delete-btn"
                    >删除</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <el-dialog
      v-if="passDialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="passDialogVisible"
      width="500px"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" status-icon :rules="rules" label-width="100px">
        <el-form-item label="密码" prop="pass">
          <el-input
            v-model="form.pass"
            :maxlength="MAX_LENGTH.DEFAULT"
            type="password"
            autocomplete="off"
            show-password
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            v-model="form.checkPass"
            :maxlength="MAX_LENGTH.DEFAULT"
            type="password"
            autocomplete="off"
            show-password
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      </span>
    </el-dialog>
    <dialog-company ref="dialogCompany" @reFresh="reFresh"></dialog-company>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { userApi } from '@/api';
import { STATUS_ENABLED_TRUE, STATUS_YES_TRUE, USER_TYPE_LIST } from '@/utils/constants';
import { patternPassword } from '@/utils/validate';
import dialogCompany from './components/DialogCompany';
export default {
  name: 'BusinessUserAccountIndex',
  components: { dialogCompany },
  mixins: [pageMiXin],
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (!patternPassword.test(value)) {
          callback(new Error('请输入正确的密码'));
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      selection: false,
      queryApi: userApi.getUserList,
      // 查询参数
      queryParams: {
        loginName: null,
        realName: null,
        enabled: null,
        locked: null,
        activeFlag: null,
        startDateActive: null,
        endDateActive: null,
      },
      title: '',
      dialogVisible: false,
      /** 修改密码弹窗 */
      passDialogVisible: false,
      // 表单参数
      form: {
        pass: null,
        checkPass: null,
      },
      // 表单校验
      rules: {
        pass: [{ required: true, validator: validatePass, trigger: 'blur' }],
        checkPass: [{ required: true, validator: validatePass2, trigger: 'blur' }],
      },
      labelList: [
        { label: '账户', prop: 'loginName', isCheck: true, disabled: false },
        { label: '名称', prop: 'realName', isCheck: true, disabled: false },
        { label: '手机', prop: 'phone', isCheck: true, disabled: false },
        { label: '邮箱', prop: 'email', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabled', isCheck: true, disabled: false },
        { label: '是否锁定', prop: 'locked', isCheck: true, disabled: false },
        { label: '是否过期', prop: 'activeFlag', isCheck: true, disabled: false },
        // { label: '用户类型', prop: 'userType', isCheck: true, disabled: false },
        { label: '有效期从', prop: 'startDateActive', isCheck: true, disabled: false },
        { label: '有效期至', prop: 'endDateActive', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      STATUS_ENABLED_TRUE,
      STATUS_YES_TRUE,
      USER_TYPE_LIST,
      clickUserId: null,
    };
  },
  computed: {},

  created() {},
  methods: {
    rowStyleFun({ row, rowIndex }) {
      const styleJson = {};
      if (row.activeFlag) {
        styleJson.color = 'red';
      }
      return styleJson;
    },
    handleSelectionChange(data) {
      console.log(data);
    },
    /** 删除操作 */
    // onDelete() {
    //   this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.$message.warning('无接口');
    //     })
    //     .catch(() => {});
    // },
    command(TEXT, row) {
      // console.log(TEXT, row.loginName + row.id);
      this.clickUserId = row.id;
      if (TEXT === '关联组织架构') {
        this.$refs.dialogCompany.show(row.id);
        return;
      }
      if (TEXT === '修改密码') {
        this.title = '修改密码';
        this.form = {
          pass: null,
          checkPass: null,
        };
        this.passDialogVisible = true;
        return;
      }
      this.$confirm(`您正在 ${TEXT} ${row.loginName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          if (TEXT === '启用') {
            this.update('userNnfrozen');
          }
          if (TEXT === '禁用') {
            this.update('userFrozen');
          }
          if (TEXT === '锁定') {
            this.update('userLocked');
          }
          if (TEXT === '解锁') {
            this.update('userUnlocked');
          }
          if (TEXT === '删除') {
            this.deleteData(this.clickUserId);
          }
        })
        .catch(() => {});
    },
    /**
     *
     */
    update(name) {
      userApi[name]({ userId: this.clickUserId })
        .then(res => {
          console.log(res);
          if (!res.failed) {
            this.msgSuccess('操作成功');
            this.reFresh();
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteData(id) {
      userApi
        .deleteUser({ userId: id })
        .then(res => {
          console.log(res);
          if (!res.failed) {
            this.msgSuccess('操作成功');
            this.reFresh();
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /* 跳转页面 */
    toPage(row) {
      if (row) {
        this.$router.push({
          name: 'BusinessUserAccountEdit',
          query: { userId: row.id },
        });
      } else {
        this.$router.push({
          name: 'BusinessUserAccountAdd',
        });
      }
    },
    /* **********************修改密码部分************************** */
    /**  */
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.adminUpdatePassword({
            password: this.form.checkPass,
            userId: this.clickUserId,
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClose() {
      this.passDialogVisible = false;
      this.resetForm('form');
    },
    adminUpdatePassword(data) {
      userApi
        .adminUpdatePassword(data)
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message);
            this.handleClose();
          }
          console.log(res);
        })
        .catch(err => {
          console.error(err);
          this.$message.warning(err.message || 'Error message not find');
        });
    },
    handleChangeStartTime(startTime) {
      const { startDateActive, endDateActive } = this.queryParams;
      if (new Date(endDateActive) < new Date(startDateActive)) {
        this.queryParams.endDateActive = null;
      }
    },
  },
};
</script>
