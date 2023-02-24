<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="24">
      <el-form-item label="策略名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="账单类型" prop="type">
        <BjRadioLov
          v-model="form.type"
          :option-list="FINANCE_PARSESTRATEGY_BULL_TYPES"
          placeholder="请选择"
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="渠道" prop="channelTypeCode">
        <BjSelectLov
          v-model="form.channelTypeCode"
          lov-code="SCHL.CHANNEL_TYPE"
          @change="getBasic"
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="取值字段" prop="filterField">
        <BjSelectLov v-model="form.filterField" :option-list="valuesList" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="匹配关键字" prop="filterKeyword">
        <el-input
          v-model.trim="form.filterKeyword"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="支持多个，以|隔开"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="订单号取值" prop="orderNoField">
        <BjSelectLov v-model="form.orderNoField" :option-list="valuesList" />
      </el-form-item>
    </el-col>
    <el-col v-if="form.type == 1" :span="24">
      <el-form-item label="退款单号取值" prop="refundNoField" :rules="rules.refundNoField">
        <BjSelectLov v-model="form.refundNoField" :option-list="valuesList" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="金额取值" prop="amountType">
        <BjRadioLov
          v-model="form.amountType"
          :option-list="FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES"
          placeholder="请选择"
        />
        <div v-if="form.amountType == 1">
          <div v-for="(item, index) in amountFormulaListFilter" :key="index" class="flex-field">
            <span>字段{{ index + 1 }} : </span>
            <BjSelectLov v-model="item.value" :option-list="valuesList" />
            <el-button v-if="index > 0" type="text" @click="onAddField(index)">增加字段</el-button>
            <el-button
              v-if="index >= 2"
              type="text"
              class="delete-btn"
              @click="onDeleteField(index)"
              >删除</el-button
            >
          </div>
          计算规则 =
          <span v-for="(e, i) in form.amountFormulaList" :key="i + '_amountFormulaList'">
            <span v-if="e.type === 'field'">{{ '字段' + (i / 2 + 1) }}</span>
            <span
              v-if="e.type === 'symbol'"
              style="display: inline-block; width: 60px; margin: 0 6px"
            >
              <BjSelectLov
                v-model="form.amountFormulaList[i].value"
                :option-list="computingMethod"
              />
            </span>
          </span>
        </div>
      </el-form-item>
    </el-col>
    <el-col v-if="form.amountType == 0" :span="24">
      <el-form-item label="" prop="amountFixedField" :rules="rules.amountFixedField">
        <BjSelectLov v-model="form.amountFixedField" :option-list="valuesList" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="状态" prop="enabledFlag">
        <el-switch v-model="form.enabledFlag" />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { financeApi } from '@/api';
import {
  FINANCE_PARSESTRATEGY_BULL_TYPES,
  FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES,
} from '@/utils/constants';

const getDefaultForm = () => {
  return {
    id: null,
    name: null,
    type: 0,
    channelTypeCode: null,
    filterField: null,
    filterKeyword: null,
    refundNoField: null,
    amountType: 0,
    enabledFlag: true,
    amountFormulaList: [
      { type: 'field', value: null },
      { type: 'symbol', value: null },
      { type: 'field', value: null },
    ],
  };
};

export default {
  data() {
    return {
      FINANCE_PARSESTRATEGY_BULL_TYPES,
      FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES,
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      valuesList: [],

      computingMethod: [
        {
          label: '加',
          value: '+',
        },
        {
          label: '减',
          value: '-',
        },
        {
          label: '乘',
          value: '*',
        },
        {
          label: '除',
          value: '/',
        },
      ],
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入策略名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择账单类型', trigger: 'change' }],
        channelTypeCode: [{ required: true, message: '请选择渠道', trigger: 'change' }],
        filterField: [{ required: true, message: '请选择取值字段', trigger: 'change' }],
        filterKeyword: [{ required: true, message: '请输入匹配关键字', trigger: 'blur' }],
        orderNoField: [{ required: true, message: '请选择订单号取值', trigger: 'change' }],
        refundNoField: [{ required: true, message: '请选择退款单号取值', trigger: 'change' }],
        amountType: [{ required: true, message: '请选择金额取值', trigger: 'change' }],
        amountFixedField: [
          {
            required: true,
            message: '请选择字段',
            trigger: 'change',
          },
        ],
        enabledFlag: [{ required: true, message: '请选择状态', trigger: 'change' }],
      },
    };
  },
  computed: {
    amountFormulaListFilter() {
      return this.form.amountFormulaList.filter(e => e.type == 'field');
    },
  },
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      if (this.isEdit) {
        this.getBasicSettingFieldList(info.channelTypeCode);
      }
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑平台账单策略' : '新增平台账单策略');
      this.form = Object.assign(getDefaultForm(), info || {});
    },
    onAddField(index) {
      this.form.amountFormulaList.splice(
        index * 2 + 1, // 需要插入两条，数组实际位置要通过计算
        0,
        { type: 'symbol', value: null },
        { type: 'field', value: null },
      );
    },
    onDeleteField(index) {
      this.form.amountFormulaList.splice(index + 1, 2);
    },
    /** 根据渠道类型编码，获取字段集 */
    getBasic(channelTypeCode) {
      this.getBasicSettingFieldList(channelTypeCode);
    },
    handleConfirm() {
      console.log('确认', this.form);
      if (this.form.amountType == 1) {
        const bool = this.form.amountFormulaList.every(e => !!e.value);
        console.log(bool);
        if (!bool) {
          this.$message.warning(
            `请选择${this.FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES.bjGet(
              this.form.amountType,
            )}字段或计算规则`,
          );
          return;
        }
      }
      const API_DATA = this.$lodash.clone(this.form);
      /** 数据处理 */
      if (API_DATA.type == 0) delete API_DATA.refundNoField;
      API_DATA.amountType == 1
        ? delete API_DATA.amountFixedField
        : delete API_DATA.amountFormulaList;

      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }

      this.saveApi(API_DATA);
    },
    reFresh() {
      this.$emit('reFresh');
    },
    getBasicSettingFieldList(channelTypeCode) {
      financeApi
        .getBasicSettingFieldList({
          channelTypeCode,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.valuesList = (res || []).map(e => {
              return {
                label: e.name,
                value: e.field,
              };
            });
          }
        });
    },
    saveApi(API_DATA) {
      financeApi.saveParsingStrategys(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.flex-field {
  width: 100%;
  position: relative;
  display: flex;
}
.flex-field > * {
  max-width: 25%;
  margin: 4px 8px;
}
</style>
