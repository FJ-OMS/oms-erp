<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    width="800px"
    @handleConfirm="handleConfirm"
  >
    <BjDivider>基本信息</BjDivider>
    <el-col :span="24">
      <el-form-item label="门店名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item> </el-col
    ><el-col :span="24">
      <BjFormItemCode v-model="form.code" :readonly="isEdit" label="门店编码" prop="code" />
    </el-col>
    <el-col :span="24">
      <el-form-item label="门店类型" prop="typeCode">
        <BjSelectLov v-model="form.typeCode" lov-code="SCHL.SHOP_TYPE" />
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
      <el-form-item label="门店地址" prop="fullAddress">
        <el-col :span="11">
          <BjRegionCascader v-model="regionList" class="full-width" is-array clearable />
        </el-col>
        <el-col :span="13">
          <el-input
            v-model.trim="form.address"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="详细地址"
            clearable
          />
        </el-col>
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

    <el-col :span="24">
      <el-form-item prop="selfPickupFlag" label-width="225px">
        <template #label>
          是否为O2O订单自提门店
          <el-tooltip
            effect="dark"
            placement="top-start"
            content="当设自提门店时，则O2O订单可以选择此门店作为自提点"
          >
            <i class="el-icon-question"></i>
          </el-tooltip>
        </template>
        <el-switch v-model="form.selfPickupFlag" />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { channelApi } from '@/api';
import BjRegionCascader from '@/components/BjRegionCascader';

const getDefaultForm = () => {
  return {
    status: true,
    channelWarehouseStatus: true,
    code: null,
    name: null,
    typeCode: null,
    manager: null,
    salesOrganizingId: null,
    invoiceTypeCode: null,

    provinceId: null,
    cityId: null,
    countyId: null, // 县ID ,
    address: null,

    description: null,
    channelWarehouseName: null,
    /** 以下新增时没有 */
    deleteFlag: false,
    id: null,
  };
};

export default {
  components: {
    BjRegionCascader,
  },
  data() {
    return {
      // 表单参数
      isEdit: false,

      form: getDefaultForm(),
      // 表单校验
      // rules: {
      //   brandName: [{ required: true, message: '请输入品牌名称', trigger: 'blur' }],
      // },
      regionList: [],
    };
  },
  computed: {
    // 表单校验
    rules() {
      const validateFullAddress = (rule, value, callback) => {
        this.$nextTick(() => {
          if (!this.form.provinceId || !this.form.cityId || !this.form.countyId) {
            callback(new Error('请选择省/市/区'));
          }
          if (!this.form.address) {
            callback(new Error('请输入详细地址'));
          }
          callback();
        });
      };

      return {
        // code: [{ required: true, message: '请输入', trigger: 'blur' }],
        name: [{ required: true, message: '请输入门店名称', trigger: 'blur' }],
        typeCode: [{ required: true, message: '请选择门店类型', trigger: 'change' }],
        manager: [{ required: true, message: '请输入负责人', trigger: 'blur' }],
        salesOrganizingId: [{ required: true, message: '请选择所属公司', trigger: 'change' }],
        invoiceTypeCode: [{ required: true, message: '请选择发票种类', trigger: 'change' }],
        fullAddress: { required: true, validator: validateFullAddress, trigger: 'change' },
        // description: [{ required: true, message: '请输入描述', trigger: 'change' }],
        channelWarehouseName: [{ required: true, message: '请输入渠道仓名称', trigger: 'blur' }],
      };
    },
  },
  watch: {
    regionList(value) {
      this.form.provinceId = (value || [])[0];
      this.form.cityId = (value || [])[1];
      this.form.countyId = (value || [])[2];
    },
  },
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑门店' : '新建门店');
      this.form = Object.assign(getDefaultForm(), info || {});
      this.regionList = [];
      if (this.isEdit) {
        this.regionList = [Number(info.provinceId), Number(info.cityId), Number(info.countyId)];
      }
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
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

    // 保存API
    addApi(API_DATA) {
      channelApi.addPhysicalShop(API_DATA).then(res => {
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
      channelApi.addPhysicalShop(API_DATA).then(res => {
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
