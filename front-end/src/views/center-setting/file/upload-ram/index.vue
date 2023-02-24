<template>
  <div class="app-container">
    <div class="flex class2021-7-14">
      <el-progress
        type="circle"
        :percentage="percentage"
        :color="color"
        :format="format"
        :width="200"
      />
      <div class="el-input-group__append-width-100">
        <el-form ref="form" :model="form" :rules="rules" label-width="140px">
          <el-form-item label="最大容量">
            <el-input v-model="form.totalCapacity" :maxlength="MAX_LENGTH.DEFAULT">
              <template slot="append">{{ form.totalCapacityUnit }}</template></el-input
            >
          </el-form-item>
          <el-form-item label="文件大小限制">
            <el-input v-model="form.storageSize" :maxlength="MAX_LENGTH.DEFAULT">
              <BjSelectLov slot="append" v-model="form.storageUnit" lov-code="HFLE.STORAGE_UNIT" />
            </el-input>
          </el-form-item>
        </el-form>
        <div class="footer">
          <el-button
            v-permission:SettingFileUploadConfig
            @click="$toPage('SettingFileUploadConfigIndex')"
            >查 看 配 置 详 情</el-button
          >
          <el-button v-permission:SettingFileUploadRamIndex_Save @click="handleClose"
            >重 置</el-button
          >
          <el-button
            v-permission:SettingFileUploadRamIndex_Save
            type="primary"
            @click="handleConfirm('form')"
            >保 存</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';
const getDefaultForm = () => {
  return {
    redisUsedCapacity: 50,
    storageSize: 30,
    storageUnit: 'MB',
    totalCapacity: 10240,
    totalCapacityUnit: 'MB',
    usedCapacity: 50,
  };
};

export default {
  name: 'SettingFileUploadRamIndex',
  // mixins: [pageMiXin],
  data() {
    return {
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        storageType: [{ required: true, message: '请选择', trigger: 'change' }],
      },
    };
  },
  computed: {
    percentage() {
      const n = this.form.usedCapacity / this.form.totalCapacity;
      return Number((n > 1 ? 1 : n) * 100);
    },
    color() {
      if (this.percentage > 90) {
        return 'rgb(255, 73, 73)';
      }
      if (this.percentage > 80) {
        return 'rgb(230, 162, 60)';
      }
      return 'rgb(32, 160, 255)';
    },
  },
  watch: {},
  mounted() {
    this.getApi();
  },
  methods: {
    format() {
      return `已使用容量
      ${this.form.usedCapacity}/${this.form.totalCapacity}(${this.form.totalCapacityUnit})`;
    },
    handleClose() {
      this.$confirm(`是否放弃当前页面数据并重新获取？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.getApi();
        })
        .catch(() => {});
    },
    handleConfirm(formName) {
      console.log('确认', this.form);

      this.$confirm(`您正在保存当前数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const API_DATA = this.$lodash.clone(this.form);

          this.$refs[formName].validate(valid => {
            if (!valid) return this.$scrollToFormError();
            this.updateApi(API_DATA);
          });
        })
        .catch(() => {});
    },
    getApi() {
      settingApi.getFileCapacityConfigs({}).then(res => {
        this.form = res;
      });
    },
    // 修改API
    updateApi(API_DATA) {
      settingApi.updateFileCapacityConfigs(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.dialogVisible = false;
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.flex {
  width: 500px;
  display: flex;
  align-items: center;
  flex-direction: column;
}
.flex > div {
  margin: 16px;
}
</style>
<style>
.class2021-7-14 .el-progress__text {
  font-size: 14px !important;
  line-height: 25px;
  height: 50px;
}
</style>
