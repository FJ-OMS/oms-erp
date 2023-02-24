<template>
  <el-date-picker
    v-model="id"
    type="datetime"
    :readonly="readonly"
    :disabled="disabled"
    :value-format="valueFormat"
    :placeholder="placeholder"
    class="full-width"
    :size="size"
    :picker-options="disabledPickerDate(startTime)"
  />
</template>
<script>
/**
 *
 * @props
 * 示例：
 *
 */
import { parseTime } from '@/utils';
export default {
  name: 'BjDateTimerPicker',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    startTime: {
      type: [Object, String],
      default: () => {
        return null;
      },
    },
    size: {
      type: String,
      default: 'mini',
    },
    valueFormat: {
      type: String,
      default: 'yyyy-MM-dd HH:mm:ss',
    },
    placeholder: {
      type: String,
      default: '请输入',
    },
  },
  data() {
    return {
      id: null,
      // selectableRange: '00:00:00 - 23:59:59',
    };
  },
  computed: {
    selectableRange() {
      console.log('cpt selectableRange', this.id);

      if (this.id) {
        const END_DATE = parseTime(this.id, '{y}{m}{d}');
        const NOW_DATE = parseTime(this.startTime, '{y}{m}{d}');

        console.log(END_DATE, NOW_DATE);
        // 选择了和开始时间同一天
        if (END_DATE === NOW_DATE) {
          // 同一天，但比startTime更早，就自动重置为startTime
          if (new Date(this.id).getTime() < new Date(this.startTime).getTime()) {
            this.id = this.startTime;
          }
          // 只可选择部分时间范围
          const START_TIME = parseTime(this.startTime, '{h}:{i}:{s}');
          return `${START_TIME} - 23:59:00`;
        }
      }

      return '00:00:00 - 23:59:59';
    },
  },
  watch: {
    value: {
      handler(v) {
        console.log(v);
        this.id = v || null;
      },
      immediate: true,
    },
    id(v) {
      this.$emit('input', v);
      this.$emit('change', v);
    },
  },
  activated() {
    this.id = this.value;
    console.log(this.value);
  },
  mounted() {},
  methods: {
    disabledPickerDate(startDate) {
      const TEMP_DATE = startDate ? new Date(startDate) : new Date();
      return {
        disabledDate: time => {
          return time.getTime() < TEMP_DATE.getTime() - 8.64e7 + 1;
        },
        selectableRange: this.selectableRange,
        // https://blog.csdn.net/weixin_46074961/article/details/106403555?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-1-106403555.pc_agg_new_rank&utm_term=element%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4%E9%80%89%E6%8B%A9selectableRange&spm=1000.2123.3001.4430
      };
    },
  },
};
</script>
