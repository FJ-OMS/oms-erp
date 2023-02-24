<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="135px"
    width="61.8%"
    @handleConfirm="handleConfirm"
  >
    <BjDivider>基本信息 </BjDivider>

    <el-col :span="10">
      <el-form-item label="分类名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="中文/英文/数字"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="10">
      <BjFormItemCode
        v-model="form.code"
        :readonly="isEdit"
        label="分类编码"
        placeholder="0-20个英文字母、数字"
        prop="code"
        :validator="validatorGoodsClassifyCode"
      />
    </el-col>
    <el-col :span="10">
      <!-- 叶子结点不展示 -->
      <el-form-item label="父级分类" prop="parentId">
        <BjGoodsTypeTreeSelect
          v-if="isBjGoodsTypeTreeSelectVisible"
          v-model="form.parentId"
          :query="{ statusFlag: true, subFlag: false }"
          has-root
          :disabled="isEdit"
        />
      </el-form-item>
    </el-col>
    <!-- <el-col :span="10">
      <el-form-item label="状态" prop="statusFlag">
        <el-switch v-model="form.statusFlag" />
      </el-form-item>
    </el-col> -->
    <el-col :span="10">
      <el-form-item prop="subFlag">
        <template #label>
          是否叶子结点
          <el-tooltip
            effect="dark"
            placement="top-start"
            content="当设为叶子结点时，无法添加子分类，商品挂载在叶子结点"
          >
            <i class="el-icon-question"></i>
          </el-tooltip>
        </template>
        <el-switch v-model="form.subFlag" />
      </el-form-item>
    </el-col>
    <el-col :span="20">
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="form.remark"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <GoodsAttributeList ref="GoodsAttribute" :parent-id="form.parentId" />
  </BjDrawerForm>
</template>
<script>
import { goodsApi } from '@/api';
import GoodsAttributeList from '@/views/center-business/goods/components/GoodsAttributeList';
import { patternGoodsClassifyName, validatorGoodsClassifyCode } from '@/utils/validate';
const getDefaultForm = () => {
  return {
    name: null,
    code: null,
    id: null,
    parentId: null,
    statusFlag: 1,
    subFlag: false,
    remark: null,
  };
};

export default {
  components: {
    GoodsAttributeList,
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, validator: patternGoodsClassifyName, trigger: 'blur' }],
        parentId: [{ required: true, message: '请选择所属分类', trigger: ['blur', 'change'] }],
      },

      isBjGoodsTypeTreeSelectVisible: false,
    };
  },
  watch: {},
  mounted() {},
  methods: {
    validatorGoodsClassifyCode() {
      return validatorGoodsClassifyCode(...arguments);
    },
    show(info) {
      this.isBjGoodsTypeTreeSelectVisible = true;
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑分类' : '新建分类');
      this.form = Object.assign(getDefaultForm(), info || {});
      if (this.isEdit) {
        this.$nextTick(() => {
          this.$refs.GoodsAttribute.setAttributeList(info.attributeList);
        });
        if (info.level === 1) {
          this.form.parentId = 'root';
        }
      }
    },
    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      API_DATA['attributeList'] = this.$refs.GoodsAttribute.attributeList;
      if (API_DATA.parentId === 'root') API_DATA.parentId = null;
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      if (this.isEdit) {
        this.updateApi(API_DATA);
      } else {
        this.addApi(API_DATA);
      }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    addApi(API_DATA) {
      goodsApi.addCategory(API_DATA).then(res => {
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
      goodsApi.updateCategory(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    onDelSelectedStore() {
      this.selectedRowStore.forEach(row => {
        this.attributeList.splice(
          this.attributeList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID),
          1,
        );
      });
      // 清除选中状态
      this.$refs.storeTable.clearSelection();
    },
  },
};
</script>
