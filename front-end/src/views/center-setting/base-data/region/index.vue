<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      is-list
      lazy
      :load="load"
      row-key="areaCode"
      :reset-data="resetData"
      serial-width="110"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:SettingBaseDataRegionIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd()"
            >新增省级区域</el-button
          >
        </el-col>
      </template>
      <template v-slot:tableColumn>
        <template v-for="(col, index) in dropCols">
          <el-table-column
            v-if="col.prop !== 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || false"
            align="center"
            class-name="item"
          >
            <template slot-scope="scope">
              <template v-if="false" />

              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="scope">
              <el-button
                v-permission:SettingBaseDataRegionIndex_AddChild
                :disabled="!scope.row.BJ_HAS_CHILD"
                type="text"
                @click="onAdd(scope.row)"
                >新建下级区域</el-button
              >
              <el-button
                v-permission:SettingBaseDataRegionIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:SettingBaseDataRegionIndex_Delete
                type="text"
                class="delete-btn"
                @click="onDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { commonApi } from '@/api';
import DataForm from './components/DataForm.vue';
export default {
  name: 'SettingBaseDataRegionIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      queryApi: commonApi.getProvince,
      // 查询参数
      queryParams: {},

      labelList: [
        { label: '区域编码', prop: 'areaCode', isCheck: true, disabled: false },

        { label: '区域名称', prop: 'name', isCheck: true, disabled: false },
        { label: '简称', prop: 'shortName', isCheck: true, disabled: false },
        // { label: '状态', prop: 'status', isCheck: true, disabled: false },

        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    // this.$refs.BjPage.tableData = [{}];
  },
  methods: {
    resetData(d) {
      return d.map(e => {
        return {
          ...e,
          children: [],
          BJ_HAS_CHILD: true,
          BJ_PARENT_ID: null,
          level: 0,
          parentCode: 0,
        };
      });
    },
    load({ row }) {
      return new Promise((resolve, reject) => {
        const LEVEL = row.level + 1;
        const PARENT_CODE = row.areaCode;
        const PARENT_NAME = row.name;
        const HAS_CHILDREN = LEVEL < 2;
        commonApi
          .getCity({ areaCode: row.areaCode })
          .then(res => {
            const TEMP = (Array.isArray(res) ? res : []).map(e => {
              return {
                ...e,
                BJ_HAS_CHILD: HAS_CHILDREN,
                level: LEVEL,
                parentCode: PARENT_CODE,
                parentName: PARENT_NAME,
                BJ_PARENT_ID: PARENT_CODE,
              };
            });
            if (TEMP.length) {
              resolve(TEMP);
            } else {
              this.$message.warning('返回数据为空，请联系管理员');
              reject();
            }
          })
          .catch(err => {
            console.error(err);
          });
      });
    },
    toRegion(row) {},
    /** 禁用 */
    // onStatus(flag, row) {
    //   console.log(flag, row);
    //   this.operationApi({
    //     ...row,
    //     enabledFlag: flag,
    //   });
    // },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi({ id: row.id });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    // onDeleteSelected() {
    //   if (!this.selectedRows.length) {
    //     this.$message.warning('请选择');
    //     return;
    //   }
    //   const ids = this.selectedRows.map(e => e.id);
    //   this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi({ ids: ids });
    //     })
    //     .catch(() => {});
    // },
    /** 添加 */
    onAdd(row) {
      this.$refs.DataForm.show();
      if (row) {
        this.$nextTick(() => {
          this.$refs.DataForm.setForm({
            _innerMap: {},
            parentCode: row.areaCode,
            parentName: row.name,
            level: row.level + 1,
          });
        });
      }
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },

    // operationApi(API_DATA) {
    //   commonApi.updateCountry(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    deleteApi(API_DATA) {
      commonApi.deleteArea(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // batchDeleteApi(API_DATA) {
    //   commonApi.batchDeleteBrand(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
<style lang="scss" scoped></style>
