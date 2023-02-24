<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >缺货商品信息

      <template slot="right">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
      </template>
    </BjDivider>

    <!-- 选商品 -->

    <div class="mb20">
      <el-button
        :disabled="dialogData.goods.length > 0"
        type="primary"
        class="xs main-btn"
        @click="$refs.dialogGoodsList.show()"
        >新增商品</el-button
      >
      <!--
      <el-button
        :disabled="selectedRow.goods.length == 0"
        type="primary"
        class="xs"
        @click="onDelSelected('goods')"
        >删除商品</el-button
      > -->
    </div>

    <el-table
      ref="goodsTable"
      :data="dialogData.goods"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChange($event, 'goods')"
    >
      <!-- <el-table-column  type="selection" width="55" align="center" /> -->
      <!-- <el-table-column type="index" width="55" align="center" label="序号" /> -->
      <el-table-column prop="spuCode" label="商品编码" />
      <el-table-column prop="spuName" label="商品名称" />
      <el-table-column prop="skuCode" label="SKU编码" />
      <el-table-column prop="categoryName" label="商品分类" />
      <el-table-column prop="action" label="操作">
        <template slot-scope="scope">
          <el-button type="text" class="delete-btn" @click="onDelSelected('goods', scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <BjDivider>关联缺货店铺 </BjDivider>

    <div class="mb20">
      <el-button type="primary" class="xs" @click="$refs.dialogStoresList.show()"
        >选择店铺</el-button
      >

      <el-button
        :disabled="selectedRow.stores.length == 0"
        type="primary"
        class="xs"
        @click="onDelSelected('stores')"
        >批量删除</el-button
      >
    </div>

    <el-table
      ref="storesTable"
      :data="dialogData.stores"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChange($event, 'stores')"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="code" label="店铺编码" />
      <el-table-column prop="name" label="店铺名称" />
      <el-table-column prop="channelTypeMeaning" label="渠道" />
      <el-table-column prop="priorityReplenish" label="是否优先补货">
        <template slot-scope="scope">
          <BjSelectLov
            v-model="scope.row.priorityReplenish"
            :clearable="false"
            constant-key="STATUS_YES_TRUE"
          />
        </template>
      </el-table-column>
      <el-table-column prop="priorityGrade" label="优先级">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.priorityGrade"
            :maxlength="MAX_LENGTH.DEFAULT"
            :precision="0"
            :min="0"
            :controls="false"
            class="full-width"
          />
        </template>
      </el-table-column>
      <el-table-column prop="priorityReplenishQty" label="优先补货数量">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.priorityReplenishQty"
            :maxlength="MAX_LENGTH.DEFAULT"
            :precision="0"
            :min="0"
            :controls="false"
            class="full-width"
          />
        </template>
      </el-table-column>
      <!-- <el-table-column prop="categoryName" label="仓位数量" /> -->
    </el-table>

    <!-- 网店 -->
    <BjDialogList
      ref="dialogStoresList"
      multiple
      :config="$BjDialogListConfig.shopOnline()"
      @confirm="dialogListConfirm($event, 'stores')"
    />
    <!-- 赠品 -->
    <BjDialogList
      ref="dialogGoodsList"
      :config="$BjDialogListConfig.skuDefault()"
      @confirm="dialogListConfirm($event, 'goods')"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';

export default {
  name: 'BusinessStockOutAdd',
  mixins: [viewMiXin],
  props: {},
  data() {
    return {
      dialogData: {
        stores: [],
        goods: [],
      },
      selectedRow: {
        stores: [],
        goods: [],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    /** 选择 商品、赠品、店铺 Start */
    dialogListConfirm(e, type) {
      const NEW_DATA = [].concat(e);
      const OLD_DATA = [].concat(this.dialogData[type]);
      NEW_DATA.forEach(row => {
        const FLAG = OLD_DATA.find(e => e.id == row.id);
        if (!FLAG) {
          this.dialogData[type].push(row);
        }
      });
    },
    handleSelectionChange(e, type) {
      console.log(...arguments);
      this.selectedRow[type] = [].concat(e);
    },
    onDelSelected(type = 'store', row) {
      if (row) {
        this.dialogData[type].splice(
          this.dialogData[type].findIndex(e => e.id == row.id),
          1,
        );
      } else {
        this.selectedRow[type].forEach(row => {
          this.dialogData[type].splice(
            this.dialogData[type].findIndex(e => e.id == row.id),
            1,
          );
        });
        // 清除选中状态
        const TABLE = type + 'Table';
        this.$refs[TABLE].clearSelection();
      }
    },
    /** 选择 商品、赠品、店铺 End */

    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      if (this.dialogData.goods.length != 1) {
        this.$message.warning('请先选择 商品信息');
        return;
      }

      if (this.dialogData.stores.length == 0) {
        this.$message.warning('请先选择 关联缺货店铺');
        return;
      }

      // if (
      //   this.dialogData.stores.some(
      //     e => e.priorityReplenish !== false && e.priorityReplenish !== true,
      //   )
      // ) {
      //   this.$message.warning('请将 选择 是否优先补货');
      //   return;
      // }

      // if (
      //   this.dialogData.stores.some(e => e.priorityGrade === undefined || e.priorityGrade === null)
      // ) {
      //   this.$message.warning('请将 优先级 填写完整');
      //   return;
      // }

      // if (
      //   this.dialogData.stores.some(
      //     e => e.priorityReplenishQty === undefined || e.priorityReplenishQty === null,
      //   )
      // ) {
      //   this.$message.warning('请将 优先补货数量 填写完整');
      //   return;
      // }

      const API_DATA = {
        shopList: this.dialogData.stores.map(e => {
          return {
            channelTypeCode: e.channelTypeCode,
            priorityGrade: e.priorityGrade || 0,
            priorityReplenish: !!e.priorityReplenish,
            priorityReplenishQty: e.priorityReplenishQty || 0,
            shopCode: e.code,
            shopName: e.name,
          };
        }),
        skuCode: this.dialogData.goods[0].skuCode,
        spuCode: this.dialogData.goods[0].spuCode,
        spuName: this.dialogData.goods[0].spuName,
      };

      this.addApi(API_DATA);
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel({
        name: 'BusinessStockOut',
        query: {
          tab: 'WithPackageRule',
        },
      });
    },
    addApi(API_DATA) {
      stockApi.stockOutAdd(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          if (Array.isArray(res)) {
            if (res.length == 0) {
              this.$message.success('保存成功');
              setTimeout(() => {
                this.handleClose();
              }, 500);
            } else if (res.length == this.dialogData.stores.length) {
              this.$message.error('保存失败，您选择的店铺与商品已经关联过了');
            } else {
              const ErrStoreString = this.dialogData.stores
                .filter(e => res.includes(e.code))
                .map(e => e.name + '  （' + e.code + '）')
                .join('<br />');

              this.$confirm(
                `以下店铺重复关联，已跳过本次设置！<br />${ErrStoreString}`,
                '操作成功',
                {
                  dangerouslyUseHTMLString: true,
                  confirmButtonText: '我知道了',
                  cancelButtonText: '返回列表',
                  type: 'warning',
                },
              )
                .then(() => {})
                .catch(() => {
                  setTimeout(() => {
                    this.handleClose();
                  }, 500);
                });
            }
          } else {
            this.$message.error('数据错误');
            console.error(res);
          }
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.xs.el-button {
  font-size: 12px;
  padding: 3px 10px;
  margin-left: 16px;
}

.tips {
  color: red;
}
</style>
