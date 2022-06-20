/*
 * 混入

  13-仅退款	1-一审	 1-待审核	   31310
                    2-审核通过	31311
                    3-审核拒绝	31312
            2-二审	1-待审核	  31320
                    2-审核通过	31321
                    3-审核拒绝	31322
14-退货退款	1-一审	 1-待审核	   31410
                    2-审核通过	31411
                    3-审核拒绝	31412
            2-二审	1-待审核	  31420
                    2-审核通过	31421
                    3-审核拒绝	31422

 */
import { afterApi } from '@/api';
export default {
  props: {},
  data() {
    return {};
  },
  filters: {},
  computed: {
    permissionBtns() {
      return this.$store.state.user.permissionBtns || [];
    },
    // 审核权限
    cptAuditPermission() {
      const INDEX = this.AUDIT_TABS_STATUS.findIndex(
        e => e.value == this.queryParams.endorseStatusList.join(),
      );
      const THIS_RULES = this.PERMISSION_RULES[INDEX];
      return THIS_RULES.some(code => this.permissionBtns.includes(code.toLocaleLowerCase()));
    },
    // 批量选中行的权限
    cptSelectRowsPermissionIsRight() {
      return this.selectedRows.every(e => {
        const INDEX = Number(this.getAuditLvl(e.endorseStatus));
        const THIS_RULES = this.PERMISSION_RULES[INDEX];
        return THIS_RULES.some(code => this.permissionBtns.includes(code.toLocaleLowerCase()));
      });
    },
  },
  watch: {},
  created() {},
  mounted() {},
  beforeDestroy() {},
  methods: {
    getAuditLvl(endorseStatus) {
      return endorseStatus.slice(3, 4);
    },
    getMessageName(endorseStatus) {
      // 取 审核状态码的第四位，1为一审，2为二审，以此类推
      return 'receive' + this.getAuditLvl(endorseStatus);
    },
    /** 审核 */
    onApprovedOrder() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择审核数据');
        return;
      }
      const checkStatus = this.selectedRows.every(e => e.status == 0);
      if (!checkStatus) {
        this.$message.warning('请选择待审核的订单');
        return;
      }

      // 权限判断
      if (!this.cptSelectRowsPermissionIsRight) {
        this.$message.warning('您权限不足，无法审核选中的订单');
        return;
      }

      const text = `是否 通过审核 售后单 ${this.selectedRows
        .map(e => e.innerRefundNo)
        .join(',')} ?`;
      this.$confirm(text, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const auditList = this.selectedRows.map(e => {
            return {
              auditId: e.id,
              failReason: '',
              status: 1,
              messageName: this.getMessageName(e.endorseStatus),
            };
          });
          this.batchAuditRefundOrder(auditList);
        })
        .catch(() => {});
    },
    onRefuseOrder() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择审核数据');
        return;
      }
      const checkStatus = this.selectedRows.every(e => e.status == 0);
      if (!checkStatus) {
        this.$message.warning('请选择待审核的订单');
        return;
      }

      // 权限判断
      if (!this.cptSelectRowsPermissionIsRight) {
        this.$message.warning('您权限不足，无法审核选中的订单');
        return;
      }

      const text = `是否 拒绝审核 售后单 ${this.selectedRows
        .map(e => e.innerRefundNo)
        .join(',')}  ?`;
      this.$prompt(text, '审核拒绝', {
        inputPlaceholder: '请输入拒绝原因',
        closeOnClickModal: false,
        inputType: 'textarea',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /\S/,
        inputErrorMessage: '请输入',
      })
        .then(({ value }) => {
          const auditList = this.selectedRows.map(e => {
            return {
              auditId: e.id,
              failReason: value,
              status: 0,
              messageName: this.getMessageName(e.endorseStatus),
            };
          });

          this.batchAuditRefundOrder(auditList);
        })
        .catch(() => {});
    },
    batchAuditRefundOrder(POST_DATA) {
      afterApi.batchAuditRefundOrder(POST_DATA).then(res => {
        console.log(res);
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
