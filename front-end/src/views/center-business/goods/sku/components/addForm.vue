<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >{{ TITLE_TEXT }}基础信息
      <template v-slot:button>
        <el-button v-if="isAdd" type="primary" class="xs" @click="$refs.dialogList.show()"
          >选择{{ TITLE_TEXT }}商品</el-button
        >
      </template>

      <template slot="right" right-fixed>
        <template v-if="isAdd">
          <el-button @click="tryCancel">取 消</el-button>
          <el-button type="primary" @click="handleConfirm(true)">保存为草稿</el-button>
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
    <el-form label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="商品编码">
            {{ goodsInfo.spuCode }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品名称">
            {{ goodsInfo.name }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="商品分类">
            {{ goodsInfo.categoryName }}
          </el-form-item>
        </el-col>
        <el-col v-if="!isPackAgeSku" :span="8">
          <el-form-item label="商品品牌">
            {{ goodsInfo.brandName }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 选完商品 -->
    <template v-if="goodsAttributeKeys.length">
      <BjDivider
        >{{ TITLE_TEXT }}SKU属性<template v-if="!isPackAgeSku" v-slot:tips
          ><i class="red">#</i> 参与生成sku的属性</template
        ></BjDivider
      >

      <el-form label-width="160px">
        <el-row>
          <template v-for="(groupItem, g) in goodsSkuAttributeKeysGroupBySource">
            <el-col :key="g" class="sku-attribute-title" :span="24">{{ groupItem.title }}</el-col>
            <el-col
              v-for="(item, i) in groupItem.list"
              :key="g + '-' + i"
              class="sku-attribute-list"
              :span="24"
            >
              <el-form-item :label="`${item.name}`" :class="{ isSku: item.skuFlag }">
                <AttributeInput
                  v-model="goodsAttributeForm[item.code]"
                  :type="item.formType"
                  :col-prop="item.code"
                  :readonly="isView"
                  :value-list="item.attributeValueList"
                />
              </el-form-item>
            </el-col>
          </template>
          <el-col key="附加属性" class="sku-attribute-title" :span="24">附加属性</el-col>
          <el-col class="sku-attribute-list" :span="24" style="padding-left: 6em">
            <el-tag
              v-for="(attachedItem, index) in attachedAttributeList"
              :key="index"
              closable
              :disable-transitions="false"
              @close="handleAttachedNameClose(attachedItem, index)"
            >
              {{ attachedItem.attributeName }}
            </el-tag>

            <el-input
              v-if="newAttachedInputVisible"
              ref="saveTagInput"
              v-model="newAttachedInputValue"
              :maxlength="MAX_LENGTH.SHORT_NAME"
              class="input-new-tag"
              size="small"
              @keyup.enter.native="handleNewAttachedInputConfirm"
              @blur="handleNewAttachedInputConfirm"
            >
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showNewAttachedInput"
              >+ 添加属性</el-button
            >
          </el-col>
        </el-row>
      </el-form>
    </template>
    <BjDivider
      >新{{ TITLE_TEXT }}SKU列表
      <template v-slot:button>
        <el-button type="primary" @click="createNewSkuList">{{
          isPackAgeSku ? '生成套餐' : '生成SKU列表'
        }}</el-button>
        <el-button
          :disabled="SELECTED_BJ_EBC_ID.length == 0"
          type="primary"
          @click="deleteNewSkuList"
          >批量删除{{ TITLE_TEXT }}SKU</el-button
        >
      </template>
      <template v-slot:right>
        <el-button
          :disabled="SELECTED_BJ_EBC_ID.length == 0"
          type="primary"
          class="xs"
          @click="toSetSkuValue"
          >批量设置属性</el-button
        >
        <el-button type="primary" class="xs" @click="showColumnSettingFn">设置属性展示</el-button>
      </template>
    </BjDivider>
    <el-table
      ref="multipleTable"
      :data="newSkuList"
      border
      :header-cell-style="$rowStyle.DEFAULT"
      :default-expand-all="isPackAgeSku"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="55" align="center" />
      <!-- 展开表单 -->
      <el-table-column type="expand" :label="isPackAgeSku ? '明细' : '更多'">
        <template slot-scope="props">
          <div class="expand-content-box">
            <!-- TODO: 兼容套餐SKU 手动展开属性 1 -->
            <BjDivider v-if="isPackAgeSku" size="mini"
              >更多属性
              <template v-slot:button>
                <el-button type="primary" class="xs" @click="setExpandForm(props.row)">{{
                  expandFormVisibleMap[props.row.BJ_EBC_ID] ? '收起属性' : '展开属性'
                }}</el-button>
              </template>
            </BjDivider>
            <!-- TODO: 兼容套餐SKU 手动展开属性 2 -->
            <el-form
              v-show="!isPackAgeSku || expandFormVisibleMap[props.row.BJ_EBC_ID]"
              class="expand-form"
              :label-width="labelWidth + 'em'"
            >
              <el-row>
                <el-col v-for="(col, iii) in skuTableAllColumns_hide" :key="iii" :span="12">
                  <el-form-item :label="col.label">
                    <span v-if="isView">{{ props.row[col.prop] }}</span>
                    <AttributeInput
                      v-else
                      v-model="props.row[col.prop]"
                      :readonly="isView"
                      :is-price="String(col.prop).includes('Price')"
                      :col-prop="col.prop"
                      :type="col.formType"
                      :value-list="col.attributeValueList"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <!-- TODO: 兼容套餐SKU 套餐需要另外添加SKU明细  skuDetailList -->
            <div v-if="isPackAgeSku" class="expand-table">
              <BjDivider size="mini"
                >SKU明细
                <template v-slot:button>
                  <el-button
                    type="primary"
                    class="xs"
                    :disabled="
                      !(
                        skuDetailListSelectedMap[props.row.BJ_EBC_ID] &&
                        skuDetailListSelectedMap[props.row.BJ_EBC_ID].length
                      )
                    "
                    @click="handleDeleteSelectedSkuDetailList(props.row, props.$index)"
                    >批量删除明细</el-button
                  >
                  <el-button
                    type="primary"
                    class="xs"
                    @click="onAddSkuDetail(props.row, props.$index)"
                    >添加SKU明细</el-button
                  >
                </template>
              </BjDivider>
              <el-table
                ref="multipleTable"
                :data="props.row.skuDetailList"
                border
                :header-cell-style="$rowStyle.DEFAULT"
                @selection-change="handleSkuDetailSelectionChange($event, props.row, props.$index)"
              >
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序号" type="index" width="55" align="center" />
                <template v-for="(col, colIndex) in skuDetailTableColumns">
                  <el-table-column
                    v-if="col.prop == 'num'"
                    :key="colIndex"
                    :prop="col.prop"
                    :label="col.label"
                    ><template slot-scope="scope">
                      <BjInputNumber v-model="scope.row[col.prop]" :min="1" /> </template
                  ></el-table-column>

                  <el-table-column
                    v-else-if="String(col.prop).includes('Price')"
                    :key="colIndex"
                    :prop="col.prop"
                    :label="col.label"
                    ><template slot-scope="scope">
                      {{ $number2money(scope.row[col.prop]) }}
                    </template></el-table-column
                  >

                  <el-table-column v-else :key="colIndex" :prop="col.prop" :label="col.label" />
                </template>
              </el-table>
            </div>
          </div>
        </template>
      </el-table-column>
      <!-- 表格行 -->
      <template v-for="(col, iii) in skuTableAllColumns_show">
        <el-table-column :key="iii" :prop="col.prop" :label="col.label">
          <!-- // TODO: 兼容套餐SKU，SKU名称 可编辑 -->
          <template
            v-if="col.prop === 'skuCode' || (col.prop == 'skuName' && isPackAgeSku)"
            slot="header"
          >
            <span class="require">{{ col.label }}</span>
          </template>
          <template slot-scope="props">
            <!-- 表格行 不可编辑的列：生成sku的属性 和 SKU名称 -->
            <!-- // TODO: 兼容套餐SKU，SKU名称 可编辑  -->
            <span v-if="isView || col.skuFlag || (col.prop == 'skuName' && !isPackAgeSku)">{{
              props.row[col.prop]
            }}</span>
            <BjInputCode
              v-else-if="col.prop === 'skuCode'"
              v-model="props.row[col.prop]"
              :readonly="isView"
            />
            <!-- // TODO: 兼容套餐SKU，套餐成本价 = 套餐内SKU成本价之和  -->
            <span v-else-if="col.prop == 'costPrice' && isPackAgeSku">{{
              props.row[col.prop] || '0.00'
            }}</span>

            <AttributeInput
              v-else
              v-model="props.row[col.prop]"
              select
              :readonly="isView"
              :is-price="String(col.prop).includes('Price')"
              :col-prop="col.prop"
              :type="col.formType"
              :value-list="col.attributeValueList"
            />
          </template>
        </el-table-column>
      </template>
    </el-table>

    <template v-if="oldSkuList && oldSkuList.length">
      <!-- 已有SKU属性展示，不做修改 -->
      <BjDivider>已有{{ TITLE_TEXT }}SKU列表</BjDivider>
      <el-table
        ref="multipleTable"
        :data="oldSkuList"
        border
        :header-cell-style="$rowStyle.DEFAULT"
        row-key="id"
      >
        <el-table-column label="序号" type="index" width="55" align="center" />
        <!-- 展开表单 -->
        <el-table-column type="expand" label="更多">
          <template slot-scope="props">
            <div class="expand-content-box">
              <!-- TODO: 兼容套餐SKU 手动展开属性 1 -->
              <BjDivider v-if="isPackAgeSku" size="mini">更多属性 </BjDivider>
              <el-form label-position="left" inline class="expand-content-box">
                <el-form-item
                  v-for="(item, iii) in oldSkuColumns.filter(e => !e.isCheck)"
                  :key="iii"
                  :label="item.label"
                >
                  <span v-if="String(item.prop).includes('Price')">
                    {{ $number2money(item[item.prop]) }}
                  </span>

                  <span v-else>{{ props.row[item.prop] }}</span>
                </el-form-item>
              </el-form>
              <!-- TODO: 兼容套餐SKU 套餐需要另外显示SKU明细  skuDetailList -->
              <div v-if="isPackAgeSku" class="expand-table">
                <BjDivider size="mini">SKU明细</BjDivider>
                <el-table
                  ref="multipleTable"
                  :key="props.$index"
                  :data="props.row.skuDetailList"
                  border
                  :header-cell-style="$rowStyle.DEFAULT"
                  row-key="id"
                >
                  <el-table-column label="序号" type="index" width="55" align="center" />
                  <template v-for="(col, colIndex) in skuDetailTableColumns">
                    <el-table-column
                      v-if="String(col.prop).includes('Price')"
                      :key="colIndex"
                      :prop="col.prop"
                      :label="col.label"
                      ><template slot-scope="scope">
                        {{ $number2money(scope.row[col.prop]) }}
                      </template></el-table-column
                    >
                    <el-table-column v-else :key="colIndex" :prop="col.prop" :label="col.label" />
                  </template>
                </el-table>
              </div>
            </div>
          </template>
        </el-table-column>
        <!-- 表格行 -->
        <template v-for="(col, colIndex) in oldSkuColumns.filter(e => e.isCheck)">
          <el-table-column
            v-if="String(col.prop).includes('Price')"
            :key="colIndex"
            :prop="col.prop"
            :label="col.label"
            ><template slot-scope="scope">
              {{ $number2money(scope.row[col.prop]) }}
            </template></el-table-column
          >

          <el-table-column v-else :key="colIndex" :prop="col.prop" :label="col.label" />
        </template>
      </el-table>
    </template>

    <BjDivider>{{ TITLE_TEXT }}商品图片</BjDivider>
    <el-row>
      <el-col>
        <el-form>
          <el-form-item label="素材名称" required>
            {{ goodsInfo.materialName }}
          </el-form-item>
          <el-form-item label="主图">
            <div class="flex">
              <BjViewUrl
                v-for="(url, i) in goodsInfo.mainImage || []"
                :key="i"
                :url="url"
                class="flex-item small-img"
              />
            </div>
          </el-form-item>
          <el-form-item label="详情图">
            <div class="flex">
              <BjViewUrl
                v-for="(url, i) in goodsInfo.imageList || []"
                :key="i"
                :url="url"
                class="flex-item small-img"
              />
            </div>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <template v-if="isView">
      <!-- TODO: 待对接 -->
      <BjDivider>审核</BjDivider>
      <el-form>
        <el-row>
          <el-col :span="4">
            <el-form-item label="一次审核"> 已通过 </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="审核意见"> 挺好的 </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="二次审核"> 被驳回 </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="审核意见"> 请修改价格 </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </template>

    <!-- 商品 -->
    <BjDialogList
      ref="dialogList"
      :config="
        setFlag == GOODS_COMBO_TYPES.ORDINARY
          ? $BjDialogListConfig.spuOrdinary()
          : $BjDialogListConfig.spuPackage()
      "
      @confirm="onConfirmDialogList"
    />

    <!-- SKU -->
    <BjDialogList
      ref="skuDialogList"
      :config="$BjDialogListConfig.skuDefault()"
      multiple
      @confirm="onConfirmSkuDialogList"
    />

    <!-- 批量设置SKU的属性 -->
    <DataForm
      ref="DataForm"
      :label-width="labelWidth + 'em'"
      :is-pack-age-sku="isPackAgeSku"
      @handleDataFormConfirm="handleDataFormConfirm"
    />

    <!-- 设置显示的表格列 -->
    <ColumnSetting ref="ColumnSetting" @handleColumn="setSkuTableAllColumns" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { goodsApi } from '@/api';
import {
  INPUT_BOX_TYPE_JSON,
  GOODS_ATTRIBUTE_SOURCES_JSON,
  GOODS_COMBO_TYPES,
} from '@/utils/constants';
import { cartesianProductOf, randomID, isSameArray } from '@/utils/index';
import DataForm from './DataForm.vue';
import ColumnSetting from './ColumnSetting.vue';
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
  name: 'BusinessGoodsSkuOrdinaryAddForm',
  components: {
    DataForm,
    ColumnSetting,
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
      default: GOODS_COMBO_TYPES.ORDINARY,
    },
  },
  data() {
    return {
      GOODS_COMBO_TYPES,
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
      goodsAttributeForm: {}, // 表单 {属性code:值}
      /** SKU LIST */
      // oldSkuList: [],
      newSkuList: [],
      selectedRow: [],
      /** SKU 表头 */
      // skuTableColumns_sys:[],//默认的系统属性
      skuTableColumns_sku: [], // 参与sku属性的
      skuTableColumns_price: [], // 价格项
      skuTableColumns_normal: [], // 不参与sku属性的
      // skuTableColumns_attached: [], //附加属性

      skuTableAllColumns_show: [],
      skuTableAllColumns_hide: [],

      /** **  2021.12.27 */
      attachedAttributeList: [],
      newAttachedInputVisible: false,
      newAttachedInputValue: null,

      goodsAttributeKeys: [], // 商品的属性集合（分类属性、商品属性），从接口获取
      resOldSkuList: [], // 返回的旧表格数据

      labelWidth: 8, // 与价格项名称的长度动态关联，单位em

      // TODO: 兼容套餐SKU，
      expandFormVisibleMap: {},
      actionSkuDetailIndex: 0, // 正操作的SKU明细
      skuDetailListSelectedMap: {}, // SKU明细 选中项   {BJ_EBC_ID:selectionArray}
    };
  },
  computed: {
    // 是否为套餐SKU TODO: 兼容套餐SKU
    isPackAgeSku() {
      return this.setFlag == GOODS_COMBO_TYPES.PACKAGE;
    },
    TITLE_TEXT() {
      return this.isPackAgeSku ? '套餐' : '';
    },
    SELECTED_BJ_EBC_ID() {
      return this.selectedRow.map(e => e.BJ_EBC_ID);
    },
    // 表头——附加属性
    skuTableColumns_attached() {
      return this.attachedAttributeList.map(e => {
        return {
          prop: e.attributeName,
          label: e.attributeName,
          attributeType: GOODS_ATTRIBUTE_SOURCES_JSON.ATTACHED,
          attributeId: null,
        };
      });
    },
    // 表头——系统属性
    skuTableColumns_sys() {
      return DefaultAttributeList.map(e => {
        return {
          prop: e.code,
          label: e.name,
          attributeType: GOODS_ATTRIBUTE_SOURCES_JSON.SYS,
          attributeId: null,
        };
      });
    },
    // 表头——选中的sku属性
    skuTableColumns_sku_selected() {
      return this.skuTableColumns_sku.filter(col => {
        const KEY = col.prop;
        const VALUE = this.goodsAttributeForm[KEY];
        if (Array.isArray(VALUE)) {
          return VALUE.length > 0;
        } else {
          return !!VALUE;
        }
      });
    },
    // 新SKU列表 的 默认表头【默认分isCheck，用于展开行 和 显示列设置】
    skuTableAllColumns() {
      const showCol = []
        .concat(this.skuTableColumns_sku_selected)
        .concat(DEFAULT_SKU_COLUMNS)
        .concat(this.skuTableColumns_price.slice(0, 2))
        .concat(this.skuTableColumns_attached)

        .map(e => {
          return {
            ...e,
            isCheck: true,
          };
        });
      const hideCol = []
        .concat(this.skuTableColumns_sys)
        .concat(this.skuTableColumns_price.slice(2))
        .concat(this.skuTableColumns_normal)
        .map(e => {
          return {
            ...e,
            isCheck: false,
          };
        });
      return showCol.concat(hideCol);
    },
    // 新SKU列表的 实际表头 （skuTableAllColumns 修改 isCheck后的）
    newSkuTableAllColumns() {
      return [].concat(this.skuTableAllColumns_show).concat(this.skuTableAllColumns_hide);
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
        ...this.skuTableColumns_price.map(e => {
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
    // 旧表头（固定不变）
    oldSkuColumns() {
      const list = this.resOldSkuList;
      const skuAttributeList = list[0].skuAttributeList || [];
      const otherAttributeList = list[0].otherAttributeList || [];
      const OLD_COLUMNS = [];
      // 组成SKU的
      OLD_COLUMNS.push(
        ...skuAttributeList.map(e => {
          return { prop: e.attributeName, label: e.attributeName, isCheck: true };
        }),
      );
      // SKU_CODE SKU_NAME
      OLD_COLUMNS.push(
        ...DEFAULT_SKU_COLUMNS.map(e => {
          return { ...e, isCheck: true };
        }),
      );
      // 价格 成本价/销售价
      OLD_COLUMNS.push(
        ...this.skuTableColumns_price.slice(0, 2).map(e => {
          return { ...e, isCheck: true };
        }),
      );
      //  附加的  attributeType= GOODS_ATTRIBUTE_SOURCES_JSON.ATTACHED
      OLD_COLUMNS.push(
        ...otherAttributeList
          .filter(e => e.attributeType == GOODS_ATTRIBUTE_SOURCES_JSON.ATTACHED)
          .map(e => {
            return { prop: e.attributeName, label: e.attributeName, isCheck: true };
          }),
      );
      // 系统属性
      OLD_COLUMNS.push(
        ...this.skuTableColumns_sys.map(e => {
          return { ...e, isCheck: false };
        }),
      );
      // 价格其它
      OLD_COLUMNS.push(
        ...this.skuTableColumns_price.slice(2).map(e => {
          return { ...e, isCheck: false };
        }),
      );
      // 不组成SKU的  attributeType= 0 / 2
      OLD_COLUMNS.push(
        ...otherAttributeList
          .filter(e => e.attributeType != GOODS_ATTRIBUTE_SOURCES_JSON.ATTACHED)
          .map(e => {
            return { prop: e.attributeName, label: e.attributeName, isCheck: false };
          }),
      );
      return OLD_COLUMNS;
    },
    // 旧数据
    oldSkuList() {
      if (this.resOldSkuList.length) {
        console.log('resOldSkuList', this.resOldSkuList);
        // 转列表
        return this.resOldSkuList.map(e => {
          const TEMP_JSON = {};
          (e.skuAttributeList || []).forEach(o => {
            TEMP_JSON[o.attributeName] = o.attributeValue;
          });
          (e.otherAttributeList || []).forEach(o => {
            TEMP_JSON[o.attributeName] = o.attributeValue;
          });
          return {
            ...e,
            ...TEMP_JSON,
          };
        });
      }
      return [];
    },
    // 选中的SKU属性和属性值 变为 二维数组，为生成SKU作准备
    selectedSkuList() {
      const TEMP = [];
      this.skuTableColumns_sku_selected.forEach(col => {
        const KEY = col.prop;
        const NAME = col.label;
        const TYPE = col.attributeType;
        const VALUE = this.goodsAttributeForm[KEY];
        console.log('prop,label', col, VALUE);
        if (Array.isArray(VALUE)) {
          TEMP.push(
            VALUE.map(e => {
              return {
                attributeId: KEY,
                attributeType: TYPE,
                attributeName: NAME,
                attributeValue: e,
              };
            }),
          );
        } else {
          TEMP.push([
            {
              attributeId: KEY,
              attributeType: TYPE,
              attributeName: NAME,
              attributeValue: VALUE,
            },
          ]);
        }
      });
      return TEMP;
    },
    // 可生成SKU的属性集
    goodsSkuAttributeKeys() {
      return this.goodsAttributeKeys.filter(e => e.skuFlag);
    },
    // 可生成SKU的属性集 根据来源分类
    goodsSkuAttributeKeysGroupBySource() {
      if (this.isPackAgeSku) return []; // TODO: 兼容套餐SKU，所有的属性都 [不] 参与生成SKU，只可手动生成
      return [
        {
          title: '商品属性',
          list: this.goodsSkuAttributeKeys.filter(
            e => e.source == GOODS_ATTRIBUTE_SOURCES_JSON.SPU,
          ),
        },
        {
          title: '分类属性',
          list: this.goodsSkuAttributeKeys.filter(
            e => e.source == GOODS_ATTRIBUTE_SOURCES_JSON.CLASSIFY,
          ),
        },
      ];
    },
  },
  watch: {
    skuTableAllColumns: {
      handler(d) {
        this.setSkuTableAllColumns(d);
      },
      deep: true,
      immediate: true,
    },
    newSkuList: {
      handler(d) {
        // TODO: 兼容套餐SKU，套餐成本价 = 套餐内SKU成本价之和
        if (this.isPackAgeSku && Array.isArray(d) && d.length) {
          d.forEach(e => {
            const DETAIL_LIST = e.skuDetailList;
            let CONST_PRICE = 0;
            if (Array.isArray(DETAIL_LIST)) {
              DETAIL_LIST.forEach(ee => {
                CONST_PRICE += Number(ee.costPrice || 0) * Number(ee.num || 1);
              });
            }
            e.costPrice = this.$number2money(CONST_PRICE);
          });
        }
      },
      deep: true,
      immediate: true,
    },
  },
  mounted() {
    if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
      this.pageName = 'BusinessGoodsSkuOrdinaryIndex';
    }
    if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
      this.pageName = 'BusinessGoodsSkuPackageIndex';
    }
    this.getPriceSetting();
  },
  methods: {
    handleDeleteSelectedSkuDetailList(parentRow, parentIndex) {
      console.log(...arguments);
      const KEY = parentRow.BJ_EBC_ID;
      const selectedRows = this.skuDetailListSelectedMap[KEY];
      if (Array.isArray(selectedRows) && selectedRows.length) {
        this.$confirm('确定批量删除明细?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          selectedRows.forEach(row => {
            const index = parentRow.skuDetailList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
            if (index > -1) parentRow.skuDetailList.splice(index, 1);
          });
        });
      } else {
        this.$message.warning('请选择');
      }
    },
    handleSkuDetailSelectionChange(selectedRows, parentRow, parentIndex) {
      console.log(...arguments);
      this.$set(this.skuDetailListSelectedMap, parentRow.BJ_EBC_ID, selectedRows);
    },
    // TODO: 兼容套餐SKU，二次展开表单
    setExpandForm(row) {
      const flag = !!this.expandFormVisibleMap[row.BJ_EBC_ID];
      this.$set(this.expandFormVisibleMap, row.BJ_EBC_ID, !flag);
    },
    /** ********* 列的显示设置 *********** */
    showColumnSettingFn() {
      this.$refs.ColumnSetting.show(
        this.newSkuTableAllColumns.map(e => {
          return {
            ...e,
            //  不可隐藏的列：生成sku的属性 /  SKU名称 / skuCode / 成本价 / 销售价
            disabled:
              e.skuFlag || ['skuCode', 'skuName', 'costPrice', 'salesPrice'].includes(e.prop),
          };
        }),
      );
    },
    setSkuTableAllColumns(d) {
      this.skuTableAllColumns_show = d.filter(e => e.isCheck);
      this.skuTableAllColumns_hide = d.filter(e => !e.isCheck);
    },
    /** ****** 添加 附加属性 */
    handleAttachedNameClose(item, index) {
      this.attachedAttributeList.splice(index, 1);
    },
    showNewAttachedInput() {
      this.newAttachedInputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleNewAttachedInputConfirm() {
      const NEW_NAME = this.newAttachedInputValue;
      if (NEW_NAME) {
        this.attachedAttributeList.push({
          attributeName: NEW_NAME,
          attributeType: GOODS_ATTRIBUTE_SOURCES_JSON.ATTACHED,
          attributeValue: null,
        });
      }
      this.newAttachedInputVisible = false;
      this.newAttachedInputValue = '';
    },
    /** **************** */
    /** ******* 生成SKU列表 ************ */
    createNewSkuList() {
      if (!this.goodsInfo || !this.goodsInfo.name) {
        this.$message.warning('请先选择商品');
        return;
      }

      // TODO: 兼容套餐SKU，SKU手动一项项添加，不会自动清空
      if (this.isPackAgeSku) {
        this.doCreateNewSkuList();
        return;
      }

      if (this.newSkuList.length) {
        this.$confirm('是否清空当前SKU列表，并重新生成新的SKU列表？')
          .then(_ => {
            this.doCreateNewSkuList();
          })
          .catch(_ => {});
      } else {
        this.doCreateNewSkuList();
      }
    },
    doCreateNewSkuList() {
      const LIST_TWO = cartesianProductOf(...this.selectedSkuList);

      console.log('this.selectedSkuList', this.selectedSkuList);
      console.log('LIST_TWO', LIST_TWO);

      const TABLE_DATA = [];
      const sameName = [];

      LIST_TWO.forEach(e => {
        let SKU_NAME = e.map(e => e.attributeValue).join('/') || this.goodsInfo.name;

        // TODO: 兼容套餐SKU，SKU名称 默认为空， 仅可手动输入
        if (this.isPackAgeSku) {
          SKU_NAME = '';
        }
        const o = {
          spuId: '',
          skuName: SKU_NAME,
          skuCode: '',
          costPrice: '',
          salesPrice: '',
          activityPrice: '',
          marketPrice: '',
          distributionPrice: '',
          wholesalePrice: '',
          clearingPrice: '',
          weight: '',
          length: '',
          width: '',
          height: '',
          BJ_EBC_ID: randomID(),
          // TODO: 兼容套餐SKU，增加SKU明细
          skuDetailList: [],
        };

        // skuFlag = true  and selected
        e.forEach(item => {
          o[item.attributeId] = item.attributeValue;
        });

        // skuFlag = false
        this.skuTableColumns_normal.forEach(item => {
          o[item.prop] = item.formType == INPUT_BOX_TYPE_JSON.CHECKBOX ? [] : null;
        });

        // attached
        this.skuTableColumns_attached.forEach(item => {
          o[item.prop] = null;
        });
        // 对比旧SKU列表去重
        if (!this.isSameSku(o.skuName)) {
          TABLE_DATA.push(o);
        } else {
          sameName.push(o.skuName);
        }
      });
      if (sameName.length) {
        this.$message.warning(`已经去除重复的SKU：${sameName.join(', ')}`);
      }
      // TODO: 兼容套餐SKU，手动添加一项，不覆盖
      if (this.isPackAgeSku) {
        this.newSkuList = [...TABLE_DATA, ...this.newSkuList];
      } else {
        this.newSkuList = TABLE_DATA;
      }
    },

    /** ********************** */
    deleteNewSkuList() {
      this.$confirm('确定批量删除选中的SKU?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.SELECTED_BJ_EBC_ID.forEach(BJ_EBC_ID => {
          const index = this.newSkuList.findIndex(e => e.BJ_EBC_ID == BJ_EBC_ID);
          if (index > -1) this.newSkuList.splice(index, 1);
        });
      });
    },
    /** ****** 批量设置SKU的属性*********** */
    toSetSkuValue() {
      if (this.SELECTED_BJ_EBC_ID.length == 0) {
        this.$message.warning('请选择SKU');
        return;
      }
      // 批量设置=> 不可编辑的列：生成sku的属性 和 SKU名称 和 skuCode
      this.$refs.DataForm.show(
        this.$lodash.clone(
          this.newSkuTableAllColumns.filter(
            e => !e.skuFlag && e.prop != 'skuName' && e.prop != 'skuCode',
          ),
        ),
      );
    },
    handleDataFormConfirm(data, ignoreEmpty) {
      console.log('ignoreEmpty', data, ignoreEmpty);
      if (ignoreEmpty) {
        for (const key in data) {
          const VALUE = data[key];
          // 空值
          if (!VALUE || (Array.isArray(VALUE) && VALUE.length == 0)) {
            delete data[key];
          }
        }
      }
      this.newSkuList = this.newSkuList.map(e => {
        if (this.SELECTED_BJ_EBC_ID.includes(e.BJ_EBC_ID)) {
          return { ...e, ...data };
        } else {
          return e;
        }
      });
    },
    /** ********************** */

    clearSkuAttribute() {
      this.goodsAttributeForm = {};
      this.skuTableColumns_sku = [];
      this.skuTableColumns_normal = [];
    },
    handleSelectionChange(d) {
      this.selectedRow = [].concat(d);
    },
    // 获取商品spu id
    onConfirmDialogList(data) {
      this.getGoodsInfo(data[0].id);
    },
    // TODO: 兼容套餐SKU 点击 添加SKU明细
    onAddSkuDetail(row, index) {
      this.actionSkuDetailIndex = index;
      this.$refs.skuDialogList.show();
    },
    // TODO: 兼容套餐SKU 返回SKU明细
    onConfirmSkuDialogList(data) {
      const ACTION_ROW = { ...this.newSkuList[this.actionSkuDetailIndex] };
      console.log('ACTION_ROW', ACTION_ROW);
      data.forEach(element => {
        if (ACTION_ROW.skuDetailList.every(e => e.skuCode !== element.skuCode)) {
          ACTION_ROW.skuDetailList.push({ ...element, BJ_EBC_ID: randomID(), num: 1 });
        }
      });
      this.$set(this.newSkuList, this.actionSkuDetailIndex, ACTION_ROW);
    },
    // 获取商品信息
    getGoodsInfo(id) {
      goodsApi
        .detail({ id: id })
        .then(res => {
          this.newSkuList = [];
          this.goodsInfo = res;

          this.goodsAttributeKeys = this.goodsInfo.attrLists.map(item => {
            const ID = item.id || item.code || item.attributeName;
            const CODE = item.code || item.id || item.attributeName;

            return {
              id: ID,
              name: item.attributeName,
              code: CODE,
              formType: item.submissionsType,
              skuFlag: this.isPackAgeSku ? false : item.isSku, // TODO: 兼容套餐SKU，所有的属性都 [不] 参与生成SKU，只可手动生成
              statusFlag: item.status,
              source: item.type,
              attributeValueList: (item.values || []).map(e => {
                return {
                  name: e.value,
                  value: e.id,
                };
              }),
            };
          });

          /** 构建商品属性的表单值 */
          this.clearSkuAttribute();
          this.goodsAttributeKeys.forEach(e => {
            // 创建字典:  { 属性code : 值 }  、
            if (e.skuFlag) {
              this.$set(
                this.goodsAttributeForm,
                e.code,
                e.formType == INPUT_BOX_TYPE_JSON.CHECKBOX ? [] : '',
              );
            }
            // 生成表头
            const COL = {
              attributeId: e.id, // 属性ID
              prop: e.code,
              label: e.name,
              formType: e.formType,
              skuFlag: e.skuFlag,
              attributeType: e.source,
              attributeValueList: e.attributeValueList || [],
            };
            if (e.skuFlag) {
              this.skuTableColumns_sku.push(COL);
            } else {
              this.skuTableColumns_normal.push(COL);
            }
          });
          console.log(' this.skuTableColumns_normal', this.skuTableColumns_normal);

          this.getOldSkuList();
        })
        .catch(err => {
          console.error(err);
        });
    },
    // 根据商品id获取已经生成的sku
    getOldSkuList() {
      goodsApi
        .getOldSkuListBySpuId({ id: this.goodsInfo.id })
        .then(res => {
          /** 旧的SKU数据 */
          this.resOldSkuList = res || [];
        })
        .catch(err => {
          console.error(err);
        });
    },
    // 获取价格项
    getPriceSetting() {
      /** *  表头 *****/
      goodsApi.getListPriceItem().then(res => {
        this.skuTableColumns_price = (res || [])
          .filter(e => e.enabledFlag == 1)
          .map(e => {
            const L = (e.meaning || '').length + 4;
            this.labelWidth = Math.max(this.labelWidth, L);
            return {
              prop: e.value,
              label: e.meaning,
              attributeId: null,
              attributeType: null,
            };
          });
      });
    },
    /**
     * flag (boolean, optional): true保存为草稿 false提交 ,
     */
    handleConfirm(flag) {
      if (this.newSkuList.some(e => !e.skuCode)) {
        this.$message.warning('SKU编码不能为空');
        return;
      }

      if (this.newSkuList.some(e => !e.skuName)) {
        this.$message.warning('SKU名称不能为空');
        return;
      }

      if (this.newSkuList.some(e => e.skuDetailList.some(ee => !ee.num))) {
        this.$message.warning('搭配数量必须大于0');
        return;
      }

      const API_DATA = {
        flag: flag,
        skuList: [],
        // TODO: 兼容套餐SKU，增加 组合类型
        setFlag: this.setFlag,
      };
      API_DATA['skuList'] = this.newSkuList.map(e => {
        const temp = {
          spuId: this.goodsInfo.id,
          skuAttributeList: [],
          otherAttributeList: [],
          // TODO: 兼容套餐SKU，增加SKU明细
          skuDetailList: (e.skuDetailList || []).map(e => {
            return {
              id: null,
              num: e.num,
              packageSkuId: e.id,
              skuId: null,
            };
          }),
        };

        for (const key in e) {
          const VALUE = Array.isArray(e[key]) ? e[key].join() : e[key];

          // 基本固定属性
          if (
            [
              ...DEFAULT_SKU_COLUMNS,
              ...this.skuTableColumns_price,
              ...this.skuTableColumns_sys,
            ].find(e => e.prop == key)
          ) {
            temp[key] = VALUE;
          }
          // 动态SKU属性
          const skuFind = [...this.skuTableColumns_sku_selected].find(ee => ee.prop == key);
          if (skuFind) {
            /**
             * skuFind
             * attributeId: （…）
             * attributeType: （…）
             * attributeValueList: （…）
             * formType: （…）
             * label: （…）
             * prop: （…）
             * skuFlag: （…）
             */
            temp.skuAttributeList.push({
              attributeId: skuFind.id,
              attributeType: skuFind.attributeType,
              attributeName: skuFind.label,
              attributeValue: VALUE,
            });
          }
          // 动态其它属性
          const otherFind = [...this.skuTableColumns_attached, ...this.skuTableColumns_normal].find(
            ee => ee.prop == key,
          );
          if (otherFind) {
            /**
             * otherFind
             * attributeId: null
             * attributeType: 3
             * label: "234234124"
             * prop: "234234124"
             */
            temp.otherAttributeList.push({
              attributeId: otherFind.id,
              attributeType: otherFind.attributeType,
              attributeName: otherFind.label,
              attributeValue: VALUE,
            });
          }
        }
        return temp;
      });

      goodsApi.addSku(API_DATA).then(res => {
        if (!res.failed) {
          this.msgSuccess('保存成功');
          this.complete();
        }
      });
    },
    /**
     * flag=false : 简单对比skuName，例：111/222/333 ==111/222/333 ，但 111/222/333 != 222/111/333
     * flag=true : 深入对比skuName，例：111/222/333 ==111/222/333 ，且 111/222/333 == 222/111/333
     */
    isSameSku(skuName, flag = false) {
      const TEMP_OLD_LIST = this.resOldSkuList.map(e => e.skuName);
      if (!flag) {
        return TEMP_OLD_LIST.includes(skuName);
      } else {
        if (TEMP_OLD_LIST.includes(skuName)) {
          return true;
        } else {
          const TEMP_SKU_NAME = skuName.split('/');
          return this.resOldSkuList.some(e => {
            return isSameArray(TEMP_SKU_NAME, e.split('/'));
          });
        }
      }
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
  margin-top: 12px;
}
.sku-attribute-title::after {
  content: '\ff1a';
}

.sku-attribute-list .el-form-item {
  margin-bottom: 8px;
}

/* expand-content-box */
.expand-content-box {
  /* font-size: 0;*/
  padding: 0 16px;
  padding-left: 55px;
}
.expand-content-box label {
  color: #99a9bf;
}
.expand-content-box .el-form-item {
  margin-bottom: 8px;
  /* margin-right: 0;
  margin-bottom: 0;
  width: 50%; */
}
</style>
