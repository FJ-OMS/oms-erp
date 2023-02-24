<template>
  <div class="app-container">
    <BjDivider>基础信息 </BjDivider>
    <el-form label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="商品编码：">
            {{ goodsInfo.code }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品名称：">
            {{ goodsInfo.name }}
          </el-form-item>
        </el-col>
        <el-col :span="8" />
      </el-row>
      <el-form-item label="商品分类：">
        {{ goodsInfo.categoryName }}
      </el-form-item>
    </el-form>

    <BjDivider>SKU组合列表</BjDivider>
    <div class="mb10">
      <el-button type="primary" @click="toSetPrice">批量设置价格</el-button>
    </div>

    <el-table
      ref="multipleTable"
      :data="skuList"
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      :row-style="rowStyleFn"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" show-overflow-tooltip />
      <el-table-column label="序号" type="index" width="55" align="center" />
      <template v-for="(col, index) in skuTableColumns">
        <el-table-column :key="index" :prop="col.prop" :label="col.label">
          <template slot-scope="props">
            <template v-if="isPriceProp(col.prop)">
              <BjInputMoney v-model.trim="props.row[col.prop]" />
            </template>
            <span v-else>{{ props.row[col.prop] }}</span>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <span class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">保 存</el-button>
    </span>

    <DataForm ref="DataForm" @handleDataFormConfirm="handleDataFormConfirm" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { goodsApi } from '@/api';
import { randomID } from '@/utils/index';
import DataForm from './components/DataForm.vue';
export default {
  name: 'BusinessGoodsSkuOrdinaryViewForm',
  components: {
    DataForm,
  },
  mixins: [viewMiXin],

  data() {
    return {
      /** 选择的商品信息 */
      goodsInfo: {
        id: null,
        code: null,
        name: null,
        type: null,
        categoryId: null,
        categoryName: null,
        brandName: null,
        materialId: null,
        materialName: null,
        mainImage: [],
        imageList: [],
        detailHtml: null,
        checkGoodsAttributeResVOS: [],
      },
      /** 商品属性 */
      goodsAttributeKeys: [], // 所有属性列表

      /** SKU LIST */
      skuList: [],
      selectedRow: [],
      /** SKU 表头 */
      skuTableColumns_sku: [],
      skuTableColumns_price: [],
    };
  },
  computed: {
    SELECTED_BJ_EBC_ID() {
      return this.selectedRow.map(e => e.BJ_EBC_ID);
    },
    spuId() {
      return this.$route.query.spuId || '';
    },
    attributeCodeList_isSku() {
      return this.goodsAttributeKeys.filter(e => e.isSku).map(e => e.code);
    },
    // 表头
    skuTableColumns() {
      return [
        {
          prop: 'sku',
          label: 'sku',
        },
        {
          prop: 'skuName',
          label: 'SKU',
        },
      ]
        .concat(this.skuTableColumns_sku)

        .concat(this.skuTableColumns_price);
    },
  },
  watch: {
    spuId: {
      handler(v) {
        if (v) this.getGoodsInfo(v);
      },
      immediate: true,
    },
  },
  mounted() {
    this.getPriceSetting();
  },
  methods: {
    handleSelectionChange(d) {
      this.selectedRow = [].concat(d);
    },
    toSetPrice() {
      if (this.SELECTED_BJ_EBC_ID.length == 0) {
        this.$message.warning('请选择sku');
        return;
      }
      this.$refs.DataForm.show(this.$lodash.clone(this.skuTableColumns_price));
    },
    handleDataFormConfirm(data) {
      this.skuList = this.skuList.map(e => {
        if (this.SELECTED_BJ_EBC_ID.includes(e.BJ_EBC_ID)) {
          return { ...e, ...data };
        } else {
          return e;
        }
      });
    },
    isPriceProp(v) {
      return this.skuTableColumns_price.findIndex(e => e.prop === v) != -1;
    },
    // 获取商品信息
    getGoodsInfo(id) {
      goodsApi
        .detail({ id: id })
        .then(res => {
          this.goodsInfo = res;
          this.getAttributeValueByGoodsId();
        })
        .catch(err => {
          console.error(err);
        });
    },
    // 加载对应的属性数据
    getAttributeValueByGoodsId() {
      goodsApi
        .getAttributeValueByGoodsId({ id: this.goodsInfo.id })
        .then(res => {
          /** 构建商品属性的表单结构 */
          const TEMP = [];
          (res.attributeList || []).forEach(element => {
            TEMP.push(...(element.attributeAndValueResVOS || []));
          });
          this.goodsAttributeKeys = TEMP.sort((a, b) => {
            return a.isSku ? 1 : -1;
          });

          /** *  SKU表头 *****/
          this.skuTableColumns_sku = this.goodsAttributeKeys
            .filter(e => e.isSku)
            .map(e => {
              return {
                prop: e.code,
                label: e.attributeName,
              };
            });

          /** 旧的SKU数据 */
          this.skuList = (res.skus || []).map(e => {
            // const TEMP_BJ_NAME = {};
            // Object.keys(e).forEach(code => {
            //   // 查找sku属性的显示值
            //   if (this.attributeCodeList_isSku.includes(code)) {
            //     const NAME_KEY = code + '_bj_name';
            //     TEMP_BJ_NAME[NAME_KEY] = e[code];
            //   }
            // });
            return { ...e, BJ_EBC_ID: randomID() };
          });
        })
        .catch(err => {
          console.error(err);
        });
    },
    getPriceSetting() {
      /** *  表头 *****/
      goodsApi.getListPriceItem().then(res => {
        this.skuTableColumns_price = (res || []).map(e => {
          return {
            prop: e.value,
            label: e.meaning,
          };
        });
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
    handleConfirm() {
      const API_DATA = this.skuList.map(e => {
        return {
          code: e.sku,
          priceReqDTO: this.skuTableColumns_price.map(priceItem => {
            return {
              priceCode: priceItem.prop,
              priceValue:
                e[priceItem.prop] === 0 ? 0 : e[priceItem.prop] ? Number(e[priceItem.prop]) : null,
            };
          }),
        };
      });

      goodsApi.updateSkuPrice(API_DATA).then(res => {
        if (!res.failed) {
          this.msgSuccess('保存成功');
          this.handleClose();
        }
      });
    },

    rowStyleFn({ row, rowIndex }) {
      const TEMP = {};
      if (row.sku && row.sku === this.$route.query.skuCode) {
        TEMP.background = 'pink';
      }
      return TEMP;
    },
  },
};
</script>
<style>
.isSku > label::before {
  content: '#';
  color: #ff4949;
  margin-right: 4px;
  font-size: 10px;
}
.small-img {
  margin: 4px;
}
</style>
