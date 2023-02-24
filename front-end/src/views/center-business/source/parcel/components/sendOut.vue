<!--
 * @Author: your name
 * @Date: 2021-11-17 14:09:55
 * @LastEditTime: 2021-11-17 17:43:17
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \newBj-admin\src\views\center-business\source\parcel\components\sendOut1.vue
-->
<template>
  <BjDialogTable
    ref="dialogList"
    :config="dialogListConfig"
    multiple
    :disabled="disabled"
    @confirm="onConfirmDialogList"
  >
    <template>
      <el-table-column label="商品名称" prop="spuName" align="center"></el-table-column>
      <el-table-column label="分包数量" prop="num" align="center" :render-header="renderHeader">
        <template slot-scope="scope">
          <BjInputNumber v-model="scope.row.num" />
        </template>
      </el-table-column>
      <el-table-column label="操 作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="onDelete(scope.row, scope.$index)">删 除</el-button>
        </template>
      </el-table-column>
    </template>
  </BjDialogTable>
</template>
<script>
import { goodsApi, strategyApi } from '@/api';
export default {
  data() {
    return {
      /** 选择商品 */
      dialogListConfig: {
        title: '选择商品',
        columns: [
          {
            prop: 'spuCode',
            label: '商品编码',
          },
          {
            prop: 'spuName',
            label: '商品名称',
          },
        ],
        search: [
          {
            placeholder: '请输入商品编码',
            key: 'spuCode',
          },
          {
            placeholder: '请输入商品名称',
            key: 'spuName',
          },
        ],
        actionApi: goodsApi.getSku,
        query: '',
      },
      /** 商品禁选项 */
      disabled: [],
    };
  },
  methods: {
    show() {
      this.$refs.dialogList.show();
    },
    hide() {},
    /** 删除  */
    onDelete(row, index) {
      this.$refs.dialogList.onDelete(index);
    },
    renderHeader(h, { column, $index }) {
      return [
        column.label,
        h(
          'el-tooltip',
          {
            props: {
              content: '设置数量，则按指定数量分包,不设置数量则按指定商品，数量不限制',
              placement: 'top',
            },
          },
          [
            h('span', {
              class: {
                'el-icon-question': true,
              },
            }),
          ],
        ),
      ];
    },
    onConfirmDialogList(data) {
      console.log(data);
      //   this.disabled = data.map(e => e.id);
      const list = data.map(e => {
        return {
          packageAmount: e.num,
          sku: e.skuCode,
          spu: e.spuCode,
          spuName: e.spuName,
        };
      });
      strategyApi.addPackageSettingSingleGoods(list).then(res => {
        console.log(res);
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
      // this.list = [
      //   ...this.list,
      //   ...data.map(e => {
      //     return {
      //       id: e.id,
      //       spuName: e.spuName,
      //       num: null,
      //     };
      //   }),
      // ];
    },
  },
};
</script>
