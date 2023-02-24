<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="`订单 ${orderNo} 的日志`"
    :visible.sync="dialogTableVisible"
    width="60%"
  >
    <div class="wrap">
      <el-table :data="tableData" style="width: 100%" :max-height="maxHeight">
        <el-table-column type="index" label="序号" width="55" align="center" />
        <el-table-column prop="createdBy" label="操作用户" align="center" />
        <el-table-column prop="content" label="操作内容" align="center" />
        <el-table-column prop="creationDate" label="操作时间" align="center" />
      </el-table>
    </div>
  </el-dialog>
</template>
<script>
// 订单统一调用 $orderlog(内部订单号)
// import { orderApi } from "@/api";
// import { ORDER_LOG_STATUS } from '@/utils/constants';
import { getOffsetTop } from '@/utils/index';
export default {
  name: 'OrderLogList',
  // props: ['orderNo'],
  data() {
    return {
      moduleName: '日志列表', // 模块名称
      tableData: [],
      dialogTableVisible: false,
      orderNo: '',
      // ORDER_LOG_STATUS,
      maxHeight: '480px',
    };
  },
  computed: {
    classID() {
      return (
        'bj-dialog-list-' +
        Number(
          Math.random()
            .toString()
            .substr(3, 12) + Date.now(),
        )
          .toString(36)
          .substr(0, 8)
          .toUpperCase()
      );
    },
  },
  watch: {
    dialogTableVisible() {
      if (this.dialogTableVisible) {
        this.$nextTick(() => {
          this.init();
          this.maxHeight =
            document.documentElement.clientHeight -
            getOffsetTop(`.${this.classID} .el-table__header-wrapper`) * 2 -
            0 +
            'px';
        });
      }
    },
  },
  mounted() {},
  methods: {
    init() {
      this.tableData = this.$getMockData([
        {
          createdBy: '@cname',
          'content|1': [
            '创建了订单',
            '下单完成',
            '下单失败',
            '支付完成',
            '支付失败',
            '挂起',
          ],
          creationDate: '@dateTime',
        },
      ]);
      // orderApi.log({ code: this.orderNo }).then(res => {
      //   this.tableData = res || [];
      // });
    },
  },
};
</script>
<style lang="scss" scoped>
.wrap {
  .werp-img {
    width: 30px;
    height: 30px;
    margin-right: 5px;
  }
}
</style>
