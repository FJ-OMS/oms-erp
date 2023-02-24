<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'100px'"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="商品ID" prop="shopId">
            <el-input
              v-model.trim="queryParams.shopId"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="SKU编码" prop="speId">
            <el-input
              v-model.trim="queryParams.speId"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品名称" prop="shopName">
            <el-input
              v-model.trim="queryParams.shopName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>
      <!-- <template v-slot:tableOperation>
        <el-col>
            <el-button type="primary"  @click="onAdd" class="main-btn">新增价格</el-button>
          </el-col>
        </div>
      </template> -->
      <template v-slot:tableColumn>
        <template v-for="(col, index) in dropCols">
          <el-table-column
            v-if="col.prop !== 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || false"
            align="center"
            class-name="item"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { channelApi } from '@/api';
const getDefaultForm = () => {
  return {};
};

export default {
  name: 'BusinessGoodsHistoryPriceList',
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      queryApi: channelApi.getOnlineShopPage,
      // 查询参数
      queryParams: {},
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelList: [
        { label: '商品ID', prop: 'code', isCheck: true, disabled: false },
        { label: '商品名称', prop: 'name', isCheck: true, disabled: false },
        { label: 'SKU编码', prop: 'channelTypeMeaning', isCheck: true, disabled: false },
        { label: 'SKU类型', prop: 'type', isCheck: true, disabled: false },
        { label: '当前价格（元）', prop: 'orgName', isCheck: true, disabled: false },
        { label: '成本价', prop: 'onlineStoreTypeMeaning', isCheck: true, disabled: false },
        { label: '历史价格（元）', prop: 'onlineStoreTypeMeaning', isCheck: true, disabled: false },
        { label: '操作人员', prop: 'status', isCheck: true, disabled: false, width: 135 },
        {
          label: '创建时间',
          prop: 'channelStorehouseStatus',
          isCheck: true,
          disabled: false,
          width: 135,
        },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {},
};
</script>
