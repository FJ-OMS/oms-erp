<template>
  <div class="app-container goods-attribute-view-form">
    <BjDivider right-fixed
      >基础信息
      <template slot="right">
        <el-button @click="tryCancel">取 消</el-button>
        <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
      </template>
    </BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="规则编码" prop="ruleCode">
            <el-input
              v-model.trim="form.ruleCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              :disabled="isEdit"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规则名称" prop="ruleName">
            <el-input
              v-model.trim="form.ruleName"
              :maxlength="MAX_LENGTH.DEFAULT"
              :disabled="isEdit"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="规则描述" prop="description">
            <el-input
              v-model.trim="form.description"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <BjDivider>编码段</BjDivider>

    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :auto-fetch="false"
      search-btn-hide
      is-list
      res="codeRuleDetailDTOList"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" class="main-btn" @click="onAdd">新增</el-button>
        </el-col>
      </template>
      <template v-slot:tableColumn>
        <!-- <el-table-column type="index" label="序号" /> -->
        <template v-for="(col, index) in dropCols">
          <el-table-column
            v-if="col.prop !== 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || false"
            align="center"
            class-name="item"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <el-switch
                v-else-if="col.prop === 'status'"
                v-model="scope.row.status"
                @change="onStatus($event, scope.row)"
              />
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="text" class="edit-btn" @click="onEdit(scope.row)">编辑</el-button>
              <el-button
                :disabled="scope.row.status"
                type="text"
                class="delete-btn"
                @click="onDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" :rule-dist-id="ruleDistId" @reFresh="reFresh" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import pageMiXin from '@/utils/page-mixin.js';
import { baseDataApi } from '@/api';
import DataForm from './DataFormRuleDist.vue';
const getDefaultForm = () => {
  return {
    ruleName: null,
    ruleCode: null,
    description: null,
  };
};

export default {
  name: 'SettingSchedulerRequestViewForm',
  components: {
    DataForm,
  },
  mixins: [viewMiXin, pageMiXin],
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
        ruleName: [{ required: true, message: '请输入', trigger: 'blur' }],
        ruleCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        description: [{ required: false, message: '请输入', trigger: 'blur' }],
      },
      queryApi: baseDataApi.detailCodeRuleDistId,
      // 查询参数
      queryParams: {
        ruleCode: null,
        ruleName: null,
      },

      labelList: [
        { label: '序号', prop: 'orderSeq', isCheck: true, disabled: false },
        { label: '段类型', prop: 'fieldTypeDescription', isCheck: true, disabled: false },
        { label: '段值', prop: 'fieldValue', isCheck: true, disabled: false },
        { label: '日期掩码', prop: 'dateMask', isCheck: true, disabled: false },
        { label: '序列最小位数', prop: 'seqLength', isCheck: true, disabled: false },
        { label: '序列开始值', prop: 'startValue', isCheck: true, disabled: false },
        // { label: '最大值', prop: 'tenantName', isCheck: true, disabled: false },
        // { label: '步长', prop: 'seqLength', isCheck: true, disabled: false },
        // { label: '修改人', prop: 'xx',isCheck: true, disabled: false, width: 135 },
        // { label: '修改日期', prop: 'xx', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {
    isSelected() {
      return !!this.selectedrow.length;
    },
    ruleDistId() {
      try {
        return this.form.codeRuleDistDTOList.content[0].ruleDistId;
      } catch (error) {
        return '';
      }
    },
  },
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
        }
      },
    },
    ruleDistId(v) {
      if (v) {
        this.queryParams.ruleDistId = v;
        this.$refs.BjPage && this.$refs.BjPage.fetchData();
      }
    },
  },

  mounted() {},
  methods: {
    reFreshParent() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
        delete API_DATA.codeRuleDistDTOList;
      }

      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.updateApi(API_DATA);
      });
    },

    onAdd() {
      this.$refs.DataForm.show();
    },
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },
    onDelete(row, index) {
      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi([row]);
        })
        .catch(() => {});
    },

    /**
     *
     *
     */

    // deleteApi
    deleteApi(API_DATA) {
      baseDataApi.deleteCodeRuleDetail(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 编辑
    updateApi(API_DATA) {
      baseDataApi.addCodeRule(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('SettingBaseDataCodeRuleIndex');
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss"></style>
