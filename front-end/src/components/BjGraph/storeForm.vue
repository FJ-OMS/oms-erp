<!--
 * @Author: your name
 * @Date: 2021-08-06 10:29:55
 * @LastEditTime: 2021-12-03 10:36:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\stock-structure\components\storeFoem.vue
-->
<template>
  <el-drawer
    :visible.sync="drawer"
    direction="rtl"
    width="400px"
    :modal-append-to-body="false"
    :before-close="handleClose"
  >
    <BjDivider v-if="form">{{ form.id ? '编辑仓库' : '新建仓库' }}</BjDivider>
    <el-form v-if="drawer" ref="form" :model="form" :rules="rules" inline label-width="100px">
      <el-form-item label="仓库名称：" prop="name">
        <el-input v-model.trim="form.name" :maxlength="20" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="仓库编码：" prop="id">
        <el-input v-model.trim="form.id" placeholder="自动生成" clearable disabled />
      </el-form-item>
      <el-form-item label="仓库类型：" prop="typeCode">
        {{ STOCK_WAREHOUSE_TYPE_SON_2_LIST.bjGet(form.typeCode) }}
      </el-form-item>
    </el-form>
    <div class="foot">
      <el-button type="danger" @click="onCancel">取 消</el-button>
      <el-button type="primary" @click="onComfirm">确 定</el-button>
    </div>
  </el-drawer>
</template>
<script>
import { STOCK_WAREHOUSE_TYPE_SON_2_LIST } from '@/utils/constants';
import { stockApi } from '@/api';
const getDefaultForm = () => {
  return {
    id: null,
    nodeId: null,
    typeCode: null,
    name: null,
    x: null,
    y: null,
    index: null,
  };
};
export default {
  props: {},
  data() {
    return {
      STOCK_WAREHOUSE_TYPE_SON_2_LIST,
      drawer: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入仓库名称', trigger: 'blur' }],
      },
    };
  },
  methods: {
    getDetail(id, index) {
      stockApi.detailWarehouses({ id }).then(res => {
        this.drawer = true;

        this.form = Object.assign(getDefaultForm(), res || {});
        this.form.index = index;
      });
    },
    show(data) {
      if (data.id) {
        this.getDetail(data.id, data.index);
        return;
      }
      this.drawer = true;
      this.form = Object.assign(getDefaultForm(), data || {});
    },
    hide() {
      this.drawer = false;
    },
    /** 关闭弹窗 */
    handleClose(done) {
      if (!this.form.id) {
        this.$confirm('还有未保存的工作，哦确定关闭吗？新增的数据会消失哦！')
          .then(_ => {
            done();
            this.$emit('close', true, this.form);
          })
          .catch(_ => {
            this.$emit('close', false, this.form);
          });
      } else {
        done();
        this.$emit('close', false, this.form);
      }
    },
    /** 取消 */
    onCancel() {
      this.hide();
      this.$emit('close', true, this.form);
    },
    /** 确定 */
    onComfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          console.log(this.form);
          this.$emit('handleConfirm', this.form);
        }
      });
    },
  },
};
</script>
<style lang="scss">
.flex-item {
  width: 200px;
  justify-content: space-between;
  align-items: flex-start;
}
.el-drawer__body {
  padding: 16px;
  display: flex;
  flex-direction: column;
  height: 100%;
}
.el-form {
  flex: 1;
}
.foot {
  text-align: center;
  .el-button {
    width: 120px;
  }
}
</style>
