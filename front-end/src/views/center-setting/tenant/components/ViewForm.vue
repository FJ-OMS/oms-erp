<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-row>
        <BjDivider>基础信息</BjDivider>
        <el-col :span="8">
          <el-form-item label="租户编码" prop="tenantName"> 自动生成</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="租户名称" prop="tenantName">
            <el-input
              v-model.trim="form.tenantName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="行业" prop="status">
            <el-select v-model.trim="form.status" placeholder="请选" clearable>
              <el-option v-for="item in 5" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="描述" prop="tenantName">
            <el-input
              v-model.trim="form.tenantName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <BjDivider>管理账号</BjDivider>
        <el-col :span="8">
          <el-form-item label="账号" prop="name" required>
            <el-input
              v-model.trim="form.name"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="名称" prop="nick" required>
            <el-input
              v-model.trim="form.nick"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="密码" prop="pwd">
            <el-input
              v-model.trim="form.pwd"
              :maxlength="MAX_LENGTH.DEFAULT"
              type="password"
              placeholder="请输入"
              show-password
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <BjFormItemMobile v-model.trim="form.phone" prop="phone" label="手机号" clearable />
        </el-col>
        <el-col :span="8">
          <BjFormItemEmail v-model.trim="form.email" label="邮箱" prop="email" clearable />
        </el-col>
        <BjDivider>基本配置</BjDivider>

        <el-col :span="8">
          <el-form-item label="系统名称" prop="phone" required>
            <el-input
              v-model.trim="form.phone"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="域名" prop="phone" required>
            <el-input
              v-model.trim="form.phone"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="有效期从" prop="strDate" required>
            <el-date-picker
              v-model="form.strDate"
              class="full-width"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="有效期至" prop="endDate">
            <el-date-picker
              v-model="form.endDate"
              class="full-width"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="启用" prop="endDate">
            <el-switch v-model="form.status" :active-value="1" :inactive-value="0"
          /></el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="logo" prop="endDate">
            <BjUpload
              ref="upload"
              class="avatar-uploader"
              auto-upload
              accept="image/png,image/jpeg"
              :accept-list="['image/jpeg', 'image/png']"
              :limit-size="2048"
              @afterUpload="afterUpload"
            >
              <template slot="default">
                <BjKeyImage
                  v-if="form.businessLicense"
                  :url="form.businessLicense"
                  class="avatar"
                />
                <i v-else class="el-icon-plus avatar-uploader-icon" />
              </template>
              <template slot="tip"> 支持JPG/JPEG/PNG格式，图片大小不超过2M </template>
            </BjUpload>
          </el-form-item>
        </el-col>
        <BjDivider>菜单配置</BjDivider>
        <el-col :span="24">
          <el-input
            v-model="menuName"
            :maxlength="MAX_LENGTH.DEFAULT"
            style="width: 120px"
            placeholder="菜单名称"
          />
          <el-button style="margin-left: 10px" @click="reSetSearchMenu">重 置</el-button>
          <el-button type="primary" @click="reFreshTree">查 询</el-button>

          <el-tree
            ref="tree"
            :data="treeData"
            :props="defaultProps"
            show-checkbox
            @node-click="handleNodeClick"
          />
        </el-col>
      </el-row>
    </el-form>

    <BjDivider>操作</BjDivider>
    <span slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm('form')">确 定</el-button>
    </span>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { tenantApi, menuApi } from '@/api';
import {} from '@/utils/constants';
import BjUpload from '@/components/BjUpload/index';
// import { patternEmail, patternCode } from '@/utils/validate';

const getDefaultForm = () => {
  return {};
};

export default {
  name: 'SettingTenantPageViewForm',
  components: {
    BjUpload,
  },
  mixins: [viewMiXin],
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      form: getDefaultForm(),
      // 表单校验
      rules: {},

      /** * 树 */

      defaultProps: {
        children: 'subMenus',
        label: 'name',
      },
      treeData: [],
      menuName: null,
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.editingBjEbcId = null;
          this.form = this.$lodash.clone(info);
        }
      },
    },
  },

  mounted() {
    this.reFreshTree();
  },
  methods: {
    reSetSearchMenu() {
      this.menuName = null;
      this.reFreshTree();
    },
    handleNodeClick(data) {},
    reFreshTree() {
      menuApi.getMenuTree({ name: this.menuName }).then(res => {
        this.treeData = (res || []).map(e => {
          // if (!e.subMenus) {
          //   delete e.subMenus;
          // }
          // if (e.subMenus && !e.subMenus.length) {
          //   delete e.subMenus;
          // }
          return e;
        });
      });
    },
    afterUpload(src) {
      this.form.businessLicense = src;
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      tenantApi.updateTenants(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      tenantApi.addTenants(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
