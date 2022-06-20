<template>
  <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="dialogVisible">
    <el-form ref="form" :inline="false" :model="form" :rules="rules">
      <el-form-item label="" prop="value">
        <BjCheckboxLov v-model="form.value" :option-list="BJ_TAGS" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmEditRemarks">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: 'BusinessOrderInternalViewFormEditTags',
  components: {},
  props: {
    title: {
      type: String,
      default: '打标',
    },
    id: {
      type: String,
      default: null,
    },
    tags: {
      type: Array,
      default: function() {
        return [];
      },
    },
  },
  data() {
    return {
      // 弹窗显示
      dialogVisible: false,

      form: {
        value: [],
      },
    };
  },
  computed: {
    // 表单校验
    rules() {
      return {
        value: [{ required: false, message: '请选择' + this.title, trigger: 'change' }],
      };
    },
    BJ_TAGS() {
      return (this.$store.state.values.BJ_TAGS || []).filter(e => !e.systemLabelFlag);
    },
  },
  mounted() {
    this.$store.dispatch('values/getTags');
  },
  methods: {
    onEdit() {
      this.dialogVisible = true;
      this.form.value = [...this.tags];
    },
    confirmEditRemarks() {
      let POST_DATA = [];
      if (this.form.value && this.form.value.length) {
        POST_DATA = [...this.form.value];
      }

      this.$emit('editBaseInfoApi', {
        orderId: this.id,
        tags: POST_DATA.join(),
      });
    },
  },
};
</script>
