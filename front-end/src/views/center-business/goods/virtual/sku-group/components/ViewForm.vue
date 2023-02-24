<template>
  <div class="app-container">
    <BjDivider
      >基础信息
      <template v-slot:button>
        <el-button
          v-if="isAdd"
          type="primary"
          class="xs"
          @click="$refs.dialogList.show({ ...form, id: form.spuId })"
          >选择商品</el-button
        >
      </template>
    </BjDivider>

    <el-form :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="商品编码：">
            {{ form.spuCode }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品名称：">
            {{ form.spuName }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品分类：">
            {{ form.categoryName }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注：" prop="remark">
            <el-input
              v-if="!isView"
              v-model="form.remark"
              :maxlength="MAX_LENGTH.DEFAULT"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="请输入"
            />
            <span v-if="isView">{{ form.remark }}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider>售卖店铺</BjDivider>
    <el-form :model="form" :rules="rules" label-width="100px">
      <el-form-item label="适用店铺：" prop="isAllStore">
        <el-radio-group v-if="!isView" v-model="form.isAllStore" :disabled="isView">
          <el-radio :label="true">全部店铺</el-radio>
          <el-radio :label="false">指定店铺</el-radio>
        </el-radio-group>

        <span v-if="isView">{{ form.isAllStore ? '全部店铺' : '指定店铺' }}</span>
      </el-form-item>
    </el-form>
    <template v-if="!form.isAllStore">
      <div v-if="!isView" class="mb20">
        <el-button class="xs" type="primary" @click="$refs.dialogListStore.show()"
          >选择店铺</el-button
        >

        <el-button
          class="xs"
          :disabled="selectedRowStore.length == 0"
          type="primary"
          @click="onDelSelectedStore()"
          >删除店铺</el-button
        >
      </div>

      <el-table
        ref="storeTable"
        :data="storeData"
        stripe
        border
        :header-cell-style="$rowStyle.DEFAULT"
        row-key="BJ_EBC_ID"
        @selection-change="handleSelectionChangeStore"
      >
        <el-table-column v-if="!isView" type="selection" width="55" align="center" />
        <el-table-column type="index" width="55" align="center" label="序号" />
        <el-table-column prop="code" label="店铺编码" />
        <el-table-column prop="name" label="店铺名称" />
        <el-table-column prop="channelTypeMeaning" label="渠道类型" />
        <el-table-column prop="onlineStoreTypeMeaning" label="店铺类型" />
      </el-table>
    </template>

    <BjDivider
      >商品明细
      <template v-if="!isView" v-slot:button>
        <el-button type="primary" class="xs" @click="$refs.dialogListSku.show()"
          >选择商品</el-button
        >

        <el-button
          :disabled="selectedRowSku.length == 0"
          type="primary"
          class="xs"
          @click="onDelSelectedSku()"
          >删除商品</el-button
        >
      </template></BjDivider
    >
    <el-table
      ref="skuTable"
      :data="skuData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChangeSku"
    >
      <el-table-column v-if="!isView" type="selection" width="55" align="center" />

      <el-table-column type="index" width="55" align="center" label="序号" />

      <el-table-column prop="categoryName" label="商品分类" />
      <el-table-column prop="spuCode" label="商品编码" />

      <el-table-column prop="spuName" label="商品名称" />
      <el-table-column prop="skuCode" label="SKU编码" />

      <el-table-column
        v-for="(item, index) in priceSettingSolumns"
        :key="index"
        :prop="item.prop"
        :label="item.label"
      >
        <template slot-scope="props">
          {{ isPriceProp(props.row, item.prop) }}
        </template>
      </el-table-column>
      <el-table-column prop="num" label="搭配数量">
        <template slot-scope="props">
          <el-input v-if="!isView" v-model="props.row.num" :maxlength="MAX_LENGTH.DEFAULT" />
          <span v-if="isView">{{ props.row.num }}</span>
        </template>
      </el-table-column>
    </el-table>

    <BjDivider>操作</BjDivider>
    <span v-if="!isView" slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
    </span>
    <span v-if="isView" slot="footer" class="footer">
      <el-button type="primary" @click="handleClose">返 回</el-button>
    </span>

    <!-- 商品 -->
    <BjDialogList
      ref="dialogList"
      :config="$BjDialogListConfig.spuVirtual()"
      @confirm="onConfirmDialogList"
    />
    <BjDialogList
      ref="dialogListStore"
      multiple
      :disabled="storeData.map(e => e.id)"
      :config="$BjDialogListConfig.shopOnline()"
      @confirm="onConfirmDialogListStore"
    />
    <BjDialogList
      ref="dialogListSku"
      multiple
      :disabled="skuData.map(e => e.id)"
      :config="$BjDialogListConfig.skuDefault()"
      @confirm="onConfirmDialogListSku"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { goodsApi } from '@/api';

import { SHOP_TYPE_LIST } from '@/utils/constants';

const getDefaultForm = () => {
  return {
    goodsGroupSkuReqDTOS: [
      // {
      //   num: 0,
      //   skuId: 0,
      // },
    ],
    spuName: null,
    id: null,
    isAllStore: false,
    remark: null,
    spuId: null,
    storeIdList: [],
  };
};
export default {
  name: 'BusinessGoodsVirtualSkuGroupViewForm',
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
        return {};
      },
    },
    parentName: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      SHOP_TYPE_LIST,

      form: getDefaultForm(),

      /** 选择的商品信息 */
      goodsInfo: {},

      // 表单校验
      rules: {
        // messageCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        // enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      storeData: [],
      skuData: [],

      selectedRowStore: [],
      selectedRowSku: [],

      priceSettingSolumns: [],
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
          this.skuData = this.form.skuGroupSkuResVOList.map(e => {
            return {
              ...e,
              priceReqDTO: JSON.parse(e.price),
            };
          });
          this.storeData = this.form.groupStoreResVOList || [];
          // if (info && Array.isArray(info.attributeValueResVOList)) {
          //   this.tableData = info.attributeValueResVOList.map(e => {
          //     return {
          //       BJ_EBC_ID: randomID(),
          //       ...e,
          //     };
          //   });
          // }
        }
      },
    },
  },
  mounted() {
    this.getPriceSetting();
  },
  methods: {
    getPriceSetting() {
      /** *  表头 *****/
      goodsApi.getListPriceItem().then(res => {
        this.priceSettingSolumns = (res || []).map(e => {
          return { label: e.meaning, prop: e.value, isCheck: true, disabled: false };
        });
      });
    },
    // 返回价格
    isPriceProp(row, prop) {
      if (!Object.prototype.hasOwnProperty.call(row, prop)) {
        const item = row.priceReqDTO.find(e => e.priceCode == prop);
        if (item) {
          return this.$number2money(item.priceValue);
        }
      }
      return false;
    },

    // 获取goodsInfo
    onConfirmDialogList(data) {
      this.form.spuId = data[0].id;
      this.form.spuCode = data[0].spuCode;
      this.form.spuName = data[0].name;
      this.form.categoryName = data[0].categoryName;
      // this.form.remark = ;
    },
    onConfirmDialogListStore(data) {
      data.forEach(row => {
        const FLAG = this.storeData.find(e => e.id == row.id);
        if (!FLAG) {
          this.storeData.push(row);
        }
      });
    },
    onConfirmDialogListSku(data) {
      data.forEach(row => {
        const FLAG = this.skuData.find(e => e.id == row.id);
        if (!FLAG) {
          this.skuData.push(row);
        }
      });
    },
    handleSelectionChangeStore(d) {
      console.log(d);
      this.selectedRowStore = [].concat(d);
    },
    handleSelectionChangeSku(d) {
      console.log(d);
      this.selectedRowSku = [].concat(d);
    },
    onDelSelectedStore() {
      this.selectedRowStore.forEach(row => {
        this.storeData.splice(
          this.storeData.findIndex(e => e.id == row.id),
          1,
        );
      });
      // 清除选中状态
      this.$refs.storeTable.clearSelection();
    },
    onDelSelectedSku() {
      this.selectedRowSku.forEach(row => {
        this.skuData.splice(
          this.skuData.findIndex(e => e.id == row.id),
          1,
        );
      });
      // 清除选中状态
      this.$refs.skuTable.clearSelection();
    },

    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      if (!this.form.spuId) {
        this.$message.warning('请先选择商品');
        return;
      }

      if (!this.form.isAllStore && this.storeData.length == 0) {
        this.$message.warning('请先选择指定店铺');
        return;
      }

      if (this.skuData.length == 0) {
        this.$message.warning('请先选择商品明细');
        return;
      }

      if (this.skuData.some(e => !e.num || e.num == 0)) {
        this.$message.warning('请将 搭配数量(不能为0) 填写完整');
        return;
      }
      const API_DATA = this.$lodash.cloneDeep(this.form);

      API_DATA.goodsGroupSkuReqDTOS = this.skuData.map(e => {
        return {
          num: e.num,
          skuId: e.id,
        };
      });

      API_DATA.storeIdList = this.storeData.map(e => e.id);

      if (this.form.isAllStore) {
        API_DATA.storeIdList = [];
      }

      console.log('确认', API_DATA);

      if (this.isAdd) {
        this.addApi(API_DATA);
      }
      if (this.isEdit) {
        this.updateApi(API_DATA);
      }
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
    addApi(API_DATA) {
      goodsApi.addSkuGroup(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      goodsApi.updateSkuGroup(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
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
</style>
