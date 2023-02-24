<template>
  <div class="app-container">
    <BjDivider>基础信息 </BjDivider>
    <el-form :model="form">
      <el-row>
        <el-col :span="24">
          <el-form-item label="安装服务编码： " prop="installServiceCode">
            {{ form.installServiceCode }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="安装服务名称： " prop="name">
            {{ form.name }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="安装类别： " prop="type">
            {{ form.type }}
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="配备人员： " prop="peopleAmount"
            >{{ form.peopleAmount }}人
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="服务费用： " prop="money"> {{ form.money }}元 </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="服务地区： " prop="fullAddress">
            {{
              (form.serviceZone || [])
                .map(e => e.cityDetail.levelTwoAreaName + e.cityDetail.levelThreeAreaName)
                .join('，')
            }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注： " prop="remark">
            {{ form.remark }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { worksheetApi } from '@/api';

export default {
  name: 'BusinessWorksheetServiceView',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      form: {},
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.onView(this.$route.query.id);
  },
  methods: {
    onView(id) {
      worksheetApi.DetailInstallServices({ id: id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.form = this.$lodash.clone(res);
        }
      });
    },
  },
};
</script>
<style scoped lang="scss">
.dialogForm {
  .el-input {
    width: 240px;
  }
}
</style>
