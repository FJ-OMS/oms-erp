<template>
  <div>
    <BjDivider
      >属性表
      <template v-if="!isView" v-slot:button>
        <el-button
          type="danger"
          :disabled="selectedRows.length == 0"
          class="xs delete-btn"
          @click="onDelSelectedStore()"
          >删除</el-button
        >
        <el-button type="primary" class="xs" :disabled="disabled" @click="getParentData">{{
          isSpu ? '导入分类属性' : '复制上级属性'
        }}</el-button>
        <el-button type="primary" class="xs" @click="$refs.GoodsAttribute.show()"
          >添加属性</el-button
        >
      </template></BjDivider
    >

    <el-table
      ref="storeTable"
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChangeStore"
    >
      <el-table-column
        v-if="!isView"
        type="selection"
        width="55"
        align="center"
        :selectable="checkSelectable"
      />
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="name" label="属性名称" align="center" />
      <el-table-column prop="code" label="属性编码" align="center" />
      <el-table-column prop="formType" label="表单方式" align="center">
        <template slot-scope="scope">
          {{ INPUT_BOX_TYPE_LIST.bjGet(scope.row.formType) }}
        </template>
      </el-table-column>
      <el-table-column prop="attributeValueList" label="属性值" align="center">
        <template slot-scope="scope">
          {{ getValueString(scope.row.attributeValueList) }}
        </template>
      </el-table-column>
      <el-table-column prop="sourceType" :label="isSpu ? '属性类型' : '属性来源'" align="center">
        <template slot-scope="scope">
          {{
            (isSpu ? GOODS_ATTRIBUTE_TYPES : GOODS_ATTRIBUTE_SOURCES).bjGet(scope.row.sourceType)
          }}
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center" />
      <el-table-column prop="skuFlag" label="是否生成SKU" align="center">
        <template slot-scope="scope">
          {{ scope.row.skuFlag ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column v-if="!isView" prop="action" label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="scope.$index > 3"
            type="text"
            class="delete-btn"
            @click="onDelete(scope.row)"
            >删除</el-button
          >
          <el-button v-if="scope.$index > 3" type="text" class="edit-btn" @click="onEdit(scope.row)"
            >编辑</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <GoodsAttribute ref="GoodsAttribute" :set-flag="setFlag" @confirm="handleAttributeConfirm" />
  </div>
</template>
<script>
import { goodsApi } from '@/api';
import { randomID } from '@/utils/index';
import {
  INPUT_BOX_TYPE_LIST,
  GOODS_ATTRIBUTE_SOURCES,
  GOODS_ATTRIBUTE_TYPES,
} from '@/utils/constants';
import GoodsAttribute from '@/views/center-business/goods/components/GoodsAttribute.vue';
import DefaultAttributeList from '@/views/center-business/goods/components/DefaultAttributeList.json';
export default {
  name: 'GoodsAttributeList',
  components: {
    GoodsAttribute,
  },
  props: {
    parentId: {
      type: [String, Number],
      default: null,
    },
    isView: {
      type: Boolean,
      default: false,
    },
    isSpu: {
      type: Boolean,
      default: false,
    },
    setFlag: {
      type: [String, Number],
      default: 0,
    },
  },
  data() {
    return {
      INPUT_BOX_TYPE_LIST,
      GOODS_ATTRIBUTE_SOURCES,
      GOODS_ATTRIBUTE_TYPES,
      attributeList: [],
      selectedRows: [],
    };
  },
  computed: {
    tableData() {
      return DefaultAttributeList.concat(this.attributeList);
    },
    disabled() {
      // 父级id 等于root 或者没有不能点击复制上级
      return !this.parentId || this.parentId === 'root';
    },
  },
  watch: {
    attributeList() {
      return this.attributeList.forEach(item => {
        item.BJ_EBC_ID = randomID();
      });
    },
  },
  methods: {
    /** 获取上级属性 */
    getParentData() {
      let categoryId = this.parentId;
      if (categoryId === 'root') categoryId = null;
      goodsApi
        .getPrentAttribute({ categoryId })
        .then(res => {
          if (Array.isArray(res)) {
            const nameArr = this.attributeList.map(item => item.name);
            let index = 0;
            res.forEach(item => {
              item.sourceType = 2;
              item.BJ_EBC_ID = randomID();
              if (nameArr.indexOf(item.name) === -1) {
                index++;
                this.attributeList.push({
                  ...item,
                });
              }
            });
            let text = '';
            if (res.length - index > 0) {
              text = '，重复 ' + (res.length - index) + ' 条';
            }
            text = '操作成功,获取 ' + res.length + ' 条属性，已添加 ' + index + ' 条' + text;
            this.$alert(text, '提示', {
              confirmButtonText: '确定',
              type: 'success',
              callback: action => {},
            });
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /** 添加商品属性回调 */
    handleAttributeConfirm(row, isEdit) {
      const sameName = this.tableData.findIndex(e => e.name == row.name);
      // 因为编码可以为空所以需要把空值的编码筛选掉
      const sameCode = this.tableData.findIndex(e => e.code && e.code == row.code);
      const sameBID = this.tableData.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID);

      // console.log(isEdit, sameName, sameCode, sameBID);

      let isSame = false;
      if (isEdit) {
        // 修改时，同名的只能是本身
        if ((sameName > -1 && sameBID !== sameName) || (sameCode > -1 && sameBID !== sameCode)) {
          isSame = true;
        } else {
          this.attributeList.splice(sameBID - DefaultAttributeList.length, 1, {
            ...row,
            BJ_EBC_ID: randomID(),
          });
        }
      } else {
        if (sameName > -1 || sameCode > -1) {
          isSame = true;
        } else {
          this.attributeList.push({
            ...row,
            BJ_EBC_ID: randomID(),
          });
        }
      }
      const TEXT = [];
      if (sameName > -1) TEXT.push('属性名称');
      if (sameCode > -1) TEXT.push('属性编码');
      if (isSame) {
        this.$alert(`${TEXT.join(',')} 重复，请重新输入`, '提示');
      } else {
        this.$refs.GoodsAttribute.handleClose();
      }
    },
    /** 前4个 设置禁止选中的条件 */
    checkSelectable(row, index) {
      return index > 3;
    },
    /**  */
    handleSelectionChangeStore(d) {
      console.log(d);
      this.selectedRows = [].concat(d);
    },
    getValueString(data) {
      if (!Array.isArray(data)) {
        return '';
      }
      // return JSON.stringify(data);
      const ARR = [...data];
      ARR.sort((a, b) => {
        return a.sort - b.sort;
      });
      return ARR.map(e => e.value).join();
    },
    onDelSelectedStore() {
      this.$confirm(`是否批量删除这些属性？`)
        .then(_ => {
          this.selectedRows.forEach(row => {
            this.deleteAction(row);
          });
        })
        .catch(_ => {});
    },
    onDelete(row) {
      this.$confirm(`是否删除此属性(${row.name})？`)
        .then(_ => {
          this.deleteAction(row);
        })
        .catch(_ => {});
    },
    deleteAction(row) {
      this.attributeList.splice(
        this.attributeList.findIndex(e => e.BJ_EBC_ID == row.BJ_EBC_ID),
        1,
      );
    },
    onEdit(row) {
      this.$refs.GoodsAttribute.show(row);
    },
    setAttributeList(data) {
      this.attributeList = this.$lodash.clone(data);
    },
  },
};
</script>
