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
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="角色名称" prop="name">
            <el-input
              v-model.trim="queryParams.name"
              :maxlength="20"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabled">
            <BjSelectLov
              v-model="queryParams.enabled"
              :option-list="STATUS_ENABLED_TRUE"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <!-- <el-button
              type="primary"

              :disabled="tableOperationProp.notSelected"
              @click="onDelete"
               class='delete-btn'>删除</el-button
            > -->

          <el-button
            v-permission:BusinessUserRoleIndex_AddRole
            type="primary"
            class="main-btn"
            @click="addRole(1)"
            >创建角色</el-button
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
              <el-button
                v-permission:BusinessUserRoleIndex_Edit
                type="text"
                class="edit-btn"
                @click="addRole(2, props.row)"
                >编辑</el-button
              >
              <el-dropdown v-hasDropdownChildren size="mini" @command="command($event, props.row)">
                <span class="el-dropdown-link">
                  操作<i class="el-icon-arrow-down el-icon--right" />
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    v-if="props.row.enabled"
                    v-permission:BusinessUserRoleIndex_Status
                    command="禁用"
                    >禁用</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-if="!props.row.enabled"
                    v-permission:BusinessUserRoleIndex_Status
                    command="启用"
                    >启用</el-dropdown-item
                  >
                  <!-- <el-dropdown-item command="分配权限">分配权限</el-dropdown-item> -->
                  <el-dropdown-item v-permission:BusinessUserRoleAllotUser command="分配用户"
                    >分配用户</el-dropdown-item
                  >
                  <el-dropdown-item v-permission:BusinessUserRoleAllotMenu command="分配菜单权限"
                    >分配菜单权限</el-dropdown-item
                  >
                  <!-- <el-dropdown-item command="分配数据权限">分配数据权限</el-dropdown-item> -->
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="addDialogVisible"
      width="500px"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" status-icon :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" :maxlength="20" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="描述" prop="describe">
          <el-input
            v-model="form.description"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="rolePermissionDialogVisible"
      width="600px"
      :before-close="handleClose"
    >
      <div class="dialog-custom-class-body-div">
        <el-tree
          ref="tree"
          :data="rolePermissionList"
          show-checkbox
          default-expand-all
          node-key="id"
          highlight-current
          :props="defaultProps"
        >
          <template slot-scope="{ node, data }">
            <div>
              {{ node.label }}
              <el-tag v-if="data.permissionType == 'button'" type="warning">按钮</el-tag>
              <el-tag v-if="data.permissionType == 'api'">API</el-tag>
            </div>
          </template>
        </el-tree>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleConfirmRolePermission">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { roleApi } from '@/api';
import { STATUS_ENABLED_TRUE } from '@/utils/constants';
export default {
  name: 'BusinessUserRoleIndex',
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      queryApi: roleApi.getRoleList,
      STATUS_ENABLED_TRUE,
      // 查询参数
      queryParams: {
        enabled: null,
        name: null,
      },
      title: '',
      addDialogVisible: false,
      rolePermissionDialogVisible: false,
      // 表单参数
      form: {
        name: null,
        description: null,
        id: null,
        _token: null,
        objectVersionNumber: null,
      },
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
      },
      labelList: [
        { label: '角色名称', prop: 'name', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdUserName', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabled', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      rolePermissionList: [
        {
          id: '',
          code: 'hzero.site.sys',
          name: '加载中',
          parentId: '0',
          type: 'root',
          sort: 10,
          permissionType: 'api',
          subMenus: [],
          viewCode: 'sys',
          rootNode: false,
        },
      ],
      defaultProps: {
        children: 'subMenus',
        label: 'name',
      },
      isEdit: false,
      selectedRow: null,
      defaultCheckedRolePermissionList: [],
    };
  },
  computed: {},

  created() {},
  mounted() {},
  methods: {
    reFresh(n = 1) {
      this.$refs.BjPage.fetchData(n);
    },
    addRole(type, row) {
      this.title = type === 1 ? '新建角色' : '编辑角色';
      this.addDialogVisible = true;
      if (type === 1) {
        this.isEdit = false;
        this.title = '新建角色';
        this.form.id = null;
        this.form.name = null;
        this.form.code = null;
        this.form.description = null;
        this.form._token = null;
        this.form.objectVersionNumber = null;
      }
      if (type === 2) {
        this.isEdit = true;
        this.title = '编辑角色';
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.code = row.code;
        this.form.description = row.description;
        this.form._token = row._token;
        this.form.objectVersionNumber = row.objectVersionNumber;
      }
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      });
    },
    handleSelectionChange(data) {
      console.log(data);
    },
    /** 删除操作 */
    onDelete() {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {})
        .catch(() => {});
    },
    switchRoleAbled(API_KEY, data) {
      roleApi[API_KEY](data)
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
          }
          this.reFresh();
          console.log(res);
        })
        .catch(err => {
          console.error(err);
          this.$message.warning(err.message || 'Error message not find');
        });
    },
    command(TEXT, row) {
      this.selectedRow = row;
      if (TEXT === '禁用' || TEXT === '启用') {
        this.$confirm(`您正在 ${TEXT} ${row.name}，是否继续？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.switchRoleAbled(TEXT === '启用' ? 'enableRole' : 'disableRole', {
              id: row.id,
              objectVersionNumber: row.objectVersionNumber,
              _token: row._token,
            });
          })
          .catch(() => {});
      }

      // if (TEXT === '分配权限') {
      //   this.title = '给 “ 产品 ” 分配权限';
      //   this.rolePermissionDialogVisible = true;
      //   this.getPermissionTreeByRoleId(row.id);
      // }

      if (TEXT === '分配用户') {
        this.$router.push({
          name: 'BusinessUserRoleAllotUser',
          query: {
            roleId: row.id,
            roleName: row.name,
            roleCode: row.code,
          },
        });
      }

      if (TEXT === '分配菜单权限') {
        this.$router.push({
          name: 'BusinessUserRoleAllotMenu',
          query: {
            roleId: row.id,
          },
        });
      }

      // if (TEXT === '分配数据权限') {
      //   this.$router.push({
      //     name: 'BusinessUserRoleAllotData',
      //     query: { roleId: row.id, roleName: row.name, roleCode: row.code },
      //   });
      // }
    },
    getPermissionTreeByRoleId(roleId) {
      roleApi
        .getPermissionTreeByRoleId({
          roleId: roleId,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.rolePermissionList = res || [];
            this.$nextTick(() => {
              this.setCheckedKeys(this.rolePermissionList);
            });
          }
          console.log(res);
        })
        .catch(err => {
          console.error(err);
          this.$message.warning(err.message || 'Error message not find');
        });
    },
    /* **********************新建角色部分************************** */
    /**  */
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        console.log(this.$store.getters.userInfo);
        //     currentRoleId: "1"
        // currentRoleLevel: "site"

        const data = {
          _innerMap: {},
          code: this.form.code,
          description: this.form.description,
          name: this.form.name,
        };
        let API_KEY = 'createRole';
        if (this.isEdit && this.form.id !== null) {
          data['id'] = this.form.id;
          data['_token'] = this.form._token;
          data['objectVersionNumber'] = this.form.objectVersionNumber;
          API_KEY = 'updateRole';
        } else {
          // TODO: 创建时，需要父级角色
          data['parentRoleId'] = this.$store.getters.userInfo.currentRoleId;
        }
        roleApi[API_KEY](data)
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              this.$message.success(res.message || '操作成功');
            }
            this.addDialogVisible = false;
            this.reFresh();
            console.log(res);
          })
          .catch(err => {
            console.error(err);
            this.$message.warning(err.message || 'Error message not find');
          });
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClose() {
      this.addDialogVisible = false;
      this.rolePermissionDialogVisible = false;
    },
    handleConfirmRolePermission() {
      this.updatePermissionTreeByRoleId(this.selectedRow.id);
    },
    updatePermissionTreeByRoleId(roleId) {
      const oldChecked = this.defaultCheckedRolePermissionList;
      const newChecked = this.getCheckedKeys();
      const addFilter = newChecked.filter(nid => {
        return oldChecked.indexOf(nid) === -1;
      });
      const removeFilter = oldChecked.filter(nid => {
        return newChecked.indexOf(nid) === -1;
      });
      const promise1 = roleApi.recyclePermissionTreeByRoleId({
        roleId: roleId,
        BjBody: removeFilter,
      });
      const promise2 = roleApi.assignPermissionTreeByRoleId({
        roleId: roleId,
        BjBody: addFilter,
      });

      Promise.all([promise1, promise2])
        .then(values => {
          const [res1, res2] = values;
          if (res1 && !res1.failed && res2 && !res2.failed) {
            this.$message.success(res2.message || '操作成功');
          }
          this.handleClose();
          console.log(values);
        })
        .catch(err => {
          console.error(err);
          this.$message.warning(err.message || 'Error message not find');
        });
    },
    setCheckedKeys() {
      const filterIds = [];
      const fn = arr => {
        arr.forEach(element => {
          if (element.checkedFlag == 'Y') {
            filterIds.push(element.id);
          }
          if (element.subMenus && element.subMenus.length) {
            fn(element.subMenus);
          }
        });
      };
      fn(this.rolePermissionList);
      setTimeout(() => {
        this.$refs.tree.setCheckedKeys(filterIds);
        this.defaultCheckedRolePermissionList = [].concat(filterIds);
      }, 100);
    },
    getCheckedNodes() {
      return this.$refs.tree.getCheckedNodes();
    },
    getCheckedKeys() {
      return this.$refs.tree.getCheckedKeys();
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog-custom-class-body-div {
  max-height: calc(85vh - 120px);
  overflow-y: scroll;
}
</style>
