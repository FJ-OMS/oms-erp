<template>
  <div class="c7n-saga-img">
    <div
      :class="{ circleActive: type === 'input' }"
      class="c7n-saga-img-circle"
      @click="showData('input', '输入数据', data.input)"
    >
      Input
    </div>
    <div class="c7n-saga-img-line" />
    <template>
      <template v-for="(taskList, index) in data.tasks">
        <div :key="'squares' + index" class="c7n-saga-img-squares">
          <div
            v-for="(taskObj, taskIndex) in taskList"
            :key="taskIndex + '_' + taskObj.id"
            :class="{
              failed: taskObj.status === 'FAILED',
              completed: taskObj.status === 'COMPLETED',
              other:
                taskObj.status === 'RUNNING' ||
                taskObj.status === 'NON_CONSUMER' ||
                taskObj.status === 'WAIT_TO_BE_PULLED',
              circleActive: type === index + '_' + taskIndex + '_' + taskObj.id,
            }"
            class="c7n-saga-img-square"
            @click="showDetail(index, taskIndex, taskObj)"
          >
            <span>{{ taskObj.taskCode }}</span>
            <span>{{ taskObj.description }}</span>
          </div>
        </div>
        <div :key="'line' + index" class="c7n-saga-img-line" />
      </template>
    </template>
    <div
      :class="{ circleActive: type === 'output' }"
      class="c7n-saga-img-circle"
      @click="showData('output', '输出数据', data.output)"
    >
      Output
    </div>
  </div>
</template>
<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      type: null,
    };
  },
  mounted() {},
  methods: {
    showData(type, title, data) {
      this.type = type;
      const obj = { title: title, detail: data };
      this.$emit('showData', obj);
    },
    showDetail(index, taskIndex, taskObj) {
      this.type = index + '_' + taskIndex + '_' + taskObj.id;
      this.$emit('showDetail', index, taskIndex);
    },
  },
};
</script>
<style lang="scss" scoped>
.c7n-saga-img {
  float: left;
  width: 392px;
  text-align: center;
  .c7n-saga-img-circle {
    width: 98px;
    height: 98px;
    border: 1px solid #000;
    border-radius: 50%;
    text-align: center;
    line-height: 98px;
    z-index: 2;
    display: inline-block;
    cursor: pointer;
  }
  .c7n-saga-img-line {
    position: relative;
    height: 37px;
    &::before {
      content: '';
      height: 28px;
      width: 1px;
      border-left: 2px solid transparent;
      background: -webkit-linear-gradient(white, white) padding-box,
        -webkit-repeating-linear-gradient(
            black 0,
            rgba(0, 0, 0, 0.78) 0,
            rgba(0, 0, 0, 0.78) 50%,
            white 0,
            white 75%
          ) 0/0.6em 0.6em;
      background: linear-gradient(white, white) padding-box,
        repeating-linear-gradient(
            black 0,
            rgba(0, 0, 0, 0.78) 0,
            rgba(0, 0, 0, 0.78) 50%,
            white 0,
            white 75%
          )
          0/0.6em 0.6em;
      opacity: 0.24;
      position: absolute;
    }
    &::after {
      width: 0;
      height: 0;
      opacity: 0.24;
      border-width: 9px 6px 0;
      border-style: solid;
      border-color: rgba(0, 0, 0, 0.78) transparent transparent;
      -webkit-transform: translateX(-5px);
      -ms-transform: translateX(-5px);
      transform: translateX(-5px);
      content: '';
      bottom: 0;
      position: absolute;
      z-index: 1;
    }
  }

  .c7n-saga-img-squares {
    border-radius: 10px;
    border: 1px solid rgba(0, 0, 0, 0.24);
    text-align: center;
    z-index: 2;
    display: inline-block;
    padding: 10px;
  }
  .c7n-saga-img-square {
    padding: 10px;
    width: 102px;
    height: 102px;
    border-radius: 10px;
    border: 1px solid rgba(0, 0, 0, 0.24);
    text-align: center;
    z-index: 2;
    cursor: pointer;
    display: -webkit-inline-box;
    display: -webkit-inline-flex;
    display: -ms-inline-flexbox;
    display: flex;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;

    flex-direction: column;
  }
  .c7n-saga-img-square > span {
    font-size: 12px;
    display: block;
    width: 100%;
    overflow: hidden;
    word-break: break-all;
  }
  .circleActive {
    background: rgba(0, 0, 0, 0.08);
  }

  .c7n-saga-img-square.other {
    background: white;
    color: $main-normal;
    border: 4px solid #ffe8d0;
  }
  .c7n-saga-img-square.failed {
    background: #ffeded;
    color: $danger;
    border: 4px solid #ffdbdb;
  }
  .c7n-saga-img-square.completed {
    background: white;
    color: $minor-normal;
    border: 4px solid $minor-lighter;
  }
  .c7n-saga-img-square.other.circleActive {
    background: white;
    color: $main-normal;
    border: 4px solid $main-normal;
  }
  .c7n-saga-img-square.failed.circleActive {
    background: #ffeded;
    color: $pink;
    border: 4px solid $danger;
  }
  .c7n-saga-img-square.completed.circleActive {
    background: white;
    color: $minor-normal;
    border: 4px solid $minor-light;
  }

  .c7n-saga-img-squares > div {
    margin: 10px;
    float: left;
  }
}
</style>
