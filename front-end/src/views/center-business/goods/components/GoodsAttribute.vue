<template>
  <el-dialog
    v-if="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    :width="width"
    append-to-body
    class="bj-dialog-list"
    :class="classID"
  >
    <div class="app-container goods-attribute-view-form">
      <BjDivider>基础信息</BjDivider>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="属性名称" prop="name">
          <el-input
            v-model.trim="form.name"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
        <BjFormItemCode v-model="form.code" label="属性编码" prop="code" />

        <el-form-item label="展示方式" prop="formType">
          <el-select v-model.trim="form.formType" placeholder="请选择" clearable>
            <el-option
              v-for="(item, index) in INPUT_BOX_TYPE_LIST"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <div>
            <span style="font-size: 12px; color: #999">
              <i class="el-icon-warning-outline" style="color: #999" />
              内容展示方式为单选框、复选框、下拉框时，需要添加对应的属性值。
            </span>
          </div>
        </el-form-item>
        <!-- 当展示方式为文本框时，默认“否”，不能更改 -->
        <el-form-item v-if="setFlag != GOODS_COMBO_TYPES.PACKAGE" label="生成SKU" prop="skuFlag">
          <el-switch
            v-model="form.skuFlag"
            :disabled="form.formType === INPUT_BOX_TYPE_JSON.INPUT"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model.trim="form.remark"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-form>
      <div v-show="form.formType && form.formType !== INPUT_BOX_TYPE_JSON.INPUT">
        <BjDivider>信息内容</BjDivider>
        <el-button class="mb16" type="primary" @click="addOneExecutorsConfig">新 增</el-button>
        <el-button :disabled="!isSelected" class="mb16" type="primary" @click="onDelSelected"
          >删 除</el-button
        >
        <el-button :disabled="!isSelected" class="mb16" type="primary" @click="onSaveSelected"
          >保 存</el-button
        >

        <el-table
          ref="multipleTable"
          :data="tableData"
          stripe
          border
          :header-cell-style="$rowStyle.DEFAULT"
          style="width: 500px"
          row-key="BJ_EBC_ID"
          class="goods-attribute-table-draggable-20211224"
          row-class-name="goods-attribute-tr-draggable-20211224"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" show-overflow-tooltip />

          <el-table-column prop="value" label="属性值">
            <template slot-scope="props">
              <el-input
                v-show="isEditRow(props.row)"
                v-model="props.row.value"
                :maxlength="MAX_LENGTH.DEFAULT"
              />
              <span v-show="!isEditRow(props.row)">{{ props.row.value }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="100" align="center">
            <template slot-scope="props">
              <el-button
                v-show="!isEditRow(props.row)"
                type="text"
                size="small"
                class="edit-btn"
                @click="onEdit(props.row, props.$index)"
                >编辑</el-button
              >
              <el-button
                v-show="isEditRow(props.row)"
                type="text"
                size="small"
                @click="onCancel(props.row, props.$index)"
                >取消</el-button
              >
              <el-button
                v-show="isEditRow(props.row)"
                type="text"
                size="small"
                @click="onSave(props.row, props.$index)"
                >保存</el-button
              >
              <el-button
                v-show="!isEditRow(props.row)"
                type="text"
                size="small"
                class="delete-btn"
                @click="onDel(props.row, props.$index)"
                >删除</el-button
              >
            </template>
          </el-table-column>
          <el-table-column label="拖拽排序" width="100" align="center" class-name="draggableItem">
            <template>
              <svg-icon icon-class="pointer" />
            </template>
          </el-table-column>
        </el-table>
      </div>

      <BjDivider>操作</BjDivider>
      <span slot="footer" class="footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleConfirm('form')">确 定</el-button>
      </span>
    </div>
  </el-dialog>
</template>
<script>
import Sortable from 'sortablejs';
// import { goodsApi } from '@/api';
import {
  INPUT_BOX_TYPE_LIST,
  INPUT_BOX_TYPE_JSON,
  STATUS_YES_TRUE,
  GOODS_COMBO_TYPES,
} from '@/utils/constants';
// import { patternEmail, patternCode } from '@/utils/validate';
import { randomID } from '@/utils/index';
import { getOffsetTop } from '@/utils/index';
const getDefaultForm = () => {
  return {
    id: null,
    name: null,
    code: null,

    skuFlag: false,
    // sort: null,
    statusFlag: true,
    formType: null,

    sourceType: 0, // GOODS_ATTRIBUTE_SOURCES  0 新增
    remark: null,

    attributeValueList: [
      //   {
      //     "sort": 0,
      //     "value": "string"
      //   }
    ],
  };
};

export default {
  name: 'GoodsAttribute',
  props: {
    width: {
      type: String,
      default: '600px',
    },

    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
    setFlag: {
      type: [String, Number],
      default: 0,
    },
  },
  data() {
    return {
      INPUT_BOX_TYPE_LIST,
      INPUT_BOX_TYPE_JSON,
      STATUS_YES_TRUE,
      GOODS_COMBO_TYPES,

      title: '添加属性',
      isEdit: false,

      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入', trigger: 'blur' }],
        formType: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      tableData: [],
      editRow: {
        list: [],
        store: {}, // 旧值，BJ_EBC_ID:VALUE
        check: function (d) {
          return this.list.includes(d);
        },
        add: function (d) {
          this.list.push(d);
        },
        del: function (d) {
          const INDEX = this.list.indexOf(d);
          if (INDEX != -1) {
            this.list.splice(INDEX, 1);
            return true;
          }
          return false;
        },
        init: function (d) {
          this.list = [];
        },
      },
      newRow: {
        list: [],
        check: function (d) {
          return this.list.includes(d);
        },
        add: function (d) {
          this.list.push(d);
        },
        del: function (d) {
          const INDEX = this.list.indexOf(d);
          if (INDEX != -1) {
            this.list.splice(INDEX, 1);
            return true;
          }
          return false;
        },
        init: function (d) {
          this.list = [];
        },
      },
      selectedRow: [],
      /** * */
      visible: false,
    };
  },
  computed: {
    classID() {
      return (
        'bj-dialog-list-' +
        Number(Math.random().toString().substr(3, 12) + Date.now())
          .toString(36)
          .substr(0, 8)
          .toUpperCase()
      );
    },
    isSelected() {
      return !!this.selectedRow.length;
    },
    TABLE_DATA_NO_EMPTY() {
      return this.tableData.filter(e => {
        return !!e.value;
      });
    },
  },
  watch: {
    visible() {
      if (this.visible) {
        this.$nextTick(() => {
          // this.reset();

          this.maxHeight =
            document.documentElement.clientHeight -
            getOffsetTop(`.${this.classID} .el-table__header-wrapper`) * 2 -
            100 +
            'px';
        });
      }
    },

    info: {
      handler(info) {
        if (info) {
          this.init(info);
        }
      },
    },
    // 'form.code'(v) {
    //   if (v) this.form.code = v.replace(/\s+/g, '');
    // },
    // 'form.sort'(v) {
    //   this.form.sort = Number(String(v).replace(/[^0-9]/g, ''));
    // },
    'form.formType'(v) {
      if (v === this.INPUT_BOX_TYPE_JSON.INPUT) {
        this.form.skuFlag = false;
      }
    },
  },

  mounted() {},
  methods: {
    show(d) {
      this.reset();
      // 获取默认值
      if (d) {
        this.isEdit = true;
        this.title = '编辑属性';
        this.init(d);
      } else {
        this.isEdit = false;
        this.title = '添加属性';
      }

      setTimeout(() => {
        this.rowDrop();
      }, 1000);
      this.visible = true;
    },
    /* 关闭弹窗 */
    handleClose() {
      this.visible = false;
    },
    /** 重置 */
    reset() {
      // console.log('重置');
      this.editRow.init();
      this.newRow.init();
      this.form = getDefaultForm();
      this.tableData = [];
    },
    init(info) {
      this.editRow.init();
      this.newRow.init();
      this.form = this.$lodash.clone(info);
      console.log('form', this.form);
      this.tableData = [];
      if (info && Array.isArray(info.attributeValueList)) {
        this.tableData = info.attributeValueList.map(e => {
          return {
            BJ_EBC_ID: randomID(),
            ...e,
          };
        });
      }
    },

    /** ******************************************* */
    // 行拖拽
    rowDrop() {
      // 此时找到的元素是要拖拽元素的父容器
      const tbody = document.querySelector(
        '.goods-attribute-table-draggable-20211224 .el-table__body-wrapper tbody',
      );
      const _this = this;
      Sortable.create(tbody, {
        //  指定父元素下可被拖拽的子元素
        draggable: 'tr.goods-attribute-tr-draggable-20211224',
        handle: '.draggableItem',
        onEnd({ newIndex, oldIndex }) {
          console.log(newIndex, oldIndex);
          const currRow = _this.tableData.splice(oldIndex, 1)[0];
          _this.tableData.splice(newIndex, 0, currRow);
        },
      });
    },

    reFresh() {
      this.$emit('reFresh');
    },
    /** 保 存 */
    handleConfirm(formName) {
      // 不是文本框需要添加属性值
      if (this.form.formType !== 3) {
        if (this.TABLE_DATA_NO_EMPTY.length === 0) {
          this.msgError('请添加一项属性值');
          return;
        }
        // 判断属性值是否重复
        if (this.checkValueRepeat()) return;
      }
      // 判断添加的名称和编码是否重复

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }

      if (this.form.formType === this.INPUT_BOX_TYPE_JSON.INPUT) {
        API_DATA['attributeValueList'] = [];
      } else {
        const TABLE_DATA_NO_EMPTY = this.TABLE_DATA_NO_EMPTY.map((e, i) => {
          return {
            sort: i,
            value: e.value,
          };
        });
        API_DATA['attributeValueList'] = [].concat(TABLE_DATA_NO_EMPTY);
      }
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$emit('confirm', API_DATA, this.isEdit);
        }
      });
    },
    /**
     * 检查属性值是否重复
     * return Boolean
     */
    checkValueRepeat(Arr) {
      Arr = Arr || this.TABLE_DATA_NO_EMPTY;
      // 有属性判断属性值是否重复
      const repeatArr = new Set();
      // 有重复数据存入定义好的数组中，方便弹窗显示
      Arr.forEach((item, index) => {
        const valueArr = Arr.filter((item, ind) => index !== ind).map(item => {
          return item.value;
        });
        if (valueArr.includes(item.value)) repeatArr.add(item.value);
      });
      // 如果数组repeatArr有数据说明有重复数据
      if (repeatArr.size) {
        this.$alert(`属性值 ${Array.from(repeatArr).join(',')} 已存在，请重新设置`, '标题名称', {
          confirmButtonText: '确定',
          callback: action => {},
        });
        return true;
      }
      return false;
    },
    handleSelectionChange(data) {
      this.selectedRow = [].concat(data);
    },
    onDelSelected() {
      this.selectedRow.forEach(row => {
        const TEMP = row.BJ_EBC_ID;
        this.editRow.del(TEMP);
        this.newRow.del(TEMP);
        this.tableData.splice(
          this.tableData.findIndex(e => e.BJ_EBC_ID == TEMP),
          1,
        );
      });
      this.$refs.multipleTable.clearSelection();
    },
    /** 保 存 */
    onSaveSelected() {
      // 合并选中数组和已保存的数组
      // 获取保存状态的属性值数组
      const saveValueArr = this.tableData.filter(
        item => !this.editRow.list.includes(item.BJ_EBC_ID),
      );
      // 因为选中的数组会存在空数据需要清除
      const selectedRow = this.selectedRow.filter(item => item.value);
      // 因为选中的数据和未编辑的数据可能是一条数据需要查重
      const ARR = Array.from(new Set([...selectedRow, ...saveValueArr]));
      if (this.checkValueRepeat(ARR)) return;
      this.selectedRow.forEach(row => {
        const TEMP = row.BJ_EBC_ID;
        this.editRow.del(TEMP);
        this.newRow.del(TEMP);
      });
      this.$refs.multipleTable.clearSelection();
    },
    /** *
     *
     */
    isEditRow(row) {
      return this.editRow.check(row.BJ_EBC_ID);
    },
    /** 新增一个属性值 */
    addOneExecutorsConfig() {
      const TEMP = randomID();
      this.editRow.add(TEMP);
      this.newRow.add(TEMP);
      this.tableData.push({
        BJ_EBC_ID: TEMP,
        sort: null,
        value: '',
      });
    },
    onEdit(row) {
      const TEMP = row.BJ_EBC_ID;
      this.editRow.add(TEMP);
      this.newRow.del(TEMP);
      // 保存一下旧值
      this.$set(this.editRow.store, TEMP, row.value);
    },
    onCancel(row, index) {
      const TEMP = row.BJ_EBC_ID;
      this.editRow.del(TEMP);
      if (this.newRow.del(TEMP)) {
        this.tableData.splice(index, 1);
      } else {
        // 取消->值 回退
        row.value = this.editRow.store[TEMP];
      }
    },
    onSave(row, index) {
      // 获取已保存和当前编辑的数据合并成数组并且清除空值的数据
      const saveValueArr = this.tableData.filter(
        item => !this.editRow.list.includes(item.BJ_EBC_ID) && item.value,
      );
      const ARR = [row, ...saveValueArr];
      // 查重
      if (this.checkValueRepeat(ARR)) return;

      const TEMP = row.BJ_EBC_ID;
      this.editRow.del(TEMP);
      this.newRow.del(TEMP);
    },
    onDel(row, index) {
      const TEMP = row.BJ_EBC_ID;
      this.editRow.del(TEMP);
      this.newRow.del(TEMP);
      this.tableData.splice(index, 1);
    },
  },
};
</script>
<style lang="scss">
.goods-attribute-view-form {
  .draggableItem {
    cursor: pointer;
    i {
      font-size: 18px;
    }
  }
  padding-bottom: 20px;
}
</style>
