<!--
 * @Author: your name
 * @Date: 2021-07-22 17:23:24
 * @LastEditTime: 2021-07-28 15:03:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\inquiry\components\inquiryOp.vue
-->
<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" inline label-width="140px">
      <el-form-item label="编码" prop="configCode">
        <el-input v-model="form.configCode" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="索引" prop="indexCode">
        <bj-code-select v-model="form.indexCode" @change="changeIndexCode" />
      </el-form-item>
      <el-form-item label="状态" prop="enabledFlag">
        <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="form.remark" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="生效开始时间" prop="activeStartTime">
        <el-date-picker
          v-model="form.activeStartTime"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="生效结束时间" prop="activeEndTime">
        <el-date-picker
          v-model="form.activeEndTime"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="请选择"
        />
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName">
      <el-tab-pane label="查询" name="1">
        <el-tabs tab-position="left" type="border-card">
          <!-- <el-tab-pane label="配置查询">
            <el-row>
              <el-col :span="10">
                <div class="flex">
                  <span>查询</span>
                  <el-button  @click="addInquire"> 新 增</el-button>
                </div>
                <el-divider />
                <condition />
              </el-col>
              <el-col :span="10" :offset="4">
              </el-col>
            </el-row>
          </el-tab-pane> -->
          <!-- <el-tab-pane label="SQL查询"></el-tab-pane> -->
          <el-tab-pane label="自定义参数">
            <el-row>
              <el-col :span="10">
                <vue-json-editor
                  v-model="resultInfo"
                  :show-btns="false"
                  :mode="'code'"
                  lang="zh"
                  @json-change="onJsonChange"
                  @json-save="onJsonSave"
                  @has-error="onError"
                />
                <div style="text-align: center">
                  <el-button type="primary" class="mt20" @click="submitJson">保 存</el-button>
                </div>
              </el-col>
              <el-col :span="10" :offset="4">
                <vue-json-editor
                  v-model="resultInfo"
                  :show-btns="false"
                  :mode="'code'"
                  lang="zh"
                  class="read-only"
                  :class="resultInfo ? '' : 'hide_json'"
                />
                <div style="text-align: center">
                  <el-button type="primary" class="mt20" @click="onTest">测 试</el-button>
                </div>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
      <!-- <el-tab-pane label="显示字段" name="2">
        <field-list :data="fieldList" />
      </el-tab-pane> -->
    </el-tabs>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="测试"
      :visible.sync="dialogVisible"
      width="420px"
    >
      <el-form label-width="100px">
        <el-form-item v-for="item in formList" :key="item" :label="item" inline>
          <el-input
            v-model="testForm[item]"
            :maxlength="MAX_LENGTH.DEFAULT"
            :placeholder="'请输入' + item"
          />
        </el-form-item>
        <el-form-item label="返回结果：">
          <!-- <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="testForm[item]" :placeholder="'请输入' + item" /> -->
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onExecute()">执 行</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { settingApi } from '@/api';
// import condition from './condition.vue';
import vueJsonEditor from 'vue-json-editor';
import BjCodeSelect from '../../components/BjCodeSelect';
// import fieldList from './fieldList';
export default {
  components: {
    // condition,
    vueJsonEditor,
    BjCodeSelect,
    // fieldList,
  },
  data() {
    return {
      form: {},
      rules: {
        configCode: [{ required: true, message: '请输入编码', trigger: 'blur' }],
        indexCode: [{ required: true, message: '请输入索引', trigger: 'change' }],
        activeStartTime: [{ required: true, message: '请输入生效开始时间', trigger: 'change' }],
        activeEndTime: [{ required: true, message: '请输入生效结束时间', trigger: 'change' }],
      },
      activeName: '1',
      resultInfo: null,
      hasJsonFlag: true,
      edit: false,

      fieldList: [],
      dialogVisible: false,
      formList: [],
      testForm: null,
    };
  },
  created() {
    if (this.$route.query.form) {
      this.form = JSON.parse(this.$route.query.form);
      this.form.queryJson = JSON.parse(this.form.queryJson);
      this.resultInfo = this.form.queryJson;
      this.edit = true;
      settingApi
        .getInquiryFieldList({ indexCode: this.form.indexCode, enabledFlag: this.form.enabledFlag })
        .then(res => {
          this.fieldList.push(res);
        });
    }
  },
  methods: {
    changeIndexCode(data) {
      this.form.indexCode = data.indexCode;
      this.form.indexId = data.indexId;
    },
    addInquire() {},
    onJsonChange(value) {
      this.hasJsonFlag = true;
      this.resultInfo = this.form.queryJson;
    },
    onJsonSave(value) {},
    onError(value) {
      this.hasJsonFlag = false;
    },
    submitJson() {
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.hasJsonFlag) {
          this.form.queryJson = JSON.stringify(this.resultInfo);
          if (this.edit) {
            settingApi.editInquiry(this.form).then(res => {
              if (!res.failed) {
                this.msgSuccess('修改成功');
                this.$router.back(-1);
              }
            });
          } else {
            settingApi.addInquiry(this.form).then(res => {
              if (!res.failed) {
                this.msgSuccess('添加成功');
                this.form = {};
                this.$router.back(-1);
              }
            });
          }
        } else {
          this.$alert('请输入JSON类型的自定义数据', '提示', {
            confirmButtonText: '确定',
            callback: action => {},
          });
        }
      });
    },
    /** 测试 */
    onTest() {
      this.formList = this.getExecData(JSON.stringify(this.resultInfo));
      this.dialogVisible = true;
    },
    /** 执行 */
    onExecute() {
      const str = JSON.stringify(this.resultInfo);
      const reg = /\#\{(.+?)\}/g;
      const changeStr = str.replace(reg, item => {
        for (const key in this.testForm) {
          const name = '#{' + key + '}';
          if (name === item) {
            return this.testForm[key];
          }
        }
      });
      console.log(changeStr);
      // console.log(this.form);
      const obj = { configCode: this.form.configCode, queryMap: this.testForm };
      console.log(obj);
      settingApi.InquiryFieldQuery(obj).then(res => {
        console.log(res);
      });
    },
    /** 收集字段 */
    getExecData(str) {
      const reg = /\#\{(.+?)\}/g;
      const list = [];
      let result = null;
      do {
        result = reg.exec(str);
        console.log(result);
        result && list.push(result[1]);
      } while (result);
      return list;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
  },
};
</script>
<style scoped>
.flex {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
::v-deep .jsoneditor-menu {
  display: none;
}
::v-deep .read-only .ace_text-input {
  display: none;
}
::v-deep .read-only .ace-jsoneditor .ace_marker-layer .ace_active-line {
  background: none;
}
::v-deep .hide_json .ace_scroller {
  display: none;
}
::v-deep .read-only .ace_gutter-active-line {
  background: none;
}
pre {
  display: block;
  white-space: pre;
}
</style>
