<!--
 * @Author: your name
 * @Date: 2021-11-23 13:49:46
 * @LastEditTime: 2022-06-09 11:27:43
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \newBj-admin\src\views\center-setting\stock-structure\components\antG6\index.vue
-->
<template>
  <div class="app-container">
    <div v-if="leftAddon" id="stencil" ref="stencilContainer" class="aside" />
    <div class="main">
      <div class="text-right">
        <el-select v-model="edgeRouter" class="mr10">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button v-if="movable" type="primary" @click="$emit('refresh')">重 置</el-button>
        <el-button v-if="movable" type="primary" @click="onSave">保存</el-button>
      </div>
      <div id="container" ref="container" class="container" />
    </div>
    <!-- 添加库存弹窗 -->
    <storeForm ref="storeForm" @handleConfirm="handleConfirm" @close="storeFormClose" />
    <!-- 选择连接弹窗 -->
    <updataForm ref="updataForm" @handleConfirm="$emit('refresh')" />
    <!-- 连接线百分比设置 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="'分配比例'"
      :visible.sync="dialogVisible"
      :append-to-body="true"
      width="400px"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item
          v-for="(item, index) in form.ratioList"
          :key="item.index"
          label="分配比例"
          :prop="'ratioList.' + index + '.ratio'"
          :rules="rules.ratio"
        >
          <el-input v-model="item.ratio" type="number"
            ><template v-if="!item.distributionType" slot="append">%</template></el-input
          >
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { stockApi } from '@/api';
import { Graph, Addon } from '@antv/x6';
import storeForm from './storeForm';
import updataForm from './updataForm';
import Contextmenu from './contextmenu';
import '@antv/x6-vue-shape';
// 节点统一宽高
const [NODE_WITTH, NODE_HEIGHT] = [120, 40];
export default {
  name: 'Graph',
  components: { storeForm, updataForm },
  props: {
    api: {
      type: Function,
      default: () => {
        return null;
      },
    },
    /** 是否显示侧边栏 */
    leftAddon: {
      type: Boolean,
      default: false,
    },
    /** 是否开启右键展示选择框 */
    menu: {
      type: Boolean,
      default: false,
    },
    /** 是否显示边文字 */
    edgeText: {
      type: Boolean,
      default: false,
    },
    /** 是否开启点击边设置百分比功能 */
    clickEdge: {
      type: Boolean,
      default: false,
    },
    /** 是否允许节点拖拽 */
    movable: {
      type: Boolean,
      default: true,
    },
    // 只显示比例虚仓
    onlyRatioVirtual: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      structureId: this.$route.query.structureId || null,
      nodes: [],
      edges: [],
      // 判断用户是否拖拽过节点发生数据变化
      isChange: false,
      // 组（默认数据）
      group: {
        x: 0,
        y: 0,
        width: 0,
        height: 0,
        zIndex: 1,
        label: '',
        attrs: {
          label: {
            fontSize: 18,
          },
          body: {
            // fill: '#fffbe6',
            stroke: '#ffe7ba',
          },
        },
      },
      // 节点基础数据
      nodeData: {
        nodeId: null,
        id: null,
        typeCode: null,
        x: 0,
        y: 0,
        width: NODE_WITTH,
        height: NODE_HEIGHT,
        zIndex: 10,
        attrs: {
          body: {
            strokeWidth: 1,
            rx: 4,
            ry: 4,
          },
          label: {
            fill: '#606266',
            fontSize: 10,
            textWrap: {
              width: NODE_WITTH - 10,
              height: 40,
              ellipsis: true,
            },
            lineHeight: 14,
          },
        },
      },
      // 画布
      graph: null,
      containerScrollTop: 0,
      // 根据滚动条滚动的数据
      FIXED_BOTTOM_LIST: [],
      dialogVisible: false,
      form: {
        ratioList: [{ ratio: '' }],
      },
      rules: {
        ratio: [
          {
            validator: (rule, value, callback) => {
              if (/^(?:[0-9]\d*)$/.test(value) == false) {
                callback(new Error('请输入正整数比例值'));
              } else {
                callback();
              }
            },
            trigger: 'blur',
          },
        ],
      },
      // 连线类型
      options: [
        { label: '直线', value: 'normal' },
        { label: '折线', value: 'orth' },
        // { label: 'oneSide', value: 'oneSide' },
        { label: '避让线', value: 'manhattan' },
        { label: '铁轨线', value: 'metro' },
        { label: '关系线', value: 'er' },
      ],
      // 连线方式
      edgeRouter: 'normal',
    };
  },
  watch: {
    /** 监听连线方式变化重新渲染 */
    edgeRouter(val) {
      this.graph.getEdges().forEach(edge => {
        this.graph.removeEdge(edge);
      });
      this.addEdges();
    },
  },
  created() {},
  mounted() {
    const self = this;
    // 创建画布
    this.graph = new Graph({
      container: document.getElementById('container'), // 指定画布容器
      // resizing: true, // 改变节点大小
      // panning: {
      //   enabled: true,
      // },
      grid: 10, // 网格大小 10px，并绘制网格
      width: this.$refs.container.offsetWidth, // 画布宽度
      height: this.$refs.container.offsetHeight, // 画布高度
      // 是否监听容器大小改变，并自动更新画布大小，默认为 false。默认监听画布容器，也可以指定监听的元素，如 Docuemnt。
      autoResize: true,
      snapline: true, // 对齐线
      scroller: {
        enabled: true,
        pannable: true, // 是否启用画布平移能力（在空白位置按下鼠标后拖动平移画布），默认为 false。
        pageVisible: true, // 是否分页，默认为 false。
        pageBreak: true, // 是否显示分页符，默认为 false
        autoResize: true,
      },
      mousewheel: {
        enabled: true,
        minScale: 0.8,
        maxScale: 1.2,
      },
      translating: {
        // 限制节点不能拖动出组区域
        restrict(view) {
          const cell = view.cell;
          if (cell.isNode()) {
            const parent = cell.getParent();
            if (parent) {
              return parent.getBBox();
            }
          }
          return null;
        },
      },
      // 判断能否滑动
      interacting: function(cellView) {
        if (self.movable) {
          const typeCode = cellView.cell.store.data.typeCode;
          // 渠道仓、实仓、逻辑仓不能移动
          if (typeCode === 'channel' || typeCode === 'logic' || typeCode === 'real') {
            return { nodeMovable: false };
          }
          return true;
        } else {
          return { nodeMovable: false };
        }
      },
    });
    if (this.menu) {
      // 添加右键选择框节点
      // 注册 vue component
      // 如果需要序列化/反序列化数据，必须使用该方式
      const self = this;
      // 创建自定义节点
      Graph.registerVueComponent(
        'contextmenu',
        {
          template: `<Contextmenu ref='contextmenu' @edit="onEdit" @edge="addEdge" />`,
          components: {
            Contextmenu,
          },
          methods: {
            onEdit(id) {
              const data = self.nodes.find(e => e.id === id);
              self.$refs.storeForm.show(data);
            },
            addEdge(id) {
              const data = self.nodes.find(e => e.id === id);
              data.structureId = self.structureId;
              self.$refs.updataForm.show(data);
            },
          },
        },
        true,
      );
      // 添加自定义节点到画布
      this.graph.addNode({
        id: 'contextmenu',
        shape: 'vue-shape',
        visible: false,
        x: -200,
        y: 0,
        width: 180,
        height: 40,
        zIndex: 99,
        component: 'contextmenu',
        data: {
          typeCode: null,
          changeId: null,
        },
      });
    }
    this.init();
    // 监听画布滚动事件
    const scrollview = document.querySelector('.x6-graph-scroller');
    scrollview.addEventListener('scroll', this.scrollChange, true);
    this.containerScrollTop = scrollview.scrollTop;
  },
  methods: {
    // 画布滚动监听
    scrollChange() {
      const OLD_SCROLL_TOP = this.containerScrollTop;
      this.containerScrollTop = document.querySelector('.x6-graph-scroller').scrollTop;
      const top = this.containerScrollTop - OLD_SCROLL_TOP;
      // 设置实仓和逻辑仓组固定在底部
      this.FIXED_BOTTOM_LIST.forEach(node => {
        const { x, y } = node.position();
        node.position(x, y + top);
      });
    },
    async init(resetEdges) {
      // 获取数据
      const res = await this.getData(this.structureId);
      this.structureId = res.structureId;
      if (this.onlyRatioVirtual) {
        res.allList = res.allList.filter(
          e => e.typeCode == 'ratio-virtual' || e.typeCode == 'real',
        );
      }
      // 处理后端返回数据格式，数组为null处理
      Object.keys(res).forEach(key => {
        res[key] = res[key] || [];
      });
      // 赋值连线数据
      this.relationList = res.relationList;
      this.nodes = [...res.allList, ...res.channelList, ...res.logicList];
      // 获取计算组宽度和子节点x轴位置
      const nodeGroup = this.getNodeGroupWidth(res);
      // 创建渠道仓组
      const channel = this.graph.addNode(
        Object.assign(this.group, {
          typeCode: 'channel',
          x: 20,
          y: 20,
          width: nodeGroup['channel'].width,
          height: NODE_HEIGHT + 20,
        }),
      );
      // 创建逻辑仓组
      const logicHeight =
        document.documentElement.clientHeight -
        this.$refs.container.getBoundingClientRect().top -
        106;
      const logic = this.graph.addNode(
        Object.assign(this.group, {
          typeCode: 'logic',
          height: NODE_HEIGHT + 20,
          x: 20,
          y: logicHeight,
        }),
      );
      this.FIXED_BOTTOM_LIST.push(logic);
      // 渲染拖拽节点
      this.nodes.forEach((item, index) => {
        let node = JSON.parse(JSON.stringify(this.nodeData)); // 深拷贝对象
        node = Object.assign(node, {
          nodeId: item.typeCode + index,
          id: item.id,
          typeCode: item.typeCode,
          label: item.name,
        });
        // 判断不是逻辑、渠道仓、实仓添加坐标
        if (item.typeCode !== 'channel' && item.typeCode !== 'logic' && item.typeCode !== 'real') {
          Object.assign(node, {
            x: item.x,
            y: item.y,
          });
          this.graph.addNode(node);
        } else {
          // 实仓处理
          if (item.typeCode === 'real') {
            Object.assign(node, {
              // 元素（实仓）在容器中左右居中
              x: nodeGroup.logic.width / 2 - NODE_WITTH / 2,
              // y坐标在逻辑仓组上分
              y: logicHeight - NODE_HEIGHT - 30,
            });
            // 因为addNode()函数添加节点到画布，返回添加的节点，所以直接可以push。
            this.FIXED_BOTTOM_LIST.push(this.graph.addNode(node));
          } else {
            // 创建渠道仓和逻辑仓节点
            const ind =
              item.typeCode === 'channel'
                ? index - res.allList.length
                : index - res.allList.length - res.channelList.length;
            // 创建节点
            // 不能移动的节点自动分配坐标
            Object.assign(node, {
              x: nodeGroup[[item.typeCode === 'channel' ? 'channel' : 'logic']].children[ind],
              y: item.typeCode === 'channel' ? 30 : logicHeight + 10,
              zIndex: 2,
            });
            node.attrs.body.stroke = 'none';
            node.attrs.body.fill = '#3199FF';
            node.attrs.label.fill = '#fff';
            const child = this.graph.addNode(node);
            if (item.typeCode === 'logic') this.FIXED_BOTTOM_LIST.push(child);
            // 添加进对应的组里
            item.typeCode === 'channel' ? channel.addChild(child) : logic.addChild(child);
          }
        }
      });
      this.addEdges();
      /* 侧边栏渲染 */
      if (this.leftAddon) {
        this.createStencil();
      }
      // this.graph.center();
      /** 节点移动 */
      this.graph.on('node:moved', ({ e, x, y, node, view }) => {
        // console.log('触发移动事件');
        // 注意： x,y 是鼠标的位置不是节点位置
        const data = this.nodes.find(e => e.id === node.store.data.id);
        // 判断数据是否发生改变
        const isChangeX = parseInt(node.store.data.position.x) !== parseInt(data.x);
        const isChangeY = parseInt(node.store.data.position.y) !== parseInt(data.y);
        if (data && isChangeX && isChangeY) {
          this.isChange = true;
          Object.assign(data, {
            x: node.store.data.position.x,
            y: node.store.data.position.y,
          });
        }
      });
      /** 单击节点 */
      this.graph.on('node:click', ({ e, x, y, node, view }) => {});
      if (this.menu) {
        /** 右键单击节点 */
        this.graph.on('node:contextmenu', ({ e, x, y, cell, view }) => {
          // console.log(cell);
          if (cell.store.data.typeCode !== 'logic' && !cell.children) {
            // 获取自定义节点
            const node = this.graph.getCellById('contextmenu');
            node.setData({
              typeCode: cell.store.data.typeCode,
              changeId: cell.id,
            });
            node.position(x, y);
            node.visible = true;
          }
        });
      }
      // /** 鼠标移入节点 */
      this.graph.on('node:mouseenter', ({ e, node, view }) => {
        // // 获取邻居节点
        // this.graph.getNeighbors(node).forEach(item => {
        //   // 修改样式
        //   item.attr({
        //     body: {
        //       fill: '#000',
        //       strokeWidth: 1,
        //     },
        //     label: {
        //       fill: '#fff',
        //     },
        //   });
        // });
        // // console.log(this.graph.getNeighbors(node));
        // // 获取与节点/边相连接的边.
        // this.graph.getConnectedEdges(node).forEach(item => {
        //   item.attr({
        //     line: {
        //       stroke: '#000', // 指定 path 元素的填充色
        //       strokeWidth: 2,
        //     },
        //   });
        // });
      });
      /** 鼠标移出节点 */
      this.graph.on('node:mouseleave', ({ e, node, view }) => {
        // this.graph.getNeighbors(node).forEach(item => {
        //   // 还原样式
        //   const typeCode = item.store.data.typeCode;
        //   typeCode !== 'channel' && typeCode !== 'logic'
        //     ? item.attr({
        //         body: {
        //           fill: 'none',
        //         },
        //         label: {
        //           fill: '#000',
        //         },
        //       })
        //     : item.attr({
        //         body: {
        //           fill: '#3199FF',
        //         },
        //       });
        // });
        // this.graph.getConnectedEdges(node).forEach(item => {
        //   item.attr({
        //     line: {
        //       stroke: '#3199FF', // 指定 path 元素的填充色
        //       strokeWidth: 1,
        //     },
        //   });
        // });
      });
      /** 点击边 */
      if (this.clickEdge) {
        this.graph.on('edge:click', ({ e, x, y, edge, view }) => {
          const [source, target, edgeData] = [
            edge.data.source,
            edge.data.target,
            edge.data.edgeData,
          ];
          // 判断是否能够弹窗
          if (
            (source.typeCode === 'real' && target.typeCode === 'ratio-virtual') ||
            (source.typeCode === 'ratio-virtual' && target.typeCode === 'channel')
          ) {
            // 收集接口数据
            this.form = {
              typeCode: edgeData.supportTypeCode,
              ratioTypeCode: edgeData.receiveTypeCode,
              structureId: this.structureId,
              warehouseId: edgeData.supportWarehouseId,
              ratioList: [
                {
                  ratio: edgeData.distributionQty,
                  warehouseId: edgeData.receiveWarehouseId,
                  distributionType: edgeData.distributionType,
                },
              ],
            };
            this.dialogVisible = true;
          }
        });
      }
    },
    getData(structureId) {
      const data = this.api({ structureId }).then(res => {
        return res;
      });
      return data;
    },
    /** 添加连接线（边） */
    addEdges() {
      this.relationList.forEach(item => {
        // 中转虚仓和渠道仓有连接的数据，但是不显示，需要跳过
        if (item.supportTypeCode == 'interline-virtual' && item.receiveTypeCode == 'channel') {
          return false;
        }
        /** supportWarehouseId 起点id */
        /** receiveWarehouseId 终点id */
        const sourceData = this.nodes.find(data => data.id === item.supportWarehouseId);
        const targetData = this.nodes.find(data => data.id === item.receiveWarehouseId);
        if (sourceData && targetData) {
          // 定义基础连接线（边）样式
          const attr = {
            line: {
              stroke: '#3199FF', // 指定 path 元素的填充色
              strokeWidth: 1,
            },
          };
          // 设置中转虚仓和数量虚仓的连接线（边）
          if (
            sourceData.typeCode === 'qty-virtual' &&
            targetData.typeCode === 'interline-virtual'
          ) {
            // 双箭头 + 虚线
            Object.assign(attr.line, {
              // strokeDasharray: 5, // 虚线
              sourceMarker: {
                name: 'classic',
              },
              targetMarker: {
                name: 'classic',
              },
            });
          }
          this.graph.addEdge({
            source: sourceData.id,
            target: targetData.id,
            // 通过 label 设置单个标签，
            label: this.edgeText
              ? item.distributionQty !== null
                ? item.distributionQty + '%'
                : ''
              : '',
            attrs: attr,
            router: this.edgeRouter, // router类型（连接方式）
            data: {
              source: sourceData,
              target: targetData,
              edgeData: item,
            },
          });
        }
      });
    },
    /** 添加节点 */
    handleConfirm(data) {
      const queryData = {
        name: data.name,
      };
      if (data.id) {
        /** 修改 */
        Object.assign(queryData, {
          objectVersionNumber: data.objectVersionNumber,
          warehouseId: data.id,
          x: data.x,
          y: data.y,
        });
      } else {
        /** 创建  */
        const node = this.graph.getNodes()[this.graph.getNodes().length - 1];
        Object.assign(queryData, {
          x: node.store.data.position.x,
          y: node.store.data.position.y,
          structureId: this.structureId,
        });
      }
      queryData.typeCode = data.typeCode;
      stockApi.updataWarehouseStructures(queryData).then(res => {
        if (!res.failed) {
          this.$emit('refresh');
          // this.$refs.storeForm.hide();
          // if (data.id) {
          // } else {
          //   this.nodes.push(res);
          //   this.graph.removeNode(data.nodeId);
          //   let node = JSON.parse(JSON.stringify(this.nodeData)); // 深拷贝对象
          //   node = Object.assign(node, {
          //     nodeId: res.typeCode + this.nodes.length,
          //     id: res.id,
          //     typeCode: res.typeCode,
          //     label: res.name,
          //     x: res.x,
          //     y: res.y,
          //     zIndex: 15,
          //   });
          //   this.graph.addNode(node);
          // }
          // this.msgSuccess(text + '成功');
        }
      });
    },
    /** 设置百分比 */
    onSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          stockApi.addWarehouseStructuresRatio(this.form).then(res => {
            if (!res.failed) {
              this.msgSuccess('设置成功');
              this.$emit('refresh');
            }
          });
        } else {
          return false;
        }
      });
    },
    /** 关闭 */
    storeFormClose(flag, data) {
      if (flag) {
        this.graph.removeNode(data.nodeId);
      }
    },
    /** 保存 */
    onSave(next) {
      // console.log(this.nodes);
      const coordList = this.nodes
        .filter(e => e.typeCode !== 'channel' && e.typeCode !== 'logic')
        .map(e => {
          return {
            ...e,
            warehouseId: e.id,
          };
        });
      // console.log(coordList);
      stockApi.saveWarehouses({ coordList: coordList, structureId: this.structureId }).then(res => {
        if (!res.failed) {
          this.msgSuccess('保存成功');
          this.isChange = false; // 清除
          if (next) next;
        }
      });
    },
    /** 拖动侧边栏节点结束后触发弹窗 */
    handleEndDrag(node) {
      const cloneNode = node.clone({ deep: true });

      /** 检测实仓数量，目前只能一个  Start */
      // if (
      //   'real' == node.store.data.typeCode &&
      //   this.nodes.filter(e => e.typeCode == 'real').length > 0
      // ) {
      //   this.$message.warning('您已经创建了一个实仓了，不能重复创建多个实仓。');
      //   const NODE_ID = cloneNode.id;
      //   setTimeout(() => {
      //     this.graph.removeNode(NODE_ID);
      //     console.log(NODE_ID);
      //   }, 100);
      //   return cloneNode;
      // }
      /** 检测实仓数量，目前只能一个  END */

      /** 这里无法获取正确的x,y坐标（原因未知） */
      const data = {
        nodeId: cloneNode.id,
        typeCode: cloneNode.store.data.typeCode,
        name: cloneNode.label,
      };
      this.$refs.storeForm.show(data);
      return cloneNode;
    },
    /** 创建侧边栏组 */
    createStencil() {
      const stencil = new Addon.Stencil({
        title: '节点',
        target: this.graph,
        stencilGraphWidth: 200,
        stencilGraphHeight: 180,
        collapsable: false,
        getDropNode: this.handleEndDrag,
        groups: [
          {
            title: '虚仓',
            name: 'group1',
          },
          // {
          //   title: '实仓',
          //   name: 'group2',
          //   graphHeight: 250,
          //   layoutOptions: {
          //     rowHeight: 70,
          //   },
          // },
        ],
        layoutOptions: {
          columns: 1, // 网格布局的列数，默认为 2。行数根据节点数自动计算
          columnWidth: 140, // 列宽。auto: 所有节点中最宽节点的宽度作为列宽, compact: 该列中最宽节点的宽度作为列宽。
          rowHeight: 55, // 行高。auto: 所有节点中最高节点的高度作为行高，compact: 该行中最高节点的高度作为行高。
        },
      });
      document.getElementById('stencil').appendChild(stencil.container);
      Graph.registerNode(
        'custom-rect',
        {
          inherit: 'rect',
          width: NODE_WITTH,
          height: NODE_HEIGHT,
          attrs: {
            body: {
              strokeWidth: 1,
              rx: 4,
              ry: 4,
            },
            text: {
              fontSize: 10,
              fill: '#606266',
            },
          },
        },
        true,
      );
      const arr = [
        { label: '比例虚仓', typeCode: 'ratio-virtual' },
        { label: '数量虚仓', typeCode: 'qty-virtual' },
        { label: '中转虚仓', typeCode: 'interline-virtual' },
        { label: '实仓', typeCode: 'real' },
      ];
      arr.forEach((item, index) => {
        arr[index] = this.graph.createNode({
          shape: 'custom-rect',
          ...item,
        });
      });
      stencil.load(
        arr.filter(e => e.store.data.typeCode !== 'real'),
        'group1',
      );
      // stencil.load(
      //   arr.filter(e => e.store.data.typeCode === 'real'),
      //   'group2',
      // );
    },
    /** 计算组宽度和子节点x轴位置 */
    getNodeGroupWidth(res) {
      const nodeGroup = {
        channel: {
          children: [],
        },
        logic: {
          children: [],
        },
      };
      // 遍历并计算每个渠道和逻辑仓的x坐标
      Object.keys(nodeGroup).forEach(key => {
        const name = key + 'List';
        if (res[name].length) {
          // 计算节点总长度
          const nodesWidth = (NODE_WITTH + 10) * res[name].length + 10;
          // 容器总长度
          const containerWidth = this.$refs.container.offsetWidth - 40;
          // 判断是否总长度大于容器
          nodeGroup[key].width = nodesWidth > containerWidth ? nodesWidth : containerWidth;
          // 平均值
          const mean = nodeGroup[key].width / res[name].length;
          res[name].forEach((item, index) => {
            // 计算每个节点的x轴位置
            const x = mean * (index + 1) - mean / 2 - NODE_WITTH / 2;
            nodeGroup[key].children.push(x);
          });
        }
      });
      return nodeGroup;
    },
  },
};
</script>
<style lang="scss" scoped>
.app-container {
  height: calc(100vh - 85px);
  display: flex;
  .aside {
    position: relative;
    width: 160px;
    height: 100%;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  .main {
    position: relative;
    height: 100%;
    flex: 1;
    margin-left: 20px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    .text-right {
      padding: 10px;
      border-bottom: 1px solid #eee;
    }
    .container {
      position: relative;
      height: calc(100% - 48px);
    }
  }
}
</style>
