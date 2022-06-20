<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" inline label-width="100px">
      <el-form-item label="商品ID">
        <el-input v-model="form.id" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="商品分类" prop="classify">
        <el-select v-model.trim="form.classify" placeholder="请选择" clearable>
          <el-option v-for="(item, index) in 5" :key="index" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="商品名称" prop="name">
        <el-select v-model.trim="form.name" placeholder="请选择" clearable>
          <el-option v-for="(item, index) in 5" :key="index" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="reset('form')">重 置</el-button>
        <el-button type="primary" @click="search()">查 询</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" @click="toPage('BusinessGoodsSkuOrdinaryIndex')"
          >SKU管理</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" @click="toPage('BusinessGoodsPriceItemIndex')"
          >价格项管理</el-button
        >
      </el-col>
    </el-row>
    <el-table :data="tableData" style="width: 100%" border :span-method="objectSpanMethod">
      <el-table-column prop="design" align="center" label="款式">
        <template slot-scope="scope">
          {{ scope.row.design ? scope.row.design : '未设置SKU' }}
        </template>
      </el-table-column>
      <el-table-column prop="color" align="center" label="颜色">
        <template slot-scope="scope">
          {{ scope.row.color ? scope.row.color : '未设置SKU' }}
        </template>
      </el-table-column>
      <el-table-column prop="spec" align="center" label="SKU编码（商品SKU编码）">
        <template slot-scope="scope">
          {{ scope.row.spec ? scope.row.spec : '未设置SKU' }}
        </template>
      </el-table-column>
      <el-table-column prop="price" align="center" label="价格（元）">
        <template slot-scope="scope">
          <span v-if="scope.row.price === undefined">未设置价格项</span>
          <el-input
            v-else
            v-model="scope.row.price"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
          />
        </template>
      </el-table-column>
      <el-table-column prop="costPrie" align="center" label="成本价">
        <template slot-scope="scope">
          <span v-if="scope.row.costPrie === undefined">未设置价格项</span>
          <el-input
            v-else
            v-model="scope.row.costPrie"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
          />
        </template>
      </el-table-column>
    </el-table>
    <div class="text-center mt20">
      <el-button @click="resetTable">重 置</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BusinessGoodsPriceComponentsTableView',
  data() {
    return {
      tableData: [
        { design: '', color: '', spec: '', price: '', costPrie: '' },
        { design: '', color: '', spec: '', price: '', costPrie: '' },
        { design: '机械款-S', color: '彩色', spec: 'GG001', price: '10', costPrie: '6' },
        { design: '机械款-S', color: '彩色', spec: 'GG001' },
        { design: '机械款-S', color: '彩色', spec: 'GG001', price: '', costPrie: '' },
      ],
      form: {},
      rules: {},
    };
  },
  methods: {
    /** 列合并 */
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          };
        }
      }
    },
    /** 重置 */
    reset() {
      this.form = {};
    },
    /** 查询 */
    search() {
      alert('查询');
    },
    /** 保存 */
    resetTable() {},
    /** 重置 */
    save() {},
    /** 页面跳转 */
    toPage(name) {
      this.$router.push({ name });
    },
  },
};
</script>
