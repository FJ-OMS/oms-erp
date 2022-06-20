<template>
  <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="dialogVisible">
    <el-form ref="form" :inline="false" :model="form" :rules="rules">
      <el-form-item label="" prop="value">
        <el-input
          v-model="form.value"
          type="textarea"
          :placeholder="'请输入' + title"
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
import { orderApi } from '@/api';
export default {
  name: 'BusinessOrderInternalViewFormSuspendReason',
  components: {},
  props: {
    title: {
      type: String,
      default: '挂起原因',
    },
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
      // 弹窗显示
      dialogVisible: false,

      form: {
        value: null,
      },
    };
  },
  computed: {
    // 表单校验
    rules() {
      return {
        value: [{ required: false, message: '请输入' + this.title, trigger: 'blur' }],
      };
    },
  },
  methods: {
    onEdit(flag) {
      if (!flag) {
        // 解挂
        this.$confirm(`您正在 解除 此订单的挂起状态，是否继续？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.orderUnHandUpApi({ id: this.id });
          })
          .catch(() => {});
        return;
      }
      this.form = Object.assign({}, this.info);
      this.dialogVisible = true;
    },
    confirmEditRemarks() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.orderHandUpApi({
            id: this.id,
            suspendReason: this.form.value,
          });
        } else {
          return false;
        }
      });
    },
    // 订单级-挂起
    orderHandUpApi(POST_DATA) {
      orderApi.suspendOrderByOrderId(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success(res.message);
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 订单级-解挂
    orderUnHandUpApi(POST_DATA) {
      orderApi.unsuspendOrderByOrderId(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success(res.message);
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    reFresh() {
      this.dialogVisible = false;
      this.$emit('reFresh');
    },
  },
};
</script>
