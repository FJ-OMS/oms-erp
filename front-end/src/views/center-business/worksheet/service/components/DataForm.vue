<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    width="800px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="24">
      <el-form-item label="安装服务编码" prop="installServiceCode">
        {{ form.installServiceCode || '自动生成' }}
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="安装服务名称" prop="name">
        <el-input
          v-model="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isView"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="安装类别" prop="type">
        <el-input
          v-model="form.type"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isView"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>

    <el-col :span="24">
      <el-form-item label="配备人员" prop="peopleAmount">
        <el-input
          v-model="form.peopleAmount"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isView"
          placeholder="请输入"
          clearable
        >
          <template slot="append">人</template>
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="服务费用" prop="money">
        <BjInputMoney v-model="form.money" :disabled="isView" placeholder="请输入" clearable>
          <template slot="append">元</template>
        </BjInputMoney>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="服务地区" prop="fullAddress">
        <div v-for="(e, i) in regionList" :key="i" class="list-box">
          <BjRegionCascader
            v-model="regionList[i]"
            :disabled="isView"
            class="full-width"
            is-array
            clearable
            :length="2"
          />

          <i v-if="i > 0" class="el-icon-remove-outline" @click="deleteOne(e, i)" />
          <i v-if="i == 0" class="el-icon-circle-plus-outline" @click="onAddOne" />
        </div>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="form.remark"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isView"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { worksheetApi } from '@/api';
import BjRegionCascader from '@/components/BjRegionCascader';
const getDefaultForm = () => {
  return {
    id: null,
    money: null,
    name: null,
    objectVersionNumber: null,
    peopleAmount: null,
    remark: null,
    installServiceCode: null,
    serviceZone: [
      // {
      //   cityId: null,
      //   cityName: null,
      //   countryId: null,
      //   countryName: null,
      //   provinceId: null,
      //   provinceName: null,
      // },
    ],
    type: null,
  };
};

export default {
  components: {
    BjRegionCascader,
  },
  data() {
    return {
      // 表单参数
      regionList: [[]],
      isEdit: false,
      isView: false,
      form: getDefaultForm(),
    };
  },
  computed: {
    rules() {
      // const validateFullAddress = (rule, value, callback) => {
      //   this.$nextTick(() => {
      //     if (!this.form.provinceId || !this.form.cityId || !this.form.countyId) {
      //       callback(new Error('请选择省/市/区'));
      //     }

      //     callback();
      //   });
      // };
      return {
        // installServiceCode: [{ required: true, message: '请输入仓库编码', trigger: 'blur' }],
        wareName: [{ required: true, message: '请输入仓库名称', trigger: 'blur' }],
        contactName: [{ required: true, message: '请输入联系人姓名', trigger: 'blur' }],

        // fullAddress: { required: true, validator: validateFullAddress, trigger: 'change' },
        // address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
      };
    },
  },
  watch: {
    'form.peopleAmount'(v) {
      this.form.peopleAmount = Number(String(v).replace(/[^0-9]/g, ''));
    },
  },
  mounted() {},
  methods: {
    onAddOne() {
      if (this.isView) return;
      this.regionList.push([]);
    },
    deleteOne(d, i) {
      if (this.isView) return;
      this.regionList.splice(i, 1);
    },

    show(info, isView) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑服务' : '新建服务');
      this.isView = !!isView;
      this.regionList = [[]];
      this.form = Object.assign(getDefaultForm(), info || {});
      console.log(info);
      if (this.isEdit) {
        this.regionList = (info.serviceZone || []).map(e => [
          Number(e.provinceId),
          Number(e.cityId),
        ]);
      }
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        // API_DATA['_innerMap'] = {};
      }

      API_DATA['serviceZone'] = this.regionList.map(addressList => {
        return {
          cityId: addressList[1],
          // cityName: null,
          // countryId: null,
          // countryName: null,
          provinceId: addressList[0],
          // provinceName: null,
        };
      });

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
      worksheetApi.updateInstallServices(API_DATA).then(res => {
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
      worksheetApi.addInstallServices(API_DATA).then(res => {
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
.el-tag {
  margin: 6px;
}

.list-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  i {
    font-size: 20px;
    padding: 8px;
    cursor: pointer;
  }
}
</style>
