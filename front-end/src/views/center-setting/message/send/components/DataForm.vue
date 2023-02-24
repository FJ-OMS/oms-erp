<template>
  <div>
    <BjDrawerForm
      ref="BjDrawerForm"
      :form="form"
      :rules="rules"
      label-width="120px"
      width="800px"
      @handleConfirm="handleConfirm"
    >
      <el-col :span="22">
        <BjDivider>基础信息</BjDivider>
      </el-col>
      <el-col :span="22">
        <el-form-item label="消息编码" prop="messageCode">
          <el-input
            v-model.trim="form.messageCode"
            :maxlength="MAX_LENGTH.DEFAULT"
            disabled
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <el-form-item label="消息名称" prop="messageName">
          <el-input
            v-model.trim="form.messageName"
            :maxlength="MAX_LENGTH.DEFAULT"
            disabled
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <BjDivider>参数</BjDivider>
      </el-col>
      <el-col v-for="(key, index) in argList" :key="index" :span="22">
        <el-form-item :label="`${key}:`" required>
          <el-input
            v-model.trim="args[key]"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <BjDivider>接收人</BjDivider>
      </el-col>

      <el-col :span="22">
        <el-row>
          <template v-for="(send, i) in sendData">
            <el-col :key="'a' + i" :span="11">
              <el-form-item label="类型：" required>
                <el-select
                  v-model.trim="send.type"
                  class="full-width"
                  placeholder="请选择"
                  clearable
                >
                  <el-option
                    v-for="(item, index) in form.serverList"
                    :key="index"
                    :label="item.typeMeaning"
                    :value="item.typeCode"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :key="'b' + i" :span="11">
              <el-form-item label="账号：" required>
                <el-input
                  v-model.trim="send.name"
                  :maxlength="MAX_LENGTH.DEFAULT"
                  placeholder="请输入"
                  clearable
                />
              </el-form-item>
            </el-col>
            <el-col :key="'c' + i" :span="2">
              <el-form-item label="" label-width="10px">
                <i class="el-icon-remove-outline" />
              </el-form-item>
            </el-col>
          </template>
        </el-row>
      </el-col>
      <el-col :span="22">
        <el-form-item label="">
          <el-button type="primary" icon="el-icon-circle-plus-outline" @click="onAdd"
            >添加接收人</el-button
          >
        </el-form-item>
      </el-col>
    </BjDrawerForm>
  </div>
</template>
<script>
import { messageApi } from '@/api';

const getDefaultForm = () => {
  return {
    messageCode: null,
    messageName: null,
    serverList: null,
  };
};

export default {
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),

      // 表单校验
      rules: {
        // templateCode: [{ required: true, message: '请选择', trigger: 'change' }],
        messageCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        messageName: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
      sendData: [],

      args: {},
    };
  },
  computed: {
    argList() {
      return Object.keys(this.args);
    },
  },
  watch: {},
  mounted() {},
  methods: {
    onAdd() {
      this.sendData.push({
        type: '',
        name: '',
      });
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle('测试发送');
      this.form = Object.assign(getDefaultForm(), info || {});
      this.sendData = [];

      this.argsTemplate(this.form.messageCode);
    },
    handleClose() {
      this.$refs.BjDrawerForm.hide();
    },
    handleConfirm() {
      for (const key in this.args) {
        if (!this.args[key]) {
          this.$message.warning(`请填写参数 ${key} 的值`);
          return;
        }
      }

      if (!this.sendData || this.sendData.length == 0) {
        this.$message.warning('请添加接收人');
        return;
      }
      const LEN = this.sendData.length;
      for (let i = 0; i < LEN; i++) {
        console.log(this.sendData[i]);
        if (!this.sendData[i].type) {
          this.$message.warning('请选择接收人类型' + this.sendData[i].type);
          return;
        }
        if (!this.sendData[i].name) {
          this.$message.warning('请填写接收人账号' + this.sendData[i].name);
          return;
        }
      }

      const API_DATA = {
        args: this.args,
        messageCode: this.form.messageCode,
        receiverAddressList: this.sendData.map(e => {
          if (e.type === 'EMAIL') {
            return {
              email: e.name,
            };
          }
          return {
            idd: '+86',
            phone: e.name,
          };
        }),
      };
      this.sendApi(API_DATA);
    },
    reFresh() {
      this.$emit('reFresh');
    },

    sendApi(API_DATA) {
      messageApi.sendMessageRelevance(API_DATA).then(res => {
        this.$confirm(`发送结果：${JSON.stringify(res)}`, '提示', {
          confirmButtonText: '返回列表',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            setTimeout(() => {
              this.handleClose();
              this.reFresh();
            }, 500);
          })
          .catch(() => {});
      });
    },
    // 根据CODE 查参数key
    argsTemplate(code) {
      messageApi
        .argsTemplate({
          messageCode: code,
          lang: 'zh_CN',
        })
        .then(res => {
          (res || []).forEach(key => {
            this.$set(this.args, key, '');
          });
        });
    },
  },
};
</script>
<style scoped>
.el-tag {
  margin: 6px;
}
.el-icon-remove-outline {
  font-size: 20px;
  line-height: 33px;
}
</style>
