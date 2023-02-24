<template>
  <div class="app-container">
    <BjDivider right-fixed>
      <span></span>
      <template #right>
        <template v-if="setFlag == GOODS_COMBO_TYPES.ORDINARY && isAudit">
          <el-button
            v-if="pendingAudit"
            v-permission:BusinessGoodsAuditSkuOrdinary_Pass
            type="primary"
            @click="onPass"
            >通 过</el-button
          >
          <el-button
            v-if="pendingAudit"
            v-permission:BusinessGoodsAuditSkuOrdinary_reject
            type="warning"
            @click="onReject"
            >驳 回</el-button
          >
        </template>
        <template v-if="setFlag == GOODS_COMBO_TYPES.PACKAGE && isAudit">
          <el-button
            v-if="pendingAudit"
            v-permission:BusinessGoodsAuditSkuPackage_Pass
            type="primary"
            @click="onPass"
            >通 过</el-button
          >
          <el-button
            v-if="pendingAudit"
            v-permission:BusinessGoodsAuditSkuPackage_reject
            type="warning"
            @click="onReject"
            >驳 回</el-button
          >
        </template>
        <el-button @click="cancel">返 回</el-button>
      </template>
    </BjDivider>
    <el-table :data="[{}]" border :show-header="false" :cell-style="handleCellStyle()">
      <el-table-column :width="labelWidth"> </el-table-column>
      <el-table-column prop="value" align="center">
        <template
          ><h3>{{ data.draftSkuVO ? '修改前' : '商品详情审核' }}</h3></template
        >
      </el-table-column>
      <el-table-column v-if="data.draftSkuVO" prop="newValue" align="center">
        <template> <h3>修改后</h3></template>
      </el-table-column>
    </el-table>

    <BjDivider>基本信息</BjDivider>
    <el-table :data="baseAttribute" border :show-header="false" :cell-style="handleCellStyle">
      <el-table-column prop="name" align="center" :width="labelWidth">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" class="link">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="value" align="center"> </el-table-column>
      <el-table-column v-if="data.draftSkuVO" prop="newValue" align="center">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
            scope.row.newValue
          }}</span>
        </template>
      </el-table-column>
    </el-table>
    <BjDivider>SKU属性</BjDivider>
    <el-table :data="skuAttribute" border :show-header="false" :cell-style="handleCellStyle">
      <el-table-column prop="name" align="center" :width="labelWidth">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
            scope.row.name
          }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="value" align="center"> </el-table-column>
      <el-table-column v-if="data.draftSkuVO" prop="newValue" align="center">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
            scope.row.newValue
          }}</span>
        </template>
      </el-table-column>
    </el-table>
    <BjDivider>SKU价格</BjDivider>
    <el-table :data="skuPrice" border :show-header="false" :cell-style="handleCellStyle">
      <el-table-column prop="name" align="center" :width="labelWidth">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
            scope.row.name
          }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="value" align="center">
        <template slot-scope="scope">
          {{ $number2money(scope.row.value) }}
        </template>
      </el-table-column>
      <el-table-column v-if="data.draftSkuVO" prop="newValue" align="center">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
            $number2money(scope.row.newValue)
          }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!-- 套餐信息 -->
    <BjDivider>SKU 明细</BjDivider>
    <el-table :data="skuDetailList" border :show-header="false" :cell-style="handleCellStyle">
      <el-table-column prop="name" align="center" :width="labelWidth" />
      <el-table-column prop="value" align="center">
        <template slot-scope="scope">
          {{ scope.row.value }}
          <span class="link ml10" @click="onSkuDetail(scope.row.row)">详情</span>
        </template>
      </el-table-column>
      <el-table-column v-if="data.draftSkuVO" prop="newValue" align="center">
        <template slot-scope="scope">
          {{ scope.row.newValue }}
          <span class="link ml10" @click="onSkuDetail(scope.row.newRow)">详情</span>
        </template>
      </el-table-column>
    </el-table>
    <!-- END -->
    <BjDivider>商品素材</BjDivider>
    <el-table :data="material" border :show-header="false" :cell-style="handleCellStyle">
      <el-table-column prop="name" align="center" :width="labelWidth">
        <template slot-scope="scope">
          <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
            scope.row.name
          }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="value" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.value">{{ scope.row.value }}</span>
          <div v-else class="flex">
            <BjViewUrl
              v-for="img in scope.row.imgs"
              :key="img"
              :url="img"
              width="100px"
              class="ml6"
              preview
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="data.draftSkuVO" prop="newValue" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.newValue">
            <span :type="checkData(scope.row) ? color : ''" :underline="false">{{
              scope.row.newValue
            }}</span>
          </span>
          <div v-else class="flex">
            <BjViewUrl
              v-for="(img, index) in scope.row.newImgs"
              :key="index"
              :url="img"
              width="100px"
              class="ml6"
              preview
            />
          </div>
        </template>
      </el-table-column>
    </el-table>
    <BjDivider>审核</BjDivider>
    <el-form>
      <el-form-item label="提交人：">{{
        data.thisSkuVO && data.thisSkuVO.auditCommitName
      }}</el-form-item>
      <el-row v-if="!pendingAudit">
        <el-col :span="4"
          ><el-form-item label="审核状态：">
            {{ GOODS_AUDIT_STATUS_LIST.bjGet($route.query.status) }}</el-form-item
          ></el-col
        >
        <el-col :span="20"
          ><el-form-item label="审核意见：">
            {{ data.thisSkuVO && data.thisSkuVO.auditIdea }}</el-form-item
          ></el-col
        >
      </el-row>
    </el-form>
    <DialogIdea ref="DialogIdea" @confirm="onConfirm" />
    <RowDetailFromSkuDetailList
      ref="RowDetailFromSkuDetailList"
      :cols="skuDetailTableColumns"
      :handle-cell-style="handleCellStyle"
    />
  </div>
</template>

<script>
import viewMiXin from '@/utils/view-mixin.js';
import { goodsApi } from '@/api';
import { GOODS_AUDIT_STATUS_LIST, GOODS_COMBO_TYPES } from '@/utils/constants';
import DialogIdea from './DialogIdea';
import RowDetailFromSkuDetailList from './RowDetailFromSkuDetailList.vue';
import DefaultAttributeList from '@/views/center-business/goods/components//DefaultAttributeList.json';
const defaultFormData = () => {
  return {
    auditIdea: null,
    flag: true,
    id: null,
  };
};
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
export default {
  components: { DialogIdea, RowDetailFromSkuDetailList },
  mixins: [viewMiXin],
  props: {
    // label 宽度
    labelWidth: {
      type: String,
      default: '200px',
    },
    // 文字颜色
    color: {
      type: String,
      default: 'primary',
      validator: function (value) {
        // 这个值必须匹配下列字符串中的一个
        return ['primary', 'success', 'warning', 'danger', 'info'].indexOf(value) !== -1;
      },
    },
    setFlag: {
      type: [String, Number],
      default: GOODS_COMBO_TYPES.ORDINARY,
    },
    isAudit: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      GOODS_AUDIT_STATUS_LIST,
      GOODS_COMBO_TYPES,
      labeWdith: '200px',
      DefaultAttributeList,
      // 基本属性
      baseAttribute: [
        {
          name: 'SKU名称',
          key: 'skuName',
          value: '',
          newValue: '',
        },
        {
          name: 'SKU编码',
          key: 'skuCode',
          value: '',
          newValue: '',
        },
        {
          name: '商品名称',
          key: 'spuName',
          value: '',
          newValue: '',
        },
        {
          name: '商品编码',
          key: 'spuCode',
          value: '',
          newValue: '',
        },
        {
          name: '商品分类',
          key: 'categoryName',
          value: '',
          newValue: '',
        },
        {
          name: '商品品牌',
          key: 'brandName',
          value: '',
          newValue: '',
        },
      ],
      // SKU属性
      skuAttribute: [],
      // SKU价格
      skuPrice: [],
      // 套餐SKU明细
      skuDetailList: [],
      // 素材
      material: [
        {
          name: '素材名称',
          key: 'materialName',
          value: '',
          newValue: '',
        },
        {
          name: '主图',
          key: 'mainImage',
          value: '',
          newValue: '',
          imgs: [],
          newImgs: [],
        },
        {
          name: '详情图',
          key: 'detailImage',
          value: '',
          newValue: '',
        },
        {
          name: '商品描述',
          key: 'detailHtml',
          value: '',
          newValue: '',
        },
      ],
      form: defaultFormData(),
      data: {},
    };
  },
  computed: {
    // TODO: 兼容套餐SKU，套餐SKU的明细，表头
    skuDetailTableColumns() {
      // SKU_CODE SKU_NAME
      const DETAIL_COLUMNS = [...DEFAULT_SKU_COLUMNS];
      // 价格 成本价/销售价
      DETAIL_COLUMNS.push(
        ...this.skuPrice.map(e => {
          return {
            prop: e.key,
            label: e.name,
          };
        }),
      );
      DETAIL_COLUMNS.push({
        prop: 'num',
        label: '搭配数量',
      });
      return DETAIL_COLUMNS;
    },
    pendingAudit() {
      return [2, 4].includes(Number(this.$route.query.status));
    },
  },
  created() {
    // 获取sku固定属性
    this.skuAttribute = DefaultAttributeList.map(item => {
      return {
        key: item.code,
        name: item.name,
        value: null,
        newValue: null,
      };
    });
    const { id } = this.$route.query;
    this.form.id = id;
    this.getData(id);

    if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
      this.pageName = 'BusinessGoodsAuditSkuOrdinaryIndex';
    }
    if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
      this.pageName = 'BusinessGoodsAuditSkuPackageIndex';
    }
  },
  methods: {
    handleCellStyle(arg) {
      if (!arg) {
        return this.$rowStyle.DEFAULT;
      }
      if (arg.columnIndex == 0) {
        return this.$rowStyle.DEFAULT;
      }
      return {};
    },
    onSkuDetail(row) {
      // 展示数据
      this.$refs.RowDetailFromSkuDetailList.show(row);
    },
    async getData(id) {
      goodsApi
        .getSkuAuditDetail({ id })
        .then(res => {
          this.data = res;
          // 赋值修改前后SKU
          const { thisSkuVO, draftSkuVO } = res;
          // 获取基本属性
          this.baseAttribute.forEach(item => {
            item.value = thisSkuVO[item.key];
            if (draftSkuVO) item.newValue = draftSkuVO[item.key];
          });
          // 合并SKU属性
          // 固有属性赋值
          this.skuAttribute.forEach(item => {
            item.value = thisSkuVO[item.key];
            item.newValue = draftSkuVO && draftSkuVO[item.key];
          });
          // 获取sku属性
          const skuAttributeList = (thisSkuVO && thisSkuVO.skuAttributeList) || [];
          const newSkuAttributeList = (draftSkuVO && draftSkuVO.skuAttributeList) || [];
          const attributeList = skuAttributeList.map((item, index) => {
            return {
              key: null,
              name: item.attributeName,
              value: item.attributeValue,
              newValue: newSkuAttributeList[index]?.attributeValue,
            };
          });
          // 合并sku属性
          this.skuAttribute = [...this.skuAttribute, ...attributeList];
          // 获取 SKU价格
          goodsApi.getListPriceItem().then(res => {
            this.skuPrice = (res || []).map(e => {
              return {
                key: e.value,
                name: e.meaning,
                value: thisSkuVO.priceVO[e.value],
                newValue: draftSkuVO && draftSkuVO.priceVO[e.value],
              };
            });
          });
          // 获取 sku套餐明细
          const skuDetailList = (thisSkuVO && thisSkuVO.skuDetailList) || [];
          const newSkuDetailList = (draftSkuVO && draftSkuVO.skuDetailList) || [];

          this.skuDetailList = skuDetailList.map((item, index) => {
            let newValue;
            const newItem = newSkuDetailList[index];

            if (newItem) {
              newValue = `${newItem.skuName} x ${newItem.num}`;
            }
            return {
              key: null,
              name: `SKU${index + 1}`, // SKU1  SKU2
              value: `${item.skuName} x ${item.num}`, // SKU名称  X  搭配数量
              newValue: newValue,
              row: item, // 点击展示用的
              newRow: newItem, // 点击展示用的
            };
          });
          // 获取商品素材
          this.material.forEach(item => {
            // 图片处理成数组
            if (item.key === 'mainImage' || item.key === 'detailImage') {
              item.imgs = thisSkuVO[item.key].split(',');
              item.newImgs = draftSkuVO && draftSkuVO[item.key].split(',');
            } else {
              item.value = thisSkuVO[item.key];
              item.newValue = draftSkuVO && draftSkuVO[item.key];
            }
          });
        })
        .catch(err => {
          console.warn(err);
        });
    },
    /** 通过 */
    onPass() {
      this.form.DialogIdea = null;
      this.form.flag = true;
      this.$refs.DialogIdea.show('审核通过');
    },
    /** 驳回 */
    onReject() {
      this.form.DialogIdea = null;
      this.form.flag = false;
      this.$refs.DialogIdea.show('审核拒绝');
    },
    onConfirm(data) {
      // 赋值审核意见
      this.form.auditIdea = data;
      // 调用审核接口
      goodsApi
        .skuAudit(this.form)
        .then(res => {
          if (!res.failed) {
            this.msgSuccess('操作成功');
            setTimeout(() => {
              this.complete();
            }, 500);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /** 校验数据是否发生变化 */
    checkData(row) {
      if (this.data.draftSkuVO) {
        if (row.value || row.newValue) {
          return row.value !== row.newValue;
        } else {
          return JSON.stringify(row.imgs) !== JSON.stringify(row.newImgs);
        }
      }
      return false;
    },
  },
};
</script>

<style lang="scss" scoped>
.span {
  font-size: 14px;
  cursor: text;
}
</style>
