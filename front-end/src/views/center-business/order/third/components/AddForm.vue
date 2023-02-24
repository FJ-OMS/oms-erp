<!--
 * @Author: your name
 * @Date: 2021-09-29 14:09:55
 * @LastEditTime: 2022-04-27 15:18:06
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\order\internal\add.vue
-->
<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >三方订单基本信息

      <template slot="right">
        <template v-if="!isView">
          <el-button @click="tryCancel">取 消</el-button>
          <el-button type="primary" @click="onConfirm">保 存</el-button>
        </template>
        <template v-if="isView">
          <el-button type="primary" @click="cancel">返 回</el-button>
        </template>
      </template>
    </BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="160px">
      <el-row>
        <el-col :span="colSpan">
          <!-- 1.必填

2.三方订单号加订单类型为唯一，提交时需验证是否有履约中的关联此三方订单号和类型的内部订单，如果有，则提示已经有履约中的内部订单，不可重复创建。 -->
          <el-form-item
            label="三方订单号"
            prop="thirdOrderBaseInfoDTO.outerOrderNo"
            label-width="160px"
          >
            <BjInputCode v-model="form.thirdOrderBaseInfoDTO.outerOrderNo" />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item
            label="订单类型"
            prop="thirdOrderBaseInfoDTO.businessType"
            label-width="160px"
          >
            <BjSelectLov
              v-model="form.thirdOrderBaseInfoDTO.businessType"
              :option-list="ORDER_BUSINESS_TYPE_LIST"
              disabled
              @change="handleBusinessTypeChange"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <BjSelectChannelStore
          :span="colSpan"
          :query-params="form.thirdOrderBaseInfoDTO"
          :query-keys="['channel', 'storeCode']"
          :query-props="['thirdOrderBaseInfoDTO.channel', 'thirdOrderBaseInfoDTO.storeCode']"
          :query-labels="['选择销售渠道', '选择销售店铺']"
          store-option-value="code"
          optional-store
          @handleStoreChange="onChangeStore"
        />
      </el-row>
      <el-row v-if="cptIsO2O">
        <el-col :span="colSpan">
          <!-- 非必填，取销售店铺的关联公司的组织架构下的员工 -->
          <el-form-item
            label="选择销售员工"
            prop="thirdOrderBaseInfoDTO.salesEmployeeId"
            label-width="160px"
          >
            <BjSelectUser
              v-model="form.thirdOrderBaseInfoDTO.salesEmployeeId"
              :default-params="{
                enabled: true,
                locked: false,
                companyId: defaultSalesOrganizingId,
              }"
              :placeholder="form.thirdOrderBaseInfoDTO.storeCode ? '请选择' : '请先选择销售店铺'"
              :disabled="!form.thirdOrderBaseInfoDTO.storeCode"
              @change="onChangeSalesEmployee"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <!-- 1.如果销售店铺是网店，则取网店关联的门店列表。

2.如果销售店铺是门店，则取已启用的且是O2O订单自提门店列表。 -->
          <el-form-item
            label="选择提货店铺"
            prop="thirdOrderBaseInfoDTO.takingDeliveryStoreId"
            label-width="160px"
          >
            <BjSelectStore
              v-model="form.thirdOrderBaseInfoDTO.takingDeliveryStoreId"
              :default-params="{ o2oSalesStoreId: defaultSalesOrganizingId }"
              :placeholder="form.thirdOrderBaseInfoDTO.storeCode ? '请选择' : '请先选择销售店铺'"
              :disabled="!form.thirdOrderBaseInfoDTO.storeCode"
              @change="handleDeliveryStoreChange"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="colSpan">
          <el-form-item
            label="三方订单下单时间"
            prop="thirdOrderBaseInfoDTO.orderTime"
            label-width="160px"
          >
            <el-date-picker
              v-model="form.thirdOrderBaseInfoDTO.orderTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择"
              class="full-width"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="colSpan * 2">
          <el-form-item
            label="商家备注"
            prop="thirdOrderBaseInfoDTO.orderRemarks"
            label-width="160px"
          >
            <BjTextarea v-model="form.thirdOrderBaseInfoDTO.orderRemarks" placeholder="商家备注" />
          </el-form-item>
        </el-col>
      </el-row>

      <BjDivider>买家以及收货信息</BjDivider>

      <el-row v-if="cptIsO2O">
        <el-col :span="colSpan">
          <el-form-item label="提货方式" prop="thirdOrderBaseInfoDTO.takingDeliveryWay">
            <BjRadioLov
              v-model="form.thirdOrderBaseInfoDTO.takingDeliveryWay"
              :option-list="ORDER_TAKING_DELIVERY_TYPE_LIST"
              :disabled="!cptIsO2O"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="买家昵称" prop="thirdOrderBuyerInfoDTO.buyerNick">
            <el-input
              v-model="form.thirdOrderBuyerInfoDTO.buyerNick"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-if="cptIsDoor2Door" :span="colSpan">
          <el-form-item label="收货人" prop="thirdOrderBuyerInfoDTO.receiverName">
            <el-input
              v-model="form.thirdOrderBuyerInfoDTO.receiverName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <BjFormItemMobile
            v-model="form.thirdOrderBuyerInfoDTO.mobile"
            :label="!cptIsDoor2Door ? '买家手机号' : '收货人手机号'"
            prop="thirdOrderBuyerInfoDTO.mobile"
            :required="true"
          />
        </el-col>

        <el-col v-if="cptIsDoor2Door" :span="colSpan * 2">
          <el-form-item label="收货地址" prop="fullAddress">
            <el-col :span="11">
              <BjRegionCascader
                v-model="regionList"
                class="full-width"
                is-array
                clearable
                @change="onChangeRegionCascader"
              />
            </el-col>
            <el-col :span="13">
              <el-input
                v-model.trim="form.thirdOrderBuyerInfoDTO.address"
                :maxlength="MAX_LENGTH.DEFAULT"
                placeholder="详细地址"
                clearable
              />
            </el-col>
          </el-form-item>
        </el-col>

        <el-col :span="colSpan * 2">
          <el-form-item label="买家备注" prop="thirdOrderBuyerInfoDTO.buyerRemarks">
            <BjTextarea v-model="form.thirdOrderBuyerInfoDTO.buyerRemarks" placeholder="买家备注" />
          </el-form-item>
        </el-col>
      </el-row>

      <BjDivider>支付信息</BjDivider>
      <el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="支付方式" prop="thirdOrderPaymentInfoDTO.payType">
              <BjSelectLov
                v-model="form.thirdOrderPaymentInfoDTO.payType"
                :option-list="ORDER_INTERNAL_PAYMENT_LIST"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付金额" prop="thirdOrderPaymentInfoDTO.payAmount"
              ><BjInputMoney v-model="form.thirdOrderPaymentInfoDTO.payAmount"
            /></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="支付状态" prop="thirdOrderBaseInfoDTO.payStatus">
              <BjSelectLov
                v-model="form.thirdOrderBaseInfoDTO.payStatus"
                :option-list="ORDER_THIRD_PAYMENT_STATUS_LIST"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付时间" prop="thirdOrderPaymentInfoDTO.payTime"
              ><el-date-picker
                v-model="form.thirdOrderPaymentInfoDTO.payTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择"
                class="full-width"
            /></el-form-item>
          </el-col>
        </el-row>
      </el-row>
      <!-- TODO: -->
      <BjDivider>优惠信息</BjDivider>
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="三方平台优惠" prop="thirdOrderDiscountInfoDTO.discountFlag">
            <BjRadioLov
              v-model="form.thirdOrderDiscountInfoDTO.discountFlag"
              :option-list="STATUS_HAVE_NUMBER"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-if="form.thirdOrderDiscountInfoDTO.discountFlag" :span="colSpan">
          <el-form-item label="三方平台优惠金额" prop="thirdOrderPaymentInfoDTO.totalDiscount">
            <BjInputMoney
              v-model="form.thirdOrderDiscountInfoDTO.totalDiscount"
              placeholder="三方平台优惠金额"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-col v-if="form.thirdOrderDiscountInfoDTO.discountFlag" :span="colSpan * 2">
        <el-form-item label="优惠承担">
          <el-row>
            <!-- TODO: 合计 total  = third + seller  ??-->
            <el-col :span="10">
              <el-form-item label="" prop="thirdOrderBaseInfoDTO.thirdDiscountAmount">
                <BjInputMoney
                  v-model="form.thirdOrderDiscountInfoDTO.thirdDiscountAmount"
                  placeholder="三方平台承担优惠金额"
                />
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="" prop="thirdOrderBaseInfoDTO.businessDiscountAmount">
                <BjInputMoney
                  v-model="form.thirdOrderDiscountInfoDTO.businessDiscountAmount"
                  placeholder="商家承担优惠金额"
                  class="ml10"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>
      </el-col>

      <BjDivider
        >商品信息

        <template v-slot:button>
          <el-button :disabled="SELECTED_BJ_EBC_ID.length == 0" type="danger" @click="batchDelete"
            >删除</el-button
          >

          <el-button type="primary" @click="addGoodsDialog('dialogListOrdinarySku')"
            >添加商品</el-button
          >
          <!-- <el-button type="primary" @click="addGoodsDialog('dialogListPackAgeSku')"
            >添加套餐商品</el-button
          > -->
        </template>
      </BjDivider>

      <OrderItemTable
        ref="OrderItemTable"
        :table-data.sync="form.thirdOrderItemDtoList"
        @delete="onDelete"
        @selection-change="handleSelectionChange"
      />
      <div class="bottom">
        <!-- 订单应收总金额：等于商品列表三方平台售价之和 -->
        <el-form-item label="订单应收总金额"
          ><span class="flag-one price"
            >¥ {{ $number2money(goodsTotalAmount) || ' 0.00' }}</span
          ></el-form-item
        >

        <!-- TODO: 优惠总金额: 三方平台优惠有时则取三方平台优惠金额，无则为0 -->
        <el-form-item label="优惠总金额"
          ><span class="flag-one price"
            >¥ {{ $number2money(cptOrderDiscountTotal || 0) || ' 0.00' }}</span
          ></el-form-item
        >

        <!-- TODO: 运费总金额: 含运费时则取运费金额，无则为0 -->
        <!-- <el-form-item label="运费总金额"
        ><span class="flag-one price"
          >¥ {{ $number2money(transPriceTotalAmount) || ' 0.00' }}</span
        ></el-form-item
      > -->

        <!-- 买家实付总金额：取支付金额 -->
        <el-form-item label="买家实付总金额"
          ><span class="flag-one price"
            >¥ {{ $number2money(form.thirdOrderPaymentInfoDTO.payAmount) || ' 0.00' }}</span
          ></el-form-item
        >

        <!-- 商品总成本：等于商品列表成本价之和 -->
        <!-- <el-form-item label="商品总成本"
        ><span class="flag-one price"
          >¥ {{ $number2money(costTotalAmount) || ' 0.00' }}</span
        ></el-form-item
      > -->
      </div>
    </el-form>
    <!-- 选择普通商品 -->
    <BjDialogList
      ref="dialogListOrdinarySku"
      width="80%"
      :config="
        $BjDialogListConfig.skuDefault('添加普通商品', {
          defaultQuery: { setFlag: 0, status: [3, 4, 5] },
        })
      "
      multiple
      :disabled="orderDisabledList"
      @confirm="onAddGoodsDialogConfirm"
    />
    <!-- 选择套餐商品 -->
    <!-- <BjDialogList
      ref="dialogListPackAgeSku"
      width="80%"
      :config="$BjDialogListConfig.skuDefault('添加套餐商品', { defaultQuery: { setFlag: 1, status: [3, 4, 5] } })"
      multiple
      :disabled="orderDisabledList"
      @confirm="onAddGoodsDialogConfirm"
    /> -->
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { orderApi } from '@/api';
import BjRegionCascader from '@/components/BjRegionCascader';
import OrderItemTable from './OrderItemTable';
import {
  ORDER_BUSINESS_TYPE_LIST,
  ORDER_INTERNAL_PAYMENT_LIST,
  ORDER_THIRD_PAYMENT_STATUS_LIST,
  STATUS_HAVE_NUMBER,
  STATUS_YES_TRUE,
  ORDER_TAKING_DELIVERY_TYPE_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
import { randomID } from '@/utils/index';
const getDefaultForm = () => {
  return {
    // 订单基础信息
    thirdOrderBaseInfoDTO: {
      actionType: '201', // (string, optional): 订单创建类型 , 手工单
      copyOrigin: null, // (string, optional): 复制来源 ,
      businessType: 'common',
      channel: null,
      orderRemarks: null,
      orderTime: null,
      outerOrderNo: null,
      payStatus: 1,
      storeCode: null,
      storeId: null,
      storeName: null,
      salesEmployeeId: null,
      salesEmployeeRealName: null,
      takingDeliveryStoreId: null,
      takingDeliveryStoreName: null,
      takingDeliveryWay: '2',
    },
    // 订单买家信息
    thirdOrderBuyerInfoDTO: {
      address: null,
      buyerNick: null,
      buyerRemarks: null,
      city: null,
      cityCode: null,
      district: null,
      districtCode: null,
      phone: null,
      mobile: null,
      province: null,
      provinceCode: null,
      receiverName: null,
      town: null,
    },
    // 订单商品详情
    thirdOrderItemDtoList: [],
    // 订单支付信息
    thirdOrderPaymentInfoDTO: {
      payAmount: null,
      payTime: null,
      payType: null,
      payStatus: 1,
      // goodsTotalAmount: null, // 订单应收总金额  取计算属性 goodsTotalAmount
      costTotalAmount: null, // 商品总成本  取计算属性 costTotalAmount
      // flag: false, // 支付金额是否含运费
      // transPrice: null,
    },
    // TODO: 优惠信息
    thirdOrderDiscountInfoDTO: {
      discountFlag: 0,
      totalDiscount: null,
      businessDiscountAmount: null,
      thirdDiscountAmount: null,
    },
  };
};
export default {
  name: 'BusinessOrderThirdAddForm',
  components: { BjRegionCascader, OrderItemTable },
  mixins: [viewMiXin],
  props: {
    businessType: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      colSpan: 9,
      ORDER_BUSINESS_TYPE_LIST,
      ORDER_INTERNAL_PAYMENT_LIST,
      ORDER_THIRD_PAYMENT_STATUS_LIST,
      STATUS_HAVE_NUMBER,
      STATUS_YES_TRUE,
      ORDER_TAKING_DELIVERY_TYPE_LIST,
      form: getDefaultForm(),

      regionList: [],
      orderDisabledList: [],

      selectedRow: [],
      /** 订单应收总金额 */
      shopTotalPrice: 0,
      // 应收总金额
      receiveTotalPrice: 0,

      defaultSalesOrganizingId: null,
    };
  },
  computed: {
    SELECTED_BJ_EBC_ID() {
      return this.selectedRow.map(e => e.BJ_EBC_ID);
    },
    transPriceTotalAmount() {
      let amount = 0;
      this.form.thirdOrderItemDtoList.forEach(row => {
        amount += Number(row.transPrice || 0);
      });
      return amount;
    },
    goodsTotalAmount() {
      let amount = 0;
      this.form.thirdOrderItemDtoList.forEach(row => {
        amount += Number(row.outerSaleAmount || 0);
      });
      return amount;
    },
    costTotalAmount() {
      let amount = 0;
      this.form.thirdOrderItemDtoList.forEach(row => {
        amount += Number(row.costPrice || 0) * Number(row.quantity || 0);
      });
      return amount;
    },
    cptOrderDiscountTotal() {
      if (!this.form.thirdOrderDiscountInfoDTO.discountFlag) return 0;
      return this.form.thirdOrderDiscountInfoDTO.totalDiscount;
    },
    // 表单校验
    rules() {
      const validateFullAddress = (rule, value, callback) => {
        this.$nextTick(() => {
          if (
            !this.form.thirdOrderBuyerInfoDTO.provinceCode ||
            !this.form.thirdOrderBuyerInfoDTO.cityCode ||
            !this.form.thirdOrderBuyerInfoDTO.districtCode
          ) {
            callback(new Error('请选择省/市/区'));
          }
          if (!this.form.thirdOrderBuyerInfoDTO.address) {
            callback(new Error('请输入详细地址'));
          }
          callback();
        });
      };

      return {
        'thirdOrderBaseInfoDTO.channel': [
          { required: true, message: '请选择渠道', trigger: 'change' },
        ],
        'thirdOrderBaseInfoDTO.storeCode': [
          { required: true, message: '请选择店铺', trigger: 'change' },
        ],
        'thirdOrderBaseInfoDTO.outerOrderNo': [
          { required: true, message: '请输入三方订单号', trigger: 'blur' },
        ],
        'thirdOrderBaseInfoDTO.orderTime': [
          { required: true, message: '请选择三方订单下单时间', trigger: 'change' },
        ],
        'thirdOrderBaseInfoDTO.businessType': [
          { required: true, message: '请选择订单类型', trigger: 'change' },
        ],

        'thirdOrderBuyerInfoDTO.buyerNick': [
          { required: true, message: '请输入买家昵称', trigger: 'blur' },
        ],
        'thirdOrderBuyerInfoDTO.receiverName': [
          { required: this.cptIsDoor2Door, message: '请输入收货人姓名', trigger: 'blur' },
        ],
        'thirdOrderBuyerInfoDTO.mobile': [
          { required: true, message: '请输入手机号', trigger: 'blur' },
        ],

        'thirdOrderPaymentInfoDTO.payType': [
          { required: true, message: '请选择支付方式', trigger: 'change' },
        ],
        'thirdOrderPaymentInfoDTO.payAmount': [
          { required: true, message: '请输入支付金额', trigger: 'blur' },
        ],
        'thirdOrderBaseInfoDTO.payStatus': [
          { required: true, message: '请选择支付状态', trigger: 'change' },
        ],
        'thirdOrderPaymentInfoDTO.payTime': [
          { required: true, message: '请选择支付时间', trigger: 'change' },
        ],
        'thirdOrderBaseInfoDTO.takingDeliveryStoreId': [
          { required: this.cptIsO2O, message: '请选择提货店铺', trigger: 'change' },
        ],
        'thirdOrderBaseInfoDTO.takingDeliveryWay': [
          { required: true, message: '请选择提货方式', trigger: 'change' },
        ],

        fullAddress: {
          required: this.cptIsDoor2Door,
          validator: validateFullAddress,
          trigger: 'change',
        },
      };
    },
    cptIsO2O() {
      return this.form.thirdOrderBaseInfoDTO.businessType == 'o2o';
    },
    cptIsDoor2Door() {
      return this.form.thirdOrderBaseInfoDTO.takingDeliveryWay == '2';
    },
  },
  watch: {
    /** 是否包邮 */
    'form.sorPostageFreeFlag': {
      handler() {
        this.form.sorPostageCharge = 0.0;
      },
    },
    /** 是否开启商家营销活动 */
    'form.sorFavourable': {
      handler() {
        this.form.sorFavourableMoney = 0.0;
      },
    },
    /** 是否开启三方平台活动 */
    'form.sorThirdPromotion': {
      handler() {
        this.form.sorThirdPromotionMoney = 0.0;
      },
    },
    // /** 是否开启三方平台活动 */
    // 'form.thirdOrderPaymentInfoDTO.flag': {
    //   handler() {
    //     this.form.thirdOrderPaymentInfoDTO.transPrice = 0.0;
    //   },
    // },
  },
  created() {
    this.form.thirdOrderBaseInfoDTO.businessType = this.businessType;
  },
  mounted() {},

  methods: {
    reSetTable() {
      this.$nextTick(() => {
        this.$refs.OrderItemTable.reFresh();
      });
    },
    handleBusinessTypeChange(v, item) {
      this.$nextTick(() => {
        if (!this.cptIsO2O) {
          this.form.thirdOrderBaseInfoDTO.salesEmployeeId = null;
          this.form.thirdOrderBaseInfoDTO.salesEmployeeRealName = null;

          this.form.thirdOrderBaseInfoDTO.takingDeliveryStoreId = null;
          this.form.thirdOrderBaseInfoDTO.takingDeliveryStoreName = null;

          this.form.thirdOrderBaseInfoDTO.takingDeliveryWay = '2';
        }
      });
    },
    onChangeStore(v, item) {
      this.form.thirdOrderBaseInfoDTO.storeId = item.id;
      this.form.thirdOrderBaseInfoDTO.storeName = item.name;

      this.form.thirdOrderBaseInfoDTO.salesEmployeeId = null;
      this.form.thirdOrderBaseInfoDTO.salesEmployeeRealName = null;

      this.form.thirdOrderBaseInfoDTO.takingDeliveryStoreId = null;
      this.form.thirdOrderBaseInfoDTO.takingDeliveryStoreName = null;

      this.defaultSalesOrganizingId = item.salesOrganizingId;
    },
    onChangeSalesEmployee(v, item) {
      this.form.thirdOrderBaseInfoDTO.salesEmployeeId = item.id;
      this.form.thirdOrderBaseInfoDTO.salesEmployeeRealName = item.realName;
    },
    handleDeliveryStoreChange(v, item) {
      this.form.thirdOrderBaseInfoDTO.takingDeliveryStoreId = item.id;
      this.form.thirdOrderBaseInfoDTO.takingDeliveryStoreName = item.name;
    },
    batchDelete() {
      this.$confirm('确定批量删除SKU?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.SELECTED_BJ_EBC_ID.forEach(BJ_EBC_ID => {
          const index = this.form.thirdOrderItemDtoList.findIndex(e => e.BJ_EBC_ID == BJ_EBC_ID);
          if (index > -1) this.form.thirdOrderItemDtoList.splice(index, 1);
        });
        this.msgSuccess('删除成功');
        this.reSetTable();
      });
    },
    onDelete(row) {
      this.$confirm('确定删除SKU?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        const index = this.form.thirdOrderItemDtoList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
        if (index > -1) this.form.thirdOrderItemDtoList.splice(index, 1);
        this.msgSuccess('删除成功');
        this.reSetTable();
      });
    },
    handleSelectionChange(d) {
      console.log('handleSelectionChange', d);
      this.selectedRow = [].concat(d.selection);
    },

    onChangeRegionCascader(v) {
      console.log('onChangeRegionCascader', v);
      this.form.thirdOrderBuyerInfoDTO.province = v[0].label;
      this.form.thirdOrderBuyerInfoDTO.city = v[1].label;
      this.form.thirdOrderBuyerInfoDTO.district = v[2].label;

      this.form.thirdOrderBuyerInfoDTO.provinceCode = v[0].value;
      this.form.thirdOrderBuyerInfoDTO.cityCode = v[1].value;
      this.form.thirdOrderBuyerInfoDTO.districtCode = v[2].value;
    },
    addGoodsDialog(REF_NAME = 'dialogListOrdinarySku') {
      // TODO: 校验 ？ 赠品之类的
      this.orderDisabledList = this.form.thirdOrderItemDtoList
        .filter(e => e.giftFlag == 0)
        .map(e => e.id);
      console.log('addGoodsDialog', this.orderDisabledList);
      this.$refs[REF_NAME].show();
    },
    onAddGoodsDialogConfirm(data) {
      console.log('onAddGoodsDialogConfirm', data);

      data.forEach(e => {
        // TODO: 校验 ？ 赠品之类的
        if (!this.form.thirdOrderItemDtoList.find(ee => ee.skuCode == e.skuCode)) {
          const SALES_PRICE = Number(e.salesPrice || 0);
          const CONST_PRICE = Number(e.costPrice || 0);
          this.form.thirdOrderItemDtoList.push({
            ...e,
            giftFlag: '0',
            setMealFlag: e.setFlag,

            outerSingleSaleAmount: this.$number2money(SALES_PRICE),
            outerSaleAmount: this.$number2money(SALES_PRICE * 1),
            innerSingleSaleAmount: this.$number2money(SALES_PRICE),
            innerSaleAmount: this.$number2money(SALES_PRICE * 1),
            transPrice: '0.00',
            costPrice: this.$number2money(CONST_PRICE * 1),
            quantity: 1,

            skuId: e.id,
            BJ_EBC_ID: randomID(),
            BJ_HAS_CHILD: !!e.setFlag,
            BJ_PARENT_ID: null,
            isChildSku: false,
          });
        }
      });
      console.log(this.form.thirdOrderItemDtoList);

      this.reSetTable();
    },

    /** 确定 */
    onConfirm() {
      console.log('this.form', this.form);

      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        /** 1.带*为必填，需验证。

2.验证是否有选择商品。

3.三方订单号加订单类型为唯一，提交时需验证是否有履约中的关联此三方订单号和类型的内部订单，如果有，则提示已经有履约中的内部订单，不可重复创建。

4.三方平台销售总价之和要验证等于支付金额。*/

        if (
          Number(this.form.thirdOrderPaymentInfoDTO.payAmount || 0) !==
          Number(this.goodsTotalAmount)
        ) {
          this.$message.warning('三方平台销售总价需要与支付金额相等！');
          return;
        }

        const POST_DATA = this.$lodash.clone(this.form);
        // 自提，清空配送信息
        if (!this.cptIsDoor2Door) {
          POST_DATA.thirdOrderBuyerInfoDTO.address = null;
          POST_DATA.thirdOrderBuyerInfoDTO.provinceCode = null;
          POST_DATA.thirdOrderBuyerInfoDTO.cityCode = null;
          POST_DATA.thirdOrderBuyerInfoDTO.districtCode = null;
          POST_DATA.thirdOrderBuyerInfoDTO.province = null;
          POST_DATA.thirdOrderBuyerInfoDTO.city = null;
          POST_DATA.thirdOrderBuyerInfoDTO.district = null;
        }
        POST_DATA.thirdOrderItemDtoList = POST_DATA.thirdOrderItemDtoList.map(e => {
          return {
            giftFlag: e.giftFlag, // (integer, optional): 是否赠品 ,   0 不是赠品  1 是赠品
            setMealFlag: e.setMealFlag,

            outerSaleAmount: e.outerSaleAmount, // (number, optional): 外部销售总价  ,
            outerSingleSaleAmount: e.outerSingleSaleAmount,
            innerSingleSaleAmount: e.innerSingleSaleAmount,
            innerSaleAmount: e.innerSaleAmount,
            transPrice: e.transPrice, // 运费

            quantity: e.quantity, // (integer, optional): 数量 ,
            skuCode: e.skuCode, // (string, optional): sku编码 ,
            skuName: e.skuName,
            skuId: e.skuId, // (integer, optional): skuId
            spuCode: e.spuCode,
            spuName: e.spuName,
            spuId: e.spuId,
            spuType: e.spuType,
          };
        });
        // 手机号
        // POST_DATA.thirdOrderBuyerInfoDTO.phone = POST_DATA.thirdOrderBuyerInfoDTO.mobile;
        // 订单应收总金额  取计算属性 goodsTotalAmount
        POST_DATA.thirdOrderPaymentInfoDTO.goodsTotalAmount = this.goodsTotalAmount;
        // 商品总成本  取计算属性 costTotalAmount
        POST_DATA.thirdOrderPaymentInfoDTO.costTotalAmount = this.costTotalAmount;

        this.saveApi(POST_DATA);
      });
    },
    saveApi(POST_DATA) {
      orderApi.addThirdOrderByHand(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('提交成功');
          this.form = getDefaultForm();
          setTimeout(() => {
            const name =
              this.businessType == ORDER_BUSINESS_TYPES.COMMON
                ? 'BusinessOrderThirdIndex'
                : 'BusinessO2oThirdIndex';
            this.complete(name);
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.bottom {
  padding-left: 15px;
}
.bottom .el-form-item {
  margin-bottom: 0;
}
.price {
  font-size: 16px;
  font-weight: bold;
}
// .el-form-item--mini.el-form-item {
//   margin-bottom: 0;
// }
</style>
