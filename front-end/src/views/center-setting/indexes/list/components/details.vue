<template>
  <div class="app-container">
    <BjDivider right-fixed
      >基础信息
      <template slot="right">
        <el-button @click="tryCancel">取 消</el-button>
        <el-button type="primary" @click="submit('form')">保 存</el-button>
      </template>
    </BjDivider>

    <el-form ref="form" :model="form" :rules="rules" inline label-width="120px">
      <el-form-item label="索引" prop="indexCode">
        <el-input v-model="form.indexCode" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="form.remark" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="状态" prop="enabledFlag">
        <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
      </el-form-item>
      <el-form-item label="分片数量" prop="shards">
        <el-input v-model="form.shards" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="副本数量" prop="replicas">
        <el-input v-model="form.replicas" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="创建人">
        <el-input :maxlength="MAX_LENGTH.DEFAULT" placeholder="" :disabled="true" />
      </el-form-item>
      <el-form-item label="最近更新人">
        <el-input :maxlength="MAX_LENGTH.DEFAULT" placeholder="" :disabled="true" />
      </el-form-item>
      <el-form-item label="最近更新时间">
        <el-input :maxlength="MAX_LENGTH.DEFAULT" placeholder="" :disabled="true" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input :maxlength="MAX_LENGTH.DEFAULT" placeholder="" :disabled="true" />
      </el-form-item>
    </el-form>

    <BjDivider
      >同步字段
      <template slot="button">
        <el-button type="primary" @click="onAdd">新 增</el-button>
      </template>
    </BjDivider>

    <el-table border :data="form.indexFieldList">
      <el-table-column label="字段名" align="center" prop="fieldName">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.fieldName"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
          />
        </template>
      </el-table-column>
      <el-table-column label="字段类型" align="center" prop="fieldType">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.fieldType"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            @blur="checkType(scope.$index)"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否索引" align="center" prop="analyzerFlag">
        <template slot-scope="scope">
          <el-select
            v-model="scope.row.analyzerFlag"
            :disabled="scope.row.fieldType !== 'Text'"
            placeholder="请选择"
            @change="selection(scope.$index)"
          >
            <el-option
              v-for="item in STATUS_YES_NUM"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="分词器" align="center" prop="filedAnalyzerCode">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.filedAnalyzerCode"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="scope.row.analyzerFlag !== 1"
            placeholder="请输入"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否主键" align="center" prop="pkFlag">
        <template slot-scope="scope">
          <el-select v-model="scope.row.pkFlag" placeholder="请选择">
            <el-option
              v-for="item in STATUS_YES_NUM"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="onDelete(scope.$index)">删 除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="mt20" style="text-align: right">
      <el-pagination
        :current-page="pageNun"
        :page-sizes="PAGE_SIZES"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { settingApi } from '@/api';
import { STATUS_YES_NUM, PAGE_SIZES } from '@/utils/constants';
export default {
  mixins: [viewMiXin],
  data() {
    return {
      STATUS_YES_NUM,
      PAGE_SIZES,
      data: [],
      form: {
        enabledFlag: 0,
        indexFieldList: [],
      },
      rules: {
        indexCode: [{ required: true, message: '请输入索引', trigger: 'blur' }],
      },
      pageNun: 1,
      pageSize: 15,
      total: 0,
    };
  },
  created() {
    if (this.$route.query.form) {
      this.form = JSON.parse(this.$route.query.form);
      settingApi.getIndexesDetail({ indexId: this.form.indexId }).then(res => {
        this.form.indexFieldList = res;
        this.total = this.form.indexFieldList.length;
      });
    }
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    onAdd() {
      const obj = {
        fieldName: '',
        fieldType: '',
        analyzerFlag: 0,
        filedAnalyzerCode: '',
        pkFlag: '',
      };
      this.form.indexFieldList.push(obj);
    },
    onDelete(index) {
      this.form.indexFieldList.splice(index, 1);
    },
    checkType(index) {
      if (this.form.indexFieldList[index].fieldType !== 'Text') {
        this.form.indexFieldList[index].analyzerFlag = 0;
      }
    },
    selection(index) {
      if (this.form.indexFieldList[index].analyzerFlag !== 1) {
        this.form.indexFieldList[index].filedAnalyzerCode = '';
      }
    },
    submit(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        const boolean = this.form.indexFieldList.every(
          item => item.fieldName && item.fieldType !== '' && item.pkFlag,
        );
        console.log(boolean);
        if (boolean) {
          this.$alert('请输入同步字段信息', '提示', {
            confirmButtonText: '确定',
            callback: action => {},
          });
          return;
        }
        settingApi.getIndexesAdd(this.form).then(res => {
          if (!res.failed) {
            // if (this.form.indexId) {
            //   return;
            // }
            // this.form = {
            //   enabledFlag: 0,
            //   indexFieldList: [],
            // };

            this.$message.success('保存成功');
            setTimeout(() => {
              this.complete('SettingIndexesConfigListIndex');
            }, 500);
          }
        });
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.flex {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
