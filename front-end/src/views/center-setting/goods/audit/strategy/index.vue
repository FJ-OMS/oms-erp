<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules">
      <BjDivider
        >SKU信息审核

        <template #right right-fixed>
          <el-button
            v-permission:SettingGoodsAuditStrategyIndex_Save
            type="primary"
            :loading="loading"
            @click="confirm"
          >
            {{ loading ? '保存中' : '保存' }}
          </el-button>
        </template>
      </BjDivider>
      <el-form-item label="创建是否开启审核">
        <el-switch
          v-model="form.createdAuditStatus"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>
      <el-form-item label="修改是否开启审核">
        <el-switch
          v-model="form.updateAuditStatus"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>
      <el-form-item v-show="form.updateAuditStatus" label="触发审核条件" style="margin-left: 15px">
        <el-checkbox
          v-model="form.updatePriceStatus"
          label="SKU价格变动"
          :true-label="1"
          :false-label="0"
        ></el-checkbox>
        <el-checkbox
          v-model="form.updatePropertyStatus"
          label="SKU属性变动"
          :true-label="1"
          :false-label="0"
        ></el-checkbox>
      </el-form-item>
      <BjDivider>上下架审核</BjDivider>
      <!-- TODO:未接入数据 -->
      <el-form-item label="是否开启"><el-switch v-model="form.todo"></el-switch></el-form-item>
    </el-form>
  </div>
</template>

<script>
import { goodsApi } from '@/api';
export default {
  name: 'SettingGoodsAuditStrategyIndex',
  data() {
    return {
      form: {},
      rules: {},
      loading: false,
    };
  },
  // watch: {
  //   'form.updateAuditStatus': {
  //     handler(v) {
  //       this.form.updatePriceStatus = 0;
  //       this.form.updatePropertyStatus = 0;
  //     },
  //   },
  // },
  created() {
    this.getData();
  },
  methods: {
    /** 获取数据 */
    getData() {
      goodsApi
        .getSkuAudit()
        .then(res => {
          this.form = res;
        })
        .catch(err => {
          console.log(err);
        });
    },
    /** 保存 */
    confirm() {
      if (
        this.form.updateAuditStatus &&
        !this.form.updatePriceStatus &&
        !this.form.updatePropertyStatus
      ) {
        this.$alert('请选择一项触发审核条件', '提示', {
          confirmButtonText: '确定',
          type: 'error',
          callback: action => {},
        });
      } else {
        this.$confirm('是否保存当前操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        })
          .then(() => {
            this.loading = true;
            goodsApi
              .updateSkuAudit(this.form)
              .then(res => {
                this.loading = false;
                this.msgSuccess('操作成功');
                setTimeout(() => {
                  this.form = res;
                }, 500);
              })
              .catch(err => {
                this.loading = false;
                console.log(err);
              });
          })
          .catch(() => {});
      }
    },
  },
};
</script>

<style></style>
