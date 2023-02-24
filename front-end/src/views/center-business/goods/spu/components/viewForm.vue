<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >基本信息
      <template slot="right">
        <template v-if="!isView">
          <el-button @click="tryCancel">取 消</el-button>
          <el-button type="primary" @click="handleConfirm">保 存</el-button>
        </template>
        <template v-if="isView">
          <el-button type="primary" @click="cancel">返 回</el-button>
        </template>
      </template>
    </BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="组合类型" prop="setFlag">
            <span>{{ GOODS_COMBO_TYPE_LIST.bjGet(form.setFlag) }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品类型" prop="type">
            <BjRadioLov
              v-if="!isView"
              v-model="form.type"
              :option-list="GOODS_TYPE_LIST"
              placeholder="请选择"
            />
            <span v-if="isView">{{ GOODS_TYPE_LIST.bjGet(form.type) }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品名称" prop="name">
            <el-input
              v-if="!isView"
              v-model.trim="form.name"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
            <span v-if="isView">{{ form.name }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <BjFormItemCode
            v-model="form.spuCode"
            required
            :readonly="!isAdd"
            label="商品编码"
            prop="spuCode"
          />
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="商品分类" prop="categoryId">
            <BjGoodsTypeTreeSelect
              v-if="isAdd"
              v-model="form.categoryId"
              :query="{ statusFlag: true }"
              only-leaf
              expand-all
            />
            <span v-if="!isAdd">{{ form.categoryName }}</span>
          </el-form-item>
        </el-col>
        <el-col v-if="form.setFlag == GOODS_COMBO_TYPES.ORDINARY" :span="colSpan">
          <el-form-item label="商品品牌" prop="brandId">
            <BjSelectBrand
              v-if="!isView"
              v-model="form.brandId"
              :default-options="{
                label: form.brandName,
                value: form.brandId,
              }"
            />
            <span v-if="isView">{{ form.brandName }}</span>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="colSpan">
        <el-form-item label="状态" prop="status">
          <el-switch v-if="!isView" v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-col> -->
        <el-col :span="colSpan">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-if="!isView"
              v-model.trim="form.remark"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
            <span v-if="isView">{{ form.remark }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <GoodsAttributeList
        ref="GoodsAttributeList"
        :parent-id="form.categoryId"
        :is-view="isView"
        is-spu
        :set-flag="form.setFlag"
      />
      <BjDivider>商品图片</BjDivider>
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="素材名称" prop="materialId">
            <BjSelectMaterial
              v-if="!isView"
              v-model="form.materialId"
              :default-options="{
                label: form.materialName,
                value: form.materialId,
              }"
            />
            <span v-if="isView">{{ form.materialName }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="主图">
          <div class="flex justify-between">
            <BjViewUrl
              v-for="(url, i) in materialInfo.mainImage || []"
              :key="i"
              :url="url"
              class="flex-item"
            />
          </div>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="详情图">
          <div class="flex justify-between">
            <BjViewUrl
              v-for="(url, i) in materialInfo.detailImage || []"
              :key="i"
              :url="url"
              class="flex-item"
            />
          </div>
        </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import viewMiXin from '@/utils/view-mixin.js';
import { goodsApi } from '@/api';
import { GOODS_TYPE_LIST, GOODS_COMBO_TYPE_LIST, GOODS_COMBO_TYPES } from '@/utils/constants';
import GoodsAttributeList from '@/views/center-business/goods/components/GoodsAttributeList';
const getDefaultMaterialInfo = () => {
  return {
    id: '',
    code: '',
    materialName: '',
    mainImage: '',
    detailImage: [],
    detailHtml: '',
    remark: '',
  };
};

export default {
  components: { GoodsAttributeList },
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
        return {
          id: null,
          attrLists: [],
        };
      },
    },
    setFlag: {
      type: [String, Number],
      default: GOODS_COMBO_TYPES.ORDINARY,
    },
  },
  data() {
    return {
      GOODS_TYPE_LIST,
      GOODS_COMBO_TYPE_LIST,
      GOODS_COMBO_TYPES,
      colSpan: 9,
      form: {
        attrs: [
          // {
          //   attributeName: '颜色',
          //   isSku: true,
          //   remark: 'stringremark',
          //   submissions_type:1,
          //   type: 1,
          //   values: [
          //     {
          //       sort: 1,
          //       value: '红色',
          //     },
          //     {
          //       sort: 2,
          //       value: '绿色',
          //     },
          //   ],
          // },
        ],
        brandId: null,
        categoryId: null,
        materialId: null,
        name: null,
        remark: null,
        spuCode: null,
        status: 1,
        type: 1,
      },
      // 表单校验
      rules: {
        type: [{ required: true, message: '请选择商品类型', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择商品分类', trigger: 'change' }],
        brandId: [{ required: true, message: '请选择商品品牌', trigger: 'change' }],
        name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        materialId: [{ required: true, message: '请输入素材名称', trigger: 'change' }],
      },
      materialInfo: getDefaultMaterialInfo(),
    };
  },
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
          this.form.attrs = info.attrLists;

          this.$nextTick(() => {
            this.$refs.GoodsAttributeList.setAttributeList(
              (info.attrLists || []).map(e => {
                return {
                  code: e.code,
                  formType: e.submissionsType,
                  id: e.id,
                  name: e.attributeName,
                  remark: e.remark,
                  skuFlag: !!e.isSku,
                  sourceType: e.type,
                  statusFlag: !!e.status,

                  attributeValueList: e.values,
                };
              }),
            );
          });
        }
      },
      deep: true,
    },

    'form.materialId'(id) {
      if (!id) {
        this.materialInfo = getDefaultMaterialInfo();
        return;
      }
      goodsApi.getDetailMaterial({ id: id, BjAjaxLoadingHide: true }).then(res => {
        if (res && res.id) {
          this.materialInfo = res;
        }
      });
    },
  },
  created() {
    this.form.setFlag = this.setFlag;
    if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
      this.pageName = 'BusinessGoodsSpuOrdinaryIndex';
    }
    if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
      this.pageName = 'BusinessGoodsSpuPackageIndex';
    }
  },
  methods: {
    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      const attributeList = this.$refs.GoodsAttributeList.attributeList;
      API_DATA['attrs'] = attributeList.map(e => {
        return {
          attributeName: e.name,
          code: e.code,
          isSku: e.skuFlag,
          remark: e.remark,
          sort: 0,
          status: e.statusFlag ? 1 : 0,
          type: e.sourceType,
          submissionsType: e.formType,
          values: e.attributeValueList,
        };
      });
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        // console.log(API_DATA, attributeList);
        if (this.isEdit) {
          API_DATA['_innerMap'] = {};
          delete API_DATA.attrLists;
          delete API_DATA.imageList;
          delete API_DATA.mainImage;
          delete API_DATA.materialName;
          delete API_DATA.brandName;
        } else {
          delete API_DATA.id;
        }
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    reFresh() {
      this.$emit('reFresh');
    },
    addApi(API_DATA) {
      goodsApi.add(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      goodsApi.update(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.cancel();
          }, 500);
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .inline {
  max-width: 200px;
  height: 38px;
}
</style>
