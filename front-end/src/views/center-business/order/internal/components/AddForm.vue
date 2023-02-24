<!--
 * @Author: your name
 * @Date: 2021-09-29 14:09:55
 * @LastEditTime: 2022-04-27 15:32:00
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
          <el-form-item label="三方订单号" prop="orderBaseInfoDTO.outerOrderNo">
            <BjInputCode v-model="form.orderBaseInfoDTO.outerOrderNo" />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="三方订单下单时间" prop="orderBaseInfoDTO.orderTime">
            <el-date-picker
              v-model="form.orderBaseInfoDTO.orderTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择"
              class="full-width"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="colSpan">
          <el-form-item label="订单类型" prop="orderBaseInfoDTO.businessType">
            <BjSelectLov
              v-model="form.orderBaseInfoDTO.businessType"
              :option-list="ORDER_BUSINESS_TYPE_LIST"
              disabled
              @change="handleBusinessTypeChange"
            />
          </el-form-item>
        </el-col> -->

        <BjSelectChannelStore
          :span="colSpan"
          :query-params="form.orderBaseInfoDTO"
          :query-keys="['channel', 'storeCode']"
          :query-props="['orderBaseInfoDTO.channel', 'orderBaseInfoDTO.storeCode']"
          :query-labels="['选择销售渠道', '选择销售店铺']"
          store-option-value="code"
          @handleStoreChange="onChangeStore"
        />

        <template v-if="cptIsO2O">
          <el-col :span="colSpan">
            <!-- 非必填，取销售店铺的关联公司的组织架构下的员工 -->
            <el-form-item
              label="选择销售员工"
              prop="orderBaseInfoDTO.salesEmployeeId"
              label-width="160px"
            >
              <BjSelectUser
                v-model="form.orderBaseInfoDTO.salesEmployeeId"
                :default-params="{
                  enabled: true,
                  locked: false,
                  companyId: form.orderBaseInfoDTO.salesOrganizingId,
                }"
                :placeholder="form.orderBaseInfoDTO.storeCode ? '请选择' : '请先选择销售店铺'"
                :disabled="!form.orderBaseInfoDTO.storeCode"
                :default-options="{
                  label: form.orderBaseInfoDTO.salesEmployeeRealName,
                  value: form.orderBaseInfoDTO.salesEmployeeId,
                }"
                @change="onChangeSalesEmployee"
              />
            </el-form-item>
          </el-col>

          <el-col :span="colSpan">
            <!-- 1.如果销售店铺是网店，则取网店关联的门店列表。

2.如果销售店铺是门店，则取已启用的且是O2O订单自提门店列表。 -->
            <el-form-item
              label="选择提货店铺"
              prop="orderBaseInfoDTO.takingDeliveryStoreId"
              label-width="160px"
            >
              <BjSelectStore
                v-model="form.orderBaseInfoDTO.takingDeliveryStoreId"
                :default-params="{ o2oSalesStoreId: form.orderBaseInfoDTO.storeId }"
                :placeholder="form.orderBaseInfoDTO.storeCode ? '请选择' : '请先选择销售店铺'"
                :disabled="!form.orderBaseInfoDTO.storeCode"
                :default-options="{
                  label: form.orderBaseInfoDTO.takingDeliveryStoreName,
                  value: form.orderBaseInfoDTO.takingDeliveryStoreId,
                }"
                @change="handleDeliveryStoreChange"
              />
            </el-form-item>
          </el-col>
        </template>

        <el-col :span="colSpan * 2">
          <el-form-item label="商家备注" prop="orderBaseInfoDTO.orderRemarks">
            <BjTextarea v-model="form.orderBaseInfoDTO.orderRemarks" placeholder="商家备注" />
          </el-form-item>
        </el-col>
      </el-row>
      <BjDivider>买家以及收货信息</BjDivider>
      <el-row v-if="cptIsO2O">
        <el-col :span="colSpan">
          <el-form-item label="提货方式" prop="orderBaseInfoDTO.takingDeliveryWay">
            <BjRadioLov
              v-model="form.orderBaseInfoDTO.takingDeliveryWay"
              :option-list="ORDER_TAKING_DELIVERY_TYPE_LIST"
              :disabled="!cptIsO2O"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="买家昵称" prop="orderBuyerInfoDTO.buyerNick">
            <el-input
              v-model="form.orderBuyerInfoDTO.buyerNick"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-if="!cptIsPickSelf" :span="colSpan">
          <el-form-item label="收货人" prop="orderBuyerInfoDTO.receiverName">
            <el-input
              v-model="form.orderBuyerInfoDTO.receiverName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <BjFormItemMobile
            v-model="form.orderBuyerInfoDTO.mobile"
            :label="cptIsPickSelf ? '买家手机号' : '收货人手机号'"
            prop="orderBuyerInfoDTO.mobile"
            :required="true"
          />
        </el-col>
        <el-col v-if="!cptIsPickSelf" :span="colSpan * 2">
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
                v-model.trim="form.orderBuyerInfoDTO.address"
                :maxlength="MAX_LENGTH.DEFAULT"
                placeholder="详细地址"
                clearable
              />
            </el-col>
          </el-form-item>
        </el-col>

        <el-col :span="colSpan * 2">
          <el-form-item label="买家备注" prop="orderBuyerInfoDTO.buyerRemarks">
            <BjTextarea v-model="form.orderBuyerInfoDTO.buyerRemarks" placeholder="买家备注" />
          </el-form-item>
        </el-col>
      </el-row>

      <BjDivider>支付信息</BjDivider>
      <el-row>
        <el-col :span="8">
          <el-form-item label="支付方式" prop="orderPaymentInfoDTO.payType">
            <BjSelectLov
              v-model="form.orderPaymentInfoDTO.payType"
              :option-list="ORDER_INTERNAL_PAYMENT_LIST"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="支付金额" prop="orderPaymentInfoDTO.payAmount"
            ><BjInputMoney v-model="form.orderPaymentInfoDTO.payAmount"
          /></el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="支付金额是否含运费" prop="orderPaymentInfoDTO.flag">
            <div class="flex align-items">
              <BjRadioLov
                v-model="form.orderPaymentInfoDTO.flag"
                :option-list="STATUS_YES_TRUE"
                style="height: 28px; line-height: 28px"
                class="mt5"
              />
              <BjInputMoney
                v-if="form.orderPaymentInfoDTO.flag"
                v-model="form.orderPaymentInfoDTO.transPrice"
                placeholder="请输入运费金额"
                class="ml20"
                style="width: 120px !important"
              />
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="支付状态" prop="orderBaseInfoDTO.payStatus">
            <BjSelectLov
              v-model="form.orderBaseInfoDTO.payStatus"
              :option-list="ORDER_INTERNAL_PAYMENT_STATUS_LIST"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="支付时间" prop="orderPaymentInfoDTO.payTime"
            ><el-date-picker
              v-model="form.orderPaymentInfoDTO.payTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择"
              class="full-width"
          /></el-form-item>
        </el-col>
      </el-row>
      <!-- TODO: -->
      <BjDivider>优惠信息</BjDivider>
      <el-row>
        <el-col :span="colSpan * 2">
          <el-form-item label="三方平台优惠">
            <el-row :gutter="10">
              <el-col :span="10">
                <el-form-item label="" prop="orderDiscountInfoDTO.flag">
                  <BjRadioLov
                    v-model="form.orderDiscountInfoDTO.flag"
                    :option-list="STATUS_HAVE_TRUE"
                  />
                </el-form-item>
              </el-col>
              <el-col v-if="form.orderDiscountInfoDTO.flag" :span="10">
                <el-form-item label="" prop="orderPaymentInfoDTO.total">
                  <BjInputMoney
                    v-model="form.orderDiscountInfoDTO.total"
                    placeholder="三方平台优惠金额"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
        <el-col v-if="form.orderDiscountInfoDTO.flag" :span="colSpan * 2">
          <el-form-item label="优惠承担">
            <el-row :gutter="10">
              <!-- TODO: 合计 total  = third + seller  ??-->
              <el-col :span="10">
                <el-form-item label="" prop="orderBaseInfoDTO.third">
                  <BjInputMoney
                    v-model="form.orderDiscountInfoDTO.third"
                    placeholder="三方平台承担优惠金额"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="" prop="orderBaseInfoDTO.third">
                  <BjInputMoney
                    v-model="form.orderDiscountInfoDTO.seller"
                    placeholder="商家承担优惠金额"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
      <BjDivider
        >商品信息

        <template v-slot:button>
          <el-button :disabled="SELECTED_BJ_EBC_ID.length == 0" type="danger" @click="batchDelete"
            >批量删除SKU</el-button
          >

          <el-button type="primary" @click="addGoodsDialog('dialogListOrdinarySku')"
            >添加普通商品</el-button
          >
          <el-button type="primary" @click="addGoodsDialog('dialogListPackAgeSku')"
            >添加套餐商品</el-button
          >
        </template>
      </BjDivider>

      <OrderItemTable
        ref="OrderItemTable"
        :table-data.sync="form.orderItemDTOList"
        @delete="onDelete"
        @selection-change="handleSelectionChange"
      />

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
      <el-form-item label="运费总金额"
        ><span class="flag-one price"
          >¥ {{ $number2money(transPriceTotalAmount) || ' 0.00' }}</span
        ></el-form-item
      >

      <!-- 买家实付总金额：取支付金额 -->
      <el-form-item label="买家实付总金额"
        ><span class="flag-one price"
          >¥ {{ $number2money(form.orderPaymentInfoDTO.payAmount) || ' 0.00' }}</span
        ></el-form-item
      >

      <!-- 商品总成本：等于商品列表成本价之和 -->
      <el-form-item label="商品总成本"
        ><span class="flag-one price"
          >¥ {{ $number2money(costTotalAmount) || ' 0.00' }}</span
        ></el-form-item
      >
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
    <BjDialogList
      ref="dialogListPackAgeSku"
      width="80%"
      :config="
        $BjDialogListConfig.skuDefault('添加套餐商品', {
          defaultQuery: { setFlag: 1, status: [3, 4, 5] },
        })
      "
      multiple
      :disabled="orderDisabledList"
      @confirm="onAddGoodsDialogConfirm"
    />
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
  ORDER_INTERNAL_PAYMENT_STATUS_LIST,
  STATUS_HAVE_TRUE,
  STATUS_YES_TRUE,
  ORDER_TAKING_DELIVERY_TYPE_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
import { randomID } from '@/utils/index';
const getDefaultForm = () => {
  return {
    // 订单基础信息
    orderBaseInfoDTO: {
      // businessType (string, optional): 业务类型 1 普通单 ,
      // channel (string, optional): 渠道 0 天猫 ,
      // orderRemarks (string, optional): 卖家留言 ,
      // orderTime (string, optional): 下单时间 ,
      // outerOrderNo (string, optional): 外部订单号 ,
      // payStatus (integer, optional): 支付状态 ,
      // storeCode (string, optional): 店铺code ,
      // storeId (integer, optional): 店铺id ,
      // storeName (string, optional): 店铺名称
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
      salesOrganizingId: null,
      takingDeliveryStoreId: null,
      takingDeliveryStoreName: null,
      takingDeliveryWay: '2',
    },
    // 订单买家信息
    orderBuyerInfoDTO: {
      // buyerNick (string, optional): 买家昵称 ,
      // buyerRemarks (string, optional): 买家备注 ,

      // province (string, optional): 收货省 ,
      // provinceCode (string, optional): 收货省编码 ,
      // city (string, optional): 收货市 ,
      // cityCode (string, optional): 收货市编码 ,
      // district (string, optional): 收货区名称 ,
      // districtCode (string, optional): 收货区编码 ,
      // address (string, optional): 详细地址 ,

      // phone (string, optional): 收货电话 ,
      // mobile (string, optional): 收货手机号 ,

      // receiverName (string, optional): 收货人 ,
      // town (string, optional): 街道
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
    orderItemDTOList: [
      // {
      // giftFlag (integer, optional): 是否赠品 ,   0 不是赠品  1 是赠品
      // outerSaleAmount (number, optional): 外部销售总价 ,
      // quantity (integer, optional): 数量 ,
      // skuCode (string, optional): sku编码 ,
      // skuId (integer, optional): skuId
      // },
    ],
    // 订单支付信息
    orderPaymentInfoDTO: {
      // payAmount (number, optional): 支付金额 ,
      // payTime (string, optional): 支付时间 ,
      // payType (string, optional): 支付类型
      payAmount: null,
      payTime: null,
      payType: null,
      goodsTotalAmount: null, // 订单应收总金额  取计算属性 goodsTotalAmount
      costTotalAmount: null, // 商品总成本  取计算属性 costTotalAmount
      flag: false, // 支付金额是否含运费
      transPrice: null,
    },
    // TODO: 优惠信息
    orderDiscountInfoDTO: {
      flag: false,
      total: null,
      seller: null,
      third: null,
    },
  };
};
export default {
  name: 'BusinessOrderInternalAdd',
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
      ORDER_INTERNAL_PAYMENT_STATUS_LIST,
      ORDER_TAKING_DELIVERY_TYPE_LIST,
      STATUS_HAVE_TRUE,
      STATUS_YES_TRUE,

      form: getDefaultForm(),

      regionList: [],
      orderDisabledList: [],

      selectedRow: [],
      /** 订单应收总金额 */
      shopTotalPrice: 0,
      // 应收总金额
      receiveTotalPrice: 0,
    };
  },
  computed: {
    SELECTED_BJ_EBC_ID() {
      return this.selectedRow.map(e => e.BJ_EBC_ID);
    },
    transPriceTotalAmount() {
      let amount = 0;
      this.form.orderItemDTOList.forEach(row => {
        amount += Number(row.transPrice || 0);
      });
      return amount;
    },
    goodsTotalAmount() {
      let amount = 0;
      this.form.orderItemDTOList.forEach(row => {
        amount += Number(row.outerSaleAmount || 0);
      });
      return amount;
    },
    costTotalAmount() {
      let amount = 0;
      this.form.orderItemDTOList.forEach(row => {
        amount += Number(row.costPrice || 0) * Number(row.quantity || 0);
      });
      return amount;
    },
    cptOrderDiscountTotal() {
      if (!this.form.orderDiscountInfoDTO.flag) return 0;
      return this.form.orderDiscountInfoDTO.total;
    },
    // 表单校验
    rules() {
      const validateFullAddress = (rule, value, callback) => {
        this.$nextTick(() => {
          if (
            !this.form.orderBuyerInfoDTO.provinceCode ||
            !this.form.orderBuyerInfoDTO.cityCode ||
            !this.form.orderBuyerInfoDTO.districtCode
          ) {
            callback(new Error('请选择省/市/区'));
          }
          if (!this.form.orderBuyerInfoDTO.address) {
            callback(new Error('请输入详细地址'));
          }
          callback();
        });
      };

      return {
        'orderBaseInfoDTO.channel': [{ required: true, message: '请选择渠道', trigger: 'change' }],
        'orderBaseInfoDTO.storeCode': [
          { required: true, message: '请选择店铺', trigger: 'change' },
        ],
        'orderBaseInfoDTO.outerOrderNo': [
          { required: true, message: '请输入三方订单号', trigger: 'blur' },
        ],
        'orderBaseInfoDTO.orderTime': [
          { required: true, message: '请选择三方订单下单时间', trigger: 'change' },
        ],
        'orderBaseInfoDTO.businessType': [
          { required: true, message: '请选择订单类型', trigger: 'change' },
        ],

        'orderBuyerInfoDTO.buyerNick': [
          { required: true, message: '请输入买家昵称', trigger: 'blur' },
        ],
        'orderBuyerInfoDTO.receiverName': [
          { required: true, message: '请输入收货人姓名', trigger: 'blur' },
        ],
        'orderBuyerInfoDTO.mobile': [{ required: true, message: '请输入手机号', trigger: 'blur' }],

        'orderPaymentInfoDTO.payType': [
          { required: true, message: '请选择支付方式', trigger: 'change' },
        ],
        'orderPaymentInfoDTO.payAmount': [
          { required: true, message: '请输入支付金额', trigger: 'blur' },
        ],
        'orderBaseInfoDTO.payStatus': [
          { required: true, message: '请选择支付状态', trigger: 'change' },
        ],
        'orderPaymentInfoDTO.payTime': [
          { required: true, message: '请选择支付时间', trigger: 'change' },
        ],
        'orderBaseInfoDTO.takingDeliveryStoreId': [
          { required: this.cptIsO2O, message: '请选择提货店铺', trigger: 'change' },
        ],
        'orderBaseInfoDTO.takingDeliveryWay': [
          { required: true, message: '请选择提货方式', trigger: 'change' },
        ],
        fullAddress: {
          required: true,
          validator: validateFullAddress,
          trigger: 'change',
        },
      };
    },
    cptIsO2O() {
      return this.form.orderBaseInfoDTO.businessType == 'o2o';
    },
    cptIsPickSelf() {
      return this.form.orderBaseInfoDTO.takingDeliveryWay == '1';
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
    /** 是否开启三方平台活动 */
    'form.orderPaymentInfoDTO.flag': {
      handler() {
        this.form.orderPaymentInfoDTO.transPrice = 0.0;
      },
    },
  },
  created() {
    this.form.orderBaseInfoDTO.businessType = this.businessType;
  },
  mounted() {
    console.log('this.$route', this.$route);

    if (Object.prototype.hasOwnProperty.call(this.$route.query, 'innerOrderNo')) {
      const innerOrderNo = this.$route.query.innerOrderNo;
      orderApi.addInternalOrderByCopy({ innerOrderNo }).then(res => {
        const GoodsList = ((res && res.itemVOList) || []).map(e => {
          return {
            ...e,
            id: e.skuId,
            giftFlag: String(e.giftFlag),
            BJ_EBC_ID: randomID(),
            BJ_HAS_CHILD: !!e.setMealFlag,
            BJ_PARENT_ID: null,
            isChildSku: false,
          };
        });
        delete res.itemVOList;
        if (
          GoodsList ||
          res.id ||
          res._token ||
          res.content ||
          Array.isArray(res) ||
          res.failed === false
        ) {
          this.$message.success('获取订单信息成功');
          this.form = Object.assign(getDefaultForm(), { ...res, orderItemDTOList: GoodsList });
          this.form.orderBaseInfoDTO.actionType = '202'; // constants.js => ORDER_ACTION_TYPE_LIST => 复制单
          this.form.orderBaseInfoDTO.copyOrigin = innerOrderNo;
          this.regionList = [
            Number(res.orderBuyerInfoDTO.provinceCode),
            Number(res.orderBuyerInfoDTO.cityCode),
            Number(res.orderBuyerInfoDTO.districtCode),
          ];
        }
        this.reSetTable();
      });
    }
  },

  methods: {
    reSetTable() {
      this.$nextTick(() => {
        this.$refs.OrderItemTable.reFresh();
      });
    },
    handleBusinessTypeChange(v, item) {
      this.$nextTick(() => {
        if (!this.cptIsO2O) {
          this.form.orderBaseInfoDTO.salesEmployeeId = null;
          this.form.orderBaseInfoDTO.salesEmployeeRealName = null;

          this.form.orderBaseInfoDTO.takingDeliveryStoreId = null;
          this.form.orderBaseInfoDTO.takingDeliveryStoreName = null;

          this.form.orderBaseInfoDTO.takingDeliveryWay = '2';
        }
      });
    },

    onChangeStore(v, item) {
      this.form.orderBaseInfoDTO.storeId = item.id;
      this.form.orderBaseInfoDTO.storeName = item.name;

      this.form.orderBaseInfoDTO.salesEmployeeId = null;
      this.form.orderBaseInfoDTO.salesEmployeeRealName = null;

      this.form.orderBaseInfoDTO.takingDeliveryStoreId = null;
      this.form.orderBaseInfoDTO.takingDeliveryStoreName = null;

      this.form.orderBaseInfoDTO.salesOrganizingId = item.salesOrganizingId;
    },
    onChangeSalesEmployee(v, item) {
      this.form.orderBaseInfoDTO.salesEmployeeId = item.id;
      this.form.orderBaseInfoDTO.salesEmployeeRealName = item.realName;
    },
    handleDeliveryStoreChange(v, item) {
      this.form.orderBaseInfoDTO.takingDeliveryStoreId = item.id;
      this.form.orderBaseInfoDTO.takingDeliveryStoreName = item.name;
    },
    batchDelete() {
      this.$confirm('确定批量删除SKU?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.SELECTED_BJ_EBC_ID.forEach(BJ_EBC_ID => {
          const index = this.form.orderItemDTOList.findIndex(e => e.BJ_EBC_ID == BJ_EBC_ID);
          if (index > -1) this.form.orderItemDTOList.splice(index, 1);
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
        const index = this.form.orderItemDTOList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
        if (index > -1) this.form.orderItemDTOList.splice(index, 1);
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
      this.form.orderBuyerInfoDTO.province = v[0].label;
      this.form.orderBuyerInfoDTO.city = v[1].label;
      this.form.orderBuyerInfoDTO.district = v[2].label;

      this.form.orderBuyerInfoDTO.provinceCode = v[0].value;
      this.form.orderBuyerInfoDTO.cityCode = v[1].value;
      this.form.orderBuyerInfoDTO.districtCode = v[2].value;
    },
    addGoodsDialog(REF_NAME = 'dialogListOrdinarySku') {
      // TODO: 校验 ？ 赠品之类的
      this.orderDisabledList = this.form.orderItemDTOList
        .filter(e => e.giftFlag == 0)
        .map(e => e.id);
      console.log('addGoodsDialog', this.orderDisabledList);
      this.$refs[REF_NAME].show();
    },
    onAddGoodsDialogConfirm(data) {
      console.log('onAddGoodsDialogConfirm', data);

      data.forEach(e => {
        // TODO: 校验 ？ 赠品之类的
        if (!this.form.orderItemDTOList.find(ee => ee.skuCode == e.skuCode)) {
          const SALES_PRICE = Number(e.salesPrice || 0);
          const CONST_PRICE = Number(e.costPrice || 0);
          this.form.orderItemDTOList.push({
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
      console.log(this.form.orderItemDTOList);

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
          Number(this.form.orderPaymentInfoDTO.payAmount || 0) !== Number(this.goodsTotalAmount)
        ) {
          this.$message.warning('三方平台销售总价需要与支付金额相等！');
          return;
        }

        const POST_DATA = this.$lodash.clone(this.form);
        // 自提，清空配送信息
        if (this.cptIsPickSelf) {
          POST_DATA.orderBuyerInfoDTO.address = null;
          POST_DATA.orderBuyerInfoDTO.provinceCode = null;
          POST_DATA.orderBuyerInfoDTO.cityCode = null;
          POST_DATA.orderBuyerInfoDTO.districtCode = null;
          POST_DATA.orderBuyerInfoDTO.province = null;
          POST_DATA.orderBuyerInfoDTO.city = null;
          POST_DATA.orderBuyerInfoDTO.district = null;
        }
        POST_DATA.orderItemDTOList = POST_DATA.orderItemDTOList.map(e => {
          return {
            giftFlag: e.giftFlag, // (integer, optional): 是否赠品 ,   0 不是赠品  1 是赠品
            setMealFlag: e.setMealFlag,

            outerSaleAmount: e.outerSaleAmount, // (number, optional): 外部销售总价  ,
            outerSingleSaleAmount: e.outerSingleSaleAmount,
            innerSingleSaleAmount: e.innerSingleSaleAmount,
            innerSaleAmount: e.innerSaleAmount,
            transPrice: e.transPrice,

            quantity: e.quantity, // (integer, optional): 数量 ,
            skuCode: e.skuCode, // (string, optional): sku编码 ,
            skuId: e.skuId, // (integer, optional): skuId
          };
        });
        // 手机号
        POST_DATA.orderBuyerInfoDTO.phone = POST_DATA.orderBuyerInfoDTO.mobile;
        // 订单应收总金额  取计算属性 goodsTotalAmount
        POST_DATA.orderPaymentInfoDTO.goodsTotalAmount = this.goodsTotalAmount;
        // 商品总成本  取计算属性 costTotalAmount
        POST_DATA.orderPaymentInfoDTO.costTotalAmount = this.costTotalAmount;
        orderApi.addInternalOrderByHand(POST_DATA).then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success('提交成功');
            this.form = getDefaultForm();
            setTimeout(() => {
              const name =
                this.businessType == ORDER_BUSINESS_TYPES.COMMON
                  ? 'BusinessOrderInternalIndex'
                  : 'BusinessO2oInternalIndex';
              this.complete(name);
            }, 500);
          }
        });
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.bottom .el-form-item {
  margin-bottom: 0;
}
.text-center button {
  width: 150px;
}
.price {
  font-size: 16px;
  font-weight: bold;
}
</style>
