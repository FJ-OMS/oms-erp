<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <BjDivider>基本信息</BjDivider>
    <el-col :span="24">
      <el-form-item label="网店名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <BjFormItemCode v-model="form.code" :readonly="isEdit" label="网店编码" prop="code" />
    </el-col>
    <el-col :span="24">
      <el-form-item label="渠道类型" prop="channelTypeCode">
        <BjSelectLov v-model="form.channelTypeCode" lov-code="SCHL.CHANNEL_TYPE" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="网店类型" prop="onlineStoreTypeCode">
        <BjSelectLov v-model="form.onlineStoreTypeCode" lov-code="SCHL.SHOP_TYPE" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="负责人" prop="manager">
        <el-input
          v-model.trim="form.manager"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <!-- <el-col :span="24">
      <el-form-item label="网店地址" prop="url">
        <el-input
          v-model.trim="form.url"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col> -->
    <el-col :span="24">
      <el-form-item label="所属公司" prop="salesOrganizingId">
        <BjCompanyTreeSelect v-model.trim="form.salesOrganizingId" clearable />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="发票种类" prop="invoiceTypeCode">
        <BjSelectLov v-model="form.invoiceTypeCode" lov-code="SCHL.INVOICE_TYPE" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="关联门店" prop="relationPhysicalShopId">
        <!-- 已启用且是O2O订单自提的门店列表 -->
        <BjSelectStorePhysical
          v-model="form.relationPhysicalShopId"
          :default-params="{ status: true, selfPickupFlag: true }"
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="描述" prop="description">
        <el-input
          v-model.trim="form.description"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <BjDivider>{{ this.isEdit ? '编辑' : '创建' }}渠道仓</BjDivider>
    <el-col :span="24">
      <el-form-item label="渠道仓名称" prop="channelWarehouseName">
        <el-input
          v-model.trim="form.channelWarehouseName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <!-- <el-col :span="24">
      <el-form-item label="渠道仓状态" prop="channelWarehouseStatus">
        <el-switch v-model="form.channelWarehouseStatus" />
      </el-form-item>
    </el-col>
    <el-col v-show="form.channelWarehouseStatus" :span="24">
      <el-form-item
        label="渠道仓名称"
        prop="channelWarehouseName"
        :rules="{
          required: !!form.channelWarehouseStatus,
          message: '请输入',
          trigger: 'blur',
        }"
      >
        <el-input
          v-model.trim="form.channelWarehouseName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col> -->
  </BjDrawerForm>
</template>
<script>
import { channelApi } from '@/api';

const getDefaultForm = () => {
  return {
    status: true,
    channelWarehouseStatus: true,
    channelTypeCode: null,
    code: null,
    description: null,
    id: null,
    invoiceTypeCode: null,
    manager: null,
    name: null,
    objectVersionNumber: 0,
    onlineStoreTypeCode: null,
    salesOrganizingId: null,
    relationPhysicalShopId: null,
    url: '',
    channelWarehouseName: null,
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
        name: [{ required: true, message: '请输入网店名称', trigger: 'blur' }],
        channelTypeCode: [{ required: true, message: '请选择渠道类型', trigger: 'change' }],
        onlineStoreTypeCode: [{ required: true, message: '请选择网店类型', trigger: 'change' }],
        manager: [{ required: true, message: '请输入负责人', trigger: 'blur' }],
        salesOrganizingId: [{ required: true, message: '请选择所属公司', trigger: 'change' }],
        invoiceTypeCode: [{ required: true, message: '请选择发票种类', trigger: 'change' }],
        url: [{ required: true, message: '请输入网店地址', trigger: 'change' }],
        channelWarehouseName: [{ required: true, message: '请输入渠道仓名称', trigger: 'blur' }],
        // channelWarehouseStatus: [{ required: true, message: '请选择渠道仓', trigger: 'blur' }],
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
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑网店' : '新建网店');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }

      this.saveApi(API_DATA);
    },
    reFresh() {
      this.$emit('reFresh');
    },

    saveApi(API_DATA) {
      channelApi.saveOnlineShop(API_DATA).then(res => {
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
