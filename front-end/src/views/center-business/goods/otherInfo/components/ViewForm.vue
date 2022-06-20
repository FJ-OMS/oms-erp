<template>
  <div class="app-container goods-attribute-view-form">
    <BjDivider right-fixed
      >基础信息

      <template slot="right">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
      </template>
    </BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="max-width: 450px">
      <BjFormItemCode v-model="form.code" :readonly="isEdit" label="信息项编码" prop="code" />
      <el-form-item label="信息项名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
      <el-form-item label="展示方式" prop="type">
        <el-select v-model.trim="form.type" placeholder="请选择" clearable>
          <el-option
            v-for="(item, index) in INPUT_BOX_TYPE_LIST"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-switch v-model="form.status" :active-value="true" :inactive-value="false" />
      </el-form-item>
    </el-form>
    <div v-show="form.type && form.type !== INPUT_BOX_TYPE_JSON.INPUT">
      <BjDivider>属性值</BjDivider>
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
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import Sortable from 'sortablejs';
import { goodsApi } from '@/api';
import { INPUT_BOX_TYPE_LIST, INPUT_BOX_TYPE_JSON, STATUS_YES_TRUE } from '@/utils/constants';
// import { patternEmail, patternCode } from '@/utils/validate';
import { randomID } from '@/utils/index';
const getDefaultForm = () => {
  return {
    id: null,
    code: null,
    name: null,
    // remark: null,
    objectVersionNumber: null,
    valueList: [
      {
        attributeId: null,
        sort: null,
        value: null,
      },
    ],

    status: true,
    type: null,
  };
};

export default {
  name: 'BusinessGoodsOtherInfoViewForm',
  mixins: [viewMiXin],
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      INPUT_BOX_TYPE_LIST,
      INPUT_BOX_TYPE_JSON,
      STATUS_YES_TRUE,

      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入', trigger: 'blur' }],
        type: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      tableData: [],
      editRow: {
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
      selectedrow: [],
    };
  },
  computed: {
    isSelected() {
      return !!this.selectedrow.length;
    },
  },
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.editRow.init();
          this.newRow.init();

          this.tableData = [];
          if (info && Array.isArray(info.voList)) {
            this.tableData = info.voList.map(e => {
              return {
                BJ_EBC_ID: randomID(),
                ...e,
              };
            });
          }

          delete info.voList;

          this.form = this.$lodash.clone(info);
        }
      },
    },
    'form.code'(v) {
      this.form.code = v.replace(/\s+/g, '');
    },
  },

  mounted() {
    setTimeout(() => {
      this.rowDrop();
    }, 1000);
  },
  methods: {
    // 行拖拽
    rowDrop() {
      // 此时找到的元素是要拖拽元素的父容器
      const tbody = document.querySelector('.el-table__body-wrapper tbody');
      const _this = this;
      Sortable.create(tbody, {
        //  指定父元素下可被拖拽的子元素
        draggable: 'tr',
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
    handleConfirm(formName) {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      // if (this.isEdit) {
      //   API_DATA['_innerMap'] = {};
      // }

      if (this.form.type === this.INPUT_BOX_TYPE_JSON.INPUT) {
        API_DATA['valueList'] = [];
      } else {
        const TABLE_DATA_NO_EMPTY = this.tableData
          .filter(e => {
            return !!e.value;
          })
          .map((e, i) => {
            return {
              sort: i,
              value: e.value,
              valueId: e.id,
            };
          });
        API_DATA['valueList'] = TABLE_DATA_NO_EMPTY;
      }
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
    handleSelectionChange(data) {
      this.selectedrow = [].concat(data);
    },
    onDelSelected() {
      this.selectedrow.forEach(row => {
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
    onSaveSelected() {
      this.selectedrow.forEach(row => {
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
    },
    onCancel(row, index) {
      const TEMP = row.BJ_EBC_ID;
      this.editRow.del(TEMP);
      if (this.newRow.del(TEMP)) {
        this.tableData.splice(index, 1);
      }
    },
    onSave(row, index) {
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

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      goodsApi.updateOtherInfo(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      goodsApi.addOtherInfo(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
            this.reFresh();
          }, 500);
        }
      });
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
}
</style>
