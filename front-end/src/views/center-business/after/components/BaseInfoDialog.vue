<template>
  <el-dialog
    v-if="visible"
    :title="title"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :visible.sync="visible"
    width="500px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="width: 400px">
      <el-form-item v-if="!onlyRemark" :label="`${cptText}类型`" prop="refundType">
        <BjSelectLov v-model="form.refundType" :option-list="INTERNAL_AFTER_RETURN_TYPE_LIST" />
      </el-form-item>
      <el-form-item v-if="!onlyRemark" :label="`${cptText}原因`" prop="reason">
        <BjSelectLov
          v-model="form.reason"
          :option-list="BJ_AFTER_REFUND_REASONS"
          item-useable="status"
        />
      </el-form-item>
      <el-form-item v-if="!onlyRemark" :label="`${cptText}阶段`" prop="stage">
        <BjSelectLov v-model="form.stage" :option-list="INTERNAL_AFTER_STAGE_STATUS" />
      </el-form-item>
      <el-form-item label="客服备注">
        <BjTextarea v-model="form.serviceRemarks" placeholder="客服备注" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="hide">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { INTERNAL_AFTER_RETURN_TYPE_LIST, INTERNAL_AFTER_STAGE_STATUS } from '@/utils/constants';
export default {
  props: {
    isRefundOnly: {
      type: Boolean,
      default: false,
    },
    isRefundGoods: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      INTERNAL_AFTER_RETURN_TYPE_LIST,
      INTERNAL_AFTER_STAGE_STATUS,
      visible: false,
      title: '',
      onlyRemark: false,
      form: {
        id: null,
        refundType: null,
        reason: null,
        stage: null,
        serviceRemarks: null,
      },
      rules: {
        refundType: [{ required: true, message: '请选择退货类型', trigger: 'change' }],
        reason: [{ required: true, message: '请选择退货原因', trigger: 'change' }],
        stage: [{ required: true, message: '请选择退货阶段', trigger: 'change' }],
      },
    };
  },
  computed: {
    cptText() {
      if (this.isRefundOnly) {
        return '仅退款';
      }
      if (this.isRefundGoods) {
        return '退货退款';
      }
      return '';
    },
    BJ_AFTER_REFUND_REASONS() {
      if (this.isRefundOnly) {
        return this.$store.state.values.BJ_AFTER_REFUND_ONLY_REASONS || [];
      }
      if (this.isRefundGoods) {
        return this.$store.state.values.BJ_AFTER_REFUND_GOODS_REASONS || [];
      }
      return [];
    },
  },
  mounted() {
    if (this.isRefundOnly) {
      this.$store.dispatch('values/getAfterRefundOnlyReasons');
    }
    if (this.isRefundGoods) {
      this.$store.dispatch('values/getAfterRefundGoodsReasons');
    }
  },
  methods: {
    show(title = '修改基础信息', info) {
      this.visible = true;
      this.title = title;
      this.form.id = info.id;
      if (title.includes('基础')) {
        this.form.refundType = Number(info.refundType);
        this.form.reason = info.reason;
        this.form.stage = Number(info.stage);
        this.onlyRemark = false;
      } else {
        // 仅客服
        this.form.refundType = null;
        this.form.reason = null;
        this.form.stage = null;
        this.onlyRemark = true;
      }
      this.form.serviceRemarks = info.serviceRemarks;
    },
    hide() {
      this.visible = false;
    },
    confirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('confirm', this.$lodash.clone(this.form));
          this.hide();
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style></style>
