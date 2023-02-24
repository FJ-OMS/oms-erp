<template>
  <div class="bj-tags-list-20220225">
    <BjTag
      v-for="(tagCode, index) in cptOrderTagCodeList"
      :key="index"
      :size="size"
      :color="(BJ_TAGS.find(e => e.labelCode == tagCode) || {}).textColor"
      :text="(BJ_TAGS.find(e => e.labelCode == tagCode) || {}).textContent"
      :closable="closable && !(BJ_TAGS.find(e => e.labelCode == tagCode) || {}).systemLabelFlag"
      @close="handleCloseTag(tagCode)"
    />
    <slot></slot>
  </div>
</template>
<script>
export default {
  name: 'BjTagList',
  props: {
    value: {
      type: [String, Array],
      default: null,
    },
    size: {
      type: String,
      default: 'medium',
    },
    tagCodeString: {
      type: String,
      default: null,
    },
    tagCodeArray: {
      type: Array,
      default: function() {
        return [];
      },
    },
    closable: {
      type: Boolean,
      default: false,
    },
    circle: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {};
  },
  computed: {
    BJ_TAGS() {
      return this.$store.state.values.BJ_TAGS || [];
    },
    cptOrderTagCodeList() {
      if (Array.isArray(this.tagCodeArray) && this.tagCodeArray.length) {
        return this.tagCodeArray;
      }
      if (this.tagCodeString) {
        return this.tagCodeString.split(',');
      }

      if (this.value) {
        if (Array.isArray(this.value) && this.value.length) {
          return this.value;
        }
        if (this.value) {
          return this.value.split(',');
        }
      }

      return [];
    },
  },
  mounted() {
    if (!this.BJ_TAGS.length) this.$store.dispatch('values/getTags');
  },
  methods: {
    /** 删除一个标签 */
    handleCloseTag(code) {
      this.$emit('close', code);
    },
  },
};
</script>
<style lang="scss">
.bj-tags-list-20220225 {
  width: 100%;
  display: flex;
}
</style>
