<template>
  <el-dialog
    title="关联组织架构"
    :visible.sync="dialogVisible"
    width="500px"
    :before-close="handleClose"
  >
    <div>
      <BjCompanyTree
        :flag="true"
        :default-checked-keys="form.companyLists"
        class="mt16"
        @check="onChangeCompany"
      />
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { userApi } from '@/api';
import BjCompanyTree from '@/components/BjCompanyTree';
export default {
  components: { BjCompanyTree },
  props: {},
  data() {
    return {
      id: null,
      form: {
        memberRoleList: [],
        companyLists: [],
      },
      dialogVisible: false,
      userInfo: {},
      userAllRoles: {},
    };
  },
  methods: {
    getUserInfoById(id) {
      userApi
        .getUserInfoById({
          userId: id,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.userInfo = res;
            console.log(this.userInfo);
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getUserAllRolesById(id) {
      userApi
        .getUserAllRolesById({
          userId: id,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.form.memberRoleList = res.map(e => {
              return {
                _innerMap: {},
                roleId: e.id,
                startDateActive: e.startDateActive,
                endDateActive: e.endDateActive,
              };
            });
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getUserAllCompany(id) {
      userApi
        .getUserCompany({
          userId: id,
        })
        .then(res => {
          console.log(res);
          this.form.companyLists = res.companyLists;
        })
        .catch(err => {
          console.error(err);
        });
    },
    show(id) {
      this.form.companyLists = [];
      this.getUserInfoById(id);
      this.getUserAllRolesById(id);
      this.getUserAllCompany(id);
      this.dialogVisible = true;
    },
    hide() {
      this.handleClose();
    },
    onChangeCompany(data, node) {
      // 获取选中组织架构ids
      this.form.companyLists = node.checkedKeys;
    },
    handleClose() {
      this.dialogVisible = false;
    },
    confirm() {
      this.form['id'] = this.userInfo.id;
      this.form['_token'] = this.userInfo._token;
      this.form['objectVersionNumber'] = Number(this.userInfo.objectVersionNumber);
      userApi
        .updateUserInfo({
          ...this.form,
          ...this.userInfo,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.handleClose();
            this.reFresh();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    reFresh() {
      this.$emit('reFresh');
    },
  },
};
</script>

<style></style>
