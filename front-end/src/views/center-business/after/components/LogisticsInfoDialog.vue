<template>
  <el-dialog
    v-if="visible"
    title="修改退货物流"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :visible.sync="visible"
    width="680px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="130px" style="width: 600px">
      <el-row v-for="(item, index) in form.list" :key="index" :gutter="10">
        <el-form-item :label="index == 0 ? '退货物流信息' : null">
          <el-col :span="10">
            <el-form-item
              :prop="'list.' + index + '.returnLogisticsCompanyId'"
              :rules="{
                required: true,
                message: '请选择物流公司',
                trigger: 'change',
              }"
            >
              <BjSelectLogisticCompany
                :value="item.returnLogisticsCompanyId"
                placeholder="请选择物流公司"
                clearable
                @change="handleChangeLogisticCompany(...arguments, index)"
              />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item
              :prop="'list.' + index + '.returnLogisticsNo'"
              :rules="{
                required: true,
                message: '请输入快递单号',
                trigger: 'blur',
              }"
            >
              <BjInputCode v-model="item.returnLogisticsNo" placeholder="请输入快递单号" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-button v-if="!index" type="primary" icon="el-icon-plus" @click="onAdd(index)" />
            <el-button
              v-if="index"
              type="danger"
              icon="el-icon-minus"
              @click="onDelete(index)"
            /> </el-col
        ></el-form-item>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="hide">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { afterApi } from '@/api';
export default {
  data() {
    return {
      visible: false,
      form: {
        list: [
          {
            returnLogisticsCompanyId: null,
            returnLogisticsCompanyName: null,
            returnLogisticsNo: null,
          },
        ],
      },
      rules: {},
    };
  },
  methods: {
    handleChangeLogisticCompany(v, row, index) {
      console.log(...arguments);
      const TEMP_NO = this.form.list[index].returnLogisticsNo;
      this.$set(this.form.list, index, {
        returnLogisticsCompanyId: row.id,
        returnLogisticsCompanyName: row.name,
        returnLogisticsNo: TEMP_NO,
      });
    },
    show(id) {
      this.visible = true;
      // TODO:  赋值有问题
      afterApi.getAfterSaleLogistics({ orderId: id }).then(res => {
        if (Array.isArray(res) && res.length) {
          this.form.list = res.map(e => {
            return {
              returnLogisticsCompanyId: e.logisticsCompanyId,
              returnLogisticsCompanyName: e.logisticsCompanyName,
              returnLogisticsNo: e.logisticsNo,
            };
          });
        } else {
          this.form.list = [
            {
              returnLogisticsCompanyId: null,
              returnLogisticsCompanyName: null,
              returnLogisticsNo: null,
            },
          ];
        }
      });
    },
    hide() {
      this.visible = false;
    },
    onAdd() {
      this.form.list.push({
        returnLogisticsCompanyId: '',
        returnLogisticsCompanyName: '',
        returnLogisticsNo: '',
      });
    },
    onDelete(index) {
      this.$confirm('是否删除该数据？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.form.list.splice(index, 1);
        })
        .catch(() => {});
    },
    confirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('confirm', { logisticsList: this.form.list });
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
