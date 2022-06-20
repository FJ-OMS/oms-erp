<template>
  <el-drawer
    class="drawer-data-form-20210720"
    :title="title"
    :visible.sync="visible"
    direction="rtl"
    :before-close="hide"
    :with-header="withHeader"
    :size="size"
  >
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :auto-fetch="false"
      :search-btn-hide="true"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >

          <el-button type="primary" class="main-btn" @click="onAdd">新增</el-button>
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
              <el-switch
                v-else-if="col.prop === 'enabledFlag'"
                v-model="scope.row[col.prop]"
                :active-value="1"
                :inactive-value="0"
              />
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
            <template slot-scope="props">
              <el-button type="text" class="delete-btn" @click="onAction('编辑', props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <div class="footer" :class="withHeader ? 'has-title' : ''">
      <el-button @click="hide">返 回</el-button>
      <!-- <el-button type="primary" @click="handleConfirm">返 回</el-button> -->
    </div>
  </el-drawer>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { messageApi } from '@/api';
import { patternEmail } from '@/utils/validate';
export default {
  name: 'SettingMessageEmailFilters',
  mixins: [pageMiXin],
  props: {
    size: {
      type: String,
      default: '61.8%',
    },
  },
  data() {
    return {
      queryApi: messageApi.getEmailFilters,
      // 查询参数
      queryParams: { serverId: null },
      labelList: [{ label: '邮箱', prop: 'address', isCheck: true, disabled: false }],
      /** */
      title: '',
      visible: false,
      info: null,
    };
  },
  computed: {
    withHeader() {
      return !!this.title;
    },
  },
  methods: {
    show(info) {
      this.visible = true;
      this.info = this.$lodash.clone(info);
      if (info.serverId) {
        this.queryParams.serverId = info.serverId;
      }
      this.$nextTick(() => {
        this.$refs.BjPage.fetchData();
      });
    },
    onAdd() {
      this.$prompt('请输入邮箱', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: patternEmail,
        inputErrorMessage: '邮箱格式不正确',
      })
        .then(({ value }) => {
          const API_DATA = {
            _innerMap: {},
            address: value,
            serverId: this.info.serverId,
            tenantId: 'default',
          };

          this.addApi([API_DATA]);
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入',
          });
        });
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.batchDeleteApi({ BjBody: this.selectedRows });
      });
    },

    setTitle(v) {
      this.title = v;
    },
    hide() {
      this.visible = false;
    },
    handleConfirm() {
      this.$emit('handleConfirm');
      this.hide();
    },
    batchDeleteApi(API_DATA) {
      messageApi.deleteEmailFilters(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      messageApi.addEmailFilters(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>

<style lang="scss">
/***/
.drawer-data-form-20210720 {
  .el-drawer__container {
    padding: 8px;
  }
  .el-drawer__header {
    margin-bottom: 4px;
  }
  .el-drawer__body {
    padding: 16px;
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  .el-drawer__body form {
    flex: 1;
    max-height: 100%;
    overflow-y: auto;
    padding: 16px;
  }
  .el-drawer__body .footer button {
    width: 200px;
  }
  .footer.has-title {
    margin-bottom: 45px;
  }
}
</style>
