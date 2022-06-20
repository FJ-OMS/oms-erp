<template>
  <div class="app-container">
    <BjDivider right-fixed
      >基础信息
      <template #right>
        <el-button @click="tryCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </template>
    </BjDivider>
    <el-form ref="queryForm" :rules="rules" :model="form" :inline="true" label-width="80px">
      <el-form-item label="名称" prop="realName">
        <el-input
          v-model.trim="form.realName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <BjFormItemMobile v-model.trim="form.phone" prop="phone" label="手机号" required clearable />
      <el-form-item label="账户" prop="loginName">
        <el-input
          v-model.trim="form.loginName"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isEdit"
          placeholder="2-20位中文/英文/数字"
          clearable
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model.trim="form.nickname"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          v-model.trim="form.password"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isEdit"
          type="password"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model.trim="form.email"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model.trim="form.gender" placeholder="请选择" clearable>
          <el-option
            v-for="item in GENDER_LIST"
            :key="item.label"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="地区" prop="regionId">
        <BjRegionCascader v-model="form.regionId" clearable />
      </el-form-item>
      <el-form-item label="详细地址" prop="addressDetail">
        <el-input
          v-model.trim="form.addressDetail"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="出生日期" prop="birthday">
        <el-date-picker
          v-model="form.birthday"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="有效期从" prop="startDateActive">
        <el-date-picker
          v-model="form.startDateActive"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="有效期至" prop="endDateActive">
        <el-date-picker
          v-model="form.endDateActive"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="请选择"
        />
      </el-form-item>
      <!-- <el-form-item label="用户类型" prop="userType" required>
        <el-select v-model.trim="form.userType" placeholder="请选择" clearable>
          <el-option
            v-for="item in USER_TYPE_LIST"
            :key="item.label"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item v-if="title === '编辑账户'" label="冻结" prop="enabled">
        <el-switch v-model="form.enabled" />
      </el-form-item>
    </el-form>
    <BjDivider>角色</BjDivider>
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
        <el-button type="primary" @click="onAdd">添加角色</el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="roleTableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="角色名称" align="center" prop="name" />
      <el-table-column label="有效期从" align="center" prop="name" width="250">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.startDateActive"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="请选择"
          />
        </template>
      </el-table-column>
      <el-table-column label="有效期至" align="center" prop="name" width="250">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.endDateActive"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="请选择"
          />
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.page"
      :limit.sync="queryParams.size"
    />
    <BjDivider>组织架构</BjDivider>
    <BjCompanyTree
      :flag="true"
      :default-checked-keys="form.companyLists"
      class="mt16"
      @check="onChangeCompany"
    />

    <BjDialogList
      ref="dialogList"
      :config="dialogListConfig"
      :disabled="disabledIds"
      multiple
      @confirm="onConfirmDialogList"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
// :TODO
import { roleApi } from '@/api';
import BjRegionCascader from '@/components/BjRegionCascader';
import { GENDER_LIST, USER_TYPE_LIST } from '@/utils/constants';
import { validateLoginName } from '@/utils/validate';
import BjCompanyTree from '@/components/BjCompanyTree';
import { parseTime } from '@/utils';
export default {
  name: 'ViewForm',
  components: {
    BjRegionCascader,
    BjCompanyTree,
  },
  mixins: [viewMiXin],
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    userInfo: {
      type: Object,
      default() {
        return null;
      },
    },
    userAllRoles: {
      type: Array,
      default() {
        return [];
      },
    },
    /** 公司 ids */
    companyLists: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  data() {
    return {
      pageName: 'BusinessUserAccountIndex',
      dialogListConfig: {
        title: '添加角色',
        columns: [
          {
            prop: 'name',
            label: '角色名称',
          },
        ],
        search: [
          {
            placeholder: '请输入角色名称',
            key: 'name',
          },
        ],
        actionApi: roleApi.getRoleList,
        defaultQuery: {
          enabled: true,
        },
        query: '',
      },
      // 遮罩层
      loading: false,
      // 查询参数
      queryParams: {},
      title: '',
      dialogVisible: false,
      // 选中数组ids
      ids: [],
      // 选中数组
      selection: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      /** 数据总条数 */
      total: 0,
      // 表单参数
      form: {
        enabled: true,
        realName: null,
        loginName: null,
        nickname: null,
        password: null,
        phone: null,
        email: null,
        gender: null,
        countryCode: null,
        countryName: null,
        regionId: null,
        addressDetail: null,
        birthday: null,
        startDateActive: null,
        endDateActive: null,
        // userType: null, // 用户类型（暂时用不到）
        memberRoleList: [],
        companyLists: [], // 组织架构 ids
      },
      // 表单校验
      rules: {
        realName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        loginName: [{ required: true, trigger: 'blur', validator: validateLoginName }],
        startDateActive: [{ required: true, message: '请选择有效期', trigger: 'change' }],
        // userType: [{ required: true, message: '请选择用户类型', trigger: 'blur' }],
      },
      roleTableData: [],
      disabledIds: [],
      GENDER_LIST,
      USER_TYPE_LIST,
    };
  },
  watch: {
    userInfo: {
      handler(info) {
        if (info) {
          this.form.enabled = info.enabled;
          this.form.realName = info.realName;
          this.form.loginName = info.loginName;
          this.form.nickname = info.nickname;
          // this.form.password: null;
          this.form.phone = info.phone;
          this.form.email = info.email;
          this.form.gender = info.gender;
          this.form.countryName = info.countryName;
          this.form.regionId = info.regionId;
          this.form.addressDetail = info.addressDetail;
          this.form.birthday = info.birthday;
          this.form.startDateActive = info.startDateActive;
          this.form.endDateActive = info.endDateActive;
          // this.form.userType = info.userType;
        }
      },
    },
    userAllRoles: {
      handler(roles) {
        if (roles) {
          /** */
          this.roleTableData = [].concat(roles);
        }
      },
    },
    companyLists: {
      handler(list) {
        this.form.companyLists = list;
      },
    },
  },
  created() {
    if (!this.isEdit) this.form.startDateActive = parseTime(new Date(), '{y}-{m}-{d}');
  },
  methods: {
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.selection = selection;
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 选择国家清空地区数据 */
    handleChangeCountry(id, data) {
      this.form.countryCode = data.countryCode;
      this.form.regionId = null;
    },
    /** 选择组织架构 */
    onChangeCompany(data, node) {
      // 获取选中组织架构ids
      this.form.companyLists = node.checkedKeys;
    },
    /** 提交 */
    submit() {
      console.log('确定', this.form);
      if (!this.roleTableData.length) {
        this.$message.warning('请为此账户添加一个角色');
        return;
      }
      this.$refs['queryForm'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.form.memberRoleList = this.roleTableData.map(e => {
          return {
            _innerMap: {},
            roleId: e.id,
            startDateActive: e.startDateActive,
            endDateActive: e.endDateActive,
          };
        });

        this.$emit('submit', this.form);
      });
    },
    /**
     * START 添加角色操作
     */
    onAdd() {
      this.disabledIds = this.roleTableData.map(item => {
        return item.id;
      });
      this.$refs.dialogList.show();
    },
    onConfirmDialogList(data) {
      if (this.isEdit) {
        this.$emit(
          'addUserRoles',
          data.map(e => e.id),
        );
      } else {
        data.forEach(element => {
          if (this.roleTableData.every(e => e.id != element.id)) {
            this.roleTableData.push({
              startDateActive: null,
              endDateActive: null,
              ...element,
            });
          }
        });
      }
    },
    /** END */
    /** 编辑操作 */
    onEdit() {
      this.title = '编辑账户';
      this.dialogVisible = true;
    },
    /** 删除操作 */
    onDeleteSelected() {
      const name = this.selection
        .map(item => {
          return item.name;
        })
        .join('，');
      this.$confirm('是否要删除<span style="color: red;">' + name + '</span>角色?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        dangerouslyUseHTMLString: true,
      })
        .then(() => {
          if (this.isEdit) {
            this.$emit('deleteUserRoles', this.ids);
          } else {
            this.ids.forEach(id => {
              const findIndex = this.roleTableData.findIndex(e => e.id == id);
              if (findIndex !== -1) {
                this.roleTableData.splice(findIndex, 1);
              }
            });
          }
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped></style>
