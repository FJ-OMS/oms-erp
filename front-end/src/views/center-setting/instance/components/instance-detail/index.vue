<template>
  <div class="box">
    <instance-img
      ref="instanceImg"
      :data="data"
      @showData="onShowData"
      @showDetail="onShowDetail"
    />
    <div v-if="detail" class="c7n-saga-img-detail">
      <div v-show="title" class="c7n-saga-task-detail">
        <div class="c7n-saga-task-detail-title">{{ title }}</div>
        <div class="c7n-saga-task-detail-content">
          <div class="c7n-saga-detail-json">
            <pre>{{ detail }}</pre>
          </div>
        </div>
      </div>
      <el-tabs v-show="!title" v-model="activeName">
        <el-tab-pane label="基本信息" name="1">
          <el-form label-width="140px" label-position="left">
            <el-form-item label="序列">
              {{ detail.seq }}
            </el-form-item>
            <el-form-item label="任务编码">
              {{ detail.taskCode }}
            </el-form-item>
            <el-form-item label="saga编码">
              {{ detail.sagaCode }}
            </el-form-item>
            <el-form-item label="任务描述">
              {{ detail.description }}
            </el-form-item>
            <el-form-item label="状态">
              <el-tag
                :type="
                  detail.status === 'RUNNING'
                    ? 'success'
                    : detail.status === 'FAILED'
                    ? 'danger'
                    : detail.status === 'COMPLETED'
                    ? 'info'
                    : detail.status === 'NON_CONSUMER'
                    ? 'success'
                    : detail.status === 'WAIT_TO_BE_PULLED'
                    ? 'success'
                    : ''
                "
                >{{ typeList.bjGet(detail.status) }}</el-tag
              >

              <template v-if="detail.status === 'FAILED'">
                <el-button
                  v-show="!wait"
                  type="info"
                  icon="el-icon-refresh"
                  class="ml0 xs"
                  @click="retry(detail.id)"
                  >重 试</el-button
                >
                <el-button
                  v-show="wait"
                  type="danger"
                  icon="el-icon-warning"
                  plain
                  class="ml0 xs"
                  @click="faild(detail.id)"
                  >强制失败</el-button
                >
              </template>
            </el-form-item>
            <el-form-item label="所属微服务">
              {{ detail.service }}
            </el-form-item>
            <el-form-item label="运行的微服务实例">
              {{ detail.instanceLock }}
            </el-form-item>

            <el-form-item label="并发限制模式">
              {{ detail.concurrentLimitPolicy }}
            </el-form-item>
            <el-form-item label="最大并发数">
              {{ detail.concurrentLimitNum }}
            </el-form-item>
            <el-form-item label="最大重试次数">
              {{ detail.maxRetryCount }}
            </el-form-item>

            <el-form-item label="最大重试次数">
              {{ detail.maxRetryCount }}
            </el-form-item>
            <el-form-item label="已重试次数">
              {{ detail.retriedCount }}
            </el-form-item>
            <el-form-item label="计划执行时间">
              {{ detail.plannedStartTime }}
            </el-form-item>
            <el-form-item label="实际开始时间">
              {{ detail.actualStartTime }}
            </el-form-item>
            <el-form-item label="实际完成时间">
              {{ detail.actualEndTime }}
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="参数 & 异常" name="2">
          <el-form label-width="140px" label-position="left">
            <el-form-item label="入参" />
            <div class="c7n-saga-detail-json">
              <pre>{{ detail.input }}</pre>
            </div>
            <el-form-item label="出参" />
            <div class="c7n-saga-detail-json">
              <pre>{{ detail.output }}</pre>
            </div>
            <el-form-item label="异常" />
            <div class="c7n-saga-detail-json">
              <pre>{{ detail.exceptionMessage }}</pre>
            </div>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import instanceImg from '../instance-img';
import { settingApi } from '@/api';
export default {
  components: {
    instanceImg,
  },
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      },
    },
    typeList: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      title: null,
      detail: null,
      activeName: '1',
      wait: false,
      timeOut: null,
      childrenData: null,
      parentIndex: null,
      childIndex: null,
    };
  },
  watch: {
    data(val) {
      this.detail = val.tasks[this.parentIndex][this.childIndex];
      if (typeof this.detail.input && typeof this.detail.output === 'string') {
        this.detail.input = JSON.parse(this.detail.input);
        this.detail.output = JSON.parse(this.detail.output);
      }
    },
  },
  methods: {
    onShowData(data) {
      this.title = data.title;
      this.detail = data.title ? JSON.parse(data.detail) : data.detail;
    },
    onShowDetail(index, int) {
      this.title = '';
      this.parentIndex = index;
      this.childIndex = int;
      this.detail = this.data.tasks[index][int];
      if (typeof this.detail.input && typeof this.detail.output === 'string') {
        this.detail.input = JSON.parse(this.detail.input);
        this.detail.output = JSON.parse(this.detail.output);
      }
    },
    retry(id) {
      this.wait = true;
      settingApi.retryInstancesTask({ id: id }).then(res => {
        this.timeOut = setTimeout(() => {
          this.wait = false;
          this.resetData();
        }, 3000);
      });
    },
    faild(id) {
      this.timeOut ? clearTimeout(this.timeOut) : null;
      settingApi.failedInstancesTask({ id: id }).then(res => {
        this.wait = false;
        this.resetData();
      });
    },
    resetData() {
      this.$emit('resetData', this.data.id);
    },
  },
};
</script>
<style lang="scss" scoped>
.c7n-saga-img-detail {
  float: left;
  margin-left: 24px;
  height: auto;
  width: calc(100% - 416px);
  background: #fff;
  box-shadow: 0 1px 0 0 rgba(0, 0, 0, 0.2), 0 0 0 1px rgba(0, 0, 0, 0.12),
    0 2px 2px -1px rgba(0, 0, 0, 0.12);
  border-radius: 2px;
  padding: 24px;
  padding-bottom: 20px;
}
.c7n-saga-task-detail {
  color: #000;
}
.c7n-saga-task-detail-content {
  font-size: 14px;
  line-height: 20px;
}
.c7n-saga-detail-json {
  max-height: 350px;
  border: 1px solid #f1f9ff;
  background-color: #f1f9ff;
  margin: 10px 0;
  overflow: auto;
}
.el-form-item {
  margin-bottom: 0;
}
// *定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
  width: 7px;
  height: 7px;
  background-color: #fff;
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
  background-color: #fff;
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 4px;
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
  background-color: #c8c8c8;
}
</style>
