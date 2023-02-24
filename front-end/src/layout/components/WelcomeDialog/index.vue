<template>
  <el-dialog
    title="游客，您好！"
    :visible.sync="dialogVisible"
    width="495px"
    :close-on-click-modal="false"
    :before-close="handleClose"
  >
    <div class="bj-welcome">
      <span class="title">欢迎体验飞骥中台！温馨提醒您：</span>
      <ul>
        <li>点击“配置中心”，可进行库存结构、业务流程和规则策略等基础信息配置</li>
        <li>
          点击“业务中心”，可进行用户、渠道、商品、库存、订单、售后、财务和营销管理
        </li>
      </ul>
      <p>如果您体验中有任何问题，欢迎与我们联系：</p>
      <p>服务热线：159 5912 2386</p>
      <p>我们将竭诚为您服务！</p>
    </div>
    <span slot="footer" class="dialog-footer flex-center">
      <el-button size="medium" type="primary" style="width: 200px;" @click="handleClose"
        >知道了</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: 'WelcomeDialog',
  data() {
    return {
      dialogVisible: false,
    };
  },
  created() {
    const visible = JSON.parse(sessionStorage.getItem('bjWelcomeDialogVisible'));
    this.dialogVisible = visible == false ? visible : true;
  },
  methods: {
    handleClose() {
      this.dialogVisible = false;
      sessionStorage.setItem('bjWelcomeDialogVisible', JSON.stringify(this.dialogVisible));
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-height: calc(100% - 30px);
  max-width: calc(100% - 30px);
}
::v-deep .el-dialog .el-dialog__body {
  flex: 1;
  overflow: auto;
  padding-top: 0;
  padding-bottom: 10px;
}
.bj-welcome {
  .title {
    font-weight: 400;
    font-size: 16px;
    color: #333333;
  }
  ul {
    background: #f5f7f9;
    margin: 0;
    padding: 15px 10px 15px 25px;
    list-style: none;
    margin-top: 10px;
  }
  ul li {
    position: relative;
    color: #333333;
    line-height: 24px;
  }
  ul li::before {
    position: absolute;
    content: ' ';
    left: -10px;
    top: 10px;
    width: 4px;
    height: 4px;
    border-radius: 50%;
    background-color: $main-dark;
  }
  span {
    font-weight: 400;
    font-size: 14px;
    color: #333333;
    letter-spacing: 0;
    line-height: 24px;
  }
  p {
    font-weight: 400;
    font-size: 14px;
    color: #333333;
  }
}
</style>
