<template>
  <div class="app-container bj-label-colon label-width-6 bj-el-input-disabled">
    <BjDivider right-fixed
      >换货单详情
      <template slot="right">
        <el-button @click="handleClose">返 回</el-button>
        <el-button type="primary" @click="showLog">操作日志</el-button>
      </template>
    </BjDivider>

    <div class="bj-row mt20">
      <!-- 正常流转状态 -->

      <div class="bj-col bj-col-16" style="padding-left: 0">
        <div class="bj-form-item">
          <BjOrderStatus
            status="11"
            constant-key="AFTER_EXCHANGE_TRADE_STATUS"
            :times="['2021-09-13 08:35:11']"
            style="width: 100%"
          />
        </div>
      </div>
      <div class="bj-col bj-col-8 flex-center">
        <div class="bj-form-item flex-center">
          <!-- <el-button type="primary"  @click="command('审核通过')" class="main-btn">审核通过</el-button>
          <el-button  @click="command('审核拒绝')">审核拒绝</el-button> -->
          <el-button type="primary" class="main-btn" @click="command('取消')">取消</el-button>
        </div>
      </div>
    </div>

    <div class="bj-row mt20 child-border-no">
      <!-- 关联单据 -->
      <div class="bj-col">
        <div class="bj-form-title">详情</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方换货单号</div>
          <div class="bj-form-item-value">
            <!-- @click="$toBusinessAfterThirdRefundView('2102082985597464100')" -->
            <span>2102082985597464100</span>
            <BjCopyIcon :copy="2102082985597464100" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方订单号</div>
          <div class="bj-form-item-value">
            <!-- @click="$toBusinessOrderThirdView('2102082985597464100')" -->
            <span>2102082985597464100</span>
            <BjCopyIcon :copy="2102082985597464100" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部换货单号</div>
          <div class="bj-form-item-value">
            <!-- @click="$toBusinessAfterInternalRefundView('HH202109161314')" -->
            <span>HH202109161314</span>
            <BjCopyIcon copy="HH202109161314" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部订单号</div>
          <div class="bj-form-item-value">
            <!-- @click="$toBusinessOrderInternalView()" -->
            <span>XS202109151819003</span>
            <BjCopyIcon copy="XS202109151819003" />
          </div>
        </div>

        <div class="bj-form-item">
          <div class="bj-form-item-label" style="width: 13em">是否需要买家先退回换货商品</div>
          <div class="bj-form-item-value">是</div>
        </div>
      </div>
      <div class="bj-col">
        <div class="bj-form-title">&nbsp;</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方换货时间</div>
          <div class="bj-form-item-value">2021-09-13 19:20:11</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部创建时间</div>
          <div class="bj-form-item-value">
            2021-09-13 18:18:11<span class="F68710">三方下载</span>
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">完成时间</div>
          <div class="bj-form-item-value"></div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">换货备注</div>
          <div class="bj-form-item-value"><span class="link" @click="onInvoice">修改</span></div>
        </div>
      </div>

      <div class="bj-col">
        <div class="bj-form-title">
          图片附件 <span class="link right" @click="showUpload()">上传</span>
        </div>
        <div class="bj-form-item" style="display: block">
          <div v-for="(accessory, i) in upload.imgs" :key="i" class="bj-form-item-tips">
            <span class="img-url link">{{ accessory }}</span
            ><i class="link right el-icon-close"></i>
          </div>
        </div>
      </div>
    </div>

    <div class="bj-row mt20">
      <div class="bj-col bj-col-24">
        <div class="bj-form-title">换出商品信息</div>
        <el-table :data="info.list1" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <el-table-column type="index" label="序号" align="center" />
          <el-table-column prop="spuName" label="商品名称" align="center" />
          <el-table-column prop="skuName" label="SKU" align="center" />
          <el-table-column prop="skuCode" label="商品编码" align="center" />
          <el-table-column prop="price1" label="三方平台单价" align="center" />
          <el-table-column prop="price2" label="内部售价" align="center" />
          <el-table-column prop="price3" label="平台均摊总价" align="center" />
          <el-table-column prop="price4" label="内部均摊总价" align="center" />
          <el-table-column prop="num" label="退货数量" align="center" />
          <!-- <el-table-column prop="bjTableAction" label="操作" width="120">
            <template slot-scope="props">
              <el-button type="text"  @click="onInvoice(props.row)">查看物流</el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </div>
    </div>

    <div class="bj-row mt20">
      <div class="bj-col bj-col-24">
        <div class="bj-form-title">换出商品物流信息</div>
        <el-table :data="info.list2" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <el-table-column type="index" label="序号" align="center" />
          <el-table-column prop="logiCompany" label="物流公司" align="center" />
          <el-table-column prop="logiNo" label="物流单号" align="center" />
          <el-table-column prop="deliverWarehouse" label="发货仓库" align="center" />
          <el-table-column prop="receivingStatus" label="收货状态" align="center" />
          <el-table-column prop="deliveryNoticeNo" label="出库通知单单号" align="center" />
          <el-table-column prop="deliveryResultNo" label="出库结果单单号" align="center" />
          <el-table-column prop="deliveryStatus" label="出库状态" align="center" />
          <el-table-column prop="deliveryNum" label="已出库数量" align="center" />
          <el-table-column prop="deliveryTime" label="出库时间" align="center" />
          <el-table-column prop="bjTableAction" label="操作" width="120">
            <template slot-scope="props">
              <el-button type="text" @click="onInvoice(props.row)">查看物流</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <div class="bj-row mt20">
      <div class="bj-col bj-col-24">
        <div class="bj-form-title">换入商品信息</div>
        <el-table :data="info.list3" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <el-table-column type="index" label="序号" align="center" />
          <el-table-column prop="spuName" label="商品名称" align="center" />
          <el-table-column prop="skuName" label="SKU" align="center" />
          <el-table-column prop="skuCode" label="商品编码" align="center" />
          <el-table-column prop="price1" label="三方平台单价" align="center" />
          <el-table-column prop="price2" label="内部售价" align="center" />
          <el-table-column prop="price3" label="平台均摊总价" align="center" />
          <el-table-column prop="price4" label="内部均摊总价" align="center" />
          <el-table-column prop="num" label="退货数量" align="center" />
          <!-- <el-table-column prop="bjTableAction" label="操作" width="120">
            <template slot-scope="props">
              <el-button type="text"  @click="onInvoice(props.row)">查看物流</el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </div>
    </div>

    <div class="bj-row mt20">
      <div class="bj-col bj-col-24">
        <div class="bj-form-title">换入商品物流信息</div>
        <el-table :data="info.list4" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <el-table-column type="index" label="序号" align="center" />
          <el-table-column prop="logiCompany" label="物流公司" align="center" />
          <el-table-column prop="logiNo" label="物流单号" align="center" />
          <el-table-column prop="deliverWarehouse" label="收货仓库" align="center" />
          <el-table-column prop="receivingStatus" label="收货状态" align="center" />
          <el-table-column prop="deliveryNoticeNo" label="收货通知单单号" align="center" />
          <el-table-column prop="deliveryResultNo" label="收货结果单单号" align="center" />
          <el-table-column prop="bjTableAction" label="操作" width="120">
            <template slot-scope="props">
              <el-button type="text" @click="onInvoice(props.row)">查看物流</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 上传附件 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="上传附件"
      :visible.sync="upload.visible"
      width="85%"
    >
      <BjUpload
        ref="upload"
        class="avatar-uploader"
        :limit="20"
        multiple
        auto-upload
        show-file-list
        list-type="picture-card"
        accept="image/png,image/jpeg"
        @afterUpload="afterUpload"
        @handleFileListChange="handleFileListChange"
      >
        <template slot="default">
          <i class="el-icon-plus avatar-uploader-icon" />
        </template>
        <template slot="tip"> 可拖拽图片调整顺序, 支持PNG/JPG格式 </template>
      </BjUpload>
    </el-dialog>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
// import { goodsApi } from '@/api';
import orderlog from '@/views/center-business/order/components/LogTable/index.js';
import BjOrderStatus from '@/views/center-business/order/components/BjOrderStatus/index';

import BjUpload from '@/components/BjUpload/index';

export default {
  name: 'BusinessAfterThirdExchangeView',
  components: { BjOrderStatus, BjUpload },
  mixins: [viewMiXin],
  props: {},
  data() {
    return {
      fileList: [
        {
          name: '聊天记录.jpg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
        },
        {
          name: '聊天记录20210919.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
        },
      ],
      info: {
        list1: [
          {
            spuName: '手持挂烫机蒸汽熨斗家用小型宿舍学生便携式熨衣服神器熨烫机',
            skuName: '豆蔻青升级款',
            skuCode: 'S256978541',
            price1: '59.90',
            price2: '59.90',
            price3: '98.00',
            price4: '98.00',
            num: '2',
          },
        ],
        list2: [
          {
            logiCompany: '中通快递',
            logiNo: '85696653250124',
            deliverWarehouse: '厦门集美仓',
            receivingStatus: '已收货',
            deliveryNoticeNo: 'SHTZ99963254553',
            deliveryResultNo: 'SHJG99963254553',
            deliveryStatus: '全部出库',
            deliveryNum: '2',
            deliveryTime: '2021-09-12 18:20:11',
          },
        ],
        list3: [
          {
            spuName: '手持挂烫机蒸汽熨斗家用小型宿舍学生便携式熨衣服神器熨烫机',
            skuName: '豆蔻青升级款',
            skuCode: 'S256978541',
            price1: '59.90',
            price2: '59.90',
            price3: '98.00',
            price4: '98.00',
            num: '2',
          },
        ],
        list4: [
          {
            logiCompany: '中通快递',
            logiNo: '85696653250124',
            deliverWarehouse: '厦门集美仓',
            receivingStatus: '已收货',
            deliveryNoticeNo: 'SHTZ99963254553',
            deliveryResultNo: 'SHJG99963254553',
            deliveryStatus: '全部出库',
            deliveryNum: '2',
            deliveryTime: '2021-09-12 18:20:11',
          },
        ],
      },

      /** * */
      upload: {
        visible: false,
        imgs: [
          'minio.baogicloud.com/test/0/1631873061345_1960KWS9_蛇.png',
          'minio.baogicloud.com/test/0/1631873066634_NL4QHNKS_xin-shang-yun.png',
          'minio.baogicloud.com/test/0/1631873071529_CC9TZDL8_微信截图_20210708092537.jpg',
        ],
      },
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onInvoice() {
      // TODO:
      this.$message.warning('暂无');
    },
    /** *start */
    getImgListForUploadShow(list) {
      return (list || []).map(e => {
        return {
          isOld: true,
          name: e, // 原始url
          url: e,
        };
      });
    },
    showUpload() {
      this.upload.visible = true;
      this.$nextTick(() => {
        // 获取地址
        const LIST = this.getImgListForUploadShow(this.upload.imgs);
        this.$refs.upload.setFileList(LIST);
      });
    },
    afterUpload(res) {
      console.log('详情图上传返回', res);
      this.upload.imgs = (this.upload.imgs || []).concat(res || []);
    },
    // 获取 旧数据的 变更
    handleFileListChange(fileList) {
      const TEMP_FILE_LIST = fileList.filter(file => !file.raw);
      this.upload.imgs = TEMP_FILE_LIST.map(e => e.url);
    },
    /** */
    showLog() {
      orderlog(this.$route.query.id);
    },
    reFresh() {
      this.$emit('reFresh');
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`,
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
  },
};
</script>
<style lang="scss" scoped>
.box {
  border: 1px solid #d7dae2;
  border-radius: 4px;
  padding: 10px 20px 20px 20px;
}
</style>
