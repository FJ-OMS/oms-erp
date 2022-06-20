<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="75%"
      append-to-body
    >
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="configId" label="ID">
          <template slot-scope="props">
            <span v-show="isRealConfigRow(props.row)">{{ props.row.configId }}</span>
            <el-tag v-show="!isRealConfigRow(props.row)" type="warning">未保存</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="执行器地址">
          <template slot-scope="props">
            <el-input
              v-show="isEditConfigRow(props.row)"
              v-model="props.row.address"
              :maxlength="MAX_LENGTH.DEFAULT"
            />
            <span v-show="!isEditConfigRow(props.row)">{{ props.row.address }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="weight" label="权重">
          <template slot-scope="props">
            <el-input
              v-show="isEditConfigRow(props.row)"
              v-model="props.row.weight"
              :maxlength="MAX_LENGTH.DEFAULT"
            />
            <span v-show="!isEditConfigRow(props.row)">{{ props.row.weight }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="maxConcurrent " label="最大并发量">
          <template slot-scope="props">
            <el-input
              v-show="isEditConfigRow(props.row)"
              v-model="props.row.maxConcurrent"
              :maxlength="MAX_LENGTH.DEFAULT"
            />
            <span v-show="!isEditConfigRow(props.row)">{{ props.row.maxConcurrent }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="enabledFlag" label="状态">
          <template slot-scope="props">
            <el-switch
              v-model="props.row.enabledFlag"
              :disabled="!isEditConfigRow(props.row)"
              :active-value="1"
              :inactive-value="0"
            />
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="props">
            <el-button
              v-show="!isEditConfigRow(props.row)"
              type="text"
              size="small"
              class="edit-btn"
              @click="onEditExecutorsConfig(props.row)"
              >编辑</el-button
            >
            <el-button
              v-show="isEditConfigRow(props.row)"
              type="text"
              size="small"
              @click="onCancelExecutorsConfig(props.row, props.$index)"
              >取消</el-button
            >
            <el-button
              v-show="isEditConfigRow(props.row)"
              type="text"
              size="small"
              @click="onSaveExecutorsConfig(props.row)"
              >保存</el-button
            >
            <el-button
              v-show="isRealConfigRow(props.row) && !isEditConfigRow(props.row)"
              type="text"
              size="small"
              class="delete-btn"
              @click="onDeleteExecutorsConfig(props.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="addOneExecutorsConfig">新 增</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { settingApi } from '@/api';
import { randomID } from '@/utils/index';

export default {
  data() {
    return {
      title: '',
      dialogVisible: false,
      editingBjEbcId: null, // 配置表，正在编辑的
      tableData: [],

      id: '',
    };
  },
  watch: {},
  methods: {
    show(row) {
      this.dialogVisible = true;

      this.title = `配置执行器(${row.executorName})`;
      this.id = row.executorId;
      this.getList();
    },
    reFresh() {
      this.$emit('reFresh');
    },
    /* 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false;
    },

    isRealConfigRow(row) {
      return row.configId && row._token;
    },
    isEditConfigRow(row) {
      return this.editingBjEbcId == row.BJ_EBC_ID;
    },
    addOneExecutorsConfig() {
      this.editingBjEbcId = 'NEW_' + randomID();
      this.tableData.push({
        BJ_EBC_ID: this.editingBjEbcId,
        address: '',
        enabledFlag: 0,
        executorId: this.id,
        weight: 1,
      });
    },
    onEditExecutorsConfig(row) {
      this.editingBjEbcId = row.BJ_EBC_ID;
    },
    onCancelExecutorsConfig(row, index) {
      this.editingBjEbcId = null;
      if (row.BJ_EBC_ID.indexOf('NEW') === 0) {
        this.tableData.splice(index, 1);
      }
    },
    onSaveExecutorsConfig(row) {
      this.updateExecutorsConfig([row]);
    },
    onDeleteExecutorsConfig(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteExecutorsConfig([row]);
        })
        .catch(() => {});
    },

    // 修改API
    updateExecutorsConfig(API_DATA) {
      settingApi.updateExecutorsConfig(API_DATA).then(res => {
        if (Array.isArray(res)) {
          res = res[0];
        }
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.getList();
          }, 500);
        }
      });
    },
    deleteExecutorsConfig(API_DATA) {
      settingApi.deleteExecutorsConfig(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('删除成功');
          setTimeout(() => {
            this.getList();
          }, 500);
        }
      });
    },
    // 执行器配置列表
    getList() {
      settingApi
        .getExecutorsConfig({
          executorId: this.id,
        })
        .then(res => {
          if (Array.isArray(res)) {
            this.tableData = res.map(e => {
              return {
                ...e,
                BJ_EBC_ID: randomID(),
              };
            });
          }
        });
    },
  },
};
</script>
