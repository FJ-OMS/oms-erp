<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" width="600px" :before-close="handleClose">
    <el-input
      v-model="remark"
      type="textarea"
      placeholder="请输入"
      :maxlength="MAX_LENGTH.REMARK"
      show-word-limit
      clearable
      rows="5"
    ></el-input>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      dialogVisible: false,
      title: '审核通过',
      remark: null,
    };
  },
  methods: {
    handleClose(done) {
      this.hide();
      // this.$confirm('确认关闭？')
      //   .then(_ => {
      //     done();
      //   })
      //   .catch(_ => {});
    },
    show(title) {
      this.title = title;
      this.remark = null;
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    confirm() {
      this.$emit('confirm', this.remark);
      this.hide();
    },
  },
};
</script>

<style></style>
