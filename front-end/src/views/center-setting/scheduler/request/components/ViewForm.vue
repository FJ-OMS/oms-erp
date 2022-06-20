<template>
  <div class="app-container">
    <BjDivider>请求定义</BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <!-- <el-col :span="8">
          <el-form-item label="租户" prop="tenantId">
            <BjSelectTenant v-model.trim="form.tenantId" placeholder="请输入"  clearable />
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="请求编码" prop="concCode">
            <el-input
              v-model.trim="form.concCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              :disabled="isEdit"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="请求描述" prop="concDescription">
            <el-input
              v-model.trim="form.concDescription"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <BjFormItemEmail
            v-model.trim="form.alarmEmail"
            prop="alarmEmail"
            label="报警邮箱"
            placeholder="请输入"
            clearable
          />
        </el-col>

        <el-col :span="8">
          <el-form-item label="请求名称" prop="concName">
            <el-input
              v-model.trim="form.concName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="可执行名称" prop="executableId">
            <BjSelectLov v-model="form.executableId" lov-code="BJ_EXECUTABLE_ID" />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="状态" prop="enabledFlag">
            <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider>参数</BjDivider>
    <el-button class="mb16" type="primary" @click="addOneExecutorsConfig">新 增</el-button>
    <el-table :data="form.paramList" border style="width: 100%">
      <el-table-column prop="orderSeq" label="排序号">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.orderSeq"
            :maxlength="MAX_LENGTH.DEFAULT"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.orderSeq }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="paramCode " label="参数名称">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.paramCode"
            :maxlength="MAX_LENGTH.DEFAULT"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.paramCode }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="paramName" label="参数描述">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.paramName"
            :maxlength="MAX_LENGTH.DEFAULT"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.paramName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="paramFormatCode" label="参数格式">
        <template slot-scope="props">
          <BjSelectLov
            v-model="props.row.paramFormatCode"
            lov-code="HSDR.PARAM_FORMAT"
            :disabled="!isEditConfigRow(props.row)"
            clearable
            @change="onChangeParamFormatCode(props.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="paramEditTypeCode " label="编辑类型">
        <template slot-scope="props">
          <el-select
            v-model.trim="props.row.paramEditTypeCode"
            :disabled="!isEditConfigRow(props.row)"
            class="full-width"
            placeholder="请选择"
            clearable
            @change="onChangeParamEditTypeCode(props.row)"
          >
            <el-option
              v-for="(item, index) in HSDR_PARAM_EDIT_TYPE_FILTER"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="businessModel " label="业务模型">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.businessModel"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="businessModelDisabled"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.businessModel }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="defaultValue" label="默认值">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.defaultValue"
            :maxlength="MAX_LENGTH.DEFAULT"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.defaultValue }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="valueFiledFrom" label="字段值从">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.valueFiledFrom"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="valueFiledFromToDisabled"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.valueFiledFrom }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="valueFiledTo" label="字段值至">
        <template slot-scope="props">
          <el-input
            v-show="isEditConfigRow(props.row)"
            v-model="props.row.valueFiledTo"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="valueFiledFromToDisabled"
          />
          <span v-show="!isEditConfigRow(props.row)">{{ props.row.valueFiledTo }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="notnullFlag " label="是否必须">
        <template slot-scope="props">
          <el-switch
            v-model="props.row.notnullFlag"
            :disabled="!isEditConfigRow(props.row)"
            :active-value="1"
            :inactive-value="0"
          />
        </template>
      </el-table-column>
      <el-table-column prop="showFlag" label="是否展示">
        <template slot-scope="props">
          <el-switch
            v-model="props.row.showFlag"
            :disabled="!isEditConfigRow(props.row)"
            :active-value="1"
            :inactive-value="0"
          />
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
            @click="onDeleteExecutorsConfig(props.row, props.$index)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <p class="tips">
      数字参数格式（可以填写字段值从，字段值至，默认值）<br />
      日期参数格式（分日期选择框，日期时间选择框，差异仅在默认值，可以填写字段值从，字段值至，默认值）<br />
      文本参数格式（分为LOV，文本，勾选框，下拉单选，下拉多选，可以填写业务模型和默认值）<br />
      LOV：业务模型填写值集视图的视图代码<br />
      文本框：只可填写默认值<br />
      勾选框：业务模型填写值集编码，默认值填写值集中的值<br />
      下拉单选：业务模型填写值集编码，默认值填写值集中的值，显示值填写值集中的含义<br />
      下拉多选：业务模型填写值集编码，默认值填写值集中的值，显示值填写值集中的含义<br />
    </p>
    <BjDivider>操作</BjDivider>
    <span slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm('form')">确 定</el-button>
    </span>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { settingApi } from '@/api';
import { EXECUTOR_TYPE_LIST, EXECUTOR_TYPE_JSON } from '@/utils/constants';
import { patternCode } from '@/utils/validate';
import { randomID } from '@/utils/index';
const getDefaultForm = () => {
  return {
    concCode: null,
    concDescription: null,
    alarmEmail: null,
    concName: null,
    executableId: null,
    enabledFlag: 1,
    tenantId: 'default',
    paramList: [],
  };
};

export default {
  name: 'SettingSchedulerRequestAdd',
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
      EXECUTOR_TYPE_LIST,
      EXECUTOR_TYPE_JSON,

      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
        concCode: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: patternCode, message: '大写字母、数字 和 -_.', trigger: 'change' },
        ],
        concName: [{ required: true, message: '请输入', trigger: 'blur' }],
        // concDescriptio: [{ required: true, message: '请选择', trigger: 'change' }],,
        executableId: [{ required: true, message: '请选择', trigger: 'change' }],
      },

      /** */
      editingBjEbcId: null, // 配置表，正在编辑的

      valueFiledFromToDisabled: false,
      businessModelDisabled: false,

      HSDR_PARAM_EDIT_TYPE_FILTER: [],
    };
  },
  computed: {
    HSDR_PARAM_EDIT_TYPE() {
      return this.$store.state.values.HSDR_PARAM_EDIT_TYPE;
    },
  },
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.editingBjEbcId = null;
          this.form = this.$lodash.clone(info);
          this.form.paramList = info.paramList.map(e => {
            return {
              BJ_EBC_ID: randomID(),
              ...e,
            };
          });
        }
      },
    },
    'form.concCode'(v) {
      this.form.concCode = this.form.concCode.replace(/\s+/g, '');
    },
    HSDR_PARAM_EDIT_TYPE(v) {
      this.HSDR_PARAM_EDIT_TYPE_FILTER = [].concat(v);
    },
  },

  mounted() {
    this.$store.dispatch('values/getValueList', 'HSDR.PARAM_EDIT_TYPE');
  },
  methods: {
    onChangeParamFormatCode(row) {
      this.valueFiledFromToDisabled = false;
      switch (row.paramFormatCode) {
        case 'DATE': {
          row.paramEditTypeCode = 'DATAPICKER';
          this.HSDR_PARAM_EDIT_TYPE_FILTER = this.HSDR_PARAM_EDIT_TYPE.filter(
            e => e.value == 'DATAPICKER',
          );
          break;
        }
        case 'NUMERICAL': {
          row.paramEditTypeCode = 'INPUTNUMBER';
          this.HSDR_PARAM_EDIT_TYPE_FILTER = this.HSDR_PARAM_EDIT_TYPE.filter(
            e => e.value == 'INPUTNUMBER',
          );
          break;
        }
        case 'TEXT': {
          row.paramEditTypeCode = 'INPUT';
          this.valueFiledFromToDisabled = true;
          row.valueFiledFrom = null;
          row.valueFiledTo = null;
          this.HSDR_PARAM_EDIT_TYPE_FILTER = this.HSDR_PARAM_EDIT_TYPE.filter(
            e => e.value != 'DATAPICKER' && e.value != 'INPUTNUMBER',
          );
          break;
        }

        default:
          row.paramEditTypeCode = null;
      }
      this.onChangeParamEditTypeCode(row);
    },
    onChangeParamEditTypeCode(row) {
      this.businessModelDisabled = false;
      switch (row.paramEditTypeCode) {
        case 'LOV':
        case 'CHECKBOX':
        case 'COMBOBOX':
        case 'MULTIPLE': {
          this.businessModelDisabled = false;
          break;
        }

        case 'DATAPICKER': {
          row.paramFormatCode = 'DATE';
          row.businessModel = null;
          this.businessModelDisabled = true;
          break;
        }
        case 'NUMERICAL': {
          row.paramFormatCode = 'NUMERICAL';
          row.businessModel = null;
          this.businessModelDisabled = true;
          break;
        }
        case 'TEXT': {
          row.paramFormatCode = 'TEXT';
          row.valueFiledFrom = null;
          row.valueFiledTo = null;
          break;
        }

        default:
        // row.paramFormatCode = null;
      }
    },
    // show(info) {
    //   this.isEdit = !!info;
    //   this.form = Object.assign(getDefaultForm(), info || {});
    // },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
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

    /** *
     *
     */
    isRealConfigRow(row) {
      return !!row.concParamId;
    },
    isEditConfigRow(row) {
      return this.editingBjEbcId == row.BJ_EBC_ID;
    },
    addOneExecutorsConfig() {
      if (this.editingBjEbcId) {
        this.$message.warning('请先保存正在编辑的数据');
        return;
      }
      this.editingBjEbcId = 'NEW_' + randomID();
      this.form.paramList.push({
        BJ_EBC_ID: this.editingBjEbcId,

        defaultValue: null,
        enabledFlag: null,
        notnullFlag: null,
        orderSeq: null,
        paramCode: null,
        paramEditTypeCode: null,

        paramFormatCode: null,

        paramName: null,
        showFlag: null,

        businessModel: null,
        valueFiledFrom: null,
        valueFiledTo: null,
        tenantId: this.$store.getters.tenantId,
      });
    },
    onEditExecutorsConfig(row) {
      this.editingBjEbcId = row.BJ_EBC_ID;
    },
    onCancelExecutorsConfig(row, index) {
      this.editingBjEbcId = null;
      if (row.BJ_EBC_ID.indexOf('NEW') === 0) {
        this.form.paramList.splice(index, 1);
      }
    },
    onSaveExecutorsConfig(row) {
      this.editingBjEbcId = null;
    },
    onDeleteExecutorsConfig(row, index) {
      this.editingBjEbcId = null;
      this.form.paramList.splice(index, 1);
    },

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      settingApi.updateRequest(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      settingApi.addRequest(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
