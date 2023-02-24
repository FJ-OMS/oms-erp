<!--
 * @Author: your name
 * @Date: 2021-07-26 17:28:57
 * @LastEditTime: 2022-05-18 13:56:35
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\inquiry\components\filedList.vue
-->
<template>
  <div class="field">
    <el-button type="primary" class="mb20" @click="showDialog">新 增</el-button>
    <el-table border :data="data">
      <el-table-column label="字段名称" prop="indexCode" align="center" />
      <el-table-column label="是否显示" prop="visibleFlag" align="center" />
      <el-table-column label="是否排序" prop="sortFlag" align="center" />
      <el-table-column label="排序方向" prop="sortDirect" align="center" />
      <el-table-column label="排序优先级" prop="weight" align="center" />
      <el-table-column label="操作" align="center" />
    </el-table>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="编辑字段信息"
      :visible.sync="dialogVisible"
      width="400px"
    >
      <el-form ref="form" :model="form" :rules="rules" inline label-width="120px">
        <el-form-item label="是否显示：" prop="visibleFlag">
          <BjSelectLov v-model="form.visibleFlag" constant-key="STATUS_YES_NUM" />
        </el-form-item>
        <el-form-item label="是否排序：" prop="sortFlag">
          <BjSelectLov v-model="form.sortFlag" constant-key="STATUS_YES_NUM" />
        </el-form-item>
        <el-form-item label="排序方向：" prop="sortDirect">
          <BjSelectLov v-model="form.sortFlag" constant-key="FIELD_TYPE_LIST" />
        </el-form-item>
        <el-form-item label="字段名称：" prop="indexCode">
          <bj-code-select v-model="form.indexCode" @change="changeIndexCode" />
        </el-form-item>
        <el-form-item label="排序优先级：" prop="weight">
          <el-input-number
            v-model="form.weight"
            :maxlength="MAX_LENGTH.DEFAULT"
            controls-position="right"
            :min="0"
            :max="100"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { settingApi } from '@/api';
import BjSelectLov from '@/components/BjSelectLov';
import BjCodeSelect from '../../components/BjCodeSelect';
export default {
  components: {
    BjSelectLov,
    BjCodeSelect,
  },
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      dialogVisible: false,
      form: {},
      rules: {
        visibleFlag: [{ required: true, message: '请选择是否显示', trigger: 'change' }],
        sortFlag: [{ required: true, message: '请选择是否排序', trigger: 'change' }],
        indexCode: [{ required: true, message: '请选择字段名称', trigger: 'change' }],
        weight: [{ required: true, message: '输入排序优先级', trigger: 'blur' }],
      },
      pageNun: 1,
      pageSize: 15,
      total: 0,
    };
  },
  mounted() {},
  methods: {
    changeIndexCode(data) {
      this.form.indexCode = data.indexCode;
      this.form.indexId = data.indexId;
    },
    onSubmit() {
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        settingApi.addInquiryField(this.form).then(res => {
          console.log(res);
        });
      });
    },
    showDialog() {
      this.dialogVisible = true;
    },
    handleClose(done) {
      this.dialogVisible = false;
    },
  },
};
</script>
