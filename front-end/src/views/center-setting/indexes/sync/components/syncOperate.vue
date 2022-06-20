<template>
  <div class="app-container">
    <BjDivider right-fixed
      >基础信息
      <template slot="right">
        <el-button @click="tryCancel">取 消</el-button>
        <el-button type="primary" @click="submit('form')">保 存</el-button>
      </template>
    </BjDivider>

    <el-form ref="form" :model="form" :rules="rules" inline label-width="140px">
      <el-form-item label="编码" prop="syncConfCode">
        <el-input v-model="form.syncConfCode" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="索引" prop="indexCode">
        <bj-code-select @change="changeIndexCode" />
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="form.remark" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="来源类型" prop="sourceFromType">
        <el-select v-model="form.sourceFromType" class="full-width" placeholder="请选择" clearable>
          <!-- 暂时写死 -->
          <el-option label="INTERFACE" value="INTERFACE" />
          <el-option label="DB" value="DB" />
        </el-select>
      </el-form-item>
      <el-form-item label="数据来源" prop="sourceFromCode">
        <el-input v-model="form.sourceFromCode" :maxlength="MAX_LENGTH.DEFAULT" placeholder="" />
      </el-form-item>
      <el-form-item label="数据来源详情" prop="sourceFromDetailCode">
        <el-input
          v-model="form.sourceFromDetailCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder=""
        />
      </el-form-item>
      <el-form-item label="是否记录到文件" prop="isRecord">
        <el-switch v-model="form.isRecord" :active-value="1" :inactive-value="0" />
      </el-form-item>
      <el-row>
        <el-col :span="24">
          <el-form-item label="命令文本" prop="commandContent">
            <el-input
              v-model="form.commandContent"
              :maxlength="MAX_LENGTH.DEFAULT"
              type="textarea"
              style="width: 600px"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider
      >同步字段
      <template slot="button">
        <el-button type="primary" @click="onAdd">新 增</el-button>
      </template>
    </BjDivider>
    <el-table border :data="form.indexFieldSyncConfList">
      <el-table-column label="字段名称" align="center" prop="fieldName">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.fieldName"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
          />
        </template>
      </el-table-column>
      <el-table-column label="映射字段" align="center" prop="fieldMapping">
        <template slot-scope="scope">
          <!-- <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="scope.row.fieldMapping"  placeholder="请输入" /> -->
          <el-select
            v-model="scope.row.fieldMapping"
            filterable
            remote
            reserve-keyword
            placeholder="请输入"
            :remote-method="remoteMethodFieldName"
            :loading="loading"
            @change="changeFieldName"
            @focus="changeFocus($event, scope)"
          >
            <el-option
              v-for="item in fieldNameListOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <!-- <el-button type="text"  icon="el-icon-edit" @click="onDelete(scope.$index)"
            >编 辑</el-button
          > -->
          <el-button type="text" icon="el-icon-delete" @click="onDelete(scope.$index)"
            >删 除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="mt20" style="text-align: right">
      <el-pagination
        :current-page="form.pageNun"
        :page-sizes="PAGE_SIZES"
        :page-size="form.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="form.total"
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
import BjCodeSelect from '../../components/BjCodeSelect';
export default {
  components: {
    BjCodeSelect,
  },
  mixins: [viewMiXin],
  data() {
    return {
      STATUS_YES_NUM,
      PAGE_SIZES,
      data: [],
      form: {
        indexFieldSyncConfList: [],
        pageNun: 1,
        pageSize: 15,
        total: 0,
      },
      rules: {
        syncConfCode: [{ required: true, message: '请输入编码', trigger: 'blur' }],
        indexCode: [{ required: true, message: '请输入索引', trigger: 'change' }],
        sourceFromCode: [{ required: true, message: '请输入数据来源', trigger: 'blur' }],
        sourceFromType: [{ required: true, message: '请选择来源类型', trigger: 'change' }],
      },
      loading: false,
      /** 模糊查询映射列表 */
      fieldNameList: [],
      /** 模糊查询映射匹配列表 */
      fieldNameListOptions: [],
      fieldNameIndex: null,
    };
  },
  computed: {},
  created() {
    if (this.$route.query.syncConfId) {
      settingApi.getIndexesSyncConfigs({ syncConfId: this.$route.query.syncConfId }).then(res => {
        // console.log(res);
        this.form = res;
      });
    }
  },
  mounted() {},
  methods: {
    changeIndexCode(data) {
      this.form.indexCode = data.indexCode;
      this.form.indexId = data.indexId;
      settingApi.getIndexesDetail({ indexId: this.form.indexId }).then(res => {
        res.forEach(item => {
          this.fieldNameList.push({
            value: item.indexFieldId,
            label: item.fieldName,
          });
        });
      });
    },
    remoteMethodFieldName(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.fieldNameListOptions = this.fieldNameList.filter(item => {
            return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.fieldNameListOptions = [];
      }
    },
    changeFieldName(val) {
      const obj = this.fieldNameListOptions.find(item => {
        return item.value === val;
      });
      const form = this.form.indexFieldSyncConfList[this.fieldNameIndex];
      form.fieldMapping = obj.label;
      form.indexFieldId = obj.value;
    },
    /** 映射聚焦时判断 */
    changeFocus(e, scope) {
      if (!this.form.indexCode) {
        this.$alert('请先输入索引获取映射字段信息！', '提示', {
          confirmButtonText: '确定',
          callback: action => {},
        });
        e.target.blur();
        return;
      }
      this.fieldNameIndex = scope.$index;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    onAdd() {
      const obj = {
        fieldName: null,
        fieldMapping: null,
      };
      this.form.indexFieldSyncConfList.push(obj);
    },
    onDelete(index) {
      this.form.indexFieldSyncConfList.splice(index, 1);
    },
    submit(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        const boolean = this.form.indexFieldSyncConfList.every(
          item => item.fieldName && item.fieldMapping,
        );
        if (!boolean || this.form.indexFieldSyncConfList.length === 0) {
          this.$alert('请输入同步字段信息', '提示', {
            confirmButtonText: '确定',
            callback: action => {},
          });
          return;
        }
        settingApi.AddIndexesSync(this.form).then(res => {
          if (!res.failed) {
            this.$message.success('保存成功');
            setTimeout(() => {
              this.complete('SettingIndexesSyncIndex');
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
