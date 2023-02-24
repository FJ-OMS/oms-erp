<template>
  <div>
    <BjDrawerForm
      ref="BjDrawerForm"
      :form="form"
      :rules="rules"
      width="800px"
      @handleConfirm="handleConfirm"
    >
      <el-col :span="22">
        <el-form-item label="序号" prop="orderSeq">
          <el-input
            v-model.trim="form.orderSeq"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>

        <el-form-item label="段类型" prop="fieldType">
          <BjSelectLov
            v-model="form.fieldType"
            :disabled="isEdit"
            constant-key="CODE_RULE_FIELD_TYPE_LIST"
          />
        </el-form-item>

        <!--
        UUID：生成8/16/22/32位的UUID
        日期：当前日期，日期格式掩码可以在值集HPFM.CODE_RULE.DATE_MASK中按需定义
        常量：固定值
        变量：动态传入的变量，可选值在值集HPFM.CODE_RULE.VARIABLE中按需定义 //
        序列：自增数字，可以设置最小显示长度（不足高位补0，如果超出长度不会截断），重置频率和起始值 -->

        <!-- 每个编码规则里面只能创建一个段类型为： 序列 -->
        <template v-if="form.fieldType == 'SEQUENCE'">
          <!-- 选项：是/否  -----打开新建时占用：获取编号，编号立马占用 ; 业务保存时占用：获取编号，但不占用编号，当业务操作保存时，编号入库成功，编号才被占用 -->
          <!-- <el-form-item label="占用策略" prop="xxx">
            TODO:对不上
          </el-form-item> -->

          <el-form-item label="最小位数" prop="seqLength">
            <el-input
              v-model.trim="form.seqLength"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>

          <!-- ① 开始值位数>最小位数：不截断  ② 开始值位数<最小位数：用0补齐至高位 -->
          <el-form-item label="开始值" prop="startValue">
            <el-input
              v-model.trim="form.startValue"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
          <!-- 如步长是负数 -->
          <!-- <el-form-item label="步长" prop="seqLength">
            TODO:对不上
          </el-form-item> -->
          <!-- 非必填 -->
          <!-- <el-form-item label="最大值" prop="xxx">
            TODO:对不上
          </el-form-item> -->
        </template>

        <el-form-item v-if="form.fieldType == 'CONSTANT'" label="段值" prop="fieldValue">
          <el-input
            v-model.trim="form.fieldValue"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
        <el-form-item v-if="form.fieldType == 'DATE'" label="日期掩码" prop="dateMask">
          <BjSelectLov v-model="form.dateMask" lov-code="HPFM.CODE_RULE.DATE_MASK" />
        </el-form-item>

        <el-form-item v-if="form.fieldType == 'VARIABLE'" label="变量" prop="fieldValue">
          <BjSelectLov v-model="form.fieldValue" lov-code="HPFM.CODE_RULE.VARIABLE" />
        </el-form-item>

        <el-form-item v-if="form.fieldType == 'UUID'" label="位数" prop="fieldValue">
          <BjSelectLov v-model="form.fieldValue" constant-key="UUID_LEN_LIST" />
        </el-form-item>
      </el-col>
    </BjDrawerForm>
  </div>
</template>
<script>
import { baseDataApi } from '@/api';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    // codeRuleKey: {
    // failFastKey: null,
    // key: null,
    // level: null,
    // levelCode: null,
    // levelValue: null,
    // previous: {},
    // previousTenantId:null,
    // ruleCode: null,
    // sequenceKey: null,
    // tenantId:null,
    // usedKey: null,
    // },
    currentValue: null,
    dateMask: null,
    enabledFlag: null,
    encryptedFlag: null,
    fieldType: null,
    fieldValue: null,
    orderSeq: null,
    resetDate: null,
    resetFrequency: null,
    ruleDetailId: null,
    ruleDistId: null,
    seqLength: null,
    startValue: null,
    tenantId: 'default',
  };
};

export default {
  props: {
    ruleDistId: {
      type: String,
      default: '',
    },
    ruleDetailId: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        orderSeq: [{ required: true, message: '请输入', trigger: 'blur' }],
        fieldType: [{ required: true, message: '请选择', trigger: 'change' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑编码段' : '新建编码段');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      if (!API_DATA['ruleDistId']) API_DATA['ruleDistId'] = this.ruleDistId;

      // if (this.isEdit) {
      //   this.updateApi(API_DATA);
      // } else {
      this.addApi(API_DATA);
      // }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    addApi(API_DATA) {
      baseDataApi.addCodeRuleDetail(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      baseDataApi.addCodeRuleDetail(API_DATA).then(res => {
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
<style scoped>
.el-tag {
  margin: 6px;
}
</style>
