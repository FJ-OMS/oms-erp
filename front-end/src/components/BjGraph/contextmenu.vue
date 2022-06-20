<!--
 * @Author: your name
 * @Date: 2021-12-01 14:26:24
 * @LastEditTime: 2021-12-02 17:34:03
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \newBj-admin\src\views\center-setting\stock-structure\components\contextmenu.vue
-->
<template>
  <div>
    <ul class="contextmenu">
      <li @click="addConnect()">添加连接</li>
      <li v-show="typeCode !== 'channel'" class="edit-btn" @click="showUpdataDrawe()">编辑</li>
      <li
        v-show="typeCode !== 'channel' && typeCode !== 'real'"
        class="delete-btn"
        @click="handleDelete()"
      >
        删除
      </li>
    </ul>
  </div>
</template>
<script>
import { stockApi } from '@/api';
export default {
  name: 'Contextmenu',
  inject: ['getGraph', 'getNode'],
  data() {
    return {
      typeCode: null,
      data: null,
    };
  },
  watch: {
    typeCode(val) {
      if (val) {
        document.body.addEventListener('click', this.closeMenu);
      } else {
        document.body.removeEventListener('click', this.closeMenu);
      }
    },
    data: {
      handler(val) {
        if (val) {
          this.typeCode = val.data.typeCode;
        }
      },
      immediate: true,
    },
  },
  mounted() {
    this.data = this.getNode();
    // console.log(this.data);
    // 监听数据改变事件
    this.data.on('change:data', ({ current }) => {
      // console.error('监听到了：', current);
      this.typeCode = current.typeCode;
    });
  },
  methods: {
    addConnect() {
      const id = this.getNode().data.changeId;
      this.$emit('edge', id);
    },
    showUpdataDrawe() {
      const id = this.getNode().data.changeId;
      this.$emit('edit', id);
    },
    handleDelete() {
      this.$confirm('是否删除该仓？')
        .then(_ => {
          stockApi.deleteWarehouses({ id: this.getNode().data.changeId }).then(res => {
            if (!res.failed) {
              this.getGraph().removeNode(this.getNode().data.changeId);
              this.msgSuccess('删除成功');
            }
          });
        })
        .catch(_ => {});
    },
    closeMenu() {
      this.data.visible = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.contextmenu {
  margin: 0;
  background: #fff;
  z-index: 3000;
  position: absolute;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
  li {
    margin: 0;
    padding: 7px 16px;
    cursor: pointer;
    &:hover {
      background: #eee;
    }
  }
}
</style>
