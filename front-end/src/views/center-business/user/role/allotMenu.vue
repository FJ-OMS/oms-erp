<template>
  <div class="app-container">
    <BjDivider right-fixed
      >分配菜单权限
      <template #right>
        <el-button @click="handleClose">返 回</el-button>
        <el-button type="primary" @click="onSave">确 定</el-button>
      </template>
    </BjDivider>

    <el-row>
      <el-input
        v-model="filterText"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="输入关键字进行过滤"
        class="mb16"
        style="width: 400px"
      />
    </el-row>
    <el-tree
      ref="tree"
      :data="data"
      show-checkbox
      node-key="id"
      highlight-current
      :props="defaultProps"
      :filter-node-method="filterNode"
      :default-checked-keys="checkedList"
    >
      <span slot-scope="{ node, data }" class="custom-tree-node">
        <span
          ><el-tag
            class="el-tag-left"
            :type="SETTING_PERMISSION_TYPE_COLOR_LIST.bjGet(data.permissionType)"
            >{{ SETTING_PERMISSION_TYPE_LIST.bjGet(data.permissionType) }}</el-tag
          >{{ node.label }}</span
        >
      </span>
    </el-tree>
  </div>
</template>

<script>
import viewMiXin from '@/utils/view-mixin.js';
import { roleApi } from '@/api';
import {
  SETTING_PERMISSION_TYPE_LIST,
  SETTING_PERMISSION_TYPE_COLOR_LIST,
} from '@/utils/constants';
export default {
  name: 'BusinessUserRoleAllotMenu',
  mixins: [viewMiXin],
  data() {
    return {
      SETTING_PERMISSION_TYPE_LIST,
      SETTING_PERMISSION_TYPE_COLOR_LIST,
      data: [],
      defaultProps: {
        children: 'children',
        label: 'title',
      },
      filterText: '',
      // 选中菜单ids
      checkedList: [],
      childrenIds: [],
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  mounted() {
    this.getData(this.$route.query.roleId);
  },
  methods: {
    getData(roleId) {
      roleApi
        .getAllmenu({ roleId: roleId })
        .then(res => {
          this.data = res.menuTrees;
          const childrenIds = this.filterData(res.menuTrees);
          res.checkedList.forEach(id => {
            // 判断是否是最后一层的id
            if (childrenIds.indexOf(id) !== -1) {
              this.checkedList.push(id);
            }
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * 筛选出数据的最后一层id
     */
    filterData(list) {
      list.forEach(item => {
        if (item.children === null) {
          this.childrenIds.push(item.id);
        } else {
          this.filterData(item.children);
        }
      });
      return this.childrenIds;
    },
    /**
     * roleId: 角色id
     * menuIds: 菜单ids
     */
    onSave() {
      const roleId = this.$route.query.roleId;
      const menuIds = [
        ...this.$refs.tree.getCheckedKeys(),
        ...this.$refs.tree.getHalfCheckedKeys(),
      ];
      roleApi
        .saveRoleMenu({ roleId, menuIds })
        .then(res => {
          if (!res.failed) {
            this.msgSuccess('保存成功');
            setTimeout(() => {
              this.handleClose();
            }, 500);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.title.indexOf(value) !== -1;
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
  },
};
</script>

<style lang="scss" scoped>
.el-tag-left {
  margin-right: 8px;
}
.foot {
  position: absolute;
  bottom: 15px;
  left: 0;
  width: 100%;
  text-align: center;
}
</style>
