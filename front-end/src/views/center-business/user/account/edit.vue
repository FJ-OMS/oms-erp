<template>
  <div class="app-container">
    <viewForm
      ref="viewForm"
      :is-edit="true"
      :user-id="userId"
      :user-info="userInfo"
      :user-all-roles="userAllRoles"
      :company-lists="companyLists"
      @submit="submit"
      @deleteUserRoles="deleteUserRoles"
      @addUserRoles="addUserRoles"
    />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { userApi } from '@/api';
import viewForm from './components/viewForm';
export default {
  name: 'BusinessUserAccountEdit',
  components: {
    viewForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      userInfo: null,
      userAllRoles: [],
      companyLists: [],
    };
  },
  computed: {
    userId() {
      return this.$route.query.userId || '';
    },
  },
  watch: {
    userId: {
      handler(v) {
        if (v) {
          this.reFresh(v);
        }
      },
      immediate: true,
    },
  },
  created() {},
  methods: {
    reFresh(v) {
      v = v || this.userId;
      this.getUserInfoById(v);
      this.getUserAllRolesById(v);
      this.getUserAllCompany(v);
    },
    addUserRoles(ids) {
      const USERLIST = ids.map(id => {
        return {
          _innerMap: {},
          memberId: this.userId,
          roleId: id,
        };
      });
      userApi
        .addUserRoles({
          BjBody: USERLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserAllRolesById();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    deleteUserRoles(ids) {
      const USERLIST = ids.map(id => {
        return {
          _innerMap: {},
          memberId: this.userId,
          roleId: id,
        };
      });
      userApi
        .deleteUserRoles({
          BjBody: USERLIST,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            this.getUserAllRolesById();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getUserAllRolesById(v) {
      v = v || this.userId;
      userApi
        .getUserAllRolesById({
          userId: v,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.userAllRoles = res;
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getUserInfoById(v) {
      v = v || this.userId;
      userApi
        .getUserInfoById({
          userId: v,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.userInfo = res;
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getUserAllCompany(v) {
      v = v || this.userId;
      userApi
        .getUserCompany({
          userId: v,
        })
        .then(res => {
          this.companyLists = res.companyLists;
        })
        .catch(err => {
          console.error(err);
        });
    },
    submit(data) {
      data['id'] = this.userInfo.id;
      data['_token'] = this.userInfo._token;
      data['objectVersionNumber'] = Number(this.userInfo.objectVersionNumber);
      data.memberRoleList = data.memberRoleList.map(e => {
        return {
          _innerMap: {},
          ...e,
        };
      });
      userApi
        .updateUserInfo({
          ...data,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success(res.message || '操作成功');
            // this.$refs.viewForm.cancel({ name: 'BusinessUserAccountIndex' });
            this.reFresh();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
  },
};
</script>
<style scoped></style>
