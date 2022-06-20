<!--
 * @Author: your name
 * @Date: 2021-09-30 10:21:32
 * @LastEditTime: 2022-04-27 15:35:11
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\after\internal\refund\add.vue
-->
<template>
  <div class="app-container bj-label-colon">
    <el-form ref="form" :model="form" :rules="rules" label-width="140px">
      <el-row :gutter="20">
        <BjDivider right-fixed
          >三方退货单基本信息

          <template slot="right">
            <el-button @click="tryCancel()">取 消</el-button>
            <el-button type="primary" @click="onConfirm">确 定</el-button>
          </template>
        </BjDivider>
        <el-col :span="colSpan">
          <el-form-item label="三方订单号" prop="thirdPartyRefundBaseInfo.outerOrderNo">
            <el-input
              v-model="form.thirdPartyRefundBaseInfo.outerOrderNo"
              disabled
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请点击右侧按钮选择"
            >
              <el-button slot="append" @click="handleShowFnForDialogOrderList"
                >关联三方订单</el-button
              >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan" label-width="30px">
          <el-form-item> </el-form-item>
        </el-col>
        <BjSelectChannelStore
          disabled
          :span="colSpan"
          :query-params="form.thirdPartyRefundBaseInfo"
          :query-keys="['channel', 'storeId']"
          :query-props="['thirdPartyRefundBaseInfo.channel', 'thirdPartyRefundBaseInfo.storeId']"
          :query-labels="['选择渠道', '选择店铺']"
          store-option-value="id"
        />

        <el-col :span="colSpan">
          <el-form-item label="三方退货单号" prop="thirdPartyRefundBaseInfo.outRefundNo">
            <el-input
              v-model="form.thirdPartyRefundBaseInfo.outRefundNo"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="三方退货时间" prop="thirdPartyRefundBaseInfo.refundTime">
            <el-date-picker
              v-model="form.thirdPartyRefundBaseInfo.refundTime"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="退货原因" prop="thirdPartyRefundBaseInfo.reason">
            <BjSelectLov
              v-model="form.thirdPartyRefundBaseInfo.reason"
              lov-code="BJ_AFTER_REFUND_GOODS_REASONS"
              item-useable="status"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货阶段" prop="thirdPartyRefundBaseInfo.stage">
            <BjSelectLov
              v-model="form.thirdPartyRefundBaseInfo.stage"
              :option-list="INTERNAL_AFTER_STAGE_STATUS"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货备注" prop="thirdPartyRefundBaseInfo.remarks ">
            <BjTextarea v-model="form.thirdPartyRefundBaseInfo.remarks" placeholder="退货备注" />
          </el-form-item>
        </el-col>
        <BjDivider>买家信息</BjDivider>
        <el-col :span="colSpan">
          <el-form-item label="买家昵称" prop="thirdPartyRefundBaseInfo.buyerNick">
            <el-input
              v-model="form.thirdPartyRefundBaseInfo.buyerNick"
              disabled
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货人" prop="thirdPartyReceiveInfo.returnName">
            <el-input
              v-model="form.thirdPartyReceiveInfo.returnName"
              disabled
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <BjFormItemMobile
            v-model.trim="form.thirdPartyReceiveInfo.mobile"
            disabled
            prop="thirdPartyReceiveInfo.mobile"
            label="退货人手机号"
            clearable
          />
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货人地址">
            <el-input
              v-model="refundAddressFromOrder"
              disabled
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <BjDivider>退货物流信息</BjDivider>

        <el-col :span="colSpan">
          <el-form-item label="退货物流" prop="thirdPartyAfterSaleLogisticsList.logisticsCompanyId">
            <BjSelectLogisticCompany
              :value="form.thirdPartyAfterSaleLogisticsList.logisticsCompanyId"
              placeholder="请选择物流公司"
              clearable
              @change="handleChangeLogisticCompany"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="退货物流单号" prop="thirdPartyAfterSaleLogisticsList.logisticsNo">
            <el-input
              v-model="form.thirdPartyAfterSaleLogisticsList.logisticsNo"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="退货收件人" prop="thirdPartyReceiveInfo.receiveName">
            <el-input
              v-model="form.thirdPartyReceiveInfo.receiveName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <BjFormItemMobile
            v-model.trim="form.thirdPartyReceiveInfo.receiveMobile"
            prop="thirdPartyReceiveInfo.receiveMobile"
            label="退货收件人手机号"
            clearable
          />
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货地址" prop="fullAddress">
            <BjRegionCascader
              v-model="regionList"
              class="full-width"
              is-array
              clearable
              @change="onChangeRegionCascader2"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="" prop="thirdPartyReceiveInfo.receiveAddress" label-width="0">
            <el-input
              v-model="form.thirdPartyReceiveInfo.receiveAddress"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="详细地址"
            />
          </el-form-item>
        </el-col>

        <BjDivider
          >商品信息
          <template #button>
            <el-button type="primary" class="xs mb16" @click="handleShowFnForDialogSkuList"
              >添加退货商品</el-button
            >
          </template>
        </BjDivider>
        <SkuList
          ref="SkuList"
          :table-data.sync="skuTableData"
          @delete="onDelete"
          @selection-change="handleSelectionChange"
        />
      </el-row>
    </el-form>
    <!-- 选择订单,  初始  全部待发货  全部发货失败-->
    <BjDialogList
      ref="BjDialogOrderList"
      width="80%"
      :config="$BjDialogListConfig.thirdOrderDefault('选择关联三方订单', { defaultQuery: {} })"
      tag-name="outerOrderNo"
      @confirm="confirmForDialogOrderList"
    />
    <!-- 选择商品 -->
    <BjDialogList
      ref="BjDialogSkuList"
      width="80%"
      :config="
        $BjDialogListConfig.thirdRefundGoodsDefault('选择退货的商品', {
          defaultQuery: {},
          actionApi: isBjDialogSkuListApi,
        })
      "
      search-btn-hide
      :row-keys="ROW_KEY"
      custom-selection
      :disabled="SKU_DISABLED_ID_LIST"
      @confirm="confirmForDialogSkuList"
    >
      <template #tableTop>
        <div class="mb10">
          <span
            >关联三方订单：<span class="link">{{
              form.thirdPartyRefundBaseInfo.outerOrderNo
            }}</span></span
          >
        </div>
      </template>
    </BjDialogList>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { afterApi, orderApi } from '@/api';
import { STATUS_YES_NUM, INTERNAL_AFTER_STAGE_STATUS, AFTER_ORDER_TYPES } from '@/utils/constants';
import SkuList from '../refund/components/SkuList';
import BjRegionCascader from '@/components/BjRegionCascader';
const getDefaultForm = () => {
  return {
    // innerOrderNo: null,
    // channel: null,
    // storeId: null,
    // thirdRefundNo: null,
    // thirdRefundTime: null,
    // reason: null,
    // stage: null,
    // serviceRemarks: null,
    // buyerNick: null,

    thirdPartyAfterSaleGoodsList: [],
    thirdPartyAfterSaleLogisticsList: {
      id: null,
      innerOrderNo: null,
      innerRefundNo: null,
      logisticsCompanyId: null,
      logisticsCompanyName: null,
      logisticsNo: null,
      outerOrderNo: null,
      outerRefundNo: null,
    },
    thirdPartyReceiveInfo: {
      address: null,
      city: null,
      cityCode: null,
      district: null,
      districtCode: null,
      id: null,
      mobile: null,
      outReturnNo: null,
      outerOrderNo: null,
      province: null,
      provinceCode: null,
      receiveAddress: null,
      receiveCity: null,
      receiveCityCode: null,
      receiveDistrict: null,
      receiveDistrictCode: null,
      receiveMobile: null,
      receiveName: null,
      receiveProvince: null,
      receiveProvinceCode: null,
      receiveTown: null,
      returnName: null,
      town: null,
    },
    thirdPartyRefundBaseInfo: {
      buyerNick: null,
      remarks: null,
      channel: null,
      exceptionReason: null,
      exceptionStatus: null,
      goodsQuantity: null,
      outReceiveInfo: null,
      outReceiveInfoMd5: null,
      outRefundNo: null,
      outerOrderNo: null,
      refundTime: null,
      stage: null,
      storeCode: null,
      storeId: null,
      storeName: null,
      totalAmount: null,
      type: AFTER_ORDER_TYPES.REFUND_GOODS,
      //
      innerOrderNo: null,
      reason: null,
    },
  };
};
const ROW_KEY = 'id';
export default {
  name: 'BusinessAfterThirdReturnAdd',
  components: {
    SkuList,
    BjRegionCascader,
  },
  mixins: [viewMiXin],
  data() {
    return {
      colSpan: 9,
      ROW_KEY,

      pageName: 'BusinessAfterInternalRefundAdd',
      STATUS_YES_NUM,
      INTERNAL_AFTER_STAGE_STATUS,
      form: getDefaultForm(),
      rules: {
        'thirdPartyRefundBaseInfo.outerOrderNo': [
          { required: true, message: '请选择关联三方订单号', trigger: 'change' },
        ],
        'thirdPartyRefundBaseInfo.channel': [
          { required: true, message: '请选择关联三方订单号', trigger: 'change' },
        ],
        'thirdPartyRefundBaseInfo.storeId': [
          { required: true, message: '请选择关联三方订单号', trigger: 'change' },
        ],
        'thirdPartyRefundBaseInfo.outRefundNo': [
          { required: true, message: '请输入三方退货单号', trigger: 'blur' },
        ],
        'thirdPartyRefundBaseInfo.refundTime': [
          { required: true, message: '请选择三方退货时间', trigger: 'change' },
        ],
        'thirdPartyRefundBaseInfo.reason': [
          { required: true, message: '请选择退货原因', trigger: 'change' },
        ],
        'thirdPartyRefundBaseInfo.stage': [
          { required: true, message: '请选择退货阶段', trigger: 'change' },
        ],
        'thirdPartyRefundBaseInfo.buyerNick': [
          { required: true, message: '请选择关联三方订单号获取', trigger: 'change' },
        ],
      },
      thirdSaleOrderSkuList: [],
      /** ************* 弹窗  BjDialogSkuList 展开表格的配置  START **************** */
      SKU_DISABLED_ID_LIST: [],

      /** ************* 弹窗 BjDialogSkuList 展开表格的配置  END **************** */
      /** ************* 底部 商品信息  SkuList 配置  START **************** */
      skuTableData: [
        //         id: null,
        // outRefundNo: null,
        // outerOrderNo: null,
        // outerSalePrice: null,
        // quantity: null,
        // skuCode: null,
        // skuName: null,
        // spuCode: null,
        // spuName: null,
        // totalAmount: null,
        // type: null,
      ], // 数据
      selectedRow: [],

      /** *************  底部 商品信息 SkuList 配置  END **************** */

      regionList: [],
      refundAddressFromOrder: null, // 退货人地址
    };
  },
  computed: {},
  created() {
    if (this.$route.query.test) {
      this.skuTableData = [
        {
          children: [],
          id: 305028317052141600,
          outerOrderNo: '3',
          innerOrderNo: 'CO00000345',
          outerItemId: null,
          spuCode: null,
          spuType: null,
          skuCode: 'sku20220413001',
          skuName: null,
          quantity: 0,
          setFlag: null,
          giftFlag: 0,
          multiFlag: null,
          exceptionFlag: null,
          exceptionReason: null,
          deleteFlag: null,
          spuName: null,
          skuId: null,
          mainItemCode: null,
          orderItemCode: null,
          BJ_TAG_PAGE: '1',
          unitPrice: '0.00',
          allPrice: '100.00',
        },
      ];
      this.form = {
        thirdPartyAfterSaleGoodsList: [
          {
            children: [],
            id: 305028317052141600,
            outerOrderNo: '3',
            innerOrderNo: 'CO00000345',
            outerItemId: null,
            spuCode: null,
            spuType: null,
            skuCode: 'sku20220413001',
            skuName: null,
            quantity: 0,
            setFlag: null,
            giftFlag: 0,
            multiFlag: null,
            exceptionFlag: null,
            exceptionReason: null,
            deleteFlag: null,
            spuName: null,
            skuId: null,
            mainItemCode: null,
            orderItemCode: null,
            BJ_TAG_PAGE: '1',
            unitPrice: '0.00',
            allPrice: '100.00',
          },
        ],
        thirdPartyAfterSaleLogisticsList: {},
        thirdPartyReceiveInfo: {
          address: null,
          city: null,
          cityCode: null,
          district: null,
          districtCode: null,
          id: null,
          mobile: null,
          outReturnNo: null,
          outerOrderNo: null,
          province: null,
          provinceCode: null,
          receiveAddress: null,
          receiveCity: null,
          receiveCityCode: null,
          receiveDistrict: null,
          receiveDistrictCode: null,
          receiveMobile: null,
          receiveName: null,
          receiveProvince: null,
          receiveProvinceCode: null,
          receiveTown: null,
          returnName: null,
          town: null,
        },
        thirdPartyRefundBaseInfo: {
          buyerNick: '11',
          remarks: 'thirdRefund00001退货备注',
          channel: '0',
          exceptionReason: null,
          exceptionStatus: null,
          goodsQuantity: null,
          outReceiveInfo: null,
          outReceiveInfoMd5: null,
          outRefundNo: 'thirdRefund00001',
          outerOrderNo: '3',
          refundTime: '2022-04-22 11:29:39',
          stage: 0,
          storeCode: null,
          storeId: '=G6amoYgActr0TGChO3g2DH_iG3CerHJR7lHRoFFiANw==',
          storeName: null,
          totalAmount: null,
          type: null,
          innerOrderNo: null,
          reason: 'RE1649820848448',
        },
      };
    }
  },
  methods: {
    handleChangeLogisticCompany(id, item) {
      console.log(...arguments);

      // this.form.thirdPartyAfterSaleLogisticsList.id = null; //(integer, optional)= id ,
      // this.form.thirdPartyAfterSaleLogisticsList.innerOrderNo = null; //(string, optional)= 内部订单号 ,
      // this.form.thirdPartyAfterSaleLogisticsList.innerRefundNo = null; //(string, optional)= 内部退款/退货单号 ,
      this.form.thirdPartyAfterSaleLogisticsList.logisticsCompanyId = item.id; // (integer, optional)= 退货物流公司id ,
      this.form.thirdPartyAfterSaleLogisticsList.logisticsCompanyName = item.name; // (string, optional)= 退货物流公司名称 ,
      // this.form.thirdPartyAfterSaleLogisticsList.logisticsNo = null; // (string, optional)= 退货物流单号 ,
      // this.form.thirdPartyAfterSaleLogisticsList.outerOrderNo = null; //(string, optional)= 平台订单号 ,
      // this.form.thirdPartyAfterSaleLogisticsList.outerRefundNo = null; //(string, optional)= 第三方退款/退货单号
    },
    onChangeRegionCascader2(v) {
      this.form.thirdPartyReceiveInfo.receiveProvince = v[0].label;
      this.form.thirdPartyReceiveInfo.receiveCity = v[1].label;
      this.form.thirdPartyReceiveInfo.receiveDistrict = v[2].label;

      this.form.thirdPartyReceiveInfo.receiveProvinceCode = v[0].value;
      this.form.thirdPartyReceiveInfo.receiveCityCode = v[1].value;
      this.form.thirdPartyReceiveInfo.receiveDistrictCode = v[2].value;
    },
    // 选择商品弹窗 的 接口
    isBjDialogSkuListApi(queryParams) {
      const start = queryParams.currentPage * queryParams.pageSize;
      const end = queryParams.page * queryParams.pageSize;
      return new Promise(resolve => {
        resolve({
          content: this.thirdSaleOrderSkuList.slice(start, end),
          totalElements: this.thirdSaleOrderSkuList.length, // 数据总数
        });
      });
    },
    // 更新商品列表
    reSetTable() {
      this.$nextTick(() => {
        this.$refs.SkuList.reFresh();
      });
    },
    /** 确定 */
    onConfirm() {
      if (!this.form.thirdPartyRefundBaseInfo.outerOrderNo) {
        this.$message.warning('请先选择关联三方销售单');
        return;
      }
      if (!this.skuTableData.length) {
        this.$message.warning('请先添加退货商品');
        return;
      }
      console.log('确认', this.form);
      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();

        const API_DATA = this.$lodash.cloneDeep(this.form);

        API_DATA.thirdPartyAfterSaleGoodsList = [...this.skuTableData];
        this.addApi(API_DATA);
      });
    },
    /** 选择关联销售单  START */
    // 显示
    handleShowFnForDialogOrderList() {
      this.$refs.BjDialogOrderList.show();
    },
    // 确认
    confirmForDialogOrderList(data) {
      if (Array.isArray(data) && data.length) {
        this.refundAddressFromOrder = data[0].address;
        this.form.thirdPartyRefundBaseInfo.storeId = data[0].storeId;
        this.setShirdSaleOrderSkuList(data[0].id);
      }
      this.skuTableData = [];
      this.reSetTable();
    },
    /** 选择关联销售单  END */
    setShirdSaleOrderSkuList(id) {
      orderApi.getThirdOrderDetail({ id }).then(res => {
        // 订单信息
        const ORDER_INFO = res.orderInfoVO || {};
        this.form.thirdPartyRefundBaseInfo.outerOrderNo = ORDER_INFO.outerOrderNo;
        this.form.thirdPartyRefundBaseInfo.channel = ORDER_INFO.channel;
        this.form.thirdPartyRefundBaseInfo.channelTypeMeaning = ORDER_INFO.channelTypeMeaning;

        this.form.thirdPartyRefundBaseInfo.storeName = ORDER_INFO.storeName;
        this.form.thirdPartyRefundBaseInfo.buyerNick = ORDER_INFO.buyerNick;
        this.form.thirdPartyRefundBaseInfo.innerOrderNo = ORDER_INFO.innerOrderNo;

        // 买家信息
        const RECEIVER_INFO = res.receiverInfoVO || {};
        this.form.thirdPartyReceiveInfo.returnName = RECEIVER_INFO.receiverName;
        this.form.thirdPartyReceiveInfo.mobile = RECEIVER_INFO.mobile;
        this.form.thirdPartyReceiveInfo.provinceCode = RECEIVER_INFO.provinceCode;
        this.form.thirdPartyReceiveInfo.cityCode = RECEIVER_INFO.cityCode;
        this.form.thirdPartyReceiveInfo.districtCode = RECEIVER_INFO.districtCode;
        this.form.thirdPartyReceiveInfo.province = RECEIVER_INFO.province;
        this.form.thirdPartyReceiveInfo.city = RECEIVER_INFO.city;
        this.form.thirdPartyReceiveInfo.district = RECEIVER_INFO.district;
        this.form.thirdPartyReceiveInfo.address = RECEIVER_INFO.address;

        // 商品信息
        const TEMP = Array.isArray(res.thirdItemVO) ? res.thirdItemVO : [];
        this.thirdSaleOrderSkuList = TEMP.map(e => {
          return {
            ...e,
            id: null,
            outRefundNo: null,
            outerOrderNo: this.form.thirdPartyRefundBaseInfo.outerOrderNo,
            outerSaleAmount: this.$number2money(e.outerSaleAmount || 0),
            outerSingleSaleAmount: this.$number2money(e.outerSingleSaleAmount || 0),
            quantity: e.quantity || 1,
          };
        });
      });
    },

    /** *****         BjDialogSkuList    START ********* */

    // BjDialogSkuList 选择商品 show
    handleShowFnForDialogSkuList() {
      if (this.form.thirdPartyRefundBaseInfo.outerOrderNo) {
        this.$nextTick(() => {
          this.$refs.BjDialogSkuList.show(this.skuTableData);
        });
      } else {
        this.$message.warning('请先选择关联三方订单');
      }
    },
    // BjDialogSkuList 选择商品 confirm
    confirmForDialogSkuList(tags, expandSelected, selectedObj) {
      console.log(...arguments);
      this.skuTableData = tags.map(e => {
        return {
          ...e,
          outRefundNo: null,
          outerOrderNo: this.form.thirdPartyRefundBaseInfo.outerOrderNo,
          outerSalePrice: e.outerSingleSaleAmount,
          totalAmount: e.outerSaleAmount,
          type: AFTER_ORDER_TYPES.REFUND_GOODS,
        };
      });
      this.reSetTable();
    },
    onDelete(row) {
      this.$confirm('确定删除SKU?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        const index = this.skuTableData.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);
        if (index > -1) this.skuTableData.splice(index, 1);
        this.msgSuccess('删除成功');
        this.reSetTable();
      });
    },
    handleSelectionChange(d) {
      this.selectedRow = [].concat(d.selection);
    },
    /** *****         BjDialogSkuList    END ********* */
    addApi(API_DATA) {
      console.log('API_DATA');
      console.log(API_DATA);
      afterApi.addThirdRefundOrder(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessAfterThirdReturnIndex');
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-input-group__append {
  background: #ff7310;
  color: #ffffff;
}
</style>
