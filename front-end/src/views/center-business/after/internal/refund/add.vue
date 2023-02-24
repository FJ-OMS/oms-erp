<!--
 * @Author: your name
 * @Date: 2021-09-30 10:21:32
 * @LastEditTime: 2022-06-10 15:13:33
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\after\internal\refund\add.vue
-->
<template>
  <div class="app-container bj-label-colon">
    <el-form ref="form" :model="form" :rules="rules" inline label-width="130px">
      <BjDivider
        >关联销售单
        <template #button>
          <el-button type="primary" class="xs mb16" @click="handleShowFnForDialogOrderList"
            >选择关联销售单</el-button
          >
        </template>
      </BjDivider>
      <InternalOrderList :data="orderRefundInfoDTO" />
      <BjDivider>基础信息</BjDivider>
      <el-form-item label="退款单类型" prop="refundInfoDTO.orderRefundType">
        <BjSelectLov
          v-model="form.refundInfoDTO.orderRefundType"
          :option-list="INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST"
        />
      </el-form-item>
      <el-form-item label="退款类型" prop="refundInfoDTO.refundType">
        <BjSelectLov
          v-model="form.refundInfoDTO.refundType"
          :option-list="INTERNAL_AFTER_REFUND_TYPE_LIST"
        />
      </el-form-item>
      <el-form-item label="三方退款单号" prop="refundInfoDTO.outerRefundNo">
        <BjInputCode v-model="form.refundInfoDTO.outerRefundNo" />
      </el-form-item>
      <el-form-item label="三方退款时间" prop="refundInfoDTO.outReturnTime">
        <el-date-picker
          v-model="form.refundInfoDTO.outReturnTime"
          class="full-width"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="请选择"
          clearable
        />
      </el-form-item>
      <el-form-item label="退款原因" prop="refundInfoDTO.reason">
        <BjSelectLov
          v-model="form.refundInfoDTO.reason"
          lov-code="BJ_AFTER_REFUND_ONLY_REASONS"
          item-useable="status"
        />
      </el-form-item>
      <el-form-item label="退款阶段" prop="refundInfoDTO.stage">
        <BjSelectLov
          v-model="form.refundInfoDTO.stage"
          :option-list="INTERNAL_AFTER_STAGE_STATUS"
        />
      </el-form-item>
      <div>
        <el-form-item label="客服备注" prop="refundInfoDTO.serviceRemarks">
          <BjTextarea v-model="form.refundInfoDTO.serviceRemarks" placeholder="客服备注" />
        </el-form-item>
      </div>
      <BjDivider>财务信息</BjDivider>
      <el-form-item label="结算方式" prop="refundInfoDTO.payType">
        <BjSelectLov
          v-model="form.refundInfoDTO.payType"
          :option-list="AFTER_INTERNAL_RETURN_PAY_LIST"
        />
      </el-form-item>
      <BjDivider
        >商品信息
        <template #button>
          <el-button type="primary" class="xs mb16" @click="handleShowFnForDialogSkuList"
            >添加退款商品</el-button
          >
        </template>
      </BjDivider>
      <SkuList
        ref="SkuList"
        :row-key="ROW_KEY"
        :parent-query-api="parentQueryApi"
        :children-query-api="childrenQueryApi"
        :parent-label-list="parentLabelList"
        :parent-columns-btn="parentColumnsBtn"
        @setSkuTableData="setSkuTableData"
      />
      <BjDivider>图片附件</BjDivider>
      <div class="mt20">
        <el-form-item label="" style="display: block; height: 100%">
          <BjUpload
            ref="BjUpload"
            class="avatar-uploader"
            :limit="20"
            :limit-size="5 * 1024"
            multiple
            show-file-list
            list-type="picture-card"
            accept="image/png,image/jpeg"
            :accept-list="['image/jpeg', 'image/png']"
            auto-upload
            limit-hide
            @afterUpload="afterUpload"
            @remove="onRemoveUrl"
          >
            <template slot="default">
              <i class="el-icon-plus avatar-uploader-icon" />
            </template>
            <template slot="tip">
              <!-- 支持上传多附件，非必填 -->
              <!-- 可拖拽图片调整顺序,支持上传文件格式、尺寸根据各平台与定，大小不超过5M -->
            </template>
          </BjUpload>
        </el-form-item>
      </div>
      <div class="text-center mt20 mb20">
        <el-button style="width: 150px" @click="cancel">取 消</el-button>
        <el-button type="primary" style="width: 150px" @click="onConfirm">确 定</el-button>
      </div>
    </el-form>
    <!-- 选择订单,  初始  全部待发货  全部发货失败-->
    <BjDialogList
      ref="BjDialogOrderList"
      width="80%"
      :config="
        $BjDialogListConfig.innerOrderDefault('选择关联销售单', {
          defaultQuery: { deliveryStatusList: ['30510', '30511', '30513'] },
        })
      "
      tag-name="innerOrderNo"
      @confirm="confirmForDialogOrderList"
    />
    <!-- 选择商品 -->
    <BjDialogList
      ref="BjDialogSkuList"
      width="80%"
      :config="
        $BjDialogListConfig.refundGoodsDefault('选择退款的商品', {
          defaultQuery: { innerOrderNo: form.orderRefundInfoDTO.innerOrderNo },
        })
      "
      :row-key="ROW_KEY"
      :reset-data="resetDataFnForDialogSkuList"
      multiple
      search-btn-hide
      :disabled="SKU_DISABLED_ID_LIST"
      :load="loadMethodDialogSkuList"
      :row-style="$treeRowStyleFun"
      @confirm="confirmForDialogSkuList"
      @afterFetchData="afterFetchDataForDialogSkuList"
    >
      <template #tableTop>
        <div class="mb10">
          <span
            >关联销售单：<span class="link">{{ form.orderRefundInfoDTO.innerOrderNo }}</span></span
          >
        </div>
      </template>
      <template #tableColumn>
        <el-table-column prop="other" label="其它" min-width="400">
          <template slot-scope="scope">
            <div v-if="scope.row.BJ_HAS_CHILD">
              <span class="table-other-td-item"
                >未发货数量： {{ scope.row.undeliveredQuantity }}</span
              >
              <span class="table-other-td-item">已退款数量： {{ scope.row.quantityRefunded }}</span>
              <span class="table-other-td-item"
                >可退款数量： {{ scope.row.refundableQuantity }}</span
              >
            </div>
            <div v-if="!scope.row.BJ_HAS_CHILD">
              <span class="table-other-td-item">
                发货状态：{{
                  ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST.bjGet(scope.row.deliveryStatus)
                }}</span
              >
            </div>
          </template>
        </el-table-column>
      </template>
    </BjDialogList>
  </div>
</template>
<script>
import ElTableColumn from '@/components/ElTableColumn/index.vue';
import viewMiXin from '@/utils/view-mixin.js';
import { afterApi, orderApi } from '@/api';
import {
  INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
  AFTER_INTERNAL_RETURN_PAY_LIST,
  STATUS_YES_NUM,
  INTERNAL_AFTER_REFUND_TYPE_LIST,
  INTERNAL_AFTER_STAGE_STATUS,
  INTERNAL_AFTER_REASON_STATUS,
  ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
  ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST,
  ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST,
} from '@/utils/constants';
import InternalOrderList from '@/views/center-business/after/components/InternalOrderList';
import SkuList from '@/views/center-business/after/components/SkuList';
import BjUpload from '@/components/BjUpload/index';
const getDefaultForm = () => {
  return {
    items: [
      // {
      //   _innerMap: {},
      //   deleteFlag: null,
      //   giftFlag: null,
      //   id: null,
      //   innerOrderNo: null,
      //   originItemId: null,
      //   outerOrderNo: null,
      //   outerRefundNo: null,
      //   quantity: null,
      //   skuCode: null,
      //   skuId: null,
      //   skuName: null,
      //   spuCode: null,
      //   spuName: null,
      //   spuType: null,
      //   type: null,
      //   unitPrice: null,
      //   itemLines: [
      // {
      //   _innerMap: {},
      //   deleteFlag: null,
      //   id: null,
      //   innerOrderNo: null,
      //   innerRefundItemId: null,
      //   innerRefundNo: null,
      //   originItemLineId: null,
      //   outerRefundNo: null,
      //   skuCode: null,
      //   skuId: null,
      //   skuName: null,
      //   spuCode: null,
      //   spuName: null,
      //   spuType: null,
      //   type: null,
      // },
      // ],
      // },
    ],
    orderRefundInfoDTO: {
      actionType: null,
      address: null,
      businessType: null,
      buyerNick: null,
      buyerPay: null,
      buyerRemarks: null,
      channel: null,
      channelTypeMeaning: null,
      costPrice: null,
      creationDate: null,
      deliveryStatus: null,
      goodsQuantity: null,
      originOrderId: null,
      id: null,
      innerOrderNo: null,
      isGift: false,
      logisticsNo: [],
      mobile: null,
      orderRemarks: null,
      orderStatus: null,
      orderTime: null,
      outerOrderNo: null,
      payTime: null,
      predictDeliveryTime: null,
      preemptionStatus: null,
      receiverName: null,
      refundStatus: null,
      serviceRemarks: null,
      sourceStatus: null,
      stockFlag: false,
      storeId: null,
      storeName: null,
      suspendStatus: null,
      syncDeliveryStatus: null,
      syncPreempStatus: null,
      tagsList: [],
      totalAmount: null,
    },
    refundInfoDTO: {
      accessory: [],
      outReturnTime: null,
      outerRefundNo: null,
      payType: null,
      reason: null,
      orderRefundType: null,
      refundType: null,
      serviceRemarks: null,
      stage: null,
      status: null,
    },
  };
};
const ROW_KEY = 'BJ_EBC_ID';
const PARENT_KEY = 'originItemId';
export default {
  name: 'BusinessAfterInternalRefundAdd',
  components: {
    ElTableColumn,
    InternalOrderList,
    SkuList,
    BjUpload,
  },
  mixins: [viewMiXin],
  data() {
    return {
      ROW_KEY,
      pageName: 'BusinessAfterInternalRefundAdd',
      INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
      AFTER_INTERNAL_RETURN_PAY_LIST,
      STATUS_YES_NUM,
      INTERNAL_AFTER_REFUND_TYPE_LIST,
      INTERNAL_AFTER_STAGE_STATUS,
      INTERNAL_AFTER_REASON_STATUS,
      ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
      ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST,
      ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST,
      form: getDefaultForm(),
      rules: {
        'refundInfoDTO.orderRefundType': [
          { required: true, message: '请选择退款单类型', trigger: 'change' },
        ],
        'refundInfoDTO.refundType': [
          { required: true, message: '请选择退款类型', trigger: 'change' },
        ],
        'refundInfoDTO.outerRefundNo': [
          { required: true, message: '请输入三方退款单号', trigger: 'blur' },
        ],
        'refundInfoDTO.outReturnTime': [
          { required: true, message: '请选择三方退款时间', trigger: 'change' },
        ],
        'refundInfoDTO.reason': [{ required: true, message: '请选择退款原因', trigger: 'change' }],
        'refundInfoDTO.stage': [{ required: true, message: '请选择退款阶段', trigger: 'change' }],
        'refundInfoDTO.payType': [{ required: true, message: '请选择结算方式', trigger: 'change' }],
      },
      /** ************* 弹窗  BjDialogSkuList 展开表格的配置  START **************** */
      SKU_DISABLED_ID_LIST: [],
      /** ************* 弹窗 BjDialogSkuList 展开表格的配置  END **************** */
      /** ************* 底部 商品信息  SkuList 配置  START **************** */
      skuTableData: [], // 数据
      parentColumnsBtn: [{ text: '删除', click: this.handleSkuLineDeleteFnForDialogSkuList }], // 主表操作按钮
      parentLabelList: [
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU名称', prop: 'skuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: '是否赠品', prop: 'giftFlag', isCheck: true, disabled: false, width: '90' },
        { label: '商品行数量', prop: 'totalQuantity', isCheck: true, disabled: false, width: '90' },
        {
          label: '可退款数量',
          prop: 'refundableQuantity',
          isCheck: true,
          disabled: false,
          width: '90',
        },
        { label: '退款数量', prop: 'quantity', isCheck: true, disabled: false, width: '90' },
        { label: '退款单价', prop: 'unitPrice', isCheck: true, disabled: false, width: '90' },
        { label: '退款合计', prop: 'allPrice', isCheck: true, disabled: false, width: '90' },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true },
      ],
      /** *************  底部 商品信息 SkuList 配置  END **************** */
    };
  },
  computed: {
    orderRefundInfoDTO() {
      return this.form.orderRefundInfoDTO.id ? [this.form.orderRefundInfoDTO] : [];
    },
  },
  mounted() {
    if (this.$route.query.innerOrderNo) {
      orderApi
        .getInternalPage({
          orderNo: this.$route.query.innerOrderNo,
          deliveryStatusList: ['30510', '30511', '30513'],
          size: 1,
          page: 0,
        })
        .then(res => {
          if (res && res.content) {
            this.confirmForDialogOrderList(res.content);
          }
        });
    }
  },
  methods: {
    setSkuTableData(row) {
      const index = this.skuTableData.findIndex(e => e[ROW_KEY] == row[ROW_KEY]);
      this.skuTableData.splice(index, 1, row);
    },
    // 获取 上传的 新数据 并 合并到旧数据
    afterUpload(url) {
      this.form.refundInfoDTO.accessory.push(url);
      // const LIST = this.getImgListForUploadShow(this.form.refundInfoDTO.accessory);
      // this.$refs.BjUpload.setFileList(LIST);
    },
    onRemoveUrl(file) {
      this.form.refundInfoDTO.accessory.splice(
        this.form.refundInfoDTO.accessory.findIndex(e => e == file.url),
        1,
      );
    },
    /** 确定 */
    onConfirm() {
      console.log(this.form.orderRefundInfoDTO);
      if (!this.form.orderRefundInfoDTO.innerOrderNo) {
        this.$message.warning('请先选择关联销售单');
        return;
      }
      if (!this.skuTableData.length) {
        this.$message.warning('请先添加退款商品');
        return;
      }
      console.log('确认', this.form);
      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.toConfirmApi();
      });
    },
    toConfirmApi() {
      const API_DATA = this.$lodash.cloneDeep(this.form);
      const originOrderId = API_DATA.orderRefundInfoDTO.id;
      delete API_DATA.orderRefundInfoDTO.id;
      API_DATA.orderRefundInfoDTO['originOrderId'] = originOrderId;
      API_DATA.items = [];
      this.skuTableData.forEach(e => {
        const itemLines = [];
        if (e.BJ_SELECT_CHILD && e.BJ_SELECT_CHILD.length) {
          itemLines.push(...e.BJ_SELECT_CHILD);
        }
        delete e.BJ_SELECT_CHILD;
        API_DATA.items.push({ ...e, itemLines });
      });
      API_DATA.refundInfoDTO.accessory = API_DATA.refundInfoDTO.accessory.join();
      console.log('提交API_DATA', API_DATA);
      this.addApi(API_DATA);
    },
    /** 选择关联销售单  START */
    // 显示
    handleShowFnForDialogOrderList() {
      const defaultSelectList = this.form.orderRefundInfoDTO.id
        ? [this.form.orderRefundInfoDTO]
        : [];
      this.$refs.BjDialogOrderList.show(defaultSelectList);
    },
    // 确认
    confirmForDialogOrderList(data) {
      if (Array.isArray(data) && data.length) {
        this.form.orderRefundInfoDTO = data[0];
      } else {
        this.form.orderRefundInfoDTO = {};
      }
      this.skuTableData = [];
      this.$refs.SkuList.reFresh();
    },
    /** 选择关联销售单  END */
    /** *****         BjDialogSkuList    START ********* */
    // 删除
    handleSkuLineDeleteFnForDialogSkuList(row, index) {
      console.log('handleSkuLineDeleteFnForDialogSkuList', row);
      this.$confirm('是否确定删除该数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 主表
        if (row.BJ_HAS_CHILD) {
          this.skuTableData.splice(index, 1);
          // 展开的子表
        } else {
          const INDEX = this.skuTableData.findIndex(e => e[ROW_KEY] == row.BJ_PARENT_ID);
          const PARENT_ROW = this.$lodash.clone(this.skuTableData[INDEX]);
          const CHILD_TABLE = this.$lodash.clone(PARENT_ROW.BJ_SELECT_CHILD);
          // 删除1行
          CHILD_TABLE.splice(index, 1);
          PARENT_ROW.quantity -= 1;
          // 回写table
          PARENT_ROW.BJ_SELECT_CHILD = CHILD_TABLE;
          // 子表删除某行后，总行数为空时，同步删除挂载的父行。
          if (CHILD_TABLE.length == 0) {
            this.skuTableData.splice(INDEX, 1);
          } else {
            this.skuTableData.splice(INDEX, 1, PARENT_ROW);
          }
        }
        this.$refs.SkuList.reFresh();
        this.msgSuccess('删除成功');
      });
    },
    // BjDialogSkuList 选择商品 show
    handleShowFnForDialogSkuList() {
      if (this.form.orderRefundInfoDTO.innerOrderNo) {
        this.$nextTick(() => {
          this.$refs.BjDialogSkuList.show(this.skuTableData);
        });
      } else {
        this.$message.warning('请先选择关联销售单');
      }
    },
    // BjDialogSkuList 选择商品 重置数据，加 BJ_SELECT_CHILD 字段
    resetDataFnForDialogSkuList(data) {
      return data.map(e => {
        const OLD_ROW = this.skuTableData.find(oldItem => e[PARENT_KEY] == oldItem[ROW_KEY]);
        if (OLD_ROW) {
          const FLAG = OLD_ROW.quantity < e.refundableQuantity;
          console.log(FLAG);
        }
        return {
          BJ_EBC_ID: e[PARENT_KEY],
          BJ_HAS_CHILD: true,
          BJ_PARENT_ID: null,
          BJ_SELECT_CHILD: OLD_ROW?.BJ_SELECT_CHILD || [],
          ...e,
        };
      });
    },
    // 半选
    afterFetchDataForDialogSkuList(res) {
      this.SKU_DISABLED_ID_LIST = (res.content || [])
        .filter(e => e.refundableQuantity == 0)
        .map(e => e[PARENT_KEY]);
    },
    loadMethodDialogSkuList({ row }) {
      const PARENT_ROW = { ...row };
      console.log('loadMethodDialogSkuList', JSON.parse(JSON.stringify(PARENT_ROW)));
      // 异步加载子节点
      return new Promise((resolve, reject) => {
        console.log('loadMethodDialogSkuList inPromise');
        if (row.refundableQuantity == 0) {
          this.$message.warning('可退款数量为0');
          resolve([]);
          return;
        }
        afterApi
          .getRefundOnlyItemListByItemId({
            itemId: PARENT_ROW[PARENT_KEY],
          })
          .then(res => {
            const TEMP = (Array.isArray(res) ? res : []).map(e => {
              return {
                ...e,
                BJ_EBC_ID: e['originItemLineId'],
                BJ_HAS_CHILD: false,
                BJ_PARENT_ID: PARENT_ROW[ROW_KEY],
                refundableQuantity: 1,
                quantity: 1,
                unitPrice: this.$number2money(row.unitPrice || 0),
                allPrice: this.$number2money(row.unitPrice || 0),
              };
            });
            if (TEMP.length) {
              resolve(TEMP);
            } else {
              this.$message.warning('返回数据为空，请联系管理员');
              reject();
            }
          });
      });
    },
    // 主表查询API
    parentQueryApi(queryParams) {
      const start = queryParams.currentPage * queryParams.pageSize;
      const end = queryParams.page * queryParams.pageSize;
      return new Promise(resolve => {
        resolve({
          content: this.skuTableData.slice(start, end),
          totalElements: this.skuTableData.length, // 数据总数
        });
      });
    },
    // 子表查询API
    childrenQueryApi(PARENT_ROW) {
      return afterApi.getRefundOnlyItemListByItemId({
        itemId: PARENT_ROW[PARENT_KEY],
      });
    },
    // BjDialogSkuList 选择商品 confirm
    confirmForDialogSkuList(tags) {
      console.log('confirmForDialogSkuList tags', JSON.parse(JSON.stringify(tags)));
      this.skuTableData = tags.map(e => {
        // e.BJ_SELECT_CHILD 选中的子集
        // 退款数量： 当有选中商品行时，以选中商品行length为退款数量，没有选中时，以可退数量为退款数量
        const QUANTITY = Number(e.BJ_SELECT_CHILD.length || e.refundableQuantity);
        return {
          ...e,
          BJ_HAS_CHILD: true,
          quantity: QUANTITY,
          allPrice: this.$number2money(QUANTITY * Number(e.unitPrice || 0)),
        };
      });
      this.$refs.SkuList.reFresh();
      console.log(this.skuTableData);
    },
    /** *****         BjDialogSkuList    END ********* */
    addApi(API_DATA) {
      afterApi.addRefundOnlyOrder(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessAfterInternalRefundIndex');
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-textarea__inner {
  width: 500px;
}
</style>
