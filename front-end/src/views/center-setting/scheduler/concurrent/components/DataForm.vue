<template>
  <div>
    <BjDrawerForm ref="BjDrawerForm" :form="form" :rules="rules" @handleConfirm="handleConfirm">
      <el-row>
        <el-form-item label="租户" prop="tenantId">
          <BjSelectTenant v-model.trim="form.tenantId" placeholder="请输入" clearable />
        </el-form-item>

        <el-form-item label="请求名称" prop="concurrentId">
          <el-input
            v-model="form.concName"
            :maxlength="MAX_LENGTH.DEFAULT"
            readonly
            placeholder="请选择"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search" @click="onAddSelected" />
          </el-input>
        </el-form-item>

        <el-form-item label="周期性" prop="cycleFlag">
          <el-switch v-model="form.cycleFlag" :active-value="1" :inactive-value="0" />
        </el-form-item>

        <template v-if="form.cycleFlag === 1">
          <el-form-item label="周期开始时间" prop="startDate">
            <el-date-picker
              v-model="form.startDate"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>

          <el-form-item label="周期结束时间" prop="endDate">
            <el-date-picker
              v-model="form.endDate"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>

          <el-form-item
            label="间隔类型"
            prop="intervalType"
            :rules="{ required: form.cycleFlag, message: '请选择', trigger: 'change' }"
          >
            <BjSelectLov v-model="form.intervalType" lov-code="HSDR.REQUEST.INTERVAL_TYPE" />
          </el-form-item>

          <el-form-item
            label="间隔大小"
            prop="intervalNumber"
            :rules="{ required: form.cycleFlag, message: '请输入', trigger: 'blur' }"
          >
            <el-input
              v-model.trim="form.intervalNumber"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>

          <el-form-item label="固定间隔-时" prop="intervalHour">
            <el-input
              v-model.trim="form.intervalHour"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>

          <el-form-item label="固定间隔-分" prop="intervalMinute">
            <el-input
              v-model.trim="form.intervalMinute"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>

          <el-form-item label="固定间隔-秒" prop="intervalSecond">
            <el-input
              v-model.trim="form.intervalSecond"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </template>
      </el-row>
      {{ paramForm }}
      <BjDivider>请求参数</BjDivider>
      <el-row>
        <el-form-item
          v-for="(p, i) in paramList"
          v-show="p.showFlag"
          :key="i"
          :label="p.paramName"
          :prop="p.paramCode"
          :rules="{
            required: !!p.notnullFlag,
            message: p.paramName + '不能为空',
            trigger: 'blur',
          }"
        >
          <el-input
            v-if="p.paramEditTypeCode.toUpperCase() === 'INPUT'"
            v-model="paramForm[p.paramCode]"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="!p.enabledFlag"
          />
          <el-input
            v-if="p.paramEditTypeCode.toUpperCase() === 'INPUTNUMBER'"
            v-model="paramForm[p.paramCode]"
            :maxlength="MAX_LENGTH.DEFAULT"
            type="number"
            :disabled="!p.enabledFlag"
            :required="!!p.notnullFlag"
          />

          <el-date-picker
            v-if="p.paramEditTypeCode.toUpperCase() === 'DATAPICKER'"
            v-model="paramForm[p.paramCode]"
            :disabled="!p.enabledFlag"
            :required="!!p.notnullFlag"
            class="full-width"
            value-format="yyyy-MM-dd"
            type="datetime"
            placeholder="请选择"
          />

          <el-select
            v-if="p.paramEditTypeCode.toUpperCase() === 'LOV'"
            v-model="paramForm[p.paramCode]"
            :disabled="!p.enabledFlag"
            :required="!!p.notnullFlag"
            class="full-width"
            placeholder="请选择"
            clearable
          >
            <el-option
              v-for="(item, index) in p.businessModelList || []"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <!-- concParamId: "=Wmq85IB4hwJMiSSt67xntA==="
concurrentId: "=o14JIHqJuY0d3HjU67vcrg==="
enabledFlag: 1
notnullFlag: 1
objectVersionNumber: 1
orderSeq: 10
showFlag: 1

paramEditTypeCode: "INPUT"
paramEditTypeMeaning: "文本"
paramFormatCode: "TEXT"
paramFormatMeaning: "文本"

paramCode: "reportUuid"
paramName: "报表uuid"

_innerMap (object, optional),
businessModel (string, optional): 业务模型 ,
concParamId (integer, optional): 表ID，主键，供其他表做外键 ,
concurrentId (integer, optional): 并发程序ID，hsdr_concurrent.concurrent_id,不可更改 ,
defaultValue (string, optional): 默认值 ,
enabledFlag (integer, optional): 启用标识 ,
notnullFlag (integer, optional): 是否必须 ,
orderSeq (integer, optional): 排序号 ,
paramCode (string, optional): 参数名称 ,
paramEditTypeCode (string, optional): 编辑类型，HSDR.PARAM_EDIT_TYPE ,
paramEditTypeMeaning (string, optional): 编辑类型Meaning ,
paramFormatCode (string, optional): 参数格式，HSDR.PARAM_FORMAT ,
paramFormatMeaning (string, optional): 参数格式Meaning ,
paramName (string, optional): 参数描述 ,
showFlag (integer, optional): 是否展示 ,
tenantId (integer, optional): 租户ID,hpfm_tenant.tenant_id,不可更改 ,
valueFiledFrom (string, optional): 字段值从 ,
valueFiledTo (string, optional): 字段值从 -->
      </el-row>
    </BjDrawerForm>
    <BjDialogList ref="dialogList" :config="dialogListConfig" @confirm="onConfirmDialogList" />
  </div>
</template>
<script>
import { settingApi } from '@/api';
import { EXECUTOR_TYPE_LIST, EXECUTOR_TYPE_JSON } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    concCode: null,
    concName: null,
    concurrentId: null,
    cron: null,
    cycleFlag: null,
    endDate: null,
    executableId: null,
    executorId: null,
    intervalHour: null,
    intervalMinute: null,
    intervalNumber: null,
    intervalSecond: null,
    intervalType: null,
    jobId: null,
    jobStatus: null,
    jobStatusMeaning: null,
    parentId: null,
    requestId: null,
    requestParam: null,
    startDate: null,
    taskId: null,
    tenantId: 'default',
    tenantName: null,
    username: null,
  };
};

export default {
  data() {
    return {
      EXECUTOR_TYPE_LIST,
      EXECUTOR_TYPE_JSON,

      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        concurrentId: [{ required: true, message: '请选择', trigger: 'change' }],
      },

      dialogListConfig: {
        title: '选择请求定义',
        columns: [
          {
            prop: 'concCode',
            label: '编码',
          },
          {
            prop: 'concName',
            label: '名称',
          },
        ],
        search: [
          {
            placeholder: '请输入名称',
            key: 'concName',
          },
        ],
        actionApi: settingApi.getRequest,
        query: '',
      },
      paramList: [],
      paramForm: null,
    };
  },
  computed: {},
  watch: {
    // 'form.orderSeq'(v) {
    //   this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    // },
    'form.executableCode'(v) {
      this.form.executableCode = this.form.executableCode.replace(/\s+/g, '');
    },
    isEdit: {
      handler(v) {
        this.title = v ? '编辑' : '新建';
      },
      immediate: true,
    },
  },

  mounted() {},
  methods: {
    onAddSelected() {
      this.$refs.dialogList.show();
    },
    onConfirmDialogList(data) {
      const TEMP = (data || [])[0];
      this.form.concCode = TEMP.concCode;
      this.form.concName = TEMP.concName;
      this.form.concurrentId = TEMP.concurrentId;
      settingApi.detailRequest({ concurrentId: TEMP.concurrentId }).then(async res => {
        console.log(res);
        // temp
        const LIST = res.paramList || [];
        // 参数JSON
        const PARAM_FORM = {};
        // 组成JSON，加businessModelList参数用于保存值集list
        LIST.forEach(element => {
          PARAM_FORM[element.paramCode] = null;
          element['businessModelList'] = [];
        });
        this.paramForm = { ...PARAM_FORM };
        this.paramList = [].concat(LIST);

        // 请求值集并保存返回值
        this.paramList.forEach((element, i) => {
          if (element.businessModel) {
            (function(element, i, that) {
              that.$store.dispatch('values/getValueList', element.businessModel).then(data => {
                that.paramList[i].businessModelList = data;
              });
            })(element, i, this);
          }
        });
      });
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑并发请求' : '新建并发请求');
      this.form = Object.assign(getDefaultForm(), info || {});
    },
    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      API_DATA['requestParam'] = JSON.stringify(this.paramForm);

      if (this.isEdit) {
        this.updateApi(API_DATA);
      } else {
        this.addApi(API_DATA);
      }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    // 修改API
    updateApi(API_DATA) {
      settingApi.addConcurrent(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      settingApi.addConcurrent(API_DATA).then(res => {
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
