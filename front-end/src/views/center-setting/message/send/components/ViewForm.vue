<template>
  <div class="app-container">
    <BjDivider>基础信息</BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="消息编码" prop="messageCode">
            <el-input
              v-model.trim="form.messageCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              :disabled="isEdit"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="消息名称" prop="messageName">
            <el-input
              v-model.trim="form.messageName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="启用" prop="enabledFlag ">
            <el-switch v-model.trim="form.enabledFlag" :active-value="1" :inactive-value="0" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider>模板配置</BjDivider>
    <div class="mb10">
      <el-button type="primary" @click="onAction('新建')">添加模板</el-button>
      <!-- <el-button
        :disabled="tableSelected.length == 0"
        type="primary"

        @click="onConifgFormDelete"
         class='delete-btn'>删除</el-button
      > -->
    </div>

    <el-table
      ref="multipleTable"
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      style="width: 100%"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" show-overflow-tooltip />

      <el-table-column prop="templateCode" label="消息模板编码" />

      <el-table-column prop="templateName" label="消模板名称" />

      <el-table-column prop="typeCode" label="消息类型">
        <template slot-scope="scope">{{
          $store.state.values.HMSG_MESSAGE_TYPE.bjGet(scope.row.typeCode)
        }}</template>
      </el-table-column>

      <el-table-column prop="serverCode" label="账户编码" />
      <el-table-column prop="serverName" label="账户名称" />

      <el-table-column prop="tryTimes" label="重试次数" />

      <el-table-column prop="enabledFlag" label="状态" />

      <el-table-column prop="remark" label="备注" />
      <el-table-column label="操作" width="120" align="center">
        <template slot-scope="scope">
          <el-button type="text" class="edit-btn" @click="onAction('编辑', scope.row)"
            >编辑</el-button
          >
          <el-button type="text" @click="onAction('预览', scope.row)">预览</el-button>
          <el-button type="text" class="delete-btn" @click="onAction('删除', scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <BjDivider />

    <span slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
    </span>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" @handleConfirm="handleDataFormConfirm" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { messageApi } from '@/api';
import DataForm from './ViewDataForm.vue';
import { randomID } from '@/utils';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    // categoryCode: null,
    // categoryMeaning: null,
    // description: null,
    enabledFlag: 0,
    messageCode: null,
    messageName: null,
    // receiveConfigFlag: 0,
    serverList: [
      // {
      //   _innerMap: {},
      //   enabledFlag: 0,
      //   remark: null,
      //   serverCode: null,
      //   serverName: null,
      //   tempServerId: 0,
      //   tempServerLineId: 0,
      //   templateCode: null,
      //   templateName: null,
      //   tenantId: 0,
      //   tryTimes: 0,
      //   typeCode: null,
      //   typeMeaning: null,
      // },
    ],
    // subcategoryCode: null,
    // subcategoryMeaning: null,
    // tempServerId: 0,
    tenantId: 'default',
  };
};

export default {
  name: 'SettingMessageSendViewForm',
  components: {
    DataForm,
  },
  mixins: [viewMiXin],
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      form: getDefaultForm(),
      // 表单校验
      rules: {
        messageCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        messageName: [{ required: true, message: '请输入', trigger: 'blur' }],

        enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      tableData: [],
      tableSelected: [],
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
          this.tableData = (info.serverList || []).map(obj => {
            return {
              BJ_EBC_ID: randomID(),
              ...obj,
            };
          });
        }
      },
    },
  },

  mounted() {},
  methods: {
    onAction(action, row) {
      switch (action) {
        case '新建':
          this.$refs.DataForm.show();
          break;
        case '编辑':
          this.$refs.DataForm.show(this.$lodash.clone(row));
          break;
        case '预览':
          this.codeTemplate({ templateCode: row.templateCode });

          break;
        case '删除':
          if (row.tempServerLineId) {
            this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
            })
              .then(() => {
                this.deleteLineApi(row);
              })
              .catch(() => {});
          } else {
            const index = this.tableData.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
            this.tableData.splice(index, 1);
          }
          break;
      }
    },

    handleDataFormConfirm(obj) {
      if (obj.tempServerLineId) {
        const index = this.tableData.findIndex(e => e.BJ_EBC_ID == obj.BJ_EBC_ID);
        this.tableData.splice(index, 1, obj);
      } else {
        this.tableData.push({
          BJ_EBC_ID: randomID(),
          ...obj,
          tempServerId: this.form.tempServerId,
        });
      }
    },
    handleSelectionChange(data) {
      console.log(data);
      this.tableSelected = [].concat(data);
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      API_DATA['serverList'] = this.tableData || [];
      console.log(API_DATA);
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          delete API_DATA._token;
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      messageApi.updateSend(API_DATA).then(res => {
        if (
          res.id ||
          res._token ||
          res.tempServerId ||
          Array.isArray(res) ||
          res.failed === false
        ) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      messageApi.addSend(API_DATA).then(res => {
        if (
          res.id ||
          res._token ||
          res.tempServerId ||
          Array.isArray(res) ||
          res.failed === false
        ) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
    deleteLineApi(API_DATA) {
      messageApi.deleteLineSend(API_DATA).then(res => {
        if (
          res.id ||
          res._token ||
          res.tempServerId ||
          Array.isArray(res) ||
          res.failed === false
        ) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    codeTemplate(API_DATA) {
      messageApi.codeTemplate(API_DATA).then(res => {
        const re = res.templateContent || '';

        if (re) {
          this.$alert(re, '消息预览', {
            dangerouslyUseHTMLString: true,
          });
        } else {
          this.$message.warning('模板内容为空');
        }
      });
    },
  },
};
</script>
