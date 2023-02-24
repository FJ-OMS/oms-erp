<template>
  <div>
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'100px'"
      :search-btn-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :before-reset-search="beforeResetSearch"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="firstColSpan" class="text-right"> 常用查询：</el-col>
        <el-col :span="24 - firstColSpan">
          <el-row :gutter="20">
            <el-col :span="colSpan">
              <el-form-item label="订单号" prop="orderNo">
                <BjInputCode
                  v-model.trim="queryParams.orderNo"
                  placeholder="三方订单号/内部订单号，多个逗号隔开"
                  comma
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
            </el-col>
            <BjSelectChannelStore
              :span="colSpan"
              :query-params="queryParams"
              :query-props="['channel', 'storeIdList']"
              multiple
            />
            <!-- o2o start-->
            <template v-if="businessType == ORDER_BUSINESS_TYPES.O2O">
              <el-col :span="colSpan">
                <el-form-item label="提货店铺" prop="o2oSearchDTO.takingDeliveryStore">
                  <BjSelectStorePhysical
                    v-model.trim="queryParams.o2oSearchDTO.takingDeliveryStore"
                    :default-params="{ status: true, selfPickupFlag: true }"
                    multiple
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <el-form-item label="销售员工" prop="o2oSearchDTO.salesEmployeeRealName">
                  <el-input
                    v-model.trim="queryParams.o2oSearchDTO.salesEmployeeRealName"
                    :maxlength="MAX_LENGTH.DEFAULT"
                    placeholder="请输入"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <el-form-item label="提货方式" prop="o2oSearchDTO.takingDeliveryWay">
                  <BjSelectLov
                    v-model.trim="queryParams.o2oSearchDTO.takingDeliveryWay"
                    constant-key="ORDER_TAKING_DELIVERY_TYPE_LIST"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <el-form-item label="收货状态" prop="receiveStatus">
                  <BjSelectLov
                    v-model="queryParams.receiveStatus"
                    :option-list="ORDER_INTERNAL_RECEIVING_STATUS_LIST"
                  />
                </el-form-item>
              </el-col>
            </template>
            <!--  end-->
            <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
              <el-col :span="colSpan">
                <el-form-item label="物流单号" prop="expressNo">
                  <BjInputCode
                    v-model.trim="queryParams.expressNo"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
            </template>
            <el-col :span="colSpan">
              <el-form-item label="商品名称/编码" prop="spuName">
                <el-input
                  v-model.trim="queryParams.spuName"
                  :maxlength="MAX_LENGTH.DEFAULT"
                  placeholder="请输入"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="SKU编码" prop="skuCode">
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
              <el-form-item label="订单来源" prop="actionType">
                <BjSelectLov
                  v-model="queryParams.actionType"
                  :option-list="ORDER_ACTION_TYPE_LIST"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="有无赠品" prop="isGift">
                <BjSelectLov v-model="queryParams.isGift" :option-list="STATUS_HAVE_TRUE" />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="买家昵称" prop="buyerNick">
                <el-input
                  v-model.trim="queryParams.buyerNick"
                  :maxlength="MAX_LENGTH.DEFAULT"
                  placeholder="请输入"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="收货人姓名" prop="receiverName">
                <el-input
                  v-model.trim="queryParams.receiverName"
                  :maxlength="MAX_LENGTH.DEFAULT"
                  placeholder="请输入"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <BjFormItemMobile
                v-model.trim="queryParams.mobile"
                prop="mobile"
                label="收货人手机"
                clearable
              />
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="买家备注" prop="buyerRemarks">
                <el-row>
                  <el-col :span="8">
                    <BjSelectLov
                      v-model="queryParams.isBuyerRemarks"
                      :option-list="STATUS_HAVE_TRUE"
                    />
                  </el-col>
                  <el-col class="text-center" :span="1">-</el-col>
                  <el-col :span="15">
                    <el-input
                      v-model="queryParams.buyerRemarks"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      clearable
                    />
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="商家备注" prop="orderRemarks">
                <el-row>
                  <el-col :span="8">
                    <BjSelectLov
                      v-model="queryParams.isOrderRemarks"
                      :option-list="STATUS_HAVE_TRUE"
                    />
                  </el-col>
                  <el-col class="text-center" :span="1">-</el-col>
                  <el-col :span="15">
                    <el-input
                      v-model="queryParams.orderRemarks"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      clearable
                    />
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="标签" prop="tags">
                <BjSelectLov v-model="queryParams.tags" :option-list="BJ_TAGS" multiple />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </template>
      <template v-slot:seniorForm>
        <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
          <el-col :span="firstColSpan" class="text-right">聚合状态：</el-col>
          <el-col :span="24 - firstColSpan">
            <el-row :gutter="20">
              <el-col :span="colSpan">
                <el-form-item label="发货状态" prop="deliveryStatus">
                  <BjSelectLov
                    v-model="queryParams.deliveryStatus"
                    :option-list="ORDER_INTERNAL_DELIVER_STATUS_LIST"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <!-- TODO: -->
                <el-form-item label="收货状态" prop="receiveStatus">
                  <BjSelectLov
                    v-model="queryParams.receiveStatus"
                    :option-list="ORDER_INTERNAL_RECEIVING_STATUS_LIST"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <el-form-item label="挂起状态" prop="suspendStatus">
                  <BjSelectLov
                    v-model="queryParams.suspendStatus"
                    :option-list="ORDER_INTERNAL_HAND_UP_STATUS_LIST"
                  />
                </el-form-item>
              </el-col>
              <!-- <el-col :span="colSpan">
              <el-form-item label="是否缺货" prop="stockFlag">
                <BjSelectLov v-model="queryParams.stockFlag" :option-list="STATUS_YES_NUM" />
              </el-form-item>
            </el-col> -->
              <el-col :span="colSpan">
                <el-form-item label="发货回写状态" prop="syncDeliveryStatus">
                  <BjSelectLov
                    v-model="queryParams.syncDeliveryStatus"
                    :option-list="ORDER_INTERNAL_STOCK_BACK_STATUS_LIST"
                  />
                </el-form-item>
              </el-col>
              <!-- <el-col :span="colSpan">
              <el-form-item label="是否退款" prop="refundStatus">
                <BjSelectLov v-model="queryParams.refundStatus" :option-list="STATUS_YES_NUM" />
              </el-form-item>
            </el-col> -->
            </el-row>
          </el-col>
        </template>
        <el-col :span="firstColSpan" class="text-right">时间查询：</el-col>
        <el-col :span="24 - firstColSpan">
          <el-row :gutter="20">
            <el-col :span="colSpan">
              <el-form-item label="三方下单时间">
                <el-date-picker
                  v-model="queryParamsTime_order"
                  class="full-width"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetimerange"
                  :picker-options="$pickerOptions()"
                  align="right"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :default-time="['00:00:00', '23:59:59']"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="支付时间">
                <el-date-picker
                  v-model="queryParamsTime_pay"
                  class="full-width"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetimerange"
                  :picker-options="$pickerOptions()"
                  align="right"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :default-time="['00:00:00', '23:59:59']"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="内部创建时间">
                <el-date-picker
                  v-model="queryParamsTime_create"
                  class="full-width"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetimerange"
                  :picker-options="$pickerOptions()"
                  align="right"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :default-time="['00:00:00', '23:59:59']"
                />
              </el-form-item>
            </el-col>
            <!-- O2O 没有以下时间  -->
            <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
              <el-col :span="colSpan">
                <el-form-item label="预计发货时间">
                  <el-date-picker
                    v-model="queryParamsTime_estimatedDelivery"
                    class="full-width"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetimerange"
                    :picker-options="$pickerOptions()"
                    align="right"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="['00:00:00', '23:59:59']"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <el-form-item label="发货时间">
                  <el-date-picker
                    v-model="queryParamsTime_delivery"
                    class="full-width"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetimerange"
                    :picker-options="$pickerOptions()"
                    align="right"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="['00:00:00', '23:59:59']"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="colSpan">
                <el-form-item label="确认收货时间">
                  <el-date-picker
                    v-model="queryParamsTime_confirmReceipt"
                    class="full-width"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetimerange"
                    :picker-options="$pickerOptions()"
                    align="right"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="['00:00:00', '23:59:59']"
                  />
                </el-form-item>
              </el-col>
            </template>
          </el-row>
        </el-col>
        <el-col :span="firstColSpan" class="text-right">金额查询：</el-col>
        <el-col :span="24 - firstColSpan">
          <el-row :gutter="20">
            <el-col :span="colSpan">
              <el-form-item label="商品总金额">
                <el-row>
                  <el-col :span="11">
                    <BjInputMoney
                      v-model="queryParams.totalAmountMin"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      placeholder="最小金额"
                      clearable
                    />
                  </el-col>
                  <el-col class="text-center" :span="2">-</el-col>
                  <el-col :span="11">
                    <BjInputMoney
                      v-model="queryParams.totalAmountMax"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      placeholder="最大金额"
                      clearable
                    />
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="买家应付金额">
                <el-row>
                  <el-col :span="11">
                    <BjInputMoney
                      v-model="queryParams.buyerPayMin"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      placeholder="最小金额"
                      clearable
                    />
                  </el-col>
                  <el-col class="text-center" :span="2">-</el-col>
                  <el-col :span="11">
                    <BjInputMoney
                      v-model="queryParams.buyerPayMax"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      placeholder="最大金额"
                      clearable
                    />
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="商品总成本">
                <el-row>
                  <el-col :span="11">
                    <BjInputMoney
                      v-model="queryParams.costPriceMin"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      placeholder="最小金额"
                      clearable
                    />
                  </el-col>
                  <el-col class="text-center" :span="2">-</el-col>
                  <el-col :span="11">
                    <BjInputMoney
                      v-model="queryParams.costPriceMax"
                      :maxlength="MAX_LENGTH.DEFAULT"
                      placeholder="最大金额"
                      clearable
                    />
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <!-- <el-col :span="colSpan">
              <el-form-item label="旗帜" prop="flag"
                ><el-select
                  v-model="queryParams.flag"
                  class="full-width"
                  placeholder="请选择"
                  clearable
                >
                  <el-option value="1" label="1">
                    <span style="float: left">1</span>
                    <i class="el-icon-s-flag flag-one" />
                  </el-option>
                  <el-option value="2" label="2">
                    <span style="float: left">2</span>
                    <i class="el-icon-s-flag flag-two" />
                  </el-option>
                  <el-option value="3" label="3">
                    <span style="float: left">3</span>
                    <i class="el-icon-s-flag flag-three" />
                  </el-option>
                  <el-option value="4" label="4">
                    <span style="float: left">4</span>
                    <i class="el-icon-s-flag flag-four" />
                  </el-option>
                  <el-option value="5" label="5">
                    <span style="float: left">5</span>
                    <i class="el-icon-s-flag flag-five" />
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col> -->
          </el-row>
        </el-col>
      </template>
      <template v-slot:beforeSearchBtn>
        <el-col :span="24 - colSpan">
          <el-tabs :value="queryParamsTradeStatus" type="card" @tab-click="handleTabClick">
            <!-- 全部选项（name='all'）需要做特殊处理 对应 queryParams.orderStatus = null -->
            <el-tab-pane key="all" label="全部" name="all" />
            <el-tab-pane
              v-for="(item, index) in ORDER_INTERNAL_ORDER_STATUS_LIST"
              :key="index + 1"
              :label="item.label"
              :name="item.value"
            />
          </el-tabs>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission="addPermissionName"
            type="primary"
            class="main-btn"
            @click="onCreate"
            >创建订单</el-button
          >
          <!-- <el-button type="primary">Excel导入</el-button> -->
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
            :show-overflow-tooltip="col.prop !== 'bjTableAction'"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <template v-else-if="col.prop === 'outerOrderNo'">
                <span
                  class="link"
                  @click="$toBusinessOrderThirdView(scope.row.outerOrderNo, false, businessType)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'innerOrderNo'">
                <span
                  class="link"
                  @click="$toBusinessOrderInternalView(scope.row.innerOrderNo, false, businessType)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'tagsList'">
                <BjTagList :tag-code-array="scope.row[col.prop]" size="mini" circle />
              </template>
              <template v-else-if="col.prop === 'goodsQuatity'">
                <span class="link" @click="onSkuDetail(scope.row)">{{ scope.row[col.prop] }}</span>
              </template>
              <template
                v-else-if="
                  col.prop === 'totalAmount' || col.prop === 'buyerPay' || col.prop === 'costPrice'
                "
              >
                {{ $number2money(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'deliveryStatus'">
                <el-tag :type="scope.row[col.prop] === '30512' ? 'success' : 'info'">{{
                  ORDER_INTERNAL_DELIVER_STATUS_LIST.bjGet(scope.row[col.prop])
                }}</el-tag>
              </template>
              <template v-else-if="col.prop === 'receivingStatus'">
                <el-tag :type="scope.row[col.prop] === '30522' ? 'success' : 'info'">{{
                  ORDER_INTERNAL_RECEIVING_STATUS_LIST.bjGet(scope.row[col.prop])
                }}</el-tag>
              </template>
              <template v-else-if="col.prop === 'stockFlag'">
                {{ STATUS_YES_NUM.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'isGift'">
                <el-tag :type="scope.row[col.prop] ? 'success' : 'info'">{{
                  STATUS_HAVE_TRUE.bjGet(scope.row[col.prop])
                }}</el-tag>
              </template>
              <template v-else-if="col.prop === 'actionType'">
                {{ ORDER_ACTION_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'businessType'">
                {{ ORDER_BUSINESS_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'takingDeliveryWay'">
                {{ ORDER_TAKING_DELIVERY_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'sourceStatus'">
                {{ ORDER_INTERNAL_SOURCE_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'suspendStatus'">
                {{ ORDER_INTERNAL_HAND_UP_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'syncDeliveryStatus'">
                {{ ORDER_INTERNAL_STOCK_BACK_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <!-- <template v-else-if="col.prop === 'orderStatus'">
                <el-tag :type="scope.row[col.prop] === 2 ? 'success' : 'info'">{{
                  ORDER_INTERNAL_ORDER_STATUS_LIST.bjGet(scope.row[col.prop])
                }}</el-tag>
              </template> -->
              <template v-else-if="col.prop === 'mobile'">
                {{ scope.row[col.prop] | phoneFilter }}
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
              <el-button
                v-if="!props.row.releaseStatus"
                type="text"
                class="delete-btn"
                @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 弹窗 -->
    <el-dialog :title="goodsInfoTab.title" :visible.sync="goodsInfoTab.visible" width="80%">
      <GoodsInfoTab :order-id="goodsInfoTab.orderId" :business-type="businessType" />
    </el-dialog>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { orderApi } from '@/api';
import GoodsInfoTab from './GoodsInfoTab';
import {
  ORDER_TRADE_TYPE_LIST,
  ORDER_BUSINESS_TYPE_LIST,
  ORDER_ACTION_TYPE_LIST,
  STATUS_YES_NUM,
  STATUS_HAVE_TRUE,
  ORDER_INTERNAL_ORDER_STATUS_LIST,
  ORDER_INTERNAL_PREEMPT_BACK_STATUS_LIST,
  ORDER_INTERNAL_SOURCE_STATUS_LIST,
  ORDER_INTERNAL_DELIVER_STATUS_LIST,
  ORDER_INTERNAL_RECEIVING_STATUS_LIST,
  ORDER_INTERNAL_STOCK_BACK_STATUS_LIST,
  ORDER_INTERNAL_HAND_UP_STATUS_LIST,
  ORDER_INTERNAL_REFUND_STATUS_LIST,
  ORDER_TAKING_DELIVERY_TYPE_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
// import tableData from './data.json';
export default {
  name: 'BusinessOrderInternalListPageIndex',
  components: { GoodsInfoTab },
  mixins: [pageMiXin],
  props: {
    businessType: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      selection: false,
      firstColSpan: 3,
      colSpan: 8,
      ORDER_TRADE_TYPE_LIST,
      ORDER_BUSINESS_TYPE_LIST,
      ORDER_ACTION_TYPE_LIST,
      STATUS_YES_NUM,
      STATUS_HAVE_TRUE,
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      ORDER_INTERNAL_PREEMPT_BACK_STATUS_LIST,
      ORDER_INTERNAL_SOURCE_STATUS_LIST,
      ORDER_INTERNAL_DELIVER_STATUS_LIST,
      ORDER_INTERNAL_RECEIVING_STATUS_LIST,
      ORDER_INTERNAL_STOCK_BACK_STATUS_LIST,
      ORDER_INTERNAL_HAND_UP_STATUS_LIST,
      ORDER_INTERNAL_REFUND_STATUS_LIST,
      ORDER_TAKING_DELIVERY_TYPE_LIST,
      ORDER_BUSINESS_TYPES,
      queryApi: orderApi.getInternalPage,
      // 查询参数
      queryParamsTime_order: [],
      queryParamsTime_pay: [],
      queryParamsTime_create: [],
      queryParamsTime_estimatedDelivery: [],
      queryParamsTime_delivery: [],
      queryParamsTime_confirmReceipt: [],
      queryParams: {
        businessType: this.businessType, // (string, optional): 订单业务类型，如：手工销售订单、直营销售订单、菜鸟外仓销售订单、京东外仓销售订单、直营退货退款订单、平台O2O销售订单、平台预售定金单等 ,
        actionType: null, // (string, optional): 订单创建类型：普通转单、手工单、复制单、导入单 ,
        buyerNick: null, // (string, optional): 买家昵称 ,
        buyerPayMax: null, // (number, optional): 买家应付最大金额 ,
        buyerPayMin: null, // (number, optional): 买家应付最小金额 ,
        buyerRemarks: null, // (string, optional): 买家备注/留言 ,
        channel: null, // (string, optional): 渠道 ,
        costPriceMax: null, // (number, optional): 总成本最大金额 ,
        costPriceMin: null, // (number, optional): 总成本最小金额 ,
        creationEndDate: null, // (string, optional): 内部单创建结束时间 ,
        creationStartDate: null, // (string, optional): 内部单创建起始时间 ,
        deliveryEndTime: null, // (string, optional): 发货结束时间 ,
        deliveryStartTime: null, // (string, optional): 发货起始时间 ,
        deliveryStatus: null, // (string, optional): 聚合商品行的发（收）货状态 ,
        expressNo: null, //  (string, optional): 物流单号
        isBuyerRemarks: null, // (boolean, optional): 买家是否有备注 ,
        isGift: null, // (boolean, optional): 有无赠品 ,
        isServiceRemarks: null, // (boolean, optional): 商家是否有备注 ,
        isOrderRemarks: null, // (string, optional): 订单备注 ,
        mobile: null, // (string, optional): 收货人手机号码 ,
        orderStatus: null, // (string, optional): 订单tab页切换 ,
        orderEndTime: null, // (string, optional): 三方下单结束时间 ,
        orderNo: null, // (string, optional): 第三方/内部订单号 ,
        orderStartTime: null, // (string, optional): 三方下单起始时间 ,
        payEndTime: null, // (string, optional): 支付结束时间 ,
        payStartTime: null, // (string, optional): 支付起始时间 ,
        predictDeliveryEndTime: null, // (string, optional): 预计发货结束时间 ,
        predictDeliveryStartTime: null, // (string, optional): 预计发货起始时间 ,
        preemptionStatus: null, // (string, optional): 聚合商品行的预占状态 ,
        receivedEndTime: null, // (string, optional): 确认收货结束时间 ,
        receivedStartTime: null, // (string, optional): 确认收货起始时间 ,
        receiverName: null, // (string, optional): 收货人姓名 ,
        receiveStatus: null, // 收货状态
        serviceRemarks: null, // (string, optional): 客服备注 ,
        orderRemarks: null, // (string, optional): 订单备注 ,
        skuCode: null, // (string, optional): SKU编码 ,
        sourceStatus: null, // (string, optional): 聚合商品行的寻源状态 ,
        spuName: null, // (string, optional): SPU编码或者名称 ,
        stockFlag: null, // (boolean, optional): 是否缺货 ,
        storeId: null, // (integer, optional): 店铺id ,
        storeIdList: [],
        suspendStatus: null, // (boolean, optional): 订单级-挂起状态：0-正常，1-挂起成功，2-挂起失败，3-解挂成功，4-解挂失败 ,
        syncDeliveryStatus: null, //  (string, optional): 发货回写状态 ,
        syncPreempStatus: null, // (string, optional): 预占回写状态 ,
        tags: null, // (Array[string], optional): 标签
        totalAmountMax: null, // (number, optional): 商品总金额最大金额 ,
        totalAmountMin: null, // (number, optional): 商品总金额最小金额 ,
        tradeType: null, // (string, optional): 订单交易类型：销售单、仅退款单、退货退款单、换货单
        refundStatus: null, // 退款状态 ,
        o2oSearchDTO: {
          salesEmployeeRealName: null,
          takingDeliveryStore: [],
          takingDeliveryWay: null,
        },
      },
      goodsInfoTab: {
        title: '商品信息',
        visible: false,
        orderId: null,
      },
      labelsVersion: 11,
      queryParamsTradeStatus: null,
    };
  },
  computed: {
    labelList() {
      const ARR = [
        { label: '三方订单号', prop: 'outerOrderNo', isCheck: true, disabled: false, width: '160' },
        { label: '内部订单号', prop: 'innerOrderNo', isCheck: true, disabled: false, width: '160' },
        { label: '标签', prop: 'tagsList', isCheck: true, disabled: false, width: '120' },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelTypeMeaning', isCheck: true, disabled: false },
        { label: '销售员工', prop: 'salesEmployeeRealName', isCheck: true, disabled: false },
        { label: '提货店铺', prop: 'takingDeliveryStoreName', isCheck: true, disabled: false },
        { label: '订单来源', prop: 'actionType', isCheck: true, disabled: false },
        { label: '商品行数量', prop: 'goodsQuatity', isCheck: true, disabled: false, width: '90' },
        { label: '买家昵称', prop: 'buyerNick', isCheck: true, disabled: false },
        { label: '买家备注', prop: 'buyerRemarks', isCheck: true, disabled: false },
        { label: '商家备注', prop: 'orderRemarks', isCheck: true, disabled: false },
        { label: '提货方式', prop: 'takingDeliveryWay', isCheck: true, disabled: false },
        { label: '收货人姓名', prop: 'receiverName', isCheck: true, disabled: false, width: '90' },
        { label: '收货人手机号', prop: 'mobile', isCheck: true, disabled: false, width: '100' },
        { label: '收货人地址', prop: 'address', isCheck: true, disabled: false, width: '90' },
        {
          label: '发货状态',
          prop: 'deliveryStatus',
          isCheck: true,
          disabled: false,
          width: '95',
        },
        { label: '物流单号', prop: 'logisticsNo', isCheck: true, disabled: false, width: '160' },
        { label: '收货状态', prop: 'receivingStatus', isCheck: true, disabled: false, width: '95' },
        { label: '商品总金额', prop: 'totalAmount', isCheck: true, disabled: false, width: '90' },
        { label: '买家应付', prop: 'buyerPay', isCheck: true, disabled: false, width: '90' },
        { label: '商品总成本', prop: 'costPrice', isCheck: true, disabled: false, width: '90' },
        { label: '三方下单时间', prop: 'orderTime', isCheck: true, disabled: false, width: '135' },
        { label: '支付时间', prop: 'payTime', isCheck: true, disabled: false, width: 135 },
        {
          label: '内部创单时间',
          prop: 'creationDate',
          isCheck: true,
          disabled: false,
          width: '135',
        },
        {
          label: '预计发货时间',
          prop: 'predictDeliveryTime',
          isCheck: true,
          disabled: false,
          width: '135',
        },
        { label: '挂起状态', prop: 'suspendStatus', isCheck: true, disabled: false, width: '95' },
        {
          label: '发货回写状态',
          prop: 'syncDeliveryStatus',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        { label: '有无赠品', prop: 'isGift', isCheck: true, disabled: false, width: '80' },
      ];
      if (this.businessType == ORDER_BUSINESS_TYPES.O2O) {
        const NOT_IN_O2O_STR =
          'logisticsNo,deliveryStatus,preemptionStatus,sourceStatus,suspendStatus,syncPreempStatus,syncDeliveryStatus,predictDeliveryTime';
        NOT_IN_O2O_STR.split(',').forEach(key => {
          const INDEX = ARR.findIndex(col => col.prop == key);
          if (INDEX > -1) {
            ARR.splice(INDEX, 1);
          }
        });
      }
      if (this.businessType == ORDER_BUSINESS_TYPES.COMMON) {
        const NOT_IN_O2O_STR = 'salesEmployeeRealName,takingDeliveryStoreName,takingDeliveryWay';
        NOT_IN_O2O_STR.split(',').forEach(key => {
          const INDEX = ARR.findIndex(col => col.prop == key);
          if (INDEX > -1) {
            ARR.splice(INDEX, 1);
          }
        });
      }
      return ARR;
    },
    SCHL_CHANNEL_TYPE() {
      return this.$store.state.values.SCHL_CHANNEL_TYPE || [];
    },
    BJ_TAGS() {
      return this.$store.state.values.BJ_TAGS || [];
    },
    addPermissionName() {
      return this.businessType == ORDER_BUSINESS_TYPES.COMMON
        ? 'BusinessOrderInternalAdd'
        : 'BusinessO2oInternalAdd';
    },
  },
  watch: {
    queryParamsTime_order(v) {
      this.queryParams.orderStartTime = (v || [])[0];
      this.queryParams.orderEndTime = (v || [])[1];
    },
    queryParamsTime_create(v) {
      this.queryParams.creationStartDate = (v || [])[0];
      this.queryParams.creationEndDate = (v || [])[1];
    },
    queryParamsTime_pay(v) {
      this.queryParams.payStartTime = (v || [])[0];
      this.queryParams.payEndTime = (v || [])[1];
    },
    queryParamsTime_estimatedDelivery(v) {
      this.queryParams.predictDeliveryStartTime = (v || [])[0];
      this.queryParams.predictDeliveryEndTime = (v || [])[1];
    },
    queryParamsTime_delivery(v) {
      this.queryParams.deliveryStartTime = (v || [])[0];
      this.queryParams.deliveryEndTime = (v || [])[1];
    },
    queryParamsTime_confirmReceipt(v) {
      this.queryParams.receivedStartTime = (v || [])[0];
      this.queryParams.receivedEndTime = (v || [])[1];
    },
    'queryParams.orderStatus': {
      handler(v) {
        // 全部选项（name='all'）需要做特殊处理 对应 queryParams.orderStatus = null
        this.queryParamsTradeStatus = v || 'all';
        console.log('queryParams.orderStatus   ', v);
        // TODO:这里执行一次搜索
        this.reFresh();
      },
      immediate: true,
    },
  },
  mounted() {
    this.$store.dispatch('values/getTags');
  },
  methods: {
    handleTabClick(tab, event) {
      // 全部选项（name='all'）需要做特殊处理 对应 queryParams.orderStatus = null
      this.queryParams.orderStatus = tab.name == 'all' ? null : tab.name;
      console.log('handleTabClick.handleTabClick   ', tab.name);
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime_order = null;
      this.queryParamsTime_create = null;
      this.queryParamsTime_pay = null;
      this.queryParamsTime_estimatedDelivery = null;
      this.queryParamsTime_delivery = null;
      this.queryParamsTime_confirmReceipt = null;
      this.queryParams.totalAmountMin = null;
      this.queryParams.totalAmountMax = null;
      this.queryParams.buyerPayMin = null;
      this.queryParams.buyerPayMax = null;
      this.queryParams.costPriceMin = null;
      this.queryParams.costPriceMax = null;
      this.queryParams.isBuyerRemarks = null;
      this.queryParams.isOrderRemarks = null;
      this.queryParams.isServiceRemarks = null;
      this.queryParams.storeIdList = null;
      this.queryParams.tags = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 创建订单 */
    onCreate() {
      if (this.businessType == ORDER_BUSINESS_TYPES.COMMON) {
        this.$toPage('BusinessOrderInternalAdd');
      }
      if (this.businessType == ORDER_BUSINESS_TYPES.O2O) {
        this.$toPage('BusinessO2oInternalAdd');
      }
    },
    /** 查看商品信息 */
    onSkuDetail(row) {
      this.goodsInfoTab.orderId = row.id;
      this.goodsInfoTab.visible = true;
    },
    /** 禁用 */
    onStatus(flag, row) {
      console.log(flag, row);
      this.operationApi({
        flag: flag,
        id: row.id,
        objectVersionNumber: row.objectVersionNumber,
      });
    },
    /** 删除 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ id: row.id });
        })
        .catch(() => {});
    },
    operationApi(API_DATA) {
      orderApi.operationStockProcess(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      orderApi.deleteStockProcess(API_DATA).then(res => {
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
<style lang="scss" scoped>
.el-icon-s-flag {
  float: right;
  line-height: 34px;
}
</style>
