<template>
  <div class="app-container css-2022-2-11-09-54-35">
    <div class="page-buttons">
      <div class="page-buttons-left">
        <el-button size="mini" type="primary" class="main-btn" @click="onAdd">新增菜单</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="reFresh">刷新</el-button>
        <el-button icon="el-icon-delete" size="mini" @click="batchDelete">批量删除</el-button>
        <el-button type="danger" icon="el-icon-magic-stick" size="mini" @click="init"
          >初始化</el-button
        >
      </div>
      <div class="page-buttons-right"></div>
    </div>
    <div class="el-tree-1">
      <el-tree
        ref="tree"
        :data="tableData"
        show-checkbox
        node-key="id"
        highlight-current
        :props="{
          children: 'children',
          label: 'title',
        }"
        :expand-on-click-node="false"
        :default-expanded-keys="defaultExpandedkeys"
      >
        <div slot-scope="{ node, data }" class="custom-tree-node">
          <span
            ><el-tag
              class="el-tag-left"
              :type="SETTING_PERMISSION_TYPE_COLOR_LIST.bjGet(data.permissionType)"
              >{{ SETTING_PERMISSION_TYPE_LIST.bjGet(data.permissionType) }}</el-tag
            >{{ node.label }}</span
          >
          <span class="span-btn">
            <el-button
              v-if="data.name != '菜单'"
              style="color: red"
              type="text"
              size="mini"
              @click="() => remove(node, data)"
            >
              删除
            </el-button>
            <el-button type="text" size="mini" @click="onEdit(node, data)"> 编辑 </el-button>
            <el-button type="text" size="mini" style="margin-left: 20px" @click="() => onAdd(data)">
              添加子菜单
            </el-button>
          </span>
        </div>
      </el-tree>
    </div>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" :a-p-i="API" @reFresh="reFresh" />
  </div>
</template>
<script>
import { userApi } from '@/api';

import permissionJson from '@/permissionOS.json';

import DataForm from './components/DataForm.vue';
import {
  SETTING_PERMISSION_TYPE_LIST,
  SETTING_PERMISSION_TYPE_COLOR_LIST,
} from '@/utils/constants';
export default {
  name: 'PermissionPage',
  components: {
    DataForm,
  },
  data() {
    return {
      SETTING_PERMISSION_TYPE_LIST,
      SETTING_PERMISSION_TYPE_COLOR_LIST,
      /** *********** */
      API: userApi,
      /** * */
      tableData: [],
      mainId: null,
      // 默认展开id
      defaultExpandedkeys: [],
    };
  },
  created() {},
  mounted() {
    this.reFresh();
  },
  methods: {
    init() {
      if (this.tableData.length > 0) {
        this.$message.warning('请先删除所有的旧菜单数据 ');
        return;
      }
      this.$confirm('将使用permission.json的数据 进行初始化, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.initApi(permissionJson);
        })
        .catch(err => {
          console.error(err);
        });
    },
    onEdit(node, row) {
      this.defaultExpandedkeys = [row.id];
      this.$refs.DataForm.show({ ...row });
    },
    onAdd(row) {
      this.defaultExpandedkeys = [row.id];
      this.$refs.DataForm.show({ parentId: row.id });
    },
    reFresh() {
      this.API.getUserMenuTree().then(res => {
        this.tableData = res || [];
        this.mainId = this.tableData[0].id;
      });
    },
    remove(node, row) {
      this.defaultExpandedkeys = [row.parentId];
      this.onDelete(row);
    },
    onDelete(row) {
      this.$confirm('此操作将删除所选的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi(row);
        })
        .catch(err => {
          console.error(err);
        });
    },
    batchDelete() {
      const keys = this.$refs.tree.getCheckedKeys().filter(id => id != this.mainId);
      console.log(keys);
      if (keys.length) {
        this.$confirm('此操作将删除所选的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.deleteApiByList(keys, 0);
          })
          .catch(err => {
            console.error(err);
          });
      }
    },
    deleteApiByList(list, index) {
      console.log(list);
      if (index == 0) {
        this.API.batchDeleteMenuTree(list)
          .then(res => {
            if (res.id || res._token || Array.isArray(res) || res.failed === false) {
              this.$message.success('操作成功 ');
              this.reFresh();
            }
          })
          .catch(res => {
            if (res.failed) {
              this.deleteNext(list, index);
            }
          });
      } else {
        this.deleteNext(list, index);
      }
    },
    deleteNext(list, index) {
      if (index >= list.length) {
        this.$message.success('所有操作已结束 ');
        setTimeout(() => {
          this.reFresh();
        }, 500);
        return;
      }
      const API_DATA = { id: list[index] };
      this.API.deleteMenuTree(API_DATA).then(res => {
        if (res.id || res._token || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功 ' + index);
          this.deleteApiByList(list, index + 1);
        }
      });
    },
    deleteApi(API_DATA) {
      this.API.deleteMenuTree(API_DATA).then(res => {
        if (res.id || res._token || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    initApi(API_DATA) {
      this.API.initMenuTree(API_DATA).then(res => {
        if (res.id || res._token || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss">
.css-2022-2-11-09-54-35 {
  .el-tag-left {
    margin-right: 8px;
  }
  .el-tree-1 {
    .custom-tree-node {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 14px;
      padding-right: 8px;
      height: 32px;
      /* border-bottom: 1px solid #eee; */
      .span-btn {
        display: none;
      }
    }
    .custom-tree-node:hover .span-btn {
      display: inline-block;
    }
  }
}
</style>
