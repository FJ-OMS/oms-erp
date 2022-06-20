<template>
  <div>
    <div v-if="hasBtn" class="tab-btns">
      <!-- TODO:添加商品 -->
      <!-- <el-button type="primary" @click="onAction('添加商品')">添加商品</el-button> -->
      <!-- 1.点击时验证选择的商品行状态，如果商品行审核状态非待审核则报错，请选择待审核的商品 -->
      <!-- <el-button type="primary" @click="onAction('审核通过')" class="main-btn">审核通过</el-button>
        <el-button type="primary" @click="onAction('审核拒绝')">审核拒绝</el-button>
        <el-button type="primary" @click="onAction('退回审核')">退回审核</el-button> -->
      <!-- 无法判断 待收货 挂起状态 ?  暂时 未完成 的可以确认收货 -->
      <el-button v-if="orderStatus == 0" type="primary" @click="onAction('确认收货')"
        >确认收货</el-button
      >
      <el-button type="primary" @click="onAction('挂起')">挂起</el-button>
      <el-button type="primary" @click="onAction('解挂')">解挂</el-button>
    </div>

    <el-tabs v-model="goodsInfoActiveTab" type="card">
      <el-tab-pane label="按商品汇总" name="sku">
        <SkuList
          v-if="goodsInfoActiveTabHistory.has('sku')"
          ref="SkuList"
          :order-id="orderId"
          :has-btn="hasBtn"
          :business-type="businessType"
        />
      </el-tab-pane>
      <el-tab-pane label="按商品行明细" name="skuItem">
        <SkuLineList
          v-if="goodsInfoActiveTabHistory.has('skuItem')"
          ref="SkuLineList"
          :order-id="orderId"
          :has-btn="hasBtn"
          :business-type="businessType"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { orderApi } from '@/api';
import SkuList from './SkuList';
import SkuLineList from './SkuLineList';
export default {
  name: 'BusinessOrderInternalViewGoodsInfoTab',
  components: { SkuList, SkuLineList },
  props: {
    innerOrderNo: {
      type: String,
      default: null,
    },
    orderId: {
      type: String,
      default: null,
    },
    hasBtn: {
      type: Boolean,
      default: false,
    },
    orderStatus: {
      default: false,
    },
    businessType: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      goodsInfoActiveTab: 'sku',
      goodsInfoActiveTabHistory: new Set(['sku']),
    };
  },
  watch: {
    goodsInfoActiveTab(v) {
      this.goodsInfoActiveTabHistory.add(v);
    },
    // orderId变化时，重新渲染LIST
    orderId(v) {
      const temp = this.goodsInfoActiveTabHistory;
      this.goodsInfoActiveTabHistory = new Set();
      this.$nextTick(e => {
        this.goodsInfoActiveTabHistory = temp;
      });
    },
  },
  methods: {
    onAction(action) {
      const hasChildren = this.goodsInfoActiveTab == 'sku';
      const THIS = this.goodsInfoActiveTab == 'sku' ? this.$refs.SkuList : this.$refs.SkuLineList;
      const data = THIS.selectedRows;

      console.log('hasChildren', hasChildren);
      console.log(action + 'onAction', data);

      /** ***  需要选中项 */
      let itemIds = [];
      let itemLineIds = [];
      // 新的传参方式
      let ITEM_IDS = [];
      if (!data) {
        this.$message.warning('请选择');
        return;
      }
      if (!hasChildren) {
        itemIds = [];
        itemLineIds = (data || []).map(e => e.id);
        // 只有商品行
        ITEM_IDS = [];
      } else {
        console.log(data);
        itemIds = (data.parents || []).map(e => e.id);
        itemLineIds = []; // (data.children || []).map(e => e.id);
        // 有商品SKU下挂着商品行
        ITEM_IDS = (data.parents || []).map(e => {
          return {
            id: e.id,
            idList: (data.children || []).filter(ee => ee.BJ_PARENT_ID == e.id).map(ee => ee.id),
          };
        });
      }

      if (!itemIds.length && !itemLineIds.length) {
        this.$message.warning('请选择');
        return;
      }

      switch (action) {
        case '挂起': {
          const POST_DATA = {
            innerOrderNo: this.innerOrderNo,
            actionType: '0', // 0-挂起 1-解挂
            itemIds: ITEM_IDS,
            itemLineIds: itemLineIds,
          };
          console.log(POST_DATA);
          this.$confirm('是否确认挂起？')
            .then(_ => {
              this.suspendOrderByItemLine(POST_DATA);
            })
            .catch(_ => {});
          break;
        }
        case '解挂': {
          const POST_DATA = {
            innerOrderNo: this.innerOrderNo,
            actionType: '1', // 0-挂起 1-解挂
            itemIds: ITEM_IDS,
            itemLineIds: itemLineIds,
          };

          this.$confirm('是否确认解挂？')
            .then(_ => {
              this.suspendOrderByItemLine(POST_DATA);
            })
            .catch(_ => {});
          break;
        }
        case '确认收货': {
          const POST_DATA = {
            innerOrderNo: this.innerOrderNo,
            itemIds: ITEM_IDS,
            itemLineIds: itemLineIds,
          };
          console.log(POST_DATA);
          this.$confirm('是否确认收货？')
            .then(_ => {
              this.confirmReceiptByItemLineApi(POST_DATA);
            })
            .catch(_ => {});
          break;
        }
        case '设置黑白名单':
          break;
      }
    },
    suspendOrderByItemLine(POST_DATA) {
      orderApi.suspendOrderByItemLine(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.$emit('reFresh', this.goodsInfoActiveTab);
          }, 500);
        }
      });
    },
    // 商品级-确认收货
    confirmReceiptByItemLineApi(POST_DATA) {
      orderApi.confirmReceiptByItemLine(POST_DATA).then(res => {
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
.tab-btns {
  display: inline-block;
  position: absolute;
  margin-left: 260px;
  margin-top: 4px;
  z-index: 1;
}
</style>
