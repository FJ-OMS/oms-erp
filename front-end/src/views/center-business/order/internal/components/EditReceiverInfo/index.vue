<template>
  <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="dialogVisible">
    <el-form ref="form" :inline="false" label-width="120px" :model="form" :rules="rules">
      <el-row>
        <el-col :span="24">
          <el-form-item label="收货人姓名" prop="receiverName">
            <el-input
              v-model="form.receiverName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <BjFormItemMobile
            v-model="form.mobile"
            label="收货人手机号"
            prop="mobile"
            :required="true"
          />
        </el-col>
        <el-col :span="24">
          <el-form-item label="收货地址" prop="fullAddress">
            <el-col :span="11">
              <BjRegionCascader
                v-model="regionList"
                class="full-width"
                is-array
                clearable
                @change="onChangeRegionCascader"
              />
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
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmEditRemarks">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import BjRegionCascader from '@/components/BjRegionCascader';
export default {
  name: 'BusinessOrderInternalViewFormEditReceiverInfo',
  components: { BjRegionCascader },
  props: {
    id: {
      type: String,
      default: null,
    },
    info: {
      type: Object,
      default: function() {
        return {};
      },
    },
  },
  data() {
    return {
      title: '修改收货信息',

      regionList: [],

      // 弹窗显示
      dialogVisible: false,

      form: {
        receiverName: null,
        mobile: null,
        phone: null,

        province: null,
        provinceCode: null,

        city: null,
        cityCode: null,

        district: null,
        districtCode: null,

        address: null,

        town: null,
      },
    };
  },
  computed: {
    // 表单校验
    rules() {
      const validateFullAddress = (rule, value, callback) => {
        this.$nextTick(() => {
          if (!this.form.provinceCode || !this.form.cityCode || !this.form.districtCode) {
            callback(new Error('请选择省/市/区'));
          }
          if (!this.form.address) {
            callback(new Error('请输入详细地址'));
          }
          callback();
        });
      };
      return {
        receiverName: [{ required: true, message: '请输入收货人姓名', trigger: 'blur' }],
        mobile: [{ required: true, message: '请输入收货人手机号', trigger: 'blur' }],

        fullAddress: { required: true, validator: validateFullAddress, trigger: 'change' },
      };
    },
  },
  methods: {
    onChangeRegionCascader(v) {
      console.log('onChangeRegionCascader', v);
      this.form.province = v[0].label;
      this.form.city = v[1].label;
      this.form.district = v[2].label;

      this.form.provinceCode = v[0].value;
      this.form.cityCode = v[1].value;
      this.form.districtCode = v[2].value;
    },

    // 修改
    onEdit() {
      this.form = Object.assign({}, this.info);
      if (!this.form.mobile) this.form.mobile = this.form.phone;

      this.regionList = [
        Number(this.form.provinceCode),
        Number(this.form.cityCode),
        Number(this.form.districtCode),
      ];

      this.dialogVisible = true;
    },
    confirmEditRemarks() {
      this.form.phone = this.form.mobile;
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('editBaseInfoApi', { orderId: this.id, receiverInfoDTO: this.form });
        } else {
          return false;
        }
      });
    },
  },
};
</script>
