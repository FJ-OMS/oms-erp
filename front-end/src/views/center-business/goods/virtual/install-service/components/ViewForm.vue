<template>
  <div class="app-container">
    <BjDivider
      >基础信息
      <template v-slot:button>
        <el-button v-if="isAdd" type="primary" class="xs" @click="$refs.dialogList.show()"
          >选择商品</el-button
        >
      </template>
    </BjDivider>

    <el-form style="width: 600px" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="商品编码：">
            {{ form.spuCode }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品名称：">
            {{ form.spuName }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品分类：">
            {{ form.categoryText }}
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="安装服务：">
            <BjSelectLov
              v-model="form.installServiceCode"
              lov-code="BJ_INSTALL_SERVICES"
              @change="onChange"
            />
          </el-form-item>
        </el-col>
        <!--  -->
      </el-row>
      <el-row v-show="form.installServiceCode">
        <el-col :span="24">
          <el-form-item label="安装类别" prop="type">
            {{ selectedInfo.type }}
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="配备人员" prop="peopleAmount"
            >{{ selectedInfo.peopleAmount }}人
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="服务费用" prop="money"> {{ selectedInfo.money }}元 </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="服务地区" prop="fullAddress">
            {{
              (selectedInfo.serviceZone || [])
                .map(e => e.cityDetail.levelTwoAreaName + e.cityDetail.levelThreeAreaName)
                .join('，')
            }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            {{ selectedInfo.remark }}
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
      :config="$BjDialogListConfig.shopOnline()"
      @confirm="onConfirmDialogListStore"
    />
    <BjDialogList
      ref="dialogListSku"
      multiple
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
    id: null,
    installServiceId: null,
    installServiceCode: null,
    isAllStore: false,
    objectVersionNumber: 0,
    remark: null,
    sonSkuCodes: [],
    spuId: null,
    spuCode: null,
    spuName: null,
    categoryText: null,
    spuStoreIds: [],
  };
};
export default {
  name: 'BusinessGoodsVirtualInstallServiceViewForm',
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

      /** 选择的安装服务信息 */
      selectedInfo: {},
      // 表单校验
      rules: {
        // messageCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        // enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      storeData: [],
      skuData: [],

      selectedRowStore: [],
      selectedRowSku: [],
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
          this.skuData = this.form.spuInstallServiceSkus || [];
          this.storeData = this.form.spuInstallServiceStores || [];
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
  mounted() {},
  methods: {
    onChange(v, ITEM) {
      console.log(v);
      this.form.installServiceId = ITEM.id;
      this.selectedInfo = ITEM || {};
    },

    // 获取goodsInfo
    onConfirmDialogList(data) {
      this.form.spuId = data[0].id;
      this.form.spuCode = data[0].code;
      this.form.spuName = data[0].name;
      this.form.categoryText = data[0].categoryName;
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

      if (!this.form.installServiceCode) {
        this.$message.warning('请先选择安装服务');
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

      const API_DATA = this.$lodash.cloneDeep(this.form);

      API_DATA.sonSkuCodes = this.skuData.map(e => e.skuCode);

      API_DATA.spuStoreIds = this.storeData.map(e => e.id);

      delete API_DATA.spuInstallServiceSkus;
      delete API_DATA.spuInstallServiceStores;

      if (this.form.isAllStore) {
        API_DATA.spuStoreIds = [];
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
      goodsApi.addSpuInstallServices(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      goodsApi.updateSpuInstallServices(API_DATA).then(res => {
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
