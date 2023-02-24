<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :rules="rules" :inline="true" label-width="120px">
      <el-form-item label="数据权限编码" prop="code">
        <el-input
          v-model.trim="form.code"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="数据权限名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="描述" prop="describe">
        <el-input
          v-model.trim="form.describe"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="启用" prop="delivery">
        <el-switch v-model="form.delivery" />
      </el-form-item>
      <el-tabs type="border-card">
        <el-tab-pane label="行权限">
          <el-link type="danger" disabled
            >说明：行权限定义用户可见的数据范围，用户只可见到符合所设权限字段的数据</el-link
          >
          <div class="mt20">
            <el-row>
              <el-form-item label="权限规则">
                <el-radio-group v-model="form.resource">
                  <el-radio label="AND" />
                  <el-radio label="OR" />
                </el-radio-group>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="权限字段">
                <el-button
                  v-if="form.horizontal.length === 0"
                  type="primary"
                  icon="el-icon-circle-plus"
                  @click="addHorizontal()"
                  >添加表</el-button
                >
                <div v-for="(item, index) in form.horizontal" :key="index" class="mb20">
                  <el-select v-model="item.table" class="width" placeholder="请选择">
                    <el-option
                      v-for="(it, ind) in option1"
                      :key="ind"
                      :label="it.label"
                      :value="it.value"
                    />
                  </el-select>
                  <el-select v-model="item.field" class="ml0 width" placeholder="请选择">
                    <el-option
                      v-for="(it, ind) in option2"
                      :key="ind"
                      :label="it.label"
                      :value="it.value"
                    />
                  </el-select>
                  <el-select v-model="item.shop" class="ml0 width" placeholder="请选择">
                    <el-option
                      v-for="(it, ind) in option3"
                      :key="ind"
                      :label="it.label"
                      :value="it.value"
                    />
                  </el-select>
                  <span class="el-icon-delete-solid ml0" @click="delHorizontal(index)" />
                  <span class="el-icon-circle-plus ml0" @click="addHorizontal(index)" />
                </div>
              </el-form-item>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="权限规则">
          <el-link type="danger" disabled
            >说明：列权限定义用户不可见的字段数据，设置的字段会被隐藏处理</el-link
          >
          <div class="mt20">
            <el-row>
              <el-form-item label="权限字段">
                <el-button
                  v-if="form.vertical.length === 0"
                  type="primary"
                  icon="el-icon-circle-plus"
                  @click="addVertical()"
                  >添加表</el-button
                >
                <div v-for="(item, index) in form.vertical" :key="index" class="mb20">
                  <el-select v-model="item.table" class="width" placeholder="请选择">
                    <el-option
                      v-for="(it, ind) in option1"
                      :key="ind"
                      :label="it.label"
                      :value="it.value"
                    />
                  </el-select>
                  <el-select
                    v-model="item.name"
                    class="ml0"
                    filterable
                    multiple
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="it in option4"
                      :key="it.value"
                      :label="it.label"
                      :value="it.value"
                    />
                  </el-select>
                  <span class="el-icon-delete-solid ml0" @click="delVertical(index)" />
                  <span class="el-icon-circle-plus ml0" @click="addVertical(index)" />
                </div>
              </el-form-item>
            </el-row>
          </div>
        </el-tab-pane>
        <el-form-item label=" ">
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="handleSubmit()">确 定</el-button>
        </el-form-item>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { testApi } from '@/api';
export default {
  name: 'BusinessUserRoleAdd',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      // 遮罩层
      loading: false,
      queryApi: testApi.transactionList,
      // 查询参数
      queryParams: {},
      title: '',
      dialogVisible: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      /** 数据总条数 */
      total: 0,
      // 表单参数
      form: {
        horizontal: [
          { table: '1', field: '', shop: '' },
          { table: '2', field: '', shop: '' },
          { table: '3', field: '', shop: '' },
        ],
        vertical: [{ table: '1', name: [] }],
      },
      // 表单校验
      rules: {
        code: [{ required: true, message: '请输入数据权限编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入数据权限名称', trigger: 'blur' }],
        delivery: [{ required: true, message: '请启用', trigger: 'blur' }],
      },
      option1: [{ id: 0, label: '订单', value: 'dingdan1' }],
      option2: [{ id: 1, label: '销售店铺', value: 'dianpu' }],
      option3: [{ id: 2, label: '九牧京东自营店', value: 'jm_Jd' }],
      option4: [
        { id: 4, label: '金额', value: 'jine' },
        { id: 5145, label: '订单', value: 'dingdan' },
      ],
      dialogData: [{ code: 'UTF-16', name: '张三' }],
    };
  },
  created() {},
  methods: {
    getList() {},
    /** 删除行 */
    delHorizontal(index) {
      this.form.horizontal.splice(index, 1);
    },
    /** 添加行 */
    addHorizontal(index) {
      index = index + 1 || 0;
      this.form.horizontal.splice(index, 0, { table: '', field: '', shop: '' });
    },
    /** 删除列 */
    delVertical(index) {
      this.form.vertical.splice(index, 1);
    },
    /** 添加列 */
    addVertical(index) {
      index = index + 1 || 0;
      this.form.vertical.splice(index, 0, { table: '', name: [] });
    },
    /** 确定 */
    handleSubmit() {},
    /** 取消 */
    handleClose() {
      this.$router.back(-1);
    },
  },
};
</script>
<style scoped>
.width {
  width: 160px;
}
</style>
