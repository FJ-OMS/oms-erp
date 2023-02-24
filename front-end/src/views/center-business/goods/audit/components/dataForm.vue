<template>
  <div class="app-container">
    <el-form label-suffix="：">
      <BjDivider>基本信息 </BjDivider>
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="商品分类">服装/男装/羽绒服</el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品名称">柒牌男装羽绒服</el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品编码">00001</el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="品牌名称">柒牌</el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="SKU名称">红色/机械款L</el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="SKU编码">SP0001</el-form-item>
        </el-col>
      </el-row>
      <BjDivider>匹配信息</BjDivider>
      <el-col :span="colSpan">
        <el-form-item label="平台类型">淘宝</el-form-item>
      </el-col>
      <el-col :span="colSpan">
        <el-form-item label="店铺名称">淘宝-九牧旗舰店</el-form-item>
      </el-col>
      <el-col :span="colSpan">
        <el-form-item label="平台规格编码">SP0001</el-form-item>
      </el-col>
      <el-col :span="colSpan">
        <el-form-item label="平台规格名称">红色/机械款L</el-form-item>
      </el-col>
      <el-col :span="colSpan">
        <el-form-item label="操作">上架</el-form-item>
      </el-col>
      <BjDivider>审核</BjDivider>
      <el-form-item label="提交人员">张三</el-form-item>
      <el-form-item>
        <el-button type="primary" class="btn-default-width" @click="onPass">审核通过</el-button>
        <el-button type="warning" class="btn-default-width" @click="onReject">审核拒绝</el-button>
      </el-form-item>
    </el-form>
    <dialogIdea ref="dialogIdea" @confirm="onConfirm"></dialogIdea>
  </div>
</template>

<script>
import dialogIdea from './dialogIdea';
export default {
  components: { dialogIdea },
  data() {
    return {
      colSpan: 8,
      column: 3,
      border: false,
    };
  },
  methods: {
    /** 通过 */
    onPass() {
      this.$refs.dialogIdea.show('审核通过');
    },
    /** 驳回 */
    onReject() {
      this.$refs.dialogIdea.show('审核拒绝');
    },
    onConfirm(data) {
      console.log(data);
      this.msgSuccess('操作成功');
    },
  },
};
</script>

<style></style>
