<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="`修改${titles[formItemKey]}`"
    :visible.sync="dialogVisible"
  >
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item v-if="formItemKey == 'buyerRemarks'" label="" prop="buyerRemarks">
        <el-input
          v-model="form.buyerRemarks"
          type="textarea"
          placeholder="请输入 买家备注"
          :maxlength="MAX_LENGTH.REMARK"
          show-word-limit
          clearable
          rows="5"
          required
        ></el-input>
      </el-form-item>
      <el-form-item v-if="formItemKey == 'orderRemarks'" label="" prop="orderRemarks">
        <el-input
          v-model="form.orderRemarks"
          type="textarea"
          placeholder="请输入 商家备注"
          :maxlength="MAX_LENGTH.REMARK"
          show-word-limit
          clearable
          rows="5"
          required
        ></el-input>
      </el-form-item>
      <el-form-item v-if="formItemKey == 'serviceRemarks'" label="" prop="serviceRemarks">
        <el-input
          v-model="form.serviceRemarks"
          type="textarea"
          placeholder="请输入 客服备注"
          :maxlength="MAX_LENGTH.REMARK"
          show-word-limit
          clearable
          rows="5"
          required
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmEditRemarks">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: 'BusinessOrderInternalViewFormEditRemarks',
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
      titles: {
        buyerRemarks: '买家备注',
        orderRemarks: '商家备注',
        serviceRemarks: '客服备注',
      },

      formItemKey: null,
      // 弹窗显示
      dialogVisible: false,

      form: {
        orderId: null,
        buyerRemarks: null,
        orderRemarks: null,
        serviceRemarks: null,
      },
      rules: {
        // buyerRemarks: [{ required: true, message: '请输入买家备注', trigger: 'blur' }],
        // orderRemarks: [{ required: true, message: '请输入商家备注', trigger: 'blur' }],
        // serviceRemarks: [{ required: true, message: '请输入客服备注', trigger: 'blur' }],
      },
    };
  },
  methods: {
    // 修改备注
    onEdit(key) {
      this.formItemKey = key;
      this.dialogVisible = true;
      this.form = {
        orderId: this.id,

        buyerRemarks: this.info.buyerRemarks,
        orderRemarks: this.info.orderRemarks,
        serviceRemarks: this.info.serviceRemarks,
      };
    },
    confirmEditRemarks() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('editBaseInfoApi', this.form);
        } else {
          return false;
        }
      });
    },
  },
};
</script>
