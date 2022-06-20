<!--
 * @Author: your name
 * @Date: 2021-09-18 10:07:41
 * @LastEditTime: 2022-04-02 11:13:18
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\after\third\return\view.vue
-->
<template>
  <div class="app-container label-width-6 bj-el-input-disabled">
    <div class="bj-row">
      <div class="bj-col bj-item-col-8">
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方换货单号</div>
          <div class="bj-form-item-value">
            210208298559852
            <BjCopyIcon copy="210208298559852" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方销售单号</div>
          <div class="bj-form-item-value">
            2102082985597464000
            <BjCopyIcon copy="2102082985597464000" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方换货时间</div>
          <div class="bj-form-item-value">
            2021-09-13 19:20:11
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部换货单号</div>
          <div class="bj-form-item-value">
            HH202109161314
            <BjCopyIcon copy="HH202109161314" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部订单号</div>
          <div class="bj-form-item-value">
            XS202109151819003
            <BjCopyIcon copy="XS202109151819003" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">完成时间</div>
          <div class="bj-form-item-value"></div>
        </div>
      </div>
    </div>

    <div class="bj-row mt20">
      <!-- 收货信息 -->
      <div class="bj-col ">
        <div class="bj-form-title">退货信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货状态</div>
          <div class="bj-form-item-value">待审核</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货原因</div>
          <div class="bj-form-item-value">7天无理由换货</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家备注</div>
          <div class="bj-form-item-value"></div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">物流公司</div>
          <div class="bj-form-item-value">申通快递</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">物流单号</div>
          <div class="bj-form-item-value">85696653250124</div>
        </div>
      </div>
      <!-- 支付信息 -->
      <div class="bj-col ">
        <div class="bj-form-title">买家信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家昵称</div>
          <div class="bj-form-item-value">tb1568246983</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">收货人</div>
          <div class="bj-form-item-value">钱欧明</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">手 机</div>
          <div class="bj-form-item-value">
            139****9858
            <BjCopyIcon copy="139****9858" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">地 址</div>
          <div class="bj-form-item-value">福建省 福州市 闽侯县*****</div>
        </div>
      </div>
      <!-- 发票信息 -->
      <div class="bj-col ">
        <div class="bj-form-title">
          店铺信息
          <!-- <span class="link right">查看详情</span> -->
        </div>
        <template>
          <div class="bj-form-item">
            <div class="bj-form-item-label">店铺</div>
            <div class="bj-form-item-value">淘宝_居家旗舰店</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">渠道</div>
            <div class="bj-form-item-value">淘宝</div>
          </div>
        </template>
      </div>
    </div>
    <div class="bj-row mt20">
      <div class="bj-col bj-col-24 ">
        <div class="bj-form-title">
          退货商品信息
        </div>
        <el-table :data="info.returnList" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <!-- <el-table-column type="index" label="序号" /> -->
          <el-table-column prop="spuName" label="商品名称" align="center" />
          <el-table-column prop="skuName" label="SKU" align="center" />
          <el-table-column prop="spuCode" label="商品编码" align="center" />
          <el-table-column prop="price" label="内部售价" align="center" />
          <el-table-column prop="num" label="退货数量" align="center" />
          <el-table-column prop="returnPrice" label="退货商品金额" align="center" />
        </el-table>
      </div>
    </div>
    <div class="bj-row mt20">
      <div class="bj-col bj-col-24 ">
        <div class="bj-form-title">
          换货商品信息
        </div>
        <el-table :data="info.exchangeList" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <!-- <el-table-column type="index" label="序号" /> -->
          <el-table-column prop="spuName" label="商品名称" align="center" />
          <el-table-column prop="skuName" label="SKU" align="center" />
          <el-table-column prop="spuCode" label="商品编码" align="center" />
          <el-table-column prop="price" label="内部售价" align="center" />
          <el-table-column prop="num" label="退货数量" align="center" />
          <el-table-column prop="returnPrice" label="退货商品金额" align="center" />
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BusinessAfterThirdExchangeView',
  components: {},
  props: {},
  data() {
    return {
      info: {
        returnList: [
          {
            spuName: '手持挂烫机蒸汽熨斗家用小型宿舍学生便携式熨衣服神器熨烫机',
            skuName: '豆蔻青升级款',
            spuCode: 'S256978541',
            price: '59.90',
            num: 2,
            returnPrice: '118.00',
          },
        ],
        exchangeList: [
          {
            spuName: '手持挂烫机蒸汽熨斗家用小型宿舍学生便携式熨衣服神器熨烫机',
            skuName: '高级黑升级款',
            spuCode: 'S256978541',
            price: '59.90',
            num: 2,
            returnPrice: '100.00',
          },
        ],
      },
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
<style lang="scss" scoped>
.box {
  border: 1px solid #d7dae2;
  border-radius: 4px;
  padding: 10px 20px 20px 20px;
}
</style>
