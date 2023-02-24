<template>
  <el-dialog
    v-if="visible"
    title="修改退货仓库"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :visible.sync="visible"
    width="680px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="130px" style="width: 600px">
      <el-row :gutter="10">
        <el-col>
          <el-form-item label="退货仓库" prop="receivingWarehouseId">
            <BjSelectWarehouse
              :value="form.receivingWarehouseId"
              :type-code="['logic']"
              @change="handleChangeReceivingWarehouse"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="hide">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: 'ReceivingWarehouseDialog',
  data() {
    return {
      visible: false,
      form: {
        id: null,
        receivingWarehouseCode: null,
        receivingWarehouseId: null,
        receivingWarehouseName: null,
      },
      rules: {
        receivingWarehouseId: [{ required: true, message: '请选择', trigger: 'change' }],
      },
    };
  },
  methods: {
    handleChangeReceivingWarehouse(v, row, index) {
      this.form.receivingWarehouseCode = row.code;
      this.form.receivingWarehouseId = row.id;
      this.form.receivingWarehouseName = row.name;

      console.log(...arguments);
    },
    show(receivingWarehouseId) {
      this.visible = true;
      this.form.receivingWarehouseId = receivingWarehouseId;
    },
    hide() {
      this.visible = false;
    },
    confirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('confirm', this.form);
          this.hide();
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 10px;
}
</style>
