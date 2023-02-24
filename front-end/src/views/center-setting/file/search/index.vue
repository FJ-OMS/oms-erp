<template>
  <div class="app-container">
    <!--  sort="orderSeq" -->
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      label-width="90px"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      res="listConfig"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <!-- <el-form-item label="批号" prop="attachmentUuid">
          <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model.trim="queryParams.attachmentUuid" clearable />
        </el-form-item> -->
        <el-col :span="colSpan">
          <el-form-item label="分组" prop="bucketName">
            <el-input
              v-model.trim="queryParams.bucketName"
              :maxlength="MAX_LENGTH.DEFAULT"
              clearable
            />
          </el-form-item>
        </el-col>
        <!-- <el-form-item label="文件类型" prop="contentType">
          <el-select
            v-model.trim="form_contentType"
            class="full-width"
            placeholder="请选择"
            filterable
            clearable
            multiple
          >
            <el-option
              v-for="(item, index) in HFLE_CONTENT_TYPE"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="租户" prop="tenantId">
          <el-select
            v-model.trim="queryParams.tenantId"
            class="full-width"
            placeholder="请选择"

          >
            <el-option
              v-for="(item, index) in HSDR_TENANTS"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item> -->
        <el-col :span="colSpan">
          <el-form-item label="文件名" prop="fileName">
            <el-input
              v-model.trim="queryParams.fileName"
              :maxlength="MAX_LENGTH.DEFAULT"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="文件格式" prop="fileFormat">
            <BjSelectLov
              v-model="form_fileFormat"
              lov-code="HFLE.FILE_FORMAT"
              filterable
              clearable
              multiple
            />
          </el-form-item>
        </el-col>
        <!-- <el-form-item label="创建日期" prop="creationDate">
          <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model.trim="queryParams.creationDate" clearable />
        </el-form-item> -->
        <el-col :span="colSpan">
          <el-form-item label="上传人" prop="realName">
            <el-input
              v-model.trim="queryParams.realName"
              :maxlength="MAX_LENGTH.DEFAULT"
              clearable
            />
          </el-form-item>
        </el-col>
        <!-- <el-form-item label="文件最小" prop="fileSize" class="el-input-group__append-width-100">
          <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model.trim="queryParams.storageSize" placeholder="请输入">
            <el-select slot="append" v-model.trim="queryParams.storageUnit" placeholder="请选择" clearable>
              <el-option
                v-for="(item, index) in HFLE_STORAGE_UNIT"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-input>
        </el-form-item>
        <el-form-item label="文件最大" prop="fileSize" class="el-input-group__append-width-100">
          <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model.trim="queryParams.storageSize" placeholder="请输入">
            <el-select slot="append" v-model.trim="queryParams.storageUnit" placeholder="请选择" clearable>
              <el-option
                v-for="(item, index) in HFLE_STORAGE_UNIT"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-input>
        </el-form-item> -->
        <el-col :span="colSpan">
          <el-form-item label="上传目录" prop="directory">
            <el-input
              v-model.trim="queryParams.directory"
              :maxlength="MAX_LENGTH.DEFAULT"
              clearable
            />
          </el-form-item>
        </el-col>
        <!-- <el-form-item label="服务器编码" prop="xxx">
          <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model.trim="queryParams.xxx" clearable />
        </el-form-item> -->
        <el-col :span="colSpan">
          <el-form-item label="来源类型" prop="sourceType">
            <el-select
              v-model.trim="queryParams.sourceType"
              class="full-width"
              placeholder="请选择"
              filterable
              clearable
            >
              <el-option
                v-for="(item, index) in FILE_STORAGE_TYPE_LIST"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </template>

      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:SettingFileSearchIndex_UpLoad
            type="primary"
            class="main-btn"
            @click="onAdd"
            >文件上传</el-button
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
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <span v-else-if="col.prop === 'defaultFlag'">{{
                STATUS_YES_NUM.bjGet(scope.row.defaultFlag)
              }}</span>
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
              <!--  v-if="props.row.fileType.indexOf('image') == 0" -->
              <el-button
                v-permission:SettingFileSearchIndex_View
                type="text"
                @click="onView(props.row)"
                >预览</el-button
              >
              <el-button
                v-permission:SettingFileSearchIndex_DownLoad
                type="text"
                @click="onLoad(props.row)"
                >下载</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <el-image-viewer v-if="showViewer" :on-close="onViewerClose" :url-list="imageViewerSrcList" />
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import ElImageViewer from 'element-ui/packages/image/src/image-viewer';
import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';
import { FILE_STORAGE_TYPE_LIST, STATUS_YES_NUM } from '@/utils/constants';
import { downloadBlob, getBlobURL, openWin } from '@/utils/index';
import DataForm from './components/DataForm.vue';
export default {
  name: 'SettingFileSearchIndex',
  components: {
    ElImageViewer,
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      STATUS_YES_NUM,
      FILE_STORAGE_TYPE_LIST,
      queryApi: settingApi.getFileList,
      // 查询参数
      queryParams: {
        tenantId: null,
        bucketName: null,
        directory: null,
        fileType: null,
        fileName: null,
        attachmentUuid: null,
        contentType: null,
        fileFormat: null,
        storageSize: null,
        storageUnit: null,
        sourceType: null,
      },

      labelList: [
        { label: '租户', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '分组', prop: 'bucketName', isCheck: true, disabled: false },
        { label: '上传目录', prop: 'directory', isCheck: true, disabled: false },
        { label: '关联表', prop: 'hsdr_job_log', isCheck: true, disabled: false },

        { label: '文件类型', prop: 'fileType', isCheck: true, disabled: false },
        { label: '文件名', prop: 'fileName', isCheck: true, disabled: false, width: 200 },
        { label: '上传人', prop: 'realName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '更新时间', prop: 'lastUpdateDate', isCheck: true, disabled: false, width: 135 },
        { label: '文件大小', prop: 'fileSize', isCheck: true, disabled: false },
        { label: '批号', prop: 'attachmentUuid', isCheck: true, disabled: false },
        { label: '来源类型', prop: 'sourceTypeMeaning', isCheck: true, disabled: false },
        //  { label: '服务器编码', prop: '', isCheck: true, disabled: false },

        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      form_contentType: null,
      form_fileFormat: null,

      showViewer: false,
      imageViewerSrcList: [],
      /** */
    };
  },
  computed: {},
  watch: {
    form_contentType(v) {
      if (v) this.queryParams.contentType = v.join(',');
    },
    'form.contentType'(v) {
      if (v) this.form_contentType = v.split(',');
    },
    form_fileFormat(v) {
      if (v) this.queryParams.fileFormat = v.join(',');
    },
    'form.fileFormat'(v) {
      if (v) this.form_fileFormat = v.split(',');
    },
  },
  mounted() {},
  methods: {
    handleConfirm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.$refs.upload.submitUpload();
      });
    },
    onLoad(row) {
      settingApi
        .downloadFileByKey({
          fileKey: row.fileKey,
        })
        .then(res => {
          downloadBlob(res, row.fileName);
        });
    },
    onView(row) {
      const t = row.fileName
        .split('.')
        .slice(-1)[0]
        .toLowerCase();
      if (row.fileType.indexOf('image') != -1 && ['png', 'jpg', 'jpeg'].includes(t)) {
        settingApi
          .getFileUrlByKey({
            fileKey: row.fileKey,
            expires: 60 * 30,
          })
          .then(res => {
            if (res.failed !== true) {
              let url = res.fileTokenUrl || res.message || res;
              if (res.fileTokenUrl.indexOf('http') !== 0) {
                url = 'http://' + url;
              }
              this.showViewer = true;
              this.imageViewerSrcList = [url];
            }
          });
      } else {
        const w = openWin('./loading.html');
        settingApi
          .viewFileByKey({
            fileKey: row.fileKey,
          })
          .then(res => {
            const URL = getBlobURL(res);
            w.location.href = URL;
          });
      }
    },

    onViewerClose() {
      this.showViewer = false;
      this.imageViewerSrcList = [];
    },

    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
  },
};
</script>
<style lang="scss">
.el-input-group__append-width-100 .el-input-group__append {
  width: 80px;
  left: -80px;
}
</style>
