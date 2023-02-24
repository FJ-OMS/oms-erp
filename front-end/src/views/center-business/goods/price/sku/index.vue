<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
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
          <el-form-item label="SPU" prop="spuCode">
            <el-input
              v-model.trim="queryParams.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="sku" prop="skuCode">
            <el-input
              v-model.trim="queryParams.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品名称" prop="spuName">
            <el-input
              v-model.trim="queryParams.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDelete"
            >删除</el-button
          >
        </el-col>
      </template>
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
              <div v-else-if="col.prop === 'materialMainImages'" class="flex justify-between">
                <BjViewUrl :url="(scope.row[col.prop] || [])[0]" style="width: 90px" />
              </div>
              <el-switch
                v-else-if="col.prop === 'showFlag'"
                v-model="scope.row[col.prop]"
                :active-value="1"
                :inactive-value="0"
                @change="updateSkuStatus(scope.row)"
              />
              <!-- 价格 -->
              <template v-else-if="isPriceProp(scope.row, col.prop)">
                {{ isPriceProp(scope.row, col.prop) }}
              </template>

              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="props">
              <!-- <el-button type="text"  @click="onView(props.row)">查看</el-button> -->
              <el-button type="text" class="edit-btn" @click="onEdit(props.row)">编辑</el-button>
              <!-- <el-button type="text"  @click="onDelete(props.row)" class='delete-btn'>删除</el-button> -->
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
const getDefaultForm = () => {
  return {};
};

export default {
  name: 'BusinessGoodsPriceSkuList',
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: goodsApi.getSku,
      // 查询参数
      queryParams: {
        spuCode: null,
        skuCode: null,
        spuName: null,
      },
      title: '',
      dialogVisible: false,
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
        classify: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
      },
      labelsVersion: 11,
      labelList: [
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false },
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU', prop: 'skuName', isCheck: true, disabled: false, width: 160 },
        { label: '素材', prop: 'materialMainImages', isCheck: true, disabled: false },
        { label: '备注', prop: 'goodsRemark', isCheck: true, disabled: false },
        { label: '状态', prop: 'showFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.getPriceSetting();
  },
  methods: {
    // 返回价格
    isPriceProp(row, prop) {
      if (!Object.prototype.hasOwnProperty.call(row, prop)) {
        const item = row.priceReqDTO.find(e => e.priceCode == prop);
        if (item) {
          return this.$number2money(item.priceValue);
        }
      }
      return false;
    },
    getPriceSetting() {
      /** *  表头 *****/
      goodsApi.getListPriceItem().then(res => {
        (res || [])
          .map(e => {
            return { label: e.meaning, prop: e.value, isCheck: true, disabled: false };
          })
          .forEach(e => {
            const INDEX = this.defaultTabulation.findIndex(ee => ee.prop == e.prop);
            if (INDEX == -1) {
              this.defaultTabulation.push(e);
            }
          });
      });
    },
    /** 删除 */
    onDelete(row) {
      let ids = [];
      if (!row) {
        ids = this.selectedRows.map(e => e.id);
      } else {
        ids.push(row.id);
      }
      this.$confirm('确认删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const API_DATA = { ids: ids };
          this.deleteApi(API_DATA);
        })
        .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      this.$router.push({ name: 'BusinessGoodsPriceSkuAdd' });
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$router.push({
        name: 'BusinessGoodsPriceSkuEdit',
        query: { spuId: row.spuId, skuCode: row.skuCode },
      });
    },

    deleteApi(API_DATA) {
      goodsApi.deleteSku(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    updateSkuStatus(row) {
      goodsApi
        .updateSkuStatus({
          id: row.id,
          showFlag: row.showFlag ? 0 : 1,
        })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success('操作成功');
            setTimeout(() => {
              this.reFresh();
            }, 500);
          }
        });
    },
  },
};
</script>
