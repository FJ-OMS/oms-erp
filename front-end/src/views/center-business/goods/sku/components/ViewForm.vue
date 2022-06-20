<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >{{ TITLE_TEXT }}基础信息{{ setFlag }}

      <template slot="right">
        <template v-if="isEdit">
          <el-button @click="tryCancel">取 消</el-button>
          <el-button
            v-if="skuInfo.status == 0 || skuInfo.status == 1"
            type="primary"
            @click="handleConfirm(true)"
            >保存为草稿</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuOrdinary_Audit
            type="primary"
            @click="handleConfirm(false)"
            >提 交</el-button
          >
        </template>
        <template v-if="isView">
          <el-button type="primary" @click="cancel">返 回</el-button>
        </template>
      </template>
    </BjDivider>
    <el-form :label-width="labelWidth + 'em'">
      <el-row>
        <el-col :span="8">
          <el-form-item label="SKU名称">
            <el-input
              v-if="isPackAgeSku && isEdit"
              v-model.trim="skuInfo.skuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
            <span v-else>
              {{ skuInfo.skuName }}
            </span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <!-- 带已生效状态 不能更改，去重校验

          可修改的普通SKU编码 状态：0草稿  1被驳回
           -->
          <el-form-item label="SKU编码">
            <el-input
              v-if="!isPackAgeSku && isEdit && [0, 1].bjIncludes(skuInfo.status)"
              v-model.trim="skuInfo.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
            <span v-else>
              {{ skuInfo.skuCode }}
            </span>
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="商品名称">
            {{ skuInfo.spuName }}
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="商品分类">
            {{ skuInfo.categoryName }}
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="商品编码">
            {{ skuInfo.spuCode }}
          </el-form-item>
        </el-col>

        <el-col v-if="setFlag == GOODS_COMBO_TYPES.ORDINARY" :span="8">
          <el-form-item label="商品品牌">
            {{ skuInfo.brandName }}
          </el-form-item>
        </el-col>
      </el-row>

      <BjDivider
        >{{ TITLE_TEXT }}SKU属性<template v-if="!isPackAgeSku" v-slot:tips
          ><i class="red">#</i> 参与生成sku的属性</template
        ></BjDivider
      >

      <el-row>
        <el-col class="sku-attribute-title" :span="24">系统属性</el-col>
        <el-col
          v-for="(col, i) in DefaultAttributeList"
          :key="'DefaultAttributeList' + i"
          class="sku-attribute-list"
          :span="8"
        >
          <el-form-item :label="`${col.name}`" :class="{ isSku: false }">
            <AttributeInput v-model="skuInfo[col.code]" :readonly="!isEdit" :col-prop="col.code">
            </AttributeInput>
          </el-form-item>
        </el-col>

        <el-col v-if="LIST.CLASSIFY.length" class="sku-attribute-title" :span="24">分类属性</el-col>
        <el-col
          v-for="(col, i) in LIST.CLASSIFY"
          :key="'CLASSIFY' + i"
          class="sku-attribute-list"
          :span="8"
        >
          <el-form-item :label="`${col.label}`" :class="{ isSku: col.isSku }">
            {{ col.value }}
          </el-form-item>
        </el-col>

        <el-col v-if="LIST.SPU.length" class="sku-attribute-title" :span="24">商品属性</el-col>
        <el-col v-for="(col, i) in LIST.SPU" :key="'SPU' + i" class="sku-attribute-list" :span="8">
          <el-form-item :label="`${col.label}`" :class="{ isSku: col.isSku }">
            {{ col.value }}
          </el-form-item>
        </el-col>

        <el-col v-if="LIST.ATTACHED.length" class="sku-attribute-title" :span="24">附加属性</el-col>
        <el-col
          v-for="(col, i) in LIST.ATTACHED"
          :key="'ATTACHED' + i"
          class="sku-attribute-list"
          :span="8"
        >
          <el-form-item :label="`${col.label}`" :class="{ isSku: col.isSku }">
            <AttributeInput
              v-model="skuInfo[col.listName][col.listIndex].attributeValue"
              :readonly="!isEdit"
              :type="col.formType"
              :value-list="col.attributeValueList"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <BjDivider>{{ TITLE_TEXT }}SKU价格</BjDivider>
      <el-row>
        <el-col v-for="(col, i) in priceCols" :key="i" class="sku-attribute-list" :span="8">
          <el-form-item :label="`${col.label}`" :class="{ isSku: false }">
            <AttributeInput
              v-model="skuInfo[col.prop]"
              :readonly="!isEdit"
              :is-price="true"
              :type="col.formType"
              :value-list="col.attributeValueList"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <BjDivider v-if="isPackAgeSku">{{ TITLE_TEXT }}SKU明细</BjDivider>
      <el-table
        v-if="isPackAgeSku"
        ref="multipleTable"
        :data="skuInfo.skuDetailList"
        border
        :header-cell-style="$rowStyle.DEFAULT"
      >
        <el-table-column label="序号" type="index" width="55" align="center" />
        <template v-for="(col, colIndex) in skuDetailTableColumns">
          <el-table-column
            v-if="col.prop.includes('Price')"
            :key="colIndex"
            :prop="col.prop"
            :label="col.label"
          >
            <template slot-scope="scope">
              {{ $number2money(scope.row[col.prop]) }}
            </template>
          </el-table-column>
          <el-table-column v-else :key="colIndex" :prop="col.prop" :label="col.label" />
        </template>
      </el-table>
      <BjDivider>{{ TITLE_TEXT }}商品图片</BjDivider>
      <el-row>
        <el-col>
          <el-form-item label="素材名称">
            {{ skuInfo.materialName }}
          </el-form-item>
          <el-form-item label="主图">
            <div class="flex">
              <BjViewUrl
                v-for="(url, i) in skuInfo.materialMainImages || []"
                :key="i"
                :url="url"
                class="flex-item small-img"
              />
            </div>
          </el-form-item>
          <el-form-item label="详情图">
            <div class="flex">
              <BjViewUrl
                v-for="(url, i) in skuInfo.imageList || []"
                :key="i"
                :url="url"
                class="flex-item small-img"
              />
            </div>
          </el-form-item>
        </el-col>
      </el-row>
      <!--  0草稿1审核中 2被驳回 3已生效 4已生效(修改中)  -->
      <template v-if="isView && skuInfo.status != 0">
        <BjDivider>审核详情</BjDivider>

        <el-row>
          <el-col :span="24">
            <el-form-item label="审核状态">
              {{ GOODS_SKU_VIEW_AUDIT_STATUS.bjGet(skuInfo.status) }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="审核意见"> {{ skuInfo.auditIdea }} </el-form-item>
          </el-col>
        </el-row>
      </template>
    </el-form>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { goodsApi } from '@/api';
import {
  GOODS_ATTRIBUTE_SOURCES_JSON,
  GOODS_SKU_VIEW_AUDIT_STATUS,
  GOODS_COMBO_TYPES,
} from '@/utils/constants';
import AttributeInput from './AttributeInput.vue';
import DefaultAttributeList from '@/views/center-business/goods/components/DefaultAttributeList.json';
const DEFAULT_SKU_COLUMNS = [
  {
    prop: 'skuName',
    label: 'SKU名称',
  },
  {
    prop: 'skuCode',
    label: 'SKU编码',
  },
];
const DEFAULT_SPU_COLUMNS = [
  {
    prop: 'spuName',
    label: '商品名称',
  },
  {
    prop: 'spuCode',
    label: '商品编码',
  },
];

export default {
  name: 'BusinessGoodsSkuOrdinaryViewForm',
  components: {
    AttributeInput,
  },
  mixins: [viewMiXin],
  props: {
    isAdd: {
      type: Boolean,
      default: false,
    },
    isEdit: {
      type: Boolean,
      default: false,
    },
    isView: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {
          mainImage: [],
          imageList: [],
        };
      },
    },
    parentName: {
      type: String,
      default: '',
    },
    // TODO: 兼容套餐SKU
    setFlag: {
      type: [String, Number],
      default: null,
    },
  },
  data() {
    return {
      GOODS_SKU_VIEW_AUDIT_STATUS,
      GOODS_COMBO_TYPES,
      DefaultAttributeList,
      /** 选择的商品信息 */
      skuInfo: {
        activityPrice: null,
        categoryName: null,
        clearingPrice: null,
        costPrice: null,
        distributionPrice: null,
        goodsRemark: null,
        id: null,
        marketPrice: null,
        materialId: null,
        materialMainImages: [],
        otherAttributeList: [],
        salesPrice: null,
        showFlag: null,
        skuAttributeList: [],
        skuCode: null,
        skuName: null,
        spuCode: null,
        spuId: null,
        spuName: null,
        status: null,
        wholesalePrice: null,
      },
      priceCols: [],
      labelWidth: 8, // 与价格项名称的长度动态关联，单位em
    };
  },
  computed: {
    SELECTED_BJ_EBC_ID() {
      return this.selectedRow.map(e => e.BJ_EBC_ID);
    },
    LIST() {
      const lo = this.skuInfo.otherAttributeList.length;

      console.log([...this.skuInfo.otherAttributeList, ...this.skuInfo.skuAttributeList]);

      const ARR = [...this.skuInfo.otherAttributeList, ...this.skuInfo.skuAttributeList].map(
        (e, index) => {
          const listName = index < lo ? 'otherAttributeList' : 'skuAttributeList';
          const listIndex = index < lo ? index : index - lo;
          return {
            label: e.attributeName,
            value: e.attributeValue,
            isSku: e.isSku,
            type: e.attributeType,
            listIndex,
            listName,
          };
        },
      );
      console.log('AttributeList', ARR);
      return {
        SPU: ARR.filter(e => e.type == GOODS_ATTRIBUTE_SOURCES_JSON.SPU),
        CLASSIFY: ARR.filter(e => e.type == GOODS_ATTRIBUTE_SOURCES_JSON.CLASSIFY),
        ATTACHED: ARR.filter(e => e.type == GOODS_ATTRIBUTE_SOURCES_JSON.ATTACHED),
      };
    },

    // 是否为套餐SKU TODO: 兼容套餐SKU
    isPackAgeSku() {
      return this.setFlag == GOODS_COMBO_TYPES.PACKAGE;
    },
    TITLE_TEXT() {
      return this.isPackAgeSku ? '套餐' : '';
    },
    // TODO: 兼容套餐SKU，套餐SKU的明细，表头
    skuDetailTableColumns() {
      const DETAIL_COLUMNS = [];
      // SKU_CODE SKU_NAME
      DETAIL_COLUMNS.push(
        ...[...DEFAULT_SPU_COLUMNS, ...DEFAULT_SKU_COLUMNS].map(e => {
          return { ...e, isCheck: true };
        }),
      );
      // 价格 成本价/销售价
      DETAIL_COLUMNS.push(
        ...this.priceCols.map(e => {
          return { ...e, isCheck: true };
        }),
      );
      DETAIL_COLUMNS.push({
        prop: 'num',
        label: '搭配数量',
        isCheck: true,
      });
      return DETAIL_COLUMNS;
    },
  },
  watch: {},
  mounted() {
    if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
      this.pageName = 'BusinessGoodsSkuOrdinaryIndex';
    }
    if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
      this.pageName = 'BusinessGoodsSkuPackageIndex';
    }
    if (this.$route.query?.setFlag == 'draft') {
      this.pageName = 'BusinessGoodsSkuDraftIndex';
    }
    this.getPriceSetting();
    this.reFresh();
  },
  methods: {
    reFresh() {
      const API_DATA = { id: this.$route.query.id };
      if (this.isEdit) {
        API_DATA['isEdit'] = true;
      }
      goodsApi.detailSku(API_DATA).then(res => {
        this.skuInfo = {
          ...res,
          skuAttributeList: res.skuAttributeList || [],
          otherAttributeList: res.otherAttributeList || [],
        };
      });
    },
    // 获取价格项
    getPriceSetting() {
      /** *  表头 *****/
      goodsApi.getListPriceItem().then(res => {
        this.priceCols = (res || [])
          .filter(e => e.enabledFlag == 1)
          .map(e => {
            const L = (e.meaning || '').length + 4;
            this.labelWidth = Math.max(this.labelWidth, L);

            return {
              prop: e.value,
              label: e.meaning,
            };
          });
      });
    },
    /**
     * flag (boolean, optional): true保存为草稿 false提交 ,
     */
    handleConfirm(flag) {
      console.log(this.skuInfo);
      goodsApi
        .updateSku({
          flag,
          sku: this.skuInfo,
        })
        .then(res => {
          if (!res.failed) {
            this.msgSuccess('保存成功');
            this.complete();
          }
        });
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
/** 附加属性 新增 */
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.sku-attribute-title {
  font-size: 14px;
  font-weight: bold;
}
.sku-attribute-title::after {
  content: '\ff1a';
}
</style>
