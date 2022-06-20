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
      <el-form-item label="仓库名称" prop="name">
        <el-input v-model="form.name" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <BjFormItemCode v-model="form.code" :readonly="isEdit" label="仓库编码" prop="code" />
    </el-col>
    <el-col :span="24">
      <el-form-item label="联系人姓名" prop="contactName">
        <el-input v-model="form.contactName" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <BjFormItemMobile
        v-model.trim="form.contactPhone"
        prop="contactPhone"
        label="联系人手机"
        clearable
      />
    </el-col>
    <el-col :span="24">
      <el-form-item label="仓库地址" prop="fullAddress">
        <el-col :span="11">
          <BjRegionCascader v-model="regionList" class="full-width" is-array clearable />
        </el-col>
        <el-col :span="13">
          <el-input
            v-model="form.detailAddress"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="详细地址"
          />
        </el-col>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
      </el-form-item>
    </el-col>

    <BjDivider>{{ this.isEdit ? '编辑' : '创建' }}逻辑仓</BjDivider>
    <el-col :span="24">
      <el-form-item label="逻辑仓名称" prop="logicalWarehouseName">
        <el-input
          v-model.trim="form.logicalWarehouseName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <!--
    <el-col :span="24">
      <el-form-item label="逻辑仓状态" prop="logicalFlag">
        <el-switch v-model="form.logicalFlag" />
      </el-form-item>
    </el-col>
    <el-col v-show="form.logicalFlag" :span="24">
      <el-form-item
        label="逻辑仓名称"
        prop="logicalWarehouseName"
        :rules="{
          required: !!form.logicalFlag,
          message: '请输入逻辑仓名称',
          trigger: 'blur',
        }"
      >
        <el-input
          v-model.trim="form.logicalWarehouseName"
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
import BjRegionCascader from '@/components/BjRegionCascader';
const getDefaultForm = () => {
  return {
    addressCode: null,
    code: null,
    cityCode: null,
    cityName: null,
    contactName: null,
    contactPhone: null,
    detailAddress: null,
    districtCode: null,
    districtName: null,
    id: null,
    latitude: null,
    longitude: null,
    provinceCode: null,
    provinceName: null,
    tenantId: 'default',
    name: null,
    enableFlag: true,
    logicalFlag: true,
    logicalWarehouseName: null,
  };
};

export default {
  components: {
    BjRegionCascader,
  },
  data() {
    return {
      // 表单参数
      regionList: [],
      isEdit: false,
      form: getDefaultForm(),
    };
  },
  computed: {
    rules() {
      const validateFullAddress = (rule, value, callback) => {
        this.$nextTick(() => {
          if (!this.form.provinceCode || !this.form.cityCode || !this.form.districtCode) {
            callback(new Error('请选择省/市/区'));
          }
          if (!this.form.detailAddress) {
            callback(new Error('请输入详细地址'));
          }
          callback();
        });
      };

      return {
        code: [{ required: true, message: '请输入仓库编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入仓库名称', trigger: 'blur' }],
        contactName: [{ required: true, message: '请输入联系人姓名', trigger: 'blur' }],
        logicalWarehouseName: [{ required: true, message: '请输入逻辑仓名称', trigger: 'blur' }],
        fullAddress: { required: true, validator: validateFullAddress, trigger: 'change' },
        detailAddress: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
      };
    },
  },
  watch: {
    regionList(value) {
      this.form.provinceCode = (value || [])[0];
      this.form.cityCode = (value || [])[1];
      this.form.districtCode = (value || [])[2];
    },
  },
  mounted() {},
  methods: {
    show(info) {
      console.log(info);
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.regionList = [];
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑仓库' : '新建仓库');
      this.form = Object.assign(getDefaultForm(), info || {});
      if (this.isEdit) {
        this.regionList = [
          Number(info.provinceCode),
          Number(info.cityCode),
          Number(info.districtCode),
        ];
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

    // 修改API
    updateApi(API_DATA) {
      channelApi.updateWarehouse(API_DATA).then(res => {
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
      channelApi.addWarehouse(API_DATA).then(res => {
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
